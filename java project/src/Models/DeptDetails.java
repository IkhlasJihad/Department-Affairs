
package Models;

/**
 *
 * @author Ikhlas Jihad
 */
public class DeptDetails {
    public String dept;
    public String building;
    public double budget;
    public int stdCounts;
    public int instructors;
    public double avgSalary;

    public DeptDetails(String dept, String building, double budget, int stdCounts, int instructors, double avgSalary) {
        this.dept = dept;
        this.building = building;
        this.budget = budget;
        this.stdCounts = stdCounts;
        this.instructors = instructors;
        this.avgSalary = avgSalary;
    }
    
    @Override
    public String toString(){    
        return ("dept: " + dept + "\n" + 
               "building: " + building + "\n" +
               "budget: " + budget + "\n" +
               "stdCounts: " + stdCounts + "\n" +
               "instructors: " + instructors + "\n" +
                "avgSalary: " + avgSalary + "\n" );
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setStdCounts(int stdCounts) {
        this.stdCounts = stdCounts;
    }

    public void setInstructors(int instructors) {
        this.instructors = instructors;
    }

    public void setAvgSalary(double avgSalary) {
        this.avgSalary = avgSalary;
    }

    public String getDept() {
        return dept;
    }

    public String getBuilding() {
        return building;
    }

    public double getBudget() {
        return budget;
    }

    public int getStdCounts() {
        return stdCounts;
    }

    public int getInstructors() {
        return instructors;
    }

    public double getAvgSalary() {
        return avgSalary;
    }
    
    
    
}
