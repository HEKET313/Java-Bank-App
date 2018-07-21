package com.userFront.service.UserServiceImpl;

import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.userFront.DAO.RoleDao;
import com.userFront.DAO.UserDao;
import com.userFront.domain.User;
import com.userFront.domain.security.UserRole;
import com.userFront.service.AccountService;
import com.userFront.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
 	private UserDao userDao;
 	@Autowired
 	private RoleDao roleDao;
 	@Autowired
 	private BCryptPasswordEncoder passwordEncoder;
 	@Autowired
 	private AccountService accountService;
 	
 	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
 	
 	public void save(User user) {
 		userDao.save(user);
 	}
 	
 	public User findByUsername(String username) {
 		return userDao.findByUsername(username);
 	}
 	
 	public User findByEmail(String email) {
 		return userDao.findByEmail(email);
 	}
 	
 	public boolean checkUserExists(String username, String email) {
 		return checkUsernameExists(username) || checkEmailExists(email);
 	}
 	
 	public boolean checkUsernameExists(String username) {
 		return null != findByUsername(username);
 	}
 	
 	public boolean checkEmailExists(String email) {
 		return null != findByEmail(email);
 	}
 	
 	@Transactional
 	public User create(User user, Set<UserRole> userRoles) {
 		User localUser = userDao.findByUsername(user.getUsername());
 		if (localUser != null) {
 			LOG.info("User with username {} already exist. Nothing will be done.", user.getUsername());
 			return localUser;
 		}
 		
 		String encryptedPassword = passwordEncoder.encode(user.getPassword());
 		user.setPassword(encryptedPassword);
 		for (UserRole ur: userRoles) {
 			roleDao.save(ur.getRole());
 		}
 		user.getUserRoles().addAll(userRoles);
 		user.setPrimaryAccount(accountService.createPrimaryAccount());
 		user.setSavingsAccount(accountService.createSavingsAccount());
 		return userDao.save(user);
 	}
}
