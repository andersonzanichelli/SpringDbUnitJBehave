package com.journaldev.spring.stories;

import java.io.FileInputStream;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.journaldev.spring.dao.PersonDAO;
import com.journaldev.spring.model.Person;

@Component
@Configuration
@EnableTransactionManagement
@RunWith(SpringJUnit4ClassRunner.class)
@ImportResource({ "classpath:hibernate4config.xml" })
@ContextConfiguration("../../../../hibernate4config.xml")
public class PersonDAOSteps extends TestCase {
	
	IDatabaseTester databaseTester;
	private Session session;
	@Autowired
    private SessionFactory sessionFactory;
	@Autowired
	private PersonDAO personDAO;
	private Person person;
	
	@Before
	public final void before() {
		session = sessionFactory.openSession();
	}
	
	@After
	public final void after() throws Exception {
		session.close();
		databaseTester.onTearDown();
	}
	
	@BeforeScenario
    public void beforeScenario() throws Exception {
		this.person = null;
		databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver","jdbc:mysql://localhost/integration","root", "root");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/java/com/journaldev/spring/dao/person.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}
	
	@Given("looking for a person with number [id]")
	public void givenLookingForPerson(@Named("id") Integer id) {
		person = personDAO.findById(id);
	}
	
	@Then("recieves the person fullname [name] who lives in [country]")
	public void thenRecievesThePerson(@Named("name") String name, @Named("country") String country) {
		Assert.assertEquals(name, person.getName());
		Assert.assertEquals(country, person.getCountry());
	}

}
