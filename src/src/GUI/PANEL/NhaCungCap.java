package GUI.PANEL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class NhaCungCap extends JPanel {

    public NhaCungCap() {
        // --- Panel chính chứa toàn bộ giao diện ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Panel Top (Chứa các nút chức năng và tìm kiếm) ---
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- Panel Center (Không chứa gì) ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Thêm panel chính vào JPanel này
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // --- Tải Icons ---
        ImageIcon addIcon = loadIcon("/icon/them.png");
        ImageIcon editIcon = loadIcon("/icon/sua.png");
        ImageIcon deleteIcon = loadIcon("/icon/xoa.png");
        ImageIcon detailIcon = loadIcon("/icon/chitiet.png");
        ImageIcon importIcon = loadIcon("/icon/nhapexcel.png");
        ImageIcon exportIcon = loadIcon("/icon/xuatexcel.png");
        ImageIcon refreshIcon = loadIcon("/icon/lammoi.png");

        // --- Tạo các nút chức năng ---
        JButton btnThem = createIconButton("THÊM", addIcon);
        JButton btnSua = createIconButton("SỬA", editIcon);
        JButton btnXoa = createIconButton("XÓA", deleteIcon);
        JButton btnChiTiet = createIconButton("CHI TIẾT", detailIcon);
        JButton btnNhapExcel = createIconButton("NHẬP EXCEL", importIcon);
        JButton btnXuatExcel = createIconButton("XUẤT EXCEL", exportIcon);

        // Thêm các nút vào panel
        topPanel.add(btnThem);
        topPanel.add(btnSua);
        topPanel.add(btnXoa);
        topPanel.add(btnChiTiet);
        topPanel.add(btnNhapExcel);
        topPanel.add(btnXuatExcel);

        topPanel.add(Box.createHorizontalGlue());

        String[] searchOptions = {"Tất cả", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại"};
        JComboBox<String> cbSearchType = new JComboBox<>(searchOptions);
        topPanel.add(cbSearchType);

        JTextField txtSearch = new JTextField("Nhập nội dung tìm kiếm .....", 20);
        topPanel.add(txtSearch);

        JButton btnLamMoi = new JButton("Làm mới");
        if (refreshIcon != null) btnLamMoi.setIcon(refreshIcon);
        topPanel.add(btnLamMoi);

        return topPanel;
    }

    private ImageIcon loadIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Không thể tải icon: " + path);
            return null;
        }
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

    private void setButtonFlat(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Không thể đặt Look and Feel của hệ thống.");
            }

            // Tạo JFrame và thêm JPanel vào
            JFrame frame = new JFrame("Quản Lý Nhà Cung Cấp");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new NhaCungCap());
            frame.setVisible(true);
        });
    }
}
