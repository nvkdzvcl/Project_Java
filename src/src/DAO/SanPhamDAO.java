package DAO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SanPhamDAO {


    public ArrayList<SanPhamDTO> getallsanpham() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        try (Connection conn = JDBCUtil.startConnection()) {
            String sql = "select * from sanpham where TRANGTHAI =1 ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamDTO dto = new SanPhamDTO();
                dto.setMaSP(rs.getInt("MASP"));
                dto.setTenSP(rs.getString("TENSP"));
                dto.setThuongHieu(rs.getString("THUONGHIEU"));
                dto.setXuatXu(rs.getString("XUATXU"));
                dto.setMauSac(rs.getString("MAUSAC"));
                dto.setKichThuoc(rs.getString("KICHTHUOC"));
                dto.setSoLuong(rs.getInt("SOLUONG"));
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public boolean insert(SanPhamDTO DTO) {
        String sql = "INSERT INTO SanPham(MASP, TENSP, THUONGHIEU, XUATXU,MAUSAC, KICHTHUOC, SOLUONG) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, DTO.getMaSP());
            ps.setString(2, DTO.getTenSP());
            ps.setString(3, DTO.getThuongHieu());
            ps.setString(4, DTO.getXuatXu());
            ps.setString(5, DTO.getMauSac());
            ps.setString(6, DTO.getKichThuoc());
            ps.setInt(7, DTO.getSoLuong());
            int result = ps.executeUpdate();
            if (result > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean update(SanPhamDTO DTO) {
        String sql = "Update sanpham SET TENSP =? , THUONGHIEU =? , XUATXU =? , MAUSAC =? ,KICHTHUOC =? , SOLUONG =? where MANV =?";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, DTO.getTenSP());
            ps.setString(2, DTO.getThuongHieu());
            ps.setString(3, DTO.getXuatXu());
            ps.setString(4, DTO.getMauSac());
            ps.setString(5, DTO.getKichThuoc());
            ps.setInt(6, DTO.getSoLuong());
            ps.setInt(7, DTO.getMaSP());
            return ps.executeUpdate() > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean delete(int id) {
        String sql = "UPDATE sanpham SET Trangthai= 0  where MASP =?";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static SanPhamDTO getonesanpham(int id) {
        String sql = "SELECT * FROM sanpham WHERE MASP = ?";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SanPhamDTO dto = new SanPhamDTO();
                    dto.setMaSP(rs.getInt("MASP"));
                    dto.setTenSP(rs.getString("TENSP"));
                    dto.setThuongHieu(rs.getString("THUONGHIEU"));
                    dto.setXuatXu(rs.getString("XUATXU"));
                    dto.setMauSac(rs.getString("MAUSAC"));
                    dto.setKichThuoc(rs.getString("KICHTHUOC"));
                    dto.setSoLuong(rs.getInt("SOLUONG"));
                    return dto;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}