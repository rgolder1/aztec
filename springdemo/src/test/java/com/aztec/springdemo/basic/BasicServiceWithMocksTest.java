package com.aztec.springdemo.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.aztec.springdemo.basic.BasicService;
import com.aztec.springdemo.dao.DemoResultDao;

public class BasicServiceWithMocksTest {
	
	private BasicService service;
	private DemoResultDao dao;
	
	@Before
	public void setup() {
		// Now a genuine unit test, mocking the DAO.
		dao = mock(DemoResultDao.class);
		when(dao.lookupResult("Basic")).thenReturn("Mock Result Success");
		
		service = new BasicService(dao);
	}
	
	@Test
	public void testBasicService() {
		String result = service.getResult("Basic");
		
		assertEquals("Mock Result Success", result);
		verify(dao, times(1)).lookupResult("Basic");
	}
	
	@Test
	public void testBasicService_Failure() {
		String result = service.getResult("Unknown Type"); 
		
		assertNull(result);
	}
}
