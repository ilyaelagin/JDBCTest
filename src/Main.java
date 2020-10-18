
public class Main {
	public static final UserOperations USER_OPERATIONS = new UserOperations();
	
	public static void main(String[] args) {
		
		USER_OPERATIONS.showUsersData();
		
		USER_OPERATIONS.create(new User(70, "Will", "Smith", "1968-10-26"));

		USER_OPERATIONS.createWithConsole(new User());

		USER_OPERATIONS.update(new User(14, 50, "Will", "Smith", "1968-09-25"));

		USER_OPERATIONS.delete(new User(23));
		
		USER_OPERATIONS.updateWithConsole(new User());

	}

}
