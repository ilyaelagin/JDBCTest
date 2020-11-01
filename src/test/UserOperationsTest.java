package test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import dto.User;
import repository.UserRepository;

public class UserOperationsTest {
	private static UserRepository repository;

	@BeforeClass
	public static void createNewUserRepository() {
		repository = new UserRepository();
	}

	@Test
	public void updateUserrr() {
		User userFromDb = new User();
		userFromDb.setTabnum(10);
		userFromDb.setName("Tom");
		userFromDb.setSurname("Ford");
		userFromDb.setBirth("1970-01-01");
		User user = new User();
		user.setTabnum(0);
		user.setName("");
		user.setSurname("");
		user.setBirth("");

		
		
		repository.update(userFromDb);
		User updateUser = repository.getUserById(user.getId());
	}

}
