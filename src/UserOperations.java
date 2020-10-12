import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserOperations {
	
	public static final String url = "jdbc:postgresql://localhost:5432/test1";
	public static final String login = "postgres";
	public static final String password = "123";
	
	Connection con;
	
	public UserOperations() {
		try {
			Class.forName("org.postgresql.Driver");
			 this.con = DriverManager.getConnection(url, login, password);
			 
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		};
	}
	
	void showUsersData() {
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY id;");
			
			while (rs.next()) {
				String str = rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("date_of_birth") + " id:" + rs.getInt("id");
				System.out.println(str);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void create(Users users) {
		try {
			String sql1 = "INSERT INTO users(name, surname, date_of_birth, id) VALUES(?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(sql1);

			pstmt.setString(1, users.getName());
			pstmt.setString(2, users.getSurname());
			pstmt.setDate(3, java.sql.Date.valueOf(users.getBirth()));
			pstmt.setInt(4, users.getId());

			pstmt.executeUpdate();
			
			System.out.println("Добавлен новый пользователь: " + users.getName() + " " + users.getSurname());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void createWithConsole(Users users) {
		
		try(Scanner scanner = new Scanner(System.in)) {
			
			System.out.print("Введите имя: ");
			users.setName(scanner.nextLine());
			
			System.out.print("Введите фамилию: ");
			users.setSurname(scanner.nextLine());
			
			System.out.print("Введите дату рождения: ");
			users.setBirth(scanner.nextLine());
			
			System.out.print("Введите id: ");
			users.setId(scanner.nextInt());
			
			String sql1 = "INSERT INTO users VALUES(?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(sql1);

			pstmt.setString(1, users.getName());
			pstmt.setString(2, users.getSurname());
			pstmt.setDate(3, java.sql.Date.valueOf(users.getBirth()));
			pstmt.setInt(4, users.getId());

			pstmt.executeUpdate();
			
			System.out.println();
			System.out.println("Добавлен новый пользователь: " + users.getName() + " " + users.getSurname());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void update(Users users) {

		try {
			String sql2 = "UPDATE users SET name = ?, surname = ?, date_of_birth = ? WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql2);

			pstmt.setString(1, users.getName());
			pstmt.setString(2, users.getSurname());
			pstmt.setDate(3, java.sql.Date.valueOf(users.getBirth()));
			pstmt.setInt(4, users.getId());

			pstmt.executeUpdate();
			
			System.out.println("Обновлены данные пользователя: " + users.getName() + " " + users.getSurname());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void delete(Users users) {
		try {
			String sql3 = "DELETE FROM users WHERE id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql3);

			pstmt.setInt(1, users.getId());

			pstmt.executeUpdate();
			
			System.out.println("Удален пользователь c id: " + users.getId());  

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
