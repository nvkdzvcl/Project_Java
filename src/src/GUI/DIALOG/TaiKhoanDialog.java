package GUI.DIALOG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TaiKhoanDialog extends JFrame {
    public TaiKhoanDialog() {
        setSize(568,750);
        setTitle("Thêm Tài Khoản");
        setLayout(new GridLayout(0,1,10,10));

        JLabel lbThemTaiKhoan = new JLabel("THÊM TÀI KHOẢN");
        lbThemTaiKhoan.setPreferredSize(new Dimension(200,50));
        lbThemTaiKhoan.setHorizontalAlignment(JLabel.CENTER);
        Font font = new Font("Arial", Font.BOLD, 25);
        lbThemTaiKhoan.setFont(font);
        lbThemTaiKhoan.setForeground(Color.WHITE);

        JLabel TenDangNhap = new JLabel("Tên Đăng Nhập");
        JLabel ChucVu = new JLabel("Chức Vụ");
        JLabel TrangThai = new JLabel("Trạng Thái");
        JTextField tfTenDangNhap = new JTextField(47);
        String [] chucVuOptions = {"Quản lý", "Nhân viên"};
        JComboBox<String> cbChucVu = new JComboBox<>(chucVuOptions);
        String [] trangThaiOptions = {"Hoạt động", "Ngưng hoạt động"};
        JComboBox<String> cbTrangThai = new JComboBox<>(trangThaiOptions);
        tfTenDangNhap.setPreferredSize(new Dimension(200,50));
        cbChucVu.setPreferredSize(new Dimension(200,50));
        cbTrangThai.setPreferredSize(new Dimension(200,50));

        JButton btnThem = new JButton("Thêm Tài Khoản");
        btnThem.setPreferredSize(new Dimension(200,38));
        Color themTaiKhoanColor = new Color(56,168,223);
        btnThem.setBackground(themTaiKhoanColor);
        btnThem.setForeground(Color.WHITE);

        JButton btnHuy = new JButton("Hủy bỏ");
        Color huyBoColor = new Color(216,92,99);
        btnHuy.setPreferredSize(new Dimension(200,38));
        btnHuy.setBackground(huyBoColor);
        btnHuy.setForeground(Color.WHITE);

        JPanel P = new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));
        JPanel P1 = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel P2 = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel P3 = new JPanel(new FlowLayout(FlowLayout.CENTER,5,0));
        JPanel P4 = new JPanel(new BorderLayout());
        JPanel P5 = new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));

        Color customBlue = new Color(30,129,206);
        P4.setBackground(customBlue);
        P4.setForeground(Color.WHITE);

        P.add(TenDangNhap);
        P.add(tfTenDangNhap);
        P1.add(ChucVu);
        P1.add(cbChucVu);
        P2.add(TrangThai);
        P2.add(cbTrangThai);
        P4.add(lbThemTaiKhoan, BorderLayout.CENTER);
        P3.add(btnThem);
        P3.add(btnHuy);

        add(P4);
        add(P);
        add(P1);
        add(P2);
        add(P3);

        setLocationRelativeTo(null);

        btnHuy.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               dispose();
           }
        });

        setVisible(true);
    }
}
