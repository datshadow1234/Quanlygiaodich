package Presentation.Controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Business.Control.GiaoDichViewDTO;
import Business.Control.InGiaoDichUC;
import Presentation.Model.GiaoDichViewItem;
import Presentation.Model.GiaoDichViewModel;

public class InGiaoDichController {
    private InGiaoDichUC inGiaoDichUC;
    private GiaoDichViewModel giaoDichViewModel;
    public InGiaoDichController(InGiaoDichUC inGiaoDichUC, GiaoDichViewModel giaoDichViewModel){
        this.inGiaoDichUC = inGiaoDichUC;
        this.giaoDichViewModel = giaoDichViewModel;
    }
    public void run(){
        List<GiaoDichViewDTO> listDTO = this.inGiaoDichUC.execute();
        List<GiaoDichViewItem> listViewItem = this.convertToPresent(listDTO);
        this.giaoDichViewModel.listItem = listViewItem;
        this.giaoDichViewModel.notifySubscriber();
    }
    private List<GiaoDichViewItem> convertToPresent(List<GiaoDichViewDTO> listDTO){
        List<GiaoDichViewItem> listItem = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Locale VN = new Locale("vi","VN");
        NumberFormat format = NumberFormat.getInstance(VN);

        int stt = 1;
        for(GiaoDichViewDTO  dto : listDTO){
            GiaoDichViewItem viewItem = new GiaoDichViewItem();
            viewItem.stt = stt++;
            viewItem.maGiaoDich = dto.maGiaoDich;
            viewItem.ngayGiaoDich = fmt.format(dto.ngayGiaoDich);
            viewItem.donGia = format.format(dto.donGia);
            viewItem.soLuong = String.format("%d", dto.soLuong);
            viewItem.loaiGiaoDich = dto.loaiGiaoDich;
            viewItem.thanhTien = format.format(dto.thanhTien);
            listItem.add(viewItem);
        }
        
        return listItem;
    }
}
