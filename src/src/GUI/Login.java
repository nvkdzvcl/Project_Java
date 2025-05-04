package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import BLL.TaiKhoanBLL;
import config.JDBCUtil;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblShowHide;
    private boolean isPasswordVisible = false;

    TaiKhoanBLL taiKhoanBLL = new TaiKhoanBLL();

    public Login() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 450);
        setLocationRelativeTo(null); // căn giữa

        setLayout(new GridLayout(1, 2)); // chia 2 cột

        add(initLeftPanel());
        add(initRightPanel());

        //loadDefaultAccount();
        txtUsername.setText("admin");
        txtPassword.setText("12345678");

        setVisible(true);
    }

    private JPanel initLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/img/login-image.png")));

        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel initRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setBounds(70, 30, 300, 30);
        panel.add(lblTitle);

        JLabel lblUsername = new JLabel("Tên đăng nhập");
        lblUsername.setBounds(70, 80, 120, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(70, 105, 300, 35);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Mật khẩu");
        lblPassword.setBounds(70, 150, 120, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(70, 175, 300, 35);
        panel.add(txtPassword);

        lblShowHide = new JLabel(new ImageIcon("src/image/eye.png")); // icon mắt
        lblShowHide.setBounds(350, 180, 20, 20);
        lblShowHide.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblShowHide.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                togglePassword();
            }
        });
        panel.add(lblShowHide);

//        JLabel lblForgot = new JLabel("Quên mật khẩu");
//        lblForgot.setBounds(270, 215, 120, 25);
//        lblForgot.setFont(new Font("SansSerif", Font.ITALIC, 13));
//        lblForgot.setForeground(Color.BLACK);
//        panel.add(lblForgot);

        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBounds(70, 260, 300, 40);
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnLogin.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));


        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Vui lòng nhập đầy đủ thông tin đăng nhập");
                    return;
                }

                if(taiKhoanBLL.checkLogin(username, password)){
                    dispose();
                    new Main(username);
                }
                else {
                    JOptionPane.showMessageDialog(Login.this, "Tên đăng nhập hoặc mật khẩu không đúng!");
                }
//                try (Connection conn = JDBCUtil.startConnection()) {
//                    String sql = "SELECT * FROM TAIKHOAN WHERE TENNGUOIDUNG = ? AND MATKHAU = ? AND TRANGTHAI = 1";
//                    PreparedStatement stmt = conn.prepareStatement(sql);
//                    stmt.setString(1, username);
//                    stmt.setString(2, password);
//
//                    System.out.println("Login attempt → user: ["+ username +"], pass: ["+ password +"]");
//                    System.out.println("Prepared SQL: " + stmt);
//
//                    ResultSet rs = stmt.executeQuery();
//
//                    if (rs.next()) {
//                        dispose();
//                        new Main(username);
//                    }
//                    else {
//                        JOptionPane.showMessageDialog(Login.this, "Tên đăng nhập hoặc mật khẩu không đúng!");
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    JOptionPane.showMessageDialog(Login.this, "Lỗi khi kết nối hoặc truy vấn cơ sở dữ liệu!");
//                }
            }
        });


        panel.add(btnLogin);

        return panel;
    }

    private void togglePassword() {
        if (isPasswordVisible) {
            txtPassword.setEchoChar('•');
        } else {
            txtPassword.setEchoChar((char) 0);
        }
        isPasswordVisible = !isPasswordVisible;
    }

//    private void loadDefaultAccount() {
//        String sql = """
//                SELECT TENNGUOIDUNG, MATKHAU
//                FROM TAIKHOAN
//                WHERE TRANGTHAI = 1
//                ORDER BY TENNGUOIDUNG LIMIT 1
//                """;
//        try (Connection conn = JDBCUtil.startConnection();
//            PreparedStatement prst = conn.prepareStatement(sql);
//            ResultSet rs = prst.executeQuery()) {
//            if (rs.next()) {
//                txtUsername.setText(rs.getString("TENNGUOIDUNG"));
//                txtPassword.setText(rs.getString("MATKHAU"));
//            }
//        } catch (SQLException sqlException) {
//            System.out.println("Không thể load tài khoản mặc định: " + sqlException.getMessage());
//        }
//    }

    public static void main(String[] args) {
        new Login();
    }
}
