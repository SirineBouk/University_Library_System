package library;

import library.database.*;
import library.model.Loan;
import library.model.Books;
import library.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // Home Page
    @FXML
    private Button HomeBtn, BookBtn, StudentBtn, LoanBtn;

    @FXML
    private AnchorPane HomePg, BookPg, StudentPg, LoanPg;

    @FXML
    private Label BookCount, StudentCount, BorrowCount, UnreturnedCount;

    // Students Page
    @FXML
    private Button AddStd, EditStd, ClearStd, DeleteStd;

    @FXML
    private TextField StudentID, FName, LName, Email, PhoneNbr, SearchStd;

    @FXML
    private TableView<Student> StudentTab;

    @FXML
    private TableColumn<Student, String> IDCln, FNameCln, LNameCln, EmailCln;

    @FXML
    private TableColumn<Student , Integer > PhoneCln;

    // Books Page
    @FXML
    private TextField BookID, Title, Author, Category, PYear, Quantity, SearchBook;

    @FXML
    private Button AddBook, EditBook, ClearBook, DeleteBook;

    @FXML
    private TableView<Books> BookTab;

    @FXML
    private TableColumn<Books, String> TitleCln, AuthorCln, CategoryCln;

    @FXML
    private TableColumn<Books, Integer > BookIDCln, PYearCln, QuantityCln;

    // Loan Page
    @FXML
    private ComboBox<Student> StudentCmb;

    @FXML
    private ComboBox<Books> BookCmb;

    @FXML
    private Button BorrowBtn, ReturnBtn, ClearBtn, DeleteLBtn;


    @FXML
    private TextField borrowid,SearchLoan;

    @FXML
    private TableView<Loan> LoanTab;

    @FXML
    private TableColumn<Loan, String> cols,coldd,colrd,colbd,colst;

    @FXML
    private TableColumn<Loan, Integer> colb,colbk;

    private final ObservableList<Student> StudentList = FXCollections.observableArrayList();

    private final ObservableList<Books> BooktList = FXCollections.observableArrayList();

    private final ObservableList<Loan> LoanList = FXCollections.observableArrayList();

    public void SwitchPg(ActionEvent event) {
        if (event.getSource() == HomeBtn) {
            HomePg.setVisible(true);
            StudentPg.setVisible(false);
            BookPg.setVisible(false);
            LoanPg.setVisible(false);

            LoanBtn.setStyle("-fx-background-color:transparent");
            StudentBtn.setStyle("-fx-background-color:transparent");
            BookBtn.setStyle("-fx-background-color:transparent");
            HomeBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #b3a1d1, #778ca3);");

        } else if (event.getSource() == StudentBtn) {
            HomePg.setVisible(false);
            StudentPg.setVisible(true);
            BookPg.setVisible(false);
            LoanPg.setVisible(false);
            //StudentPage(event);

            LoanBtn.setStyle("-fx-background-color:transparent");
            StudentBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #b3a1d1, #778ca3);");
            BookBtn.setStyle("-fx-background-color:transparent");
            HomeBtn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == BookBtn) {
            HomePg.setVisible(false);
            StudentPg.setVisible(false);
            BookPg.setVisible(true);
            LoanPg.setVisible(false);

            BookBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #b3a1d1, #778ca3);");
            StudentBtn.setStyle("-fx-background-color:transparent");
            HomeBtn.setStyle("-fx-background-color:transparent");
            LoanBtn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == LoanBtn) {
            HomePg.setVisible(false);
            StudentPg.setVisible(false);
            BookPg.setVisible(false);
            LoanPg.setVisible(true);

            LoanBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #b3a1d1, #778ca3);");
            StudentBtn.setStyle("-fx-background-color:transparent");
            BookBtn.setStyle("-fx-background-color:transparent");
            HomeBtn.setStyle("-fx-background-color:transparent");
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        SqlConnection.connect();
        initializeStudent();
        initializeBook() ;
        initializeLoan() ;
        HomePage();
    }

    public  void initializeStudent(){
        //for display the column
        IDCln.setCellValueFactory(new PropertyValueFactory<>("ID"));
        FNameCln.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        LNameCln.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        EmailCln.setCellValueFactory(new PropertyValueFactory<>("Email"));
        PhoneCln.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        StudentList.setAll(StudentDataBase.loadStudents());

        //for search
        FilteredList<Student> filter = new FilteredList<>(StudentList, en -> true);

        SearchStd.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(student -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (student.getID().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (student.getFname().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (student.getLname().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(student.getPhone()).contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Student> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(StudentTab.comparatorProperty());
        StudentTab.setItems(sortList);

        // Populate ComboBox with student list
        StudentCmb.setItems(StudentList);
        StudentCmb.setCellFactory(param -> new ListCell<Student>() {
            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                if (empty || student == null) {
                    setText(null);
                } else {
                    setText(student.getID());
                }
            }
        });
        StudentCmb.setButtonCell(StudentCmb.getCellFactory().call(null));
    }

    public void StudentPage(ActionEvent e){
        /// declaration of variable
        if (e.getSource() == AddStd) {
            try {
                String StdID = StudentID.getText();
                String Fname = FName.getText();
                String Lname = LName.getText();
                String Email = this.Email.getText();
                int Phone = Integer.parseInt(PhoneNbr.getText());

                /// Add Button

                StudentDataBase.AddStudent(StdID, Fname, Lname, Email, Phone);
                //for update the table
                StudentList.setAll(StudentDataBase.loadStudents());
                HomePage();


            } catch (Exception ex) {
                showAlert("Please fill in all fields correctly for adding a student.");
            }
        }
        /// Clear Button
        else if (e.getSource() == ClearStd){
            StudentID.clear() ;
            FName.clear();
            LName.clear();
            this.Email.clear();
            PhoneNbr.clear();
        }
        /// Edit Button
        else if(e.getSource() == EditStd){
            try {
                String StdID = StudentID.getText();
                //for create a confirmation window
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
                alert.setTitle("Edit Student Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to edit this student's information?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {

                    String Fname = FName.getText().isEmpty() ? null : FName.getText();
                    String Lname = LName.getText().isEmpty() ? null : LName.getText();
                    String Email = this.Email.getText().isEmpty() ? null : this.Email.getText();
                    Integer Phone = PhoneNbr.getText().isEmpty() ? null : Integer.parseInt(PhoneNbr.getText()) ;

                    StudentDataBase.EditStudent(StdID, Fname, Lname, Email, Phone);
                    //for update the table
                    StudentList.setAll(StudentDataBase.loadStudents());
                }
            } catch (Exception ex) {
                showAlert("Please enter a valid Student ID to edit.");
            }

        }
        /// Delete Button
        else if (e.getSource() == DeleteStd) {
            try{
                String StdID = StudentID.getText();
                //for create a confirmation window
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Student Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete student with ID: " + StdID + "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    StudentDataBase.DeleteStudent(StdID);
                    //for update the table
                    StudentList.setAll(StudentDataBase.loadStudents());
                    LoanList.setAll(LoanDataBase.loadBorrow());
                    HomePage();
                }
            } catch (Exception ex) {
                showAlert("Please enter a valid Student ID to delete.");
            }
        }

    }

    public  void initializeBook(){
        //for display the column
        BookIDCln.setCellValueFactory(new PropertyValueFactory<>("CBook"));
        TitleCln.setCellValueFactory(new PropertyValueFactory<>("title"));
        AuthorCln.setCellValueFactory(new PropertyValueFactory<>("auth"));
        CategoryCln.setCellValueFactory(new PropertyValueFactory<>("cate"));
        PYearCln.setCellValueFactory(new PropertyValueFactory<>("anne"));
        QuantityCln.setCellValueFactory(new PropertyValueFactory<>("quan"));

        BooktList.setAll(BookDataBase.loadBook());

        //for search
        FilteredList<Books> filter1 = new FilteredList<>(BooktList, en -> true);

        SearchBook.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter1.setPredicate(books -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (String.valueOf(books.getCBook()).contains(searchKey)) {
                    return true;
                } else if (books.getTitle().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (books.getAuth().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (books.getCate().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(books.getAnne()).contains(searchKey)) {
                    return true;
                } else if (String.valueOf(books.getQuan()).contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Books> sortList = new SortedList<>(filter1);
        sortList.comparatorProperty().bind(BookTab.comparatorProperty());
        BookTab.setItems(sortList);

        // Populate ComboBox with Book list
        BookCmb.setItems(BooktList);
        BookCmb.setCellFactory(param -> new ListCell<Books>() {
            @Override
            protected void updateItem(Books books, boolean empty) {
                super.updateItem(books, empty);
                if (empty || books == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(books.getCBook()));
                }
            }
        });
        BookCmb.setButtonCell(BookCmb.getCellFactory().call(null));
    }

    public void BookPage(ActionEvent e) {

        /// ADD BOOK
        if (e.getSource() == AddBook) {
            try {
                int bookId = Integer.parseInt(BookID.getText());
                String title = Title.getText();
                String author = Author.getText();
                String category = Category.getText();
                int year = Integer.parseInt(PYear.getText());
                int quantity = Integer.parseInt(Quantity.getText());

                BookDataBase.AddBook(bookId, title, author, category, year, quantity);
                BooktList.setAll(BookDataBase.loadBook());
                HomePage();
            } catch (Exception ex) {
                showAlert("Please fill in all fields correctly for adding a book.");
            }
        }

        /// CLEAR BOOK
        else if (e.getSource() == ClearBook) {
            BookID.clear();
            Title.clear();
            Author.clear();
            Category.clear();
            PYear.clear();
            Quantity.clear();
        }

        /// EDIT BOOK
        else if (e.getSource() == EditBook) {
            try {
                int bookId = Integer.parseInt(BookID.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Edit Book Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to edit this Book's information?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Only use the filled fields
                    String title = Title.getText().isEmpty() ? null : Title.getText();
                    String author = Author.getText().isEmpty() ? null : Author.getText();
                    String category = Category.getText().isEmpty() ? null : Category.getText();
                    Integer year = PYear.getText().isEmpty() ? null : Integer.parseInt(PYear.getText());
                    Integer quantity = Quantity.getText().isEmpty() ? null : Integer.parseInt(Quantity.getText());

                    BookDataBase.EditBook(bookId, title, author, category, year, quantity);
                    BooktList.setAll(BookDataBase.loadBook());
                }
            } catch (Exception ex) {
                showAlert("Please enter a valid Book Code (ID) to edit.");
            }
        }

        /// DELETE BOOK
        else if (e.getSource() == DeleteBook) {
            try {
                int bookId = Integer.parseInt(BookID.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Book Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Book with ID: " + bookId + "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    BookDataBase.DeleteBook(bookId);
                    BooktList.setAll(BookDataBase.loadBook());
                    LoanList.setAll(LoanDataBase.loadBorrow());
                    HomePage();
                }
            } catch (Exception ex) {
                showAlert("Please enter a valid Book Code (ID) to delete.");
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void HomePage(){
        int Bookcount = HomeDataBase.BookCount() ;
        int StudentCount = HomeDataBase.StudentCount();
        int BorrowCount = HomeDataBase.BorrowCount();
        int NortCount = HomeDataBase.NorCount();
        BookCount.setText(String.valueOf(Bookcount));
        this.StudentCount.setText(String.valueOf((StudentCount)));
        this.BorrowCount.setText(String.valueOf((BorrowCount)));
        UnreturnedCount.setText(String.valueOf((NortCount)));
    }

    public void Borrowpage(ActionEvent e) {
        if (e.getSource() == BorrowBtn) {
            try {
                int loanId = Integer.parseInt(borrowid.getText());
                String studentId = StudentCmb.getValue().getID();
                int bookId = BookCmb.getValue().getCBook();
                LoanDataBase.AddBorrow(loanId, studentId, bookId);
                LoanList.setAll(LoanDataBase.loadBorrow());
                BooktList.setAll(BookDataBase.loadBook());
                HomePage();
            } catch (Exception ex) {
                showAlert("Please fill all fields correctly for borrowing.");
            }
        }

        else if (e.getSource() == ReturnBtn) {
            try {
                int loanId = Integer.parseInt(borrowid.getText());
                LoanDataBase.AddReturn(loanId);
                LoanList.setAll(LoanDataBase.loadBorrow());
                BooktList.setAll(BookDataBase.loadBook());
                HomePage();
            } catch (Exception ex) {
                showAlert("Please enter a valid Loan ID to return.");
            }
        }

        else if (e.getSource() == ClearBtn) {
            borrowid.clear();
            StudentCmb.setValue(null);
            BookCmb.setValue(null);

        }

        else if(e.getSource() == DeleteLBtn){
            try {
                int loanId = Integer.parseInt(borrowid.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Loan Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Loan with ID: " + loanId + "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    LoanDataBase.DeleteLoan(loanId);
                    LoanList.setAll(LoanDataBase.loadBorrow());
                    BooktList.setAll(BookDataBase.loadBook());
                    HomePage();
                }
            } catch (Exception ex) {
                showAlert("Please enter a valid Loan ID to delete.");
            }
        }
    }


    public void initializeLoan() {
        LoanDataBase.updateOverdueLoans();
        // Column bindings
        colb.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        cols.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colbk.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colbd.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        coldd.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colrd.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colst.setCellValueFactory(new PropertyValueFactory<>("status"));
        // Load data
        LoanList.setAll(LoanDataBase.loadBorrow());
        // Filtered list
        FilteredList<Loan> filteredList = new FilteredList<>(LoanList, loan -> true);

        SearchLoan.textProperty().addListener((obs, oldValue, newValue) -> {
            String search = newValue.toLowerCase();

            filteredList.setPredicate(loan -> {
                if (search == null || search.isEmpty()) return true;

                return String.valueOf(loan.getLoanId()).contains(search)
                        || loan.getStudentId().toLowerCase().contains(search)
                        || String.valueOf(loan.getBookId()).contains(search)
                        || loan.getLoanDate().toLowerCase().contains(search)
                        || loan.getDueDate().toLowerCase().contains(search)
                        || (loan.getReturnDate() != null && loan.getReturnDate().toLowerCase().contains(search))
                        || loan.getStatus().toLowerCase().contains(search);
            });
        });
        SortedList<Loan> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(LoanTab.comparatorProperty());
        LoanTab.setItems(sortedList);
    }

}




