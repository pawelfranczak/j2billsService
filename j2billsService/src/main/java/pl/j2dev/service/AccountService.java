package pl.j2dev.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.j2dev.dao.jdbc.AccountDaoImpl;
import pl.j2dev.pojo.Account;

@Service
public class AccountService {
	
	@Autowired
	AccountDaoImpl dao;
	
	public List<Account> getListOfAllObjects() {
		return dao.getList();
	}
	
	public Account getObjectById(long id) {
		return dao.getById(id);
	}
	
	public Account createObject(Account object) {
		long id = dao.add(object);
		object.setId(id);
		return object;
	}
	
	public void actualizeBalance(long accountId, BigDecimal value) {
		dao.updateBalance(accountId, value);
	}
	
}
