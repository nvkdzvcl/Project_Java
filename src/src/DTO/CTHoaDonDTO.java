package DTO;

public class CTHoaDonDTO {
    private int maHoaDon;
    private int maSP;
    private int donGia;
    private int soLuong;

    public CTHoaDonDTO() {}

    public CTHoaDonDTO(int maHoaDon, int maSP, int donGia, int soLuong) {
        this.maHoaDon = maHoaDon;
        this.maSP = maSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public int getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(int maHoaDon) { this.maHoaDon = maHoaDon; }

    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }

    public int getDonGia() { return donGia; }
    public void setDonGia(int donGia) { this.donGia = donGia; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    @Override
    public String toString() {
        return "CTHoaDonDTO{" +
                "maPhieuNhap=" + maHoaDon +
                ", maSP=" + maSP +
                ", donGia=" + donGia +
                ", soLuong=" + soLuong +
                '}';
    }
}
