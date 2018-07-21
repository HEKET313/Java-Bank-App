package com.userFront.service.UserServiceImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userFront.DAO.AccountDao;
import com.userFront.DAO.PrimaryAccountDao;
import com.userFront.DAO.SavingsAccountDao;
import com.userFront.domain.Account;
import com.userFront.domain.PrimaryAccount;
import com.userFront.domain.SavingsAccount;
import com.userFront.service.AccountService;
import com.userFront.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static int nextAccountNumber = 11223145;

	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	@Autowired
	private UserService userService;
	
	@Override
	public PrimaryAccount createPrimaryAccount() {
		return (PrimaryAccount)createAccount(new PrimaryAccount(), primaryAccountDao);
	}

	@Override
	public SavingsAccount createSavingsAccount() {
		return (SavingsAccount)createAccount(new SavingsAccount(), savingsAccountDao);
	}
	
	private <T extends Account> Account createAccount(T newInstance, AccountDao<T, ?> accountDao) {
		newInstance.setAccountBalance(new BigDecimal(0.0));
		newInstance.setAccountNumber(accountGen());
		accountDao.save(newInstance);
		return accountDao.findByAccountNumber(newInstance.getAccountNumber());
	}
	
	private int accountGen() {
		return ++nextAccountNumber;
	}

}
