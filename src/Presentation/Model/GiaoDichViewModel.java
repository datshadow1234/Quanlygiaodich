package Presentation.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Business.Control.*;
import Business.Enity.GiaoDich;

public class GiaoDichViewModel extends Publisher {
    public List<GiaoDichViewItem> listItem = new ArrayList<>();
    public List<GiaoDich> danhSachGiaoDich = new ArrayList<>();

    public void updateItem(GiaoDichViewItem item) {
    if (listItem == null) return;
    for (int i = 0; i < listItem.size(); i++) {
        if (item.maGiaoDich != null && item.maGiaoDich.equals(listItem.get(i).maGiaoDich)) {
            listItem.set(i, item);
            notifySubscriber();
            break;
        }
    }
}
    public void setListItem(List<GiaoDichViewItem> items) {
        this.listItem = items;
        notifySubscriber(); // Gửi tín hiệu cho view cập nhật giao diện
    }
    public List<GiaoDichViewItem> getListItem() {
    return listItem;
}


    public List<GiaoDich> getDanhSach() {
        return danhSachGiaoDich;
    }

    public void setDanhSach(List<GiaoDich> ds) {
        this.danhSachGiaoDich = ds;
    }

    // ✅ Đã thêm public tại đây 👇
    public GiaoDichViewItem fromEntity(GiaoDich gd, int stt) {
        GiaoDichViewItem item = new GiaoDichViewItem();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        item.stt = stt;
        item.maGiaoDich = gd.getMaGiaoDich();
        item.ngayGiaoDich = sdf.format(gd.getNgayGiaoDich());
        item.donGia = String.valueOf(gd.getDonGia());
        item.soLuong = String.valueOf(gd.getSoLuong());
        item.loaiGiaoDich = gd.getLoaiGiaoDich();
        item.thanhTien = String.valueOf(gd.thanhTien());

        if (gd instanceof Business.Enity.Gold) {
            Business.Enity.Gold gold = (Business.Enity.Gold) gd;
            item.loaiVang = gold.getLoaiVang();
            item.loaiChiTiet = gold.getLoaiVang();
        }

        if (gd instanceof Business.Enity.Money) {
            Business.Enity.Money money = (Business.Enity.Money) gd;
            item.loaiTien = money.getLoaiTien();
            item.tiGia = String.valueOf(money.getTiGia());
            item.loaiChiTiet = money.getLoaiTien();
        }

        return item;
        
    }
}
