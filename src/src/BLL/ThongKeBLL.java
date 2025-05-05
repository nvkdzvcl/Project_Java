// File: ThongKeBLL.java (Đã xóa phần liên quan đến Thống kê Tồn Kho và Khách Hàng)
package BLL;

import DAO.ThongKeDAO;
import DTO.ThongKe.ThongKeDoanhThuDTO;
// import DTO.ThongKe.ThongKeKhachHangDTO; // Đã xóa
import DTO.ThongKe.ThongKeTheoThangDTO;
// import DTO.ThongKe.ThongKeTonKhoDTO; // Đã xóa
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;

import java.util.ArrayList;
import java.util.Date; // Sử dụng java.util.Date
// import java.util.HashMap; // Không cần HashMap cho Tồn kho nữa


public class ThongKeBLL {

    // Sử dụng instance của ThongKeDAO cho các phương thức non-static
    private final ThongKeDAO thongkeDAO;

    // Danh sách lưu trữ dữ liệu tạm thời (nếu cần)
    // private ArrayList<ThongKeKhachHangDTO> listKhachHang; // Đã xóa
    // private ArrayList<ThongKeTonKhoDTO> listTonKho; // Đã xóa

    /**
     * Constructor khởi tạo đối tượng ThongKeDAO.
     * Có thể load dữ liệu mặc định ở đây nếu muốn.
     */
    public ThongKeBLL() {
        thongkeDAO = new ThongKeDAO(); // Khởi tạo DAO
        // Các dòng load dữ liệu mặc định cho tồn kho và khách hàng đã được xóa
    }

    // ==================== Thống kê Khách hàng ====================
    // ** Phần Thống kê Khách hàng đã được xóa hoàn toàn **


    // ==================== Thống kê Tồn Kho ====================
    // ** Phần Thống kê Tồn kho đã được xóa hoàn toàn **
    // ** Bao gồm cả phương thức getTongSoLuongTonKho **


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
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKeTuNgayDenNgay(start, end);
    }

    /**
     * Lấy thống kê doanh thu/chi phí của 7 ngày gần nhất.
     * @return ArrayList<ThongKeTungNgayTrongThangDTO>
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKe7NgayGanNhat() {
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKe7NgayGanNhat();
    }
}