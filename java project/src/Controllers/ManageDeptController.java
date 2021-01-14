
package Controllers;
import Models.Course;
import Models.Instructor;
import Models.Navigation;
import Models.Student;
import Models.Utility;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
/**
 * FXML Controller class
 * @author Ikhlas Jihad
 */
public class ManageDeptController implements Initializable {
    String Dept = StartController.department;
    Navigation nav = new Navigation();
    @FXML private AnchorPane root;
    @FXML TabPane tabs;
    @FXML Tab std_tab;
    @FXML Tab inst_tab;
    @FXML Tab courses_tab;
    @FXML private TableColumn<Instructor, String> inst_id;
    @FXML private TableColumn<Instructor, String> inst_name;    
    @FXML private TableColumn<Instructor, String> salary;
    @FXML private TableColumn<Student, String> std_id;
    @FXML private TableColumn<Student, String> std_name;
    @FXML private TableColumn<Student, String> tot_cred;
    @FXML private TableColumn<Course, String> c_id;
    @FXML private TableColumn<Course, String> title;
    @FXML private TableColumn c_cred;
    @FXML private TableView<Instructor> instTable;
    @FXML private TableView<Student> std_table;
    @FXML private TableView<Course> courseTable;
    private  Integer curT ;
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        if(rb != null){
            curT = (Integer) rb.getObject("res");
            tabs.getSelectionModel().select(curT);
        }
        setCellValues();
        try {            
            courseTable.setItems(FXCollections.observableArrayList(Utility.DB.getCourses(Dept)));
            instTable.setItems(FXCollections.observableArrayList(Utility.DB.getInstructors(Dept)));
            std_table.setItems(FXCollections.observableArrayList(Utility.DB.getStds(Dept)));
        } catch (SQLException ex) {
            Logger.getLogger(ManageDeptController.class.getName()).log(Level.SEVERE, null, ex);
        }
        title.setOnEditCommit(
            new EventHandler<CellEditEvent<Course, String>>() {
                public void handle(CellEditEvent<Course, String> t) {
                    Course  c = courseTable.getSelectionModel().getSelectedItem();
                    ((Course) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTitle(t.getNewValue());
                    courseTable.refresh();
                    try {
                        Utility.DB.updateCourseTitle(c.getC_id(), t.getNewValue());
                    } catch (SQLException ex) {
                        //System.out.println(ex.getMessage());
                    }
                }
            }
        );
        c_cred.setOnEditCommit(
            new EventHandler<CellEditEvent<Course, Integer>>() {
                public void handle(CellEditEvent<Course, Integer> t) {
                    Course  c = courseTable.getSelectionModel().getSelectedItem();
                    ((Course) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setC_cred(t.getNewValue());
                    courseTable.refresh();
                    try {
                        Utility.DB.updateCourseCred(c.getC_id(), t.getNewValue());
                    } catch (SQLException ex) {
                        //System.out.println(ex.getMessage());
                    }
                }
            }
        );
    }   
    
    private void setCellValues(){
        //instructor
        inst_id.setCellValueFactory(new PropertyValueFactory<>("Inst_id"));
        inst_name.setCellValueFactory(new PropertyValueFactory<>("Inst_name"));
        salary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        //student
        std_id.setCellValueFactory(new PropertyValueFactory<>("Std_id"));
        std_name.setCellValueFactory(new PropertyValueFactory<>("Std_name"));
        tot_cred.setCellValueFactory(new PropertyValueFactory<>("Tot_cred"));
        //course
        c_id.setCellValueFactory(new PropertyValueFactory<>("C_id"));
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        c_cred.setCellValueFactory(new PropertyValueFactory<>("C_cred"));
        title.setCellFactory(TextFieldTableCell.<Course>forTableColumn());
        c_cred.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    @FXML
    private void on_add_instructor(ActionEvent event) {
            nav.navTowithTab(root, nav.fxmladdinst, tabs.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void on_add_std(ActionEvent event) {
        nav.navTowithTab(root, nav.fxmladdSTD, tabs.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void on_add_course(ActionEvent event) {            
        nav.navTowithTab(root, nav.fxmladdC, tabs.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void nav_back(ActionEvent event) {
        nav.navTo(root,nav.fxmlstart);
    }
  
    @FXML
    private void showSTD(MouseEvent event) {
        Student std = std_table.getSelectionModel().getSelectedItem();
        if(std != null)
            if (event.getClickCount() == 2 && ( std.getStd_id()!= null ) ) {
                nav.navTowithStudent(root, nav.fxmlSdetails, std, tabs.getSelectionModel().getSelectedIndex());
            }
    }

    @FXML
    private void showInst(MouseEvent event) {
        Instructor inst = instTable.getSelectionModel().getSelectedItem();
        if(inst != null)
            if (event.getClickCount() == 2 && ( inst.getInst_id()!= null ) ) {
                nav.navTowithInst(root, nav.fxmlinst, inst,tabs.getSelectionModel().getSelectedIndex() );
            }
    }
    
    
    
    
}
