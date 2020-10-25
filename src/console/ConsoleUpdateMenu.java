package console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

import standart.User;
import static standart.UserOperations.LOGIN;
import static standart.UserOperations.PASSWORD;
import static standart.UserOperations.URL;
import static standart.UserOperations.INT_PATTERN;
import static standart.UserOperations.NAME_PATTERN;
import static standart.UserOperations.isValidFormat;

public class ConsoleUpdateMenu {
	private Connection con;
	User user = new User();
	Scanner scanner = new Scanner(System.in);

	public ConsoleUpdateMenu() {
		try {
			Class.forName("org.postgresql.Driver");
			this.con = DriverManager.getConnection(URL, LOGIN, PASSWORD);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void updateWithConsole() {
		User user = new User();
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
}
