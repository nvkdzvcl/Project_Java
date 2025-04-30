package GUI.DIALOG;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChiTietHoaDonDialog extends JDialog {
    private JTextField txtMaHD, txtTenNV_BanHang, txtTenKH, txtThoiGian, txtTongTien;
    private JComboBox<String> cbTrangThai;
    private JTable tblChiTietHD;

    public ChiTietHoaDonDialog(Frame owner) {
        super(owner,"Chi Tiết Hóa Đơn",true);
        setSize(980,500);
        setTitle("Chi Tiết Hóa Đơn");
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbTittle = new JLabel("CHI TIẾT HÓA ĐƠN", SwingConstants.CENTER);
        lbTittle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTittle.setBounds(0,10,970,60);
        lbTittle.setOpaque(true);
        lbTittle.setBackground(new Color(30,129,206));
        lbTittle.setForeground(Color.WHITE);
        add(lbTittle);

        //các textfield
        //Mã HĐ
        JLabel lbMaHD = new JLabel("Mã HĐ");
        lbMaHD.setBounds(10, 80, 150, 25);
        add(lbMaHD);
        txtMaHD = new JTextField();
        txtMaHD.setBounds(10, 110, 150, 25);
        add(txtMaHD);
        //tên nhân viên bán hàng
        JLabel lbTenNV_BanHang = new JLabel("Tên NV Bán Hàng");
        lbTenNV_BanHang.setBounds(170, 80, 150, 25);
        add(lbTenNV_BanHang);
        txtTenNV_BanHang = new JTextField();
        txtTenNV_BanHang.setBounds(170, 110, 150, 25);
        add(txtTenNV_BanHang);
        //tên khách hàng
        JLabel lbTenKH = new JLabel("Khách hàng");
        lbTenKH.setBounds(330, 80, 150, 25);
        add(lbTenKH);
        txtTenKH = new JTextField();
        txtTenKH.setBounds(330, 110, 150, 25);
        add(txtTenKH);
        //thời gian tạo
        JLabel lbThoiGian = new JLabel("Thời gian tạo");
        lbThoiGian.setBounds(490, 80, 150, 25);
        add(lbThoiGian);
        txtThoiGian = new JTextField();
        txtThoiGian.setBounds(490, 110, 150, 25);
        add(txtThoiGian);
        //trạng thái
        JLabel lbTrangThai = new JLabel("Trạng thái");
        lbTrangThai.setBounds(650, 80, 150, 25);
        add(lbTrangThai);
        cbTrangThai = new JComboBox<>();
        cbTrangThai.setBounds(650, 110, 150, 25);
        add(cbTrangThai);
        //tổng tiền
        JLabel lbTongTien = new JLabel("Tổng tiền");
        lbTongTien.setBounds(810, 80, 150, 25);
        add(lbTongTien);
        txtTongTien = new JTextField();
        txtTongTien.setBounds(810, 110, 150, 25);
        add(txtTongTien);


        tblChiTietHD = new JTable(new DefaultTableModel(new Object[] {"STT","Mã SP","Màu sắc","Kích thước","Thương hiệu","Xuất xứ","Giá bán","Số lượng"},0));
        JScrollPane scrtblChiTietPN = new JScrollPane(tblChiTietHD);
        scrtblChiTietPN.setBounds(10,150,950,300);
        add(scrtblChiTietPN);
    }
}
