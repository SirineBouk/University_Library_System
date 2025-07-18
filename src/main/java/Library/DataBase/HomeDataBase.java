package Library.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HomeDataBase {
    private static Connection connection ;
    private static Statement statement ;

    public static int BookCount(){
        connection = SqlConnection.getConnection();
        String sql0 = "SELECT COUNT(*) as Books_Number FROM Book ";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql0); // important!

            if (rs.next()) {
                return rs.getInt("Books_Number"); // fetch the result
            }
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
        return 0 ;
    }
    public static int StudentCount(){
        connection = SqlConnection.getConnection();
        String sql0 = "SELECT COUNT(*) as Student_Number FROM student ";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql0); // important!

            if (rs.next()) {
                return rs.getInt("Student_Number"); // fetch the result
            }
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
        return 0 ;
    }
    public static int BorrowCount(){
        connection = SqlConnection.getConnection();
        String sql0 = "SELECT COUNT(*) as Borrow_Number FROM Loan  ";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql0); // important!

            if (rs.next()) {
                return rs.getInt("Borrow_Number"); // fetch the result
            }
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
        return 0 ;
    }
    public static int NorCount(){
        connection = SqlConnection.getConnection();
        String sql0 = "SELECT COUNT(*) as NoReturn_Number FROM Loan where status = 'In Progress' ";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql0); // important!

            if (rs.next()) {
                return rs.getInt("NoReturn_Number"); // fetch the result
            }
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
        return 0 ;
    }
}
