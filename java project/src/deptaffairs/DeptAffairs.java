
<<<<<<< HEAD:java project/src/Deprtment_Affairs/Department_Affairs.java
package Deprtment_Affairs;
=======
package deptaffairs;
>>>>>>> 569ec14a16d847c42f739c81b7fcdff470698bdf:java project/src/deptaffairs/DeptAffairs.java
import Models.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 *
 * @author Ikhlas Jihad 
 */
<<<<<<< HEAD:java project/src/Deprtment_Affairs/Department_Affairs.java
public class Department_Affairs extends Application{
=======
public class DeptAffairs extends Application{
   
>>>>>>> 569ec14a16d847c42f739c81b7fcdff470698bdf:java project/src/deptaffairs/DeptAffairs.java
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/start.fxml"));
        stage.setScene(new Scene(root,600,385));
        stage.setResizable(false);
        stage.setTitle("Department Affairs");        
        stage.show();        
    }
    @Override
    public void stop(){
        Utility.DB.exit();
    }
    
    
}
