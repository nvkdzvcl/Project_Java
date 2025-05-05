package GUI.PANEL.ThongKe; // Đảm bảo package đúng

// import BUS.ThongKeBUS; // Thay thế
import BLL.ThongKeBLL;   // Import BLL
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;
// import GUI.Component.TableSorter; // Bỏ
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
// import helper.Formater; // Bỏ
// import helper.JTableExporter; // Bỏ
import java.awt.BorderLayout;
import java.awt.Color;
// import java.awt.Dimension; // Có thể không cần nếu không set size đặc biệt
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
// import java.io.IOException; // Bỏ vì không còn export
import java.text.NumberFormat; // Thêm
import java.text.SimpleDateFormat; // Thêm
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale; // Thêm
// import java.util.logging.Level; // Bỏ nếu không dùng logger nhiều
// import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane; // Giữ lại để báo lỗi
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public final class ThongKeDoanhThuTrongThang extends JPanel {

    JPanel pnl_top;
    // ThongKeBUS thongkeBUS; // Thay thế
    ThongKeBLL thongkeBLL; // Dùng BLL
    JMonthChooser monthchooser;
    JYearChooser yearchooser;
    private JTable tableThongKe;
    private JScrollPane scrollTableThongKe;
    private DefaultTableModel tblModel;
    private JButton btnThongKe;
    // private JButton btnExport; // Bỏ nút Export

    // Định dạng tiền tệ và ngày
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public ThongKeDoanhThuTrongThang(ThongKeBLL thongkeBLL) { // Nhận BLL
        this.thongkeBLL = thongkeBLL;
        initComponent();
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        monthchooser.setMonth(currentMonth);
        yearchooser.setYear(currentYear);
        loadThongKeTungNgayTrongThang(currentMonth + 1, currentYear);
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnl_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblChonThang = new JLabel("Chọn tháng:");
        monthchooser = new JMonthChooser();
        JLabel lblChonNam = new JLabel("Chọn năm:");
        yearchooser = new JYearChooser();
        btnThongKe = new JButton("Thống kê");
        // btnExport = new JButton("Xuất Excel"); // Bỏ

        pnl_top.add(lblChonThang);
        pnl_top.add(monthchooser);
        pnl_top.add(lblChonNam);
        pnl_top.add(yearchooser);
        pnl_top.add(btnThongKe);
        // pnl_top.add(btnExport); // Bỏ

        // Bỏ ActionListener của btnExport

        btnThongKe.addActionListener((ActionEvent e) -> {
            int selectedMonth = monthchooser.getMonth() + 1;
            int selectedYear = yearchooser.getYear();
            loadThongKeTungNgayTrongThang(selectedMonth, selectedYear);
        });

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
        String[] header = new String[]{"Ngày", "Chi phí", "Doanh thu", "Lợi nhuận"};
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

    public void loadThongKeTungNgayTrongThang(int thang, int nam) {
        ArrayList<ThongKeTungNgayTrongThangDTO> list = thongkeBLL.getThongKeTungNgayTrongThang(thang, nam); // Gọi từ BLL

        tblModel.setRowCount(0);
        if (list != null && !list.isEmpty()) {
            for (ThongKeTungNgayTrongThangDTO data : list) {
                tblModel.addRow(new Object[]{
                        data.getNgay() != null ? dateFormatter.format(data.getNgay()) : "",
                        currencyFormatter.format(data.getChiphi()),
                        currencyFormatter.format(data.getDoanhthu()),
                        currencyFormatter.format(data.getLoinhuan())
                });
            }
        } else {
            System.out.println("Không có dữ liệu thống kê cho tháng " + thang + "/" + nam);
            // Có thể thêm JOptionPane.showMessageDialog ở đây nếu muốn
        }
    }
}