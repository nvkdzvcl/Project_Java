package GUI.PANEL;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Vector;

public class KhuVuc extends JPanel {

    private JTable warehouseTable;
    private DefaultTableModel tableModel;
    private JPanel productListPanel; // Panel để chứa danh sách sản phẩm bên phải
    private JScrollPane productScrollPane; // ScrollPane cho danh sách sản phẩm
    private JLabel productListTitle; // Tiêu đề cho danh sách sản phẩm

    // Màu sắc và Font chữ
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255); // AliceBlue nhẹ
    private static final Color HEADER_BACKGROUND = new Color(228, 241, 254);
    private static final Color BUTTON_COLOR = new Color(245, 245, 245);
    private static final Color BORDER_COLOR = new Color(210, 220, 230);
    private static final Font FONT_GENERAL = new Font("Arial", Font.PLAIN, 13);
    private static final Font FONT_BOLD = new Font("Arial", Font.BOLD, 13);
    private static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 14);
    private static final Font FONT_BUTTON = new Font("Arial", Font.BOLD, 11);

    public KhuVuc() {
        initComponents();
        loadInitialData(); // Tải dữ liệu mẫu ban đầu
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 15)); // Layout chính với khoảng cách
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 20, 15, 20)); // Padding cho toàn panel

        // --- Thanh công cụ và tìm kiếm (NORTH) ---
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // --- Nội dung chính (CENTER) - Sử dụng JSplitPane ---
        JSplitPane mainSplitPane = createMainContentPanel();
        add(mainSplitPane, BorderLayout.CENTER);

        // Thêm listener cho bảng để cập nhật danh sách sản phẩm khi chọn hàng
        warehouseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && warehouseTable.getSelectedRow() != -1) {
                int selectedRow = warehouseTable.getSelectedRow();
                // Lấy ID hoặc tên của khu vực kho từ hàng được chọn
                Object warehouseIdentifier = tableModel.getValueAt(selectedRow, 0); // Giả sử cột 0 là ID/Mã
                updateProductList(warehouseIdentifier.toString());
            } else if (warehouseTable.getSelectedRow() == -1) {
                clearProductList(); // Xóa danh sách sản phẩm nếu không có hàng nào được chọn
            }
        });
    }

    /**
     * Tạo thanh công cụ và tìm kiếm ở phía trên
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        topPanel.setOpaque(false); // Nền trong suốt để thấy nền của panel cha

        // -- Panel chứa các nút chức năng (Trái) --
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(createActionButton("THÊM", "../../icon/them.png"));
        buttonPanel.add(createActionButton("SỬA", "../../icon/sua.png"));
        buttonPanel.add(createActionButton("XÓA", "../../icon/xoa.png"));
        buttonPanel.add(createActionButton("NHẬP EXCEL", "../../icon/nhapexcel.png"));
        buttonPanel.add(createActionButton("XUẤT EXCEL", "../../icon/xuatexcel.png"));

        // -- Panel chứa các thành phần tìm kiếm (Phải) --
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        searchPanel.setOpaque(false);

        JComboBox<String> searchCategory = new JComboBox<>(new String[]{"Tất cả", "Mã kho", "Tên khu vực"});
        searchCategory.setFont(FONT_GENERAL);
        searchCategory.setFocusable(false);

        JTextField searchField = new JTextField(20); // Độ rộng gợi ý 20 ký tự
        searchField.setFont(FONT_GENERAL);
        searchField.setFocusable(false);
        // Placeholder text (cần custom hoặc dùng thư viện hỗ trợ)
        //searchField.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm...");

        JButton refreshButton = new JButton("Làm mới");
        refreshButton.setFont(FONT_GENERAL);
        refreshButton.setFocusable(false);
        // refreshButton.setIcon(new ImageIcon(getClass().getResource("/path/to/refresh_icon.png"))); // Thêm icon
        refreshButton.addActionListener(e -> loadInitialData()); // Action làm mới

        searchPanel.add(searchCategory);
        searchPanel.add(searchField);
        searchPanel.add(refreshButton);

        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JButton createActionButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBackground(BUTTON_COLOR);
        button.setOpaque(true);
        button.setFocusable(false);
        button.setFocusPainted(false);
        //button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(5, 15, 5, 15) // Padding bên trong nút
        ));

        // --- Tải Icon (thay thế bằng cách tải thực tế) ---
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image scaledImg = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImg));
            // Placeholder icon:
            //button.setIcon(createPlaceholderIcon(20, 20)); // Icon giả lập
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            //button.setIcon(createPlaceholderIcon(20, 20));
        }
        // --- --------------------------------------- ---

        // Thêm action listener (hiện tại chỉ in ra console)
        //button.addActionListener(e -> System.out.println("Button clicked: " + text));

        return button;
    }

    /**
     * Tạo phần nội dung chính gồm bảng và danh sách sản phẩm dùng JSplitPane
     */
    private JSplitPane createMainContentPanel() {
        // --- Panel chứa bảng (Bên trái) ---
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Tạo bảng
        tableModel = new DefaultTableModel(new Object[]{"Mã kho", "Tên khu vực", "Ghi chú"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép sửa trực tiếp trên bảng
            }
        };
        warehouseTable = new JTable(tableModel);
        warehouseTable.setFont(FONT_GENERAL);
        warehouseTable.setRowHeight(28); // Chiều cao hàng
        warehouseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Chỉ chọn 1 hàng
        warehouseTable.setGridColor(new Color(220, 220, 220)); // Màu đường kẻ
        warehouseTable.setShowGrid(true); // Hiển thị đường kẻ ngang dọc
        warehouseTable.setIntercellSpacing(new Dimension(0, 1)); // Khoảng cách giữa các ô (chỉ dọc)

        // Tùy chỉnh Header của bảng
        JTableHeader header = warehouseTable.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(HEADER_BACKGROUND);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false); // Không cho kéo thả cột
        header.setPreferredSize(new Dimension(header.getWidth(), 32)); // Chiều cao header

        // Đặt bảng vào JScrollPane
        JScrollPane tableScrollPane = new JScrollPane(warehouseTable);
        tableScrollPane.setBorder(new LineBorder(BORDER_COLOR));
        tableScrollPane.getViewport().setBackground(Color.WHITE); // Nền viewport
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // --- Panel chứa danh sách sản phẩm (Bên phải) ---
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10)); // Khoảng cách dọc
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Padding bên trái

        // Tiêu đề
        productListTitle = new JLabel("Chọn một khu vực để xem sản phẩm");
        productListTitle.setFont(FONT_TITLE);
        productListTitle.setBorder(new EmptyBorder(5, 5, 5, 5));
        rightPanel.add(productListTitle, BorderLayout.NORTH);

        // Panel chứa các item sản phẩm (sẽ được đặt trong ScrollPane)
        productListPanel = new JPanel();
        // Dùng BoxLayout để xếp sản phẩm dọc xuống
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        productListPanel.setBackground(BACKGROUND_COLOR); // Nền cho danh sách
        productListPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding bên trong

        // Đặt panel sản phẩm vào JScrollPane
        productScrollPane = new JScrollPane(productListPanel);
        productScrollPane.setBorder(new LineBorder(BORDER_COLOR));
        productScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        productScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        productScrollPane.getViewport().setBackground(BACKGROUND_COLOR); // Nền viewport

        rightPanel.add(productScrollPane, BorderLayout.CENTER);

        // --- Tạo JSplitPane ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, rightPanel);
        splitPane.setDividerLocation(0.65); // Vị trí thanh chia, 65% cho bảng
        splitPane.setResizeWeight(0.65);    // Ưu tiên thay đổi kích thước cho bảng
        splitPane.setDividerSize(8);       // Kích thước thanh chia
        splitPane.setBorder(null);         // Bỏ viền của splitpane
        //splitPane.setOneTouchExpandable(true); // Cho phép nút thu gọn/mở rộng nhanh

        return splitPane;
    }

    /**
     * Tải dữ liệu mẫu vào bảng
     */
    private void loadInitialData() {
        // Xóa dữ liệu cũ
        tableModel.setRowCount(0);
        clearProductList(); // Xóa cả danh sách sản phẩm
        // Thêm dữ liệu mẫu
        String[][] sampleData = {
                {"1", "Khu vực A", "Apple"},
                {"2", "Khu vực B", "Xiaomi"},
                {"3", "Khu vực C", "Samsung"},
                {"4", "Khu vực D", "Realme"},
                {"5", "Khu vực E", "Oppo"}
        };
        for (String[] row : sampleData) {
            tableModel.addRow(row);
        }
        // Bỏ chọn hàng (nếu có)
        warehouseTable.clearSelection();
        productListTitle.setText("Chọn một khu vực kho để xem sản phẩm");

    }

    private void updateProductList(String warehouseIdentifier) {
        clearProductList(); // Xóa danh sách cũ trước khi thêm mới
        productListTitle.setText("Danh sách sản phẩm đang có tại khu vực: " + warehouseIdentifier); // Cập nhật tiêu đề

        // --- Lấy dữ liệu sản phẩm TƯƠNG ỨNG VỚI warehouseIdentifier (Đây là phần giả lập) ---
        // Trong ứng dụng thực tế, bạn sẽ truy vấn CSDL hoặc API tại đây
        Vector<ProductData> products = getSampleProductsForWarehouse(warehouseIdentifier);
        // --- ------------------------------------------------------------------------ ---

        if (products.isEmpty()) {
            JLabel emptyLabel = new JLabel("Không có sản phẩm nào trong khu vực này.");
            emptyLabel.setFont(FONT_GENERAL);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            productListPanel.add(emptyLabel);
        } else {
            for (ProductData product : products) {
                productListPanel.add(createProductItemPanel(product));
                productListPanel.add(Box.createRigidArea(new Dimension(0, 8))); // Khoảng cách giữa các sản phẩm
            }
        }

        // Refresh lại giao diện của panel sản phẩm
        productListPanel.revalidate();
        productListPanel.repaint();
        // Cuộn lên đầu danh sách
        SwingUtilities.invokeLater(() -> productScrollPane.getVerticalScrollBar().setValue(0));
    }

    /**
     * Xóa tất cả sản phẩm khỏi danh sách bên phải.
     */
    private void clearProductList() {
        productListPanel.removeAll();
        productListPanel.revalidate();
        productListPanel.repaint();
        productListTitle.setText("Danh sách sản phẩm"); // Reset tiêu đề
    }


    /**
     * Lấy dữ liệu sản phẩm mẫu cho khu vực kho (GIẢ LẬP)
     */
    private Vector<ProductData> getSampleProductsForWarehouse(String warehouseId) {
        Vector<ProductData> products = new Vector<>();
        switch (warehouseId) {
            case "1": // Khu vực A
                products.add(new ProductData("/path/to/iphone.png", "iPhone 14 Pro", 15));
                products.add(new ProductData("/path/to/macbook.png", "Macbook Air M2", 8));
                break;
            case "2": // Khu vực B
                products.add(new ProductData("/path/to/redmi_note_12.png", "Xiaomi Redmi Note 12", 22));
                break;
            case "3": // Khu vực C
                products.add(new ProductData("/path/to/s20fe.png", "Samsung Galaxy S20 FE", 6));
                products.add(new ProductData("/path/to/s22plus.png", "Samsung Galaxy S22+ 5G", 20));
                products.add(new ProductData("/path/to/zfold.png", "Samsung Galaxy Z Fold 4", 5));
                break;
            case "4": // Khu vực D
                products.add(new ProductData("/path/to/realme_gt.png", "Realme GT Neo 3", 12));
                break;
            case "5": // Khu vực E
                products.add(new ProductData("/path/to/oppo_reno6.png", "OPPO Reno6 Pro 5G", 7));
                products.add(new ProductData("/path/to/find_x5.png", "OPPO Find X5 Pro", 9));
                break;
            default:
                // Không có sản phẩm cho các khu vực khác trong ví dụ này
                break;
        }
        return products;
    }

    private JPanel createProductItemPanel(ProductData product) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 0)); // Layout cho item: Ảnh trái, Text phải
        itemPanel.setBackground(Color.WHITE); // Nền trắng cho từng item
        itemPanel.setBorder(new EmptyBorder(5, 8, 5, 8)); // Padding bên trong item
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65)); // Giới hạn chiều cao tối đa
        itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái trong BoxLayout

        // --- Ảnh sản phẩm ---
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(50, 50)); // Kích thước ảnh
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(new LineBorder(Color.LIGHT_GRAY)); // Viền ảnh
        try {
            // ImageIcon icon = new ImageIcon(getClass().getResource(product.imagePath));
            // Image scaledImg = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            // imageLabel.setIcon(new ImageIcon(scaledImg));
            // Placeholder:
            imageLabel.setText("IMG");
            imageLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            imageLabel.setOpaque(true);
            imageLabel.setBackground(Color.LIGHT_GRAY);
        } catch (Exception e) {
            imageLabel.setText("N/A");
            System.err.println("Product image not found: " + product.imagePath);
        }
        itemPanel.add(imageLabel, BorderLayout.WEST);

        // --- Text thông tin sản phẩm ---
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Xếp dọc tên và số lượng
        textPanel.setOpaque(false); // Nền trong suốt

        JLabel nameLabel = new JLabel(product.name);
        nameLabel.setFont(FONT_BOLD);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel quantityLabel = new JLabel("Số lượng: " + product.quantity);
        quantityLabel.setFont(FONT_GENERAL);
        quantityLabel.setForeground(Color.DARK_GRAY);
        quantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(nameLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 3))); // Khoảng cách nhỏ
        textPanel.add(quantityLabel);

        itemPanel.add(textPanel, BorderLayout.CENTER);

        return itemPanel;
    }

    // Lớp nội bộ để lưu trữ dữ liệu sản phẩm mẫu
    private static class ProductData {
        String imagePath;
        String name;
        int quantity;

        ProductData(String imagePath, String name, int quantity) {
            this.imagePath = imagePath;
            this.name = name;
            this.quantity = quantity;
        }
    }


    // --- Hàm main để chạy thử nghiệm giao diện này ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Không thể đặt Look and Feel.");
            }

            JFrame frame = new JFrame("Kiểm tra Giao diện Khu vực Kho");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 700); // Kích thước cửa sổ lớn hơn

            // ---- SIDEBAR GIẢ LẬP ----
            JPanel sidebarPlaceholder = new JPanel();
            sidebarPlaceholder.setPreferredSize(new Dimension(180, 600));
            sidebarPlaceholder.setBackground(new Color(235, 245, 255)); // Màu xanh rất nhạt
            sidebarPlaceholder.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
            sidebarPlaceholder.add(new JLabel("Sidebar"));
            // ---- KẾT THÚC SIDEBAR GIẢ LẬP ----


            // Sử dụng BorderLayout cho Frame chính
            frame.setLayout(new BorderLayout());
            frame.add(sidebarPlaceholder, BorderLayout.WEST); // Sidebar bên trái
            frame.add(new KhuVuc(), BorderLayout.CENTER); // Panel chính ở giữa

            frame.setLocationRelativeTo(null); // Hiển thị giữa màn hình
            frame.setVisible(true);
        });
    }
}
