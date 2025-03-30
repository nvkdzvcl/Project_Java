package GUI;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public Main() {
        setTitle("Hệ thống quản lý kho hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel menu bên trái
        JPanel menuPanel = createMenuPanel();

        // Panel hiển thị nội dung
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Thêm các màn hình vào contentPanel
        contentPanel.add(new JLabel("Trang chủ"), "trangchu");
        contentPanel.add(new JLabel("Sản phẩm"), "sanpham");
        contentPanel.add(new JLabel("Đặc trưng sản phẩm"), "dactrungsanpham");
        contentPanel.add(new JLabel("Khu vực kho"), "khuvuckho");
        contentPanel.add(new JLabel("Phiếu nhập"), "phieunhap");
        contentPanel.add(new JLabel("Phiếu xuất"), "phieuxuat");
        contentPanel.add(new JLabel("Khách hàng"), "khachhang");
        contentPanel.add(new JLabel("Nhà cung cấp"), "nhacungcap");
        contentPanel.add(new JLabel("Nhân viên"), "nhanvien");
        contentPanel.add(new JLabel("Tài khoản"), "taikhoan");
        contentPanel.add(new JLabel("Thống kê"), "thongke");
        contentPanel.add(new JLabel("Phân quyền"), "phanquyen");
        contentPanel.add(new JLabel("Đăng xuất"), "dangxuat");

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, getHeight()));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));

        JLabel userLabel = new JLabel("👤 Nguyễn Văn Khanh");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(userLabel);

        // Các nút menu với icon (cứ thay đường dẫn icon của bạn vào nhé)
        panel.add(createMenuButton("Trang chủ", "trangchu", "/icon/trangchu.png"));
        panel.add(createMenuButton("Sản phẩm", "sanpham", "/icon/sanpham.png"));
        panel.add(createMenuButton("Đặc trưng", "dactrungsanpham", "/icon/dactrungsanpham.png"));
        panel.add(createMenuButton("Khu vực kho", "khuvuckho", "/icon/khuvuckho.png"));
        panel.add(createMenuButton("Phiếu nhập", "phieunhap", "/icon/phieunhap.png"));
        panel.add(createMenuButton("Phiếu xuất", "phieuxuat", "/icon/phieuxuat.png"));
        panel.add(createMenuButton("Khách hàng", "khachhang", "/icon/khachhang.png"));
        panel.add(createMenuButton("Nhà cung cấp", "nhacungcap", "/icon/nhacungcap.png"));
        panel.add(createMenuButton("Nhân viên", "nhanvien", "/icon/nhanvien.png"));
        panel.add(createMenuButton("Tài khoản", "taikhoan", "/icon/taikhoan.png"));
        panel.add(createMenuButton("Thống kê", "thongke", "/icon/thongke.png"));
        panel.add(createMenuButton("Phân quyền", "phanquyen", "/icon/phanquyen.png"));
        panel.add(createMenuButton("Đăng xuất", "dangxuat", "/icon/dangxuat.png"));

        return panel;
    }

    private JButton createMenuButton(String title, String cardName, String iconPath) {
        Icon icon;
        java.net.URL imgURL = getClass().getResource(iconPath);
        if (imgURL != null) {
            // Resize icon (20x20)
            Image img = new ImageIcon(imgURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } else {
            System.err.println("Không tìm thấy icon: " + iconPath);
            icon = UIManager.getIcon("OptionPane.informationIcon");  // Icon mặc định
        }

        JButton btn = new JButton(title, icon);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(10);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> cardLayout.show(contentPanel, cardName));
        return btn;
    }



    public static void main(String[] args) {
        new Main();
    }
}
