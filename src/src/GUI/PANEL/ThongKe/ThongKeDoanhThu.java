package GUI.PANEL.ThongKe; // Đảm bảo package đúng

// import BUS.ThongKeBUS; // Thay thế
import BLL.ThongKeBLL;   // Import BLL
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Panel chứa các tab con cho thống kê doanh thu.
 * Sử dụng ThongKeBLL.
 * @author GeminiAI Refactored
 */
public class ThongKeDoanhThu extends JPanel {

    JTabbedPane tabbedPane;
    // Đảm bảo các lớp panel con cũng được sửa đổi để nhận BLL
    ThongKeDoanhThuTrongThang thongketrongthang;
    ThongKeDoanhThuTungNam thongketungnam;
    ThongKeDoanhThuTungThang thongkedoanhthutungthang;
    ThongKeDoanhThuTuNgayDenNgay thongkedoanhthutungaydenngay;

    Color BackgroundColor = new Color(240, 247, 250);
    ThongKeBLL thongkeBLL; // Dùng BLL

    public ThongKeDoanhThu(ThongKeBLL thongkeBLL) { // Nhận BLL
        this.thongkeBLL = thongkeBLL;
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(BackgroundColor);

        // Khởi tạo các panel con và truyền BLL
        thongketrongthang = new ThongKeDoanhThuTrongThang(thongkeBLL);
        thongketungnam = new ThongKeDoanhThuTungNam(thongkeBLL);
        thongkedoanhthutungthang = new ThongKeDoanhThuTungThang(thongkeBLL);
        thongkedoanhthutungaydenngay = new ThongKeDoanhThuTuNgayDenNgay(thongkeBLL);

        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);

        tabbedPane.addTab("Theo năm", thongketungnam);
        tabbedPane.addTab("Từng tháng trong năm", thongkedoanhthutungthang);
        tabbedPane.addTab("Từng ngày trong tháng", thongketrongthang);
        tabbedPane.addTab("Từ ngày đến ngày", thongkedoanhthutungaydenngay);

        this.add(tabbedPane);
    }
}