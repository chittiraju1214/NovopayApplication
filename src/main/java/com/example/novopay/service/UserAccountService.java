package com.example.novopay.service;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.novopay.exception.NoUserFoundException;
import com.example.novopay.model.UserAccount;
import com.example.novopay.repository.UserAccountRepository;

@Service
public class UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	public UserAccount userAccountByPK(Long userAccountId) throws NoUserFoundException {
		return userAccountRepository.findById(userAccountId).orElseThrow(
				() -> new NoUserFoundException(String.format("userAccount with '%d' not found ", userAccountId)));
	}

	/**
	 * this operations registers a user and creates and userAccount for him/her
	 * with minimal details
	 */
	@Transactional
	public UserAccount save(UserAccount userAccount) throws Exception {
		if (userAccount.getUserName() != null) {

			return userAccountRepository.save(userAccount);
		}
		throw new Exception("user name is mandatory");
	}

	/**
	 * this operation updates a users userAccount information and checks for
	 * concurrent user modification
	 */
	@Transactional
	public UserAccount update(UserAccount userAccount, Long userAccountId) throws Exception {
		if (userAccount.getUserName() != null) {
			userAccount.setId(userAccountId);
			try {
				return userAccountRepository.save(userAccount);
			} catch (Exception e) {
				throw new Exception("Try again");
			}
		}
		throw new Exception("user name is mandatory");

	}

	/**
	 * this operation gets all userAccount lists and their respective
	 * transaction transactions
	 */
	public List<UserAccount> getList() {
		return Lists.newArrayList(userAccountRepository.findAll());
	}

}
