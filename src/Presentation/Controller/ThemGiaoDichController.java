package Presentation.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Business.Control.LuuGiaoDichUseCase;
import Business.Enity.GiaoDich; // IMPORTANT: để bắt Gold/Money
import Persistence.GiaoDichDTO;
import Presentation.Model.GiaoDichViewItem;
import Presentation.Model.GiaoDichViewModel;

public class ThemGiaoDichController {
    private final GiaoDichViewModel viewModel;
    private final LuuGiaoDichUseCase useCase;

    public ThemGiaoDichController(GiaoDichViewModel vm, LuuGiaoDichUseCase useCase){
        this.viewModel = vm;
        this.useCase = useCase;
    }

    // Dành cho flow dùng DTO (nếu cần)
    public void themGiaoDich(GiaoDichDTO dto){
        useCase.luuTuDTO(dto);
        capNhatViewModel();
    }

    // Flow thầy: Form tạo entity Gold/Money
    public void themGiaoDich(GiaoDich gd){
        useCase.luu(gd);
        capNhatViewModel();
    }

    public void capNhatViewModel(){
        List<GiaoDichDTO> list = useCase.getAll();
        List<GiaoDichViewItem> danhSachViewItem = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        int stt = 1;

        for (GiaoDichDTO dto : list){
            GiaoDichViewItem item = new GiaoDichViewItem();
            item.stt = stt++;
            item.maGiaoDich = dto.maGiaoDich;
            item.ngayGiaoDich = dto.ngayGiaoDich != null ? df.format(dto.ngayGiaoDich) : "";
            item.donGia = String.valueOf(dto.donGia);
            item.soLuong = String.valueOf(dto.soLuong);
            item.thanhTien = String.valueOf(dto.thanhTien);

            String loai = dto.loaiGiaoDich;
            if ("gold".equalsIgnoreCase(loai) || "Vàng".equalsIgnoreCase(loai)){
                item.loaiGiaoDich = "Vàng";
                item.loaiChiTiet = dto.loaiVang;
            } else if ("money".equalsIgnoreCase(loai) || "Tiền".equalsIgnoreCase(loai) || "Tiền tệ".equalsIgnoreCase(loai)){
                item.loaiGiaoDich = "Tiền tệ";
                item.loaiChiTiet = dto.loaiTien;
            } else {
                item.loaiGiaoDich = "Không xác định";
                item.loaiChiTiet = dto.loaiChiTiet != null ? dto.loaiChiTiet : "";
            }
            danhSachViewItem.add(item);
        }

        viewModel.listItem = danhSachViewItem;
        viewModel.notifySubscriber();
    }
}
