package com.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.app.entities.Role;
import com.app.entities.User;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class UserEntityRepositoryTest {

	// dep
		@Autowired
		private UserRepository userRepo;

		@Autowired
		private PasswordEncoder enc;

		@Test
	void testAddUsers() {
			List<User> list = List.of(
					new User("Rushi", "rushi@gmail.com", "9764800356", Role.ROLE_ADMIN, enc.encode("12345")),
					new User("Harish", "harish@gmail.com", "9764800356", Role.ROLE_EMPLOYEE, enc.encode("2345")),
			        new User("Ajay", "ajay@gmail.com", "9764800356", Role.ROLE_USER, enc.encode("1345")));
			List<User> list2 = userRepo.saveAll(list);
			assertEquals(3, list2.size());

		}

}
