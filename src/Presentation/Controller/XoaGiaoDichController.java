package Presentation.Controller;


import Business.Control.XoaGiaoDichUC;
import Business.Control.XoaGiaoDichViewDTO;
import Presentation.Model.XoaGDViewItem;
import Presentation.Model.XoaGDViewModel;
import Presentation.View.InGiaoDichUI;

public class XoaGiaoDichController {
    private InGiaoDichUI inGiaoDichUI;
    private XoaGiaoDichUC xoaGiaoDichUC;
    private XoaGDViewModel xoaGDViewModel;

    public XoaGiaoDichController(InGiaoDichUI inGiaoDichUI, XoaGiaoDichUC xoaGiaoDichUC, XoaGDViewModel xoaGDViewModel){
        this.inGiaoDichUI = inGiaoDichUI;
        this.xoaGiaoDichUC = xoaGiaoDichUC;
        this.xoaGDViewModel = xoaGDViewModel;
    }
    public void run(){
        
        XoaGDViewItem item = new XoaGDViewItem();
        item.maGiaoDich = inGiaoDichUI.getMaGiaoDich();
        xoaGDViewModel.xoa = item;
        XoaGiaoDichViewDTO xoaDTO = convertToViewDTO(xoaGDViewModel);
        xoaGiaoDichUC.execute(xoaDTO);

    }
    private XoaGiaoDichViewDTO convertToViewDTO(XoaGDViewModel xoaGDViewModel){
        XoaGiaoDichViewDTO xoaGiaoDichViewDTO = new XoaGiaoDichViewDTO();
        xoaGiaoDichViewDTO.maGiaoDich = xoaGDViewModel.xoa.maGiaoDich;
        return xoaGiaoDichViewDTO;
    }

}
