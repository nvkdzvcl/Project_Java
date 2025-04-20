package BLL;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import java.util.ArrayList;

public class TaiKhoanBLL {
    private ArrayList<TaiKhoanDTO> listTaiKhoan;

    public TaiKhoanBLL(){
        this.listTaiKhoan = TaiKhoanDAO.getInstance().selectAll();
    }

    public ArrayList<TaiKhoanDTO> getListTaiKhoan() {
        return listTaiKhoan;
    }

    public TaiKhoanDTO getTaiKhoan(int maNV) {
        return listTaiKhoan.get(maNV);
    }

    public int getTaiKhoanByMaNV(int maNV) {
        int i = 0;
        int pos = -1;
        while (i < listTaiKhoan.size() && pos == -1) {
            if (listTaiKhoan.get(i).getMaNV() == maNV) {
                pos = i;
            } else {
                i++;
            }
        }
        return pos;
    }

    public void addAccount(TaiKhoanDTO tk) {
        listTaiKhoan.add(tk);
    }

    public void updateAccount(int index, TaiKhoanDTO tk) {
        listTaiKhoan.set(index, tk);
    }

    public void deleteAccount(int maNV) {
        int index = getTaiKhoanByMaNV(maNV);
        if (index != -1) listTaiKhoan.remove(index);
    }

    public ArrayList<TaiKhoanDTO> search(String txt, String type) {
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (Integer.toString(i.getMaNV()).contains(txt) || i.getTenNguoiDung().contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Mã NV" -> {
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (Integer.toString(i.getMaNV()).contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Tên Đăng Nhập" -> {
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (i.getTenNguoiDung().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Chức Vụ" -> {
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (i.getChucVu().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Trạng Thái" -> {
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (Integer.toString(i.getTrangThai()).contains(txt)) {
                        result.add(i);
                    }
                }
            }
        }

        return result;
    }
}
