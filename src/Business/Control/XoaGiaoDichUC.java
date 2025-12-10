package Business.Control;



import Persistence.XoaGiaoDichDAOGateWay;
import Persistence.XoaGiaoDichDTO;

public class XoaGiaoDichUC {
    private XoaGiaoDichDAOGateWay xoaGiaoDichDAOGateWay;
    public XoaGiaoDichUC(XoaGiaoDichDAOGateWay xoaGiaoDichDAOGateWay){
        this.xoaGiaoDichDAOGateWay = xoaGiaoDichDAOGateWay;
    }
    public void execute(XoaGiaoDichViewDTO xoaGiaoDichViewDTO){
        XoaGiaoDichDTO dto = convertToDTO(xoaGiaoDichViewDTO);
        this.xoaGiaoDichDAOGateWay.delete(dto);

    }
    public XoaGiaoDichDTO convertToDTO(XoaGiaoDichViewDTO viewDTO){
        XoaGiaoDichDTO dto = new XoaGiaoDichDTO();
        dto.maGiaoDich = viewDTO.maGiaoDich;
        return dto;
    }
}
