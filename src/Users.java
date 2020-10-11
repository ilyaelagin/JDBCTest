
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Users {
	// Исправленная кодировка
	public static final String url = "jdbc:postgresql://localhost:5432/test1";
	public static final String login = "postgres";
	public static final String password = "123";

	public static void main(String[] args) {
		
		new Users().getTables();
	}

	public void getTables() {
		try (Connection conn = getConnection()) {
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("Введите имя: ");
			String name = scanner.nextLine();
			System.out.print("Введите фамилию: ");
			String surname = scanner.nextLine();
			System.out.print("Введите ДР: ");
			String birth = scanner.nextLine();
			System.out.print("Введите id: ");	
			int id = scanner.nextInt();
			
			scanner.close();
			
			CreateUsers createUser1 = new CreateUsers(name, surname, birth, id);
			createUser1.addUsers(conn);	
			
			CreateUsers createUser2 = new CreateUsers("Wil", "smyth", "1968-11-24", 4);
			createUser2.addUsers(conn);
			
			CreateUsers createUser3 = new CreateUsers("Will", "Smith", "1968-09-25", 4);
			createUser3.updateUsers(conn);
			
			CreateUsers deleteUser4 = new CreateUsers(5);
			deleteUser4.deleteUsers(conn);
			
			// Выполняем SQL-запрос в БД на чтение данных
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users;");

			System.out.println();
			
			while (rs.next()) {
				String str = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " id:" + rs.getInt(4);
				System.out.println(str);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	};

	private Connection getConnection() throws Exception {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(url, login, password);
	}

	class CreateUsers {

		String name;
		String surname;
		String birth;
		int id;

		String url = "jdbc:postgresql://localhost:5432/test1";
		String login = "postgres";
		String password = "123";
		
		public CreateUsers(String name, String surname, String birth, int id) {

			this.name = name;
			this.surname = surname;
			this.birth = birth;
			this.id = id;
		}

		public CreateUsers(int id) {
			super();
			this.id = id;
		}

	void addUsers(Connection conn) {
		try {
			String sql1 = "INSERT INTO users VALUES(?, ?, ?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql1);

			pstmt.setString(1, name);
			pstmt.setString(2, surname);
			pstmt.setDate(3, java.sql.Date.valueOf(birth));
			pstmt.setInt(4, id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void updateUsers(Connection conn) {
		
		try {
			String sql2 = "UPDATE users SET name = ?, surname = ?, date_of_birth = ? WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql2);

			pstmt.setString(1, name);
			pstmt.setString(2, surname);
			pstmt.setDate(3, java.sql.Date.valueOf(birth));
			pstmt.setInt(4, id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

		void deleteUsers(Connection conn) {
			try {
				String sql3 = "DELETE FROM users WHERE id = ?";

				PreparedStatement pstmt = conn.prepareStatement(sql3);

				pstmt.setInt(1, id);

				pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
