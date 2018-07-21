package com.userFront.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.userFront.domain.Account;

@NoRepositoryBean
public interface AccountDao<T extends Account, ID> extends CrudRepository<T, ID> {
	public T findByAccountNumber(int accountNumber);
}
