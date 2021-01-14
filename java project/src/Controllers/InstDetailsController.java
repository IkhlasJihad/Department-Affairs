package Controllers;

import Models.Instructor;
import Models.Navigation;
import Models.Utility;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
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
 *
 * @author Ikhlas Jihad
 */
public class InstDetailsController implements Initializable {
    Navigation nav = new Navigation();
    Utility util = new Utility();
    @FXML
    private ImageView img;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField salary;
    @FXML
    private Button back;
    @FXML
    private ComboBox deptCombo;
    @FXML
    private Button save;
    @FXML
    private Label label_out;
    @FXML
    private AnchorPane d_root;
    @FXML
    private DialogPane dialoge;
    ButtonType NO;
    ButtonType YES;
    Instructor oldI;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane delete_root;
    @FXML
    private DialogPane delet_dialoge;
    @FXML
    private Button delete;
    @FXML
    private ButtonType del_no;
    @FXML
    private ButtonType del_yes;
    String path = null;
    boolean changed = false;
    boolean saved = false;
    private Integer tab;
    private void initializeObj(Instructor i){
        id.setEditable(false);
        id.setText(i.getInst_id());
        name.setText(i.getInst_name());
        salary.setText(String.valueOf(i.getSalary()));
        deptCombo.setItems(FXCollections.observableArrayList( Utility.DB.departments()));
        deptCombo.setValue(i.getDept_name());
        if(i.getInst_pic()!= null){
           // System.out.println(">>> Image is loading ... ");
            img.setImage(i.getInst_pic());
            img.setFitHeight(180);
            img.setFitWidth(150);
        }    
        else{
            //System.out.println("NO Image");
            img.setImage(new Image("/views/teacher.png"));
            img.setFitHeight(180);
            img.setFitWidth(150);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tab = (Integer)rb.getObject("tab"); 
        Instructor i = (Instructor)rb.getObject("inst");
        oldI = i;
        initializeObj(i);         
        Button d_yes = (Button) dialoge.lookupButton(ButtonType.YES);
        if(d_yes != null)
        d_yes.setOnAction(e -> { 
            System.out.println("YES pressed");/* ... */ 
            updateFields();
           nav.navTowithTab(root, nav.fxmlManage, tab);
        }); 
        Button d_no = (Button) dialoge.lookupButton(ButtonType.NO);
        if(d_no != null)
        d_no.setOnAction(e -> { 
            System.out.println("NO pressed");/* ... */
            nav.navTowithTab(root, nav.fxmlManage, tab);           
        });     
        //delete dialog
        Button delete_yes = (Button) delet_dialoge.lookupButton(ButtonType.YES);
        if(delete_yes != null)
            delete_yes.setOnAction(e -> { 
                System.out.println("Deleting .... ");/* ... */ 
                if(! Utility.DB.deleteInstByID(id.getText())) 
                   label_out.setText("Deleting Failed!");
                nav.navTowithTab(root, nav.fxmlManage, tab);
            }); 
        Button delete_no = (Button) delet_dialoge.lookupButton(ButtonType.NO);
        if(delete_no != null)
            delete_no.setOnAction(e -> { 
                System.out.println("Not Deleted. ");/* ... */  
                delete_root.setVisible(false);
                 root.setFocusTraversable(true);
            }); 
    }
    private void updateFields(){
        String ID = id.getText();

        try {
            if(path != null){
                Utility.DB.insertInstImg(ID,path);
                label_out.setText("SuccessFully Updated");
               // changed = false;
            }

            if( Utility.DB.updateSalary(ID, Double.parseDouble(salary.getText()))
                    &Utility.DB.updateInsrDept(ID, deptCombo.getValue().toString())
                    & Utility.DB.updateInstName(ID, name.getText()))
                label_out.setText("SuccessFully Updated");
        } catch (Exception ex) {
            label_out.setText("Failed.");
        } 
    } 
    
    private boolean checkChnges(){
        return (! oldI.getDept_name().equals(deptCombo.getValue().toString())
                || oldI.getSalary()!= Double.parseDouble(salary.getText())
                || ! oldI.getInst_name().equals(name.getText())
                || changed 
                );
    }
    @FXML
    private void nav_back(ActionEvent event) {
        if(!saved){
            if( checkChnges()){
            System.out.println("Save Dialog");
            //handledialoge();
            d_root.setVisible(true);
            root.setFocusTraversable(false);
            } 
            else nav.navTowithTab(root, nav.fxmlManage, tab);
        }
        else nav.navTowithTab(root, nav.fxmlManage, tab);
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

    @FXML
    private void select(MouseEvent event) {
    }

    @FXML
    private void on_delete_btn(ActionEvent event) {
        delete_root.setVisible(true);
        root.setFocusTraversable(false);
            
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
                System.out.println("Trying .. ");
                File file = new File(path);
                FileInputStream fis = new FileInputStream(file);
                img.setImage(new Image(fis));
                img.setFitHeight(180);
                img.setFitWidth(150);
                changed = true; 
        } catch (Exception ex) {
                System.out.println(ex.getMessage());
                label_out.setText("Setting image failed.");
            }
        }
    }
   
}
