package DAO;

import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhachHangDAO {
    public KhachHangDAO() {

    }
    public ArrayList<KhachHangDTO> getallkhachhang() {
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        String sql = "select * from khachhang";
        try (Connection conn= JDBCUtil.startConnection())
        {
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                KhachHangDTO dto = new KhachHangDTO();
                dto.setMaKhachHang(rs.getInt("MAKHACHHANG"));
                dto.setTenKhachHang(rs.getString("TENKHACHHANG"));
                dto.setDiachi(rs.getString("DIACHI"));
                dto.setSoDienThoai(rs.getString("SDT"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean insertkhachhang(KhachHangDTO dto) {
        String sql = "insert into khachhang (MAKHACHHANG, TENKHACHHANG, DIACHI,SDT ) values(?,?,?,?)";
        try(Connection conn= JDBCUtil.startConnection())
        {
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1, dto.getMaKhachHang());
            ps.setString(2, dto.getTenKhachHang());
            ps.setString(3, dto.getDiachi());
            ps.setString(4, dto.getSoDienThoai());
            return ps.executeUpdate() >0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean updatekhachhang(KhachHangDTO dto) {
        String sql="Update KHACHHANG set TENKHACHHANG =?  SDT= ? DIACHI= ?  where MAKHACHHANG=?";
        try (Connection conn=JDBCUtil.startConnection())
        {
            PreparedStatement ps=conn.prepareStatement(sql);

            ps.setString(1,dto.getTenKhachHang());
            ps.setString(2,dto.getSoDienThoai());
            ps.setString(3,dto.getDiachi());
            ps.setInt(4,dto.getMaKhachHang());
            return ps.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean deletekhachhang(int id) {
        String sql="set TRANGTHAI = 0 where MAKHACHHANG=?";
        try (Connection conn=JDBCUtil.startConnection())
        {
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static KhachHangDTO getonekhachhang(int id) {
        KhachHangDTO dto = new KhachHangDTO();
        String sql = "select * from khachhang where MAKHACHHANG=?";
        try(Connection conn=JDBCUtil.startConnection())
        {
            KhachHangDTO dto1=new KhachHangDTO();
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                dto.setMaKhachHang(id);
                dto.setTenKhachHang(rs.getString("TENKHACHHANG"));
                dto.setDiachi(rs.getString("DIACHI"));
                dto.setSoDienThoai(rs.getString("SDT"));
            }
            return dto;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
