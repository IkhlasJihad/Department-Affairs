
package Controllers;
import Models.Navigation;
import Models.Utility;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Ikhlas Jihad
 */
public class AddCourseController implements Initializable {
    String Dept = StartController.department;
    Navigation nav = new Navigation();
    private Integer tab ;
    @FXML
    private Button close;
    @FXML
    private Button add;
    @FXML
    private TextField id;
    @FXML
    private TextField title;
    @FXML
    private TextField cred;
    @FXML
    private Label label_out;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tab = (Integer)rb.getObject("res"); 
    }    
    @FXML
    private void nav_back(ActionEvent event) {   
        nav.navTowithTab(root, nav.fxmlManage, tab);
    }

    @FXML
    private void addCourse(ActionEvent event) {
        if(id.getText() != null ){
            try {
                if(Utility.DB.validateCourseID(id.getText()) == 0){  
                        int i = Utility.DB.insertCourse(id.getText(), title.getText(), Dept, 
                                Integer.parseInt(cred.getText())); 
                        if (i == 1 ){
                             label_out.setText("Successfully Added");
                        }
                }  
                else
                    label_out.setText("Course ID must be distinct");
            } catch (Exception ex) {
               // Logger.getLogger(AddstdController.class.getName()).log(Level.SEVERE, null, ex);
                label_out.setText("Remember: (credits > (0)::numeric) & course_id is PRIMARY KEY");
            } 
        }
        else
            label_out.setText("id can't be null");
    }    
    
}
    