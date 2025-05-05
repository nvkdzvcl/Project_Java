package DAO;

import DTO.NhanVienDTO;
import com.mysql.cj.jdbc.JdbcConnection;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhanVienDAO {
    public NhanVienDAO() {

    }

    public ArrayList<NhanVienDTO> getallnhanvien() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        try (Connection conn = JDBCUtil.startConnection()) {
            String sql = "select * from NhanVien  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienDTO dto = new NhanVienDTO();
                dto.setMaNV(rs.getInt("MANV"));
                dto.setHoTen(rs.getString("HOTENNV"));
                dto.setGioiTinh(rs.getString("GIOITINH"));
                dto.setNgaySinh(rs.getString("NGAYSINH"));
                dto.setSdt(rs.getString("SDT"));
                dto.setEmail(rs.getString("EMAIL"));
                dto.setDiachi(rs.getString("DIACHI"));
                dto.setTrangThai(rs.getInt("TRANGTHAI"));
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public ArrayList<NhanVienDTO> gettennhanvien() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        try (Connection conn = JDBCUtil.startConnection()) {
            String sql = "select HOTENNV from NhanVien ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienDTO dto = new NhanVienDTO();
                dto.setHoTen(rs.getString("HOTENNV"));
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(NhanVienDTO DTO) {
        String sql = "INSERT INTO NhanVien (HOTENNV, GIOITINH, NGAYSINH, SDT, EMAIL,DIACHI) VALUES (?,?,?,?,?,?)";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, DTO.getHoTen());
            ps.setString(2, DTO.getGioiTinh());
            ps.setString(3, DTO.getNgaySinh());
            ps.setString(4, DTO.getSdt());
            ps.setString(5, DTO.getEmail());
            ps.setString(6, DTO.getDiachi());
            int result = ps.executeUpdate();
            if (result > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(NhanVienDTO DTO) {
        String sql = "Update NhanVien SET HOTENNV =? , GIOITINH =? , NGAYSINH =? , SDT =? ,EMAIL =? , DIACHI =? ,TRANGTHAI = ? where MANV =?";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, DTO.getHoTen());
            ps.setString(2, DTO.getGioiTinh());
            ps.setString(3, DTO.getNgaySinh());
            ps.setString(4, DTO.getSdt());
            ps.setString(5, DTO.getEmail());
            ps.setString(6, DTO.getDiachi());
            ps.setInt(7,DTO.getTrangThai());
            ps.setInt(8,DTO.getMaNV());
            return ps.executeUpdate() > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(int id) {
        String sql = "UPDATE nhanvien SET Trangthai= 0  where MANV =?";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static NhanVienDTO getonenhanvien(int id) {
        String sql = "SELECT * FROM NhanVien WHERE MANV = ?";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NhanVienDTO DTO = new NhanVienDTO();
                    DTO.setMaNV(rs.getInt("MANV"));
                    DTO.setHoTen(rs.getString("HOTENNV"));
                    DTO.setGioiTinh(rs.getString("GIOITINH"));
                    DTO.setNgaySinh(rs.getString("NGAYSINH"));
                    DTO.setSdt(rs.getString("SDT"));
                    DTO.setEmail(rs.getString("EMAIL"));
                    DTO.setDiachi(rs.getString("DIACHI"));
                    DTO.setTrangThai(rs.getInt("TRANGTHAI"));
                    return DTO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
