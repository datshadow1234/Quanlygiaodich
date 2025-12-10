package Persistence;

import java.util.List;

import Business.Enity.GiaoDich;


public interface GiaoDichDAOGateWay {
    List<GiaoDichDTO> getAll();

    void save(GiaoDich gd);

    int getTongSoLuongTheoLoai(String loaiGiaoDich);

}
