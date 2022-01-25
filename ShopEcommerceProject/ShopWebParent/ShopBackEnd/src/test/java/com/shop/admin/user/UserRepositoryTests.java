package com.shop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shop.common.entity.Role;
import com.shop.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false) 
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userSangTD = new User("tadacsang@gmail.com", "sangvjp5", "Sang", "Ta Dac");
		userSangTD.addRole(roleAdmin);
		
		User savedUser = repo.save(userSangTD);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userThanhXuan = new User("thanhxuan2k2@gmail.com", "springboot", "Xuan", "Ta Thi Thanh");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userThanhXuan.addRole(roleEditor);
		userThanhXuan.addRole(roleAssistant);
		
		User savedUser = repo.save(userThanhXuan);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		Optional<User> userSangTD = repo.findById(1);
		System.out.println(userSangTD);
		assertThat(userSangTD).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userSangTD = repo.findById(1).get();
		userSangTD.setEnabled(true);
		userSangTD.setEmail("tadacsang98@gmail.com");
		
		repo.save(userSangTD);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userThanhXuan = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSaleperson = new Role(2);
		
		userThanhXuan.getRoles().remove(roleEditor);
		userThanhXuan.addRole(roleSaleperson);
		
		repo.save(userThanhXuan);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
	}
}
