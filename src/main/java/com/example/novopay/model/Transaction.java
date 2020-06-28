package com.example.novopay.model;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

public class Transaction {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	@JoinColumn(name="user_account_id")
	private UserAccount userAccount;
	@NotNull
	private Double amount;

	/** Purpose of Transaction */
	private String details;

	@Temporal(javax.persistence.TemporalType.DATE)
	private Date transactionDate;

	public Transaction(UserAccount userAccount, Double amount, String details, Date transactionDate) {
		this.userAccount = userAccount;
		this.amount = amount;
		this.details = details;
		this.transactionDate = transactionDate;
	}

	public Transaction(TransactionBuilder builder) {
		id = builder.id;
		userAccount = new UserAccount(builder.userAccountId);
		amount = builder.amount;
		details = builder.details;
		transactionDate = builder.transactionDate;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public static class TransactionBuilder {

		private Long id;
		private Long userAccountId;
		private Double amount;
		private String details;
		private Date transactionDate;

		public TransactionBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public TransactionBuilder setUserAccount(Long userAccountId) {
			this.userAccountId = userAccountId;
			return this;
		}

		public TransactionBuilder setAmount(Double amount) {
			this.amount = amount;
			return this;
		}

		public TransactionBuilder setDetails(String details) {
			this.details = details;
			return this;
		}

		public TransactionBuilder setTransactionDate(Date transactionDate) {
			this.transactionDate = transactionDate;
			return this;
		}

		public Transaction build() {
			return new Transaction(this);
		}

	}

}
