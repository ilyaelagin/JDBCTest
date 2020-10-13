import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		}
	}

	void showUsersData() {
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY id;");

			while (rs.next()) {
				String str = rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("date_of_birth")
						+ " id:" + rs.getInt("id");
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

		try (Scanner scanner = new Scanner(System.in)) {

			while (true) {
				System.out.print("Введите фамилию:");
				String name = scanner.nextLine();
				if (name.matches("[а-яА-Яa-zA-z]+")) {
					users.setName(name);
					break;
				} else {
					System.out.println("Недопустимые символы в фамилии!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите фамилию: ");
				String surname = scanner.nextLine();
				if (surname.matches("[а-яА-Яa-zA-z]+")) {
					users.setSurname(surname);
					break;
				} else {
					System.out.println("Недопустимые символы в фамилии!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите дату рождения: ");
				String birth = scanner.nextLine();
				if (isValidFormat("yyyy-MM-dd", birth)) {
					users.setBirth(birth);
					break;
				} else {
					continue;
				}
			}

			while (true) {
				System.out.print("Введите id: ");
				int id = scanner.nextInt();
				if (id >= 0) {
					users.setId(id);
					break;
				} else {
					continue;
				}
			}

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

	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
				System.out.println("Некорректная дата.");
			}
		} catch (ParseException ex) {
			System.out.println("Ошибка. Формат даты: 2020-12-31");
		}
		return date != null;
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

			System.out.println("Обновлены данные пользователя с id: " + users.getId());

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
