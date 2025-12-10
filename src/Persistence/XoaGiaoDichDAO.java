package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class XoaGiaoDichDAO implements XoaGiaoDichDAOGateWay{
       private Connection conn;
        public XoaGiaoDichDAO() {
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
    public void delete(XoaGiaoDichDTO xoa) {
    String sql = "DELETE FROM GiaoDich WHERE maGiaoDich = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, xoa.maGiaoDich);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace(); // hoặc throw custom exception lên tầng trên
    }
}
}
