package pl.j2dev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.j2dev.dao.jdbc.PersonDaoImpl;
import pl.j2dev.pojo.Person;

@Service
public class PersonService {
	
	@Autowired
	PersonDaoImpl dao;
	
	public List<Person> getListOfAllObjects() {
		return dao.getList();
	}

	public Person getObjectById(long id) {
		return dao.getById(id);
	}
	
	public Person createNewPersonInDB(Person person) {
		long id = dao.add(person);
		person.setId(id);
		return person;
	}
	
}
