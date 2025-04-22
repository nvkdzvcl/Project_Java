package BLL;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapBLL {

    private ArrayList<PhieuNhapDTO> danhSachPhieuNhap;

    public PhieuNhapBLL() {
        this.danhSachPhieuNhap = PhieuNhapDAO.getInstance().selectAll();
    }

    public ArrayList<PhieuNhapDTO> getDanhSachPhieuNhap() {
        return danhSachPhieuNhap;
    }

    public PhieuNhapDTO getPhieuNhap(int maPhieuNhap) {
        for (PhieuNhapDTO pn : danhSachPhieuNhap) {
            if (pn.getMaPhieuNhap() == maPhieuNhap) {
                return pn;
            }
        }
        return null;
    }

    public int getIndexByMaPhieuNhap(int maPhieuNhap) {
        for (int i = 0; i < danhSachPhieuNhap.size(); i++) {
            if (danhSachPhieuNhap.get(i).getMaPhieuNhap() == maPhieuNhap) {
                return i;
            }
        }
        return -1;
    }

    public void insertPhieuNhap(PhieuNhapDTO phieuNhap) {
        danhSachPhieuNhap.add(phieuNhap);
    }

    public void updatePhieuNhap(int viTri, PhieuNhapDTO phieuNhap) {
        danhSachPhieuNhap.set(viTri, phieuNhap);
    }

    public void deletePhieuNhap(int maPhieuNhap) {
        int viTri = getIndexByMaPhieuNhap(maPhieuNhap);
        if (viTri != -1) {
            danhSachPhieuNhap.get(viTri).setTrangthai(0);
            PhieuNhapDAO.getInstance().update(danhSachPhieuNhap.get(viTri));
        }
    }

    public void restorePhieuNhap(int maPhieuNhap) {
        int viTri = getIndexByMaPhieuNhap(maPhieuNhap);
        if (viTri != -1) {
            danhSachPhieuNhap.get(viTri).setTrangthai(1);
            PhieuNhapDAO.getInstance().update(danhSachPhieuNhap.get(viTri));
        }
    }

    public ArrayList<PhieuNhapDTO> searchPhieuNhap(String text, String type) {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        text = text.toLowerCase();

        switch (type) {
            case "Tất cả" -> {
                for (PhieuNhapDTO pn : danhSachPhieuNhap) {
                    if (String.valueOf(pn.getMaPhieuNhap()).contains(text) ||
                            pn.getNhaCungCap().toLowerCase().contains(text) ||
                            pn.getNhanVienNhap().toLowerCase().contains(text)) {
                        ketQua.add(pn);
                    }
                }
            }
            case "Mã Phiếu Nhập" -> {
                for (PhieuNhapDTO pn : danhSachPhieuNhap) {
                    if (String.valueOf(pn.getMaPhieuNhap()).contains(text)) {
                        ketQua.add(pn);
                    }
                }
            }
            case "Nhà Cung Cấp" -> {
                for (PhieuNhapDTO pn : danhSachPhieuNhap) {
                    if (pn.getNhaCungCap().toLowerCase().contains(text)) {
                        ketQua.add(pn);
                    }
                }
            }
            case "Nhân Viên Nhập" -> {
                for (PhieuNhapDTO pn : danhSachPhieuNhap) {
                    if (pn.getNhanVienNhap().toLowerCase().contains(text)) {
                        ketQua.add(pn);
                    }
                }
            }
        }

        return ketQua;
    }
}
