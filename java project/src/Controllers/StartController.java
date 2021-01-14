package Controllers;

import Models.DeptDetails;
import Models.Navigation;
import Models.Utility;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Ikhlas Jihad
 */
public class StartController implements Initializable {
    public static String department = null;
    Navigation nav = new Navigation();
    @FXML SplitPane root;
    @FXML ComboBox deptCombo;
    @FXML TableView table;
    @FXML TableColumn<DeptDetails,String> dept;
    @FXML TableColumn<DeptDetails,String> building;
    @FXML TableColumn<DeptDetails,String> budget;
    @FXML TableColumn<DeptDetails,String> stdCounts;
    @FXML TableColumn<DeptDetails,String> instructors;
    @FXML TableColumn<DeptDetails,String> avgSalary;
    @FXML Label label_out;
    Button manage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        deptCombo.setItems(FXCollections.observableArrayList(Utility.DB.departments()));
        dept.setCellValueFactory(new PropertyValueFactory<>("Dept"));
        building.setCellValueFactory(new PropertyValueFactory<>("Building"));
        budget.setCellValueFactory(new PropertyValueFactory<>("Budget"));
        stdCounts.setCellValueFactory(new PropertyValueFactory<>("StdCounts"));
        instructors.setCellValueFactory(new PropertyValueFactory<>("Instructors"));
        avgSalary.setCellValueFactory(new PropertyValueFactory<>("AvgSalary"));
    }       

    @FXML
    private void on_details_btn(ActionEvent event) {
        if(deptCombo.getValue() != null){  
            //System.out.println(deptCombo.getValue().toString());
            department = deptCombo.getValue().toString();
            try {   
                label_out.setVisible(false);
                table.setVisible(true);
                table.setItems(FXCollections.observableArrayList(Utility.DB.deptDetails(deptCombo.getValue().toString())));
                table.setVisible(true);
                
            } catch (Exception ex) {
                Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
                label_out.setVisible(true);
                label_out.setText(ex.getMessage());
            }
        }
        else{
            label_out.setVisible(true);
            label_out.setText("Select a Department");}
    }
    
    @FXML
    public void manageDept(){
        if(deptCombo.getValue() != null ){
            label_out.setVisible(false);
            department = deptCombo.getValue().toString();
            nav.navTo(root, nav.fxmlManage);
        }
        else{
            label_out.setVisible(true);
            label_out.setText("Select a Department");
        }
    }
}
