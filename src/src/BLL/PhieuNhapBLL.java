package BLL;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapBLL {
    private final PhieuNhapDAO dao = PhieuNhapDAO.getInstance();
    private ArrayList<PhieuNhapDTO> danhSachPhieuNhap;

    public PhieuNhapBLL() {
        refresh();
    }

    public void refresh() {
        this.danhSachPhieuNhap = PhieuNhapDAO.getInstance().selectAll();
    }

    public ArrayList<PhieuNhapDTO> getDanhSachPhieuNhap() {
        return danhSachPhieuNhap;
    }

    public int insertPhieuNhap(PhieuNhapDTO phieuNhap) {
        int newId = PhieuNhapDAO.getInstance().insert(phieuNhap);
        if (newId > 0) refresh();
        return newId;
    }

    public boolean updatePhieuNhap(PhieuNhapDTO phieuNhap) {
        boolean ok = PhieuNhapDAO.getInstance().update(phieuNhap);
        if (ok) refresh();
        return ok;
    }

    public boolean deletePhieuNhap(int maPhieuNhap) {
        boolean ok = PhieuNhapDAO.getInstance().delete(maPhieuNhap);
        if (ok) refresh();
        return ok;
    }

    public boolean restorePhieuNhap(int maPhieuNhap) {
        boolean ok = PhieuNhapDAO.getInstance().restore(maPhieuNhap);
        if (ok) refresh();
        return ok;
    }

    public ArrayList<PhieuNhapDTO> filterPhieuNhap(Integer nhanVienId, Date from, Date to,
                                              Integer minTien, Integer maxTien) {
        return PhieuNhapDAO.getInstance().filter(nhanVienId, from, to, minTien, maxTien);
    }

    public int getNextMaPhieuNhap() {
        return dao.getNextMaPhieuNhap();
    }
}
