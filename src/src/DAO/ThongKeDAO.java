// File: ThongKeDAO.java (Đã xóa phần liên quan đến Thống kê Tồn Kho và Khách Hàng)
package DAO;

import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeTheoThangDTO;
// import DTO.ThongKe.ThongKeKhachHangDTO; // Đã xóa
// import DTO.ThongKe.ThongKeTonKhoDTO; // Đã xóa
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Sử dụng java.sql.Date cho cột DATE
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
// import java.util.Date; // java.util.Date được sử dụng cho tham số đầu vào
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDAO {

    public static ThongKeDAO getInstance() {
        return new ThongKeDAO();
    }

    // **********************************************************************
    // ** Phần getThongKeTonKho đã được xóa hoàn toàn                      **
    // **********************************************************************


    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí (tổng tiền nhập) theo từng năm.
     * @param year_start Năm bắt đầu.
     * @param year_end Năm kết thúc.
     * @return Danh sách các đối tượng ThongKeDoanhThuDTO.
     */
    public ArrayList<ThongKeDoanhThuDTO> getDoanhThuTheoTungNam(int year_start, int year_end) {
        ArrayList<ThongKeDoanhThuDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = JDBCUtil.startConnection();
            // Tạo dãy năm và tính tổng doanh thu (HOADON.TONGTIEN) và chi phí (PHIEUNHAP.TONGTIEN)
            String sql = """
                        WITH RECURSIVE years(year) AS (
                          SELECT ? -- year_start
                          UNION ALL
                          SELECT year + 1
                          FROM years
                          WHERE year < ? -- year_end
                        )
                        SELECT
                          yr.year AS Nam,
                          COALESCE(SUM(DISTINCT pn.TONGTIEN), 0) AS Von, -- Tổng tiền các phiếu nhập trong năm (tránh trùng nếu JOIN nhiều)
                          COALESCE(SUM(DISTINCT hd.TONGTIEN), 0) AS DoanhThu -- Tổng tiền các hóa đơn trong năm (tránh trùng nếu JOIN nhiều)
                        FROM years yr
                        LEFT JOIN PHIEUNHAP pn ON YEAR(pn.THOIGIAN) = yr.year AND pn.TRANGTHAI != 3 -- Chi phí từ phiếu nhập (không hủy)
                        LEFT JOIN HOADON hd ON YEAR(hd.THOIGIAN) = yr.year AND hd.TRANGTHAI != 3 -- Doanh thu từ hóa đơn (không hủy)
                        GROUP BY yr.year
                        ORDER BY yr.year;
                       """;
            pst = con.prepareStatement(sql);
            pst.setInt(1, year_start);
            pst.setInt(2, year_end);

            rs = pst.executeQuery();

            while (rs.next()) {
                int thoigian = rs.getInt("Nam");
                long von = rs.getLong("Von");
                long doanhthu = rs.getLong("DoanhThu");
                long loinhuan = doanhthu - von; // Lợi nhuận = Doanh thu - Chi phí (Tổng tiền nhập)
                ThongKeDoanhThuDTO x = new ThongKeDoanhThuDTO(thoigian, von, doanhthu, loinhuan);
                result.add(x);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, "SQL Error in getDoanhThuTheoTungNam", e);
        }
        return result;
    }

    // **********************************************************************
    // ** Phần getThongKeKhachHang đã được xóa hoàn toàn                   **
    // **********************************************************************


    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí (tổng tiền nhập) theo từng tháng trong năm.
     * @param nam Năm cần thống kê.
     * @return Danh sách các đối tượng ThongKeTheoThangDTO.
     */
    public ArrayList<ThongKeTheoThangDTO> getThongKeTheoThang(int nam) {
        ArrayList<ThongKeTheoThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = JDBCUtil.startConnection();
            // Tạo 12 tháng và tính tổng doanh thu, chi phí cho từng tháng
            String sql = """
                        WITH RECURSIVE months(month) AS (
                            SELECT 1
                            UNION ALL
                            SELECT month + 1
                            FROM months
                            WHERE month < 12
                        )
                        SELECT
                            m.month AS Thang,
                            COALESCE(SUM(DISTINCT pn.TONGTIEN), 0) AS ChiPhi, -- Tổng tiền nhập trong tháng
                            COALESCE(SUM(DISTINCT hd.TONGTIEN), 0) AS DoanhThu -- Tổng tiền hóa đơn trong tháng
                        FROM months m
                        LEFT JOIN PHIEUNHAP pn ON MONTH(pn.THOIGIAN) = m.month AND YEAR(pn.THOIGIAN) = ? AND pn.TRANGTHAI != 3
                        LEFT JOIN HOADON hd ON MONTH(hd.THOIGIAN) = m.month AND YEAR(hd.THOIGIAN) = ? AND hd.TRANGTHAI != 3
                        GROUP BY m.month
                        ORDER BY m.month;
                        """;
            pst = con.prepareStatement(sql);
            pst.setInt(1, nam); // Năm cho PHIEUNHAP
            pst.setInt(2, nam); // Năm cho HOADON

            rs = pst.executeQuery();
            while (rs.next()) {
                int thang = rs.getInt("Thang");
                int chiphi = rs.getInt("ChiPhi");
                int doanhthu = rs.getInt("DoanhThu");
                int loinhuan = doanhthu - chiphi;
                ThongKeTheoThangDTO thongke = new ThongKeTheoThangDTO(thang, chiphi, doanhthu, loinhuan);
                result.add(thongke);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, "SQL Error in getThongKeTheoThang", e);
        }
        return result;
    }

    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí (tổng tiền nhập) theo từng ngày trong tháng.
     * @param thang Tháng cần thống kê (1-12).
     * @param nam Năm cần thống kê.
     * @return Danh sách các đối tượng ThongKeTungNgayTrongThangDTO.
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTungNgayTrongThang(int thang, int nam) {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        // Tạo ngày đầu tiên của tháng
        String firstDayOfMonthStr = String.format("%d-%02d-01", nam, thang);

        try {
            con = JDBCUtil.startConnection();
            // Tạo danh sách các ngày trong tháng và tính toán
            // Cú pháp tạo dãy ngày có thể cần điều chỉnh cho CSDL cụ thể (ví dụ này dùng cú pháp MySQL)
            String sql = """
                        WITH RECURSIVE DateSeries AS (
                          SELECT DATE(?) AS dt -- Ngày đầu tháng
                          UNION ALL
                          SELECT DATE_ADD(dt, INTERVAL 1 DAY)
                          FROM DateSeries
                          WHERE dt < LAST_DAY(?) -- Ngày cuối tháng
                        )
                        SELECT
                          ds.dt AS Ngay,
                          COALESCE(SUM(DISTINCT pn.TONGTIEN), 0) AS ChiPhi, -- Tổng tiền nhập trong ngày
                          COALESCE(SUM(DISTINCT hd.TONGTIEN), 0) AS DoanhThu -- Tổng tiền hóa đơn trong ngày
                        FROM DateSeries ds
                        LEFT JOIN PHIEUNHAP pn ON pn.THOIGIAN = ds.dt AND pn.TRANGTHAI != 3
                        LEFT JOIN HOADON hd ON hd.THOIGIAN = ds.dt AND hd.TRANGTHAI != 3
                        GROUP BY ds.dt
                        ORDER BY ds.dt;
                      """;
            pst = con.prepareStatement(sql);
            pst.setString(1, firstDayOfMonthStr);
            pst.setString(2, firstDayOfMonthStr); // Dùng cho LAST_DAY

            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                int chiphi = rs.getInt("ChiPhi");
                int doanhthu = rs.getInt("DoanhThu");
                int loinhuan = doanhthu - chiphi;
                // Chuyển đổi java.sql.Date sang java.util.Date nếu cần thiết cho DTO
                java.util.Date utilDate = new java.util.Date(ngay.getTime());
                ThongKeTungNgayTrongThangDTO tn = new ThongKeTungNgayTrongThangDTO(utilDate, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, "SQL Error in getThongKeTungNgayTrongThang", e);
        }
        return result;
    }

    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí (tổng tiền nhập) cho 7 ngày gần nhất.
     * @return Danh sách các đối tượng ThongKeTungNgayTrongThangDTO.
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKe7NgayGanNhat() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = JDBCUtil.startConnection();
            // Tạo dãy 7 ngày gần nhất (tính cả ngày hiện tại)
            String sql = """
                         WITH RECURSIVE DateSeries AS (
                           SELECT CURDATE() AS dt -- Ngày hiện tại
                           UNION ALL
                           SELECT DATE_SUB(dt, INTERVAL 1 DAY)
                           FROM DateSeries
                           WHERE dt > DATE_SUB(CURDATE(), INTERVAL 6 DAY) -- Lùi lại 6 ngày nữa (tổng 7 ngày)
                         )
                         SELECT
                           ds.dt AS Ngay,
                           COALESCE(SUM(DISTINCT pn.TONGTIEN), 0) AS ChiPhi,
                           COALESCE(SUM(DISTINCT hd.TONGTIEN), 0) AS DoanhThu
                         FROM DateSeries ds
                         LEFT JOIN PHIEUNHAP pn ON pn.THOIGIAN = ds.dt AND pn.TRANGTHAI != 3
                         LEFT JOIN HOADON hd ON hd.THOIGIAN = ds.dt AND hd.TRANGTHAI != 3
                         GROUP BY ds.dt
                         ORDER BY ds.dt; -- Sắp xếp từ cũ đến mới
                       """;
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                int chiphi = rs.getInt("ChiPhi");
                int doanhthu = rs.getInt("DoanhThu");
                int loinhuan = doanhthu - chiphi;
                java.util.Date utilDate = new java.util.Date(ngay.getTime());
                ThongKeTungNgayTrongThangDTO tn = new ThongKeTungNgayTrongThangDTO(utilDate, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, "SQL Error in getThongKe7NgayGanNhat", e);
        }
        return result;
    }

    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí (tổng tiền nhập) trong một khoảng ngày cụ thể.
     * @param start Ngày bắt đầu (java.util.Date).
     * @param end Ngày kết thúc (java.util.Date).
     * @return Danh sách các đối tượng ThongKeTungNgayTrongThangDTO.
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTuNgayDenNgay(java.util.Date start, java.util.Date end) {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        java.sql.Date sqlStartDate = new java.sql.Date(start.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(end.getTime());

        try {
            con = JDBCUtil.startConnection();
            // Tạo dãy ngày trong khoảng và tính toán
            String sql = """
                         WITH RECURSIVE DateSeries AS (
                           SELECT ? AS dt -- Ngày bắt đầu
                           UNION ALL
                           SELECT DATE_ADD(dt, INTERVAL 1 DAY)
                           FROM DateSeries
                           WHERE dt < ? -- Ngày kết thúc
                         )
                         SELECT
                           ds.dt AS Ngay,
                           COALESCE(SUM(DISTINCT pn.TONGTIEN), 0) AS ChiPhi,
                           COALESCE(SUM(DISTINCT hd.TONGTIEN), 0) AS DoanhThu
                         FROM DateSeries ds
                         LEFT JOIN PHIEUNHAP pn ON pn.THOIGIAN = ds.dt AND pn.TRANGTHAI != 3
                         LEFT JOIN HOADON hd ON hd.THOIGIAN = ds.dt AND hd.TRANGTHAI != 3
                         GROUP BY ds.dt
                         ORDER BY ds.dt;
                       """;
            pst = con.prepareStatement(sql);
            pst.setDate(1, sqlStartDate);
            pst.setDate(2, sqlEndDate);

            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                int chiphi = rs.getInt("ChiPhi");
                int doanhthu = rs.getInt("DoanhThu");
                int loinhuan = doanhthu - chiphi;
                java.util.Date utilDate = new java.util.Date(ngay.getTime());
                ThongKeTungNgayTrongThangDTO tn = new ThongKeTungNgayTrongThangDTO(utilDate, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, "SQL Error in getThongKeTuNgayDenNgay", e);
        }
        return result;
    }
}