package com.example.novopay.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novopay.exception.NoUserFoundException;
import com.example.novopay.model.Transaction;
import com.example.novopay.model.UserAccount;
import com.example.novopay.service.TransactionService;
import com.example.novopay.service.UserAccountService;

@RestController
@RequestMapping("/users")
public class UserAccountController {

	private static final Logger log = LoggerFactory.getLogger(UserAccountController.class);

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private TransactionService transactionService;

	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity getUsers() {
		List<UserAccount> userAccounts = userAccountService.getList();
		return new ResponseEntity<List<UserAccount>>(userAccounts, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}")
	public ResponseEntity getUser(@PathVariable("id") Long id) {
		UserAccount userAccount;
		try {
			userAccount = userAccountService.userAccountByPK(id);
		} catch (NoUserFoundException ex) {
			log.info("Exception occured while fetching users", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserAccount>(userAccount, HttpStatus.OK);

	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/createUser")
	public ResponseEntity createUser(@RequestBody UserAccount userAccount) {
		UserAccount saved;
		try {
			saved = userAccountService.save(userAccount);
		} catch (Exception ex) {
			log.info("Exception occured while creating users", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserAccount>(saved, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/update/{id}")
	public ResponseEntity updateUser(@PathVariable("id") Long userAccountId, @RequestBody UserAccount userAccount) {
		UserAccount saved;
		try {
			saved = userAccountService.update(userAccount, userAccountId);
		} catch (Exception ex) {
			log.info("Exception occured while updating users", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserAccount>(saved, HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 * @return list of transactions based on user id
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}/passbook")
	public ResponseEntity getUserPassbook(@PathVariable("id") Long id) {
		List<Transaction> passbook;
		try {
			passbook = transactionService.transactionsByUserAccountID(id);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Transaction>>(passbook, HttpStatus.OK);
	}
}
