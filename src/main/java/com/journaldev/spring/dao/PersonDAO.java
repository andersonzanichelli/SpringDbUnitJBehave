package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.model.Person;

public interface PersonDAO {

	public void insert(Person p);
	public void update(Person p);
	public List<Person> list();
	public Person findById(int id);
	public void delete(int id);
}
