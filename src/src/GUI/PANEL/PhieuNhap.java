package GUI.PANEL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class PhieuNhap extends JPanel {

    public PhieuNhap() {
        // Đặt layout cho panel chính của Phieunhap
        setLayout(new BorderLayout());

        // Tạo mainPanel với BorderLayout và thêm khoảng cách giữa các cạnh
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tạo topPanel (thanh công cụ phía trên)
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Tạo leftPanel (bộ lọc tìm kiếm bên trái)
        JPanel leftPanel = createLeftFilterPanel();
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Nếu cần, bạn có thể tạo thêm centerPanel để chứa bảng dữ liệu hoặc các thành phần khác

        // Thêm mainPanel vào Phieunhap (JPanel chính)
        add(mainPanel);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        ImageIcon addIcon = loadIcon("/icon/them.png");
        ImageIcon detailIcon = loadIcon("/icon/chitiet.png");
        ImageIcon cancelIcon = loadIcon("/icon/huyphieu.png");
        ImageIcon exportIcon = loadIcon("/icon/xuatexcel.png");
        ImageIcon refreshIcon = loadIcon("/icon/lammoi.png");

        JButton btnThem = createIconButton("THÊM", addIcon);
        JButton btnChiTiet = createIconButton("CHI TIẾT", detailIcon);
        JButton btnHuyPhieu = createIconButton("HUỶ PHIẾU", cancelIcon);
        JButton btnXuatExcel = createIconButton("XUẤT EXCEL", exportIcon);

        topPanel.add(btnThem);
        topPanel.add(btnChiTiet);
        topPanel.add(btnHuyPhieu);
        topPanel.add(btnXuatExcel);

        topPanel.add(Box.createHorizontalGlue());

        String[] searchOptions = {"Tất cả", "Mã phiếu nhập", "Nhà cung cấp", "Nhân viên nhập"};
        JComboBox<String> cbSearchType = new JComboBox<>(searchOptions);
        topPanel.add(cbSearchType);

        JTextField txtSearch = new JTextField("Nhập nội dung tìm kiếm .....", 20);
        topPanel.add(txtSearch);
        cbSearchType.setPreferredSize(new Dimension(150, 30));
        txtSearch.setPreferredSize(new Dimension(250, 30));

        JButton btnLamMoi = new JButton("Làm mới");
        if (refreshIcon != null) {
            btnLamMoi.setIcon(refreshIcon);
        }
        topPanel.add(btnLamMoi);

        return topPanel;
    }

    private JPanel createLeftFilterPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Bộ lọc tìm kiếm"),
                new EmptyBorder(5, 5, 5, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Nhà cung cấp:"), gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        JComboBox<String> cbNhaCungCap = new JComboBox<>(new String[]{
                "Tất cả", "Công Ty TNHH Thế Giới Di Động", "Công Ty Samsung Việt Nam"
        });
        leftPanel.add(cbNhaCungCap, gbc);
        gbc.weightx = 0;

        gbc.gridy = 2;
        leftPanel.add(new JLabel("Nhân viên nhập:"), gbc);

        gbc.gridy = 3;
        gbc.weightx = 1.0;
        JComboBox<String> cbNhanVien = new JComboBox<>(new String[]{
                "Tất cả", "Vũ Hồng Vĩnh Khang", "Nguyễn Văn Khanh", "Hàn Gia Hào"
        });
        leftPanel.add(cbNhanVien, gbc);
        gbc.weightx = 0;

        gbc.gridy = 4;
        leftPanel.add(new JLabel("Từ ngày:"), gbc);

        gbc.gridy = 5;
        gbc.weightx = 1.0;
        JPanel datePanelTu = new JPanel(new BorderLayout(5, 0));
        datePanelTu.add(new JTextField(), BorderLayout.CENTER);
        leftPanel.add(datePanelTu, gbc);
        gbc.weightx = 0;

        gbc.gridy = 6;
        leftPanel.add(new JLabel("Đến ngày:"), gbc);

        gbc.gridy = 7;
        gbc.weightx = 1.0;
        JPanel datePanelDen = new JPanel(new BorderLayout(5, 0));
        datePanelDen.add(new JTextField(), BorderLayout.CENTER);
        leftPanel.add(datePanelDen, gbc);
        gbc.weightx = 0;

        gbc.gridy = 8;
        leftPanel.add(new JLabel("Từ số tiền (VND):"), gbc);

        gbc.gridy = 9;
        gbc.weightx = 1.0;
        leftPanel.add(new JTextField(), gbc);
        gbc.weightx = 0;

        gbc.gridy = 10;
        leftPanel.add(new JLabel("Đến số tiền (VND):"), gbc);

        gbc.gridy = 11;
        gbc.weightx = 1.0;
        leftPanel.add(new JTextField(), gbc);
        gbc.weightx = 0;

        gbc.gridy = 12;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        leftPanel.add(new JLabel(), gbc);

        leftPanel.setPreferredSize(new Dimension(220, leftPanel.getPreferredSize().height));

        return leftPanel;
    }

    private JButton createIconButton(String text, ImageIcon icon) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
        }
        // Đặt vị trí của text và icon
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }

    // Phương thức tiện ích để làm phẳng nút (nếu cần)
    private void setButtonFlat(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }

    // Phương thức tải icon từ đường dẫn và thay đổi kích thước (20x20)
    private ImageIcon loadIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image = icon.getImage();
            // Resize ảnh với chất lượng cao
            Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Không thể tải icon: " + path);
            return null;
        }
    }

    // Phương thức main tạo JFrame chứa instance của Phieunhap
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Không thể đặt Look and Feel của hệ thống.");
            }

            JFrame frame = new JFrame("Quản Lý Phiếu Nhập");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);
            frame.setLocationRelativeTo(null);

            // Thêm instance của Phieunhap (một JPanel) vào JFrame
            frame.add(new PhieuNhap());
            frame.setVisible(true);
        });
    }
}
