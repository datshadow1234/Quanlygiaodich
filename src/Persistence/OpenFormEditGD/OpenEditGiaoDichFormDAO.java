package Persistence.OpenFormEditGD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OpenEditGiaoDichFormDAO implements OpenEditGiaoDichFormGateway {

    private final String URL = "jdbc:mysql://localhost:3306/AppQuanLyGiaoDich_G6?useSSL=false&serverTimezone=UTC";
    private final String USERNAME = "root";
    private final String PASSWORD = "123456";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public List<TypeGDDTO> getAll() {
        List<TypeGDDTO> result = new ArrayList<>();
        String sql = "SELECT DISTINCT loaiGiaoDich FROM GiaoDich";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("loaiGiaoDich");
                result.add(new TypeGDDTO(name));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
