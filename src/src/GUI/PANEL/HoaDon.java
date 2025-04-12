package GUI.PANEL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class HoaDon extends JPanel {

    public HoaDon() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel leftPanel = createLeftFilterPanel();
        mainPanel.add(leftPanel, BorderLayout.WEST);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // --- Panel trái chứa các nút chức năng ---
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnThem = createIconButton("THÊM", resizeIcon(loadIcon("/icon/them.png")));
        JButton btnChiTiet = createIconButton("CHI TIẾT", resizeIcon(loadIcon("/icon/chitiet.png")));
        JButton btnHuyPhieu = createIconButton("HUỶ PHIẾU", resizeIcon(loadIcon("/icon/huyphieu.png")));
        JButton btnXuatExcel = createIconButton("XUẤT EXCEL", resizeIcon(loadIcon("/icon/xuatexcel.png")));

        for (JButton btn : new JButton[]{btnThem, btnChiTiet, btnHuyPhieu, btnXuatExcel}) {
            leftPanel.add(btn);
        }

        // --- Panel phải chứa tìm kiếm ---
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        String[] searchOptions = {"Tất cả", "Mã Phiếu", "Khách hàng", "Nhân viên xuất"};
        JComboBox<String> cbSearchType = new JComboBox<>(searchOptions);
        cbSearchType.setPreferredSize(new Dimension(150, 40));
        JTextField txtSearch = new JTextField(20);
        txtSearch.setPreferredSize(new Dimension(200, 40));

        JButton btnLamMoi = createIconButton("LÀM MỚI", resizeIcon(loadIcon("/icon/lammoi.png")));

        rightPanel.add(cbSearchType);
        rightPanel.add(txtSearch);
        rightPanel.add(btnLamMoi);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        return panel;
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
        leftPanel.add(new JLabel("Khách hàng:"), gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        leftPanel.add(new JComboBox<>(new String[]{
                "Tất cả", "Công Ty TNHH Thế Giới Di Động", "Công Ty Samsung Việt Nam"
        }), gbc);
        gbc.weightx = 0;

        gbc.gridy++;
        leftPanel.add(new JLabel("Nhân viên xuất:"), gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        leftPanel.add(new JComboBox<>(new String[]{
                "Tất cả", "Vũ Hồng Vĩnh Khang", "Nguyễn Văn Khanh", "Hàn Gia Hào"
        }), gbc);
        gbc.weightx = 0;

        gbc.gridy++;
        leftPanel.add(new JLabel("Từ ngày:"), gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        leftPanel.add(new JTextField(), gbc);
        gbc.weightx = 0;

        gbc.gridy++;
        leftPanel.add(new JLabel("Đến ngày:"), gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        leftPanel.add(new JTextField(), gbc);
        gbc.weightx = 0;

        gbc.gridy++;
        leftPanel.add(new JLabel("Từ số tiền (VND):"), gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        leftPanel.add(new JTextField(), gbc);
        gbc.weightx = 0;

        gbc.gridy++;
        leftPanel.add(new JLabel("Đến số tiền (VND):"), gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        leftPanel.add(new JTextField(), gbc);

        gbc.weightx = 0;
        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        leftPanel.add(new JLabel(), gbc);

//        leftPanel.setPreferredSize(new Dimension(220, leftPanel.getPreferredSize().height));
        leftPanel.setPreferredSize(new Dimension(220, 0)); // hoặc có thể bỏ luôn

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

    private ImageIcon resizeIcon(ImageIcon icon) {
        if (icon == null) return null;
        Image image = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Không thể đặt Look and Feel của hệ thống.");
            }

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new HoaDon());
            frame.setVisible(true);
        });
    }
}
