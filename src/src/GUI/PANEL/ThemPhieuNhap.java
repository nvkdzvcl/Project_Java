package GUI.PANEL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThemPhieuNhap extends JPanel {
    private JTable tblSanPham, tblChiTietNhapHang;
    private JTextField txtTimKiem, txtMaSP, txtTenSP, txtGiaNhap, txtSoLuong, txtMaPN, txtTenNV_Nhap;
    private JComboBox<String> cbKichThuoc, cbThuongHieu, cbXuatXu;
    private JButton btnThemSP, btnSuaSP, btnXoaSP, btnNhapExcel, btnThemPN;
    private JLabel lbTongTien;

    public ThemPhieuNhap() {
        setLayout(null);
        setBackground(Color.WHITE);

        //Side trái
        //Search
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(10,10,345,20);
        add(txtTimKiem);

        //bảng sản phẩm
        tblSanPham = new JTable(new DefaultTableModel(new Object[]{"Mã SP", "Tên SP","Số lượng"},0));
        JScrollPane scrtblSanPham = new JScrollPane(tblSanPham);
        scrtblSanPham.setBounds(10,50,345,300);
        add(scrtblSanPham);

        //bảng chi tiết nhập hàng
        tblChiTietNhapHang = new JTable(new DefaultTableModel(new Object[]{"STT","Mã SP","Tên SP","Thương Hiệu","Xuất xứ","Màu sắc","Giá nhập","Số lượng"},0));
        JScrollPane srctblChiTietNhapHang = new JScrollPane(tblChiTietNhapHang);
        srctblChiTietNhapHang.setBounds(10,420,1070,260);
        add(srctblChiTietNhapHang);

        //Side giữa
        JLabel lb;
        lb = new JLabel("Mã SP");
        lb.setBounds(360,50,100,25);
        add(lb);
        txtMaSP = new JTextField();
        txtMaSP.setBounds(470,50,235,25);
        add(txtMaSP);

        lb = new JLabel("Tên SP");
        lb.setBounds(360,90,100,25);
        add(lb);
        txtTenSP = new JTextField();
        txtTenSP.setBounds(470,90,235,25);
        add(txtTenSP);

        lb = new JLabel("Màu sắc");
        lb.setBounds(360,130,100,25);
        add(lb);
        JPanel colorPanel = new JPanel(new GridLayout(2,7,5,5));
        colorPanel.setBounds(470,130,200,50);
        Color[] palette = {
                new Color( 40,  42,  43),  //#282A2B Đen
                new Color(219, 209, 188),  //#DBD1BC Be
                new Color(144, 113,  59),  //#90713B Nâu
                new Color(159, 169, 169),  //#9FA9A9 Xám nhạt
                new Color(208, 119, 113),  //#D07771 Hồng nhạt
                new Color(149, 155, 120),  //#959B78 Xanh rêu
                new Color( 79,  92, 112),  //#4F5C70 Xanh biển đậm
                new Color(245, 241, 235),  //#F5F1E6 Trắng
                new Color(165,   5,  29),  //#A5051D Đỏ
                new Color( 89,  86,  79),  //#59564F Olive
                new Color( 56, 126, 160),  //#387EA0 Xanh biển nhạt
                new Color( 60,  69,  37),  //#3C4525 Navy
                new Color( 57,  29,  43),  //#391D2B Rượu vang
                new Color(181, 191, 108)   //#B5BF6C Be đậm
        };
        String[] colorNames = {
                "Đen", "Be", "Nâu", "Xám nhạt", "Hồng nhạt", "Xanh rêu", "Xanh biển đậm", "Trắng", "Đỏ", "Olive", "Xanh biển nhạt", "Navy", "Rượu vang", "Be đậm"
        };
        final Color[] selectedColor = {null};
        final String[] selectedColorName = {null};
        for (int i = 0; i < palette.length; i++) {
            Color color = palette[i];
            String name = colorNames[i];
            JPanel swatch = new JPanel();
            swatch.setBackground(color);
            swatch.setToolTipText(name);
            swatch.putClientProperty("colorName", name);
            swatch.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            swatch.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedColor[0] = color;
                    selectedColorName[0] = name;
                    for (Component component : colorPanel.getComponents()) {
                        ((JPanel)component).setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    }
                    swatch.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                }

            });
            colorPanel.add(swatch);
        }
        add(colorPanel);

        lb = new JLabel("Kích thước");
        lb.setBounds(360,200,100,25);
        add(lb);
        cbKichThuoc = new JComboBox<>(new String[] {"S","M","L","XL","2XL","3XL"});
        cbKichThuoc.setBounds(470,200,235,25);
        add(cbKichThuoc);

        lb = new JLabel("Thương hiệu");
        lb.setBounds(360,240,100,25);
        add(lb);
        cbThuongHieu = new JComboBox<>();
        cbThuongHieu.setBounds(470,240,235,25);
        add(cbThuongHieu);

        lb = new JLabel("Xuất xứ");
        lb.setBounds(360,280,100,25);
        add(lb);
        cbXuatXu = new JComboBox<>();
        cbXuatXu.setBounds(470,280,235,25);
        add(cbXuatXu);

        lb = new JLabel("Giá nhập");
        lb.setBounds(360,320,100,25);
        add(lb);
        txtGiaNhap = new JTextField();
        txtGiaNhap.setBounds(470,320,120,25);
        add(txtGiaNhap);

        lb = new JLabel("Số lượng");
        lb.setBounds(590,320,60,25);
        add(lb);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(655,320,50,25);
        add(txtSoLuong);

        //Side phải
        lb = new JLabel("Mã phiếu nhập");
        lb.setBounds(750,50,100,25);
        add(lb);
        txtMaPN = new JTextField();
        txtMaPN.setBounds(860,50,220,25);
        add(txtMaPN);

        lb = new JLabel("Tên NV nhập");
        lb.setBounds(750,90,120,25);
        add(lb);
        txtTenNV_Nhap = new JTextField();
        txtTenNV_Nhap.setBounds(860,90,220,25);
        add(txtTenNV_Nhap);

        lb = new JLabel("Tổng tiền:");
        lb.setBounds(750,320,100,25);
        add(lb);
        lbTongTien = new JLabel("0 VNĐ");
        lbTongTien.setBounds(860,320,220,25);
        add(lbTongTien);

        btnThemSP = new JButton("Thêm Sản Phẩm");
        btnThemSP.setForeground(Color.WHITE);
        btnThemSP.setBackground(new Color(54,162,220));
        btnThemSP.setBounds(10,365,170,40);
        add(btnThemSP);

        btnNhapExcel = new JButton("Nhập Excel");
        btnNhapExcel.setForeground(Color.WHITE);
        btnNhapExcel.setBackground(new Color(93,163,113));
        btnNhapExcel.setBounds(185,365,170,40);
        add(btnNhapExcel);

        btnSuaSP = new JButton("Sửa SP");
        btnSuaSP.setForeground(Color.WHITE);
        btnSuaSP.setBackground(new Color(242,161,0));
        btnSuaSP.setBounds(360,365,170,40);
        add(btnSuaSP);

        btnXoaSP = new JButton("Xóa SP");
        btnXoaSP.setForeground(Color.WHITE);
        btnXoaSP.setBackground(new Color(236,91,91));
        btnXoaSP.setBounds(535,365,170,40);
        add(btnXoaSP);

        btnThemPN = new JButton("Thêm Phiếu Nhập");
        btnThemPN.setForeground(Color.WHITE);
        btnThemPN.setBackground(new Color(93,163,113));
        btnThemPN.setBounds(750,365,330,40);
        add(btnThemPN);


    }

//    public static void main(String[] args) {
//        JFrame f = new JFrame();
//        f.add(new ThemPhieuNhap());
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setVisible(true);
//        f.setSize(1300, 700);
//        f.setLocationRelativeTo(null);
//    }
}
