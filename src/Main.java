import java.util.Scanner;

public class Main {
	public static final UserOperations USER_OPERATIONS = new UserOperations();
	public static final String INT_PATTERN = "1|2|3|4";

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Введите команду и нажмите Enter: ");
			System.out.println("1 - просмотр всех пользователей");
			System.out.println("2 - создание нового пользователя");
			System.out.println("3 - обновление данных пользователя");
			System.out.println("4 - удаление пользователя");
			System.out.print("Команда: ");

			String n = scanner.nextLine().trim();

			if (n.matches(INT_PATTERN)) {
				int num = Integer.parseInt(n);

				switch (num) {
				case 1:
					USER_OPERATIONS.showUsersData();
					break;
				case 2:
					USER_OPERATIONS.createWithConsole(new User());
					break;
				case 3:
					USER_OPERATIONS.updateWithConsole(new User());
					break;
				case 4:
					USER_OPERATIONS.delete(new User());
					break;
				default:
					System.out.println("Такой команды не существует.");
					break;
				}

				System.out.print("\nВведите любую клавишу и нажмите Enter для завершения работы. Или нажмите Enter чтобы продолжить: ");
				String question = scanner.nextLine();
				if (question.matches("")) {
					continue;
				} else {
					break;
				}

			} else {
				System.out.println("Введите команду от 1 до 4!\n");
				continue;
			}
		}
		scanner.close();
	}
}
