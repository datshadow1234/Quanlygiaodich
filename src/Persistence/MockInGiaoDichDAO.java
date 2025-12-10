package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class MockInGiaoDichDAO implements GiaoDichWriterDAOGateway {
    private Connection conn;
    private Connection getConnection() throws Exception {
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
                    return conn;
    }

    @Override
    public void update(GiaoDichDTO dto) {
        String sql = "UPDATE GiaoDich SET ngayGiaoDich=?, donGia=?, soLuong=?, loaiGiaoDich=?, loaiVang=?, tiGia=?, loaiTien=? WHERE maGiaoDich=?";

        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setDate(1, new java.sql.Date(dto.ngayGiaoDich.getTime()));
            stmt.setDouble(2, dto.donGia);
            stmt.setInt(3, dto.soLuong);
            stmt.setString(4, dto.loaiGiaoDich);

            // Xử lý loại vàng
            if ("VANG".equalsIgnoreCase(dto.loaiGiaoDich)) {
                stmt.setString(5, dto.loaiVang);        // Có dữ liệu
            } else {
                stmt.setNull(5, java.sql.Types.VARCHAR); // Không phải vàng → null
            }

            // Xử lý tỉ giá
            if ("TIEN".equalsIgnoreCase(dto.loaiGiaoDich)) {
                stmt.setDouble(6, dto.tiGia);
            } else {
                stmt.setNull(6, java.sql.Types.DOUBLE); // Không phải tiền → null
            }

            // Xử lý loại tiền
            if ("TIEN".equalsIgnoreCase(dto.loaiGiaoDich)) {
                stmt.setString(7, dto.loaiTien);
            } else {
                stmt.setNull(7, java.sql.Types.VARCHAR); // Không phải tiền → null
            }
            stmt.setString(8, dto.maGiaoDich); // WHERE điều kiện

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(String maGiaoDich) {
        String sql = "DELETE FROM GiaoDich WHERE maGiaoDich=?";
        
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, maGiaoDich);
            stmt.executeUpdate();
            System.out.println("Đã xóa giao dịch: " + maGiaoDich);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
