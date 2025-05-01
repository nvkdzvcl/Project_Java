package DAO;

import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeKhachHangDTO;
import DTO.ThongKe.ThongKeTheoThangDTO;
import DTO.ThongKe.ThongKeTonKhoDTO;
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

    /**
     * Lấy dữ liệu thống kê tồn kho.
     * Lưu ý: Tính toán tồn kho dựa trên tổng nhập và tổng xuất.
     * @param text Văn bản tìm kiếm (tên hoặc mã sản phẩm).
     * @param timeStart Thời gian bắt đầu (java.util.Date).
     * @param timeEnd Thời gian kết thúc (java.util.Date).
     * @return ArrayList các đối tượng ThongKeTonKhoDTO.
     */
    public static ArrayList<ThongKeTonKhoDTO> getThongKeTonKho(String text, java.util.Date timeStart, java.util.Date timeEnd) {
        ArrayList<ThongKeTonKhoDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        // Chuyển đổi java.util.Date sang java.sql.Date
        java.sql.Date sqlTimeStart = new java.sql.Date(timeStart.getTime());
        java.sql.Date sqlTimeEnd = new java.sql.Date(timeEnd.getTime());


        try {
            con = JDBCUtil.startConnection();
            // Câu SQL tính tồn kho
            // Sử dụng CTE (Common Table Expressions) để làm rõ các bước tính toán
            String sql = """
                        WITH NhapTrongKy AS (
                            SELECT ctpn.MASP, SUM(ctpn.SOLUONG) AS SoLuongNhap
                            FROM CTPHIEUNHAP ctpn
                            JOIN PHIEUNHAP pn ON ctpn.MAPHIEUNHAP = pn.MAPHIEUNHAP
                            WHERE pn.THOIGIAN BETWEEN ? AND ? AND pn.TRANGTHAI != 3 -- Chỉ tính phiếu nhập không bị hủy
                            GROUP BY ctpn.MASP
                        ),
                        XuatTrongKy AS (
                            SELECT cthd.MASP, SUM(cthd.SOLUONG) AS SoLuongXuat
                            FROM CTHOADON cthd
                            JOIN HOADON hd ON cthd.MAHOADON = hd.MAHOADON
                            WHERE hd.THOIGIAN BETWEEN ? AND ? AND hd.TRANGTHAI != 3 -- Chỉ tính hóa đơn không bị hủy
                            GROUP BY cthd.MASP
                        ),
                        NhapDauKy AS (
                            SELECT ctpn.MASP, SUM(ctpn.SOLUONG) AS SoLuongNhapDau
                            FROM CTPHIEUNHAP ctpn
                            JOIN PHIEUNHAP pn ON ctpn.MAPHIEUNHAP = pn.MAPHIEUNHAP
                            WHERE pn.THOIGIAN < ? AND pn.TRANGTHAI != 3
                            GROUP BY ctpn.MASP
                        ),
                        XuatDauKy AS (
                            SELECT cthd.MASP, SUM(cthd.SOLUONG) AS SoLuongXuatDau
                            FROM CTHOADON cthd
                            JOIN HOADON hd ON cthd.MAHOADON = hd.MAHOADON
                            WHERE hd.THOIGIAN < ? AND hd.TRANGTHAI != 3
                            GROUP BY cthd.MASP
                        )
                        SELECT
                            sp.MASP,
                            sp.TENSP,
                            sp.THUONGHIEU,
                            sp.XUATXU,
                            sp.MAUSAC,
                            sp.KICHTHUOC,
                            sp.SOLUONG AS SoLuongHienTai, -- Số lượng hiện tại trong bảng SANPHAM
                            sp.TRANGTHAI,
                            COALESCE(ndk.SoLuongNhapDau, 0) - COALESCE(xdk.SoLuongXuatDau, 0) AS TonDauKy,
                            COALESCE(nk.SoLuongNhap, 0) AS NhapTrongKy,
                            COALESCE(xk.SoLuongXuat, 0) AS XuatTrongKy,
                            (COALESCE(ndk.SoLuongNhapDau, 0) - COALESCE(xdk.SoLuongXuatDau, 0)) + COALESCE(nk.SoLuongNhap, 0) - COALESCE(xk.SoLuongXuat, 0) AS TonCuoiKy
                        FROM SANPHAM sp
                        LEFT JOIN NhapTrongKy nk ON sp.MASP = nk.MASP
                        LEFT JOIN XuatTrongKy xk ON sp.MASP = xk.MASP
                        LEFT JOIN NhapDauKy ndk ON sp.MASP = ndk.MASP
                        LEFT JOIN XuatDauKy xdk ON sp.MASP = xdk.MASP
                        WHERE (sp.TENSP LIKE ? OR CAST(sp.MASP AS CHAR) LIKE ?) -- Tìm theo tên hoặc mã
                        ORDER BY sp.MASP;
                       """;
            pst = con.prepareStatement(sql);

            // Tham số cho NhapTrongKy và XuatTrongKy
            pst.setDate(1, sqlTimeStart); // NhapTrongKy timeStart
            pst.setDate(2, sqlTimeEnd);   // NhapTrongKy timeEnd
            pst.setDate(3, sqlTimeStart); // XuatTrongKy timeStart
            pst.setDate(4, sqlTimeEnd);   // XuatTrongKy timeEnd

            // Tham số cho NhapDauKy và XuatDauKy
            pst.setDate(5, sqlTimeStart); // NhapDauKy timeStart
            pst.setDate(6, sqlTimeStart); // XuatDauKy timeStart

            // Tham số cho điều kiện WHERE
            String searchText = "%" + text + "%";
            pst.setString(7, searchText); // TENSP LIKE ?
            pst.setString(8, searchText); // MASP LIKE ?


            rs = pst.executeQuery();

            while (rs.next()) {
                ThongKeTonKhoDTO p = new ThongKeTonKhoDTO(
                        rs.getInt("MASP"),
                        rs.getString("TENSP"),
                        rs.getString("THUONGHIEU"),
                        rs.getString("XUATXU"),
                        rs.getString("MAUSAC"),
                        rs.getString("KICHTHUOC"),
                        rs.getInt("SoLuongHienTai"), // Lấy số lượng hiện tại từ bảng SANPHAM
                        rs.getInt("TRANGTHAI"),
                        rs.getInt("TonDauKy"),
                        rs.getInt("NhapTrongKy"),
                        rs.getInt("XuatTrongKy"),
                        rs.getInt("TonCuoiKy")
                );
                result.add(p); // Thêm vào danh sách kết quả
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, "SQL Error in getThongKeTonKho", e);
        }
        return result;
    }

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

    /**
     * Lấy dữ liệu thống kê khách hàng (số lượng hóa đơn, tổng tiền mua).
     * @param text Văn bản tìm kiếm (tên hoặc mã khách hàng).
     * @param timeStart Thời gian bắt đầu (java.util.Date).
     * @param timeEnd Thời gian kết thúc (java.util.Date).
     * @return Danh sách các đối tượng ThongKeKhachHangDTO.
     */
    public static ArrayList<ThongKeKhachHangDTO> getThongKeKhachHang(String text, java.util.Date timeStart, java.util.Date timeEnd) {
        ArrayList<ThongKeKhachHangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        java.sql.Date sqlTimeStart = new java.sql.Date(timeStart.getTime());
        java.sql.Date sqlTimeEnd = new java.sql.Date(timeEnd.getTime());

        try {
            con = JDBCUtil.startConnection();
            // Thống kê số lượng hóa đơn và tổng tiền theo khách hàng
            String sql = """
                        SELECT
                            kh.MAKHACHHANG,
                            kh.TENKHACHHANG,
                            COUNT(hd.MAHOADON) AS SoLuongHoaDon,
                            COALESCE(SUM(hd.TONGTIEN), 0) AS TongTienMua
                        FROM KHACHHANG kh
                        LEFT JOIN HOADON hd ON kh.MAKHACHHANG = hd.MAKHACHHANG
                                            AND hd.THOIGIAN BETWEEN ? AND ?
                                            AND hd.TRANGTHAI != 3 -- Chỉ tính hóa đơn không bị hủy
                        WHERE kh.TRANGTHAI = 1 -- Chỉ khách hàng đang hoạt động
                          AND (kh.TENKHACHHANG LIKE ? OR CAST(kh.MAKHACHHANG AS CHAR) LIKE ?)
                        GROUP BY kh.MAKHACHHANG, kh.TENKHACHHANG
                        ORDER BY TongTienMua DESC, kh.TENKHACHHANG;
                       """;
            pst = con.prepareStatement(sql);

            pst.setDate(1, sqlTimeStart);
            pst.setDate(2, sqlTimeEnd);
            String searchText = "%" + text + "%";
            pst.setString(3, searchText); // TENKHACHHANG LIKE ?
            pst.setString(4, searchText); // MAKHACHHANG LIKE ?


            rs = pst.executeQuery();

            while (rs.next()) {
                int makh = rs.getInt("MAKHACHHANG");
                String tenkh = rs.getString("TENKHACHHANG");
                int soluongphieu = rs.getInt("SoLuongHoaDon");
                long tongtien = rs.getLong("TongTienMua");
                ThongKeKhachHangDTO x = new ThongKeKhachHangDTO(makh, tenkh, soluongphieu, tongtien);
                result.add(x);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, "SQL Error in getThongKeKhachHang", e);
        }
        return result;
    }

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