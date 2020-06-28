package com.example.novopay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novopay.exception.LowBalanceException;
import com.example.novopay.exception.NoUserFoundException;
import com.example.novopay.model.Transaction;
import com.example.novopay.model.UserAccount;
import com.example.novopay.repository.UserAccountRepository;
import com.example.novopay.service.TransactionService;

@RestController
@RequestMapping("/transferTo")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@SuppressWarnings("rawtypes")
	@PostMapping("/addMoney/{id}")
	public ResponseEntity addMoney(@PathVariable("id") Long userAccountId, @RequestBody Transaction transaction) {
		Transaction saved;
		try {
			UserAccount userAccount = userAccountRepository.findByid(userAccountId);
			transaction.setUserAccount(userAccount);
			saved = transactionService.createTransaction(transaction);
		} catch (LowBalanceException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Transaction>(saved, HttpStatus.CREATED);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{toUser}/from/{fromUser}")
	public ResponseEntity transferMoney(@PathVariable("toUser") Long toUserAccountId,
			@PathVariable("fromUser") Long fromUserAccountId, @RequestBody Transaction transaction) {
		List<Transaction> both;

		try {
			both = transactionService.transfer(transaction, toUserAccountId, fromUserAccountId);
		} catch (NoUserFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (LowBalanceException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Transaction>>(both, HttpStatus.OK);
	}

}
