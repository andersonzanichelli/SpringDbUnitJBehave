package com.journaldev.spring.dao;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.unitils.UnitilsJUnit4;

import com.journaldev.spring.model.Person;

@Configuration
@EnableTransactionManagement
@RunWith(SpringJUnit4ClassRunner.class)
@ImportResource({ "classpath:hibernate4config.xml" })
@ContextConfiguration("../../../../hibernate4config.xml")
public class PersonDAOImplTestOld extends UnitilsJUnit4 {
	
	private Session session;
	@Autowired
    private SessionFactory sessionFactory;
	@Autowired
	private PersonDAO personDAO;
	
	@Before
	public final void before() {
		session = sessionFactory.openSession();
	}
	
	@After
	public final void after() {
		session.close();
	}
	
	@Test
	public void testFindById() {
	    Person person = personDAO.findById(1);
	    assertEquals(1, person.getId());
	    assertEquals("Anderson", person.getName());
	    assertEquals("Brazil", person.getCountry());
	}

}
