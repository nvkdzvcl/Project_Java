package GUI.DIALOG;

import BLL.SanPhamBLL;
import DTO.SanPhamDTO;
import GUI.PANEL.SanPham;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ThemSanPhamDialog extends JDialog {
    private JTextField txtTenSP, txtThuongHieu, txtXuatXu, txtSoLuong, txtDonGia;
    private JComboBox<String> cbKichThuoc;
    private JLabel lbHinhAnhSP;
    private JButton btnHinhAnhSP, btnThem, btnHuy;

    final Color[] selectedColor = {null};
    final String[] selectedColorName = {null};

    JFileChooser chooser;
    File file;

    SanPhamBLL sanPhamBLL = new SanPhamBLL();

    public ThemSanPhamDialog(Frame owner, SanPham sanPhamPanel) {
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

        JLabel lbDonGia = new JLabel("Đơn giá:");
        lbDonGia.setBounds(50,290,200,25);
        add(lbDonGia);
        txtDonGia = new JTextField();
        txtDonGia.setBounds(50,320,200,25);
        add(txtDonGia);


        //Cột 2
        JLabel lbMauSac = new JLabel("Màu Sắc:");
        lbMauSac.setBounds(300,220,200,25);
        add(lbMauSac);
        JPanel colorPanel = new JPanel(new GridLayout(2,7,5,5));
        colorPanel.setBounds(300,250,200,50);
        Color[] palette = {
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
        String[] colorNames = {
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

        JLabel lbKichThuoc = new JLabel("Kích thước:");
        lbKichThuoc.setBounds(300,80,200,25);
        add(lbKichThuoc);
        cbKichThuoc = new JComboBox<>(new String[] {"S","M","L","XL","2XL","3XL"});
        cbKichThuoc.setBounds(300,110,200,25);
        add(cbKichThuoc);

        JLabel lbSoLuong = new JLabel("Số lượng:");
        lbSoLuong.setBounds(300,150,200,25);
        add(lbSoLuong);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(300,180,200,25);
        add(txtSoLuong);


        //Cột 3
        btnHinhAnhSP = new JButton("Ảnh sản phẩm");
        btnHinhAnhSP.setBounds(640,80,120,25);
        btnHinhAnhSP.setFocusPainted(false);
        add(btnHinhAnhSP);

        lbHinhAnhSP = new JLabel();
        lbHinhAnhSP.setBounds(575,110,250,250);
        add(lbHinhAnhSP);

        btnHinhAnhSP.addActionListener(e -> {
            chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn ảnh sản phẩm");
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Ảnh JPG, PNG, GIF", "jpg", "jpeg", "png", "gif");
            chooser.addChoosableFileFilter(filter);

            int result = chooser.showOpenDialog(ThemSanPhamDialog.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                try {
                    BufferedImage img = ImageIO.read(file);
                    Image scaled = img.getScaledInstance(lbHinhAnhSP.getWidth(),lbHinhAnhSP.getHeight(),Image.SCALE_SMOOTH);
                    lbHinhAnhSP.setIcon(new ImageIcon(scaled));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ThemSanPhamDialog.this, "Không thể mở file ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //Nút thêm, hủy
        btnThem = new JButton("Thêm Sản Phẩm");
        btnThem.setBounds(250,400,200,40);
        btnThem.setBackground(new Color(56,168,223));
        add(btnThem);
        btnThem.addActionListener(e -> {
            String tenSP = txtTenSP.getText().trim();
            String thuongHieu = txtThuongHieu.getText().trim();
            String xuatXu = txtXuatXu.getText().trim();
            String soLuong = txtSoLuong.getText().trim();
            String donGia = txtDonGia.getText().trim();

            if (tenSP.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập Tên SP!","Lỗi",JOptionPane.ERROR_MESSAGE);
                txtTenSP.requestFocusInWindow();
                return;
            }

            if (thuongHieu.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập Thương hiệu!","Lỗi",JOptionPane.ERROR_MESSAGE);
                txtThuongHieu.requestFocusInWindow();
                return;
            }

            if (xuatXu.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập Xuất sứ!","Lỗi",JOptionPane.ERROR_MESSAGE);
                txtXuatXu.requestFocusInWindow();
                return;
            }
            String XuatXuRegex = "^[\\p{L}\\s']+$";
            if (!xuatXu.matches(XuatXuRegex)) {
                JOptionPane.showMessageDialog(this,"Xuất sứ không hợp lệ!","Lỗi",JOptionPane.ERROR_MESSAGE);
                txtXuatXu.requestFocusInWindow();
                return;
            }

            if (soLuong.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập Số lượng!","Lỗi",JOptionPane.ERROR_MESSAGE);
                txtSoLuong.requestFocusInWindow();
                return;
            }
            String numRegex = "^[1-9]\\d*$";
            if (!soLuong.matches(numRegex)) {
                JOptionPane.showMessageDialog(this,"Số lượng không hợp lệ! Vui lòng nhập số nguyên dương.", "Lỗi",JOptionPane.ERROR_MESSAGE);
                txtSoLuong.requestFocusInWindow();
                return;
            }

            if (donGia.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập Đơn giá!","Lỗi",JOptionPane.ERROR_MESSAGE);
                txtDonGia.requestFocusInWindow();
                return;
            }
            if (!donGia.matches(numRegex)) {
                JOptionPane.showMessageDialog(this,"Đơn giá không hợp lệ! Vui lòng nhập số nguyên dương.","Lỗi",JOptionPane.ERROR_MESSAGE);
                txtDonGia.requestFocusInWindow();
                return;
            }

            if (selectedColorName[0] == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn màu sắc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }


            SanPhamDTO sanPham = new SanPhamDTO(tenSP, thuongHieu, xuatXu, selectedColorName[0], (String)cbKichThuoc.getSelectedItem(),Integer.parseInt(txtDonGia.getText().trim()), Integer.parseInt(soLuong));
            if(sanPhamBLL.insert(sanPham)){
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công");
                sanPhamPanel.loadDataToTable(sanPhamBLL.getlistsp());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Thêm thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
            }

        });

        btnHuy = new JButton(("Hủy bỏ"));
        btnHuy.setBounds(450,400,200,40);
        btnHuy.setBackground(new Color(216,92,99));
        add(btnHuy);
        btnHuy.addActionListener(e -> {
            dispose();
        });
    }
}
