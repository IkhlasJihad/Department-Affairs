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
public class Instructor {
    String inst_id;
    String inst_name;
    double salary;
    Image inst_pic;
    String dept_name;
    public Instructor(String inst_id, String inst_name, String dept_name, double salary) {
        this.inst_id = inst_id;
        this.inst_name = inst_name;
        this.salary = salary;
        this.dept_name = dept_name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setInst_id(String inst_id) {
        this.inst_id = inst_id;
    }

    public void setInst_name(String inst_name) {
        this.inst_name = inst_name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setInst_pic(Image inst_pic) {
        this.inst_pic = inst_pic;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public Image getInst_pic() {
        return inst_pic;
    }

    public String getInst_id() {
        return inst_id;
    }

    public String getInst_name() {
        return inst_name;
    }

    public double getSalary() {
        return salary;
    }
    
}
