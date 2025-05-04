package DTO;

public class CTPhieuNhapDTO {
    private int maPhieuNhap;
    private int maSP;
    private int donGia;
    private int soLuong;

    public CTPhieuNhapDTO() {}

    public CTPhieuNhapDTO(int maPhieuNhap, int maSP, int donGia, int soLuong) {
        this.maPhieuNhap = maPhieuNhap;
        this.maSP         = maSP;
        this.donGia        = donGia;
        this.soLuong       = soLuong;
    }

    public int getMaPhieuNhap() { return maPhieuNhap; }
    public void setMaPhieuNhap(int maPhieuNhap) { this.maPhieuNhap = maPhieuNhap; }

    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }

    public int getDonGia() { return donGia; }
    public void setDonGia(int donGia) { this.donGia = donGia; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    @Override
    public String toString() {
        return "CTPhieuNhapDTO{" +
                "maPhieuNhap=" + maPhieuNhap +
                ", maSP=" + maSP +
                ", donGia=" + donGia +
                ", soLuong=" + soLuong +
                '}';
    }
}
