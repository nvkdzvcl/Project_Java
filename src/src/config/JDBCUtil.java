package LapTrinhJava.JavaSwing.Week9;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {

    public static Connection startConnection(){
        Connection conn = null;
        try{
            //DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://localhost:3306/java";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Khong the ket noi voi database");
        }

        return conn;
    }

    public static void closeConnection(Connection conn){
        try{
            if(conn != null){
                conn.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
