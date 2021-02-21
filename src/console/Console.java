package console;

import dto.User;
import service.UserOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class Console {
	protected static final String NAME_PATTERN = "[а-яА-Яa-zA-z]+(\\s|-)?([а-яА-Яa-zA-z]+)?";
    protected static final String DATE_PATTERN = "yyyy-MM-dd";
    protected static final String INT_PATTERN = "-?\\d+";
    
    protected static final UserOperations USER_OPERATIONS = new UserOperations();
    
    protected static final Scanner scanner = new Scanner(System.in);

    protected static boolean isValidFormat(String value) {
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
    
    protected static String getIdConsoleInput() {
		while (true) {
			System.out.print("Введите id: ");
			String value = scanner.nextLine().trim();
			if (!value.matches(INT_PATTERN)) {
				System.out.println("id должен быть числовой!");
				continue;
			}
			int id = Integer.parseInt(value); 
			if (id < 0) {
				System.out.println("id должен быть больше нуля!");
				continue;
			}
			User userId = USER_OPERATIONS.getUserById(id);
			if (userId == null) {
				System.out.println("Пользователь с id:" + value + " не найден!");
				continue;
			}
			return value;
		}
	}

    /**
     * Всех наследников этого класса обязуем реализовывать метод operate()
     */
    protected abstract void operate();

}
