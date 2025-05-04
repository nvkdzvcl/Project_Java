package DAO;

import DTO.CTPhieuNhapDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class CTPhieuNhapDAO {
    private static final CTPhieuNhapDAO INSTANCE = new CTPhieuNhapDAO();
    public static CTPhieuNhapDAO getInstance() { return INSTANCE; }

    /** Trả về list các CT của phiếu nhập maPN */
    public ArrayList<CTPhieuNhapDTO> getByMaPhieuNhap(int maPN) {
        ArrayList<CTPhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CTPHIEUNHAP WHERE MAPHIEUNHAP = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new CTPhieuNhapDTO(
                            rs.getInt("MAPHIEUNHAP"),
                            rs.getInt("MASP"),
                            rs.getInt("DONGIA"),
                            rs.getInt("SOLUONG")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi load chi tiết phiếu nhập: " + e.getMessage());
        }
        return list;
    }

    /** (Nếu cần) Insert chi tiết, update, delete ở đây... */
}
