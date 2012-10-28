package com.aztec.springdemo.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.aztec.springdemo.hibernate.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	public Item findByKey(String key);
	
    @Query("SELECT i FROM Item i WHERE i.key=:key")
    public Item findBySpecifiedKey(@Param("key") String key);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Item i WHERE i.key=:key")
    public void deleteByKey(@Param("key") String key);
}
