
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

//		USER_OPERATIONS.showUsersData();

//		Users userCreate = new Users("Will", "Smith", "1968-09-25", 4);
//		USER_OPERATIONS.create(userCreate);

//		Users users2 = new Users();
//		operations4.createWithConsole(users2);

//		Users users3 = new Users();
//		operationsUp.update(users3);

//		Users users4 = new Users(4);
//		operationsDel.delete(users4);

	}

}
