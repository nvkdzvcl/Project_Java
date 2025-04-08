package GUI.PANEL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TrangChu extends JPanel {

    public TrangChu() {
        initComponents();
    }

    private void initComponents() {
        // Sử dụng BorderLayout cho panel chính này
        setLayout(new BorderLayout(10, 20)); // Khoảng cách ngang 10, dọc 20
        setBackground(Color.WHITE);
        // Thêm padding xung quanh toàn bộ nội dung
        setBorder(new EmptyBorder(20, 30, 20, 30)); // Top, Left, Bottom, Right

        // --- Phần tiêu đề (NORTH) ---
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // --- Phần nội dung chính giữa (CENTER) ---
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Tạo panel chứa các tiêu đề ở phía trên
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        // Dùng BoxLayout để xếp các thành phần theo chiều dọc (Y_AXIS)
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE); // Nền trắng giống panel cha
        panel.setOpaque(true); // Có thể đặt trong suốt nếu cần

        // Tiêu đề chính
        JLabel mainTitle = new JLabel("HỆ THỐNG QUẢN LÝ KHO ĐIỆN THOẠI THEO MÃ IMEI");
        //mainTitle.setFont(new Font("Arial", Font.BOLD, 24));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa
        mainTitle.setForeground(new Color(0, 102, 204)); // Màu xanh dương

        // Dòng chữ nhỏ dưới tiêu đề
        JLabel subTitle = new JLabel("- Hãy hướng về phía mặt trời , nơi mà bóng tối luôn ở phía sau lưng bạn . Điều mà hoa hướng dương vẫn làm mỗi ngày.-");
        //subTitle.setFont(new Font("Arial", Font.ITALIC, 12));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa
        subTitle.setForeground(Color.DARK_GRAY);

        // Tên tập đoàn
        JLabel companyLabel = new JLabel("Tập đoàn AnBachSi");
        //companyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        companyLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa
        companyLabel.setForeground(Color.GRAY);

        // Thêm các thành phần vào panel top
        panel.add(mainTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng trống nhỏ
        panel.add(subTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));  // Khoảng trống nhỏ
        panel.add(companyLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25))); // Khoảng trống lớn hơn bên dưới

        return panel;
    }

    /**
     * Tạo panel chứa 3 hộp thông tin ở giữa
     */
    private JPanel createCenterPanel() {
        // Sử dụng GridLayout với 1 hàng, 3 cột, khoảng cách ngang 30px
        JPanel panel = new JPanel(new GridLayout(1, 3, 30, 0));
        panel.setBackground(Color.WHITE); // Nền trắng
        panel.setOpaque(false);

        // Tạo và thêm 3 hộp thông tin
        panel.add(createInfoBox(
                "/path/to/your/icon1.png", // <-- THAY ĐỔI ĐƯỜNG DẪN ICON CỦA BẠN
                "TÍNH CHÍNH XÁC",
                "Mã IMEI là một số duy nhất được gán cho từng thiết bị điện thoại, do đó hệ thống quản lý điện thoại theo mã IMEI sẽ đảm bảo tính chính xác và độ tin cậy cao."
        ));
        panel.add(createInfoBox(
                "/path/to/your/icon2.png", // <-- THAY ĐỔI ĐƯỜNG DẪN ICON CỦA BẠN
                "TÍNH BẢO MẬT",
                "Ngăn chặn việc sử dụng các thiết bị điện thoại giả mạo hoặc bị đánh cắp. Điều này giúp tăng tính bảo mật cho các hoạt động quản lý điện thoại."
        ));
        panel.add(createInfoBox(
                "/path/to/your/icon3.png", // <-- THAY ĐỔI ĐƯỜNG DẪN ICON CỦA BẠN
                "TÍNH HIỆU QUẢ",
                "Dễ dàng xác định được thông tin về từng thiết bị điện thoại một cách nhanh chóng và chính xác, giúp cho việc quản lý điện thoại được thực hiện một cách hiệu quả hơn."
        ));

        return panel;
    }

    private JPanel createInfoBox(String iconPath, String title, String description) {
        JPanel boxPanel = new JPanel();
        // Dùng BoxLayout để xếp các thành phần trong hộp theo chiều dọc
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        // Thêm viền và padding cho hộp
        boxPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220)), // Viền màu xám nhạt
                new EmptyBorder(20, 20, 20, 20)      // Padding bên trong hộp
        ));
        // Đặt màu nền hơi xám nhạt cho hộp
        boxPanel.setBackground(new Color(248, 249, 250));

        // --- Icon ---
        JLabel iconLabel = new JLabel();
        try {
            // Cố gắng tải icon từ đường dẫn
            // Thay thế bằng cách tải tài nguyên phù hợp trong ứng dụng của bạn
            // Ví dụ: ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            // Nếu không có icon, dùng placeholder:
            iconLabel.setText("[ICON]"); // Placeholder nếu không tải được icon
            //iconLabel.setFont(new Font("Arial", Font.BOLD, 48));
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            // Đặt kích thước ưa thích cho icon placeholder
            iconLabel.setPreferredSize(new Dimension(80, 80));
            iconLabel.setMinimumSize(new Dimension(80, 80));
            iconLabel.setMaximumSize(new Dimension(100, 100));
        } catch (Exception e) {
            iconLabel.setText("[ICON]"); // Fallback text
            System.err.println("Không thể tải icon: " + iconPath + " - " + e.getMessage());
            iconLabel.setPreferredSize(new Dimension(80, 80));
        }
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa icon


        // --- Tiêu đề của hộp ---
        JLabel titleLabel = new JLabel(title);
        //titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa
        titleLabel.setForeground(new Color(52, 58, 64)); // Màu xám đậm


        // --- Nội dung mô tả ---
        // Sử dụng JTextArea để tự động xuống dòng
        JTextArea descriptionArea = new JTextArea(description);
        //descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);       // Tự động xuống dòng
        descriptionArea.setWrapStyleWord(true);  // Ngắt dòng theo từ
        descriptionArea.setEditable(false);      // Không cho phép chỉnh sửa
        descriptionArea.setFocusable(false);
        descriptionArea.setOpaque(false);        // Làm cho nền trong suốt
        descriptionArea.setBackground(new Color(0,0,0,0)); // Nền trong suốt hoàn toàn
        descriptionArea.setForeground(Color.DARK_GRAY); // Màu chữ xám đậm

        // Căn giữa cho JTextArea khó hơn, có thể dùng JLabel với HTML nếu muốn căn giữa chính xác
        // Hoặc giới hạn kích thước để BoxLayout căn giữa tốt hơn
        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Đặt kích thước tối đa để quản lý layout tốt hơn
        descriptionArea.setMaximumSize(new Dimension(200, Integer.MAX_VALUE)); // Điều chỉnh chiều rộng nếu cần


        // Thêm các thành phần vào hộp panel
        boxPanel.add(iconLabel);
        boxPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Khoảng cách sau icon
        boxPanel.add(titleLabel);
        boxPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách sau tiêu đề
        boxPanel.add(descriptionArea);

        return boxPanel;
    }

    // --- Hàm main để chạy thử nghiệm giao diện này ---
    public static void main(String[] args) {
        // Đảm bảo rằng code Swing chạy trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kiểm tra Giao diện Chính");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 650); // Kích thước cửa sổ ví dụ

            // ---- TẠO SIDEBAR GIẢ LẬP (THAY BẰNG SIDEBAR CỦA BẠN) ----
            JPanel sidebarPlaceholder = new JPanel();
            sidebarPlaceholder.setPreferredSize(new Dimension(220, 600));
            sidebarPlaceholder.setBackground(new Color(52, 73, 94)); // Màu nền ví dụ cho sidebar
            sidebarPlaceholder.add(new JLabel("<html><font color='white'>Đây là Sidebar</font></html>"));
            // ---- KẾT THÚC SIDEBAR GIẢ LẬP ----


            // Sử dụng JSplitPane để kết hợp Sidebar và Content Panel
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setLeftComponent(sidebarPlaceholder); // Đặt sidebar của bạn vào đây
            splitPane.setRightComponent(new TrangChu()); // Đặt panel nội dung mới tạo vào đây
            splitPane.setDividerLocation(230); // Vị trí ban đầu của thanh chia
            splitPane.setDividerSize(1);      // Kích thước thanh chia (đặt 0 hoặc 1 để ẩn/mỏng)
            splitPane.setEnabled(false);      // Vô hiệu hóa việc kéo thanh chia (tùy chọn)
            splitPane.setBorder(null);        // Bỏ viền của JSplitPane

            // Thêm splitPane vào frame
            frame.add(splitPane);
            // Hoặc chỉ thêm TrangChu nếu không test với sidebar
            // frame.add(new TrangChu());

            frame.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
            frame.setVisible(true);
        });
    }
}
