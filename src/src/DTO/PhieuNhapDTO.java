package DTO;

import java.util.Date;

public class PhieuNhapDTO {
    private int maPhieuNhap;
    private int nhanVienNhap;
    private String tenNhanVien;
    private Date ngay;
    private int tongTien;
    private int trangThai;
    public PhieuNhapDTO(){

    }
    public PhieuNhapDTO(int maPhieuNhap,int nhanVienNhap,String tenNhanVien ,Date ngay,int tongTien,int trangThai){
        this.maPhieuNhap = maPhieuNhap;
        this.nhanVienNhap = nhanVienNhap;
        this.tenNhanVien = tenNhanVien;
        this.ngay = ngay;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }
    public PhieuNhapDTO(int maPhieuNhap,int nhanVienNhap,String tenNhanVien ,Date ngay,int tongTien){
        this.maPhieuNhap = maPhieuNhap;
        this.nhanVienNhap = nhanVienNhap;
        this.tenNhanVien = tenNhanVien;
        this.ngay = ngay;
        this.tongTien = tongTien;

    }

    public int getMaPhieuNhap(){
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public int getNhanVienNhap() {
        return nhanVienNhap;
    }

    public void setNhanVienNhap(int nhanVienNhap) {
        this.nhanVienNhap = nhanVienNhap;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String ten) {
        tenNhanVien = ten;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
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
        return "PhieuNhapDTO{" +
                "maPhieuNhap=" + maPhieuNhap +
                ", nhanVienNhap='" + nhanVienNhap + '\'' +
                ", ngay=" + ngay +
                ", tongTien=" + tongTien +
                ", trangThai=" + trangThai +
                '}';
    }
}

