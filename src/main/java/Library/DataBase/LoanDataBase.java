package Library.DataBase;

import Library.Manager.Loan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoanDataBase {
    private static Statement statement ;

    public static void AddBorrow(int loanId, String studentId ,int bookId  ){
        Connection connection = SqlConnection.getConnection();
        String sql5 = "INSERT INTO Loan (loan_id, student_id, book_id) VALUES (" +
                loanId + ", '" + studentId + "', " + bookId + ")";


        try {
            statement = connection.createStatement();
            statement.execute(sql5);

        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
    }

    public static ObservableList<Loan> loadBorrow() {
        Connection connection = SqlConnection.getConnection();
        ObservableList<Loan> LoanB = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Loan";

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                LoanB.add(new Loan(
                        res.getInt("loan_id"),
                        res.getString("student_id"),
                        res.getInt("book_id"),
                        res.getString("loan_date"),
                        res.getString("due_date"),
                        res.getString("return_date"),
                        res.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LoanB;
    }

    public static void AddReturn(int loanId) {
        Connection connection = SqlConnection.getConnection();
        String sql = "UPDATE Loan SET return_date = SYSDATE, status = 'Returned' WHERE loan_id = " + loanId;

        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateOverdueLoans() {
        Connection connection = SqlConnection.getConnection();
        String sql = "UPDATE Loan SET status = 'Overdue' " +
                "WHERE status = 'In Progress' " +
                "AND due_date < SYSDATE " +
                "AND return_date IS NULL";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void DeleteLoan(int LoanID){
        Connection connection = SqlConnection.getConnection();
        String sql = "DELETE FROM Loan WHERE loan_id ='"+LoanID+"'" ;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
    }


}
