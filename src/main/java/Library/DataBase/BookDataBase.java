package Library.DataBase;

import Library.Manager.Books;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDataBase {
    private static Connection connection ;
    private static Statement statement ;


    public static void AddBook(int CBook , String title, String auth, String cate, int anne, int quan){
        connection = SqlConnection.getConnection();
        String sql2 = "INSERT INTO Book VALUES ("+CBook+", '"+title+"', '"+auth+"', '"+cate+"', "+anne+", "+quan+" )" ;



        try {
            statement = connection.createStatement();
            statement.execute(sql2);
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
    }

    public static ObservableList<Books> loadBook() {
        connection = SqlConnection.getConnection();
        ObservableList<Books> books = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Book";

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                books.add(new Books(
                        res.getInt("BOOK_ID"),
                        res.getString("TITLE"),
                        res.getString("AUTHOR"),
                        res.getString("CATEGORY"),
                        res.getInt("PUBLICATION_YEAR"),
                        res.getInt("STOCK")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public static void EditBook(int bookId, String title, String author, String category, Integer year, Integer quantity) {
        connection = SqlConnection.getConnection();

        StringBuilder sql = new StringBuilder("UPDATE Book SET ");
        List<Object> params = new ArrayList<>();

        if (title != null) {
            sql.append("TITLE = ?, ");
            params.add(title);
        }
        if (author != null) {
            sql.append("AUTHOR = ?, ");
            params.add(author);
        }
        if (category != null) {
            sql.append("CATEGORY = ?, ");
            params.add(category);
        }
        if (year != null) {
            sql.append("PUBLICATION_YEAR = ?, ");
            params.add(year);
        }
        if (quantity != null) {
            sql.append("STOCK = ?, ");
            params.add(quantity);
        }

        // Remove last comma
        if (params.isEmpty()) return; // Nothing to update
        sql.setLength(sql.length() - 2); // remove trailing comma + space

        sql.append(" WHERE BOOK_ID = ?");
        params.add(bookId);

        try {
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void DeleteBook(int CBook){
        connection = SqlConnection.getConnection();
        String sql3 ="DELETE FROM Loan WHERE book_id ="+CBook+" ";
        String sql4 = "Delete from Book where BOOK_ID = "+CBook+" " ;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql3);
            statement.executeUpdate(sql4);
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
    }

}

