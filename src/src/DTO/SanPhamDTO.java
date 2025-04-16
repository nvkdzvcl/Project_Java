package DTO;

public class SanPhamDTO {

    private int maSP;
    private String tenSP;
    private String xuatXu;
    private String mauSac;
    private String kichThuoc;
    private int soLuong;
    private int maThuongHieu;

    public SanPhamDTO(){

    }
    public SanPhamDTO(int maSP, String tenSP, String xuatXu, String mauSac, String kichThuoc, int soLuong, int maThuongHieu) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.xuatXu = xuatXu;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.soLuong = soLuong;
        this.maThuongHieu = maThuongHieu;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(int maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    @Override
    public String toString() {
        return "SanPhamDTO{" +
                "maSP=" + maSP +
                ", tenSP='" + tenSP + '\'' +
                ", xuatXu='" + xuatXu + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", kichThuoc='" + kichThuoc + '\'' +
                ", soLuong=" + soLuong +
                ", maThuongHieu=" + maThuongHieu +
                '}';
    }
}
