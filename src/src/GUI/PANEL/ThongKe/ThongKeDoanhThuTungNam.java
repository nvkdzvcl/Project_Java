package GUI.PANEL.ThongKe; // Đảm bảo package đúng


import BLL.ThongKeBLL; // Import BLL
import DTO.ThongKe.ThongKeDoanhThuDTO;
// import GUI.Component.NumericDocumentFilter; // Bỏ
// import GUI.Component.TableSorter; // Bỏ
// import helper.Formater; // Bỏ
// import helper.JTableExporter; // Bỏ
// import helper.Validation; // Bỏ
import java.awt.BorderLayout;
import java.awt.Color;
// import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.io.IOException; // Bỏ
import java.text.NumberFormat; // Thêm
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale; // Thêm
// import java.util.logging.Level; // Bỏ
// import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane; // Giữ
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
// import javax.swing.text.PlainDocument; // Bỏ


public final class ThongKeDoanhThuTungNam extends JPanel implements ActionListener {

    JPanel pnl_top;
    // ThongKeBUS thongkeBUS; // Thay thế
    ThongKeBLL thongkeBLL; // Dùng BLL
    JTextField txtYearStart, txtYearEnd;
    JButton btnReset, btnThongKe;
    // JButton btnExport; // Bỏ nút Export

    private JTable tableThongKe;
    private JScrollPane scrollTableThongKe;
    private DefaultTableModel tblModel;
    private ArrayList<ThongKeDoanhThuDTO> dataset;
    private final int currentYear;

    // Định dạng tiền tệ
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public ThongKeDoanhThuTungNam(ThongKeBLL thongkeBLL) { // Nhận BLL
        this.thongkeBLL = thongkeBLL;
        this.currentYear = LocalDate.now().getYear();
        initComponent();
        int defaultStartYear = Math.max(2015, currentYear - 4) ;
        txtYearStart.setText(String.valueOf(defaultStartYear));
        txtYearEnd.setText(String.valueOf(currentYear));
        loadData(defaultStartYear, currentYear);
    }

    private void loadData(int startYear, int endYear) {
        this.dataset = this.thongkeBLL.getDoanhThuTheoTungNam(startYear, endYear); // Gọi từ BLL
        loadDataTable(this.dataset);
    }

    public void loadDataTable(ArrayList<ThongKeDoanhThuDTO> list) {
        tblModel.setRowCount(0);
        if (list != null && !list.isEmpty()) {
            for (ThongKeDoanhThuDTO data : list) {
                tblModel.addRow(new Object[]{
                        data.getThoigian(), // Năm là số nguyên, không cần định dạng đặc biệt
                        currencyFormatter.format(data.getVon()),
                        currencyFormatter.format(data.getDoanhthu()),
                        currencyFormatter.format(data.getLoinhuan())
                });
            }
        } else {
            System.out.println("Không có dữ liệu thống kê cho khoảng năm đã chọn.");
        }
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        pnl_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblYearStart = new JLabel("Từ năm:");
        JLabel lblYearEnd = new JLabel("Đến năm:");

        txtYearStart = new JTextField(5);
        txtYearEnd = new JTextField(5);

        // Bỏ áp dụng NumericDocumentFilter

        btnThongKe = new JButton("Thống kê");
        btnReset = new JButton("Làm mới");
        // btnExport = new JButton("Xuất Excel"); // Bỏ

        btnThongKe.addActionListener(this);
        btnReset.addActionListener(this);
        // btnExport.addActionListener(this); // Bỏ

        pnl_top.add(lblYearStart);
        pnl_top.add(txtYearStart);
        pnl_top.add(lblYearEnd);
        pnl_top.add(txtYearEnd);
        pnl_top.add(btnThongKe);
        pnl_top.add(btnReset);
        // pnl_top.add(btnExport); // Bỏ

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
        String[] header = new String[]{"Năm", "Vốn", "Doanh thu", "Lợi nhuận"};
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnThongKe) {
            String startYearStr = txtYearStart.getText().trim(); // Dùng trim()
            String endYearStr = txtYearEnd.getText().trim();   // Dùng trim()

            // Thay thế Validation.isEmpty bằng kiểm tra chuỗi rỗng tiêu chuẩn
            if (startYearStr.isEmpty() || endYearStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ năm bắt đầu và năm kết thúc.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Kiểm tra NumberFormatException khi parse
                int startYear = Integer.parseInt(startYearStr);
                int endYear = Integer.parseInt(endYearStr);

                // Logic validate năm giữ nguyên
                if (startYear > currentYear || endYear > currentYear) {
                    JOptionPane.showMessageDialog(this, "Năm bắt đầu và kết thúc không được lớn hơn năm hiện tại (" + currentYear + ").", "Năm không hợp lệ", JOptionPane.ERROR_MESSAGE);
                } else if (startYear < 2015 || endYear < 2015) {
                    JOptionPane.showMessageDialog(this, "Năm thống kê phải từ 2015 trở đi.", "Năm không hợp lệ", JOptionPane.ERROR_MESSAGE);
                } else if (endYear < startYear) {
                    JOptionPane.showMessageDialog(this, "Năm kết thúc phải lớn hơn hoặc bằng năm bắt đầu.", "Năm không hợp lệ", JOptionPane.ERROR_MESSAGE);
                } else {
                    loadData(startYear, endYear); // Load dữ liệu nếu hợp lệ
                }
            } catch (NumberFormatException ex) {
                // Bắt lỗi nếu người dùng nhập không phải số
                JOptionPane.showMessageDialog(this, "Định dạng năm không hợp lệ. Vui lòng chỉ nhập số.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }

        } else if (source == btnReset) {
            int defaultStartYear = Math.max(2015, currentYear - 4) ;
            txtYearStart.setText(String.valueOf(defaultStartYear));
            txtYearEnd.setText(String.valueOf(currentYear));
            loadData(defaultStartYear, currentYear);
        }

    }
}