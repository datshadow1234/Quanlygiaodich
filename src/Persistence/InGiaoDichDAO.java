package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import Business.Enity.GiaoDich;
import Business.Enity.Gold;
import Business.Enity.Money;

public class InGiaoDichDAO implements GiaoDichDAOGateWay {
    private Connection conn;

    public InGiaoDichDAO() {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
		String username = "root";
		String password = "123456";
		String url = "jdbc:mysql://localhost:3306/AppQuanLyGiaoDich_G6?useSSL=false&serverTimezone=UTC";
		conn = DriverManager.getConnection(url, username, password);
		System.out.println("Connected!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<GiaoDichDTO> getAll() {
        List<GiaoDichDTO> listDTO = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String sqlQuery = "SELECT maGiaoDich, ngayGiaoDich, donGia, soLuong, loaiGiaoDich, loaiVang, tiGia, loaiTien FROM GiaoDich";
    
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
    
            while (rs.next()) {
                GiaoDichDTO dto = new GiaoDichDTO();
                dto.maGiaoDich = rs.getString("maGiaoDich");
                dto.ngayGiaoDich = fmt.parse(rs.getString("ngayGiaoDich"));
                dto.donGia = rs.getDouble("donGia");
                dto.soLuong = rs.getInt("soLuong");
                dto.loaiGiaoDich = rs.getString("loaiGiaoDich");
                dto.loaiVang = rs.getString("loaiVang");
                dto.tiGia = rs.getDouble("tiGia");
                dto.loaiTien = rs.getString("loaiTien");
                listDTO.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        return listDTO;
    }
    @Override
public void save(GiaoDich gd) {
    try {
        String sql = "";
        PreparedStatement stmt = null;

        if (gd instanceof Gold) {
            Gold gold = (Gold) gd;
            sql = "INSERT INTO GiaoDich (maGiaoDich, ngayGiaoDich, donGia, soLuong, loaiGiaoDich, loaiVang) VALUES (?, ?, ?, ?, 'Gold', ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, gold.getMaGiaoDich());
            stmt.setDate(2, new java.sql.Date(gold.getNgayGiaoDich().getTime()));
            stmt.setDouble(3, gold.getDonGia());
            stmt.setInt(4, gold.getSoLuong());
            stmt.setString(5, gold.getLoaiVang());
        } else if (gd instanceof Money) {
            Money money = (Money) gd;
            sql = "INSERT INTO GiaoDich (maGiaoDich, ngayGiaoDich, donGia, soLuong, loaiGiaoDich, tiGia, loaiTien) VALUES (?, ?, ?, ?, 'Money', ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, money.getMaGiaoDich());
            stmt.setDate(2, new java.sql.Date(money.getNgayGiaoDich().getTime()));
            stmt.setDouble(3, money.getDonGia());
            stmt.setInt(4, money.getSoLuong());
            stmt.setDouble(5, money.getTiGia());
            stmt.setString(6, money.getLoaiTien());
        }

        if (stmt != null) {
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Thêm giao dịch thành công!");
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi thêm giao dịch: " + e.getMessage());
        e.printStackTrace();
    }
}

@Override
public int getTongSoLuongTheoLoai(String loaiGiaoDich) {
    String sql = "SELECT SUM(soLuong) AS total FROM GiaoDich WHERE loaiGiaoDich = ?";
    PreparedStatement stmt = null;
    ResultSet rs = null;
    int total = 0;

    try {
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, loaiGiaoDich);
        rs = stmt.executeQuery();

        if (rs.next()) {
            total = rs.getInt("total");
        }
    } catch (SQLException e) {
        System.err.println("SQL Error: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return total;
}

}