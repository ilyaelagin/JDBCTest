import java.sql.*;

public class UsersS2 {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/test1";
    private static final String DB_LOGIN = "postgres";
    private static final String DB_PASSWORD = "123";

    public static void main(String[] args) {
        new UsersS2().start();
    }

    public void start() {
        try (Connection con = getConnection()) {
            // Выполняем SQL-запрос в БД на чтение данных
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
            // Выводим прочитанные данные в консоль
            while (rs.next()) {
                System.out.print(rs.getString("id") + " ");
                System.out.print(rs.getString("name") + " ");
                System.out.print(rs.getString("date_of_birth") + " ");
            }

            // Формируем DTO (data transfer object) для последующего сохранения в БД
            CreateUsersS createUsers = new CreateUsersS("TestName", "TestDescription");
            createUsers.addUsersS(con);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    private Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
    }
}

class CreateUsersS {
    String name;
    String surname;

    public CreateUsersS(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    void addUsersS(Connection con) {
        try {
            String sql = "INSERT INTO users (name, surname) VALUES(" + "'" + name + "'" + "," + "'" + surname + "'" + ")";
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Не удалось сохранить данные: " + e);
        }
    }
}
