package DTO;

import java.util.Date;

public class PhieuNhapDTO {
    private int maPhieuNhap;
    private String nhaCungCap;
    private String nhanVienNhap;
    private Date ngay;
    public PhieuNhapDTO(){

    }
    public PhieuNhapDTO(int maPhieuNhap,String nhaCungCap,String nhanVienNhap,Date ngay){
        this.maPhieuNhap = maPhieuNhap;
        this.nhaCungCap = nhaCungCap;
        this.nhanVienNhap = nhanVienNhap;
        this.ngay = ngay;
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

    public String getNhanVienNhap() {
        return nhanVienNhap;
    }

    public void setNhanVienNhap(String nhanVienNhap) {
        this.nhanVienNhap = nhanVienNhap;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
}

