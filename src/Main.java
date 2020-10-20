import java.util.Scanner;

public class Main {
	public static final UserOperations USER_OPERATIONS = new UserOperations();

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Введите команду и нажмите Enter: ");
		System.out.println("1 - просмотр всех пользователей");
		System.out.println("2 - создание нового пользователя");
		System.out.println("3 - обновление данных пользователя");
		System.out.println("4 - удаление пользователя");

		int num = scanner.nextInt();

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
		scanner.close();
	}

}
