package com.aztec.mongodemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * To run test, type 'mongod' on the command line to start the mongo database.
 * 
 * To view the contents, start the mongo shell with type 'mongo mongodemo', then type
 * db.getCollectionName(), and db.users.find() to view any created users.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/application-context.xml"})
public class MongoServiceTest {

	@Autowired
	private MongoService service;
	
	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String PHONE_NUMBER = "phone_number";
	private static final String NEW_EMAIL = "new_email";
	private static final String NEW_PHONE_NUMBER = "new_phone_number";
	
	private static final String STREET = "street";
	private static final String CITY = "city";
	private static final String POSTCODE = "postCode";

	private static final String NEW_STREET = "new_street";
	private static final String NEW_CITY = "new_city";
	
	@After
	public void tearDown() {
		List<User> users = service.getAllUsers();
		for(User user : users) {
			service.deleteUser(user.getName());
		}
	}
	
	@Test
	public void testMongoService_User() {
		// Create a User.
		service.createUser(NAME, EMAIL, PHONE_NUMBER);
		User user = service.getUser(NAME);
		assertNotNull(user);
		assertEquals(NAME, user.getName());
		assertEquals(EMAIL, user.getEmail());
		assertEquals(PHONE_NUMBER, user.getPhoneNumber());
		
		// Update fields.
		service.updateEmail(NAME, NEW_EMAIL);
		String newEmail = service.getEmail(NAME);
		assertEquals(NEW_EMAIL, newEmail);
		service.updatePhoneNumber(NAME, NEW_PHONE_NUMBER);
		String newPhoneNumber = service.getPhoneNumber(NAME);
		assertEquals(NEW_PHONE_NUMBER, newPhoneNumber);

		// Create a second user.
		service.createUser(NAME+"2", EMAIL+"2", PHONE_NUMBER+"2");
		List<User> users = service.getAllUsers();
		assertNotNull(users);
		assertEquals(2, users.size());
		
		// Delete the users.
		for(User thisUser : users) {
			service.deleteUser(thisUser.getName());
		}		
		users = service.getAllUsers();
		assertEquals(0, users.size());
	}

	@Test
	public void testMongoService_Address() {
		// Create a User.
		service.createUser(NAME, EMAIL, PHONE_NUMBER);
		
		// Add an Address.
		service.addAddress(NAME, STREET, CITY, POSTCODE);
		Address address = service.getAddress(NAME);
		assertNotNull(address);
		assertEquals(STREET, address.getStreet());
		assertEquals(CITY, address.getCity());
		assertEquals(POSTCODE, address.getPostCode());
		
		// Update part of the address.
		service.updateAddress(NAME, NEW_STREET, NEW_CITY, POSTCODE);
		address = service.getAddress(NAME);
		assertNotNull(address);
		assertEquals(NEW_STREET, address.getStreet());
		assertEquals(NEW_CITY, address.getCity());
		assertEquals(POSTCODE, address.getPostCode());
		
		// Remove the address.
		service.removeAddress(NAME);
		address = service.getAddress(NAME);
		assertNull(address);
	}
}
