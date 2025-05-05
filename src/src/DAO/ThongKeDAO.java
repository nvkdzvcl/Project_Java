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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDAO {

    // Logger để ghi lỗi
    private static final Logger LOGGER = Logger.getLogger(ThongKeDAO.class.getName());

    // --- Singleton Pattern (Giữ nguyên cách của bạn) ---
    public static ThongKeDAO getInstance() {
        return new ThongKeDAO();
    }
    // ---------------------------------------------------


    public ArrayList<ThongKeDoanhThuDTO> getDoanhThuTheoTungNam(int year_start, int year_end) {
        ArrayList<ThongKeDoanhThuDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        // Câu SQL đã sửa đổi
        String sql = """
                    WITH RECURSIVE years(year) AS (
                      SELECT ? -- year_start
                      UNION ALL
                      SELECT year + 1
                      FROM years
                      WHERE year < ? -- year_end
                    ),
                    YearlyCosts AS (
                        SELECT
                            YEAR(pn.THOIGIAN) AS Nam,
                            SUM(pn.TONGTIEN) AS Von
                        FROM PHIEUNHAP pn
                        WHERE pn.TRANGTHAI != 3
                        GROUP BY YEAR(pn.THOIGIAN)
                    ),
                    YearlyRevenue AS (
                        SELECT
                            YEAR(hd.THOIGIAN) AS Nam,
                            SUM(hd.TONGTIEN) AS DoanhThu
                        FROM HOADON hd
                        WHERE hd.TRANGTHAI != 3
                        GROUP BY YEAR(hd.THOIGIAN)
                    )
                    SELECT
                      yr.year AS Nam,
                      COALESCE(yc.Von, 0) AS Von,
                      COALESCE(yrv.DoanhThu, 0) AS DoanhThu
                    FROM years yr
                    LEFT JOIN YearlyCosts yc ON yr.year = yc.Nam
                    LEFT JOIN YearlyRevenue yrv ON yr.year = yrv.Nam
                    WHERE yr.year BETWEEN ? AND ? -- Đảm bảo chỉ lấy năm trong khoảng
                    ORDER BY yr.year;
                   """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, year_start);
            pst.setInt(2, year_end);
            pst.setInt(3, year_start); // Cho điều kiện WHERE cuối cùng
            pst.setInt(4, year_end);  // Cho điều kiện WHERE cuối cùng

            rs = pst.executeQuery();

            while (rs.next()) {
                int thoigian = rs.getInt("Nam");
                long von = rs.getLong("Von");
                long doanhthu = rs.getLong("DoanhThu");
                long loinhuan = doanhthu - von;
                ThongKeDoanhThuDTO x = new ThongKeDoanhThuDTO(thoigian, von, doanhthu, loinhuan);
                result.add(x);
            }
            // Đóng tài nguyên (vẫn giữ cách cũ theo file gốc)
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error in getDoanhThuTheoTungNam", e);
        }
        return result;
    }



    public ArrayList<ThongKeTheoThangDTO> getThongKeTheoThang(int nam) {
        ArrayList<ThongKeTheoThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        // Câu SQL đã sửa đổi
        String sql = """
                    WITH RECURSIVE months(month) AS (
                        SELECT 1
                        UNION ALL
                        SELECT month + 1
                        FROM months
                        WHERE month < 12
                    ),
                    MonthlyCosts AS (
                        SELECT
                            MONTH(pn.THOIGIAN) AS Thang,
                            SUM(pn.TONGTIEN) AS ChiPhiThang
                        FROM PHIEUNHAP pn
                        WHERE YEAR(pn.THOIGIAN) = ? AND pn.TRANGTHAI != 3
                        GROUP BY MONTH(pn.THOIGIAN)
                    ),
                    MonthlyRevenue AS (
                        SELECT
                            MONTH(hd.THOIGIAN) AS Thang,
                            SUM(hd.TONGTIEN) AS DoanhThuThang
                        FROM HOADON hd
                        WHERE YEAR(hd.THOIGIAN) = ? AND hd.TRANGTHAI != 3
                        GROUP BY MONTH(hd.THOIGIAN)
                    )
                    SELECT
                        m.month AS Thang,
                        COALESCE(mc.ChiPhiThang, 0) AS ChiPhi,
                        COALESCE(mr.DoanhThuThang, 0) AS DoanhThu
                    FROM months m
                    LEFT JOIN MonthlyCosts mc ON m.month = mc.Thang
                    LEFT JOIN MonthlyRevenue mr ON m.month = mr.Thang
                    ORDER BY m.month;
                    """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, nam); // Năm cho MonthlyCosts
            pst.setInt(2, nam); // Năm cho MonthlyRevenue

            rs = pst.executeQuery();
            while (rs.next()) {
                int thang = rs.getInt("Thang");
                long chiphi = rs.getLong("ChiPhi");
                long doanhthu = rs.getLong("DoanhThu");
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


    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTungNgayTrongThang(int thang, int nam) {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String firstDayOfMonthStr = String.format("%d-%02d-01", nam, thang);

        // Câu SQL đã sửa đổi
        String sql = """
                    WITH RECURSIVE DateSeries AS (
                      SELECT DATE(?) AS dt -- Ngày đầu tháng
                      UNION ALL
                      SELECT DATE_ADD(dt, INTERVAL 1 DAY)
                      FROM DateSeries
                      WHERE dt < LAST_DAY(?) -- Ngày cuối tháng
                    ),
                    DailyCosts AS (
                        SELECT
                            DATE(pn.THOIGIAN) AS Ngay,
                            SUM(pn.TONGTIEN) AS ChiPhiNgay
                        FROM PHIEUNHAP pn
                        WHERE pn.TRANGTHAI != 3
                            -- Optional: Filter by month/year here if DB is very large
                            -- AND YEAR(pn.THOIGIAN) = ? AND MONTH(pn.THOIGIAN) = ?
                        GROUP BY DATE(pn.THOIGIAN)
                    ),
                    DailyRevenue AS (
                        SELECT
                            hd.THOIGIAN AS Ngay, -- Already DATE type
                            SUM(hd.TONGTIEN) AS DoanhThuNgay
                        FROM HOADON hd
                        WHERE hd.TRANGTHAI != 3
                            -- Optional: Filter by month/year here if DB is very large
                            -- AND YEAR(hd.THOIGIAN) = ? AND MONTH(hd.THOIGIAN) = ?
                        GROUP BY hd.THOIGIAN
                    )
                    SELECT
                      ds.dt AS Ngay,
                      COALESCE(dc.ChiPhiNgay, 0) AS ChiPhi,
                      COALESCE(dr.DoanhThuNgay, 0) AS DoanhThu
                    FROM DateSeries ds
                    LEFT JOIN DailyCosts dc ON ds.dt = dc.Ngay
                    LEFT JOIN DailyRevenue dr ON ds.dt = dr.Ngay
                    ORDER BY ds.dt;
                  """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, firstDayOfMonthStr); // Cho DateSeries bắt đầu
            pst.setString(2, firstDayOfMonthStr); // Cho LAST_DAY

            // Nếu bạn thêm filter trong CTEs (Optional):
            // pst.setInt(3, nam); // Cho DailyCosts YEAR
            // pst.setInt(4, thang); // Cho DailyCosts MONTH
            // pst.setInt(5, nam); // Cho DailyRevenue YEAR
            // pst.setInt(6, thang); // Cho DailyRevenue MONTH

            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                long chiphi = rs.getLong("ChiPhi");
                long doanhthu = rs.getLong("DoanhThu");
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
            LOGGER.log(Level.SEVERE, "SQL Error in getThongKeTungNgayTrongThang", e);
        }
        return result;
    }


    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKe7NgayGanNhat() {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        // Câu SQL đã sửa đổi
        String sql = """
                     WITH RECURSIVE DateSeries AS (
                       SELECT CURDATE() AS dt -- Ngày hiện tại
                       UNION ALL
                       SELECT DATE_SUB(dt, INTERVAL 1 DAY)
                       FROM DateSeries
                       WHERE dt > DATE_SUB(CURDATE(), INTERVAL 6 DAY) -- Lùi lại 6 ngày nữa (tổng 7 ngày)
                     ),
                    DailyCosts AS (
                        SELECT
                            DATE(pn.THOIGIAN) AS Ngay,
                            SUM(pn.TONGTIEN) AS ChiPhiNgay
                        FROM PHIEUNHAP pn
                        WHERE pn.TRANGTHAI != 3 AND DATE(pn.THOIGIAN) >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) -- Filter for performance
                        GROUP BY DATE(pn.THOIGIAN)
                    ),
                    DailyRevenue AS (
                        SELECT
                            hd.THOIGIAN AS Ngay, -- Already DATE type
                            SUM(hd.TONGTIEN) AS DoanhThuNgay
                        FROM HOADON hd
                        WHERE hd.TRANGTHAI != 3 AND hd.THOIGIAN >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) -- Filter for performance
                        GROUP BY hd.THOIGIAN
                    )
                     SELECT
                       ds.dt AS Ngay,
                       COALESCE(dc.ChiPhiNgay, 0) AS ChiPhi,
                       COALESCE(dr.DoanhThuNgay, 0) AS DoanhThu
                     FROM DateSeries ds
                     LEFT JOIN DailyCosts dc ON ds.dt = dc.Ngay
                     LEFT JOIN DailyRevenue dr ON ds.dt = dr.Ngay
                     ORDER BY ds.dt; -- Sắp xếp từ cũ đến mới
                   """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                long chiphi = rs.getLong("ChiPhi");
                long doanhthu = rs.getLong("DoanhThu");
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
     * Lấy dữ liệu thống kê doanh thu và chi phí trong một khoảng ngày cụ thể. (ĐÃ SỬA)
     * Tính tổng chi phí và doanh thu riêng biệt trước khi join.
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

        // Câu SQL đã sửa đổi
        String sql = """
                     WITH RECURSIVE DateSeries AS (
                       SELECT ? AS dt -- Ngày bắt đầu (kiểu DATE)
                       UNION ALL
                       SELECT DATE_ADD(dt, INTERVAL 1 DAY)
                       FROM DateSeries
                       WHERE dt < ? -- Ngày kết thúc (kiểu DATE)
                     ),
                    DailyCosts AS (
                        SELECT
                            DATE(pn.THOIGIAN) AS Ngay,
                            SUM(pn.TONGTIEN) AS ChiPhiNgay
                        FROM PHIEUNHAP pn
                        WHERE pn.TRANGTHAI != 3 AND DATE(pn.THOIGIAN) BETWEEN ? AND ? -- Filter for performance
                        GROUP BY DATE(pn.THOIGIAN)
                    ),
                    DailyRevenue AS (
                        SELECT
                            hd.THOIGIAN AS Ngay, -- Already DATE type
                            SUM(hd.TONGTIEN) AS DoanhThuNgay
                        FROM HOADON hd
                        WHERE hd.TRANGTHAI != 3 AND hd.THOIGIAN BETWEEN ? AND ? -- Filter for performance
                        GROUP BY hd.THOIGIAN
                    )
                     SELECT
                       ds.dt AS Ngay,
                       COALESCE(dc.ChiPhiNgay, 0) AS ChiPhi,
                       COALESCE(dr.DoanhThuNgay, 0) AS DoanhThu
                     FROM DateSeries ds
                     LEFT JOIN DailyCosts dc ON ds.dt = dc.Ngay
                     LEFT JOIN DailyRevenue dr ON ds.dt = dr.Ngay
                     ORDER BY ds.dt;
                   """;

        try {
            con = JDBCUtil.startConnection();
            pst = con.prepareStatement(sql);
            pst.setDate(1, sqlStartDate); // Cho DateSeries start
            pst.setDate(2, sqlEndDate);   // Cho DateSeries end condition
            pst.setDate(3, sqlStartDate); // Cho DailyCosts BETWEEN
            pst.setDate(4, sqlEndDate);   // Cho DailyCosts BETWEEN
            pst.setDate(5, sqlStartDate); // Cho DailyRevenue BETWEEN
            pst.setDate(6, sqlEndDate);   // Cho DailyRevenue BETWEEN

            rs = pst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngay = rs.getDate("Ngay");
                long chiphi = rs.getLong("ChiPhi");
                long doanhthu = rs.getLong("DoanhThu");
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