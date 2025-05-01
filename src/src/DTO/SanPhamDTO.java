package DTO;

public class SanPhamDTO {

    private String hinhAnh;
    private int maSP;
    private String tenSP;
    private String thuongHieu;
    private String xuatXu;
    private String mauSac;
    private String kichThuoc;
    private int soLuong;
    private int trangThai;

    public SanPhamDTO(){
    }
    public SanPhamDTO(String hinhAnh, String tenSP, String thuongHieu, String xuatXu, String mauSac, String kichThuoc, int soLuong) {
        this.hinhAnh = hinhAnh;
        this.tenSP = tenSP;
        this.thuongHieu = thuongHieu;
        this.xuatXu = xuatXu;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.soLuong = soLuong;
    }
    public SanPhamDTO(String hinhAnh, int maSP, String tenSP, String thuongHieu, String xuatXu, String mauSac, String kichThuoc, int soLuong, int trangThai) {
        this.hinhAnh = hinhAnh;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.thuongHieu = thuongHieu;
        this.xuatXu = xuatXu;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
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

    public String getThuongHieu() {
        return thuongHieu;
    }
    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
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

    public int getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "SanPhamDTO{" +
                "hinhAnh='" + hinhAnh + '\'' +
                ", maSP=" + maSP +
                ", tenSP='" + tenSP + '\'' +
                ", thuongHieu='" + thuongHieu + '\'' +
                ", xuatXu='" + xuatXu + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", kichThuoc='" + kichThuoc + '\'' +
                ", soLuong=" + soLuong +
                ", trangThai=" + trangThai +
                '}';
    }
}
