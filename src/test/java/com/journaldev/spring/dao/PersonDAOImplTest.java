package com.journaldev.spring.dao;

import java.io.FileInputStream;

import junit.framework.TestCase;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
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

import com.journaldev.spring.model.Person;

@Configuration
@EnableTransactionManagement
@RunWith(SpringJUnit4ClassRunner.class)
@ImportResource({ "classpath:hibernate4config.xml" })
@ContextConfiguration("../../../../hibernate4config.xml")
public class PersonDAOImplTest extends TestCase{

	IDatabaseTester databaseTester;
	private Session session;
	@Autowired
    private SessionFactory sessionFactory;
	@Autowired
	private PersonDAO personDAO;
	
	@Before
	public final void before() throws Exception {
		session = sessionFactory.openSession();
		databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver","jdbc:mysql://localhost/integration","root", "root");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/java/com/journaldev/spring/dao/person.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}
	
	@After
	public final void after() throws Exception {
		session.close();
		databaseTester.onTearDown();
	}
	
	@Test
	public void testFindById() {
	    Person anderson = personDAO.findById(1);
	    Person bilbo = personDAO.findById(2);
	    
	    assertEquals(1, anderson.getId());
	    assertEquals("Anderson Zanichelli", anderson.getName());
	    assertEquals("Brazil", anderson.getCountry());
	    
	    assertEquals(2, bilbo.getId());
	    assertEquals("Bilbo Baggins", bilbo.getName());
	    assertEquals("The Shire", bilbo.getCountry());
	}

}
