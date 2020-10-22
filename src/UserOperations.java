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

	Scanner scanner = new Scanner(System.in);

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

		try {
			while (true) {
				System.out.print("Введите tabnum(табельный номер): ");
				String tn = scanner.nextLine().trim();
				if (!tn.matches(INT_PATTERN)) {
					System.out.println("Табельный номер должен быть числовой!");
					continue;
				}
				int tabnum = Integer.parseInt(tn);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT tabnum FROM users WHERE tabnum = " + tabnum + ";");
				if (rs.next()) {
					System.out.println("Значение tabnum:" + tabnum + " уже есть в базе! Введите уникальное значение.");
					continue;
				} else if (tabnum > 0) {
					user.setTabnum(tabnum);
					break;
				} else {
					System.out.println("tabnum должен быть больше нуля!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите имя: ");
				String name = scanner.nextLine().trim();
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
				String surname = scanner.nextLine().trim();
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
				String birth = scanner.nextLine().trim();
				if (isValidFormat(birth)) {
					user.setBirth(birth);
					break;
				} else {
					continue;
				}
			}

			String sql = "INSERT INTO users(tabnum, name, surname, date_of_birth) VALUES(?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(sql);
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
		try {

			while (true) {
				System.out.print("Изменение данных пользователя. Введите id: ");
				String i = scanner.nextLine().trim();
				if (!i.matches(INT_PATTERN)) {
					System.out.println("id должен быть числовой!");
					continue;
				}
				int id = Integer.parseInt(i);
				if (id > 0) {
					user.setId(id);
				} else {
					System.out.println("id должен быть больше нуля!");
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

			String sql1 = "SELECT * FROM users WHERE id = ?;";
			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setInt(1, user.getId());
			ResultSet rs1 = pstmt1.executeQuery();

			if (rs1.next()) {
				String str = "id:" + rs1.getInt("id") + " " + "tabnum:" + rs1.getInt("tabnum") + " "
						+ rs1.getString("name") + " " + rs1.getString("surname") + " " + rs1.getString("date_of_birth");
				System.out.println(str);
			} else {
				System.out.println("Пользователь с id:" + user.getId() + " не найден.");
			}

			User userDB = new User(rs1);

			while (true) {
				System.out.print("\nВведите новый tabnum (или нажмите Enter, чтобы оставить старое значение): ");
				String tn = scanner.nextLine().trim();
				if ("".equals(tn)) {
					System.out.println("Установлено старое значение.\n");
					break;
				} else if (!tn.matches(INT_PATTERN)) {
					System.out.println("Табельный номер должен быть числовой!");
					continue;
				} else if (Integer.parseInt(tn) < 0) {
					System.out.println("tabnum должен быть больше нуля!");
					continue;
				}

				int tabnum = Integer.parseInt(tn);
				Statement stmt = con.createStatement();
				ResultSet rs2 = stmt.executeQuery("SELECT tabnum FROM users WHERE tabnum = " + tabnum + ";");
				if (rs2.next()) {
					System.out.println("Значение tabnum:" + tabnum
							+ " уже есть в базе! Введите уникальное значение. Или нажмите Enter, чтобы оставить старое значение.");
					continue;
				} else {
					userDB.setTabnum(tabnum);
					break;
				}
			}

			while (true) {
				System.out.print("Введите имя (или нажмите Enter, чтобы оставить старое значение): ");
				String name = scanner.nextLine().trim();
				if (name.matches(NAME_PATTERN)) {
					userDB.setName(name);
					break;
				} else if ("".equals(name)) {
					System.out.println("Установлено старое значение.\n");
					break;
				} else {
					System.out.println("Недопустимые символы в имени!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите фамилию (или нажмите Enter, чтобы оставить старое значение): ");
				String surname = scanner.nextLine().trim();
				if (surname.matches(NAME_PATTERN)) {
					userDB.setSurname(surname);
					break;
				} else if ("".equals(surname)) {
					System.out.println("Установлено старое значение.\n");
					break;
				} else {
					System.out.println("Недопустимые символы в фамилии!");
					continue;
				}
			}

			while (true) {
				System.out.print("Введите дату рождения (или нажмите Enter, чтобы оставить старое значение): ");
				String birth = scanner.nextLine().trim();
				if ("".equals(birth)) {
					System.out.println("Установлено старое значение.\n");
					break;
				} else if (isValidFormat(birth)) {
					userDB.setBirth(birth);
					break;
				} else {
					continue;
				}
			}

			String sql2 = "UPDATE users SET tabnum = ?, name = ?, surname = ?, date_of_birth = ? WHERE id ="
					+ rs1.getInt("id") + ";";

			PreparedStatement pstmt2 = con.prepareStatement(sql2);
			pstmt2.setInt(1, userDB.getTabnum());
			pstmt2.setString(2, userDB.getName());
			pstmt2.setString(3, userDB.getSurname());
			pstmt2.setDate(4, java.sql.Date.valueOf(userDB.getBirth()));
			pstmt2.executeUpdate();

			System.out.println("\nОбновлены данные пользователя: \n" + userDB);

		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void delete(User user) {
		try {
			while (true) {
				System.out.print("Удаление пользователя. Введите id: ");
				String i = scanner.nextLine().trim();
				if (!i.matches(INT_PATTERN)) {
					System.out.println("id должен быть числовой!");
					continue;
				}
				int id = Integer.parseInt(i);
				if (id > 0) {
					user.setId(id);
				} else {
					System.out.println("id должен быть больше нуля!");
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

			String sql1 = "SELECT * FROM users WHERE id = ?";
			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setInt(1, user.getId());
			ResultSet rs = pstmt1.executeQuery();
			while (rs.next()) {
				String str = "id:" + rs.getInt("id") + " " + "tabnum:" + rs.getInt("tabnum") + " "
						+ rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("date_of_birth");
				System.out.println(str);
			}

			while (true) {
				System.out.print("\nПодтвердите удаление пользователя (yes/no): ");
				String response = scanner.nextLine();

				if (response.matches(NO_PATTERN)) {
					System.out.println("Отмена удаления пользователя!");
					break;

				} else if (response.matches(YES_PATTERN)) {
					String sql2 = "DELETE FROM users WHERE id = ?";
					PreparedStatement pstmt2 = con.prepareStatement(sql2);
					pstmt2.setInt(1, user.getId());
					pstmt2.executeUpdate();
					System.out.println("Удален пользователь c id:" + user.getId());
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
}
