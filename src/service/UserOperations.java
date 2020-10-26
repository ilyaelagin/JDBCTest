package service;

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

	public Integer getTabnumByTabnum(Integer tabnum) {
		return repository.getTabnumByTabnum(tabnum);
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
}
