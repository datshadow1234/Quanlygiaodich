package Presentation.Controller;

import Business.Control.*;
import Presentation.Model.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class GiaoDichTheoDonController {
    private GiaoDichViewModel model;
    private GiaoDichTheoDonUseCase useCase;

    public GiaoDichTheoDonController(GiaoDichViewModel model, GiaoDichTheoDonUseCase useCase) {
        this.model = model;
        this.useCase = useCase;
    }

    public void fetchGiaoDichLonHon1Ty() {
        List<GiaoDichViewDTO> dtoList = useCase.execute();//Gọi use case (tầng business) để lấy danh sách GiaoDichViewDTO.Tại bước này, execute() đã lọc sẵn giao dịch > 1 tỷ.
        List<GiaoDichViewItem> items = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        int stt = 1;
        for (GiaoDichViewDTO dto : dtoList) {//Duyệt qua từng dto (đối tượng GiaoDichViewDTO) trong danh sách.
            GiaoDichViewItem item = new GiaoDichViewItem();
            item.stt = stt++;
            item.maGiaoDich = dto.maGiaoDich;
            item.ngayGiaoDich = fmt.format(dto.ngayGiaoDich);
            item.donGia = String.format("%.0f", dto.donGia);
            item.soLuong = String.valueOf(dto.soLuong);
            item.loaiGiaoDich = dto.loaiGiaoDich;
            item.thanhTien = String.format("%.0f", dto.thanhTien);
            items.add(item);
        }
        model.listItem = items; //truyền dữ liệu cho model
        model.notifySubscriber();
    }
    
}

