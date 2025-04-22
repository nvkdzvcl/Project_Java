package GUI.DIALOG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class SuaTaiKhoanDialog extends JDialog {
    private JTextField txtTenTK;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cbChucVu, cbTrangThai;
    private JButton btnSua, btnHuy;
    public SuaTaiKhoanDialog(Frame owner) {
        super(owner);
        setTitle("Sửa Tài Khoản");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        //Tittle
        JLabel lbTittle = new JLabel("SỬA TÀI KHOẢN", SwingConstants.CENTER);
        lbTittle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTittle.setBounds(0, 10, 400, 60);
        lbTittle.setOpaque(true);
        lbTittle.setBackground(new Color(30,129,206));
        lbTittle.setForeground(Color.WHITE);
        add(lbTittle);

        //Các thuộc tính
        JLabel lbTenTK = new JLabel("Tên Tài Khoản");
        lbTenTK.setBounds(50, 80, 250, 25);
        add(lbTenTK);
        txtTenTK = new JTextField();
        txtTenTK.setBounds(50,110, 250, 25);
        add(txtTenTK);

        JLabel lbMatKhau = new JLabel("Mật Khẩu");
        lbMatKhau.setBounds(50, 150, 250, 25);
        add(lbMatKhau);
        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(50,180, 250, 25);
        add(txtMatKhau);

        JLabel lbChucVu = new JLabel("Chức Vụ");
        lbChucVu.setBounds(50, 220, 250, 25);
        add(lbChucVu);
        cbChucVu = new JComboBox<>(new String[] {"Quản lý","Nhân viên"});
        cbChucVu.setBounds(50,250, 250, 25);
        add(cbChucVu);

        JLabel lbTrangThai = new JLabel("Trạng Thái");
        lbTrangThai.setBounds(50, 290, 250, 25);
        add(lbTrangThai);
        cbTrangThai = new JComboBox<>(new String[] {"Hoạt động", "Ngừng hoạt động"});
        cbTrangThai.setBounds(50,320, 250, 25);
        add(cbTrangThai);

        //Nút thêm, hủy
        btnSua = new JButton("Sửa Thông Tin TK");
        btnSua.setBounds(50, 400, 150, 40);
        btnSua.setBackground(new Color(56,168,223));
        add(btnSua);
        btnSua.addActionListener(e -> {
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
        btnHuy.setBounds(200, 400, 150, 40);
        btnHuy.setBackground(new Color(216,92,99));
        add(btnHuy);
        btnHuy.addActionListener(e -> {
            dispose();
        });
    }
}
