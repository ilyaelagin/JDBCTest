package console;

import java.util.Scanner;

import dto.User;
import service.UserOperations;

public class ConsoleUpdateMenu extends Console {

	private final UserOperations USER_OPERATIONS = new UserOperations();
	Scanner scanner = new Scanner(System.in);

	@Override
	protected void operate() {
		User user = new User();
		enterId(user);
		USER_OPERATIONS.showUser(user);
		enterTabnum(user);
		enterName(user);
		enterSurname(user);
		enterBirth(user);
		USER_OPERATIONS.updateUser(user);
	}

	private String getIdConsoleInput() {
		while (true) {
			System.out.print("Изменение данных пользователя. Введите id: ");
			String value = scanner.nextLine().trim();
			if (!value.matches(INT_PATTERN)) {
				System.out.println("id должен быть числовой!");
				continue;
			}
			Integer id = USER_OPERATIONS.getIdById(Integer.parseInt(value));
			if (id == null) {
				System.out.println("Пользователь с id:" + value + " не найден!");
				continue;
			}
			return value;
		}
	}
	
	
	private String getTabnumConsoleInput() {
		while (true) {
			System.out.print("\nВведите новый tabnum (или нажмите Enter, чтобы оставить старое значение): ");
			String value = scanner.nextLine().trim();
			if ("".equals(value)) {
				System.out.println("Установлено старое значение.\n");
//				User user = new User();
//				System.out.println(eId(user));
				
				
				return "11111111111";
				
			} else if (!value.matches(INT_PATTERN)) {
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

	private String getNameConsoleInput() {
		while (true) {
			System.out.print("Введите имя (или нажмите Enter, чтобы оставить старое значение): ");
			String value = scanner.nextLine().trim();
			if ("".equals(value)) {
				System.out.println("Установлено старое значение.\n");
				return "111111111";
			} else if (!value.matches(NAME_PATTERN)) {
				System.out.println("Недопустимые символы в имени!");
				continue;
			}
			return value;
		}
	}

	private String getSurnameConsoleInput() {
		while (true) {
			System.out.print("Введите фамилию (или нажмите Enter, чтобы оставить старое значение): ");
			String value = scanner.nextLine().trim();
			if ("".equals(value)) {
				System.out.println("Установлено старое значение.\n");
				return "111111111";
			} else if (!value.matches(NAME_PATTERN)) {
				System.out.println("Недопустимые символы в фамилии!");
				continue;
			}
			return value;
		}
	}

	private String getBirthConsoleInput() {
		while (true) {
			System.out.print("Введите дату рождения (или нажмите Enter, чтобы оставить старое значение): ");
			String value = scanner.nextLine().trim();
			if ("".equals(value)) {
				System.out.println("Установлено старое значение.\n");
				return "1999-07-03";
			} else if (!isValidFormat(value)) {
				System.out.println("Некорректный формат даты!");
				continue;
			}
			return value;
		}
	}

	private void enterId(User user) {
		String inputValue = getIdConsoleInput();
		user.setId(Integer.parseInt(inputValue));
	}
//	private  void eId() {
//		System.out.println(user.getId());
//		User userDB = new User();
//		userDB.getTabnum();
//	}

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