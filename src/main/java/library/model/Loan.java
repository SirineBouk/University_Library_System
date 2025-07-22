package library.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Loan {
    private final SimpleIntegerProperty loanId;
    private final SimpleStringProperty studentId;
    private final SimpleIntegerProperty bookId;
    private final SimpleStringProperty loanDate;
    private final SimpleStringProperty dueDate;
    private final SimpleStringProperty returnDate;
    private final SimpleStringProperty status ;

    public Loan(int Num_E, String ID, int CBook, String date_e, String date_r_p, String date_r_e, String status) {
        this.loanId = new SimpleIntegerProperty(Num_E);
        this.studentId = new SimpleStringProperty(ID);
        this.bookId = new SimpleIntegerProperty(CBook);
        this.status = new SimpleStringProperty(status);
        this.loanDate = new SimpleStringProperty(date_e);
        this.dueDate = new SimpleStringProperty(date_r_p);
        this.returnDate = new SimpleStringProperty(date_r_e);

    }

    // Getters
    public int getLoanId() { return loanId.get(); }
    public String getStudentId() { return studentId.get(); }
    public int getBookId() { return bookId.get(); }
    public String getLoanDate() { return loanDate.get(); }
    public String getDueDate() { return dueDate.get(); }
    public String getReturnDate() { return returnDate.get(); }
    public String getStatus() { return status.get(); }

}
