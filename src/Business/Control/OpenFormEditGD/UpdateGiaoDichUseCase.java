package Business.Control.OpenFormEditGD;

import java.util.ArrayList;
import java.util.List;

import Business.Control.GiaoDichFactory;
import Business.Control.GiaoDichViewDTO;
import Business.Enity.GiaoDich;
import Persistence.GiaoDichDTO;
import Persistence.OpenFormEditGD.UpdateGiaoDichGateway;

public class UpdateGiaoDichUseCase {
    private UpdateGiaoDichGateway gateway;

    public UpdateGiaoDichUseCase(UpdateGiaoDichGateway gateway) {
        this.gateway = gateway;
    }

    public boolean execute(GiaoDichViewDTO viewDTO) {
        GiaoDichDTO dto = null;
        GiaoDich entity = null;
        try {
            dto = convertToDTO(viewDTO);
            this.gateway.update(dto);
            entity = this.convertToBusinessObject(dto);
            GiaoDichViewDTO updatedDTO = convertToViewDTO(entity);
            copyViewDTOFields(viewDTO, updatedDTO);
    
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private GiaoDich convertToBusinessObject(GiaoDichDTO dto){
        GiaoDich gd = GiaoDichFactory.createGiaoDich(dto);
        return gd;
    }


    private GiaoDichDTO convertToDTO(GiaoDichViewDTO viewDTO) {
        GiaoDichDTO dto = new GiaoDichDTO();
        dto.maGiaoDich = viewDTO.maGiaoDich;
        dto.ngayGiaoDich = viewDTO.ngayGiaoDich;
        dto.donGia = viewDTO.donGia;
        dto.soLuong = viewDTO.soLuong;
        dto.loaiGiaoDich = viewDTO.loaiGiaoDich;
       // dto.thanhTien = viewDTO.thanhTien;
       dto.loaiTien = viewDTO.loaiTien != null ? viewDTO.loaiTien : "";
    dto.loaiVang = viewDTO.loaiVang != null ? viewDTO.loaiVang : "";
    dto.tiGia = viewDTO.tiGia;
        return dto;
    }

    private GiaoDichViewDTO convertToViewDTO(GiaoDich gd){
        GiaoDichViewDTO dto = new GiaoDichViewDTO();
        dto.maGiaoDich = gd.getMaGiaoDich();
        dto.ngayGiaoDich = gd.getNgayGiaoDich();
        dto.donGia = gd.getDonGia();
        dto.soLuong = gd.getSoLuong();
        dto.loaiGiaoDich = gd.getLoaiGiaoDich();
        dto.thanhTien = gd.getThanhTien();
        
        return dto;
    }

    private void copyViewDTOFields(GiaoDichViewDTO target, GiaoDichViewDTO source) {
    target.maGiaoDich = source.maGiaoDich;
    target.ngayGiaoDich = source.ngayGiaoDich;
    target.donGia = source.donGia;
    target.soLuong = source.soLuong;
    target.loaiGiaoDich = source.loaiGiaoDich;
    target.thanhTien = source.thanhTien;
}



}
