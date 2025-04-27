package DTO;

public class KhachHangDTO {
    private int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String diachi;

    public KhachHangDTO() {}

    public KhachHangDTO(int maKhachHang, String tenKhachHang,String diachi,String soDienThoai) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.diachi = diachi;
        this.soDienThoai = soDienThoai;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiachi() {
        return diachi;
    }
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    @Override
    public String toString() {
        return "KhachHangDTO{" +
                "maKhachHang=" + maKhachHang +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diachi='" + diachi + '\'' +
                '}';
    }
}
