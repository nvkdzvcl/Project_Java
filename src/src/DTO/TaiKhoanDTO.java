package DTO;

public class TaiKhoanDTO {

    private int maNV;
    private String tenNguoiDung;
    private String matKhau;

    public TaiKhoanDTO(){

    }
    public TaiKhoanDTO(int maNV, String tenNguoiDung, String matKhau) {
        this.maNV = maNV;
        this.tenNguoiDung = tenNguoiDung;
        this.matKhau = matKhau;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
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

    @Override
    public String toString() {
        return "TaiKhoanDTO{" +
                "maNV=" + maNV +
                ", tenNguoiDung='" + tenNguoiDung + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}
