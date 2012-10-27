package com.aztec.springdemo.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.aztec.springdemo.hibernate.DemoResult;

public interface DemoRepository extends JpaRepository<DemoResult, Long> {

	public DemoResult findByType(String type);
	
    @Query("SELECT dr FROM DemoResult dr WHERE dr.type=:type")
    public DemoResult findBySpecifiedType(@Param("type") String type);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM DemoResult dr WHERE dr.type=:type")
    public void deleteByType(@Param("type") String type);
}
