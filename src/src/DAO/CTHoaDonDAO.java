package DAO;

import DTO.CTHoaDonDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CTHoaDonDAO {
    private static final CTHoaDonDAO INSTANCE = new CTHoaDonDAO();
    public static CTHoaDonDAO getInstance() { return INSTANCE; }


    //Lấy tất cả chi tiết của hóa đơn maHD
    public List<CTHoaDonDTO> getByMaHoaDon(int maHD) {
        List<CTHoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CTHOADON WHERE MAHOADON = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CTHoaDonDTO dto = new CTHoaDonDTO(
                            rs.getInt("MAHOADON"),
                            rs.getInt("MASP"),
                            rs.getInt("DONGIA"),
                            rs.getInt("SOLUONG")
                    );
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi load chi tiết hóa đơn: " + e.getMessage());
        }
        return list;
    }

    /**
     * Chèn 1 bản ghi chi tiết hóa đơn
     */
    public boolean insert(int maHD, int maSP, int donGia, int soLuong) {
        String sql = """
            INSERT INTO CTHOADON (MAHOADON, MASP, DONGIA, SOLUONG)
            VALUES (?,?,?,?)
            """;
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHD);
            ps.setInt(2, maSP);
            ps.setInt(3, donGia);
            ps.setInt(4, soLuong);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi thêm chi tiết hóa đơn: " + e.getMessage());
            return false;
        }
    }
}
