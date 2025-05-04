package BLL;

import DAO.CTHoaDonDAO;
import DAO.HoaDonDAO;
import DTO.HoaDonDTO;

import java.util.ArrayList;
import java.util.Date;

public class HoaDonBLL {
    private final HoaDonDAO dao;
    private ArrayList<HoaDonDTO> danhSach;

    public HoaDonBLL() {
        this.dao = HoaDonDAO.getInstance();
        this.danhSach = dao.selectAll();
    }

    public void refresh() {
        this.danhSach = dao.selectAll();
    }

    /** Lấy tất cả hóa đơn đang hoạt động */
    public ArrayList<HoaDonDTO> getDanhSachHoaDon() {
        return dao.selectAll();
    }

    /** Lấy hóa đơn theo ID */
    public HoaDonDTO getHoaDonById(int maHD) {
        for (HoaDonDTO hd : danhSach) {
            if (hd.getMaHoaDon() == maHD) return hd;
        }
        return null;
    }

    /** Tạo hóa đơn mới, trả về ID hoặc -1 */
    public int insert(HoaDonDTO hd) {
        int id = dao.insert(hd);
        if (id > 0) danhSach.add(hd);
        return id;
    }

    public boolean updateHoaDon(HoaDonDTO hd) {
        boolean ok = dao.update(hd);
        if (ok) {
            for (int i = 0; i < danhSach.size(); i++) {
                if (danhSach.get(i).getMaHoaDon() == hd.getMaHoaDon()) {
                    danhSach.set(i, hd);
                    break;
                }
            }
        }
        return ok;
    }

    public boolean deleteHoaDon(int maHD) {
        boolean ok = dao.delete(maHD);
        if (ok) danhSach.removeIf(hd -> hd.getMaHoaDon() == maHD);
        return ok;
    }

    public ArrayList<HoaDonDTO> getHoaDonByDateRange(Date from, Date to) {
        return dao.selectByDateRange(from, to);
    }

    public ArrayList<HoaDonDTO> getHoaDonByCustomerName(String tenKH) {
        return dao.selectByCustomerName(tenKH);
    }

    public int getNextMaHoaDon() {
        return dao.getNextMaHoaDon();
    }

    public boolean insertChiTiet(int maHD, int maSP, int donGia, int soLuong) {
        return CTHoaDonDAO.getInstance().insert(maHD, maSP, donGia, soLuong);
    }
}
