package com.aztec.social;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/application-context.xml"})
public class TwitterServiceTest {

	@Autowired
	private TwitterService service;
	
	@Test
	public void testGetUserTweets() {
		List<Tweet> tweets = service.getTweets("robert_golder");
		assertNotNull(tweets);
		assertTrue(tweets.size()>0);
		System.out.println("Total tweets: "+tweets.size()); 
		for(Tweet tweet : tweets) {
	    	String text = tweet.getText();
	    	System.out.println("["+tweet.getCreatedAt()+"] "+text);
	    }
	}
	
	@Test
	public void testSearch() {
		SearchResults results = service.search("AFCWimbledon");
		assertNotNull(results);
		assertNotNull(results.getResults());
		assertTrue(results.getResults().size()>0);
		for(Result result : results.getResults()) {
			assertNotNull(result.getText());
			assertNotNull(result.getCreated());
			//assertNotNull(result.getName());  // TODO fix.
			System.out.println("Search result: Created: ["+result.getCreated()+"] Text: ["+result.getText()+"]");
		}
	}
}
