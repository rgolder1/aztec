package com.aztec.springdemo.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.aztec.springdemo.dao.DemoResultDao;

public class JdbcDao implements DemoResultDao {
	
	private DataSource dataSource;
	
	private final String SELECT_SQL = "SELECT RESULT FROM DEMO_RESULT dr WHERE dr.TYPE=?";
	
	@Override
	public String lookupResult(String type) {
		JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
		String result = jdbc.queryForObject(SELECT_SQL, new Object[]{type}, String.class);
		return result;
	}

	@Override
	public void createRecord(Long id, String type, String result) {
		// No-op.
	}

	@Override
	public void deleteByType(String type) {
		// No-op.
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
