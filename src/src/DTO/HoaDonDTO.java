package DTO;

import java.util.Date;

public class HoaDonDTO {
    private int maHoaDon;
    private String khachhang;
    private String nhanVienBan;
    private Date thoigian;
    private int tongTien;
    public HoaDonDTO(){
    }
    public HoaDonDTO(int maHoaDon,String khachhang,String nhanVienBan,Date thoigian,int tongTien){
        this.maHoaDon = maHoaDon;
        this.khachhang = khachhang;
        this.nhanVienBan = nhanVienBan;
        this.thoigian = thoigian;
        this.tongTien = tongTien;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(String khachhang) {
        this.khachhang = khachhang;
    }

    public String getNhanVienBan() {
        return nhanVienBan;
    }

    public void setNhanVienBan(String nhanVienBan) {
        this.nhanVienBan = nhanVienBan;
    }

    public Date getThoigian() {
        return thoigian;
    }

    public void setThoigian(Date thoigian) {
        this.thoigian = thoigian;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return "HoaDonDTO{" +
                "maHoaDon=" + maHoaDon +
                ", khachhang='" + khachhang + '\'' +
                ", nhanVienBan='" + nhanVienBan + '\'' +
                ", thoigian=" + thoigian +
                ", tongTien=" + tongTien +
                '}';
    }
}
