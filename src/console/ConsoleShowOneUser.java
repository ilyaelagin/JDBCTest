package console;

import dto.User;

public class ConsoleShowOneUser extends Console {
	
	@Override
	protected void operate() {
		User user = new User();
		enterId(user);
		USER_OPERATIONS.showUser(user);
	}
	
	private void enterId(User user) {
		System.out.print("Просмотр пользователя. ");
		String inputValue = getIdConsoleInput();
		user.setId(Integer.parseInt(inputValue));
	}

}
