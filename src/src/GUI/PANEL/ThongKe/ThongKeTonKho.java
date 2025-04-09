package GUI.PANEL.ThongKe;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ThongKeTonKho extends JPanel {

    public ThongKeTonKho() {
        setLayout(new BorderLayout());

        // Tạo các thành phần cho giao diện
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel searchLabel = new JLabel("Tìm kiếm sản phẩm:");
        JTextField searchField = new JTextField(15);
        JButton exportButton = new JButton("Xuất Excel");
        JButton refreshButton = new JButton("Làm mới");

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(exportButton);
        topPanel.add(refreshButton);

        // Tạo bảng để hiển thị dữ liệu
        String[] columns = {"STT", "Mã SP", "Tên sản phẩm", "Số lượng nhập", "Số lượng tồn", "Đã bán"};
        Object[][] data = {
                {1, "A001", "Aó len đỏ", 16, 11, 5},
                {2, "A002", "Aó len đỏ", 16, 11, 5},
                {3, "A003", "Aó len đỏ", 16, 11, 5},
                {4, "A004", "Aó len đỏ", 16, 11, 5},
                {5, "A005", "Aó len đỏ", 16, 11, 5},
                {6, "A006", "Aó len đỏ", 16, 11, 5},
                {7, "A007", "Aó len đỏ", 16, 11, 5},
                {8, "A008", "Aó len đỏ", 16, 11, 5},
                {9, "A009", "Aó len đỏ", 16, 11, 5},
                {10, "A010", "Aó len đỏ", 16, 11, 5},
                {11, "A011", "Aó len đỏ", 16, 11, 5},
                {12, "A012", "Aó len đỏ", 16, 11, 5},

        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        // Thêm các thành phần vào panel
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Khởi tạo JFrame để hiển thị ThongKe
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.add(new ThongKeTonKho());
        frame.setVisible(true);
    }
}
