
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


    public ThongKeBLL() {
        thongkeDAO = new ThongKeDAO(); // Khởi tạo DAO
    }

    // ==================== Thống kê Doanh thu/Chi phí theo thời gian ====================


    public ArrayList<ThongKeDoanhThuDTO> getDoanhThuTheoTungNam(int year_start, int year_end) {
        // Gọi phương thức non-static của instance DAO
        return this.thongkeDAO.getDoanhThuTheoTungNam(year_start, year_end);
    }


    public ArrayList<ThongKeTheoThangDTO> getThongKeTheoThang(int nam) {
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKeTheoThang(nam);
    }


    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTungNgayTrongThang(int thang, int nam) {
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKeTungNgayTrongThang(thang, nam);
    }


    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTuNgayDenNgay(Date start, Date end) {
        // Thêm kiểm tra null cho start/end nếu cần
        if (start == null || end == null) {
            // Hoặc trả về list rỗng, hoặc throw exception tùy logic mong muốn
            return new ArrayList<>();
        }
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKeTuNgayDenNgay(start, end);
    }


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



    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKe7NgayGanNhat() {
        // Gọi phương thức non-static của instance DAO
        return thongkeDAO.getThongKe7NgayGanNhat();
    }
}