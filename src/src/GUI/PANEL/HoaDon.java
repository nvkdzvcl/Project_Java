package GUI.PANEL;

import BLL.HoaDonBLL;
import BLL.KhachHangBLL;
import BLL.NhanVienBLL;
import BLL.PhieuNhapBLL;
import DAO.HoaDonDAO;
import DAO.PhieuNhapDAO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import GUI.DIALOG.ChiTietHoaDonDialog;
import GUI.DIALOG.ChiTietPhieuNhapDialog;
import com.toedter.calendar.JDateChooser;
import GUI.Main;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class HoaDon extends JPanel {
    private final Main parent;
    private final HoaDonBLL bll = new HoaDonBLL();
    private final HoaDonDAO dao = HoaDonDAO.getInstance();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"STT","Mã HĐ","Tên KH","NV Bán","Thời gian","Tổng tiền","Trạng thái"}, 0);
    private final JTable table = new JTable(model);

    public HoaDon(Main parent) {
        this.parent = parent;
        // Sử dụng BorderLayout với khoảng cách 10 pixel
        setLayout(new BorderLayout(10, 10));

        // --------- PHẦN NÚT CHỨC NĂNG (TOP) -----------
        JPanel P = new JPanel(new BorderLayout());
        JPanel P1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Nút Thêm
        ImageIcon addIcon = resizeimg(new ImageIcon(getClass().getResource("/icon/them.png")));
        JButton btnthem = createIconButton("Thêm", addIcon);
        btnthem.setOpaque(false);
        btnthem.setFocusPainted(false);
        btnthem.setBorderPainted(false);
        P1.add(btnthem);

        // Nút Chi tiết
        ImageIcon chitieticon = resizeimg(new ImageIcon(getClass().getResource("/icon/chitiet.png")));
        JButton btnchitiet = createIconButton("Chi tiết", chitieticon);
        btnchitiet.setOpaque(false);
        btnchitiet.setFocusPainted(false);
        btnchitiet.setBorderPainted(false);
        P1.add(btnchitiet);

        // Nút Hủy phiếu
        ImageIcon huyphieuicon = resizeimg(new ImageIcon(getClass().getResource("/icon/huyphieu.png")));
        JButton btnhuyphieu = createIconButton("Hủy hóa đơn", huyphieuicon);
        btnhuyphieu.setOpaque(false);
        btnhuyphieu.setFocusPainted(false);
        btnhuyphieu.setBorderPainted(false);
        P1.add(btnhuyphieu);

        // Nút Xuất Excel
//        ImageIcon xuatexcelicon = resizeimg(new ImageIcon(getClass().getResource("/icon/xuatexcel.png")));
//        JButton btnxuatexcel = createIconButton("Xuất Excel", xuatexcelicon);
//        btnxuatexcel.setOpaque(false);
//        btnxuatexcel.setFocusPainted(false);
//        btnxuatexcel.setBorderPainted(false);
//        P1.add(btnxuatexcel);

        // Nút Làm mới
        ImageIcon lmcon = resizeimg(new ImageIcon(getClass().getResource("/icon/lammoi.png")));
        JButton btnlm = createIconButton("Làm Mới", lmcon);
        btnlm.setOpaque(false);
        btnlm.setFocusPainted(false);
        btnlm.setVerticalTextPosition(SwingConstants.CENTER);
        btnlm.setHorizontalTextPosition(SwingConstants.RIGHT);

        // Panel chứa công cụ tìm kiếm (bên phải của thanh chức năng)
        JPanel P2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        String[] cb = {"Tất Cả", "STT", "Mã HĐ"};
        JComboBox<String> pl = new JComboBox<>(cb);
        pl.setPreferredSize(new Dimension(100, 40));
        JTextField tf = new JTextField(20);
        tf.setPreferredSize(new Dimension(100, 40));
        P2.add(pl);
        P2.add(tf);
        P2.add(btnlm);

        // Ghép hai panel con vào panel P
        P.add(P1, BorderLayout.WEST);
        P.add(P2, BorderLayout.EAST);

        // Thêm panel chứa các nút chức năng vào phần NORTH của giao diện
        add(P, BorderLayout.NORTH);

        // --------- PHẦN GIAO DIỆN CHÍNH -----------
        // Tạo một panel trung tâm để chứa cả bộ lọc tìm kiếm và khu vực hiển thị nội dung
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Thêm bộ lọc tìm kiếm vào phần WEST
        JPanel filterPanel = createLeftFilterPanel();
        centerPanel.add(filterPanel, BorderLayout.WEST);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);


        add(centerPanel, BorderLayout.CENTER);
        btnthem.addActionListener(e -> {
            ThemHoaDon themHd = (ThemHoaDon) parent.getPanel("themhoadon");
            themHd.resetForm();
            parent.showPanel("themhoadon");
        });
        btnchitiet.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn hóa đơn cần xem chi tiết",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Nếu bạn kích hoạt sorting/filtering, convert index:
            int modelRow = table.convertRowIndexToModel(viewRow);
            int maHD = Integer.parseInt(
                    model.getValueAt(modelRow, 1).toString()
            );
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            new ChiTietHoaDonDialog(owner, maHD).setVisible(true);
        });

        btnhuyphieu.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn hóa đơn cần hủy!",
                        "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int maHD = (int) model.getValueAt(row, 1);
            int ans = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn hủy hóa đơn #" + maHD + "?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                if (bll.deleteHoaDon(maHD)) {
                    JOptionPane.showMessageDialog(this,
                            "Đã hủy hóa đơn #" + maHD,
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    reloadTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Hủy hóa đo thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnlm.addActionListener(e -> {
            reloadTable();
            ((SanPham) parent.getPanel("sanpham")).reloadTable();
        });

        // Khi gõ search hoặc chọn filter, bạn có thể gọi:
        // List<PhieuNhapDTO> filtered = bll.filterPhieuNhap(...);
        // loadDataToTable(filtered);

        // ==== khởi tạo dữ liệu lên table lần đầu ====
        reloadTable();

        //Tìm kiếm
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        tf.getDocument().addDocumentListener(new DocumentListener() {
            private void applyFilter() {
                String text = tf.getText().trim();
                if (text.isEmpty()) {
                    sorter.setRowFilter(null);
                    return;
                }
                int idx = pl.getSelectedIndex();
                try {
                    switch (idx) {
                        case 0 -> {
                            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text), 0,1));
                        }
                        case 1 -> {
                            int stt = Integer.parseInt(text);
                            sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL,stt,0));
                        }
                        case 2 -> {
                            int mpn = Integer.parseInt(text);
                            sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL,mpn,1));
                        }
                    }
                } catch (NumberFormatException ex) {
                    sorter.setRowFilter(null);
                }
            }
            public void insertUpdate(DocumentEvent e) {
                applyFilter();
            }
            public void removeUpdate(DocumentEvent e) {
                applyFilter();
            }
            public void changedUpdate(DocumentEvent e) {
                applyFilter();
            }
        });

        pl.addActionListener(e -> {
            tf.setText("");
            sorter.setRowFilter(null);
        });


        setVisible(true);
    }

    // Phương thức thay đổi kích thước icon
    public ImageIcon resizeimg(ImageIcon img) {
        Image tmp = img.getImage();
        Image tmp2 = tmp.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        return new ImageIcon(tmp2);
    }

    // Tạo nút có icon và text
    private JButton createIconButton(String text, ImageIcon icon) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
        }
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }

    private JPanel createLeftFilterPanel() {
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Bộ lọc tìm kiếm"),
                new EmptyBorder(5, 5, 5, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;

        // --- 1) Khách hàng ---
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Khách hàng:"), gbc);
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        JComboBox<String> cbKH = new JComboBox<>();
        cbKH.addItem("Tất cả");
        java.util.List<KhachHangDTO> khList = new KhachHangBLL().getlistkh();
        for (KhachHangDTO kh : khList) {
            cbKH.addItem(kh.getTenKhachHang());
        }
        leftPanel.add(cbKH, gbc);
        gbc.weightx = 0;

        // --- 2) Nhân viên bán ---
        gbc.gridy = 2;
        leftPanel.add(new JLabel("Nhân viên bán:"), gbc);
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        JComboBox<String> cbNV = new JComboBox<>();
        cbNV.addItem("Tất cả");
        java.util.List<NhanVienDTO> nvList = new NhanVienBLL().getlistnv();
        for (NhanVienDTO nv : nvList) {
            cbNV.addItem(nv.getHoTen());
        }
        leftPanel.add(cbNV, gbc);
        gbc.weightx = 0;

        // --- 3) Từ ngày / Đến ngày ---
        gbc.gridy = 4;
        leftPanel.add(new JLabel("Từ ngày:"), gbc);
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        JDateChooser dcFrom = new JDateChooser();
        dcFrom.setDateFormatString("dd/MM/yyyy");
        leftPanel.add(dcFrom, gbc);
        gbc.weightx = 0;

        gbc.gridy = 6;
        leftPanel.add(new JLabel("Đến ngày:"), gbc);
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        JDateChooser dcTo = new JDateChooser();
        dcTo.setDateFormatString("dd/MM/yyyy");
        leftPanel.add(dcTo, gbc);
        gbc.weightx = 0;

        // --- 4) Từ tiền / Đến tiền ---
        gbc.gridy = 8;
        leftPanel.add(new JLabel("Từ tiền (VNĐ):"), gbc);
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        JTextField tfMin = new JTextField();
        leftPanel.add(tfMin, gbc);
        gbc.weightx = 0;

        gbc.gridy = 10;
        leftPanel.add(new JLabel("Đến tiền (VNĐ):"), gbc);
        gbc.gridy = 11;
        gbc.weightx = 1.0;
        JTextField tfMax = new JTextField();
        leftPanel.add(tfMax, gbc);
        gbc.weightx = 0;

        // --- 5) Nút Áp dụng & Xóa ---
        gbc.gridy = 12;
        gbc.weightx = 1.0;
        JButton btnApply = new JButton("Áp dụng");
        leftPanel.add(btnApply, gbc);

        gbc.gridy = 13;
        JButton btnClear = new JButton("Xóa bộ lọc");
        leftPanel.add(btnClear, gbc);

        // Khoảng trống đẩy nút xuống dưới
        gbc.gridy = 14;
        gbc.weighty = 1.0;
        leftPanel.add(new JLabel(), gbc);

        // --- Logic cho 2 nút ---
        btnApply.addActionListener(e -> {
            // Lấy điều kiện
            Integer khId = null, nvId = null;
            if (cbKH.getSelectedIndex() > 0) {
                khId = khList.get(cbKH.getSelectedIndex() - 1).getMaKhachHang();
            }
            if (cbNV.getSelectedIndex() > 0) {
                nvId = nvList.get(cbNV.getSelectedIndex() - 1).getMaNV();
            }
            java.util.Date from = dcFrom.getDate();
            java.util.Date to   = dcTo.getDate();

            Integer minTien = null, maxTien = null;
            try {
                String sMin = tfMin.getText().trim();
                if (!sMin.isEmpty()) minTien = Integer.parseInt(sMin);
                String sMax = tfMax.getText().trim();
                if (!sMax.isEmpty()) maxTien = Integer.parseInt(sMax);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Giá trị tiền phải là số nguyên!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Gọi BLL để lọc và nạp lại bảng
            java.util.List<HoaDonDTO> filtered =
                    bll.filterHoaDon(khId, nvId, from, to, minTien, maxTien);
            loadDataToTable(filtered);
        });

        btnClear.addActionListener(e -> {
            cbKH.setSelectedIndex(0);
            cbNV.setSelectedIndex(0);
            dcFrom.setDate(null);
            dcTo.setDate(null);
            tfMin.setText("");
            tfMax.setText("");
            reloadTable();
        });

        leftPanel.setPreferredSize(new Dimension(240, leftPanel.getPreferredSize().height));
        return leftPanel;
    }


    private ImageIcon loadIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Không thể tải icon: " + path);
            return null;
        }
    }

    public void loadDataToTable(java.util.List<HoaDonDTO> danhSach) {
        model.setRowCount(0);
        int stt = 1;
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (HoaDonDTO hd : danhSach) {
            String tenKH = new KhachHangBLL().getonekh(hd.getKhachHang()).getTenKhachHang();
            String tenNV = new NhanVienBLL().getonenv(hd.getNhanVienBan()).getHoTen();
            String thoiGian = fmt.format(hd.getThoiGian());

            model.addRow(new Object[]{
                    stt++,
                    hd.getMaHoaDon(),
                    tenKH,
                    tenNV,
                    thoiGian,
                    hd.getTongTien(),
                    switch (hd.getTrangThai()) {
                        case 2 -> "Đã giao";
                        case 3 -> "Hủy";
                        default -> "Đang giao";
                    }
            });
        }
    }


    public HoaDonDTO getById(int maHD) {
        return dao.selectById(maHD);
    }

    public void reloadTable() {
        bll.refresh();
        ArrayList<HoaDonDTO> list = bll.getDanhSachHoaDon();
        loadDataToTable(list);
    }
}
