package com.aztec.springdemo.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.aztec.springdemo.dao.ItemDao;

public class BasicServiceWithMocksTest {
	
	private BasicService service;
	private ItemDao dao;
	
	@Before
	public void setup() {
		// Now a genuine unit test, mocking the DAO.
		dao = mock(ItemDao.class);
		when(dao.getValue("Basic")).thenReturn("Mock Result Success");
		
		service = new BasicService(dao);
	}
	
	@Test
	public void testBasicService() {
		String value = service.getValue("Basic");
		
		assertEquals("Mock Result Success", value);
		verify(dao, times(1)).getValue("Basic");
	}
	
	@Test
	public void testBasicService_Failure() {
		String value = service.getValue("Unknown Type"); 
		
		assertNull(value);
	}
}
