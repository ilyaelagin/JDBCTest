package console;

import dto.User;
import service.UserOperations;
import java.util.Scanner;

public class ConsoleCreateMenu extends Console {

	private final UserOperations USER_OPERATIONS = new UserOperations();

	@Override
	public void operate() {
		User user = new User();
		enterTabnum(user);
		enterName(user);
		enterSurname(user);
		enterBirth(user);
		USER_OPERATIONS.createUser(user);
	}

	private String getTabnumConsoleInput() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("Введите tabnum(табельный номер): ");
				String value = scanner.nextLine().trim();
				if (!value.matches(INT_PATTERN)) {
					System.out.println("Табельный номер должен быть числовой!");
					continue;
				}
				Integer tabnum = USER_OPERATIONS.getTabnumByTabnum(Integer.parseInt(value));
				if (tabnum != null) {
					System.out.println("Значение tabnum: " + tabnum + " уже есть в базе! Введите уникальное значение.");
					continue;
				}
				return value;
			}
		}
	}

	private String getNameConsoleInput() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("Введите имя: ");
				String value = scanner.nextLine().trim();
				if (!value.matches(NAME_PATTERN)) {
					System.out.println("Недопустимые символы в имени!");
					continue;
				}
				return value;
			}
		}
	}

	private String getSurnameConsoleInput() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("Введите фамилию: ");
				String value = scanner.nextLine().trim();
				if (!value.matches(NAME_PATTERN)) {
					System.out.println("Недопустимые символы в фамилии!");
					continue;
				}
				return value;
			}
		}
	}

	private String getBirthConsoleInput() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("Введите дату рождения: ");
				String value = scanner.nextLine().trim();
				if (!isValidFormat(value)) {
					System.out.println("Некорректный формат даты!");
					continue;
				}
				return value;
			}
		}
	}

	private void enterTabnum(User user) {
		String inputValue = getTabnumConsoleInput();
		user.setTabnum(Integer.parseInt(inputValue));
	}

	private void enterName(User user) {
		String inputValue = getNameConsoleInput();
		user.setName(inputValue);
	}

	private void enterSurname(User user) {
		String inputValue = getSurnameConsoleInput();
		user.setSurname(inputValue);
	}

	private void enterBirth(User user) {
		String inputValue = getBirthConsoleInput();
		user.setBirth(inputValue);
	}
}
