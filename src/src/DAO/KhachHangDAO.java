package DAO;

import DTO.KhachHangDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {
    private static final KhachHangDAO INSTANCE = new KhachHangDAO();
    public static KhachHangDAO getInstance() {
        return INSTANCE;
    }

    private KhachHangDAO() {
        // Private constructor để đảm bảo singleton
    }

    /**
     * Lấy tất cả khách hàng còn hoạt động (TRANGTHAI=1)
     */
    public ArrayList<KhachHangDTO> getallkhachhang() {
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE TRANGTHAI = 1";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhachHangDTO dto = new KhachHangDTO();
                dto.setMaKhachHang(rs.getInt("MAKHACHHANG"));
                dto.setTenKhachHang(rs.getString("TENKHACHHANG"));
                dto.setDiachi(rs.getString("DIACHI"));
                dto.setSoDienThoai(rs.getString("SDT"));
                list.add(dto);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách khách hàng: " + e.getMessage());
        }
        return list;
    }

    /**
     * Lấy 1 khách hàng theo ID
     */
    public KhachHangDTO selectById(int id) {
        String sql = "SELECT * FROM khachhang WHERE MAKHACHHANG = ? AND TRANGTHAI = 1";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    KhachHangDTO dto = new KhachHangDTO();
                    dto.setMaKhachHang(rs.getInt("MAKHACHHANG"));
                    dto.setTenKhachHang(rs.getString("TENKHACHHANG"));
                    dto.setDiachi(rs.getString("DIACHI"));
                    dto.setSoDienThoai(rs.getString("SDT"));
                    return dto;
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi lấy khách hàng theo ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Thêm mới khách hàng
     */
    public boolean insertkhachhang(KhachHangDTO dto) {
        String sql = "INSERT INTO khachhang (TENKHACHHANG, DIACHI, SDT, TRANGTHAI) VALUES (?, ?, ?, 1)";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dto.getTenKhachHang());
            ps.setString(2, dto.getDiachi());
            ps.setString(3, dto.getSoDienThoai());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        dto.setMaKhachHang(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi thêm khách hàng: " + e.getMessage());
        }
        return false;
    }

    /**
     * Cập nhật thông tin khách hàng
     */
    public boolean updatekhachhang(KhachHangDTO dto) {
        String sql = "UPDATE khachhang SET TENKHACHHANG = ?, DIACHI = ?, SDT = ? WHERE MAKHACHHANG = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getTenKhachHang());
            ps.setString(2, dto.getDiachi());
            ps.setString(3, dto.getSoDienThoai());
            ps.setInt(4, dto.getMaKhachHang());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật khách hàng: " + e.getMessage());
        }
        return false;
    }

    /**
     * Xóa mềm khách hàng (TRANGTHAI = 0)
     */
    public boolean deletekhachhang(int id) {
        String sql = "UPDATE khachhang SET TRANGTHAI = 0 WHERE MAKHACHHANG = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi xóa khách hàng: " + e.getMessage());
        }
        return false;
    }

}
