package library.database;

import library.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDataBase {
    private static Connection connection ;
    private static Statement statement ;

    
    public static void AddStudent(String id , String Fname, String Lname, String Email, int Phone){
        connection = SqlConnection.getConnection();
        String sql0 = "INSERT INTO Student VALUES ('"+id+"', '"+Lname+"', '"+Fname+"', '"+Email+"', "+Phone+" )" ;

        try {
            statement = connection.createStatement();
            statement.execute(sql0);
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
    }

    public static ObservableList<Student> loadStudents() {
        connection = SqlConnection.getConnection();
        ObservableList<Student> students = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Student";

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                students.add(new Student(
                        res.getString("STUDENT_ID"),
                        res.getString("FIRST_NAME"),
                        res.getString("LAST_NAME"),
                        res.getString("EMAIL"),
                        res.getInt("PHONE_NUMBER")
                ));
            }
            //studcom.setItems(students);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public static void EditStudent(String StdID, String Fname, String Lname, String Email, Integer Phone){
        connection = SqlConnection.getConnection();
        StringBuilder sql = new StringBuilder("UPDATE student set ");
        List<Object> params = new ArrayList<>();

        if (Fname != null) {
            sql.append("FIRST_NAME = ?, ");
            params.add(Fname);
        }
        if (Lname != null) {
            sql.append("LAST_NAME = ?, ");
            params.add(Lname);
        }
        if (Email != null) {
            sql.append("Email = ?, ");
            params.add(Email);
        }
        if (Phone != null) {
            sql.append("PHONE_NUMBER = ?, ");
            params.add(Phone);
        }

        // Remove last comma
        if (params.isEmpty()) return; // Nothing to update
        sql.setLength(sql.length() - 2); // remove trailing comma + space

        sql.append(" WHERE STUDENT_ID = ?");
        params.add(StdID);

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

    public static void DeleteStudent(String StdID){
        connection = SqlConnection.getConnection();
        String sql2 = "Delete from student where STUDENT_ID = '"+StdID+"'" ;
        String sql1 = "DELETE FROM Loan WHERE student_id ='"+StdID+"'" ;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
    }

}


