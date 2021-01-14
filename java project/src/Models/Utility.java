/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 *
 * @author Ikhlas Jihad
 */
public class Utility {
    public static final DBModel DB = new DBModel();
    
    
    public String browseImg(){
        try{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("png image","'.png"));
            File f = fc.showOpenDialog(null);
            return (f.getAbsolutePath());
        }
        catch(Exception ex){
            return null;
        }  
    }
    
    public void playToolTip(Node n) {
        Tooltip t = new Tooltip("Click to insert/update the image");
        Tooltip.install(n, t);
    }
    
    public boolean handleImage(String path, ImageView img) throws FileNotFoundException{
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        img.setImage(new Image(fis));
        img.setFitHeight(180);
        img.setFitWidth(150);
        return true;
    }
    
   
    
}
