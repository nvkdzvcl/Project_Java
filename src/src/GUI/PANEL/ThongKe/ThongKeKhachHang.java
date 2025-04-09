package GUI.PANEL.ThongKe;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ThongKeKhachHang extends JPanel {

    public ThongKeKhachHang() {
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

        String[] columns = {"STT", "Mã KH", "Tên khách hàng", "Số lượng hóa đơn", "Tổng số tiền"};
        Object[][] data = {
                {1, "KH001", "Nguyễn Văn Khanh", 1, 4000000},
                {2, "KH002", "Nguyễn Văn Khanh", 2, 4000000},
                {3, "KH003", "Nguyễn Văn Khanh", 3, 4000000},
                {4, "KH004", "Nguyễn Văn Khanh", 1, 4000000},
                {5, "KH005", "Nguyễn Văn Khanh", 4, 4000000},
                {6, "KH006", "Nguyễn Văn Khanh", 2, 4000000},
                {7, "KH007", "Nguyễn Văn Khanh", 2, 4000000},
                {8, "KH008", "Nguyễn Văn Khanh", 2, 4000000},
                {9, "KH009", "Nguyễn Văn Khanh", 4, 4000000},
                {10, "KH010", "Nguyễn Văn Khanh", 3, 4000000},
                {11, "KH011", "Nguyễn Văn Khanh", 1, 4000000},
                {12, "KH012", "Nguyễn Văn Khanh", 1, 4000000},

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
        frame.add(new ThongKeKhachHang());
        frame.setVisible(true);
    }
}
