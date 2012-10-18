package com.aztec.social;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.aztec.util.MarshallingUtil;

public class TwitterService {
	
	private TwitterTemplate twitter;
		
	// These values are specified in META-INF/application.properties.  This is not in GIT, as it requires real credentials.
	@Value("${twitter.api.key}") 
	private String apiKey;
	@Value("${twitter.api.secret}")
	private String apiSecret;
	@Value("${twitter.access.token}")
	private String accessToken;
	@Value("${twitter.access.token.secret}")
	private String accessTokenSecret;
	
	private final static String SEARCH_TWEETS_URL = "https://api.twitter.com/1.1/search/tweets.json?q={q}";
	
	public List<Tweet> getTweets(String userName) {
		TimelineOperations timelineOps = getTemplate().timelineOperations();
	    return timelineOps.getUserTimeline(userName);
	}
	
	public SearchResults search(String searchTerm) {
		Map<String, String> urlVariables = new HashMap<String, String>(1);
		urlVariables.put("q", searchTerm);
		ResponseEntity<String> response = getTemplate().getRestTemplate().getForEntity(SEARCH_TWEETS_URL, String.class, urlVariables);
		SearchResults results = MarshallingUtil.unmarshalJson(response.getBody(), SearchResults.class);
		return results;
	}
	
	private TwitterTemplate getTemplate() {
		if(twitter==null) {
			twitter = new TwitterTemplate(apiKey, apiSecret, accessToken, accessTokenSecret); 
		}
		return twitter;
	}
}
