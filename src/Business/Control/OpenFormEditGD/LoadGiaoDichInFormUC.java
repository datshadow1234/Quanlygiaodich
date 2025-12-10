package Business.Control.OpenFormEditGD;

import Business.Control.GiaoDichFactory;
import Business.Control.GiaoDichViewDTO;
import Business.Enity.GiaoDich;
import Persistence.GiaoDichDTO;
import Persistence.OpenFormEditGD.LoadGiaoDichInFormGateway;

public class LoadGiaoDichInFormUC {
    private LoadGiaoDichInFormGateway gateway;

    public LoadGiaoDichInFormUC(LoadGiaoDichInFormGateway gateway) {
        this.gateway = gateway;
    }

    public GiaoDichViewDTO execute(String ma) {
        GiaoDich gd = null;
        GiaoDichViewDTO viewDTO = null;
        GiaoDichDTO dto = null;
        try{
            dto = this.gateway.loadByMa(ma);
            gd = this.convertToBusinessObject(dto);
            viewDTO = this.convertToViewDTO(gd);

        }catch(Exception e){
            e.printStackTrace();
        }
        return viewDTO;
    }


    private GiaoDich convertToBusinessObject(GiaoDichDTO dto){
        return GiaoDichFactory.createGiaoDich(dto);
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

}
