package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.DTO.NhanVienDTO;
import com.config.JDBCConnection;

public class NhanVienDAO {

    public static ArrayList<NhanVienDTO> getAllNhanVien() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(rs.getInt("maNhanVien"));
                nv.setTenNV(rs.getString("tenNhanVien"));
                nv.setNgaySinh(rs.getDate("ngaySinh"));
                nv.setGioiTinh(rs.getString("gioiTinh")); // INT theo DTO
                nv.setDiaChi(rs.getString("diaChi"));
                nv.setSdt(rs.getString("sdt"));
                nv.setEmail(rs.getString("email"));
                nv.setChucVu(rs.getString("chucVu"));
                nv.setTrangThai(rs.getInt("trangThai"));

                list.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static NhanVienDTO getNhanVienById(int id) {
        String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";

        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NhanVienDTO nv = new NhanVienDTO();
                    nv.setMaNV(rs.getInt("maNhanVien"));
                    nv.setTenNV(rs.getString("tenNhanVien"));
                    nv.setNgaySinh(rs.getDate("ngaySinh"));
                    nv.setGioiTinh(rs.getString("gioiTinh"));
                    nv.setDiaChi(rs.getString("diaChi"));
                    nv.setSdt(rs.getString("sdt"));
                    nv.setEmail(rs.getString("email"));
                    nv.setChucVu(rs.getString("chucVu"));
                    nv.setTrangThai(rs.getInt("trangThai"));
                    return nv;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean insertNhanVien(NhanVienDTO nv) {
        String sql = "INSERT INTO NhanVien (tenNhanVien, ngaySinh, gioiTinh, diaChi, sdt, email, chucVu, trangThai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getTenNV());
            ps.setDate(2, nv.getNgaySinh());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getSdt());
            ps.setString(6, nv.getEmail());
            ps.setString(7, nv.getChucVu());
            ps.setInt(8, nv.getTrangThai());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateNhanVien(NhanVienDTO nv) {
        String sql = "UPDATE NhanVien SET tenNhanVien=?, ngaySinh=?, gioiTinh=?, diaChi=?, sdt=?, email=?, chucVu=?, trangThai=? " +
                     "WHERE maNhanVien=?";

        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getTenNV());
            ps.setDate(2, nv.getNgaySinh());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getSdt());
            ps.setString(6, nv.getEmail());
            ps.setString(7, nv.getChucVu());
            ps.setInt(8, nv.getTrangThai());
            ps.setInt(9, nv.getMaNV());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteNhanVien(int id) {
        String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";

        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
