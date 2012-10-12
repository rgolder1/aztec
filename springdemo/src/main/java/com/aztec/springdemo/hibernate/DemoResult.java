package com.aztec.springdemo.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DemoResult {

    @Id
    private long id;
    
    private String type;
    private String result;
    
    public DemoResult() {
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
