package DAO;

import DTO.PhieuNhapDTO;
import config.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapDAO {
    private static final PhieuNhapDAO INSTANCE = new PhieuNhapDAO();
    public static PhieuNhapDAO getInstance() {
        return INSTANCE;
    }

    public int insert(PhieuNhapDTO phieuNhap) {
        String sql = "INSERT INTO phieunhap " +
                "(NHACUNGCAP, NHANVIENNHAP, NGAY, TONGTIEN, TRANGTHAI) " +
                "VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prst.setString(1, phieuNhap.getNhaCungCap());
            prst.setInt(2, phieuNhap.getNhanVienNhap());
            prst.setDate(3, new java.sql.Date(phieuNhap.getNgay().getTime()));
            prst.setInt(4, phieuNhap.getTongTien());

            int affectedRows = prst.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet rs = prst.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    phieuNhap.setMaPhieuNhap(newId);
                    return newId;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi thêm phiếu nhập: " + e.getMessage());
        }
        return -1;
    }


    public boolean update(PhieuNhapDTO phieuNhap) {
        String sql = "UPDATE phieunhap SET NHACUNGCAP = ?, NHANVIENNHAP = ?, NGAY = ?, TONGTIEN = ? " +
                "WHERE MAPHIEUNHAP = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setString(1, phieuNhap.getNhaCungCap());
            prst.setInt(2, phieuNhap.getNhanVienNhap());
            prst.setDate(3, new java.sql.Date(phieuNhap.getNgay().getTime()));
            prst.setInt(4, phieuNhap.getTongTien());
            prst.setInt(5, phieuNhap.getMaPhieuNhap());

            return prst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật phiếu nhập: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int maPhieuNhap) {
        String sql = "UPDATE phieunhap SET TRANGTHAI = 0 WHERE MAPHIEUNHAP = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setInt(1, maPhieuNhap);
            return prst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa phiếu nhập: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<PhieuNhapDTO> selectAll() {
        ArrayList<PhieuNhapDTO> danhSachPhieuNhap = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap WHERE TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                        rs.getInt("MAPHIEUNHAP"),
                        rs.getString("NHACUNGCAP"),
                        rs.getInt("NHANVIENNHAP"),
                        rs.getDate("NGAY"),
                        rs.getInt("TONGTIEN")
                );
                danhSachPhieuNhap.add(pn);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách phiếu nhập: " + e.getMessage());
        }

        return danhSachPhieuNhap;
    }

    public ArrayList<PhieuNhapDTO> filter(Integer nhanVienId, Date from, Date to,
                                     Integer minTien, Integer maxTien) {
        ArrayList<PhieuNhapDTO> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT * FROM phieunhap WHERE TRANGTHAI = 1");

        if (nhanVienId != null && nhanVienId > 0) sb.append(" AND NHANVIENNHAP = ?");
        if (from != null && to != null)     sb.append(" AND NGAY BETWEEN ? AND ?");
        if (minTien != null)                sb.append(" AND TONGTIEN >= ?");
        if (maxTien != null)                sb.append(" AND TONGTIEN <= ?");

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sb.toString())) {

            int idx = 1;
            if (nhanVienId != null && nhanVienId > 0) prst.setInt(idx++, nhanVienId);
            if (from != null && to != null) {
                prst.setDate(idx++, new java.sql.Date(from.getTime()));
                prst.setDate(idx++, new java.sql.Date(to.getTime()));
            }
            if (minTien != null) prst.setInt(idx++, minTien);
            if (maxTien != null) prst.setInt(idx++, maxTien);

            try (ResultSet rs = prst.executeQuery()) {
                while (rs.next()) {
                    list.add(new PhieuNhapDTO(
                            rs.getInt("MAPHIEUNHAP"),
                            rs.getString("NHACUNGCAP"),
                            rs.getInt("NHANVIENNHAP"),
                            rs.getDate("NGAY"),
                            rs.getInt("TONGTIEN")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lọc phiếu nhập: " + e.getMessage());
        }
        return list;
    }
    // Khôi phục phiếu nhập đã bị ẩn (TRANGTHAI = 0)
    public boolean restore(int maPhieuNhap) {
        String sql = "UPDATE phieunhap SET TRANGTHAI = 1 WHERE MAPHIEUNHAP = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setInt(1, maPhieuNhap);
            return prst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khôi phục phiếu nhập: " + e.getMessage());
            return false;
        }
    }


    // Tìm phiếu nhập theo khoảng ngày
    public ArrayList<PhieuNhapDTO> getByDateRange(Date from, Date to) {
        ArrayList<PhieuNhapDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap WHERE NGAY BETWEEN ? AND ? AND TRANGTHAI = 1";

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setDate(1, new java.sql.Date(from.getTime()));
            prst.setDate(2, new java.sql.Date(to.getTime()));
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                        rs.getInt("MAPHIEUNHAP"),
                        rs.getString("NHACUNGCAP"),
                        rs.getInt("NHANVIENNHAP"),
                        rs.getDate("NGAY"),
                        rs.getInt("TONGTIEN")
                );
                danhSach.add(pn);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm phiếu nhập theo khoảng ngày: " + e.getMessage());
        }

        return danhSach;
    }

    public int getNextMaPhieuNhap() {
        String sql = "SHOW TABLE STATUS WHERE `Name` = 'PHIEUNHAP'";
        try (Connection conn = JDBCUtil.startConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("Auto_increment");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy Auto_increment PHIEUNHAP: " + e.getMessage());
        }
        return -1;
    }
}
