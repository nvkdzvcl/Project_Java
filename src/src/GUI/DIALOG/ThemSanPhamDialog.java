package GUI.DIALOG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThemSanPhamDialog extends JDialog {
    private JTextField txtTenSP, txtThuongHieu, txtXuatXu, txtSoLuong;
    private JComboBox<String> cbMauSac, cbKichThuoc, cbTrangThai;
    private JLabel lbImage;
    private JButton btnThem, btnHuy;

    public ThemSanPhamDialog(Frame owner) {
        super(owner,"Thêm Sản Phẩm",true);
        setSize(900,500);
        setTitle("Thêm Sản Phẩm");
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbTittle = new JLabel("THÊM SẢN PHẨM", SwingConstants.CENTER);
        lbTittle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTittle.setBounds(0, 10, 900, 60);
        lbTittle.setOpaque(true);
        lbTittle.setBackground(new Color(30,129,206));
        lbTittle.setForeground(Color.WHITE);
        add(lbTittle);


        //Cột 1
        JLabel lbTenSP = new JLabel("Tên SP:");
        lbTenSP.setBounds(50,80,200,25);
        add(lbTenSP);
        txtTenSP = new JTextField();
        txtTenSP.setBounds(50,110,200,25);
        add(txtTenSP);

        JLabel lbThuongHieu = new JLabel("Thương Hiệu:");
        lbThuongHieu.setBounds(50,150,200,25);
        add(lbThuongHieu);
        txtThuongHieu = new JTextField();
        txtThuongHieu.setBounds(50,180,200,25);
        add(txtThuongHieu);

        JLabel lbXuatXu = new JLabel("Xuất Xứ:");
        lbXuatXu.setBounds(50,220,200,25);
        add(lbXuatXu);
        txtXuatXu = new JTextField();
        txtXuatXu.setBounds(50,250,200,25);
        add(txtXuatXu);


        //Cột 2
        JLabel lbMauSac = new JLabel("Màu Sắc:");
        lbMauSac.setBounds(300,80,200,25);
        add(lbMauSac);
        cbMauSac = new JComboBox<>(new String[] {"Đỏ","Xanh","Vàng","Trắng"});
        cbMauSac.setBounds(300,110,200,25);
        add(cbMauSac);

        JLabel lbKichThuoc = new JLabel("Kích thước:");
        lbKichThuoc.setBounds(300,150,200,25);
        add(lbKichThuoc);
        cbKichThuoc = new JComboBox<>(new String[] {"S","M","L","XL","2XL","3XL"});
        cbKichThuoc.setBounds(300,180,200,25);
        add(cbKichThuoc);

        JLabel lbSoLuong = new JLabel("Số lượng:");
        lbSoLuong.setBounds(300,220,200,25);
        add(lbSoLuong);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(300,250,200,25);
        add(txtSoLuong);


        //Cột 3
        JLabel lbTrangThai = new JLabel("Trạng thái:");
        lbTrangThai.setBounds(550,80,200,25);
        add(lbTrangThai);
        cbTrangThai = new JComboBox<>(new String[] {"Bình thường","Khóa"});
        cbTrangThai.setBounds(550,110,200,25);
        add(cbTrangThai);

        JLabel lbHinhAnhSP = new JLabel("Hình ảnh:");
        lbHinhAnhSP.setBounds(550,150,200,25);
        add(lbHinhAnhSP);


        //Nút thêm, hủy
        btnThem = new JButton("Thêm Sản Phẩm");
        btnThem.setBounds(250,400,200,40);
        btnThem.setBackground(new Color(56,168,223));
        add(btnThem);

        btnHuy = new JButton(("Hủy bỏ"));
        btnHuy.setBounds(450,400,200,40);
        btnHuy.setBackground(new Color(216,92,99));
        add(btnHuy);
        btnHuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
