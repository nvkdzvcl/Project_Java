package GUI.PANEL.ThongKe; // Đảm bảo package đúng


import BLL.ThongKeBLL;   // Import BLL
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;
// import GUI.Component.TableSorter; // Bỏ
import com.toedter.calendar.JDateChooser;
// import helper.Formater; // Bỏ
// import helper.JTableExporter; // Bỏ
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
// import java.io.IOException; // Bỏ
import java.text.NumberFormat; // Thêm
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale; // Thêm
// import java.util.logging.Level; // Bỏ
// import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane; // Giữ
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;


public final class ThongKeDoanhThuTuNgayDenNgay extends JPanel {

    JPanel pnl_top;
    // ThongKeBUS thongkeBUS; // Thay thế
    ThongKeBLL thongkeBLL; // Dùng BLL

    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JButton btnThongKe, btnReset;
    // private JButton btnExport; // Bỏ nút Export
    private JTable tableThongKe;
    private JScrollPane scrollTableThongKe;
    private DefaultTableModel tblModel;

    // Định dạng tiền tệ và ngày
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    // Định dạng ngày gửi đi BLL/DAO (nếu cần)
    private final SimpleDateFormat dbDateFormatter = new SimpleDateFormat("yyyy-MM-dd");


    public ThongKeDoanhThuTuNgayDenNgay(ThongKeBLL thongkeBLL) { // Nhận BLL
        this.thongkeBLL = thongkeBLL;
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnl_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFrom = new JLabel("Từ ngày:");
        dateFrom = new JDateChooser();
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setPreferredSize(new Dimension(120, dateFrom.getPreferredSize().height));

        JLabel lblTo = new JLabel("Đến ngày:");
        dateTo = new JDateChooser();
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setPreferredSize(new Dimension(120, dateTo.getPreferredSize().height));

        btnThongKe = new JButton("Thống kê");
        btnReset = new JButton("Làm mới");
        // btnExport = new JButton("Xuất Excel"); // Bỏ

        pnl_top.add(lblFrom);
        pnl_top.add(dateFrom);
        pnl_top.add(lblTo);
        pnl_top.add(dateTo);
        pnl_top.add(btnThongKe);
        pnl_top.add(btnReset);
        // pnl_top.add(btnExport); // Bỏ

        // Bỏ ActionListener của btnExport

        // Listener validate ngày khi chọn có thể giữ lại
        dateFrom.addPropertyChangeListener("date", (PropertyChangeEvent e) -> {
            if ("date".equals(e.getPropertyName())) {
                try {
                    validateSelectDate();
                } catch (ParseException ex) { /* Bỏ qua lỗi ở đây */ }
            }
        });

        dateTo.addPropertyChangeListener("date", (PropertyChangeEvent e) -> {
            if ("date".equals(e.getPropertyName())) {
                try {
                    validateSelectDate();
                } catch (ParseException ex) { /* Bỏ qua lỗi ở đây */ }
            }
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

        btnThongKe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (validateSelectDate()) { // Validate trước khi lấy dữ liệu
                        Date fromDate = dateFrom.getDate();
                        Date toDate = dateTo.getDate();

                        if (fromDate != null && toDate != null) {
                            // Gọi hàm load dữ liệu với đối tượng Date
                            loadThongKeTheoKhoangNgay(fromDate, toDate);
                        } else {
                            JOptionPane.showMessageDialog(ThongKeDoanhThuTuNgayDenNgay.this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (ParseException ex) {
                    // Lỗi này ít khi xảy ra nếu validateSelectDate đúng
                    JOptionPane.showMessageDialog(ThongKeDoanhThuTuNgayDenNgay.this, "Có lỗi xảy ra trong quá trình xử lý ngày.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateFrom.setDate(null);
                dateTo.setDate(null);
                tblModel.setRowCount(0);
            }
        });
    }

    // Hàm validate giữ nguyên logic
    public boolean validateSelectDate() throws ParseException {
        Date time_start = dateFrom.getDate();
        Date time_end = dateTo.getDate();
        Date current_date = new Date(); // Ngày hiện tại

        // Chỉ lấy phần ngày, bỏ qua giờ phút giây để so sánh chính xác
        Calendar cal = Calendar.getInstance();
        cal.setTime(current_date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();

        if (time_start != null) {
            cal.setTime(time_start);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            time_start = cal.getTime();
            if (time_start.after(today)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại.", "Lỗi chọn ngày", JOptionPane.ERROR_MESSAGE);
                dateFrom.setDate(null);
                return false;
            }
        }

        if (time_end != null) {
            cal.setTime(time_end);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            time_end = cal.getTime();
            if (time_end.after(today)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại.", "Lỗi chọn ngày", JOptionPane.ERROR_MESSAGE);
                dateTo.setDate(null);
                return false;
            }
        }


        if (time_start != null && time_end != null && time_start.after(time_end)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu.", "Lỗi chọn ngày", JOptionPane.ERROR_MESSAGE);
            dateTo.setDate(null);
            return false;
        }
        return true;
    }

    // Hàm load dữ liệu (nhận Date, gọi BLL)
    public void loadThongKeTheoKhoangNgay(Date start, Date end) {
        // Gọi BLL với đối tượng Date
        ArrayList<ThongKeTungNgayTrongThangDTO> list = thongkeBLL.getThongKeTuNgayDenNgay(start, end);

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
            System.out.println("Không có dữ liệu thống kê cho khoảng ngày đã chọn.");
            JOptionPane.showMessageDialog(this,"Không tìm thấy dữ liệu thống kê cho khoảng thời gian đã chọn.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}