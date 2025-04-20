package DAO;

import DTO.TaiKhoanDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {

    public static TaiKhoanDAO getInstance() {
        return new TaiKhoanDAO();
    }

    public int insert(TaiKhoanDTO taiKhoan) {
        String sql = "INSERT INTO taikhoan (TENNGUOIDUNG, MATKHAU, CHUCVU, MANV) VALUES (?,?,?,?)";
        try (Connection conn = JDBCUtil.startConnection();
            PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, taiKhoan.getTenNguoiDung());
            prst.setString(2, taiKhoan.getMatKhau());
            prst.setString(3, taiKhoan.getChucVu());
            prst.setInt(4, taiKhoan.getMaNV());
            return prst.executeUpdate();
        }
        catch (SQLException sqlException){
            System.out.println("Lỗi thêm tài khoản" + sqlException.getMessage());
            return 0;
        }
    }

    public int delete(TaiKhoanDTO taiKhoan) {
        int kq = 0;
        String sql = "UPDATE taikhoan SET TRANGTHAI = 0 WHERE TENNGUOIDUNG = ?";
        try (Connection conn = JDBCUtil.startConnection();
            PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, taiKhoan.getTenNguoiDung());
            return prst.executeUpdate();
        }
        catch (SQLException sqlException){
            System.out.println("Lỗi xoá tài khoản" + sqlException.getMessage());
            return 0;
        }
    }

    public int update(TaiKhoanDTO taiKhoan){
        int kq = 0;
        String sql = "UPDATE `taikhoan` SET `MATKHAU` = ?, `MANV` = ?, `CHUCVU` = ? WHERE `TENNGUOIDUNG` =?";
        try (Connection conn = JDBCUtil.startConnection();
            PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, taiKhoan.getMatKhau());
            prst.setInt(2, taiKhoan.getMaNV());
            prst.setString(3, taiKhoan.getChucVu());
            prst.setString(4, taiKhoan.getTenNguoiDung());
            return prst.executeUpdate();
        }
        catch (SQLException sqlException){
            System.out.println("Lỗi cập nhật tài khoản" + sqlException.getMessage());
            return 0;
        }
    }

    public List<TaiKhoanDTO> selectAll(){
        List<TaiKhoanDTO> danhSachTaiKhoan = new ArrayList<TaiKhoanDTO>();
        String sql = "SELECT TENNGUOIDUNG, MATKHAU, CHUCVU, TRANGTHAI, MANV FROM taikhoan";
        try (Connection conn = JDBCUtil.startConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) {
                TaiKhoanDTO tk = new TaiKhoanDTO(
                        rs.getString("TENNGUOIDUNG"),
                        rs.getString("MATKHAU"),
                        rs.getString("CHUCVU"),
                        rs.getInt("TRANGTHAI"),
                        rs.getInt("MANV")
                );
                danhSachTaiKhoan.add(tk);
            }
        }
        catch(SQLException sqlException){
            System.out.println("Lỗi hiển thị danh sách tài khoản" + sqlException.getMessage());
        }

        return danhSachTaiKhoan;
    }

    public TaiKhoanDTO selectByMANV(int maNV) {
        String sql = """
                SELECT TENNGUOIDUNG, MATKHAU, CHUCVU, TRANGTHAI, MANV
                FROM taikhoan
                WHERE MANV = ?
        """;
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setInt(1, maNV);
            try (ResultSet rs = prst.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoanDTO(
                            rs.getString("TENNGUOIDUNG"),
                            rs.getString("MATKHAU"),
                            rs.getString("CHUCVU"),
                            rs.getInt("TRANGTHAI"),
                            rs.getInt("MANV")
                    );
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Lỗi tìm tài khoản theo MANV: " + sqlException.getMessage());
        }
        return null;
    }


    public TaiKhoanDTO selectByTENNGUOIDUNG(String tenNguoiDung){
        String sql = """
                SELECT TENNGUOIDUNG, MATKHAU, CHUCVU, TRANGTHAI, MANV
                FROM taikhoan
                WHERE TENNGUOIDUNG = ?
                """;
        try (Connection conn = JDBCUtil.startConnection();
            PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, tenNguoiDung);
            try (ResultSet rs = prst.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoanDTO(
                            rs.getString("TENNGUOIDUNG"),
                            rs.getString("MATKHAU"),
                            rs.getString("CHUCVU"),
                            rs.getInt("TRANGTHAI"),
                            rs.getInt("MAVN")
                    );
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Lỗi tìm tài khoản theo TENNGUOIDUNG: " + sqlException.getMessage());
        }
        return null;
    }

}
