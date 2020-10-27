package console;

import java.util.Scanner;

import dto.User;
import service.UserOperations;

public class ConsoleShowOneUser extends Console {

	private final UserOperations USER_OPERATIONS = new UserOperations();
	Scanner scanner = new Scanner(System.in);
	
	@Override
	protected void operate() {
		User user = new User();
		enterId(user);
		USER_OPERATIONS.showUser(user);
	}
	
	private String getIdConsoleInput() {
		while (true) {
			System.out.print("Просмотр пользователя. Введите id: ");
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
	
	private void enterId(User user) {
		String inputValue = getIdConsoleInput();
		user.setId(Integer.parseInt(inputValue));
	}

}
