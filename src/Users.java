
public class Users {
	public static final UserOperations USER_OPERATIONS = new UserOperations();
	private String name;
	private String surname;
	private String birth;
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users(String name, String surname, String birth, int id) {
		super();
		this.name = name;
		this.surname = surname;
		this.birth = birth;
		this.id = id;
	}

	public Users(int id) {
		super();
		this.id = id;
	}

	public Users() {
		super();
	}

	public static void main(String[] args) {

		USER_OPERATIONS.showUsersData();

		Users createUser = new Users("Will", "Smith", "1968-10-26", 4);
		USER_OPERATIONS.create(createUser);

		Users createUserFromConsole = new Users();
		USER_OPERATIONS.createWithConsole(createUserFromConsole);

		Users updateUser = new Users("Will", "Smith", "1968-09-25", 4);
		USER_OPERATIONS.update(updateUser);

		Users deleteUser = new Users(5);
		USER_OPERATIONS.delete(deleteUser);
	}
}
