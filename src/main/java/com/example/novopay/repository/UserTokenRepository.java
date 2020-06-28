package com.example.novopay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.novopay.model.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

	Optional<UserToken> findByToken(String jwtToken);

	void deleteByToken(String token);

	@Query("from UserToken as ut where ut.tokenType = :tokenTypeId" + " and ut.user.id=:userId")
	List<UserToken> findAllByUserIdAndTokenType(long userId, int tokenTypeId);
}
