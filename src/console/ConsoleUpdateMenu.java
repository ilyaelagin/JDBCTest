package console;

import dto.User;

public class ConsoleUpdateMenu extends Console {

	@Override
	protected void operate() {
		User user = new User();
		User userFromDb = new User();
		enterId(user);
		USER_OPERATIONS.showUser(user);
		enterTabnum(user);
		enterName(user);
		enterSurname(user);
		enterBirth(user);
		USER_OPERATIONS.updateUser(user, userFromDb);
	}

	private String getTabnumConsoleInput() {
		while (true) {
			System.out.print("\nВведите новый tabnum (или нажмите Enter, чтобы оставить старое значение): ");
			String value = scanner.nextLine().trim();
			if ("".equals(value)) {
				System.out.println("Установлено старое значение.\n");
				return null;

			} else if (!value.matches(INT_PATTERN)) {
				System.out.println("Табельный номер должен быть числовой!");
				continue;
			}
			int tn = Integer.parseInt(value);
			if (tn <= 0) {
				System.out.println("tabnum должен быть больше нуля!");
				continue;
			}
			User userTabnum = USER_OPERATIONS.getUserByTabnum(tn);
			if (userTabnum != null) {
				System.out.println("Значение tabnum:" + userTabnum.getTabnum() + " уже есть в базе! Введите уникальное значение.");
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
				return null;
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
				return null;
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
				return null;
			} else if (!isValidFormat(value)) {
				System.out.println("Некорректный формат даты!");
				continue;
			}
			return value;
		}
	}

	private void enterId(User user) {
		System.out.print("Изменение данных пользователя. ");
		String inputValue = getIdConsoleInput();
		user.setId(Integer.parseInt(inputValue));
	}

	private void enterTabnum(User user) {
		String inputValue = getTabnumConsoleInput();
		if (inputValue != null) {
			user.setTabnum(Integer.parseInt(inputValue));
		}
	}

	private void enterName(User user) {
		String inputValue = getNameConsoleInput();
		if (inputValue != null) {
			user.setName(inputValue);
		}
	}

	private void enterSurname(User user) {
		String inputValue = getSurnameConsoleInput();
		if (inputValue != null) {
			user.setSurname(inputValue);
		}
	}

	private void enterBirth(User user) {
		String inputValue = getBirthConsoleInput();
		if (inputValue != null) {
			user.setBirth(inputValue);
		}
	}

}