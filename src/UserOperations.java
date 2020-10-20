import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

public class UserOperations {
	public static final String url = "jdbc:postgresql://localhost:5432/test1";
	public static final String login = "postgres";
	public static final String password = "123";

	public static final String NAME_PATTERN = "[а-яА-Яa-zA-z]+(\\s|-)?([а-яА-Яa-zA-z]+)?";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String INT_PATTERN = "-?\\d+";
	public static final String NO_PATTERN = "no";
	public static final String YES_PATTERN = "yes";

	private Connection con;

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
				String str = "id:" + rs.getInt("id") + " " + "tabnum:" + rs.getInt("tabnum") + " "
						+ rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("date_of_birth");
				System.out.println(str);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void createWithConsole(User user) {

		try (Scanner scanner = new Scanner(System.in)) {

			while (true) {
				System.out.print("Введите tabnum(табельный номер): ");
				String tm = scanner.nextLine().trim();
				if (!tm.matches(INT_PATTERN)) {
					System.out.println("Табельный номер должен быть числовой!");
					continue;
				}
				int tabnum = Integer.parseInt(tm);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT tabnum FROM users WHERE tabnum = " + tabnum + ";");

				if (rs.next()) {
					System.out.println("Значение tabnum:" + tabnum + " уже есть в базе! Введите уникальное значение.");
					continue;
				} else {
					user.setTabnum(tabnum);
				}

				if (tabnum >= 0) {
					user.setTabnum(tabnum);
					break;
				} else {
					System.out.println("Отрицательный tabnum недопускается!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите имя: ");

				String name = scanner.nextLine();
				if (name.matches(NAME_PATTERN)) {
					user.setName(name);
					break;
				} else {
					System.out.println("Недопустимые символы в имени!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите фамилию: ");
				String surname = scanner.nextLine();
				if (surname.matches(NAME_PATTERN)) {
					user.setSurname(surname);
					break;
				} else {
					System.out.println("Недопустимые символы в фамилии!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите дату рождения: ");
				String birth = scanner.nextLine();
				if (isValidFormat(birth)) {
					user.setBirth(birth);
					break;
				} else {
					continue;
				}
			}

			String sql1 = "INSERT INTO users(tabnum, name, surname, date_of_birth) VALUES(?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(sql1);

			pstmt.setInt(1, user.getTabnum());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getSurname());
			pstmt.setDate(4, java.sql.Date.valueOf(user.getBirth()));
			pstmt.executeUpdate();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE tabnum = " + user.getTabnum() + ";");
			rs.next();
			System.out.println("\nДобавлен новый пользователь: " + "id:" + rs.getInt("id") + " tabnum:"
					+ user.getTabnum() + " " + user.getName() + " " + user.getSurname() + " " + user.getBirth());

		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isValidFormat(String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
				System.out.println("Некорректная дата. Формат даты: 2020-12-31");
			}
		} catch (ParseException ex) {
			System.out.println("Ошибка. Формат даты: 2020-12-31");
		}
		return date != null;
	}

	void updateWithConsole(User user) {

		try (Scanner scanner = new Scanner(System.in)) {

			while (true) {
				System.out.print("Изменение данных пользователя. Введите id: ");
				String i = scanner.nextLine().trim();
				if (!i.matches(INT_PATTERN)) {
					System.out.println("id должен быть числовой!");
					continue;
				}
				int id = Integer.parseInt(i);
				if (id >= 0) {
					user.setId(id);
				} else {
					System.out.println("Введен отрицательный id!");
					continue;
				}
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE id = " + id + ";");

				if (!rs.next()) {
					System.out.println("Пользователь с id:" + id + " не найден!");
					continue;
				} else {
					break;
				}
			}

			String sql10 = "SELECT * FROM users WHERE id = ?;";
			PreparedStatement pstmt = con.prepareStatement(sql10);
			pstmt.setInt(1, user.getId());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String str = "id:" + rs.getInt("id") + " " + "tabnum:" + rs.getInt("tabnum") + " "
						+ rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("date_of_birth");
				System.out.println(str);
			} else {
				System.out.println("Пользователь с id:" + user.getId() + " не найден.");
			}

			User userDB = new User(rs);

			while (true) {
				System.out.print("\nВведите новый tabnum (или нажмите Enter, чтобы оставить старое значение): ");
				scanner.nextLine();
				String tabnum = (String) scanner.nextLine();
				if ("".equals(tabnum)) {
					break;
				} else if (Integer.parseInt(tabnum) >= 0) {
					userDB.setTabnum(Integer.parseInt(tabnum));
					break;
				} else {
					continue;
				}
			}

			while (true) {
				System.out.print("Введите имя (или нажмите Enter, чтобы оставить старое значение): ");
				String name = scanner.nextLine();
				if (name.matches(NAME_PATTERN)) {
					userDB.setName(name);
					break;
				} else if ("".equals(name)) {
					break;
				} else {
					System.out.println("Недопустимые символы в имени!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите фамилию (или нажмите Enter, чтобы оставить старое значение): ");
				String surname = scanner.nextLine();
				if (surname.matches(NAME_PATTERN)) {
					userDB.setSurname(surname);
					break;
				} else if ("".equals(surname)) {
					break;
				} else {
					System.out.println("Недопустимые символы в фамилии!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите дату рождения (или нажмите Enter, чтобы оставить старое значение): ");
				String birth = scanner.nextLine();
				if ("".equals(birth)) {
					break;
				} else if (isValidFormat(birth)) {
					userDB.setBirth(birth);
					break;
				} else {
					continue;
				}
			}

			String sql1 = "UPDATE users SET tabnum = ?, name = ?, surname = ?, date_of_birth = ? WHERE id ="
					+ rs.getInt("id") + ";";

			PreparedStatement pstmt2 = con.prepareStatement(sql1);

			pstmt2.setInt(1, userDB.getTabnum());
			pstmt2.setString(2, userDB.getName());
			pstmt2.setString(3, userDB.getSurname());
			pstmt2.setDate(4, java.sql.Date.valueOf(userDB.getBirth()));
			pstmt2.executeUpdate();

			System.out.println("\nОбновлены данные пользователя: " + userDB);

		} catch (InputMismatchException e) {
			e.printStackTrace();
			;
		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void delete(User user) {
		try (Scanner scanner = new Scanner(System.in); Scanner scanner2 = new Scanner(System.in)) {

			while (true) {
				System.out.print("Удаление пользователя. Введите id: ");
				String i = scanner.nextLine().trim();
				if (!i.matches(INT_PATTERN)) {
					System.out.println("id должен быть числовой!");
					continue;
				}
				int id = Integer.parseInt(i);
				if (id >= 0) {
					user.setId(id);
				} else {
					System.out.println("Введен отрицательный id!");
					continue;
				}
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE id = " + id + ";");

				if (!rs.next()) {
					System.out.println("Пользователь с id:" + id + " не найден!");
					continue;
				} else {
					break;
				}
			}

			String sql3 = "SELECT * FROM users WHERE id = ?";
			PreparedStatement pstmt1 = con.prepareStatement(sql3);
			pstmt1.setInt(1, user.getId());
			ResultSet rs = pstmt1.executeQuery();
			while (rs.next()) {
				String str = "id:" + rs.getInt("id") + " " + "tabnum:" + rs.getInt("tabnum") + " "
						+ rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("date_of_birth");
				System.out.println(str);
			}

			while (true) {
				System.out.println("Подтвердите удаление пользователя (yes/no):");
				String response = scanner2.nextLine().trim();

				if (response.matches(NO_PATTERN)) {
					System.out.println("Отмена удаления пользователя!");
					break;

				} else if (response.matches(YES_PATTERN)) {
					String sql4 = "DELETE FROM users WHERE id = ?";
					PreparedStatement pstmt = con.prepareStatement(sql4);
					pstmt.setInt(1, user.getId());
					pstmt.executeUpdate();
					System.out.print("Удален пользователь c id:" + user.getId());
					break;

				} else {
					System.out.println("Введите yes или no!");
					continue;
				}
			}
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	void create(User user) {
//		try {
//			String sql1 = "INSERT INTO users(tabnum, name, surname, date_of_birth) VALUES(?, ?, ?, ?)";
//
//			PreparedStatement pstmt = con.prepareStatement(sql1);
//
//			pstmt.setInt(1, user.getTabnum());
//			pstmt.setString(2, user.getName());
//			pstmt.setString(3, user.getSurname());
//			pstmt.setDate(4, java.sql.Date.valueOf(user.getBirth()));
//
//			pstmt.executeUpdate();
//
//			System.out.println("Добавлен новый пользователь: " + user);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

//	void update(User user) {
//
//		try {
//			String sql2 = "UPDATE users SET tabnum = ?, name = ?, surname = ?, date_of_birth = ? WHERE id = ?";
//			PreparedStatement pstmt = con.prepareStatement(sql2);
//
//			pstmt.setInt(1, user.getTabnum());
//			pstmt.setString(2, user.getName());
//			pstmt.setString(3, user.getSurname());
//			pstmt.setDate(4, java.sql.Date.valueOf(user.getBirth()));
//			pstmt.setInt(5, user.getId());
//
//			pstmt.executeUpdate();
//
//			System.out.println("Обновлены данные пользователя: " + user);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
