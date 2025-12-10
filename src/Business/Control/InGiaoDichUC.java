package Business.Control;

import java.util.ArrayList;
import java.util.List;

import Business.Enity.GiaoDich;
import Persistence.GiaoDichDTO;
import Persistence.*;

public class InGiaoDichUC {
    private GiaoDichDAOGateWay inGiaoDichDAOGateWay;
    public InGiaoDichUC(GiaoDichDAOGateWay inGiaoDichDAOGateWay){
        this.inGiaoDichDAOGateWay = inGiaoDichDAOGateWay;
    }
    public List<GiaoDichViewDTO> execute(){
        List<GiaoDich> listGD = null;
        List<GiaoDichDTO>listDTO = null;
        List<GiaoDichViewDTO> listViewDTO = null;
        try{
            listDTO = this.inGiaoDichDAOGateWay.getAll();
            listGD = this.convertToBusinessObject(listDTO);
            listViewDTO = this.convertToViewDTO(listGD);

        }catch(Exception e){
            e.printStackTrace();
        }
        return listViewDTO;
    }
    private List<GiaoDich> convertToBusinessObject(List<GiaoDichDTO> dto){
        List<GiaoDich> listGD = new ArrayList<>();
        for(GiaoDichDTO eleDTO : dto){
            GiaoDich gd = GiaoDichFactory.createGiaoDich(eleDTO);
            listGD.add(gd);
        }
        return listGD;
    }
    private List<GiaoDichViewDTO> convertToViewDTO(List<GiaoDich> gd){
        List<GiaoDichViewDTO> listDTO = new ArrayList<>();
        for(GiaoDich ele : gd){
            GiaoDichViewDTO dto = new GiaoDichViewDTO();
            dto.maGiaoDich = ele.getMaGiaoDich();
            dto.ngayGiaoDich = ele.getNgayGiaoDich();
            dto.donGia = ele.getDonGia();
            dto.soLuong = ele.getSoLuong();
            dto.loaiGiaoDich = ele.getLoaiGiaoDich();
            dto.thanhTien = ele.getThanhTien();
            listDTO.add(dto);
        }
        return listDTO;
    }
}
