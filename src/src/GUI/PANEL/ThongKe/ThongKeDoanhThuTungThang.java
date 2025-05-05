package GUI.PANEL.ThongKe; // Đảm bảo package đúng


import BLL.ThongKeBLL; // Import BLL
import DTO.ThongKe.ThongKeTheoThangDTO;
// import GUI.Component.TableSorter; // Bỏ
import com.toedter.calendar.JYearChooser;
// import helper.Formater; // Bỏ
// import helper.JTableExporter; // Bỏ
import java.awt.BorderLayout;
import java.awt.Color;
// import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
// import java.io.IOException; // Bỏ
import java.text.NumberFormat; // Thêm
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale; // Thêm
// import java.util.logging.Level; // Bỏ
// import java.util.logging.Logger;
import javax.swing.JButton; // Giữ JButton nếu còn nút nào khác, nếu không thì bỏ
import javax.swing.JLabel;
import javax.swing.JOptionPane; // Giữ
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public final class ThongKeDoanhThuTungThang extends JPanel /* implements ActionListener bỏ nếu không còn nút nào */ {

    JPanel pnl_top;
    // ThongKeBUS thongkeBUS; // Thay thế
    ThongKeBLL thongkeBLL; // Dùng BLL
    JYearChooser yearchooser;
    // JButton export; // Bỏ nút Export
    private JTable tableThongKe;
    private JScrollPane scrollTableThongKe;
    private DefaultTableModel tblModel;

    // Định dạng tiền tệ
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public ThongKeDoanhThuTungThang(ThongKeBLL thongkeBLL) { // Nhận BLL
        this.thongkeBLL = thongkeBLL;
        initComponent();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearchooser.setYear(currentYear);
        loadThongKeThang(currentYear);
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnl_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblChonNam = new JLabel("Chọn năm thống kê:");
        yearchooser = new JYearChooser();

        yearchooser.addPropertyChangeListener("year", (PropertyChangeEvent e) -> {
            if ("year".equals(e.getPropertyName())) {
                int selectedYear = (Integer) e.getNewValue();
                loadThongKeThang(selectedYear);
            }
        });

        // export = new JButton("Xuất Excel"); // Bỏ
        // export.addActionListener(this); // Bỏ

        pnl_top.add(lblChonNam);
        pnl_top.add(yearchooser);
        // pnl_top.add(export); // Bỏ

        tableThongKe = new JTable();
        scrollTableThongKe = new JScrollPane();
        tblModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            // @Override // (Tùy chọn)
            // public Class<?> getColumnClass(int columnIndex) { ... }
        };
        String[] header = new String[]{"Tháng", "Chi phí", "Doanh thu", "Lợi nhuận"};
        tblModel.setColumnIdentifiers(header);
        tableThongKe.setModel(tblModel);
        tableThongKe.setAutoCreateRowSorter(true); // Kích hoạt sắp xếp
        scrollTableThongKe.setViewportView(tableThongKe);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        // tableThongKe.setDefaultRenderer(Object.class, centerRenderer); // Áp dụng chung
        for (int i = 0; i < tableThongKe.getColumnCount(); i++) {
            tableThongKe.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tableThongKe.setFocusable(false);

        // Bỏ cấu hình TableSorter

        this.add(pnl_top, BorderLayout.NORTH);
        this.add(scrollTableThongKe, BorderLayout.CENTER);
    }

    public void loadThongKeThang(int nam) {
        ArrayList<ThongKeTheoThangDTO> list = thongkeBLL.getThongKeTheoThang(nam); // Gọi từ BLL

        tblModel.setRowCount(0);
        if (list != null && !list.isEmpty()) {
            // Đảm bảo list có đủ 12 tháng hoặc thêm logic để hiển thị tháng đúng
            if (list.size() < 12) {
                System.out.println("Cảnh báo: Dữ liệu trả về không đủ 12 tháng cho năm " + nam);
                // Có thể tạo các dòng trống cho các tháng thiếu nếu muốn
            }
            // Giả sử list trả về theo thứ tự tháng
            for (int i = 0; i < list.size(); i++) { // Chỉ lặp qua dữ liệu có
                ThongKeTheoThangDTO data = list.get(i);
                // Giả định data.getThang() trả về số tháng (1-12)
                tblModel.addRow(new Object[]{
                        "Tháng " + data.getThang(), // Lấy tháng từ DTO nếu có
                        currencyFormatter.format(data.getChiphi()),
                        currencyFormatter.format(data.getDoanhthu()),
                        currencyFormatter.format(data.getLoinhuan())
                });
            }
            // Hoặc nếu DAO luôn trả về 12 tháng (kể cả tháng 0đ)
            // for (int i = 0; i < list.size(); i++) {
            //    ThongKeTheoThangDTO data = list.get(i);
            //     tblModel.addRow(new Object[]{
            //         "Tháng " + (i + 1), // Hoặc data.getThang()
            //         currencyFormatter.format(data.getChiphi()),
            //         currencyFormatter.format(data.getDoanhthu()),
            //         currencyFormatter.format(data.getLoinhuan())
            //     });
            // }

        } else {
            System.out.println("Không có dữ liệu thống kê cho năm " + nam);
        }
    }


}