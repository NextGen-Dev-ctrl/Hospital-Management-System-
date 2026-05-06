package src.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {

    public static Connection getConnection() {

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital_db",
                "root",
                "Subhan@9704"   // My sql password
            );

            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}