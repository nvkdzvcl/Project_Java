package BLL;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;

import java.util.ArrayList;

public class HoaDonBLL {

    private ArrayList<HoaDonDTO> danhSachHoaDon;

    public HoaDonBLL(){
        this.danhSachHoaDon = HoaDonDAO.getInstance().selectAll();
    }

    public ArrayList<HoaDonDTO> getDanhSachHoaDon(){
        return danhSachHoaDon;
    }

    public HoaDonDTO getHoaDon(int maHoaDon){
        return danhSachHoaDon.get(maHoaDon);
    }

    public int getTaiKhoanByMaHoaDon(int maHoaDon){
        int i = 0;
        int viTri = -1;
        while(i < danhSachHoaDon.size() && viTri == -1){
            if(danhSachHoaDon.get(i).getMaHoaDon() == maHoaDon){
                viTri = i;
            }
            else{
                i++;
            }
        }

        return viTri;
    }

    public void insertHoaDon(HoaDonDTO hoaDon){
        danhSachHoaDon.add(hoaDon);
    }

    public void updateHoaDon(int viTri, HoaDonDTO hoaDon){
        danhSachHoaDon.set(viTri, hoaDon);
    }

    public void deleteHoaDon(int maHoaDon){
        int viTri = getTaiKhoanByMaHoaDon(maHoaDon);
        if(viTri != -1){
            danhSachHoaDon.remove(viTri);
        }
    }

    public ArrayList<HoaDonDTO> searchHoaDon(String text, String type){
        ArrayList<HoaDonDTO> danhSachKetQua = new ArrayList<>();
        text = text.toLowerCase();
        switch (type){
            case "Tất cả":
                for(HoaDonDTO hoaDon : danhSachHoaDon){
                    if(Integer.toString(hoaDon.getMaHoaDon()).contains(text) || hoaDon.getKhachhang().contains(text) || hoaDon.getNhanVienBan().contains(text)){
                        danhSachKetQua.add(hoaDon);
                    }
                }
                break;
            case "Mã hoá đơn":
                for(HoaDonDTO hoaDon : danhSachHoaDon){
                    if(Integer.toString(hoaDon.getMaHoaDon()).contains(text)){
                        danhSachKetQua.add(hoaDon);
                    }
                }
                break;
            case "Khách hàng":
                for(HoaDonDTO hoaDon : danhSachHoaDon){
                    if(hoaDon.getKhachhang().contains(text)){
                        danhSachKetQua.add(hoaDon);
                    }
                }
                break;
            case "Nhân viên bán":
                for(HoaDonDTO hoaDon : danhSachHoaDon){
                    if(hoaDon.getNhanVienBan().contains(text)){
                        danhSachKetQua.add(hoaDon);
                    }
                }
                break;
        }

        return danhSachHoaDon;
    }
}
