// File: ThongKeBLL.java (Đổi tên từ ThongKeBLL.java)
package BLL; // Đổi package thành BLL

import DAO.ThongKeDAO;
import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeTheoThangDTO;
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;

import java.util.ArrayList;
import java.util.Date; // Sử dụng java.util.Date


public class ThongKeBLL { // Đổi tên lớp

    // Sử dụng instance của ThongKeDAO cho các phương thức non-static
    private final ThongKeDAO thongkeDAO;

    /**
     * Constructor khởi tạo đối tượng ThongKeDAO.
     */
    public ThongKeBLL() {
        thongkeDAO = new ThongKeDAO(); // Khởi tạo DAO
    }

    // ==================== Thống kê Doanh thu/Chi phí theo thời gian ====================

    /**
     * Lấy thống kê doanh thu/chi phí theo từng năm.
     * @param year_start Năm bắt đầu.
     * @param year_end Năm kết thúc.
     * @return ArrayList<ThongKeDoanhThuDTO>
     */
    public ArrayList<ThongKeDoanhThuDTO> getDoanhThuTheoTungNam(int year_start, int year_end) {
        // Gọi phương thức non-static của instance DAO
        return this.thongkeDAO.getDoanhThuTheoTungNam(year_start, year_end);
    }

    /**
     * Lấy thống kê doanh thu/chi phí theo từng tháng trong một năm.
     * @param nam Năm cần thống kê.
     * @return ArrayList<ThongKeTheoThangDTO>
     */
    public ArrayList<ThongKeTheoThangDTO> getThongKeTheoThang(int nam) {
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKeTheoThang(nam);
    }

    /**
     * Lấy thống kê doanh thu/chi phí theo từng ngày trong một tháng.
     * @param thang Tháng cần thống kê.
     * @param nam Năm cần thống kê.
     * @return ArrayList<ThongKeTungNgayTrongThangDTO>
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTungNgayTrongThang(int thang, int nam) {
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKeTungNgayTrongThang(thang, nam);
    }

    /**
     * Lấy thống kê doanh thu/chi phí trong một khoảng ngày cụ thể.
     * @param start Ngày bắt đầu (java.util.Date).
     * @param end Ngày kết thúc (java.util.Date).
     * @return ArrayList<ThongKeTungNgayTrongThangDTO>
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTuNgayDenNgay(Date start, Date end) {
        // Thêm kiểm tra null cho start/end nếu cần
        if (start == null || end == null) {
            // Hoặc trả về list rỗng, hoặc throw exception tùy logic mong muốn
            return new ArrayList<>();
        }
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKeTuNgayDenNgay(start, end);
    }

    /**
     * Lấy thống kê doanh thu/chi phí trong một khoảng ngày cụ thể (dùng cho GUI cũ nếu nó truyền String).
     * Cần đảm bảo GUI mới truyền java.util.Date. Nếu không, cần thêm hàm này
     * hoặc sửa GUI để truyền Date.
     *
     * @param start Ngày bắt đầu (String yyyy-MM-dd).
     * @param end Ngày kết thúc (String yyyy-MM-dd).
     * @return ArrayList<ThongKeTungNgayTrongThangDTO>
     */
    // public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTuNgayDenNgay(String start, String end) {
    //     try {
    //         java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
    //         Date startDate = formatter.parse(start);
    //         Date endDate = formatter.parse(end);
    //         return thongkeDAO.getThongKeTuNgayDenNgay(startDate, endDate);
    //     } catch (java.text.ParseException e) {
    //         // Xử lý lỗi parse ngày nếu cần
    //         Logger.getLogger(ThongKeBLL.class.getName()).log(Level.SEVERE, "Error parsing date strings", e);
    //         return new ArrayList<>(); // Trả về rỗng nếu lỗi
    //     }
    // }


    /**
     * Lấy thống kê doanh thu/chi phí của 7 ngày gần nhất.
     * @return ArrayList<ThongKeTungNgayTrongThangDTO>
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKe7NgayGanNhat() {
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKe7NgayGanNhat();
    }
}