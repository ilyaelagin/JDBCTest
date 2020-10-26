//package console;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.InputMismatchException;
//import dto.User;
//import service.UserOperations;
//
//public class ConsoleDeleteMenu extends Console {
//
//	private final UserOperations USER_OPERATIONS = new UserOperations();
//
//	@Override
//	protected void operate() {
//		// TODO сделать аналогично тому как сделано в ConsoleCreateMenu
//	}
//
//	void delete() {
//		User user = new User();
//		try {
//			while (true) {
//				System.out.print("Удаление пользователя. Введите id: ");
//				String i = scanner.nextLine().trim();
//				if (!i.matches(INT_PATTERN)) {
//					System.out.println("id должен быть числовой!");
//					continue;
//				}
//				int id = Integer.parseInt(i);
//				if (id > 0) {
//					user.setId(id);
//				} else {
//					System.out.println("id должен быть больше нуля!");
//					continue;
//				}
//				Statement stmt = con.createStatement();
//				ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE id = " + id + ";");
//
//				if (!rs.next()) {
//					System.out.println("Пользователь с id:" + id + " не найден!");
//					continue;
//				} else {
//					break;
//				}
//			}
//
//			String sql1 = "SELECT * FROM users WHERE id = ?";
//			PreparedStatement pstmt1 = con.prepareStatement(sql1);
//			pstmt1.setInt(1, user.getId());
//			ResultSet rs = pstmt1.executeQuery();
//			while (rs.next()) {
//				String str = "id:" + rs.getInt("id") + " " + "tabnum:" + rs.getInt("tabnum") + " "
//						+ rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("date_of_birth");
//				System.out.println(str);
//			}
//
//			while (true) {
//				System.out.print("\nПодтвердите удаление пользователя (yes/no): ");
//				String response = scanner.nextLine();
//
//				if (response.matches(NO_PATTERN)) {
//					System.out.println("Отмена удаления пользователя!");
//					break;
//
//				} else if (response.matches(YES_PATTERN)) {
//					String sql2 = "DELETE FROM users WHERE id = ?";
//					PreparedStatement pstmt2 = con.prepareStatement(sql2);
//					pstmt2.setInt(1, user.getId());
//					pstmt2.executeUpdate();
//					System.out.println("Удален пользователь c id:" + user.getId());
//					break;
//
//				} else {
//					System.out.println("Введите yes или no!");
//					continue;
//				}
//			}
//
//		} catch (InputMismatchException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
