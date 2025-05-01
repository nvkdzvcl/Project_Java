package DTO;

public class NhanVienDTO {

    private int maNV;
    private String hoTen;
    private String gioiTinh;
    private String ngaySinh;
    private String sdt;
    private String email;
    private String diachi;
    public NhanVienDTO(){

    }
    public NhanVienDTO(int maNV, String hoTen, String gioiTinh, String ngaySinh, String sdt,String email, String diachi) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
        this.diachi=diachi;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }
    public String getDiachi(){
        return diachi;
    }
    public void setDiachi(String diachi){
        this.diachi=diachi;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public String getSdt() {
        return sdt;
    }
    @Override
    public String toString() {
        return "NhanVienDTO{" +
                "maNV='" + maNV + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", email='" + email + '\'' +
                ", diachi='" + diachi + '\'' +
                '}';
    }
}
