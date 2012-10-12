package com.aztec.springdemo.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aztec.springdemo.hibernate.DemoResult;

public interface DemoRepository extends JpaRepository<DemoResult, Long> {

	public DemoResult findByType(String type);
	
    @Query("SELECT dr FROM DemoResult dr WHERE dr.type=:type")
    public DemoResult findBySpecifiedType(@Param("type") String type);
}
