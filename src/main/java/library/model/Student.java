package library.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty Fname;
    private final SimpleStringProperty Lname;
    private final SimpleStringProperty Email;
    private final SimpleIntegerProperty Phone;

    public Student(String ID, String Fname, String Lname, String Email, int Phone) {
        this.ID = new SimpleStringProperty(ID);
        this.Fname = new SimpleStringProperty(Fname);
        this.Lname = new SimpleStringProperty(Lname);
        this.Email = new SimpleStringProperty(Email);
        this.Phone = new SimpleIntegerProperty(Phone);
    }

    public String getID() {
        return ID.get();
    }

    public String getFname() {
        return Fname.get();
    }

    public String getLname() {
        return Lname.get();
    }

    public String getEmail() {
        return Email.get();
    }

    public int getPhone() {
        return Phone.get();
    }

    @Override
    public String toString() {
        return ID.get(); }

}
