package DTO.ThongKe;

import java.util.Objects; // Thêm import Objects để dùng trong equals/hashCode nếu cần

public class ThongKeTheoThangDTO {
    private int thang;
    // Đã sửa: int thành long
    private long chiphi;
    private long doanhthu;
    private long loinhuan;

    public ThongKeTheoThangDTO(){

    }

    // Constructor đã cập nhật kiểu dữ liệu
    public ThongKeTheoThangDTO(int thang, long chiphi, long doanhthu, long loinhuan){
        this.thang = thang;
        this.chiphi = chiphi;
        this.doanhthu = doanhthu;
        this.loinhuan = loinhuan;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    // Getter/Setter đã cập nhật kiểu dữ liệu
    public long getChiphi() {
        return chiphi;
    }

    public void setChiphi(long chiphi) {
        this.chiphi = chiphi;
    }

    public long getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(long doanhthu) {
        this.doanhthu = doanhthu;
    }

    public long getLoinhuan() {
        return loinhuan;
    }

    public void setLoinhuan(long loinhuan) {
        this.loinhuan = loinhuan;
    }

    // Cập nhật hashCode và equals nếu cần thiết để xử lý kiểu long
    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 59 * hash + this.thang;
//        hash = 59 * hash + Objects.hashCode(this.chiphi); // Dùng Objects.hashCode cho kiểu Wrapper nếu là Long, nhưng ở đây là long nguyên thủy
//        hash = 59 * hash + Objects.hashCode(this.doanhthu);
//        hash = 59 * hash + Objects.hashCode(this.loinhuan);
//        // Hoặc cách khác cho long nguyên thủy:
//        // hash = 59 * hash + (int) (this.chiphi ^ (this.chiphi >>> 32));
//        // hash = 59 * hash + (int) (this.doanhthu ^ (this.doanhthu >>> 32));
//        // hash = 59 * hash + (int) (this.loinhuan ^ (this.loinhuan >>> 32));
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final ThongKeTheoThangDTO other = (ThongKeTheoThangDTO) obj;
//        if (this.thang != other.thang) {
//            return false;
//        }
//        if (this.chiphi != other.chiphi) {
//            return false;
//        }
//        if (this.doanhthu != other.doanhthu) {
//            return false;
//        }
//        return this.loinhuan == other.loinhuan;
//    }
//
//    @Override
    public String toString() {
        return "ThongKeTheoThangDTO{" + "thang=" + thang + ", chiphi=" + chiphi + ", doanhthu=" + doanhthu + ", loinhuan=" + loinhuan + '}';
    }

}