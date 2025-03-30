package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblShowHide;
    private boolean isPasswordVisible = false;

    public Login() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 450);
        setLocationRelativeTo(null); // căn giữa

        setLayout(new GridLayout(1, 2)); // chia 2 cột

        add(initLeftPanel());
        add(initRightPanel());

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

        JLabel lblForgot = new JLabel("Quên mật khẩu");
        lblForgot.setBounds(270, 215, 120, 25);
        lblForgot.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblForgot.setForeground(Color.BLACK);
        panel.add(lblForgot);

        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBounds(70, 260, 300, 40);
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnLogin.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    public static void main(String[] args) {
        new Login();
    }
}
