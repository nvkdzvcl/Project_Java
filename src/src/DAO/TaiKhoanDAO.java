package DAO;

import DTO.TaiKhoanDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<TaiKhoanDTO> selectAll(){
        ArrayList<TaiKhoanDTO> danhSachTaiKhoan = new ArrayList<TaiKhoanDTO>();

        try{
            Connection conn = JDBCUtil.startConnection();
            String selectQuery = "SELECT * FROM taikhoan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while(rs.next()){
                String tenNguoiDung = rs.getString("TENNGUOIDUNG");
                String matKhau = rs.getString("MATKHAU");
                String chucVu = rs.getString("CHUCVU");
                int trangThai = rs.getInt("TRANGTHAI");
                int maNV = rs.getInt("MANV");
                TaiKhoanDTO taiKhoan = new TaiKhoanDTO(tenNguoiDung, matKhau, chucVu, trangThai, maNV);
                danhSachTaiKhoan.add(taiKhoan);
            }
            JDBCUtil.closeConnection(conn);
        }
        catch(SQLException sqlException){
            System.out.println("Lỗi hiển thị danh sách tài khoản" + sqlException.getMessage());
        }

        return danhSachTaiKhoan;
    }

    public TaiKhoanDTO selectByMANV(String maNV){
        TaiKhoanDTO taiKhoan = null;
        try{
            Connection conn = JDBCUtil.startConnection();
            String selectByIDQuery = "SELECT * FROM taikhoan WHERE MANV = ?";
            PreparedStatement prst = conn.prepareStatement(selectByIDQuery);
            prst.setString(1, maNV);
            ResultSet rs = prst.executeQuery();
            while(rs.next()){
                String tenNguoiDung = rs.getString("TENNGUOIDUNG");
                String matKhau = rs.getString("MATKHAU");
                String chucVu = rs.getString("CHUCVU");
                int trangThai = rs.getInt("TRANGTHAI");
                int manv = rs.getInt("MANV");
                taiKhoan = new TaiKhoanDTO(tenNguoiDung, matKhau, chucVu, trangThai, manv);
                return taiKhoan;
            }
            JDBCUtil.closeConnection(conn);
        }
        catch (SQLException sqlException){
            System.out.println("Lỗi tìm tài khoản dựa trên mã nhân viên" + sqlException.getMessage());
        }

        return taiKhoan;
    }

    public TaiKhoanDTO selectByTENNGUOIDUNG(String tenNguoiDung){
        TaiKhoanDTO taiKhoan = null;
        try{
            Connection conn = JDBCUtil.startConnection();
            String selectByIDQuery = "SELECT * FROM taikhoan WHERE TENNGUOIDUNG = ?";
            PreparedStatement prst = conn.prepareStatement(selectByIDQuery);
            prst.setString(1, tenNguoiDung);
            ResultSet rs = prst.executeQuery();
            while(rs.next()){
                String tennguoidung = rs.getString("TENNGUOIDUNG");
                String matKhau = rs.getString("MATKHAU");
                String chucVu = rs.getString("CHUCVU");
                int trangThai = rs.getInt("TRANGTHAI");
                int maNV = rs.getInt("MANV");
                taiKhoan = new TaiKhoanDTO(tennguoidung, matKhau, chucVu, trangThai, maNV);
                return taiKhoan;
            }
            JDBCUtil.closeConnection(conn);
        }
        catch (SQLException sqlException){
            System.out.println("Lỗi tìm tài khoản dựa trên tên người dùng" + sqlException.getMessage());
        }

        return taiKhoan;
    }

}
