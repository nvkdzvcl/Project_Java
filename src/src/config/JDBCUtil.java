package config;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {

    public static Connection startConnection(){
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/quanlycuahangbanquanao";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to DB: " + conn.getCatalog());
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể kết nối với Database","Lỗi",JOptionPane.ERROR_MESSAGE);
        }

        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
