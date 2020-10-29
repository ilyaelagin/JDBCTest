package service;

import java.util.Scanner;

import dto.User;
import repository.UserRepository;


/**
 * Это сервисный слой для работы с сущностью User
 * Сервисный - означает что тут присутствует бизнес логика
 * Только этот слой должен знать о такой штуке как UserRepository, ни один другой класс не должен иметь дело напрямую с базой.
 * Если какие-то классы хотят получить что-то из БД - они должны обратиться к UserOperations, а уже он в свою очередь запросит базу через UserRepository
 * showUsersData - тут мы запрашиваем у repository-слоя данные и выводим информацию о них пользователю
 * getTabnumByTabnum - в этом методе бизнес-логики нет, но следуя концепции вызываем repository-слой и получаем нужные данные для дальнейшего использования
 */
public class UserOperations {

	private final UserRepository repository = new UserRepository();

	public void showUsersData() {
		repository.getAll().forEach(System.out::println);
	}

	public Integer getTabnumByTabnum(int tabnum) {
		return repository.getTabnumByTabnum(tabnum);
	}
	
	public Integer getIdById(int id) {
		return repository.getIdById(id);
	}

	public void createUser(User user) {
		repository.create(user);
		User createdUser = repository.getUserByTabnum(user.getTabnum());
		if (createdUser == null) {
			System.out.println("\nЧто-то пошло не так и пользователя не удалось создать: " + user);
		} else {
			System.out.println("\nДобавлен новый пользователь: " + createdUser);
		}
	}
	public void updateUser(User user) {
		User userFromDb = repository.getUserById(user.getId());
		if(userFromDb == null) {
			System.out.println("Ошибка обновления данных.");
			return;
		}
		if(user.getTabnum() != 0) {
			userFromDb.setTabnum(user.getTabnum());
		}
		if(user.getName() != null) {
			userFromDb.setName(user.getName());
		}
		if(user.getSurname() != null) {
			userFromDb.setSurname(user.getSurname());
		}
		if(user.getBirth() != null) {
			userFromDb.setBirth(user.getBirth());
		}
		repository.update(userFromDb);
		User updateUser = repository.getUserById(user.getId());
		if (updateUser == null) {
			System.out.println("\nЧто-то пошло не так, данные пользователя не удалось обновить: " + user);
		} else {
			System.out.println("\nОбновлены данные пользователя: " + updateUser);
		}
	}
	public void showUser(User user) {
		repository.show(user);
		User showUser = repository.getUserById(user.getId());
		if (showUser == null) {
			System.out.println("\nЧто-то пошло не так и пользователя не удалось показать: " + user);
		} else {
			System.out.println("\nПользователь: " + showUser);
		}
	}
	public void deleteUser(User user) {
		User showUser = repository.getUserById(user.getId());
		System.out.println("\n" + showUser);
		while(true) {
 		System.out.println("\nПодтвердите удаление пользователя (yes/no): ");
		Scanner scanner = new Scanner(System.in);
			String response = scanner.nextLine().trim();
			if("yes".equals(response)) {
				repository.delete(user);
				User deleteUser = repository.getUserById(user.getId());
				if (deleteUser == null) {
					System.out.println("\nПользователь удален." );
					break;
				} else {
					System.out.println("\nЧто-то пошло не так и пользователя не удалось удалить.");
				}	
			} else if("no".equals(response)) {
				System.out.println("Отмена удаления пользователя.");
				break;
			} else {
				System.out.println("Ошибка. Введите yes или no!");
				continue;
			}
			scanner.close();
		}
	}
}
