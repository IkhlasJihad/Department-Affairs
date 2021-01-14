/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.scene.image.Image;

/**
 *
 * @author Ikhlas Jihad
 */
public class Student {
    String std_id;
    String std_name;
    int tot_cred;
    Image std_pic;
    String dept;

    public String getDept() {
        return dept;
    }

    public Student(String std_id, String std_name, String dept, int tot_cred) {
        this.std_id = std_id;
        this.std_name = std_name;
        this.tot_cred = tot_cred;
        this.dept = dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getStd_id() {
        return std_id;
    }

    public void setStd_id(String std_id) {
        this.std_id = std_id;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public void setTot_cred(int tot_cred) {
        this.tot_cred = tot_cred;
    }

    public void setStd_pic(Image std_pic) {
        this.std_pic = std_pic;
    }
    

    public String getStd_name() {
        return std_name;
    }


    public int getTot_cred() {
        return tot_cred;
    }

    public Image getStd_pic() {
        return std_pic;
    }
    
    
}
