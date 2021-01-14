
package Controllers;

import Models.Navigation;
import Models.Student;
import Models.Utility;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ikhlas Jihad 
 */
public class Std_detailsController implements Initializable {
    Navigation nav = new Navigation();
    Utility util = new Utility();
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView img;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField cred;
    @FXML
    private Button back;
    @FXML
    private ComboBox deptCombo;
    @FXML
    private Button save;
    @FXML
    private Label label_out;
    Student oldStd;
    @FXML DialogPane dialoge;
    @FXML
    private AnchorPane d_root;
    ButtonType NO;
    ButtonType YES;       
    @FXML
    private Button delete;
    @FXML
    private AnchorPane delete_root;
    @FXML
    private DialogPane delet_dialoge;
    @FXML
    private ButtonType delete_NO;
    @FXML
    private ButtonType delete_ues;
    private String path = null;
    private boolean imgChanged = false;
    private boolean saved  = false;
    private Integer tab;
    private void initialiseObj(Student std){
        id.setText(std.getStd_id());
        id.setEditable(false);
        name.setText(std.getStd_name());
        cred.setText(String.valueOf(std.getTot_cred()));
        deptCombo.setItems(FXCollections.observableArrayList( Utility.DB.departments()));
        deptCombo.setValue(std.getDept());
        if(std.getStd_pic()!= null){
            //System.out.println(">>> Image is loading ... ");
            try {
                img.setImage( Utility.DB.showImg(id.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(Std_detailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            img.setFitHeight(180);
            img.setFitWidth(150);
        }    
        else{
            //System.out.println("NO Image");
            img.setImage(new Image("unknown.png"));
            img.setFitHeight(180);
            img.setFitWidth(150);
        }    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Student std = (Student)rb.getObject("std");
        tab = (Integer)rb.getObject("tab"); 
        oldStd = std;
        initialiseObj(std);
        //edit dialog
        Button d_yes = (Button) dialoge.lookupButton(ButtonType.YES);
        if(d_yes !=null)
            d_yes.setOnAction(e -> { 
                //System.out.println("YES pressed");
                updateFields();
                nav.navTowithTab(root, nav.fxmlManage, tab);
            });  
        Button d_no = (Button) dialoge.lookupButton(ButtonType.CANCEL);
        if(d_no != null)
            d_no.setOnAction(e -> { 
                //System.out.println("NO pressed");/* ... */
                nav.navTowithTab(root, nav.fxmlManage, tab);          
            });
        //delete dialog
        Button delete_yes = (Button) delet_dialoge.lookupButton(ButtonType.YES);
        if(delete_yes != null)
            delete_yes.setOnAction(e -> { 
                //System.out.println("Deleting .... ");/* ... */ 
                if(! Utility.DB.deleteStdByID(id.getText()))
                   label_out.setText("Deleting Failed!");
                nav.navTowithTab(root, nav.fxmlManage, tab);
            }); 
        Button delete_no = (Button) delet_dialoge.lookupButton(ButtonType.NO);
        if(delete_no != null)
            delete_no.setOnAction(e -> { 
                //System.out.println("Not Deleted. ");/* ... */  
                delete_root.setVisible(false);
                 root.setFocusTraversable(true);
            });  
    }       
    @FXML
    private void nav_back(ActionEvent event) throws IOException {
        if(!saved){
            if( checkChnges()){
                System.out.println("Save Dialog");
                d_root.setVisible(true);                
            }
            else nav.navTowithTab(root, nav.fxmlManage, tab);
        }
        else nav.navTowithTab(root, nav.fxmlManage, tab);
    }
    private boolean checkChnges(){
        return(! oldStd.getDept().equals(deptCombo.getValue().toString())
               || oldStd.getTot_cred() != Integer.parseInt(cred.getText())
               || ! oldStd.getStd_name().equals(name.getText())
               || imgChanged );    
    }
    @FXML
    private void on_save_btn(ActionEvent event) {
        saved = true;
        if(checkChnges()){
           updateFields();
        }
        else
           label_out.setText("Nothimg changes.");
    }
    
    private void updateFields(){
        String idS = id.getText();
        try {
            if(path != null){
                 Utility.DB.insertImg(idS, path);
                 label_out.setText("SuccessFully Updated");
                 imgChanged = false;
            }
            if( Utility.DB.updateCred(idS, Integer.parseInt(cred.getText()))
                &  Utility.DB.updateStdDept(idS, deptCombo.getValue().toString())
                &  Utility.DB.updatestdName(idS, name.getText()))
                {
                    label_out.setText("SuccessFully Updated");
                    
                }
        } catch (Exception ex) {
            label_out.setText("Error Occured.\n" + ex.getMessage());
        } 
    }

    @FXML
    private void select(MouseEvent event) {
    }

    @FXML
    private void on_delete_btn(ActionEvent event) {
            delete_root.setVisible(true);
    }

    @FXML
    private void playToolTip(MouseEvent event) {
        util.playToolTip(img);
    }
    
    @FXML
    private void handleImg(MouseEvent event) {
        path = util.browseImg();  
        System.out.println(path);
        if(path == null)
            label_out.setText("No image is selected.");
        else{
            try {
                util.handleImage(path, img);
                imgChanged = true; 
        } catch (Exception ex) {
                imgChanged = false;
                label_out.setText(ex.getMessage());
            }
        }
    }

}