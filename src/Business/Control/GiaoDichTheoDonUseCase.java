// === business/GiaoDichTheoDonUseCase.java ===
package Business.Control;

import Business.Enity.*;
import Persistence.*;

import java.util.ArrayList;
import java.util.List;

public class GiaoDichTheoDonUseCase {
    private final GiaoDichDAOGateWay gateway;

    public GiaoDichTheoDonUseCase(GiaoDichDAOGateWay gateway) {
        this.gateway = gateway;
    }

    public List<GiaoDichViewDTO> execute() {
        List<GiaoDichDTO> listDTO = gateway.getAll();
        List<GiaoDich> domainList = convertToBusinessObjects(listDTO);
        List<GiaoDichViewDTO> viewList = convertToViewModel(domainList);
        return viewList;
    }

   
    private List<GiaoDich> convertToBusinessObjects(List<GiaoDichDTO> dtoList) {//convertToBusinessObjects là hàm chuyển đổi dữ liệu từ DTO sang Domain Object (Business Object), đồng thời áp dụng logic nghiệp vụ (lọc giao dịch > 1 tỷ).
        List<GiaoDich> filtered = new ArrayList<>(); //tạo ds rỗng để chứa kết quả sau khi lọc
        for (GiaoDichDTO dto : dtoList) {
            GiaoDich giaoDich = GiaoDichFactory.createGiaoDich(dto);//Dùng Factory pattern để tạo ra đối tượng domain GiaoDich từ dto
            if (giaoDich.thanhTien() > 1_000_000_000) {
                filtered.add(giaoDich);
            }
        }
        return filtered;
    }

    private List<GiaoDichViewDTO> convertToViewModel(List<GiaoDich> domainList) { //hàm cho thấy nhiệm vụ chuyển từ Domain → ViewModel (ViewDTO).
        List<GiaoDichViewDTO> result = new ArrayList<>();
        for (GiaoDich gd : domainList) {
            GiaoDichViewDTO view = new GiaoDichViewDTO();
            view.maGiaoDich = gd.getMaGiaoDich();
            view.ngayGiaoDich = gd.getNgayGiaoDich();
            view.donGia = gd.getDonGia();
            view.soLuong = gd.getSoLuong();
            view.loaiGiaoDich = gd.getLoaiGiaoDich();
            view.thanhTien = gd.thanhTien();
            result.add(view);
        }
        return result;
    }
}
