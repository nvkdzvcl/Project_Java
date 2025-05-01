package DTO.ThongKe;

import java.util.Objects;

public class ThongKeTonKhoDTO {

    int masp;
    String tensp;
    String thuonghieu;
    String xuatxu;
    String mausac;
    String kichthuoc;
    int soluong;
    int trangthai;

    int tondauky;
    int nhaptrongky;
    int xuattrongky;
    int toncuoiky;

    public ThongKeTonKhoDTO() {
    }

    public ThongKeTonKhoDTO(int masp, String tensp, String thuonghieu, String xuatxu,
                                   String mausac, String kichthuoc, int soluong, int trangthai,
                                   int tondauky, int nhaptrongky, int xuattrongky, int toncuoiky) {
        this.masp = masp;
        this.tensp = tensp;
        this.thuonghieu = thuonghieu;
        this.xuatxu = xuatxu;
        this.mausac = mausac;
        this.kichthuoc = kichthuoc;
        this.soluong = soluong;
        this.trangthai = trangthai;
        this.tondauky = tondauky;
        this.nhaptrongky = nhaptrongky;
        this.xuattrongky = xuattrongky;
        this.toncuoiky = toncuoiky;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    public String getXuatxu() {
        return xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        this.xuatxu = xuatxu;
    }

    public String getMausac() {
        return mausac;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
    }

    public String getKichthuoc() {
        return kichthuoc;
    }

    public void setKichthuoc(String kichthuoc) {
        this.kichthuoc = kichthuoc;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getTondauky() {
        return tondauky;
    }

    public void setTondauky(int tondauky) {
        this.tondauky = tondauky;
    }

    public int getNhaptrongky() {
        return nhaptrongky;
    }

    public void setNhaptrongky(int nhaptrongky) {
        this.nhaptrongky = nhaptrongky;
    }

    public int getXuattrongky() {
        return xuattrongky;
    }

    public void setXuattrongky(int xuattrongky) {
        this.xuattrongky = xuattrongky;
    }

    public int getToncuoiky() {
        return toncuoiky;
    }

    public void setToncuoiky(int toncuoiky) {
        this.toncuoiky = toncuoiky;
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
        final ThongKeTonKhoDTO other = (ThongKeTonKhoDTO) obj;
        if (this.masp != other.masp) {
            return false;
        }
        if (this.soluong != other.soluong) {
            return false;
        }
        if (this.trangthai != other.trangthai) {
            return false;
        }
        if (this.tondauky != other.tondauky) {
            return false;
        }
        if (this.nhaptrongky != other.nhaptrongky) {
            return false;
        }
        if (this.xuattrongky != other.xuattrongky) {
            return false;
        }
        if (this.toncuoiky != other.toncuoiky) {
            return false;
        }
        if (!Objects.equals(this.tensp, other.tensp)) {
            return false;
        }
        if (!Objects.equals(this.thuonghieu, other.thuonghieu)) {
            return false;
        }
        if (!Objects.equals(this.xuatxu, other.xuatxu)) {
            return false;
        }
        if (!Objects.equals(this.mausac, other.mausac)) {
            return false;
        }
        return Objects.equals(this.kichthuoc, other.kichthuoc);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.masp;
        hash = 29 * hash + Objects.hashCode(this.tensp);
        hash = 29 * hash + Objects.hashCode(this.thuonghieu);
        hash = 29 * hash + Objects.hashCode(this.xuatxu);
        hash = 29 * hash + Objects.hashCode(this.mausac);
        hash = 29 * hash + Objects.hashCode(this.kichthuoc);
        hash = 29 * hash + this.soluong;
        hash = 29 * hash + this.trangthai;
        hash = 29 * hash + this.tondauky;
        hash = 29 * hash + this.nhaptrongky;
        hash = 29 * hash + this.xuattrongky;
        hash = 29 * hash + this.toncuoiky;
        return hash;
    }

    @Override
    public String toString() {
        return "ThongKeTonKhoDTO{" +
                "masp=" + masp +
                ", tensp='" + tensp + '\'' +
                ", thuonghieu='" + thuonghieu + '\'' +
                ", xuatxu='" + xuatxu + '\'' +
                ", mausac='" + mausac + '\'' +
                ", kichthuoc='" + kichthuoc + '\'' +
                ", soluong=" + soluong +
                ", trangthai=" + trangthai +
                ", tondauky=" + tondauky +
                ", nhaptrongky=" + nhaptrongky +
                ", xuattrongky=" + xuattrongky +
                ", toncuoiky=" + toncuoiky +
                '}';
    }
}
