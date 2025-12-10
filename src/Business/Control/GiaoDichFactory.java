package Business.Control;

import Business.Enity.GiaoDich;
import Business.Enity.Gold;
import Business.Enity.Money;
import Persistence.GiaoDichDTO;

public class GiaoDichFactory {
    public static GiaoDich createGiaoDich(GiaoDichDTO dto){
        if(dto.loaiGiaoDich.equalsIgnoreCase("gold")) {
            return new Gold(dto.maGiaoDich, dto.ngayGiaoDich, dto.donGia, dto.soLuong, dto.loaiVang);
        } else 
        {
            if("VND".equalsIgnoreCase(dto.loaiTien)){
                return new Money(dto.maGiaoDich, dto.ngayGiaoDich, dto.donGia, dto.soLuong,dto.tiGia, dto.loaiTien);
            }else{
                return new Money(dto.maGiaoDich, dto.ngayGiaoDich, dto.donGia, dto.soLuong,dto.tiGia, dto.loaiTien);
            }
        }
            
    }
}
