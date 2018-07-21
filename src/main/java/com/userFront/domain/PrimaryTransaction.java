package com.userFront.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PrimaryTransaction extends Transaction {
	@ManyToOne
	@JoinColumn(name = "primary_account_id")
	private PrimaryAccount primaryAccount;
	
	public PrimaryTransaction(
		Date date, 
		String description, 
		String type, 
		String status, 
		double amount,
		BigDecimal availableBalance,
		PrimaryAccount primaryAccount
	) {
		super(date, description, type, status, amount, availableBalance);
		this.primaryAccount = primaryAccount;
	}

	public PrimaryAccount getPrimaryAccount() {
		return this.primaryAccount;
	}

	public void setPrimaryAccount(PrimaryAccount primaryAccount) {
		this.primaryAccount = primaryAccount;
	}
}
