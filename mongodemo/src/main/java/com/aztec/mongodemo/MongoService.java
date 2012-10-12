package com.aztec.mongodemo;

import java.util.List;

public class MongoService {

	private MongoDao dao;
	
	public MongoService(MongoDao dao) {
		this.dao = dao;
	}

	public String getPhoneNumber(String name) {
		User user = dao.getUser(name);
		return user.getPhoneNumber();
	}
	
	public String getEmail(String name) {
		User user = dao.getUser(name);
		return user.getEmail();
	}

	public User getUser(String name) {
		return dao.getUser(name);
	}
	
	public List<User> getAllUsers() {
		return dao.getAll();
	}
	
	public void deleteUser(String name) {
		dao.deleteUser(name);
	}
	
	public void createUser(String name, String email, String phoneNumber) {
		dao.createUser(name, email, phoneNumber);
	}
	
	public void updateEmail(String name, String email) {
		dao.updateEmail(name, email);
	}

	public void updatePhoneNumber(String name, String phoneNumber) {
		dao.updatePhoneNumber(name, phoneNumber);
	}
}
