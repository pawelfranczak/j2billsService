package pl.j2dev.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.j2dev.dao.jdbc.JournalDaoImpl;
import pl.j2dev.pojo.Journal;
import pl.j2dev.pojo.JournalMovement;

@Service
public class JournalService {

	@Autowired
	JournalDaoImpl dao;
	
	@Autowired
	AccountService accountService;

	public List<Journal> getListObjects(int limit, int offset) {
		return dao.getList(limit, offset);
	}
	
	public Journal getObjectById(long id) {
		return dao.getById(id);
	}
	
	public Journal createJournalEntry(Journal journal) {
		long id = dao.add(journal);
		accountService.actualizeBalance(journal.getAccountId(), journal.getValue());
		return dao.getById(id);
	}
	
	/*
	 * example of json
	{
	    "source": {
	        "personId": 1,
	        "accountId": 1,
	        "value": 123,
	        "description": "przelew"
	    },
	    "target": {
	        "accountId": 2
	    }
	}
	 */
	
	public JournalMovement createJournalMovement(JournalMovement jm) {
		BigDecimal valueOfMovement = jm.getSource().getValue();
		int personId = jm.getSource().getPersonId();
		String description = jm.getSource().getDescription();
		jm.getSource().setValue(valueOfMovement.negate());
		jm.getTarget().setValue(valueOfMovement);
		jm.getTarget().setPersonId(personId);
		jm.getTarget().setDescription(description);
		
		Journal source = createJournalEntry(jm.getSource());
		Journal target = createJournalEntry(jm.getTarget());
		
		JournalMovement jmNew = new JournalMovement();
		jmNew.setSource(source);
		jmNew.setTarget(target);
		
		return jmNew;
	}
	
}
