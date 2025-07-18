package Library.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class SqlConnection {
    private static Connection connection;

    public static void connect() {
        try {
            forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ev) {
            throw new RuntimeException(ev);
        }
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","sirine");
        } catch (
                SQLException ev) {
            throw new RuntimeException(ev);
        }
    }
    public static Connection getConnection() {
        return connection;
    }
}
