package BLL;

import DAO.ThongKeDAO;
import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeKhachHangDTO;
import DTO.ThongKe.ThongKeTheoThangDTO;
import DTO.ThongKe.ThongKeTonKhoDTO;
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;

import java.util.ArrayList;
import java.util.Date; // Sử dụng java.util.Date
// import java.util.HashMap; // Không cần HashMap cho Tồn kho nữa


public class ThongKeBLL {

    // Sử dụng instance của ThongKeDAO cho các phương thức non-static
    private final ThongKeDAO thongkeDAO;

    // Danh sách lưu trữ dữ liệu tạm thời (nếu cần)
    private ArrayList<ThongKeKhachHangDTO> listKhachHang;
    private ArrayList<ThongKeTonKhoDTO> listTonKho; // Đổi từ HashMap sang ArrayList

    /**
     * Constructor khởi tạo đối tượng ThongKeDAO.
     * Có thể load dữ liệu mặc định ở đây nếu muốn.
     */
    public ThongKeBLL() {
        thongkeDAO = new ThongKeDAO(); // Khởi tạo DAO
        // Load dữ liệu tồn kho ban đầu (tất cả sản phẩm, từ trước đến nay)
        // Lưu ý: new Date(0) là thời điểm epoch (1/1/1970), phù hợp để lấy từ đầu.
        // listTonKho = ThongKeDAO.getThongKeTonKho("", new Date(0), new Date(System.currentTimeMillis()));
        // Hoặc không load ở constructor mà load khi gọi hàm get lần đầu
    }

    // ==================== Thống kê Khách hàng ====================

    /**
     * Lấy danh sách thống kê tất cả khách hàng từ trước đến nay.
     * @return ArrayList<ThongKeKhachHangDTO>
     */
    public ArrayList<ThongKeKhachHangDTO> getAllKhachHangDefault() {
        // Gọi phương thức DAO tĩnh, lấy từ trước đến nay
        this.listKhachHang = ThongKeDAO.getThongKeKhachHang("", new Date(0), new Date(System.currentTimeMillis()));
        return this.listKhachHang;
    }

    /**
     * Lọc và lấy danh sách thống kê khách hàng theo tiêu chí.
     * @param text Từ khóa tìm kiếm (tên hoặc mã khách hàng).
     * @param start Ngày bắt đầu (java.util.Date).
     * @param end Ngày kết thúc (java.util.Date).
     * @return ArrayList<ThongKeKhachHangDTO>
     */
    public ArrayList<ThongKeKhachHangDTO> filterKhachHang(String text, Date start, Date end) {
        // Gọi phương thức DAO tĩnh với các tham số lọc
        this.listKhachHang = ThongKeDAO.getThongKeKhachHang(text, start, end);
        return this.listKhachHang;
    }

    // ==================== Thống kê Tồn Kho ====================

    /**
     * Lấy danh sách thống kê tồn kho ban đầu (có thể là toàn bộ hoặc mặc định).
     * @return ArrayList<ThongKeTonKhoDTO>
     */
    public ArrayList<ThongKeTonKhoDTO> getTonKhoDefault() {
        if (this.listTonKho == null) { // Load lần đầu nếu chưa có
            this.listTonKho = ThongKeDAO.getThongKeTonKho("", new Date(0), new Date(System.currentTimeMillis()));
        }
        return this.listTonKho;
    }

    /**
     * Lọc và lấy danh sách thống kê tồn kho theo tiêu chí.
     * @param text Từ khóa tìm kiếm (tên hoặc mã sản phẩm).
     * @param time_start Ngày bắt đầu (java.util.Date).
     * @param time_end Ngày kết thúc (java.util.Date).
     * @return ArrayList<ThongKeTonKhoDTO>
     */
    public ArrayList<ThongKeTonKhoDTO> filterTonKho(String text, Date time_start, Date time_end) {
        // Gọi phương thức DAO tĩnh và trả về kết quả trực tiếp
        // Có thể lưu lại vào this.listTonKho nếu logic yêu cầu
        return ThongKeDAO.getThongKeTonKho(text, time_start, time_end);
    }

    /**
     * Tính tổng các cột số lượng từ danh sách ThongKeTonKhoDTO.
     * @param list Danh sách ThongKeTonKhoDTO cần tính tổng.
     * @return Mảng int gồm 4 phần tử: [Tổng tồn đầu kỳ, Tổng nhập trong kỳ, Tổng xuất trong kỳ, Tổng tồn cuối kỳ].
     */
    public int[] getTongSoLuongTonKho(ArrayList<ThongKeTonKhoDTO> list) {
        int[] result = {0, 0, 0, 0};
        if (list == null) {
            return result; // Trả về mảng 0 nếu danh sách rỗng
        }
        for (ThongKeTonKhoDTO item : list) {
            result[0] += item.getTondauky();
            result[1] += item.getNhaptrongky();
            result[2] += item.getXuattrongky();
            result[3] += item.getToncuoiky();
        }
        return result;
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
        // Gọi phương thức non-static của instance DAO
        // Đã thay đổi tham số từ String sang Date để khớp với DAO
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