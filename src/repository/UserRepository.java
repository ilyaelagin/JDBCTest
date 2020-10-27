package repository;

import dto.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Это слой для работы с БД
 * В этом слое нет никакой бизнес-логики - это сугубо технический слой
 * Тут должны быть методы создать, изменить, удалить, получить данные и т.д.
 */
public class UserRepository {

    private static final String GET_ALL_USERS_SQL = "SELECT id, tabnum, name, surname, date_of_birth FROM users ORDER BY id;";
   
    private static final String GET_ONE_USER_SQL = "SELECT id, tabnum, name, surname, date_of_birth FROM users WHERE id = ? ;";
   
    private static final String GET_TABNUM_BY_TABNUM_SQL = "SELECT tabnum FROM users WHERE tabnum = ";
    private static final String GET_USER_BY_TABNUM_SQL = "SELECT id, tabnum, name, surname, date_of_birth FROM users WHERE tabnum = ";
    private static final String INSERT_USER_SQL = "INSERT INTO users(tabnum, name, surname, date_of_birth) VALUES(?, ?, ?, ?)";
    
    private static final String GET_ID_BY_ID_SQL = "SELECT id FROM users WHERE id = ";
    private static final String GET_USER_BY_ID_SQL = "SELECT id, tabnum, name, surname, date_of_birth FROM users WHERE id = ";
    private static final String UPDATE_USER_SQL = "UPDATE users SET tabnum = ?, name = ?, surname = ?, date_of_birth = ? WHERE id = ?;";

    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?;";

    private static final String URL = "jdbc:postgresql://localhost:5432/test1";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "123";

    private Connection con;

    public UserRepository() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try {
            ResultSet rs = con.createStatement().executeQuery(GET_ALL_USERS_SQL);
            while (rs.next()) {
                userList.add(new User(rs));
            }
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
            return userList;
        }
    }
    public List<User> getOne() {
    	List<User> userOne = new ArrayList<>();
    	try {
    		ResultSet rs = con.createStatement().executeQuery(GET_ONE_USER_SQL);
    		while (rs.next()) {
    			userOne.add(new User(rs));
    		}
    		return userOne;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return userOne;
    	}
    }

    public Integer getTabnumByTabnum(int tabnum) {
        try {
            ResultSet rs = con.createStatement().executeQuery(GET_TABNUM_BY_TABNUM_SQL + tabnum + ";");
            return rs.next() ? rs.getInt("tabnum") : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByTabnum(int tabnum) {
        try {
            ResultSet rs = con.createStatement().executeQuery(GET_USER_BY_TABNUM_SQL + tabnum + ";");
            return rs.next() ? new User(rs) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public Integer getIdById(int id) {
        try {
            ResultSet rs = con.createStatement().executeQuery(GET_ID_BY_ID_SQL + id + ";");
            return rs.next() ? rs.getInt("id") : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(int id) {
        try {
            ResultSet rs = con.createStatement().executeQuery(GET_USER_BY_ID_SQL + id + ";");
            return rs.next() ? new User(rs) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Integer getIdByIdOneUser(int id) {
    	try {
    		ResultSet rs = con.createStatement().executeQuery(GET_ID_BY_ID_SQL + id + ";");
    		return rs.next() ? rs.getInt("id") : null;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public User getUserByIdOneUser(int id) {
    	try {
    		ResultSet rs = con.createStatement().executeQuery(GET_USER_BY_ID_SQL + id + ";");
    		return rs.next() ? new User(rs) : null;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public void create(User user) {
        try {
            PreparedStatement pstmt = con.prepareStatement(INSERT_USER_SQL);
            pstmt.setInt(1, user.getTabnum());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getSurname());
            pstmt.setDate(4, java.sql.Date.valueOf(user.getBirth()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не удалось создать пользователя: " + user);
        }
    }
    public void update(User user) {
    	try {
    		PreparedStatement pstmt = con.prepareStatement(UPDATE_USER_SQL);
    		pstmt.setInt(1, user.getTabnum());
    		pstmt.setString(2, user.getName());
    		pstmt.setString(3, user.getSurname());
    		pstmt.setDate(4, java.sql.Date.valueOf(user.getBirth()));
    		pstmt.setInt(5, user.getId());
    		pstmt.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("Не удалось обновить пользователя: " + user);
    	}
    }
    public void show(User user) {
    	try {
    		PreparedStatement pstmt = con.prepareStatement(GET_ONE_USER_SQL);
    		pstmt.setInt(1, user.getId());
    		pstmt.executeQuery();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("Не удалось получить пользователя: " + user);
    	}
    }
    public void delete(User user) {
    	try {
    		PreparedStatement pstmt = con.prepareStatement(DELETE_USER_SQL);
    		pstmt.setInt(1, user.getId());
    		pstmt.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("Не удалось получить пользователя: " + user);
    	}
    }
}
