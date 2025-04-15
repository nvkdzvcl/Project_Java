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

    public static void main(String[] args) {
        Connection conn = startConnection();

        if (conn != null) {
            JOptionPane.showMessageDialog(null, "Kết nối database thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            closeConnection(conn);
        } else {
            JOptionPane.showMessageDialog(null, "Kết nối database thất bại!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }

}
