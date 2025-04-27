package BLL;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import java.util.ArrayList;

public class TaiKhoanBLL {
    private ArrayList<TaiKhoanDTO> listTaiKhoan;
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

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

    public String getMatKhauByTenNguoiDung(String tenNguoiDung){
        int i = 0;
        while (i < listTaiKhoan.size()){
            if(listTaiKhoan.get(i).getTenNguoiDung().equals(tenNguoiDung)){
                return listTaiKhoan.get(i).getMatKhau();
            }
            else {
                i++;
            }
        }
        return null;
    }

//    public void addAccount(TaiKhoanDTO tk) {
//        listTaiKhoan.add(tk);
//    }

    public Boolean addAccount(TaiKhoanDTO taiKhoan){
        if(taiKhoanDAO.insert(taiKhoan) > 0){
            listTaiKhoan.add(taiKhoan);
            return true;
        }

        return false;
    }

    public Boolean updateAccount(int index, TaiKhoanDTO tk) {
        if(taiKhoanDAO.update(tk) > 0){
            listTaiKhoan.set(index, tk);
            return true;
        }

        return false;
    }

    public void deleteAccount(int maNV) {
        int index = getTaiKhoanByMaNV(maNV);
        if (index != -1) listTaiKhoan.remove(index);
    }

    public ArrayList<TaiKhoanDTO> search(String txt, String type) {
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất Cả" -> {
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
