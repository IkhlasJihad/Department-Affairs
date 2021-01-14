package Models;

/**
 *
 * @author Ikhlas Jihad
 */
public class Course {
    String c_id;
    String title;
    int c_cred;

    public String getC_id() {
        return c_id;
    }

    public String getTitle() {
        return title;
    }

    public int getC_cred() {
        return c_cred;
    }

    public Course(String c_id, String title, int c_cred) {
        this.c_id = c_id;
        this.title = title;
        this.c_cred = c_cred;
    }

    public void setC_cred(int c_cred) {
        this.c_cred = c_cred;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
}
