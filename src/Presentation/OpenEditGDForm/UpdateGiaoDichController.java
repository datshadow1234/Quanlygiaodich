package Presentation.OpenEditGDForm;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Business.Control.GiaoDichViewDTO;
import Business.Control.OpenFormEditGD.UpdateGiaoDichUseCase;
import Presentation.Model.GiaoDichViewItem;
import Presentation.Model.GiaoDichViewModel;

public class UpdateGiaoDichController {

    private UpdateGiaoDichUseCase useCase;
    private GiaoDichViewModel viewModel;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public UpdateGiaoDichController(UpdateGiaoDichUseCase useCase, GiaoDichViewModel viewModel) {
        this.useCase = useCase;
        this.viewModel = viewModel;
    }

    /** Execute update giao dịch */
    public boolean execute(GiaoDichViewItem viewItem) {
        try {
            // 1. Chuyển từ String sang DTO
            GiaoDichViewDTO dto = toDTO(viewItem);

            // 2. Gọi UseCase để update và tính thanhTien
            boolean result = useCase.execute(dto);

            if (result) {
                // 3. Cập nhật lại viewItem với dữ liệu mới từ DTO (String)
                updateViewItemFromDTO(viewItem, dto);

                // 4. Cập nhật vào viewModel và thông báo UI
                viewModel.updateItem(viewItem);
                viewModel.notifySubscriber();
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Chuyển từ String (UI) sang DTO để UseCase xử lý */
   public static GiaoDichViewDTO toDTO(GiaoDichViewItem item) {
    GiaoDichViewDTO dto = new GiaoDichViewDTO();

    try {
        // Mã giao dịch
        dto.maGiaoDich = item.maGiaoDich != null ? item.maGiaoDich.trim() : "";

        // Ngày giao dịch
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (item.ngayGiaoDich != null && !item.ngayGiaoDich.isBlank()) {
            dto.ngayGiaoDich = sdf.parse(item.ngayGiaoDich.trim());
        } else {
            dto.ngayGiaoDich = new Date(System.currentTimeMillis()); // mặc định là hôm nay
        }

        // Đơn giá
        dto.donGia = parseDouble(item.donGia);

        // Số lượng
        dto.soLuong = parseInt(item.soLuong);

        // Thành tiền
        dto.thanhTien = parseDouble(item.thanhTien);

        // Loại giao dịch
        dto.loaiGiaoDich = item.loaiGiaoDich != null ? item.loaiGiaoDich.trim() : "";

        // Loại vàng / tiền / tỉ giá
        dto.loaiVang = item.loaiVang != null ? item.loaiVang.trim() : "";
        dto.loaiTien = item.loaiTien != null ? item.loaiTien.trim() : "";
        dto.tiGia = parseDouble(item.tiGia);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return dto;
}


    /** Cập nhật lại viewItem (String) từ DTO sau khi UseCase tính toán */
    private void updateViewItemFromDTO(GiaoDichViewItem item, GiaoDichViewDTO dto) {
        item.maGiaoDich = dto.maGiaoDich;
        item.ngayGiaoDich = sdf.format(dto.ngayGiaoDich);
        item.donGia = String.valueOf(dto.donGia);
        item.soLuong = String.valueOf(dto.soLuong);
        item.loaiGiaoDich = dto.loaiGiaoDich;
        item.thanhTien = String.valueOf(dto.thanhTien); // cập nhật thanhTien chính xác
        item.loaiTien = dto.loaiTien;
        item.loaiVang = dto.loaiVang;
        item.tiGia = String.valueOf(dto.tiGia);
    }

    /** Parse int từ String, trả về 0 nếu lỗi */
    private static int parseInt(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /** Parse double từ String, trả về 0 nếu lỗi */
    private static double parseDouble(String str) {
        try {
            if (str == null || str.isBlank()) return 0.0;
            String clean = str.replace(".", "").replace(",", ".");
            return Double.parseDouble(clean);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
