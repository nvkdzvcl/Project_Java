package BLL;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapBLL {
    private final PhieuNhapDAO dao = PhieuNhapDAO.getInstance();
    private ArrayList<PhieuNhapDTO> danhSach;

    public PhieuNhapBLL() {
        refresh();
    }

    /** Tải lại toàn bộ phiếu nhập (TRANGTHAI = 1) */
    public void refresh() {
        danhSach = dao.selectAll();
    }

    /** Lấy danh sách hiện tại */
    public ArrayList<PhieuNhapDTO> getDanhSachPhieuNhap() {
        return new ArrayList<>(danhSach);
    }

    /** Thêm phiếu nhập, trả về ID mới (hoặc -1 nếu lỗi) */
    public int insertPhieuNhap(PhieuNhapDTO pn) {
        int newId = dao.insert(pn);
        if (newId > 0) refresh();
        return newId;
    }

    /** Cập nhật phiếu nhập, trả về true nếu thành công */
    public boolean updatePhieuNhap(PhieuNhapDTO pn) {
        boolean ok = dao.update(pn);
        if (ok) refresh();
        return ok;
    }

    /** Xóa (hủy) phiếu nhập, trả về true nếu thành công */
    public boolean deletePhieuNhap(int maPN) {
        boolean ok = dao.delete(maPN);
        if (ok) refresh();
        return ok;
    }

    /** Lọc phiếu nhập theo điều kiện, không thay đổi danhSach gốc */
    public ArrayList<PhieuNhapDTO> filterPhieuNhap(Integer nvId, Date from, Date to,
                                                   Integer minTien, Integer maxTien) {
        return dao.filter(nvId, from, to, minTien, maxTien);
    }

    /** Lấy Auto_increment để hiển thị mã PN tiếp theo */
    public int getNextMaPhieuNhap() {
        return dao.getNextMaPhieuNhap();
    }

    /** Thêm chi tiết vào CTPHIEUNHAP */
    public boolean insertChiTiet(int maPN, int maSP, int donGia, int soLuong) {
        return dao.insertChiTiet(maPN, maSP, donGia, soLuong);
    }

    public PhieuNhapDTO getById(int maPN) {
        return dao.selectById(maPN);
    }
}
