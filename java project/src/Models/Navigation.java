
package Models;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
/**
 *
 * @author Ikhlas Jihad
 */
public class Navigation {
    public final String fxmlstart = "/views/start.fxml";
    public final String fxmlManage = "/views/manageDept.fxml";
    public final String fxmladdSTD = "/views/addstd.fxml";
    public final String fxmladdinst = "/views/addInstructor.fxml"; 
    public final String fxmladdC = "/views/addCourse.fxml";
    public final String fxmlSdetails = "/views/std_details.fxml";
    public final String fxmlinst = "/views/inst_details.fxml";
    public void navTo(Parent rootPane, String path){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void navTowithStudent(Parent rootPane, String path, Student s, Integer t){
         
         MyResources_s res = new MyResources_s(s,t);
            try {
                Parent root = FXMLLoader.load(getClass().getResource(path),res);
                rootPane.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void navTowithInst(Parent rootPane, String path, Instructor i, Integer t ){
         
         MyResources_i res = new MyResources_i(i,t);
            try {
                Parent root = FXMLLoader.load(getClass().getResource(path),res);                               
                rootPane.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public void navTowithTab(Parent rootPane, String path, Integer s){
         
        MyResources_tab res = new MyResources_tab(s);
            try {
                Parent root = FXMLLoader.load(getClass().getResource(path),res);
                rootPane.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 
public class MyResources_tab extends ListResourceBundle {
    private Integer t  ;
    MyResources_tab(){}
    public MyResources_tab(Integer t){this.t = t;}
    
     @Override
     protected Object[][] getContents() {
        return new Object[][] {
         // LOCALIZE THIS
             {"res", t}                    
        };
    }
 }
    
public class MyResources_s extends ListResourceBundle {
    
    private Student s ;
    private Integer t;
    MyResources_s(){}
    public MyResources_s(Student s,  Integer t){
        this.s = s;
        this.t = t;}
    
     @Override
     protected Object[][] getContents() {
         return new Object[][] {
         // LOCALIZE THIS
             {"std", s} ,
             {"tab",t}
         };
     }
 }

public class MyResources_i extends ListResourceBundle {
    private Instructor i ;
    private Integer t;
    MyResources_i(){}
    public MyResources_i(Instructor i, Integer t){
        this.i = i;
        this. t = t; }
    
     @Override
     protected Object[][] getContents() {
        return new Object[][] {
         // LOCALIZE THIS
            {"inst", i},
            {"tab", t}
                
        };
    }
 }

    
}
