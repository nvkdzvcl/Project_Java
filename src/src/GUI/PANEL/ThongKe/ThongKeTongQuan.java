package GUI.PANEL.ThongKe; // Đảm bảo package đúng


import BLL.ThongKeBLL;   // Import BLL
import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;
// import GUI.Component.TableSorter; // Bỏ
// import GUI.Component.itemTaskbar; // Bỏ
// import helper.Formater; // Bỏ
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout; // Thêm nếu cần cho panel tóm tắt
import java.awt.Font;
import java.text.NumberFormat; // Dùng để định dạng tiền tệ
import java.text.SimpleDateFormat; // Dùng để định dạng ngày
import java.util.ArrayList;
import java.util.Locale; // Dùng cho định dạng tiền tệ VN
import javax.swing.BorderFactory; // Thêm để tạo border
import javax.swing.Box; // Thêm để tạo khoảng cách nếu dùng BoxLayout
import javax.swing.BoxLayout; // Thêm nếu dùng BoxLayout cho panel tóm tắt
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
// import javax.swing.table.TableRowSorter; // Có thể cần nếu muốn tùy chỉnh sorter nhiều hơn
// import javax.swing.table.TableModel;


public class ThongKeTongQuan extends JPanel {

    // ThongKeBUS thongkebus; // Thay thế
    ThongKeBLL thongkeBLL; // Dùng BLL
    JPanel jp_top; // Panel chứa các thông tin tóm tắt
    // itemTaskbar[] listitem; // Bỏ
    JLabel lblSoKhachHang; // Label hiển thị số KH
    JLabel lblSoNhanVien;  // Label hiển thị số NV

    private JTable tableThongKe;
    private JScrollPane scrollTableThongKe;
    private DefaultTableModel tblModel;
    ArrayList<ThongKeTungNgayTrongThangDTO> dataset;

    // Định dạng tiền tệ VNĐ
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    // Định dạng ngày dd/MM/yyyy
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public ThongKeTongQuan(ThongKeBLL thongkeBLL) { // Nhận BLL
        this.thongkeBLL = thongkeBLL;
        this.dataset = thongkeBLL.getThongKe7NgayGanNhat(); // Gọi phương thức từ BLL
        initComponent();
        loadDataTalbe(this.dataset);
        updateSummaryInfo(); // Cập nhật thông tin KH, NV ban đầu
    }


    // Cập nhật thông tin tóm tắt (số KH, số NV)
    private void updateSummaryInfo() {
        try {
            // Sửa tên phương thức thành getallkhachhang()
            int soKH = KhachHangDAO.getInstance().getallkhachhang().size(); //
            lblSoKhachHang.setText(String.valueOf(soKH));
        } catch (Exception e) {
            lblSoKhachHang.setText("Lỗi");
            // Ghi log lỗi nếu cần
            e.printStackTrace(); // Nên in lỗi ra để dễ debug
        }
        try {
            // Tạo đối tượng NhanVienDAO và gọi phương thức getallnhanvien()
            NhanVienDAO nvDAO = new NhanVienDAO(); // Tạo instance mới
            int soNV = nvDAO.getallnhanvien().size(); // Gọi phương thức trên instance và sửa tên phương thức
            lblSoNhanVien.setText(String.valueOf(soNV));
        } catch (Exception e) {
            lblSoNhanVien.setText("Lỗi");
            // Ghi log lỗi nếu cần
            e.printStackTrace(); // Nên in lỗi ra để dễ debug
        }
    }

    // Hàm load dữ liệu vào bảng (sử dụng định dạng)
    public void loadDataTalbe(ArrayList<ThongKeTungNgayTrongThangDTO> list) {
        tblModel.setRowCount(0);
        if (list != null) {
            for (ThongKeTungNgayTrongThangDTO i : list) {
                tblModel.addRow(new Object[]{
                        i.getNgay() != null ? dateFormatter.format(i.getNgay()) : "", // Định dạng ngày
                        currencyFormatter.format(i.getChiphi()), // Định dạng chi phí
                        currencyFormatter.format(i.getDoanhthu()), // Định dạng doanh thu
                        currencyFormatter.format(i.getLoinhuan()) // Định dạng lợi nhuận
                        // Lưu ý: Việc thêm chuỗi đã định dạng có thể ảnh hưởng sắp xếp
                        // Để sắp xếp tốt hơn, nên thêm dữ liệu gốc (Date, long) vào model
                        // và dùng TableCellRenderer để định dạng hiển thị.
                });
            }
        }
    }

    private void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(true); // Đặt lại thành true nếu cần nền trắng
        this.setBackground(Color.WHITE); // Đặt nền trắng
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel Top chứa thông tin tóm tắt
        jp_top = new JPanel();
        // Dùng GridLayout hoặc FlowLayout tùy ý
        jp_top.setLayout(new GridLayout(1, 2, 20, 0)); // 1 hàng, 2 cột (KH, NV)
        jp_top.setOpaque(false); // Để nền của panel cha hiển thị
        jp_top.setBorder(new EmptyBorder(0, 0, 10, 0));
        // jp_top.setPreferredSize(new Dimension(0, 80)); // Giảm chiều cao nếu cần

        // Tạo panel con cho Khách hàng
        JPanel khPanel = createSummaryPanel("Khách hàng");
        lblSoKhachHang = new JLabel("0", JLabel.CENTER); // Label số lượng
        lblSoKhachHang.setFont(lblSoKhachHang.getFont().deriveFont(Font.BOLD, 18f)); // Font lớn hơn
        khPanel.add(lblSoKhachHang, BorderLayout.CENTER);
        jp_top.add(khPanel);

        // Tạo panel con cho Nhân viên
        JPanel nvPanel = createSummaryPanel("Nhân viên");
        lblSoNhanVien = new JLabel("0", JLabel.CENTER); // Label số lượng
        lblSoNhanVien.setFont(lblSoNhanVien.getFont().deriveFont(Font.BOLD, 18f));
        nvPanel.add(lblSoNhanVien, BorderLayout.CENTER);
        jp_top.add(nvPanel);


        // Khởi tạo bảng dữ liệu
        tableThongKe = new JTable();
        scrollTableThongKe = new JScrollPane();
        tblModel = new DefaultTableModel() {
            // Ghi đè isCellEditable để không cho sửa trực tiếp
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            // (Tùy chọn) Ghi đè getColumnClass để sorter biết kiểu dữ liệu gốc
            // @Override
            // public Class<?> getColumnClass(int columnIndex) {
            //     switch (columnIndex) {
            //         case 0: return String.class; // Hoặc Date.class nếu lưu Date
            //         case 1: case 2: case 3: return String.class; // Hoặc Long.class nếu lưu Long
            //         default: return Object.class;
            //     }
            // }
        };
        String[] header = new String[]{"Ngày", "Chi phí", "Doanh thu", "Lợi nhuận"};
        tblModel.setColumnIdentifiers(header);
        tableThongKe.setModel(tblModel);

        // Kích hoạt sắp xếp mặc định
        tableThongKe.setAutoCreateRowSorter(true);

        // Không cho sửa trực tiếp (đã xử lý trong new DefaultTableModel())
        // tableThongKe.setDefaultEditor(Object.class, null);

        scrollTableThongKe.setViewportView(tableThongKe);

        // Căn giữa nội dung ô (vẫn giữ)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        // Áp dụng cho tất cả các cột hoặc chỉ cột cụ thể nếu muốn
        for (int i = 0; i < tableThongKe.getColumnCount(); i++) {
            tableThongKe.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // tableThongKe.setDefaultRenderer(Object.class, centerRenderer); // Áp dụng cho tất cả nếu không có renderer cụ thể khác
        tableThongKe.setFocusable(false);

        // Bỏ cấu hình TableSorter
        // TableSorter.configureTableColumnSorter(...);

        // Bố cục: Top panel ở trên, bảng dữ liệu ở giữa
        this.add(jp_top, BorderLayout.NORTH);
        this.add(scrollTableThongKe, BorderLayout.CENTER);
    }

    // Hàm tiện ích tạo panel tóm tắt đơn giản
    private JPanel createSummaryPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), // Viền ngoài
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding trong
        ));
        panel.setBackground(Color.WHITE); // Nền trắng cho panel tóm tắt
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }

    // Hàm làm mới dữ liệu (cập nhật cả phần tóm tắt)
    public void refreshData() {
        updateSummaryInfo(); // Cập nhật số KH, NV
        this.dataset = thongkeBLL.getThongKe7NgayGanNhat(); // Lấy lại dữ liệu từ BLL
        loadDataTalbe(this.dataset);
    }
}