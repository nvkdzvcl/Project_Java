package DAO;

import DTO.HoaDonDTO;
import config.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;

public class HoaDonDAO {

    private static HoaDonDAO instance;

    public static HoaDonDAO getInstance() {
       return new HoaDonDAO();
    }

    public int insert(HoaDonDTO hoaDon) {
        String sql = "INSERT INTO hoadon (MAHOADON, KHACHHANG, NHANVIENBAN, THOIGIAN, TRANGTHAI) VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setInt(1, hoaDon.getMaHoaDon());
            prst.setString(2, hoaDon.getKhachhang());
            prst.setString(3, hoaDon.getNhanVienBan());
            prst.setDate(4, new java.sql.Date(hoaDon.getThoigian().getTime()));
            return prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi thêm hóa đơn: " + e.getMessage());
            return 0;
        }
    }

    public int update(HoaDonDTO hoaDon) {
        String sql = "UPDATE hoadon SET KHACHHANG = ?, NHANVIENBAN = ?, THOIGIAN = ? WHERE MAHOADON = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, hoaDon.getKhachhang());
            prst.setString(2, hoaDon.getNhanVienBan());
            prst.setDate(3, new java.sql.Date(hoaDon.getThoigian().getTime()));
            prst.setInt(4, hoaDon.getMaHoaDon());
            return prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật hóa đơn: " + e.getMessage());
            return 0;
        }
    }

    public int delete(HoaDonDTO hoaDon) {
        String sql = "UPDATE hoadon SET TRANGTHAI = 0 WHERE MAHOADON = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setInt(1, hoaDon.getMaHoaDon());
            return prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi xóa hóa đơn (mềm): " + e.getMessage());
            return 0;
        }
    }

    public ArrayList<HoaDonDTO> selectAll() {
        ArrayList<HoaDonDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                        rs.getInt("MAHOADON"),
                        rs.getString("KHACHHANG"),
                        rs.getString("NHANVIENBAN"),
                        rs.getDate("THOIGIAN")
                );
                danhSach.add(hd);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi hiển thị danh sách hóa đơn: " + e.getMessage());
        }

        return danhSach;
    }

    public HoaDonDTO selectById(int maHoaDon) {
        HoaDonDTO hd = null;
        String sql = "SELECT * FROM hoadon WHERE MAHOADON = ? AND TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setInt(1, maHoaDon);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                hd = new HoaDonDTO(
                        rs.getInt("MAHOADON"),
                        rs.getString("KHACHHANG"),
                        rs.getString("NHANVIENBAN"),
                        rs.getDate("THOIGIAN")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm hóa đơn theo mã hóa đơn: " + e.getMessage());
        }

        return hd;
    }

    public ArrayList<HoaDonDTO> getByKhachHang(String tenKH) {
        ArrayList<HoaDonDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE KHACHHANG LIKE ? AND TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, "%" + tenKH + "%");
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                        rs.getInt("MAHOADON"),
                        rs.getString("KHACHHANG"),
                        rs.getString("NHANVIENBAN"),
                        rs.getDate("THOIGIAN")
                );
                danhSach.add(hd);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm hóa đơn theo khách hàng: " + e.getMessage());
        }

        return danhSach;
    }
}

