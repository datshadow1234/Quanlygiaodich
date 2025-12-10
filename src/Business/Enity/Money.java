package Business.Enity;

import java.util.Date;


public class Money extends GiaoDich{
    private double tiGia;
    private String loaiTien;

    public Money(String maGiaoDich, Date ngayGiaoDich, double donGia, int soLuong, double tiGia, String loaiTien){
        super(maGiaoDich, ngayGiaoDich, donGia, soLuong, "money");
        this.tiGia = tiGia;
        this.loaiTien = loaiTien;
    }

   @Override
public double thanhTien() {
    if (loaiTien == null || loaiTien.trim().isEmpty()) {
        return 0;
    }

    String loaiTienLower = loaiTien.toLowerCase().trim();
    if (loaiTienLower.equals("vnd")) {
        return getSoLuong() * getDonGia();
    } else if (loaiTienLower.equals("usd") || loaiTienLower.equals("euro")) {
        return getSoLuong() * getDonGia() * getTiGia();
    } else {
        return 0; // đề phòng loại tiền không hợp lệ
    }
}

    

    //getter
    public double getTiGia() {
        return tiGia;
    }

    public String getLoaiTien(){
        return this.loaiTien;
    }
    
}
