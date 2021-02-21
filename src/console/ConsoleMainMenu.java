package console;

public class ConsoleMainMenu extends Console {
    private static final ConsoleCreateMenu CONSOLE_CREATE_MENU = new ConsoleCreateMenu();
    private static final ConsoleUpdateMenu CONSOLE_UPDATE_MENU = new ConsoleUpdateMenu();
    private static final ConsoleDeleteMenu CONSOLE_DELETE_MENU = new ConsoleDeleteMenu();
    private static final ConsoleShowOneUser CONSOLE_SHOW_USER = new ConsoleShowOneUser();
    private static final String INT_PATTERN = "[12345]";

    @Override
    public void operate() {
        while (true) {
            System.out.println("Введите команду и нажмите Enter: ");
            System.out.println("1 - просмотр всех пользователей");
            System.out.println("2 - просмотр одного пользователя");
            System.out.println("3 - создание нового пользователя");
            System.out.println("4 - изменение данных пользователя");
            System.out.println("5 - удаление пользователя");
            System.out.print("Команда: ");

            String n = scanner.nextLine().trim();

            if (!n.matches(INT_PATTERN)) {
                System.out.println("Введите команду от 1 до 5!\n");
                continue;
            }

            switch (n) {
                case "1" -> USER_OPERATIONS.showUsersData();
                case "2" -> CONSOLE_SHOW_USER.operate();
                case "3" -> CONSOLE_CREATE_MENU.operate();
                case "4" -> CONSOLE_UPDATE_MENU.operate();
                case "5" -> CONSOLE_DELETE_MENU.operate();
            }

            System.out.print("\nВведите любую клавишу и нажмите Enter для завершения работы. Или нажмите Enter чтобы продолжить: ");
            if (!scanner.nextLine().matches("")) {
                System.out.println("Работа завершена.");
                break;
            }
        }
        scanner.close();
    }
}
