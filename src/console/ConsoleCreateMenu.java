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
import static standart.UserOperations.URL;
import static standart.UserOperations.LOGIN;
import static standart.UserOperations.PASSWORD;
import static standart.UserOperations.INT_PATTERN;
import static standart.UserOperations.NAME_PATTERN;
import static standart.UserOperations.isValidFormat;

public class ConsoleCreateMenu {
	private Connection con;
	User user = new User();
	Scanner scanner = new Scanner(System.in);
	
	
	public ConsoleCreateMenu() {
		try {
			Class.forName("org.postgresql.Driver");
			this.con = DriverManager.getConnection(URL, LOGIN, PASSWORD);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void createWithConsole() {
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
			System.out.println("\nДобавлен новый пользователь: " + user);

		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
