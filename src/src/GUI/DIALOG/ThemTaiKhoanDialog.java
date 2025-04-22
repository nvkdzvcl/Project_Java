package GUI.DIALOG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class ThemTaiKhoanDialog extends JDialog {
    private JTextField txtTenTK;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cbChucVu, cbTrangThai;
    private JButton btnThem, btnHuy;
    public ThemTaiKhoanDialog(Frame owner) {
        super(owner);
        setTitle("Thêm Tài Khoản");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        //Tittle
        JLabel lbTittle = new JLabel("THÊM TÀI KHOẢN", SwingConstants.CENTER);
        lbTittle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTittle.setBounds(0, 10, 400, 60);
        lbTittle.setOpaque(true);
        lbTittle.setBackground(new Color(30,129,206));
        lbTittle.setForeground(Color.WHITE);
        add(lbTittle);

        //Các thuộc tính
        JLabel lbTenTK = new JLabel("Tên Tài Khoản");
        lbTenTK.setBounds(70, 100, 250, 25);
        add(lbTenTK);
        txtTenTK = new JTextField();
        txtTenTK.setBounds(70,130, 250, 25);
        add(txtTenTK);

        JLabel lbMatKhau = new JLabel("Mật Khẩu");
        lbMatKhau.setBounds(70, 170, 250, 25);
        add(lbMatKhau);
        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(70,210, 250, 25);
        add(txtMatKhau);

        JLabel lbChucVu = new JLabel("Chức Vụ");
        lbChucVu.setBounds(70, 250, 250, 25);
        add(lbChucVu);
        cbChucVu = new JComboBox<>(new String[] {"Quản lý","Nhân viên"});
        cbChucVu.setBounds(70,280, 250, 25);
        add(cbChucVu);

        JLabel lbTrangThai = new JLabel("Trạng Thái");
        lbTrangThai.setBounds(70, 320, 250, 25);
        add(lbTrangThai);
        cbTrangThai = new JComboBox<>(new String[] {"Hoạt động", "Ngừng hoạt động"});
        cbTrangThai.setBounds(70,350, 250, 25);
        add(cbTrangThai);

        //Nút thêm, hủy
        btnThem = new JButton("Thêm Tài Khoản");
        btnThem.setBounds(50, 450, 150, 40);
        btnThem.setBackground(new Color(56,168,223));
        add(btnThem);
        btnThem.addActionListener(e -> {
            String tenTK = txtTenTK.getText().trim();
            char[] matKhau = txtMatKhau.getPassword();
            String matKhauStr = new String(matKhau);
            if (tenTK.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập tên tài khoản!","Lỗi", JOptionPane.ERROR_MESSAGE);
                txtTenTK.requestFocusInWindow();
                Arrays.fill(matKhau,'\0');
                return;
            }
            if (matKhauStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập mật khẩu!","Lỗi", JOptionPane.ERROR_MESSAGE);
                txtMatKhau.requestFocusInWindow();
                Arrays.fill(matKhau,'\0');
                return;
            }
            if (matKhauStr.length() < 8) {
                JOptionPane.showMessageDialog(this,"Mật khẩu tối thiểu 8 ký tự!","Lỗi", JOptionPane.ERROR_MESSAGE);
                txtMatKhau.requestFocusInWindow();
                Arrays.fill(matKhau,'\0');
                return;
            }
            Arrays.fill(matKhau,'\0');
            dispose();
        });

        btnHuy = new JButton("Hủy");
        btnHuy.setBounds(200, 450, 150, 40);
        btnHuy.setBackground(new Color(216,92,99));
        add(btnHuy);
        btnHuy.addActionListener(e -> {
           dispose();
        });
    }
}
