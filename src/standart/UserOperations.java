package standart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserOperations {
	public static final String URL = "jdbc:postgresql://localhost:5432/test1";
	public static final String LOGIN = "postgres";
	public static final String PASSWORD = "123";

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
			this.con = DriverManager.getConnection(URL, LOGIN, PASSWORD);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showUsersData() {
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
}
