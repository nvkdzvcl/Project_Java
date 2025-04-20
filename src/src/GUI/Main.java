package GUI;

import javax.swing.*;
import java.awt.*;

import GUI.PANEL.*;
import GUI.PANEL.ThongKe.*;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private String currentUser;
    private JLabel userLabel;   // đưa ra thành field để cập nhật

    // Constructor chính, nhận username
    public Main(String username) {
        this.currentUser = username;
        initUI();
    }

    // Không cần constructor không tham số nữa, hoặc chỉ để test
    public Main() {
        this("Khách");  // gọi qua constructor chính với tên mặc định
    }

    // Tất cả logic khởi tạo giao diện gom vào đây
    private void initUI() {
        setTitle("Hệ thống quản lý cửa hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menu bên trái
        JPanel menuPanel = createMenuPanel();

        // Content chính dùng CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Thêm các card vào
        contentPanel.add(new TrangChu(),    "trangchu");
        contentPanel.add(new SanPham(),     "sanpham");
        contentPanel.add(new PhieuNhap(),   "phieunhap");
        contentPanel.add(new HoaDon(),      "hoadon");
        contentPanel.add(new KhachHang(),   "khachhang");
        contentPanel.add(new NhanVien(),    "nhanvien");
        contentPanel.add(new TaiKhoan(),    "taikhoan");
        contentPanel.add(new JLabel("Thống kê"), "thongke");
        contentPanel.add(new JLabel("Đăng xuất"),"dangxuat");

        add(menuPanel,    BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Hiển thị window
        setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, getHeight()));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));

        // Label hiển thị user
        userLabel = new JLabel("👤 " + currentUser);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(userLabel);

        // Các nút menu
        panel.add(createMenuButton("Trang chủ",   "trangchu",   "/icon/trangchu.png"));
        panel.add(createMenuButton("Sản phẩm",    "sanpham",    "/icon/sanpham.png"));
        panel.add(createMenuButton("Phiếu nhập",  "phieunhap",  "/icon/phieunhap.png"));
        panel.add(createMenuButton("Hóa đơn",     "hoadon",     "/icon/hoadon.png"));
        panel.add(createMenuButton("Khách hàng",  "khachhang",  "/icon/khachhang.png"));
        panel.add(createMenuButton("Nhân viên",   "nhanvien",   "/icon/nhanvien.png"));
        panel.add(createMenuButton("Tài khoản",   "taikhoan",   "/icon/taikhoan.png"));
        panel.add(createMenuButton("Thống kê",    "thongke",    "/icon/thongke.png"));
        panel.add(createMenuButton("Đăng xuất",   "dangxuat",   "/icon/dangxuat.png"));

        return panel;
    }

    private JButton createMenuButton(String title, String cardName, String iconPath) {
        Icon icon;
        java.net.URL imgURL = getClass().getResource(iconPath);
        if (imgURL != null) {
            Image img = new ImageIcon(imgURL).getImage()
                    .getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } else {
            icon = UIManager.getIcon("OptionPane.informationIcon");
        }

        JButton btn = new JButton(title, icon);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setIconTextGap(10);
        btn.setMaximumSize(new Dimension(180, 60));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(240, 248, 255));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            if ("dangxuat".equals(cardName)) {
                dispose();             // đóng Main
                new Login().setVisible(true); // quay về Login
            } else {
                cardLayout.show(contentPanel, cardName);
            }
        });
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(182, 232, 243));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(240, 248, 255));
            }
        });

        return btn;
    }
}
