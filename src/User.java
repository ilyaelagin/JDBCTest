import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private int id;
	private int tabnum;
	private String name;
	private String surname;
	private String birth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTabnum() {
		return tabnum;
	}

	public void setTabnum(int tabnum) {
		this.tabnum = tabnum;
	}

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

	public User(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.tabnum = rs.getInt("tabnum");
		this.name = rs.getString("name");
		this.surname = rs.getString("surname");
		this.birth = rs.getString("date_of_birth");
	}

	public User(int id, int tabnum, String name, String surname, String birth) {
		this.id = id;
		this.tabnum = tabnum;
		this.name = name;
		this.surname = surname;
		this.birth = birth;
	}

	public User(int tabnum, String name, String surname, String birth) {
		this.tabnum = tabnum;
		this.name = name;
		this.surname = surname;
		this.birth = birth;
	}

	public User(int id) {
		this.id = id;
	}

	public User() {
	}

	@Override
	public String toString() {
		return "id:" + id + " " + "tabnum:" + tabnum + " " + name + " " + surname + " " + birth;
	}
}
