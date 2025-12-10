package Business.Control;

import java.util.List;

import Business.Enity.GiaoDich;
import Persistence.GiaoDichDAOGateWay;
import Persistence.GiaoDichDTO;

public class LuuGiaoDichUseCase {
    private final GiaoDichDAOGateWay dao;

    public LuuGiaoDichUseCase(GiaoDichDAOGateWay dao){
        this.dao = dao;
    }

    // Gộp từ Management
    public List<GiaoDichDTO> getAll() { return dao.getAll(); }

    public void luuTuDTO(GiaoDichDTO dto){
        GiaoDich entity = GiaoDichFactory.createGiaoDich(dto);
        validate(entity);
        dao.save(entity);
    }

    public void luu(GiaoDich entity){
        validate(entity);
        dao.save(entity);
    }

    private void validate(GiaoDich gd){
        if (gd == null) throw new IllegalArgumentException("Giao dịch không được null");
        if (gd.getMaGiaoDich() == null || gd.getMaGiaoDich().trim().isEmpty())
            throw new IllegalArgumentException("Mã giao dịch không được để trống");
        if (gd.getDonGia() <= 0) throw new IllegalArgumentException("Đơn giá phải > 0");
        if (gd.getSoLuong() <= 0) throw new IllegalArgumentException("Số lượng phải > 0");
        if (gd.getNgayGiaoDich() == null) throw new IllegalArgumentException("Ngày giao dịch không hợp lệ");
    }
}
