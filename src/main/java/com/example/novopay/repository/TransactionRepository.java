package com.example.novopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.novopay.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("select ifnull(sum(amount),0.00) from Transaction where userAccount = :accountId")
	Double getBalance(Long accountId);

	@Query("select * from Transaction where userAccount = :accountId")
	List<Transaction> getTransactionsForUser(Long accountId);
}
