package GUI.DIALOG;

import BLL.SanPhamBLL;
import DTO.SanPhamDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChiTietSanPhamDialog extends JDialog {
    private JTextField txtTenSP, txtThuongHieu, txtXuatXu, txtSoLuong, txtDonGia;
    private JComboBox<String> cbKichThuoc;
    private JLabel lbHinhAnhSP;
    private JButton btnHinhAnhSP, btnLuu, btnHuy;

    private JPanel colorPanel;
    private Color[] palette;
    private String[] colorNames;
    private String selectedColorName;

    private SanPhamDTO currentDTO;
    private SanPhamBLL sanPhamBLL = new SanPhamBLL();

    private GUI.PANEL.SanPham parentPanel;

    public ChiTietSanPhamDialog(Frame owner,GUI.PANEL.SanPham parentPanel) {
        super(owner,"Sửa Sản Phẩm",true);
        this.parentPanel = parentPanel;
        setSize(900,500);
        setTitle("Sửa Sản Phẩm");
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbTittle = new JLabel("XEM SẢN PHẨM", SwingConstants.CENTER);
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
        txtTenSP.setEnabled(false);
        add(txtTenSP);


        JLabel lbThuongHieu = new JLabel("Thương Hiệu:");
        lbThuongHieu.setBounds(50,150,200,25);
        add(lbThuongHieu);
        txtThuongHieu = new JTextField();
        txtThuongHieu.setBounds(50,180,200,25);
        txtThuongHieu.setEnabled(false);
        add(txtThuongHieu);

        JLabel lbXuatXu = new JLabel("Xuất Xứ:");
        lbXuatXu.setBounds(50,220,200,25);
        add(lbXuatXu);
        txtXuatXu = new JTextField();
        txtXuatXu.setBounds(50,250,200,25);
        txtXuatXu.setEnabled(false);
        add(txtXuatXu);

        JLabel lbDonGia = new JLabel("Đơn giá:");
        lbDonGia.setBounds(50,290,200,25);
        add(lbDonGia);
        txtDonGia = new JTextField();
        txtDonGia.setBounds(50,320,200,25);
        txtDonGia.setEnabled(false);
        add(txtDonGia);

        //Cột 2
        JLabel lbMauSac = new JLabel("Màu Sắc:");
        lbMauSac.setBounds(300,220,200,25);
        add(lbMauSac);
        colorPanel = new JPanel(new GridLayout(2,7,5,5));
        colorPanel.setBounds(300,250,200,50);
        palette = new Color[] {
                new Color( 40,  42,  43),  //#282A2B Đen
                new Color(219, 209, 188),  //#DBD1BC Be
                new Color(144, 113,  59),  //#90713B Nâu
                new Color(159, 169, 169),  //#9FA9A9 Xám nhạt
                new Color(208, 119, 113),  //#D07771 Hồng nhạt
                new Color(149, 155, 120),  //#959B78 Xanh rêu
                new Color( 79,  92, 112),  //#4F5C70 Xanh biển đậm
                new Color(245, 241, 230),  //#F5F1E6 Trắng
                new Color(165,   5,  29),  //#A5051D Đỏ
                new Color( 89,  86,  79),  //#59564F Olive
                new Color( 56, 126, 160),  //#387EA0 Xanh biển nhạt
                new Color( 60,  69,  37),  //#3C4525 Navy
                new Color( 57,  29,  43),  //#391D2B Rượu vang
                new Color(181, 191, 108)   //#B5BF6C Be đậm
        };
        colorNames = new String[] {
                "Đen", "Be", "Nâu", "Xám nhạt", "Hồng nhạt", "Xanh rêu", "Xanh biển đậm", "Trắng", "Đỏ", "Olive", "Xanh biển nhạt", "Navy", "Rượu vang", "Be đậm"
        };
        for (int i = 0; i < palette.length; i++) {
            Color color = palette[i];
            String name = colorNames[i];
            JPanel swatch = new JPanel();
            swatch.setBackground(color);
            swatch.setToolTipText(name);
            swatch.putClientProperty("colorName", name);
            swatch.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        }
        for (int i = 0; i < palette.length; i++) {
            Color color = palette[i];
            String name = colorNames[i];
            JPanel swatch = new JPanel();
            swatch.setBackground(color);
            swatch.setToolTipText(name);
            swatch.putClientProperty("colorName", name);
            swatch.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            colorPanel.add(swatch);
        }
        add(colorPanel);

        add(colorPanel);

        JLabel lbKichThuoc = new JLabel("Kích thước:");
        lbKichThuoc.setBounds(300,80,200,25);
        add(lbKichThuoc);
        cbKichThuoc = new JComboBox<>(new String[] {"S","M","L","XL","2XL","3XL"});
        cbKichThuoc.setBounds(300,110,200,25);
        cbKichThuoc.setEnabled(false);
        add(cbKichThuoc);

        JLabel lbSoLuong = new JLabel("Số lượng:");
        lbSoLuong.setBounds(300,150,200,25);
        add(lbSoLuong);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(300,180,200,25);
        txtSoLuong.setEnabled(false);
        add(txtSoLuong);


        //Cột 3
        btnHinhAnhSP = new JButton("Ảnh sản phẩm");
        btnHinhAnhSP.setBounds(640,80,120,25);
        btnHinhAnhSP.setFocusPainted(false);
        add(btnHinhAnhSP);

        lbHinhAnhSP = new JLabel();
        lbHinhAnhSP.setBounds(575,110,250,250);
        add(lbHinhAnhSP);






        btnHuy = new JButton(("Hủy bỏ"));
        btnHuy.setBounds(450,400,200,40);
        btnHuy.setBackground(new Color(216,92,99));
        add(btnHuy);
        btnHuy.addActionListener(e -> {
            dispose();
        });
    }

    public void setSanPham(SanPhamDTO dto) {
        txtTenSP.setText(dto.getTenSP());
        txtThuongHieu.setText(dto.getThuongHieu());
        txtXuatXu.setText(dto.getXuatXu());
        txtSoLuong.setText(String.valueOf(dto.getSoLuong()));
        cbKichThuoc.setSelectedItem(dto.getKichThuoc());
        txtDonGia.setText(String.valueOf(dto.getDonGia()));
        this.currentDTO = dto;

        for (Component component : colorPanel.getComponents()) {
            JPanel swatch = (JPanel) component;
            String name = (String)swatch.getClientProperty("colorName");
            if (dto.getMauSac().equals(name)) {
                swatch.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                selectedColorName = name;
            } else {
                swatch.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }

//        try {
//            BufferedImage img = ImageIO.read(new File(dto.getHinhAnh()));
//            Image scaled = img.getScaledInstance(lbHinhAnhSP.getWidth(), lbHinhAnhSP.getHeight(), Image.SCALE_SMOOTH);
//            lbHinhAnhSP.setIcon(new ImageIcon(scaled));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this,"Không thể tải hình ảnh","Lỗi",JOptionPane.ERROR_MESSAGE);
//        }
    }
}
