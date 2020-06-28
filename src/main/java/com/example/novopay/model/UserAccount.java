package com.example.novopay.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.transaction.Transaction;
import javax.validation.constraints.NotNull;

@Entity
public class UserAccount {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String userName;
	private String email;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateCreated;
	@OneToMany(mappedBy = "userAccount", fetch = FetchType.EAGER)
	private Set<Transaction> transactions = new HashSet<>();

	public UserAccount(Long id) {
		this.id = id;

	}

	public UserAccount(Long id, String userName, String email, Date dateCreated) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.dateCreated = dateCreated;
	}

	public UserAccount(UserAccountBuilder builder) {
		id = builder.id;
		userName = builder.userName;
		email = builder.email;
		dateCreated = builder.dateCreated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public static class UserAccountBuilder {

		private Long id;
		private String userName;
		private String email;
		private Date dateCreated;

		public Long getId() {
			return id;
		}

		public UserAccountBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public UserAccountBuilder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public UserAccountBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserAccountBuilder setDateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
			return this;
		}

		public UserAccount build() {
			return new UserAccount(this);
		}

	}
}
