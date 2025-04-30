package GUI.DIALOG;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChiTietPhieuNhapDialog extends JDialog {
    private JTextField txtMaPN, txtTenNV_Nhap, txtThoiGian, txtTongTien;
    private JTable tblChiTietPN;

    public ChiTietPhieuNhapDialog(Frame owner) {
        super(owner,"Chi Tiet Phieu Nhap",true);
        setSize(900,500);
        setTitle("Chi Tiết Phiếu Nhập");
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbTittle = new JLabel("CHI TIẾT PHIẾU NHẬP", SwingConstants.CENTER);
        lbTittle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTittle.setBounds(0,10,900,60);
        lbTittle.setOpaque(true);
        lbTittle.setBackground(new Color(30,129,206));
        lbTittle.setForeground(Color.WHITE);
        add(lbTittle);

        //các textfield
        JLabel lbMaPN = new JLabel("Mã PN");
        lbMaPN.setBounds(10,80,120,25);
        add(lbMaPN);
        txtMaPN = new JTextField();
        txtMaPN.setBounds(10,110,210,25);
        add(txtMaPN);

        JLabel lbTenNV_Nhap = new JLabel("Tên NV nhập");
        lbTenNV_Nhap.setBounds(230,80,120,25);
        add(lbTenNV_Nhap);
        txtTenNV_Nhap = new JTextField();
        txtTenNV_Nhap.setBounds(230,110,210,25);
        add(txtTenNV_Nhap);

        JLabel lbThoiGian = new JLabel("Thời gian tạo");
        lbThoiGian.setBounds(450,80,120,25);
        add(lbThoiGian);
        txtThoiGian = new JTextField();
        txtThoiGian.setBounds(450,110,210,25);
        add(txtThoiGian);

        JLabel lbTongTien = new JLabel("Tổng tiền");
        lbTongTien.setBounds(670,80,120,25);
        add(lbTongTien);
        txtTongTien = new JTextField();
        txtTongTien.setBounds(670,110,210,25);
        add(txtTongTien);

        tblChiTietPN = new JTable(new DefaultTableModel(new Object[] {"STT","Mã SP","Màu sắc","Kích thước","Thương hiệu","Xuất xứ","Giá nhập","Số lượng"},0));
        JScrollPane scrtblChiTietPN = new JScrollPane(tblChiTietPN);
        scrtblChiTietPN.setBounds(10,150,865,300);
        add(scrtblChiTietPN);

    }

}
