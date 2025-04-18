package DTO;

public class TaiKhoanDTO {

    private String tenNguoiDung;
    private String matKhau;
    private String chucVu;
    private int trangThai;
    private int maNV;

    public TaiKhoanDTO(){

    }
    public TaiKhoanDTO(String tenNguoiDung, String matKhau, String chucVu, int trangThai, int maNV) {
        this.tenNguoiDung = tenNguoiDung;
        this.matKhau = matKhau;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
        this.maNV = maNV;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    @Override
    public String toString() {
        return "TaiKhoanDTO{" +
                "tenNguoiDung='" + tenNguoiDung + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", trangThai=" + trangThai +
                ", maNV=" + maNV +
                '}';
    }
}
