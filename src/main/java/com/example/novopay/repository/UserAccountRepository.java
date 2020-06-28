package com.example.novopay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.novopay.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{

	Optional<UserAccount> getByUserName(String name);
	
	@Query("select u from UserAccount u where id=:id")
	UserAccount findByid(Long id);
}
