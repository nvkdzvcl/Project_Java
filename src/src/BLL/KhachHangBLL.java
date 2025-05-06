package BLL;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import java.util.ArrayList;

public class KhachHangBLL {
    private final KhachHangDAO dao;
    private final ArrayList<KhachHangDTO> khList;

    public KhachHangBLL() {
        this.dao = KhachHangDAO.getInstance();
        this.khList = dao.getallkhachhang();
    }


    public ArrayList<KhachHangDTO> getlistkh() {
        return khList;
    }


    public KhachHangDTO getonekh(int id) {
        return dao.selectById(id);
    }


    public boolean insert(KhachHangDTO dto) {
        boolean ok = dao.insertkhachhang(dto);
        if (ok) {
            khList.add(dto);
        }
        return ok;
    }


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


    public boolean delete(int id) {
        if (id < 0) return false;
        boolean ok = dao.deletekhachhang(id);
        if (ok) {
            khList.removeIf(kh -> kh.getMaKhachHang() == id);
        }
        return ok;
    }
}
