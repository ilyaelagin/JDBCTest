package console;

import dto.User;

public class ConsoleDeleteMenu extends Console {
	
	@Override
	protected void operate() {
		User user = new User();
		enterId(user);
		USER_OPERATIONS.deleteUser(user);
	}
	
	private void enterId(User user) {
		System.out.print("Удаление пользователя. ");
		String inputValue = getIdConsoleInput();
		user.setId(Integer.parseInt(inputValue));
	}

}
