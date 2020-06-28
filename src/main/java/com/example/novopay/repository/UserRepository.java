package com.example.novopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.novopay.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUserEmail(String userEmail);

	Users findByUserId(long id);

	@Query("select u.userEmail from Users u")
	List<String> findAllUserEmail();

	@Query("from Users u where u.createdBy=:sampleUserId")
	List<Users> getAllRegisteredUsers(long sampleUserId);
}
