package GUI.PANEL;

import BLL.NhanVienBLL;
import BLL.PhieuNhapBLL;
import BLL.SanPhamBLL;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import GUI.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class ThemPhieuNhap extends JPanel {
    private final Main parent;
    private final PhieuNhapBLL bll = new PhieuNhapBLL();
    private final SanPhamBLL sanPhamBLL = new SanPhamBLL();
    private final NhanVienBLL nhanVienBLL = new NhanVienBLL();
    private java.util.ArrayList<NhanVienDTO> listNhanVien;

    private JTable tblSanPham, tblChiTietNhapHang;
    private DefaultTableModel modelSanPham, modelChiTietNhapHang;
    private JTextField txtTimKiem, txtMaSP, txtTenSP, txtGiaNhap, txtSoLuong, txtMaPN;
    private JComboBox<String> cbKichThuoc, cbThuongHieu, cbXuatXu, cbNhanVien_Nhap, cbTrangThai;
    private JButton btnThemSP, btnSuaSP, btnXoaSP, btnNhapExcel, btnThemPN;
    private JLabel lbTongTien;
    private JPanel colorPanel;
    private final Color[] selectedColor = {null};
    private final String[] selectedColorName = {null};

    private PhieuNhapBLL phieuNhapBLL = new PhieuNhapBLL();

    private int editingRow = -1;

    public ThemPhieuNhap(Main parent) {
        this.parent = parent;
        setLayout(null);
        setBackground(Color.WHITE);

        //Side trái
        //Search
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(10,10,345,20);
        add(txtTimKiem);

        //bảng sản phẩm
        tblSanPham = new JTable(new DefaultTableModel(new Object[]{"Mã SP", "Tên SP","Số lượng tồn"},0));
        modelSanPham = (DefaultTableModel) tblSanPham.getModel();
        loadSanPhamToTable();
        JScrollPane scrtblSanPham = new JScrollPane(tblSanPham);
        scrtblSanPham.setBounds(10,50,345,300);
        tblSanPham.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblSanPham.getSelectedRow();
                if (row >= 0) {
                    fillChiTietFromTable(row);
                }
            }
        });
        add(scrtblSanPham);

        //bảng chi tiết nhập hàng
        tblChiTietNhapHang = new JTable(new DefaultTableModel(new Object[]{"STT","Mã SP","Tên SP","Thương Hiệu","Xuất xứ","Màu sắc","Kích thước","Giá nhập","Số lượng"},0));
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
        colorPanel = new JPanel(new GridLayout(2,7,5,5));
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

        Set<String> thuongHieu = new LinkedHashSet<>();
        Set<String> xuatXu = new LinkedHashSet<>();
        for (SanPhamDTO sp : sanPhamBLL.getlistsp()) {
            thuongHieu.add(sp.getThuongHieu());
            xuatXu.add(sp.getXuatXu());
        }
        for (String th : thuongHieu) {
            cbThuongHieu.addItem(th);
        }
        for (String xx : xuatXu) {
            cbXuatXu.addItem(xx);
        }

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
        txtMaPN.setEditable(false);
        add(txtMaPN);
        int nextId = phieuNhapBLL.getNextMaPhieuNhap();
        if (nextId > 0) {
            txtMaPN.setText(String.valueOf(nextId));
        } else {
            txtMaPN.setText("-");
        }

        lb = new JLabel("Tên NV nhập");
        lb.setBounds(750,90,120,25);
        add(lb);
        cbNhanVien_Nhap = new JComboBox<>();
        cbNhanVien_Nhap.setBounds(860,90,220,25);
        add(cbNhanVien_Nhap);
        listNhanVien = nhanVienBLL.getlistnv();
        for (NhanVienDTO nv : listNhanVien) {
            cbNhanVien_Nhap.addItem(nv.getHoTen());
        }

        lb = new JLabel("Trạng thái");
        lb.setBounds(750,120,120,25);
        add(lb);
        cbTrangThai = new JComboBox<>(new String[] {"Chờ","Hoàn thành"});
        cbTrangThai.setBounds(860,120,220,25);
        add(cbTrangThai);

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
        //-----------------
        modelChiTietNhapHang = (DefaultTableModel) tblChiTietNhapHang.getModel();
        btnThemSP.addActionListener(e -> {
            try {
                String maSP = txtMaSP.getText().trim();
                String tenSP = txtTenSP.getText().trim();
                String thuong_Hieu = (String) cbThuongHieu.getSelectedItem();
                String xuat_Xu = (String) cbXuatXu.getSelectedItem();
                String mauSac = selectedColorName[0];
                String kichThuoc = (String) cbKichThuoc.getSelectedItem();
                int donGia = Integer.parseInt(txtGiaNhap.getText().trim());
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());

                int stt = modelChiTietNhapHang.getRowCount() + 1;

                modelChiTietNhapHang.addRow(new Object[] {stt,maSP,tenSP,thuong_Hieu,xuat_Xu,mauSac,kichThuoc,donGia,soLuong});

                int tong = 0;
                for (int i = 0; i < modelChiTietNhapHang.getRowCount(); i++) {
                    int dg = (int)modelChiTietNhapHang.getValueAt(i,7);
                    int sl = (int)modelChiTietNhapHang.getValueAt(i,8);
                    tong += dg * sl;
                }
                lbTongTien.setText(tong + " VNĐ");

                txtMaSP.setText("");
                txtTenSP.setText("");
                txtGiaNhap.setText("");
                txtSoLuong.setText("");
                cbKichThuoc.setSelectedItem("");
                cbThuongHieu.setSelectedItem("");
                cbXuatXu.setSelectedItem("");
                for (Component c1 : colorPanel.getComponents()) {
                    ((JPanel) c1).setBorder(BorderFactory.createLineBorder(Color.GRAY));
                }
                selectedColorName[0] = null;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,"Giá nhập và Số lượng phải là số hợp lệ!","Lỗi",JOptionPane.ERROR_MESSAGE);
            }
        });

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
        btnSuaSP.addActionListener(e -> {
            int row = tblChiTietNhapHang.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn sản phẩm cần sửa",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            editingRow = row;

            String maSP = modelChiTietNhapHang.getValueAt(row, 1).toString();
            String tenSP = modelChiTietNhapHang.getValueAt(row, 2).toString();
            String thuong_Hieu = modelChiTietNhapHang.getValueAt(row, 3).toString();
            String xuat_Xu = modelChiTietNhapHang.getValueAt(row, 4).toString();
            String mauSac = modelChiTietNhapHang.getValueAt(row, 5).toString();
            String kichThuoc = modelChiTietNhapHang.getValueAt(row, 6).toString();
            String donGia = modelChiTietNhapHang.getValueAt(row, 7).toString();
            String soLuong = modelChiTietNhapHang.getValueAt(row, 8).toString();

            txtMaSP.setText(maSP);
            txtTenSP.setText(tenSP);
            cbThuongHieu.setSelectedItem(thuong_Hieu);
            cbXuatXu.setSelectedItem(xuat_Xu);
            cbKichThuoc.setSelectedItem(kichThuoc);
            txtGiaNhap.setText(donGia);
            txtSoLuong.setText(soLuong);

            for (Component comp : colorPanel.getComponents()) {
                JPanel swatch = (JPanel) comp;
                String name = (String) swatch.getClientProperty("colorName");
                if (name.equals(mauSac)) {
                    swatch.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    selectedColorName[0] = name;
                } else {
                    swatch.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                }
            }
        });
        txtSoLuong.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    commitEdit();
                }
            }
        });


        btnXoaSP = new JButton("Xóa SP");
        btnXoaSP.setForeground(Color.WHITE);
        btnXoaSP.setBackground(new Color(236,91,91));
        btnXoaSP.setBounds(535,365,170,40);
        add(btnXoaSP);
        btnXoaSP.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) tblChiTietNhapHang.getModel();
            int row = tblChiTietNhapHang.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this,"Vui lòng chọn sản phẩm cần xóa","Thông báo",JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa sản phẩm này?","Xác nhận xóa",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                for (int i = 0; i < modelChiTietNhapHang.getRowCount(); i++) {
                    modelChiTietNhapHang.setValueAt(i+1,i,0);
                }
                recalcTotal();
                if (editingRow == row) {
                    editingRow = -1;
                    clearForm();
                }
            }
        });

        btnThemPN = new JButton("Thêm Phiếu Nhập");
        btnThemPN.setForeground(Color.WHITE);
        btnThemPN.setBackground(new Color(93,163,113));
        btnThemPN.setBounds(750,365,330,40);
        add(btnThemPN);
        btnThemPN.addActionListener(e -> {
            // 1. Kiểm tra xem có SP nào chưa
            if (modelChiTietNhapHang.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Chưa có sản phẩm nào để nhập",
                        "Lỗi",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // 2. Xác nhận tạo phiếu
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn tạo phiếu nhập này?",
                    "Xác nhận tạo phiếu",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                // 3. Chuẩn bị DTO phiếu nhập
                PhieuNhapDTO pn = new PhieuNhapDTO();
                int idxNV = cbNhanVien_Nhap.getSelectedIndex();
                pn.setNhanVienNhap(listNhanVien.get(idxNV).getMaNV());
                pn.setNgay(new Date());
                // Lấy tổng tiền (loại bỏ " VNĐ")
                String raw = lbTongTien.getText().replace(" VNĐ", "").trim();
                pn.setTongTien(Integer.parseInt(raw));
                int trangThai = cbTrangThai.getSelectedIndex() == 1 ? 2 : 1;
                pn.setTrangThai(trangThai);

                // 4. Gọi BLL để insert
                int newId = bll.insertPhieuNhap(pn);
                if (newId < 0) {
                    throw new RuntimeException("Lỗi khi thêm phiếu nhập");
                }

                //kiểm tra phiếu nhập nào hoàn thành thì tăng số lượng sản phẩm lên
                boolean isCompleted = pn.getTrangThai() == 2;

                // 5. Insert chi tiết
                for (int i = 0; i < modelChiTietNhapHang.getRowCount(); i++) {
                    int maSP   = Integer.parseInt(modelChiTietNhapHang.getValueAt(i, 1).toString());
                    int donGia = (int) modelChiTietNhapHang.getValueAt(i, 7);
                    int soLuong= (int) modelChiTietNhapHang.getValueAt(i, 8);
                    bll.insertChiTiet(newId, maSP, donGia, soLuong);

                    if (isCompleted) {
                        sanPhamBLL.increaseStock(maSP, soLuong);
                    }
                }

                // 6. Thông báo và quay về panel Phiếu nhập
                JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thành công");
                parent.showPanel("phieunhap");

                // 7. Làm mới dữ liệu bảng ở panel Phiếu nhập
                PhieuNhap pnPanel = (PhieuNhap) parent.getPanel("phieunhap");
                pnPanel.reloadTable();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Lỗi khi tạo phiếu nhập:\n" + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            SanPham spPanel = (SanPham) parent.getPanel("sanpham");
            spPanel.reloadTable();
        });
    }

    private void loadSanPhamToTable() {
        modelSanPham.setRowCount(0);
        for (SanPhamDTO sp : sanPhamBLL.getlistsp()) {
            modelSanPham.addRow(new Object[]{sp.getMaSP(),sp.getTenSP(),sp.getSoLuong()});
        }
    }

    private void fillChiTietFromTable(int row) {
        int maSP = (int) modelSanPham.getValueAt(row, 0);
        SanPhamDTO sp = sanPhamBLL.getonesp(maSP);
        if (sp == null) {
            return;
        }

        txtMaSP.setText(String.valueOf(sp.getMaSP()));
        txtTenSP.setText(String.valueOf(sp.getTenSP()));
//        txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
//        txtGiaNhap.setText(String.valueOf(sp.getDonGia()));
        cbKichThuoc.setSelectedItem(sp.getKichThuoc());
        cbThuongHieu.setSelectedItem(sp.getThuongHieu());
        cbXuatXu.setSelectedItem(sp.getXuatXu());
        txtGiaNhap.setText(String.valueOf(sp.getDonGia()));
        for (Component c : colorPanel.getComponents()) {
            JPanel swatch = (JPanel) c;
            String name = (String) swatch.getClientProperty("colorName");
            if (sp.getMauSac().equals(name)) {
                swatch.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                selectedColor[0] = swatch.getBackground();
                selectedColorName[0] = name;
            } else {
                swatch.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }


    }

    private void commitEdit() {
        if (editingRow >= 0) {
            DefaultTableModel m = (DefaultTableModel) tblChiTietNhapHang.getModel();
            m.setValueAt(txtMaSP.getText().trim(), editingRow, 1);
            m.setValueAt(txtTenSP.getText().trim(), editingRow, 2);
            m.setValueAt(cbThuongHieu.getSelectedItem(), editingRow, 3);
            m.setValueAt(cbXuatXu.getSelectedItem(), editingRow, 4);
            m.setValueAt(selectedColorName[0], editingRow, 5);
            m.setValueAt(cbKichThuoc.getSelectedItem(), editingRow, 6);
            m.setValueAt(Integer.parseInt(txtGiaNhap.getText().trim()), editingRow, 7);
            m.setValueAt(Integer.parseInt(txtSoLuong.getText().trim()), editingRow, 8);
            editingRow = -1;
            clearForm();
            recalcTotal();
        }
    }

    private void clearForm() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtGiaNhap.setText("");
        txtSoLuong.setText("");
        cbKichThuoc.setSelectedIndex(0);
        cbThuongHieu.setSelectedIndex(0);
        cbXuatXu.setSelectedIndex(0);
        for (Component comp : colorPanel.getComponents()) {
            ((JPanel) comp).setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        selectedColorName[0] = null;
    }

    private void recalcTotal() {
        DefaultTableModel m = (DefaultTableModel) tblChiTietNhapHang.getModel();
        int total = 0;
        for (int i = 0; i < m.getRowCount(); i++) {
            int dg = (int) m.getValueAt(i, 7);
            int sl = (int) m.getValueAt(i, 8);
            total += dg * sl;
        }
        lbTongTien.setText(total + " VNĐ");
    }

    public void resetForm() {
        loadSanPhamToTable();

        modelChiTietNhapHang.setRowCount(0);
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtGiaNhap.setText("");
        txtSoLuong.setText("");
        cbKichThuoc.setSelectedIndex(0);
        cbThuongHieu.setSelectedIndex(0);
        cbXuatXu.setSelectedIndex(0);
        for (Component c : colorPanel.getComponents()) {
            ((JPanel)c).setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        selectedColorName[0] = null;
        cbNhanVien_Nhap.setSelectedIndex(0);
        cbTrangThai.setSelectedIndex(0);
        editingRow = -1;
        int nextId = phieuNhapBLL.getNextMaPhieuNhap();
        txtMaPN.setText(nextId > 0 ? String.valueOf(nextId) : "-");
        lbTongTien.setText("0 VNĐ");
        clearForm();
    }
}
