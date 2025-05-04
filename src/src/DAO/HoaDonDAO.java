package DAO;

import DTO.HoaDonDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAO {
    private static final HoaDonDAO INSTANCE = new HoaDonDAO();
    public static HoaDonDAO getInstance() { return INSTANCE; }

    /** Thêm mới hóa đơn, trả về ID sinh tự động hoặc -1 nếu thất bại */
    public int insert(HoaDonDTO hd) {
        String sql = """
            INSERT INTO hoadon (
              MAKHACHHANG,
              MANV,
              THOIGIAN,
              DIACHI,
              TONGTIEN,
              TRANGTHAI
            ) VALUES (?,?,?,?,?,?)
            """;
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, hd.getKhachHang());
            ps.setInt(2, hd.getNhanVienBan());
            ps.setTimestamp(3, new Timestamp(hd.getThoiGian().getTime()));
            ps.setString(4, hd.getDiaChi());
            ps.setInt(5, hd.getTongTien());
            ps.setInt(6, hd.getTrangThai());

            int affected = ps.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    hd.setMaHoaDon(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi thêm hóa đơn: " + e.getMessage());
        }
        return -1;
    }

    /** Cập nhật hóa đơn */
    public boolean update(HoaDonDTO hd) {
        String sql = """
            UPDATE hoadon SET
               MAKHACHHANG = ?,
               MANV         = ?,
               THOIGIAN     = ?,
               DIACHI       = ?,
               TONGTIEN     = ?,
               TRANGTHAI    = ?
            WHERE MAHOADON = ?
            """;
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hd.getKhachHang());
            ps.setInt(2, hd.getNhanVienBan());
            ps.setTimestamp(3, new Timestamp(hd.getThoiGian().getTime()));
            ps.setString(4, hd.getDiaChi());
            ps.setInt(5, hd.getTongTien());
            ps.setInt(6, hd.getTrangThai());
            ps.setInt(7, hd.getMaHoaDon());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật hóa đơn: " + e.getMessage());
            return false;
        }
    }

    /** Xóa mềm hóa đơn (TRANGTHAI = 0) */
    public boolean delete(int maHD) {
        String sql = "UPDATE hoadon SET TRANGTHAI = 0 WHERE MAHOADON = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa hóa đơn: " + e.getMessage());
            return false;
        }
    }

    /** Lấy tất cả hóa đơn đang hoạt động (TRANGTHAI <> 0) */
    public ArrayList<HoaDonDTO> selectAll() {
        var list = new ArrayList<HoaDonDTO>();
        String sql = "SELECT * FROM hoadon WHERE TRANGTHAI <> 0";
        try (Connection conn = JDBCUtil.startConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new HoaDonDTO(
                        rs.getInt("MAHOADON"),
                        rs.getInt("MAKHACHHANG"),
                        rs.getInt("MANV"),
                        rs.getTimestamp("THOIGIAN"),
                        rs.getString("DIACHI"),
                        rs.getInt("TONGTIEN"),
                        rs.getInt("TRANGTHAI")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách hóa đơn: " + e.getMessage());
        }
        return list;
    }

    /** Lấy hóa đơn theo ID */
    public HoaDonDTO selectById(int maHD) {
        String sql = "SELECT * FROM hoadon WHERE MAHOADON = ? AND TRANGTHAI <> 0";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new HoaDonDTO(
                            rs.getInt("MAHOADON"),
                            rs.getInt("MAKHACHHANG"),
                            rs.getInt("MANV"),
                            rs.getTimestamp("THOIGIAN"),
                            rs.getString("DIACHI"),
                            rs.getInt("TONGTIEN"),
                            rs.getInt("TRANGTHAI")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm hóa đơn: " + e.getMessage());
        }
        return null;
    }

    /** Lấy hóa đơn theo khoảng thời gian */
    public ArrayList<HoaDonDTO> selectByDateRange(Date from, Date to) {
        var list = new ArrayList<HoaDonDTO>();
        String sql = """
            SELECT * FROM hoadon
             WHERE THOIGIAN BETWEEN ? AND ?
               AND TRANGTHAI <> 0
            """;
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new HoaDonDTO(
                            rs.getInt("MAHOADON"),
                            rs.getInt("MAKHACHHANG"),
                            rs.getInt("MANV"),
                            rs.getTimestamp("THOIGIAN"),
                            rs.getString("DIACHI"),
                            rs.getInt("TONGTIEN"),
                            rs.getInt("TRANGTHAI")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm hóa đơn theo ngày: " + e.getMessage());
        }
        return list;
    }

    /** Tìm hóa đơn theo tên Khách hàng (cần JOIN nếu implement) */
    public ArrayList<HoaDonDTO> selectByCustomerName(String tenKH) {
        // TODO: implement JOIN với bảng KhachHang
        return new ArrayList<>();
    }

    public List<HoaDonDTO> selectFiltered(
            Integer khId,
            Integer nvId,
            Date from,
            Date to,
            Integer minTien,
            Integer maxTien
    ) {
        List<HoaDonDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT * FROM hoadon
             WHERE TRANGTHAI <> 0
            """);

        // xây dựng điều kiện động
        if (khId != null)    sql.append(" AND MAKHACHHANG = ?");
        if (nvId != null)    sql.append(" AND MANV = ?");
        if (from != null)    sql.append(" AND THOIGIAN >= ?");
        if (to != null)      sql.append(" AND THOIGIAN <= ?");
        if (minTien != null) sql.append(" AND TONGTIEN >= ?");
        if (maxTien != null) sql.append(" AND TONGTIEN <= ?");

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (khId != null)    ps.setInt(idx++, khId);
            if (nvId != null)    ps.setInt(idx++, nvId);
            if (from != null)    ps.setTimestamp(idx++, new Timestamp(from.getTime()));
            if (to != null)      ps.setTimestamp(idx++, new Timestamp(to.getTime()));
            if (minTien != null) ps.setInt(idx++, minTien);
            if (maxTien != null) ps.setInt(idx++, maxTien);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new HoaDonDTO(
                            rs.getInt("MAHOADON"),
                            rs.getInt("MAKHACHHANG"),
                            rs.getInt("MANV"),
                            rs.getTimestamp("THOIGIAN"),
                            rs.getString("DIACHI"),
                            rs.getInt("TONGTIEN"),
                            rs.getInt("TRANGTHAI")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lọc hóa đơn: " + e.getMessage());
        }
        return list;
    }

    /** Lấy Auto_increment tiếp theo */
    public int getNextMaHoaDon() {
        String sql = "SHOW TABLE STATUS WHERE `Name` = 'hoadon'";
        try (Connection conn = JDBCUtil.startConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("Auto_increment");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy Auto_increment hoadon: " + e.getMessage());
        }
        return -1;
    }
}