package com.aztec.springdemo.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.aztec.springdemo.dao.ItemDao;

public class JdbcDao implements ItemDao {
	
	private DataSource dataSource;
	
	private final String SELECT_SQL = "SELECT VALUE FROM ITEM i WHERE i.KEY=?";
	
	@Override
	public String getValue(String key) {
		JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
		String value = jdbc.queryForObject(SELECT_SQL, new Object[]{key}, String.class);
		return value;
	}

	@Override
	public void createItem(Long id, String key, String value) {
		// No-op.
	}

	@Override
	public void deleteByKey(String key) {
		// No-op.
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
