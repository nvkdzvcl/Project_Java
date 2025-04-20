package DAO;

import DTO.PhieuNhapDTO;
import config.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;

public class PhieuNhapDAO {

    private static PhieuNhapDAO instance;

    public static PhieuNhapDAO getInstance() {
        return new PhieuNhapDAO();
    }

    public int insert(PhieuNhapDTO phieuNhap) {
        String sql = "INSERT INTO phieunhap (MAPHIEUNHAP, NHACUNGCAP, NHANVIENNHAP, NGAY, TONGTIEN, TRANGTHAI) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setInt(1, phieuNhap.getMaPhieuNhap());
            prst.setString(2, phieuNhap.getNhaCungCap());
            prst.setString(3, phieuNhap.getNhanVienNhap());
            prst.setDate(4, new java.sql.Date(phieuNhap.getNgay().getTime()));
            prst.setInt(5, phieuNhap.getTongTien());

            return prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi thêm phiếu nhập: " + e.getMessage());
            return 0;
        }
    }

    public int update(PhieuNhapDTO phieuNhap) {
        String sql = "UPDATE phieunhap SET NHACUNGCAP = ?, NHANVIENNHAP = ?, NGAY = ?, TONGTIEN = ? WHERE MAPHIEUNHAP = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setString(1, phieuNhap.getNhaCungCap());
            prst.setString(2, phieuNhap.getNhanVienNhap());
            prst.setDate(3, new java.sql.Date(phieuNhap.getNgay().getTime()));
            prst.setInt(4, phieuNhap.getTongTien());
            prst.setInt(5, phieuNhap.getMaPhieuNhap());

            return prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật phiếu nhập: " + e.getMessage());
            return 0;
        }
    }

    public int delete(PhieuNhapDTO phieuNhap) {
        String sql = "UPDATE phieunhap SET TRANGTHAI = 0 WHERE MAPHIEUNHAP = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setInt(1, phieuNhap.getMaPhieuNhap());
            return prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi xóa phiếu nhập: " + e.getMessage());
            return 0;
        }
    }

    public ArrayList<PhieuNhapDTO> selectAll() {
        ArrayList<PhieuNhapDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap WHERE TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                        rs.getInt("MAPHIEUNHAP"),
                        rs.getString("NHACUNGCAP"),
                        rs.getString("NHANVIENNHAP"),
                        rs.getDate("NGAY"),
                        rs.getInt("TONGTIEN")
                );
                danhSach.add(pn);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách phiếu nhập: " + e.getMessage());
        }

        return danhSach;
    }

    public PhieuNhapDTO selectById(int maPhieuNhap) {
        String sql = "SELECT * FROM phieunhap WHERE MAPHIEUNHAP = ? AND TRANGTHAI = 1";
        PhieuNhapDTO pn = null;

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setInt(1, maPhieuNhap);
            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                pn = new PhieuNhapDTO(
                        rs.getInt("MAPHIEUNHAP"),
                        rs.getString("NHACUNGCAP"),
                        rs.getString("NHANVIENNHAP"),
                        rs.getDate("NGAY"),
                        rs.getInt("TONGTIEN")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm phiếu nhập theo mã: " + e.getMessage());
        }

        return pn;
    }

    public ArrayList<PhieuNhapDTO> getByNhaCungCap(String nhaCungCap) {
        ArrayList<PhieuNhapDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap WHERE NHACUNGCAP LIKE ? AND TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setString(1, "%" + nhaCungCap + "%");
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                        rs.getInt("MAPHIEUNHAP"),
                        rs.getString("NHACUNGCAP"),
                        rs.getString("NHANVIENNHAP"),
                        rs.getDate("NGAY"),
                        rs.getInt("TONGTIEN")
                );
                danhSach.add(pn);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm phiếu nhập theo nhà cung cấp: " + e.getMessage());
        }

        return danhSach;
    }
}
