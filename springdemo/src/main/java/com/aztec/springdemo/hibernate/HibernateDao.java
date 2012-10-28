package com.aztec.springdemo.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aztec.springdemo.dao.ItemDao;
import com.aztec.springdemo.hibernate.repository.ItemRepository;

@Component
public class HibernateDao implements ItemDao {
	
	@Autowired
	private ItemRepository repository;
		
	@Override
	public String getValue(String key) {
		String value = null;
		
		Item item = repository.findByKey(key);  
		if(item!=null) {
			value = item.getValue();
		}
		
		return value;
	}

	@Override
	public void createItem(Long id, String key, String value) {
		Item item = new Item();
		item.setId(id);
		item.setKey(key);
		item.setValue(value);
		repository.save(item);
	}
	
	@Override
	public void deleteByKey(String key) {
		repository.deleteByKey(key);
	}
}
