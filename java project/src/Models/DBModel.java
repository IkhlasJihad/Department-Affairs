
package Models;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import org.postgresql.ds.PGSimpleDataSource;
/**
 *
 * @author Ikhlas Jihad
 */
public class DBModel {
    public DBModel(){
        schemaConnect("uni_space");
    }
    private static DBModel dbmodel = null;
    Connection conn = null;
    public static DBModel getModel(){
        if (dbmodel == null)
            dbmodel = new DBModel();
        return dbmodel;
    }
    
    public void connect(){
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("UNI1");
        source.setUser("postgres");
        source.setPassword("admin");
        try {
            conn = source.getConnection();
            //System.out.println("Connected to database");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
        
    }
    
    public void schemaConnect(String schema){
        String sql = "set search_path to '"+ schema + "'";
        Statement s1 = null;
        try {
            connect();
            s1 = conn.createStatement();
            s1.execute(sql);
           // System.out.println("Connected to schema "+ schema);
        } catch (SQLException ex) {
        }finally{
            try {
                s1.close();
            } catch (SQLException ex) {
            }        
        }        
       }
    
    private void closeEverything() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exit() {
        closeEverything();
        System.out.println("Exiting... \nBye!");
        System.exit(0);
    }
    
    public ArrayList<DeptDetails> deptDetails(String dept) throws SQLException{
        String query = "select  dept_name, building, budget, count(distinct student.id)," +
                    " count(distinct instructor.id) as instructors,coalesce(cast(avg(salary) as numeric(12,2)),0)" +
                    " from  (student left join instructor using(dept_name)) natural join department " +
                    " where dept_name = (?) group by 1,2,3;";
        ArrayList <DeptDetails> details = new ArrayList<>();
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(1, dept);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){ 
            DeptDetails d = new DeptDetails(rs.getString(1), rs.getString(2), rs.getDouble(3),
                   rs.getInt(4), rs.getInt(5), rs.getDouble(6));
            System.out.println(">>>> Result " + d.toString());
            details.add (d);
        }  
        rs.close();
        pstm.close();
        return details; 
    }
    
    public ArrayList<String> departments(){
        String query = "select dept_name from department;";
        ArrayList<String> depts = new ArrayList<>();
        try (Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(query)
            ){
            while(rs.next()){
                depts.add(rs.getString(1));
            }  
            return depts;
        } catch (SQLException ex) {            
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList<Course> getCourses(String dept) throws SQLException{
        ArrayList<Course> cs = new ArrayList<>();
        String query = "select course_id, title, credits from course where dept_name = ? ;";
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(1, dept);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            cs.add(new Course(rs.getString(1), rs.getString(2), rs.getInt(3)));        
        }
        pstm.close();
        rs.close();
        return cs;
    }
    
    public ArrayList<Student> getStds(String dept) throws SQLException{
        ArrayList<Student> std = new ArrayList<>();
        String query = "select id, name, tot_cred, std_pic from student where dept_name = ? ;";
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(1, dept);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            Student s =  new Student(rs.getString(1), rs.getString(2),dept, rs.getInt(3));
            byte[] imgBytes = rs.getBytes(4);
            if(imgBytes != null)
              s.setStd_pic(new Image(new ByteArrayInputStream(imgBytes)));
            else s.setStd_pic(null);                
            std.add(s); 
        }
        pstm.close();
        rs.close();
        return std;
    }
    
    public ArrayList<Instructor> getInstructors(String dept) throws SQLException{
        ArrayList<Instructor> instList = new ArrayList<>();
        String query = "select id, name, salary, inst_pic from instructor where dept_name = ? ;";
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(1, dept);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            byte[] imgBytes = rs.getBytes(4);  
            Instructor inst = new Instructor(rs.getString(1), rs.getString(2), dept, rs.getDouble(3));
            if(imgBytes != null)
                inst.setInst_pic(new Image(new ByteArrayInputStream(imgBytes)));
            else
                inst.setInst_pic(null); // null value
            instList.add(inst);  
        }
        pstm.close();
        rs.close();
        return instList;
    }
    
    public int isIdValid(String std_id) throws SQLException{
           PreparedStatement ps1 = conn.prepareStatement("select * from student where id = (?) ;");          
            //fist let's check if the it is a valid ID
            ps1.setString(1, std_id);
            ResultSet rs = ps1.executeQuery();
            if (rs.next())
                return 1; //NOT Valid           
            return 0; 
    }
    
    public int isinstIDValid(String std_id) throws SQLException{
       PreparedStatement ps1 = conn.prepareStatement("select * from instructor where id = (?) ;");          
        //fist let's check if the it is a valid ID
        ps1.setString(1, std_id);
        ResultSet rs = ps1.executeQuery();
        if (rs.next())
            return 1; //NOT Valid           
        return 0; 
    }
    
    public int addInstructor(String name, String d, double salary, String path) throws SQLException, FileNotFoundException, IOException{
        String query = "insert into instructor(id, name, dept_name, salary, inst_pic) values(?,?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(1,String.valueOf(getMaxInstID() +1 ));
        pstm.setString(2, name);
        pstm.setString(3, d);
        pstm.setDouble(4, salary);
       if(path != null & !path.equals("")){
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            pstm.setBinaryStream(5, fis, file.length());
            fis.close();
        }
        else pstm.setBytes(5, null);
        pstm.executeUpdate();
        pstm.close();
        return (getMaxInstID() + 1) ;
    }
    
    private int getMaxStdID() throws SQLException{
        String sql = "select max(id) from student";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }
    
    private int getMaxInstID() throws SQLException{
        String sql = "select max(id) from instructor";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }

    public int addStd(String name, String d, int cred, String path) throws SQLException, FileNotFoundException, IOException{
        String query = "insert into student(id, name, dept_name, tot_cred, std_pic) values(?,?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(1,String.valueOf(getMaxStdID()+1));
        pstm.setString(2, name);
        pstm.setString(3, d);
        pstm.setInt(4, cred);
        if(path != null & !path.equals("")){
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            pstm.setBinaryStream(5, fis, file.length());
            fis.close();
        }
        else pstm.setBytes(5, null);
        pstm.executeUpdate();
        pstm.close();
        return (getMaxStdID()+1);
    }
    
    public void insertImg ( String std_id,String path) throws IOException, SQLException{ 
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        PreparedStatement pstm = conn.prepareStatement("update student set std_pic = ? where id = ? ; ");
        pstm.setString(2, std_id);
        pstm.setBinaryStream(1, fis, file.length());
        pstm.executeUpdate();
        fis.close();
    }
    
    public void insertInstImg (String id,String path) throws IOException, SQLException{ 
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        PreparedStatement pstm = conn.prepareStatement("update instructor set inst_pic = ? where id = ? ; ");
        pstm.setString(2, id);
        pstm.setBinaryStream(1, fis, file.length());
        pstm.executeUpdate();
        fis.close();
    }
    
    public int insertCourse(String id, String title, String d, int c) throws SQLException{
        String query = "insert into course(course_id, title, dept_name, credits) values(?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(1,id);
        pstm.setString(2, title);
        pstm.setString(3, d);
        pstm.setInt(4, c);
        int i = pstm.executeUpdate();
        pstm.close();
        return i;
    }
    
    public int validateCourseID(String id) throws SQLException{
        PreparedStatement ps1 = conn.prepareStatement("select * from course where course_id = (?) ;");          
        ps1.setString(1, id);
        ResultSet rs = ps1.executeQuery();
        if (rs.next())
            return 1; //NOT Valid 
        System.out.println("Valid");
        return 0; 
    }
    
    public void updateCourseCred(String cid, int newValue) throws SQLException{
        String query = "update course set credits = (?) where course_id = ?;";
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(2,cid);
        pstm.setInt(1, newValue);
        pstm.executeQuery();
        pstm.close();
    }
    
    public void updateCourseTitle(String cid, String newValue) throws SQLException{
        String query = "update course set title = (?) where course_id = ?;";
        PreparedStatement pstm = conn.prepareStatement(query);
        pstm.setString(1,newValue);
        pstm.setString(2, cid);
        pstm.executeQuery();
        pstm.close();
    }
    
    public boolean updateStdDept(String id, String dept) {
        String query = "update student set dept_name = ? where id = ? ;";
        try(PreparedStatement pstm = conn.prepareStatement(query)){
            pstm.setString(1, dept);
            pstm.setString(2, id);
            if (!pstm.execute())
                if (pstm.getUpdateCount()> 0)
                    System.out.println("Updated successfully");
            return true;
        }
        catch (SQLException ex) {
            return false;
        }
    }

    public boolean updatestdName(String id, String name) {
        String query = "update student set name = ? where id = ? ;";
        try(PreparedStatement pstm = conn.prepareStatement(query)){
            pstm.setString(1, name);
            pstm.setString(2, id);
            if (!pstm.execute())
                if (pstm.getUpdateCount()> 0)
                    System.out.println("Updated successfully");
            return true;
        }
        catch (SQLException ex) {
            return false;
        }
    }

    public boolean updateCred(String id, int newCred){
        String query = "update student set tot_cred = ? where id = ? ";
        int updatedrows = 0;
        try ( PreparedStatement stm = conn.prepareStatement(query)){
            stm.setInt(1, newCred);
            stm.setString(2, id);
            updatedrows = stm.executeUpdate();
            if (updatedrows > 0 )
                System.out.println("Updated Successfully:)");
            return true;
        } catch (SQLException ex) { return false;
        }

    }
    
    public boolean updateInsrDept(String id, String dept) {
        String query = "update instructor set dept_name = ? where id = ? ;";
        try(PreparedStatement pstm = conn.prepareStatement(query)){
            pstm.setString(1, dept);
            pstm.setString(2, id);
            if (!pstm.execute())
                if (pstm.getUpdateCount()> 0){
                    System.out.println("Updated successfully");
                    return true;
        }
        return false;
        }
        catch (SQLException ex) {
            return false;

        }
    }
    
    public boolean updateInstName(String id, String name) {
        String query = "update instructor set name = ? where id = ? ;";
        System.out.println("here is updateInstName");
        System.out.println("id: "+ id);
            System.out.println("name: "+ name);
        try(PreparedStatement pstm = conn.prepareStatement(query)){
            pstm.setString(1, name);
            pstm.setString(2, id);
            
            if (pstm.executeUpdate()>0){
                    System.out.println("Updated successfully");
                    return true;
        } 
            return false;}
        catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updateSalary(String id, double salary){
        String query = "update instructor set salary = ? where id = ? ;";
        int updatedrows = 0;

        try ( PreparedStatement stm = conn.prepareStatement(query)){
            stm.setDouble(1, salary);
            stm.setString(2, id);
            updatedrows = stm.executeUpdate();
            if (updatedrows > 0 )
            System.out.println("Updated Successfully:)");
            return true;
        } catch (SQLException ex) {
                return false;        
        }
    }

    public boolean deleteStdByID(String id){
        String query = "delete from student where id = ? ;";
        try(PreparedStatement pstm = conn.prepareStatement(query)){
            pstm.setString(1, id);
            if (pstm.executeUpdate() > 0)
                System.out.println("Deleted Successfully");
            return true;
        }
        catch (SQLException ex) {
                return false;    
        }
    }
    
    public boolean deleteInstByID(String id){
        String query = "delete from instructor where id = ? ;";
        try(PreparedStatement pstm = conn.prepareStatement(query)){
            pstm.setString(1, id);
            if (pstm.executeUpdate() > 0)
                System.out.println("Deleted Successfully");
            return true;
        }
        catch (SQLException ex) {
                return false;    
        }
    }
    
    public Image showImg(String std_id) throws SQLException{
        Image img = null; 
        PreparedStatement pstm = conn.prepareStatement
        ("select std_pic from student where id = ? ;");
        pstm.setString(1, std_id);
        ResultSet rs = pstm.executeQuery();
        if(rs != null ) {
            while(rs.next()){
                byte[] imgBytes = rs.getBytes(1);
                img = new Image(new ByteArrayInputStream(imgBytes));
            }
            rs.close();
        }
        pstm.close();
        return img;         
   }
    
    public Image showInstImg(String id) throws SQLException{
        Image img = null; 
        PreparedStatement pstm = conn.prepareStatement
        ("select inst_pic from instructor where id = ?");
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if(rs != null ) {
            while(rs.next()){
                byte[] imgBytes = rs.getBytes(1);
                img = new Image(new ByteArrayInputStream(imgBytes));
            }
            rs.close();
        }
        pstm.close();
        return img;         
   }
}
