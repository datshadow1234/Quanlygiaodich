package Business.Control;

import Business.Enity.GiaoDich;
import Business.Enity.Money;
import Business.Enity.Gold;
import Persistence.GiaoDichDAOGateWay;

import java.util.List;
import java.util.stream.Collectors;

public class TrungBinhThanhTienUseCase {
    private final GiaoDichDAOGateWay gateway;

    public TrungBinhThanhTienUseCase(GiaoDichDAOGateWay gateway) {
        this.gateway = gateway;
    }

    public double execute(Class<? extends GiaoDich> loai) {
        List<GiaoDich> danhSach = gateway.getAll().stream()
            .map(dto -> GiaoDichFactory.createGiaoDich(dto))
            .collect(Collectors.toList());

        return danhSach.stream()
            .filter(loai::isInstance)
            .mapToDouble(GiaoDich::thanhTien)
            .average()
            .orElse(0.0);
    }
}
