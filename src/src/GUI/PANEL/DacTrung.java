package GUI.PANEL;
import GUI.Main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DacTrung extends JPanel {

    // Định nghĩa màu sắc và font chữ để dễ dàng thay đổi
    private static final Color PANEL_BACKGROUND = new Color(240, 248, 255); // Màu xanh dương rất nhạt (AliceBlue)
    private static final Color PANEL_BORDER_COLOR = new Color(225, 235, 245); // Màu viền nhạt hơn
    private static final Color PANEL_HOVER_BACKGROUND = new Color(210, 230, 250); // Màu nền khi di chuột qua
    private static final Color TEXT_COLOR = new Color(60, 60, 60); // Màu chữ xám đậm
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 18);
    private static final Dimension ICON_SIZE = new Dimension(50, 50); // Kích thước mong muốn cho icon

    public DacTrung() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2, 25, 25));
        setBackground(Color.WHITE); // Màu nền cho khu vực chứa lưới
        // Thêm padding lớn xung quanh lưới các ô thuộc tính
        setBorder(new EmptyBorder(5, 5, 5, 5)); // Top, Left, Bottom, Right

        // Thêm các panel thuộc tính vào lưới
        // ** QUAN TRỌNG: Thay thế các đường dẫn icon bên dưới bằng đường dẫn thực tế của bạn **
        add(createAttributePanel("../../icon/dactrungsanpham.png", "Loại sản phẩm"));
        add(createAttributePanel("../../icon/gioitinh.png", "Giới tính"));
        add(createAttributePanel("../../icon/size.png", "Size"));
        add(createAttributePanel("../../icon/mau.png", "Màu sắc"));
        add(createAttributePanel("../../icon/chatlieu.png", "Chất liệu"));
        add(createAttributePanel("../../icon/thuonghieu.png", "Thương hiệu")); // Giả sử là icon chip cho Rom
        add(createAttributePanel("../../icon/xuatxu.png", "Xuất xứ"));
    }

    private JPanel createAttributePanel(String iconPath, String labelText) {
        // Mỗi ô thuộc tính dùng BorderLayout để đặt icon (WEST) và text (CENTER)
        JPanel panel = new JPanel(new BorderLayout(5, 0)); // Khoảng cách 20px giữa icon và text
        panel.setBackground(PANEL_BACKGROUND);
        // Tạo viền kết hợp: viền ngoài mỏng và padding bên trong
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PANEL_BORDER_COLOR, 1), // Viền ngoài 1px
                new EmptyBorder(5, 5, 5, 5) // Padding bên trong (Top, Left, Bottom, Right)
        ));

        // --- Label chứa Icon ---
        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(ICON_SIZE); // Đặt kích thước ưa thích cho label chứa icon
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            // *** THAY THẾ BẰNG CÁCH TẢI ICON CỦA BẠN ***
            // Ví dụ:
             ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath)); // Tải từ resource
             if (originalIcon.getIconWidth() > 0) { // Kiểm tra xem icon có tải được không
                 Image scaledImage = originalIcon.getImage().getScaledInstance(ICON_SIZE.width, ICON_SIZE.height, Image.SCALE_SMOOTH);
                 iconLabel.setIcon(new ImageIcon(scaledImage));
             } else {
                  iconLabel.setText("[X]"); // Hiển thị lỗi nếu không tải được
             }
        } catch (Exception e) {
            iconLabel.setText("ERR"); // Lỗi tải icon
            iconLabel.setOpaque(true);
            iconLabel.setBackground(Color.PINK);
            System.err.println("Không thể tải icon: " + iconPath + " - " + e.getMessage());
        }


        // --- Label chứa Text ---
        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(LABEL_FONT);
        textLabel.setForeground(TEXT_COLOR);
        textLabel.setHorizontalAlignment(SwingConstants.LEFT); // Căn trái text
        textLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Thêm icon vào bên trái (WEST), text vào giữa (CENTER)
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textLabel, BorderLayout.CENTER);

        // --- Thêm hiệu ứng và sự kiện nhấp chuột ---
        panel.addMouseListener(new MouseAdapter() {
            private final Color originalBackground = panel.getBackground();
            private final Border originalBorder = panel.getBorder();
            private final Border hoverBorder = BorderFactory.createCompoundBorder(
                    new LineBorder(Color.BLUE, 1), // Viền xanh khi hover/click
                    new EmptyBorder(25, 30, 25, 30));

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(PANEL_HOVER_BACKGROUND);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(originalBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Khôi phục lại trạng thái hover nếu chuột vẫn ở trên panel
                if (panel.contains(e.getPoint())) {
                    mouseEntered(e); // Gọi lại mouseEntered để có màu hover
                    panel.setBorder(originalBorder); // Khôi phục viền gốc sau khi nhả chuột
                } else {
                    mouseExited(e); // Nếu chuột đã ra ngoài thì gọi mouseExited
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Hiệu ứng khi nhấn chuột xuống (tùy chọn)
                panel.setBackground(originalBackground.darker()); // Làm tối màu nền một chút
                panel.setBorder(hoverBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // === HÀNH ĐỘNG KHI NHẤP VÀO Ô THUỘC TÍNH ===
                System.out.println("Đã nhấp vào: " + labelText);
                // Ví dụ: Mở cửa sổ quản lý cho thuộc tính này
                // openAttributeManagementWindow(labelText);
                // ===========================================
            }
        });

        return panel;
    }

    // Phương thức ví dụ để mở cửa sổ mới (bạn cần tự định nghĩa lớp này)
    // private void openAttributeManagementWindow(String attributeName) {
    //     JFrame attributeFrame = new JFrame("Quản lý " + attributeName);
    //     // Thêm nội dung vào attributeFrame...
    //     attributeFrame.setSize(500, 400);
    //     attributeFrame.setLocationRelativeTo(this); // Hiển thị gần panel chính
    //     attributeFrame.setVisible(true);
    // }


    // --- Hàm main để chạy thử nghiệm giao diện này ---
    public static void main(String[] args) {
        // Đảm bảo rằng code Swing chạy trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Thử đặt Look and Feel của hệ thống để giao diện đẹp hơn (tùy chọn)
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Không thể đặt Look and Feel hệ thống.");
            }

            JFrame frame = new JFrame("Kiểm tra Giao diện Thuộc tính");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(850, 650); // Kích thước cửa sổ ví dụ

            // ---- TẠO SIDEBAR GIẢ LẬP (THAY BẰNG SIDEBAR CỦA BẠN) ----
            JPanel sidebarPlaceholder = new JPanel();
            sidebarPlaceholder.setPreferredSize(new Dimension(200, 600));
            sidebarPlaceholder.setBackground(new Color(245, 245, 245)); // Màu xám nhạt
            sidebarPlaceholder.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY)); // Viền phải mỏng
            sidebarPlaceholder.add(new JLabel("Sidebar của bạn"));
            // ---- KẾT THÚC SIDEBAR GIẢ LẬP ----


            // Sử dụng JSplitPane để kết hợp Sidebar và Content Panel
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setLeftComponent(sidebarPlaceholder); // Đặt sidebar của bạn vào đây
            splitPane.setRightComponent(new DacTrung()); // Đặt panel thuộc tính mới tạo vào đây
            splitPane.setDividerLocation(210); // Vị trí ban đầu của thanh chia
            splitPane.setDividerSize(1);      // Kích thước thanh chia (0 hoặc 1 để ẩn/mỏng)
            splitPane.setEnabled(false);      // Vô hiệu hóa việc kéo thanh chia (tùy chọn)
            splitPane.setBorder(null);        // Bỏ viền của JSplitPane

            frame.add(splitPane);
            frame.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
            frame.setVisible(true);
        });
    }
}