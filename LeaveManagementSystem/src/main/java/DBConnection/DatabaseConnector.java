package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnector class implements an application that
 * Illustrate to give a database connection
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 19 FEB 2024
 */

public class DatabaseConnector {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER_NAME = "SYSTEM";
    private static final String PASSWORD = "Sand1234";

    public Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return con;
    }
}
