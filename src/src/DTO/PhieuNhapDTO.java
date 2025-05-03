package DTO;

import java.util.Date;

public class PhieuNhapDTO {
    private int maPhieuNhap;
    private String nhaCungCap;
    private int nhanVienNhap;
    private Date ngay;
    private int tongTien;
    private int trangThai;
    public PhieuNhapDTO(){

    }
    public PhieuNhapDTO(int maPhieuNhap,String nhaCungCap,int nhanVienNhap,Date ngay,int tongTien,int trangThai){
        this.maPhieuNhap = maPhieuNhap;
        this.nhaCungCap = nhaCungCap;
        this.nhanVienNhap = nhanVienNhap;
        this.ngay = ngay;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }
    public PhieuNhapDTO(int maPhieuNhap,String nhaCungCap,int nhanVienNhap,Date ngay,int tongTien){
        this.maPhieuNhap = maPhieuNhap;
        this.nhaCungCap = nhaCungCap;
        this.nhanVienNhap = nhanVienNhap;
        this.ngay = ngay;
        this.tongTien = tongTien;

    }

    public int getMaPhieuNhap(){
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }
    public String getNhaCungCap(){
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public int getNhanVienNhap() {
        return nhanVienNhap;
    }

    public void setNhanVienNhap(int nhanVienNhap) {
        this.nhanVienNhap = nhanVienNhap;
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
                ", nhaCungCap='" + nhaCungCap + '\'' +
                ", nhanVienNhap='" + nhanVienNhap + '\'' +
                ", ngay=" + ngay +
                ", tongTien=" + tongTien +
                ", trangThai=" + trangThai +
                '}';
    }
}

