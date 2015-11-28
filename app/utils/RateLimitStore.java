package utils;

import models.RateLimit;

public class RateLimitStore {
	private final int SIZE = 10;
	private RateLimit[] table;
	public RateLimitStore(){
		table = new RateLimit[SIZE];
	}
	 
	private int getBucketNumber(int hash) {
	    return hash & (SIZE - 1);
	}
	
	public void put(RateLimit rateLimit) {
		String token = rateLimit.getToken();
	    int userHash = token.hashCode();

	    int bucket = getBucketNumber(userHash);
	    
	    RateLimit existingElement = table[bucket];
	    
	    for (; existingElement != null; existingElement = existingElement.next) {
	 
	        if (existingElement.getToken().equals(token)) {
	            existingElement.setCurrentRate(rateLimit.getCurrentRate());
	            existingElement.setRateLimit(rateLimit.getRateLimit());
	            existingElement.setStartInterval(rateLimit.getStartInterval());
	            existingElement.setStatus(rateLimit.getStatus());
	            return;
	        }
	    }
	 
	    rateLimit.next = table[bucket];
	    table[bucket] = rateLimit;
	}
	
	public RateLimit get(String token) {
	    int bucket = getBucketNumber(token.hashCode());

	    RateLimit existingElement = table[bucket];

	    while (existingElement != null) {
	        if (existingElement.getToken().equals(token)) {
	            return existingElement;
	        }
	        existingElement = existingElement.next;
	    }
	 
	    return null;
	}
}
