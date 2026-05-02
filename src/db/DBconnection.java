package src.db;

import java.sql.*;

public class DBconnection {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/HOSPITAL_DB";
        String user = "root";
        String password = "Subhan@9704"; // your password

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to Database!");
            Statement stmt = con.createStatement();
            String query ="INSERT INTO PATIENT VALUES (102,'ANY 2','FEVER')";
            stmt.executeUpdate(query);
            ResultSet rs = stmt.executeQuery("SELECT * FROM PATIENT");

            while (rs.next()) {
                System.out.print(rs.getInt("ID"));
                System.out.print(" ");
                System.out.print(rs.getString("name"));
                System.out.print(" ");
                System.out.println(rs.getString("DISEASE"));
            }            

        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }
}