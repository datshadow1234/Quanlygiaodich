package Business.Enity;

import java.util.Date;

public class Gold extends GiaoDich{
    private String loaiVang;
    public Gold(String maGiaoDich, Date ngayGiaoDich, double donGia, int soLuong, String loaiVang){
        super(maGiaoDich, ngayGiaoDich, donGia, soLuong, "gold"); // super class được tạo ở đây nè ae g6
        this.loaiVang = loaiVang;
    }
    @Override
    public double thanhTien(){
        
        return (getSoLuong() * getDonGia());
    }
    //getter
    public String getLoaiVang() {
        return loaiVang;
    }
}
