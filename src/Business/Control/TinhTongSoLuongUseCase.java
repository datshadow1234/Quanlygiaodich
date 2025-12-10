package Business.Control;

import Persistence.GiaoDichDAOGateWay;

public class TinhTongSoLuongUseCase {
    private final GiaoDichDAOGateWay gateway;

    public TinhTongSoLuongUseCase(GiaoDichDAOGateWay gateway) {
        this.gateway = gateway;
    }

    public int execute(String loaiGiaoDich) {
        return gateway.getTongSoLuongTheoLoai(loaiGiaoDich);
    }
}
