package GUI.PANEL;

import BLL.NhanVienBLL;
import BLL.PhieuNhapBLL;
import DAO.PhieuNhapDAO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import GUI.DIALOG.ChiTietPhieuNhapDialog;
import GUI.Main;
import com.toedter.calendar.JDateChooser;

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


public class PhieuNhap extends JPanel {
    private final Main parent;
    private final PhieuNhapBLL bll = new PhieuNhapBLL();
    private final PhieuNhapDAO dao = PhieuNhapDAO.getInstance();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"STT","Mã PN","NV Nhập","Thời gian","Tổng tiền","Trạng thái"}, 0);
    private final JTable table = new JTable(model);

    public PhieuNhap(Main parent) {
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
        JButton btnchitiet = createIconButton("Chi tiêt", chitieticon);
        btnchitiet.setOpaque(false);
        btnchitiet.setFocusPainted(false);
        btnchitiet.setBorderPainted(false);
        P1.add(btnchitiet);

        // Nút Hủy phiếu
        ImageIcon huyphieuicon = resizeimg(new ImageIcon(getClass().getResource("/icon/huyphieu.png")));
        JButton btnhuyphieu = createIconButton("Hủy phiếu", huyphieuicon);
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
        String[] cb = {"Tất Cả", "STT", "Mã PN"};
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
            ThemPhieuNhap themPn = (ThemPhieuNhap) parent.getPanel("themphieunhap");
            themPn.resetForm();
            parent.showPanel("themphieunhap");
        });
        btnchitiet.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn phiếu cần xem chi tiết",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Nếu bạn kích hoạt sorting/filtering, convert index:
            int modelRow = table.convertRowIndexToModel(viewRow);
            int maPN = Integer.parseInt(
                    model.getValueAt(modelRow, 1).toString()
            );
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            new ChiTietPhieuNhapDialog(owner, maPN).setVisible(true);
        });

        btnhuyphieu.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn phiếu cần hủy!",
                        "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int maPN = (int) model.getValueAt(row, 1);
            int ans = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn hủy phiếu #" + maPN + "?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                if (bll.deletePhieuNhap(maPN)) {
                    JOptionPane.showMessageDialog(this,
                            "Đã hủy phiếu #" + maPN,
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    reloadTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Hủy phiếu thất bại!",
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


    public ImageIcon resizeimg(ImageIcon img) {
        Image tmp = img.getImage();
        Image tmp2 = tmp.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        return new ImageIcon(tmp2);
    }

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

        // --- 1. Nhân viên nhập ---
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Nhân viên nhập:"), gbc);
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        JComboBox<String> cbNhanVien = new JComboBox<>();
        cbNhanVien.addItem("Tất cả");
        // load danh sách nhân viên từ BLL
        ArrayList<NhanVienDTO> listNV = new NhanVienBLL().getlistnv();
        for (NhanVienDTO nv : listNV) {
            cbNhanVien.addItem(nv.getHoTen());
        }
        leftPanel.add(cbNhanVien, gbc);
        gbc.weightx = 0;

        // --- 2. Từ ngày / Đến ngày ---
        gbc.gridy = 2;
        leftPanel.add(new JLabel("Từ ngày:"), gbc);
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        JDateChooser dateChooserTu = new JDateChooser();
        dateChooserTu.setDateFormatString("dd/MM/yyyy");
        leftPanel.add(dateChooserTu, gbc);
        gbc.weightx = 0;

        gbc.gridy = 4;
        leftPanel.add(new JLabel("Đến ngày:"), gbc);
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        JDateChooser dateChooserDen = new JDateChooser();
        dateChooserDen.setDateFormatString("dd/MM/yyyy");
        leftPanel.add(dateChooserDen, gbc);
        gbc.weightx = 0;

        // --- 3. Từ số tiền / Đến số tiền ---
        gbc.gridy = 6;
        leftPanel.add(new JLabel("Từ số tiền (VND):"), gbc);
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        JTextField tfMinTien = new JTextField();
        leftPanel.add(tfMinTien, gbc);
        gbc.weightx = 0;

        gbc.gridy = 8;
        leftPanel.add(new JLabel("Đến số tiền (VND):"), gbc);
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        JTextField tfMaxTien = new JTextField();
        leftPanel.add(tfMaxTien, gbc);
        gbc.weightx = 0;

        // --- 4. Nút Áp dụng và Xóa bộ lọc ---
        gbc.gridy = 10;
        gbc.weightx = 1.0;
        JButton btnApply = new JButton("Áp dụng");
        leftPanel.add(btnApply, gbc);

        gbc.gridy = 11;
        JButton btnClear = new JButton("Xóa bộ lọc");
        leftPanel.add(btnClear, gbc);

        // --- 5. Hành động cho 2 nút ---
        btnApply.addActionListener(e -> {
            // Lấy điều kiện
            Integer nvId = null;
            if (cbNhanVien.getSelectedIndex() > 0) {
                // vì vị trí 0 là "Tất cả", nên index-1 tương ứng listNV
                nvId = listNV.get(cbNhanVien.getSelectedIndex() - 1).getMaNV();
            }
            java.util.Date from = dateChooserTu.getDate();
            java.util.Date to   = dateChooserDen.getDate();
            Integer minTien = null, maxTien = null;
            try {
                String s1 = tfMinTien.getText().trim();
                if (!s1.isEmpty()) minTien = Integer.parseInt(s1);
                String s2 = tfMaxTien.getText().trim();
                if (!s2.isEmpty()) maxTien = Integer.parseInt(s2);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Giá trị số tiền phải là số nguyên!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Gọi BLL để lọc
            java.util.List<PhieuNhapDTO> filtered =
                    bll.filterPhieuNhap(nvId, from, to, minTien, maxTien);
            // Nạp lại table
            loadDataToTable(filtered);
        });

        btnClear.addActionListener(e -> {
            cbNhanVien.setSelectedIndex(0);
            dateChooserTu.setDate(null);
            dateChooserDen.setDate(null);
            tfMinTien.setText("");
            tfMaxTien.setText("");
            reloadTable();
        });

        // Để chiếm khoảng trống cuối
        gbc.gridy = 12;
        gbc.weighty = 1.0;
        leftPanel.add(new JLabel(), gbc);

        leftPanel.setPreferredSize(new Dimension(220, leftPanel.getPreferredSize().height));
        return leftPanel;
    }


    // Phương thức tải icon từ đường dẫn và thay đổi kích thước (20x20)
    private ImageIcon loadIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Không thể tải icon: " + path);
            return null;
        }
    }

    public void loadDataToTable(java.util.List<PhieuNhapDTO> danhSach) {
        model.setRowCount(0);
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int stt = 1;
        for (PhieuNhapDTO pn : danhSach) {
            // Lấy tên NV từ BLL hoặc DTO nếu đã có sẵn
            String tenNV = new NhanVienBLL()
                    .getonenv(pn.getNhanVienNhap())
                    .getHoTen();
            String trangThai = switch (pn.getTrangThai()) {
                case 2 -> "Hoàn thành";
                case 3 -> "Hủy";
                default -> "Chờ";
            };
            model.addRow(new Object[]{
                    stt++,
                    pn.getMaPhieuNhap(),
                    tenNV,
                    fmt.format(pn.getNgay()),
                    pn.getTongTien(),
                    trangThai
            });
        }
    }

    public PhieuNhapDTO getById(int maPN) {
        return dao.selectById(maPN);
    }

    public void reloadTable() {
        bll.refresh();
        ArrayList<PhieuNhapDTO> list = bll.getDanhSachPhieuNhap();
        loadDataToTable(list);
    }
}
