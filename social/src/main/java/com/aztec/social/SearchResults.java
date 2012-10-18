package com.aztec.social;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class SearchResults {

	@XmlElement(name = "statuses")
	private List<Result> results;
	
	public List<Result> getResults() {
		return results;
	}
	public void setResults(List<Result> results) {
		this.results = results;
	}
}
