package Presentation.OpenEditGDForm;
import Business.Control.OpenFormEditGD.LoadGiaoDichInFormUC;
import Presentation.Model.GiaoDichViewItem;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import Business.Control.GiaoDichViewDTO;
public class LoadGiaoDichInFormController {

    private OpenEditGDFormModel model;
    private LoadGiaoDichInFormUC useCase;

    public LoadGiaoDichInFormController(OpenEditGDFormModel model, LoadGiaoDichInFormUC useCase) {
        this.model = model;
        this.useCase = useCase;
    }

    public void execute(String ma) {
        GiaoDichViewDTO viewDTO = useCase.execute(ma);
        model.item = convertToPresent(viewDTO);
        model.notifySubscriber();
        
    }

    private GiaoDichViewItem convertToPresent(GiaoDichViewDTO dto){
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Locale VN = new Locale("vi","VN");
        NumberFormat format = NumberFormat.getInstance(VN);

        GiaoDichViewItem viewItem = new GiaoDichViewItem();
        viewItem.maGiaoDich = dto.maGiaoDich;
        viewItem.ngayGiaoDich = fmt.format(dto.ngayGiaoDich);
        viewItem.donGia = format.format(dto.donGia);
        viewItem.soLuong = String.format("%d", dto.soLuong);
        viewItem.loaiGiaoDich = dto.loaiGiaoDich;
        viewItem.thanhTien =format.format(dto.thanhTien);
        return viewItem;
    }

}
