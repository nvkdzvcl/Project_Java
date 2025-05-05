package BLL;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import java.util.ArrayList;

public class KhachHangBLL {
    private final KhachHangDAO dao;
    private final ArrayList<KhachHangDTO> khList;

    public KhachHangBLL() {
        // Lấy instance của DAO
        this.dao = KhachHangDAO.getInstance();
        // Tải lên danh sách khách hàng ban đầu
        this.khList = dao.getallkhachhang();
    }

    /**
     * Trả về danh sách khách hàng (đã load từ DAO)
     */
    public ArrayList<KhachHangDTO> getlistkh() {
        return khList;
    }

    /**
     * Lấy 1 khách hàng theo ID (reload trực tiếp từ DB)
     */
    public KhachHangDTO getonekh(int id) {
        return dao.selectById(id);
    }

    /**
     * Thêm mới khách hàng
     * – Thêm vào DB, nếu thành công thì push vào list
     */
    public boolean insert(KhachHangDTO dto) {
        boolean ok = dao.insertkhachhang(dto);
        if (ok) {
            khList.add(dto);
        }
        return ok;
    }

    /**
     * Cập nhật khách hàng
     * – Cập nhật DB; nếu thành công thì cũng cập nhật trong list
     */
    public boolean update(KhachHangDTO dto) {
        if (dto == null) return false;
        boolean ok = dao.updatekhachhang(dto);
        if (ok) {
            // Tìm trong list và thay thế
            for (int i = 0; i < khList.size(); i++) {
                if (khList.get(i).getMaKhachHang() == dto.getMaKhachHang()) {
                    khList.set(i, dto);
                    break;
                }
            }
        }
        return ok;
    }

    /**
     * Xóa mềm khách hàng
     * – Cập nhật DB; nếu thành công thì cũng remove khỏi list
     */
    public boolean delete(int id) {
        if (id < 0) return false;
        boolean ok = dao.deletekhachhang(id);
        if (ok) {
            khList.removeIf(kh -> kh.getMaKhachHang() == id);
        }
        return ok;
    }
}
