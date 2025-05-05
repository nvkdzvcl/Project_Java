package GUI.PANEL.ThongKe; // Đảm bảo package đúng

// import BUS.ThongKeBUS; // Thay thế bằng BLL
import BLL.ThongKeBLL; // Import lớp BLL
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public final class ThongKe extends JPanel {

    JTabbedPane tabbedPane;
    JPanel tongquan;
    JPanel doanhthu;

    Color BackgroundColor = new Color(240, 247, 250);
    // Đổi BUS thành BLL
    ThongKeBLL thongkeBLL = new ThongKeBLL(); // Khởi tạo BLL

    public ThongKe() {
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(BackgroundColor);

        // Khởi tạo các panel và truyền BLL vào
        // Đảm bảo các lớp panel con cũng nhận ThongKeBLL
        tongquan = new ThongKeTongQuan(thongkeBLL);
        doanhthu = new ThongKeDoanhThu(thongkeBLL);

        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);

        tabbedPane.addTab("Tổng quan", tongquan);
        tabbedPane.addTab("Doanh thu", doanhthu);

        this.add(tabbedPane);
    }
}