
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test1", "postgres", "123");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users;");

			while (rs.next()) {
				System.out.print(rs.getString("surname") + " ");
				System.out.print(rs.getString("name") + " ");
				System.out.println(rs.getString("date_of_birth"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
