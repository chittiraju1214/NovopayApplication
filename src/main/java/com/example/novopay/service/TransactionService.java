package com.example.novopay.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.novopay.exception.LowBalanceException;
import com.example.novopay.exception.NoUserFoundException;
import com.example.novopay.model.Transaction;
import com.example.novopay.model.UserAccount;
import com.example.novopay.repository.TransactionRepository;
import com.example.novopay.repository.UserAccountRepository;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserAccountRepository userAccountRepository;

	/**
	 * this operations registers a transaction on behalf of user debit/credits,
	 * it also validates if a user has insufficient funds if a debit is to be
	 * made
	 */
	@Transactional(rollbackFor = RuntimeException.class)
	public Transaction createTransaction(Transaction transaction) throws LowBalanceException {

		Double balance = transactionRepository.getBalance(transaction.getUserAccount().getId());

		if (balance + (transaction.getAmount()) >= 0) {
			return transactionRepository.save(transaction);
		}

		throw new LowBalanceException("user balance is: " + balance.doubleValue()
				+ "and cannot perform a transaction of: " + transaction.getAmount().doubleValue());

	}

	/**
	 * this operations transfers the amount from one account to another account
	 */

	@Transactional(rollbackFor = RuntimeException.class)
	public List<Transaction> transfer(Transaction transaction, Long toUserAccountId, Long fromUserAccountId)
			throws NoUserFoundException, LowBalanceException {
		List<Transaction> transactions = new ArrayList<>();

		Transaction sourceUserTransaction;
		Transaction destinationUserTransaction;

		UserAccount fromUserAccount = userAccountRepository.findByid(fromUserAccountId);
		if (fromUserAccount == null)
			throw new NoUserFoundException("userAccount with '%d' not found " + fromUserAccountId);
		UserAccount toUserAccount = userAccountRepository.findByid(toUserAccountId);
		if (toUserAccount == null) {
			throw new NoUserFoundException("userAccount with '%d' not found " + toUserAccountId);
		}

		Double amount = transaction.getAmount();
		Double finalAmount = amount + (0.2 * amount) + (0.05 * amount);

		transaction.setUserAccount(fromUserAccount);
		transaction.setAmount(finalAmount);
		sourceUserTransaction = createTransaction(transaction);
		transactions.add(sourceUserTransaction);

		transaction.setUserAccount(toUserAccount);
		transaction.setAmount(transaction.getAmount());
		destinationUserTransaction = createTransaction(transaction);
		transactions.add(destinationUserTransaction);

		return transactions;
	}

	/**
	 * this operation lists all the transactions by user account id
	 * 
	 * 
	 */
	public List<Transaction> transactionsByUserAccountID(Long accountId) {
		return transactionRepository.getTransactionsForUser(accountId);
	}

}
