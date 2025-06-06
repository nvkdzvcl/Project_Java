package GUI.DIALOG;

import BLL.NhanVienBLL;
import BLL.TaiKhoanBLL;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import GUI.PANEL.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class SuaTaiKhoanDialog extends JDialog {
    private JTextField txtTenTK;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cbChucVu, cbTrangThai, cbMaNV;
    private JButton btnSua, btnHuy;

    TaiKhoanBLL taiKhoanBLL = new TaiKhoanBLL();
    NhanVienBLL nhanVienBLL = new NhanVienBLL(1);

    public SuaTaiKhoanDialog(/*Frame owner*/TaiKhoanDTO taiKhoanDTO, TaiKhoan taiKhoanPanel, int index) {
        //super(owner);
        new TaiKhoan();
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
        lbTenTK.setBounds(70, 100, 250, 25);
        add(lbTenTK);
        txtTenTK = new JTextField();
        txtTenTK.setBounds(70,130, 250, 25);
        txtTenTK.setText(taiKhoanDTO.getTenNguoiDung());
        txtTenTK.setEditable(false);
        add(txtTenTK);

        JLabel lbMatKhau = new JLabel("Mật Khẩu");
        lbMatKhau.setBounds(70, 170, 250, 25);
        add(lbMatKhau);
        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(70,200, 250, 25);
        txtMatKhau.setText(taiKhoanBLL.getMatKhauByTenNguoiDung(txtTenTK.getText()));
        add(txtMatKhau);

        JLabel lbChucVu = new JLabel("Chức Vụ");
        lbChucVu.setBounds(70, 240, 250, 25);
        add(lbChucVu);
        cbChucVu = new JComboBox<>(new String[] {"Quản lý","Nhân viên"});
        cbChucVu.setBounds(70,270, 250, 25);
        cbChucVu.setSelectedItem(taiKhoanDTO.getChucVu());
        add(cbChucVu);

        JLabel lbTrangThai = new JLabel("Trạng Thái");
        lbTrangThai.setBounds(70, 310, 250, 25);
        add(lbTrangThai);
        cbTrangThai = new JComboBox<>(new String[] {"Hoạt động", "Ngừng hoạt động"});
        cbTrangThai.setBounds(70,340, 250, 25);
        if(taiKhoanDTO.getTrangThai() == 1){
            cbTrangThai.setSelectedItem("Hoạt động");
        }
        else{
            cbTrangThai.setSelectedItem("Ngừng hoạt động");
        }
        add(cbTrangThai);

        JLabel lbMaNv = new JLabel("Tên Nhân Viên");
        lbMaNv.setBounds(70, 380, 250, 25);
        add(lbMaNv);
        String[] danhSachNhanVien = new String[0];
        int i = 0;
        for(NhanVienDTO nhanVienDTO : nhanVienBLL.getlistnv()){
            danhSachNhanVien = Arrays.copyOf(danhSachNhanVien, danhSachNhanVien.length + 1);
            danhSachNhanVien[i] = nhanVienDTO.getHoTen();
            i++;
        };
        cbMaNV = new JComboBox<>(danhSachNhanVien);
        cbMaNV.setBounds(70,410, 250, 25);
        for(int j = 0; j < danhSachNhanVien.length; j++){
            if(taiKhoanDTO.getMaNV() == j + 1){
                cbMaNV.setSelectedItem(danhSachNhanVien[j]);
            }
        }
        add(cbMaNV);

        //Nút thêm, hủy
        btnSua = new JButton("Sửa Tài Khoản");
        btnSua.setBounds(50, 500, 150, 40);
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
            String tenTKRegex = "^[A-Za-z0-9]+$";
            if (!tenTK.matches(tenTKRegex)) {
                JOptionPane.showMessageDialog(this,"Tên tài khoản không hợp lệ!","Lỗi", JOptionPane.ERROR_MESSAGE);
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
            int trangThai = (cbTrangThai.getSelectedItem()).toString().equals("Hoạt động") ? 1 : 0;
            TaiKhoanDTO taiKhoanMoi = new TaiKhoanDTO(tenTK, matKhauStr, (String)cbChucVu.getSelectedItem(), trangThai, (cbMaNV.getSelectedIndex() + 1));
            if(taiKhoanBLL.updateAccount(index, taiKhoanMoi)){
                JOptionPane.showMessageDialog(this, "Sửa tài khoản thành công");
                taiKhoanPanel.loadDataToTable(taiKhoanBLL.getListTaiKhoan());
            }

            dispose();
        });

        btnHuy = new JButton("Hủy");
        btnHuy.setBounds(200, 500, 150, 40);
        btnHuy.setBackground(new Color(216,92,99));
        add(btnHuy);
        btnHuy.addActionListener(e -> {
            dispose();
        });
    }
}
