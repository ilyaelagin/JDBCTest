package dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;

public class User {
	@Getter @Setter private int id;
	@Getter @Setter private int tabnum;
	@Getter @Setter private String name;
	@Getter @Setter private String surname;
	@Getter @Setter private String birth;
	
	public User() {
		
	}

	public User(ResultSet rs) throws SQLException {
		if (rs != null) {
			this.id = rs.getInt("id");
			this.tabnum = rs.getInt("tabnum");
			this.name = rs.getString("name");
			this.surname = rs.getString("surname");
			this.birth = rs.getString("date_of_birth");
		}
	}

	@Override
	public String toString() {
		return "id:" + id + " " + "tabnum:" + tabnum + " " + name + " " + surname + " " + birth;
	}
}
