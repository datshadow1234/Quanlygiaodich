package Persistence.OpenFormEditGD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Persistence.GiaoDichDTO;

public class LoadGiaoDichInFormDAO implements LoadGiaoDichInFormGateway {

    private static final String URL =
        "jdbc:mysql://localhost:3306/AppQuanLyGiaoDich_G6?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456"; // thay bằng password MySQL của bạn

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public GiaoDichDTO loadByMa(String ma) {
        GiaoDichDTO dto = null;
        String sql = "SELECT * FROM GiaoDich WHERE maGiaoDich = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ma);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dto = new GiaoDichDTO();
                    dto.maGiaoDich = rs.getString("maGiaoDich");
                    dto.ngayGiaoDich = rs.getDate("ngayGiaoDich"); // java.sql.Date
                    dto.donGia = rs.getDouble("donGia");
                    dto.soLuong = rs.getInt("soLuong");
                    dto.loaiGiaoDich = rs.getString("loaiGiaoDich");
                    dto.loaiVang = rs.getString("loaiVang");
                    dto.tiGia = rs.getDouble("tiGia");
                    dto.loaiTien = rs.getString("loaiTien");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc log lỗi
        }

        return dto;
    }

}
