package console;

import java.util.Scanner;
import service.UserOperations;

public class ConsoleMainMenu extends Console {
	private static final UserOperations USER_OPERATIONS = new UserOperations();
	private static final ConsoleCreateMenu CONSOLE_CREATE_MENU = new ConsoleCreateMenu();
//	private static final ConsoleUpdateMenu CONSOLE_UPDATE_MENU = new ConsoleUpdateMenu();
//	private static final ConsoleDeleteMenu CONSOLE_DELETE_MENU = new ConsoleDeleteMenu();
	private static final String INT_PATTERN = "1|2|3|4";

	@Override
	public void operate() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println("Введите команду и нажмите Enter: ");
				System.out.println("1 - просмотр всех пользователей");
				System.out.println("2 - создание нового пользователя");
				System.out.println("3 - обновление данных пользователя");
				System.out.println("4 - удаление пользователя");
				System.out.print("Команда: ");

				String n = scanner.nextLine().trim();

				if (!n.matches(INT_PATTERN)) {
					System.out.println("Введите команду от 1 до 4!\n");
					continue;
				}

				switch (n) {
				case "1":
					USER_OPERATIONS.showUsersData();
					break;
				case "2":
					CONSOLE_CREATE_MENU.operate();
					break;
//				case "3":
//					CONSOLE_UPDATE_MENU.operate();
//					break;
//				case "4":
//					CONSOLE_DELETE_MENU.operate();
//					break;
				}

				System.out.print("\nВведите любую клавишу и нажмите Enter для завершения работы. Или нажмите Enter чтобы продолжить: ");
				if (!scanner.nextLine().matches("")) {
					System.out.println("Работа завершена.");
					break;
				}
			}
		}
	}
}
