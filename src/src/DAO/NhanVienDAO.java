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
            String sql = "select * from NhanVien ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienDTO dto = new NhanVienDTO();
                dto.setMaNV(rs.getString("MANV"));
                dto.setHoTen(rs.getString("HOTENNV"));
                dto.setGioiTinh(rs.getString("GIOITINH"));
                dto.setNgaySinh(rs.getString("NGAYSINH"));
                dto.setEmail(rs.getString("EMAIL"));
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public boolean insert(NhanVienDTO DTO) {
        String sql = "insert into NhanVien values(?,?,?,?,?)";
        try (Connection conn = JDBCUtil.startConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, DTO.getMaNV());
            ps.setString(2, DTO.getHoTen());
            ps.setString(3, DTO.getGioiTinh());
            ps.setString(4, DTO.getNgaySinh());
            ps.setString(5, DTO.getEmail());
            int result = ps.executeUpdate();
            if (result > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean update(NhanVienDTO DTO) {
        String sql="Update NhanVien SET HOTENNV =? and GIOITINH =? and NGAYSINH =? and EMAIL =? where MANV =?";
        try(Connection conn = JDBCUtil.startConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(2,DTO.getHoTen());
            ps.setString(1,DTO.getGioiTinh());
            ps.setString(3,DTO.getNgaySinh());
            ps.setString(4,DTO.getEmail());
            return ps.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean delete(String id) {
        String sql = "UPDATE nhanvien SET Trangthai=0 from NhanVien where MANV =?";
        try (Connection conn = JDBCUtil.startConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            return ps.executeUpdate(sql) >0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static NhanVienDTO getonenhanvien(String id)
    {
        String sql = "select * from NhanVien where MANV =?";
        try (Connection conn=JDBCUtil.startConnection())
        {
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            try(ResultSet rs=ps.executeQuery())
            {
                NhanVienDTO DTO = new NhanVienDTO();
                DTO.setMaNV(rs.getString("MANV"));
                DTO.setHoTen(rs.getString("HOTENNV"));
                DTO.setGioiTinh(rs.getString("GIOITINH"));
                DTO.setNgaySinh(rs.getString("NGAYSINH"));
                DTO.setEmail(rs.getString("EMAIL"));
                return DTO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
return null;
    }
}
