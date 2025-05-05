// File: ThongKeDAO.java
package DAO;

import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeTheoThangDTO;
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;
import config.JDBCUtil; // Đảm bảo bạn có lớp này để kết nối DB

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Dùng java.sql.Date cho cột DATE
import java.util.ArrayList;
// import java.util.Calendar; // Không thấy dùng trực tiếp
// import java.util.HashMap; // Không thấy dùng
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDAO {

    // Logger để ghi lỗi
    private static final Logger LOGGER = Logger.getLogger(ThongKeDAO.class.getName());

    // Phương thức khởi tạo private nếu bạn muốn làm Singleton thực sự
    // private ThongKeDAO() {}
    // private static ThongKeDAO instance;
    // public static synchronized ThongKeDAO getInstance() {
    //     if (instance == null) {
    //         instance = new ThongKeDAO();
    //     }
    //     return instance;
    // }

    // Hoặc giữ nguyên cách của bạn nếu đơn giản là đủ
    public static ThongKeDAO getInstance() {
        return new ThongKeDAO();
    }


    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí theo từng năm.
     * Đã sửa: Bỏ DISTINCT khỏi SUM. Thêm đóng tài nguyên.
     * @param year_start Năm bắt đầu.
     * @param year_end Năm kết thúc.
     * @return Danh sách các đối tượng ThongKeDoanhThuDTO.
     */
    public ArrayList<ThongKeDoanhThuDTO> getDoanhThuTheoTungNam(int year_start, int year_end) {
        ArrayList<ThongKeDoanhThuDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

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
                      COALESCE(SUM(pn.TONGTIEN), 0) AS Von, -- Đã bỏ DISTINCT
                      COALESCE(SUM(hd.TONGTIEN), 0) AS DoanhThu -- Đã bỏ DISTINCT
                    FROM years yr
                    LEFT JOIN PHIEUNHAP pn ON YEAR(pn.THOIGIAN) = yr.year AND pn.TRANGTHAI != 3
                    LEFT JOIN HOADON hd ON YEAR(hd.THOIGIAN) = yr.year AND hd.TRANGTHAI != 3
                    GROUP BY yr.year
                    ORDER BY yr.year;
                   """;

        try {
            con = JDBCUtil.startConnection(); // Giả sử hàm này trả về Connection
            pst = con.prepareStatement(sql);
            pst.setInt(1, year_start);
            pst.setInt(2, year_end);

            rs = pst.executeQuery();

            while (rs.next()) {
                int thoigian = rs.getInt("Nam");
                long von = rs.getLong("Von"); // Dùng getLong
                long doanhthu = rs.getLong("DoanhThu"); // Dùng getLong
                long loinhuan = doanhthu - von;
                ThongKeDoanhThuDTO x = new ThongKeDoanhThuDTO(thoigian, von, doanhthu, loinhuan);
                result.add(x);
            }
            // Đóng tài nguyên theo yêu cầu (trong try, trước catch) - Không phải cách tốt nhất
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) JDBCUtil.closeConnection(con); // Giả sử có hàm đóng trong JDBCUtil

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error in getDoanhThuTheoTungNam", e);
        }
        // Không có finally theo yêu cầu
        return result;
    }


    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí theo từng tháng trong năm.
     * Đã sửa: Bỏ DISTINCT khỏi SUM. Dùng getLong. Thêm đóng tài nguyên.
     * @param nam Năm cần thống kê.
     * @return Danh sách các đối tượng ThongKeTheoThangDTO.
     */
    public ArrayList<ThongKeTheoThangDTO> getThongKeTheoThang(int nam) {
        ArrayList<ThongKeTheoThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

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
                        COALESCE(SUM(pn.TONGTIEN), 0) AS ChiPhi, -- Đã bỏ DISTINCT
                        COALESCE(SUM(hd.TONGTIEN), 0) AS DoanhThu -- Đã bỏ DISTINCT
                    FROM months m
                    LEFT JOIN PHIEUNHAP pn ON MONTH(pn.THOIGIAN) = m.month AND YEAR(pn.THOIGIAN) = ? AND pn.TRANGTHAI != 3
                    LEFT JOIN HOADON hd ON MONTH(hd.THOIGIAN) = m.month AND YEAR(hd.THOIGIAN) = ? AND hd.TRANGTHAI != 3
                    GROUP BY m.month
                    ORDER BY m.month;
                    """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, nam); // Năm cho PHIEUNHAP
            pst.setInt(2, nam); // Năm cho HOADON

            rs = pst.executeQuery();
            while (rs.next()) {
                int thang = rs.getInt("Thang");
                long chiphi = rs.getLong("ChiPhi"); // Dùng getLong
                long doanhthu = rs.getLong("DoanhThu"); // Dùng getLong
                long loinhuan = doanhthu - chiphi;
                ThongKeTheoThangDTO thongke = new ThongKeTheoThangDTO(thang, chiphi, doanhthu, loinhuan);
                result.add(thongke);
            }
            // Đóng tài nguyên
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error in getThongKeTheoThang", e);
        }
        return result;
    }

    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí theo từng ngày trong tháng.
     * Đã sửa: Sửa so sánh DATE(pn.THOIGIAN), bỏ DISTINCT, dùng getLong, thêm đóng tài nguyên.
     * @param thang Tháng cần thống kê (1-12).
     * @param nam Năm cần thống kê.
     * @return Danh sách các đối tượng ThongKeTungNgayTrongThangDTO.
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTungNgayTrongThang(int thang, int nam) {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String firstDayOfMonthStr = String.format("%d-%02d-01", nam, thang);

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
                      COALESCE(SUM(pn.TONGTIEN), 0) AS ChiPhi, -- Đã bỏ DISTINCT
                      COALESCE(SUM(hd.TONGTIEN), 0) AS DoanhThu -- Đã bỏ DISTINCT
                    FROM DateSeries ds
                    LEFT JOIN PHIEUNHAP pn ON DATE(pn.THOIGIAN) = ds.dt AND pn.TRANGTHAI != 3 -- Sửa: Dùng DATE()
                    LEFT JOIN HOADON hd ON hd.THOIGIAN = ds.dt AND hd.TRANGTHAI != 3 -- Giữ nguyên vì hd.THOIGIAN là DATE
                    GROUP BY ds.dt
                    ORDER BY ds.dt;
                  """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, firstDayOfMonthStr);
            pst.setString(2, firstDayOfMonthStr); // Dùng cho LAST_DAY

            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                long chiphi = rs.getLong("ChiPhi"); // Dùng getLong
                long doanhthu = rs.getLong("DoanhThu"); // Dùng getLong
                long loinhuan = doanhthu - chiphi;
                java.util.Date utilDate = new java.util.Date(ngay.getTime()); // Chuyển sang util.Date
                ThongKeTungNgayTrongThangDTO tn = new ThongKeTungNgayTrongThangDTO(utilDate, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
            // Đóng tài nguyên
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error in getThongKeTungNgayTrongThang", e);
        }
        return result;
    }

    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí cho 7 ngày gần nhất.
     * Đã sửa: Sửa so sánh DATE(pn.THOIGIAN), bỏ DISTINCT, dùng getLong, thêm đóng tài nguyên.
     * @return Danh sách các đối tượng ThongKeTungNgayTrongThangDTO.
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKe7NgayGanNhat() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

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
                       COALESCE(SUM(pn.TONGTIEN), 0) AS ChiPhi, -- Đã bỏ DISTINCT
                       COALESCE(SUM(hd.TONGTIEN), 0) AS DoanhThu -- Đã bỏ DISTINCT
                     FROM DateSeries ds
                     LEFT JOIN PHIEUNHAP pn ON DATE(pn.THOIGIAN) = ds.dt AND pn.TRANGTHAI != 3 -- Sửa: Dùng DATE()
                     LEFT JOIN HOADON hd ON hd.THOIGIAN = ds.dt AND hd.TRANGTHAI != 3
                     GROUP BY ds.dt
                     ORDER BY ds.dt; -- Sắp xếp từ cũ đến mới
                   """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                long chiphi = rs.getLong("ChiPhi"); // Dùng getLong
                long doanhthu = rs.getLong("DoanhThu"); // Dùng getLong
                long loinhuan = doanhthu - chiphi;
                java.util.Date utilDate = new java.util.Date(ngay.getTime());
                ThongKeTungNgayTrongThangDTO tn = new ThongKeTungNgayTrongThangDTO(utilDate, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
            // Đóng tài nguyên
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error in getThongKe7NgayGanNhat", e);
        }
        return result;
    }

    /**
     * Lấy dữ liệu thống kê doanh thu và chi phí trong một khoảng ngày cụ thể.
     * Đã sửa: Sửa so sánh DATE(pn.THOIGIAN), bỏ DISTINCT, dùng getLong, thêm đóng tài nguyên.
     * @param start Ngày bắt đầu (java.util.Date).
     * @param end Ngày kết thúc (java.util.Date).
     * @return Danh sách các đối tượng ThongKeTungNgayTrongThangDTO.
     */
    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTuNgayDenNgay(java.util.Date start, java.util.Date end) {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        // Chuyển đổi java.util.Date sang java.sql.Date
        java.sql.Date sqlStartDate = new java.sql.Date(start.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(end.getTime());

        String sql = """
                     WITH RECURSIVE DateSeries AS (
                       SELECT ? AS dt -- Ngày bắt đầu (kiểu DATE)
                       UNION ALL
                       SELECT DATE_ADD(dt, INTERVAL 1 DAY)
                       FROM DateSeries
                       WHERE dt < ? -- Ngày kết thúc (kiểu DATE)
                     )
                     SELECT
                       ds.dt AS Ngay,
                       COALESCE(SUM(pn.TONGTIEN), 0) AS ChiPhi, -- Đã bỏ DISTINCT
                       COALESCE(SUM(hd.TONGTIEN), 0) AS DoanhThu -- Đã bỏ DISTINCT
                     FROM DateSeries ds
                     LEFT JOIN PHIEUNHAP pn ON DATE(pn.THOIGIAN) = ds.dt AND pn.TRANGTHAI != 3 -- Sửa: Dùng DATE()
                     LEFT JOIN HOADON hd ON hd.THOIGIAN = ds.dt AND hd.TRANGTHAI != 3
                     GROUP BY ds.dt
                     ORDER BY ds.dt;
                   """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            pst.setDate(1, sqlStartDate);
            pst.setDate(2, sqlEndDate);

            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                long chiphi = rs.getLong("ChiPhi"); // Dùng getLong
                long doanhthu = rs.getLong("DoanhThu"); // Dùng getLong
                long loinhuan = doanhthu - chiphi;
                java.util.Date utilDate = new java.util.Date(ngay.getTime());
                ThongKeTungNgayTrongThangDTO tn = new ThongKeTungNgayTrongThangDTO(utilDate, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
            // Đóng tài nguyên
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error in getThongKeTuNgayDenNgay", e);
        }
        return result;
    }
}