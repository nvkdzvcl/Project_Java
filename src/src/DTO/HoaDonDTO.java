package DTO;

import java.util.Date;

public class HoaDonDTO {
    private int maHoaDon;
    private int khachHang;
    private int nhanVienBan;
    private Date thoiGian;
    private String diaChi;
    private int tongTien;
    private int trangThai;

    public HoaDonDTO() {

    }

    public HoaDonDTO(int maHoaDon, int khachHang, int nhanVienBan, Date thoiGian, String diaChi, int tongTien, int trangThai) {
        this.maHoaDon = maHoaDon;
        this.khachHang = khachHang;
        this.nhanVienBan = nhanVienBan;
        this.thoiGian = thoiGian;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(int khachHang) {
        this.khachHang = khachHang;
    }

    public int getNhanVienBan() {
        return nhanVienBan;
    }

    public void setNhanVienBan(int nhanVienBan) {
        this.nhanVienBan = nhanVienBan;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDonDTO{" +
                "maHoaDon=" + maHoaDon +
                ", khachHang=" + khachHang +
                ", nhanVienBan=" + nhanVienBan +
                ", thoiGian=" + thoiGian +
                ", tongTien=" + tongTien +
                ", trangThai=" + trangThai +
                '}';
    }
}
