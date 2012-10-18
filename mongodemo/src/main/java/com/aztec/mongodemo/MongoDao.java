package com.aztec.mongodemo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class MongoDao {
	
	private MongoTemplate template;
	
	private final static String USERS_COLLECTION = "users";
	private final static String NAME_FIELD = "name";
	private final static String EMAIL_FIELD = "email";
	private final static String PHONENUMBER_FIELD = "phoneNumber";
	private final static String ADDRESS_FIELD = "address";
	
	public MongoDao(MongoTemplate template) {
		this.template = template;
	}
	
	public void createUser(String name, String email, String phoneNumber) {
		User user = new User(name, email, phoneNumber);
		template.save(user, USERS_COLLECTION);
	}
	
	public User getUser(String name) {
		return  template.findOne(new Query(Criteria.where(NAME_FIELD).is(name)), User.class, USERS_COLLECTION);
	}

	public List<User> getAll() {
		return  template.findAll(User.class, USERS_COLLECTION);
	}
	
	public void deleteUser(String name) {
		template.remove(new Query(Criteria.where(NAME_FIELD).is(name)), USERS_COLLECTION);
	}
	
	public void updateEmail(String name, String email) {
		template.updateFirst(new Query(Criteria.where(NAME_FIELD).is(name)), Update.update(EMAIL_FIELD, email), USERS_COLLECTION);
	}

	public void updatePhoneNumber(String name, String phoneNumber) {
		template.updateFirst(new Query(Criteria.where(NAME_FIELD).is(name)), Update.update(PHONENUMBER_FIELD, phoneNumber), USERS_COLLECTION);
	}
	
	public void addAddress(String name, Address address) {
		updateAddress(name, address);
	}
	
	public void removeAddress(String name) {
		updateAddress(name, null);
	}
	
	public void updateAddress(String name, Address address) {
		template.updateFirst(new Query(Criteria.where(NAME_FIELD).is(name)), Update.update(ADDRESS_FIELD, address), USERS_COLLECTION);
	}
	
	public Address getAddress(String name) {
		Address address = null;
		User user = getUser(name);
		if(user!=null) {
			address = user.getAddress();
		}
		return address;
	}
}
