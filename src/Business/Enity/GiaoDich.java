package Business.Enity;

import java.util.Date;

public abstract class GiaoDich {
    protected String maGiaoDich;
    protected Date ngayGiaoDich;
    protected double donGia;
    protected int soLuong;
    protected String loaiGiaoDich;
    
    protected GiaoDich(String maGiaoDich, Date ngayGiaoDich, double donGia, int soLuong, String loaiGiaoDich){
        this.maGiaoDich = maGiaoDich;
        this.ngayGiaoDich = ngayGiaoDich;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.loaiGiaoDich = loaiGiaoDich;
    }
    public abstract double thanhTien();

    // getter
    public String getMaGiaoDich() {
        return maGiaoDich;
    }
    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }
    public double getDonGia() {
        return donGia;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public String getLoaiGiaoDich() {
        return loaiGiaoDich;
    }
    public double getThanhTien(){
        return this.thanhTien();
    }
}
