
package Controllers;

import Models.Navigation;
import Models.Utility;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AddstdController implements Initializable {
   
    String Dept = StartController.department;
    Navigation nav = new Navigation();
    @FXML
    private Button close;
    @FXML
    private Button add;
    
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField cred;
    @FXML
    private TextField pic;
    @FXML
    private AnchorPane root;
    @FXML
    private Label label_out;
    private Integer tab;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tab = (Integer)rb.getObject("res"); 
        name.setText(null);
        pic.setText("");
        //cred.setText();
    }    

    @FXML
    private void nav_back(ActionEvent event) {
        nav.navTowithTab(root, nav.fxmlManage, tab);
    }

    @FXML
    private void addStudent(ActionEvent event) {
        System.out.println(".... Adding  .... ");
        try {
            String i = cred.getText(); int c ;
            if(i.equals(""))
                c = 0;
            else 
                c = Integer.parseInt(cred.getText());
            int newID = Utility.DB.addStd(name.getText(), 
                Dept, c, pic.getText()); 
            label_out.setText("Successfully Added\n>>>ID = " + newID );
        } catch (Exception ex) {
           Logger.getLogger(AddstdController.class.getName()).log(Level.SEVERE, null, ex);
            label_out.setText(ex.getMessage());
        } 
    } 

    @FXML
    private void browsePic(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("png image","'.png"));
        File f = fc.showOpenDialog(null);
        try{
            pic.setText(f.getAbsolutePath());}
        catch(Exception ex){
            pic.setText("");
        }
    }
    
    
}
