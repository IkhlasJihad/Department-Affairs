package Deprtment_Affairs;
import Models.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * @author Ikhlas Jihad 
 */
public class Department_Affairs extends Application{
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
