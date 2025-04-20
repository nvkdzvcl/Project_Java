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
        String sql = "INSERT INTO hoadon (MAHOADON, KHACHHANG, NHANVIENBAN, THOIGIAN, TONGTIEN, TRANGTHAI) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setInt(1, hoaDon.getMaHoaDon());
            prst.setString(2, hoaDon.getKhachhang());
            prst.setString(3, hoaDon.getNhanVienBan());
            prst.setDate(4, new java.sql.Date(hoaDon.getThoigian().getTime()));
            prst.setInt(5, hoaDon.getTongTien());

            return prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi thêm hóa đơn: " + e.getMessage());
            return 0;
        }
    }

    public int update(HoaDonDTO hoaDon) {
        String sql = "UPDATE hoadon SET KHACHHANG = ?, NHANVIENBAN = ?, THOIGIAN = ?, TONGTIEN = ? WHERE MAHOADON = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setString(1, hoaDon.getKhachhang());
            prst.setString(2, hoaDon.getNhanVienBan());
            prst.setDate(3, new java.sql.Date(hoaDon.getThoigian().getTime()));
            prst.setInt(4, hoaDon.getTongTien());
            prst.setInt(5, hoaDon.getMaHoaDon());

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
        ArrayList<HoaDonDTO> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                        rs.getInt("MAHOADON"),
                        rs.getString("KHACHHANG"),
                        rs.getString("NHANVIENBAN"),
                        rs.getDate("THOIGIAN"),
                        rs.getInt("TONGTIEN")
                );
                danhSachHoaDon.add(hd);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi hiển thị danh sách hóa đơn: " + e.getMessage());
        }

        return danhSachHoaDon;
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
                        rs.getDate("THOIGIAN"),
                        rs.getInt("TONGTIEN")
                );
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm hóa đơn theo mã hóa đơn: " + e.getMessage());
        }

        return hd;
    }

    public ArrayList<HoaDonDTO> getByKhachHang(String tenKH) {
        ArrayList<HoaDonDTO> danhSachKhachHang = new ArrayList<>();
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
                        rs.getDate("THOIGIAN"),
                        rs.getInt("TONGTIEN")
                );
                danhSachKhachHang.add(hd);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm hóa đơn theo khách hàng: " + e.getMessage());
        }

        return danhSachKhachHang;
    }
    // Khôi phục hóa đơn đã bị xóa mềm (TRANGTHAI = 0)
    public int restore(int maHoaDon) {
        String sql = "UPDATE hoadon SET TRANGTHAI = 1 WHERE MAHOADON = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setInt(1, maHoaDon);
            return prst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Lỗi khôi phục hóa đơn: " + e.getMessage());
            return 0;
        }
    }

    // Lấy danh sách hóa đơn trong khoảng thời gian
    public ArrayList<HoaDonDTO> getByDateRange(Date from, Date to) {
        ArrayList<HoaDonDTO> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE THOIGIAN BETWEEN ? AND ? AND TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setDate(1, new java.sql.Date(from.getTime()));
            prst.setDate(2, new java.sql.Date(to.getTime()));
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                        rs.getInt("MAHOADON"),
                        rs.getString("KHACHHANG"),
                        rs.getString("NHANVIENBAN"),
                        rs.getDate("THOIGIAN"),
                        rs.getInt("TONGTIEN")
                );
                danhSachHoaDon.add(hd);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm hóa đơn theo khoảng thời gian: " + e.getMessage());
        }

        return danhSachHoaDon;
    }

}
