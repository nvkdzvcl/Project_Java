package DAO;

import BLL.NhanVienBLL;
import DTO.PhieuNhapDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapDAO {
    private static final PhieuNhapDAO INSTANCE = new PhieuNhapDAO();
    public static PhieuNhapDAO getInstance() { return INSTANCE; }

    /** Thêm phiếu nhập, trả về ID sinh tự động hoặc -1 nếu thất bại */
    public int insert(PhieuNhapDTO phieuNhap) {
        String sql = """
            INSERT INTO PHIEUNHAP
              (MANV, THOIGIAN, TONGTIEN, TRANGTHAI)
            VALUES (?,?,?,?)
            """;
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prst.setInt(1, phieuNhap.getNhanVienNhap());
            prst.setTimestamp(2, new Timestamp(phieuNhap.getNgay().getTime()));
            prst.setInt(3, phieuNhap.getTongTien());
            prst.setInt(4, phieuNhap.getTrangThai());

            int affected = prst.executeUpdate();
            if (affected == 0) return -1;

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

    /** Cập nhật phiếu nhập */
    public boolean update(PhieuNhapDTO phieuNhap) {
        String sql = """
            UPDATE PHIEUNHAP
            SET MANV = ?, THOIGIAN = ?, TONGTIEN = ?, TRANGTHAI = ?
            WHERE MAPHIEUNHAP = ?
            """;
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {

            prst.setInt(1, phieuNhap.getNhanVienNhap());
            prst.setTimestamp(2, new Timestamp(phieuNhap.getNgay().getTime()));
            prst.setInt(3, phieuNhap.getTongTien());
            prst.setInt(4, phieuNhap.getTrangThai());
            prst.setInt(5, phieuNhap.getMaPhieuNhap());

            return prst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật phiếu nhập: " + e.getMessage());
            return false;
        }
    }

    /** Đánh dấu hủy (TRANGTHAI = 0) */
    public boolean delete(int maPhieuNhap) {
        String sql = "UPDATE PHIEUNHAP SET TRANGTHAI = 0 WHERE MAPHIEUNHAP = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setInt(1, maPhieuNhap);
            return prst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa phiếu nhập: " + e.getMessage());
            return false;
        }
    }

    /** Lấy tất cả phiếu nhập (TRANGTHAI = 1) kèm tên nhân viên */
    public ArrayList<PhieuNhapDTO> selectAll() {
        ArrayList<PhieuNhapDTO> danhSachPhieuNhap = new ArrayList<>();
        // TRANGTHAI <> 0 sẽ lấy cả 1 (Chờ) và 2 (Hoàn thành)
        String sql = "SELECT * FROM phieunhap WHERE TRANGTHAI <> 0";

        try (Connection conn = JDBCUtil.startConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                        rs.getInt("MAPHIEUNHAP"),
                        rs.getInt("MANV"),
                        new NhanVienBLL().getonenv(rs.getInt("MANV")).getHoTen(), // nếu bạn lưu luôn tên
                        rs.getTimestamp("THOIGIAN"),
                        rs.getInt("TONGTIEN"),
                        rs.getInt("TRANGTHAI")
                );
                danhSachPhieuNhap.add(pn);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách phiếu nhập: " + e.getMessage());
        }

        return danhSachPhieuNhap;
    }


    /** Lọc phiếu theo NV, khoảng ngày, khoảng tiền (kết quả vẫn có tên NV) */
    public ArrayList<PhieuNhapDTO> filter(
            Integer nhanVienId,
            Date from,
            Date to,
            Integer minTien,
            Integer maxTien
    ) {
        ArrayList<PhieuNhapDTO> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder("""
        SELECT p.MAPHIEUNHAP,
               p.MANV,
               nv.HOTENNV,
               p.THOIGIAN,
               p.TONGTIEN,
               p.TRANGTHAI
          FROM PHIEUNHAP p
          JOIN NHANVIEN nv ON p.MANV = nv.MANV
         WHERE p.TRANGTHAI = 1
        """);

        if (nhanVienId != null && nhanVienId > 0) {
            sb.append(" AND p.MANV = ?");
        }
        if (from != null && to != null) {
            sb.append(" AND p.THOIGIAN BETWEEN ? AND ?");
        }
        if (minTien != null) {
            sb.append(" AND p.TONGTIEN >= ?");
        }
        if (maxTien != null) {
            sb.append(" AND p.TONGTIEN <= ?");
        }

        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement prst = conn.prepareStatement(sb.toString())) {

            int idx = 1;
            if (nhanVienId != null && nhanVienId > 0) {
                prst.setInt(idx++, nhanVienId);
            }
            if (from != null && to != null) {
                prst.setTimestamp(idx++, new Timestamp(from.getTime()));
                prst.setTimestamp(idx++, new Timestamp(to.getTime()));
            }
            if (minTien != null) {
                prst.setInt(idx++, minTien);
            }
            if (maxTien != null) {
                prst.setInt(idx++, maxTien);
            }

            try (ResultSet rs = prst.executeQuery()) {
                while (rs.next()) {
                    PhieuNhapDTO pn = new PhieuNhapDTO(
                            rs.getInt("MAPHIEUNHAP"),
                            rs.getInt("MANV"),
                            rs.getString("HOTENNV"),
                            rs.getTimestamp("THOIGIAN"),
                            rs.getInt("TONGTIEN"),
                            rs.getInt("TRANGTHAI")
                    );
                    list.add(pn);
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi lọc phiếu nhập: " + e.getMessage());
        }

        return list;
    }


    /** Lấy Auto_increment tiếp theo */
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

    /** Thêm chi tiết nhập */
    public boolean insertChiTiet(int maPN, int maSP, int donGia, int soLuong) {
        String sql = """
            INSERT INTO CTPHIEUNHAP
              (MAPHIEUNHAP, MASP, DONGIA, SOLUONG)
            VALUES (?,?,?,?)
            """;
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            ps.setInt(2, maSP);
            ps.setInt(3, donGia);
            ps.setInt(4, soLuong);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public PhieuNhapDTO selectById(int maPN) {
        String sql = "SELECT * FROM phieunhap WHERE MAPHIEUNHAP = ?";
        try (Connection conn = JDBCUtil.startConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PhieuNhapDTO pn = new PhieuNhapDTO();
                    pn.setMaPhieuNhap(rs.getInt("MAPHIEUNHAP"));
                    pn.setNhanVienNhap(rs.getInt("MANV"));
                    pn.setNgay(rs.getTimestamp("THOIGIAN"));
                    pn.setTongTien(rs.getInt("TONGTIEN"));
                    pn.setTrangThai(rs.getInt("TRANGTHAI"));
                    return pn;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy phiếu nhập theo ID: " + e.getMessage());
        }
        return null;
    }
}
