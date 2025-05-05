package DTO.ThongKe;

import java.util.Date;
import java.util.Objects;


public class ThongKeTungNgayTrongThangDTO{
    private Date ngay;
    // Đã sửa: int thành long
    private long chiphi;
    private long doanhthu;
    private long loinhuan;

    // Constructor đã cập nhật kiểu dữ liệu
    public ThongKeTungNgayTrongThangDTO(Date ngay, long chiphi, long doanhthu, long loinhuan) {
        this.ngay = ngay;
        this.chiphi = chiphi;
        this.doanhthu = doanhthu;
        this.loinhuan = loinhuan;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
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

    // Cập nhật hashCode và equals nếu cần thiết
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.ngay);
        hash = 29 * hash + Objects.hashCode(this.chiphi); // Hoặc cách xử lý long nguyên thủy
        hash = 29 * hash + Objects.hashCode(this.doanhthu);
        hash = 29 * hash + Objects.hashCode(this.loinhuan);
        // hash = 29 * hash + (int) (this.chiphi ^ (this.chiphi >>> 32));
        // hash = 29 * hash + (int) (this.doanhthu ^ (this.doanhthu >>> 32));
        // hash = 29 * hash + (int) (this.loinhuan ^ (this.loinhuan >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ThongKeTungNgayTrongThangDTO other = (ThongKeTungNgayTrongThangDTO) obj;
        if (this.chiphi != other.chiphi) {
            return false;
        }
        if (this.doanhthu != other.doanhthu) {
            return false;
        }
        if (this.loinhuan != other.loinhuan) {
            return false;
        }
        return Objects.equals(this.ngay, other.ngay);
    }

    @Override
    public String toString() {
        return "ThongKeTungNgayTrongThangDTO{" + "ngay=" + ngay + ", chiphi=" + chiphi + ", doanhthu=" + doanhthu + ", loinhuan=" + loinhuan + '}';
    }
}