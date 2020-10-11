import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SanyaUsers {

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			Connection conn = null;
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test1", "postgres", "123");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users;");

			List<String> columnNameList = getColumnNameList(rs);
			while (rs.next()) {
				for (String columnName : columnNameList) {
					System.out.print(columnName + ": " + rs.getString(columnName) + "\n");
				}
				System.out.println("-----------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<String> getColumnNameList(ResultSet rs) throws SQLException {
		List<String> columnNameList = new ArrayList<>();
		ResultSetMetaData meta = rs.getMetaData();
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			columnNameList.add(meta.getColumnName(i));
		}
		return columnNameList;
	}

}
