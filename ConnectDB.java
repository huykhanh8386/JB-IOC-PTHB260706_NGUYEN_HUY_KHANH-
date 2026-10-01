import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_cinema_booking";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";
    public static Connection openConnection()
            throws SQLException {return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}