
package Controllers;

import Models.DBModel;
import Models.Navigation;
import Models.Utility;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Ikhlas Jihad
 */
public class AddInstructorController implements Initializable {
    String Dept = StartController.department;
    Navigation nav = new Navigation();
    Utility util = new Utility();
    @FXML
    private AnchorPane root;
    @FXML
    private Button close;
    @FXML
    private Button add;

    @FXML
    private TextField name;
    @FXML
    private TextField salary;
    @FXML
    private TextField pic;
    @FXML
    private Label label_out;
    private Integer tab;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(null);
        pic.setText("");
        tab = (Integer)rb.getObject("res");
    }    

    @FXML
    private void nav_back(ActionEvent event) {
      //  nav.navTo(root, nav.fxmlManage);
        nav.navTowithTab(root, nav.fxmlManage, tab);
    }

    @FXML
    private void addInst(ActionEvent event) {
        try {  
            String i = salary.getText(); double s ;
            if(i.equals(""))
                s = 0;
            else 
                s = Double.parseDouble(salary.getText());
            int newID = Utility.DB.addInstructor(name.getText(), 
                Dept, s, pic.getText()); 
            label_out.setText("Successfully Added\nID = " + newID); 
            } catch (Exception ex) {
               // Logger.getLogger(AddstdController.class.getName()).log(Level.SEVERE, null, ex);
                label_out.setText(ex.getMessage());
            } 
    }     

    @FXML
    private void browsePic(MouseEvent event) {
        String path = util.browseImg();
        if(path != null){
            util.browseImg();
            pic.setText(path);
        }
    }
    
}
