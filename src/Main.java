import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Config.properties not found.");
                return;
            }
            props.load(input);

            String jdbcURL = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");


            try (Connection conn = DriverManager.getConnection(jdbcURL, username, password)) {
                System.out.println("Successful connection!");


                String sql = "SELECT ID, Ime FROM studenti";

                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {


                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        String ime = rs.getString("Ime");


                        System.out.println("ID: " + id + ", Ime: " + ime);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error while merging or executing query.");
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
