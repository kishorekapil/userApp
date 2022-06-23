package com.infosys.api;

import com.infosys.api.entity.User;
import com.infosys.api.exception.UserNotFoundException;
import com.infosys.api.repository.UserRepository;
import com.infosys.api.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
	@Autowired
	private UserService service;
	@MockBean
	private UserRepository repository;

	@Test
	public void getUsersTest() {
		when(repository.findAll()).thenReturn(Stream
				.of(new User(376, "Danile", 31, "USA","5.7","67","unknown"), new User(377, "ADB", 38, "USA","6.5","78","unknown")).collect(Collectors.toList()));
		assertEquals(2, service.getALlUsers().size());
	}

	@Test
	public void saveUserTest() {
		User user = new User(0, "Danile", 31, "USA","5.7","67","unknown");
		when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.saveUser(user));
	}

	@Test
	public void saveNegativeUserTest() {
		User user = new User(0, "Danile", 31, "USA","5.7","67","unknown");
		when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.saveUser(user));
	}

	@Test
	public void getUserTest()throws UserNotFoundException {
		User user = new User(1, "Danile", 31, "USA","5.7","67","unknown");
		when(repository.findByUserId(1)).thenReturn(user);
		assertEquals(user, service.getUser(1));
	}

	@Test(expected = UserNotFoundException.class)
	public void getNegativeUserTest()throws UserNotFoundException {
		User user = new User(1, "Danile", 31, "USA","5.7","67","unknown");
		when(repository.findByUserId(1)).thenReturn(user);
		service.getUser(2);
	}

	@Test
	public void updateUserTest()throws UserNotFoundException {
		User user1 = new User(2, "kapil", 31, "USA","5.7","67","unknown");
		User user = new User(1, "Danile", 31, "USA","5.7","67","unknown");
		when(repository.findByUserId(1)).thenReturn(user);
		when(repository.save(user)).thenReturn(user1);
		assertNotEquals(user, service.updateUser(user1,1));

	}

}
