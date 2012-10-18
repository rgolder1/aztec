package com.aztec.social;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Result {

	@XmlElement(name = "created_at")
	private String created;

	@XmlElement(name = "name") // This is not working - does not access user/name.
	private String userName;

	@XmlElement(name = "text")
	private String text;

	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
