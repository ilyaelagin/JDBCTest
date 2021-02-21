package test;

import dto.User;
import org.junit.BeforeClass;
import org.junit.Test;
import service.UserOperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserOperationsTest {
    private static UserOperations operations;

    @BeforeClass
    public static void createNewUserRepository() {
        operations = new UserOperations();
    }

    @Test
    public void userShouldGetUpdate() {
        User userFromDb = new User();
        userFromDb.setId(1);
        userFromDb.setTabnum(100);
        userFromDb.setName("Tom");
        userFromDb.setSurname("Hancks");
        userFromDb.setBirth("1962-06-26");
        User user = new User();
        user.setTabnum(10);
        user.setName("Thomas");
        User userForUpdate = operations.prepareUserForUpdate(user, userFromDb);
        assertNotNull(userForUpdate);
        assertEquals(userForUpdate.getTabnum(), 10);
        assertEquals(userForUpdate.getName(), "Thomas");
        assertEquals(userForUpdate.getSurname(), "Hancks");
        assertEquals(userForUpdate.getBirth(), "1962-06-26");
    }
}
