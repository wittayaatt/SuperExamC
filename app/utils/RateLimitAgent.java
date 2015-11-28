package utils;

import java.security.SecureRandom;
import java.util.Date;

import models.RateLimit;

import org.apache.commons.codec.binary.Base64;

import play.Logger;

public class RateLimitAgent {
	private static long CHECK_INTERVAL = 10*1000;
	private static long BAN_INTERVAL = 5*60*1000;
	private static RateLimitStore tokenStore = new RateLimitStore();
	private RateLimitAgent(){}
	
	public static String newToken() {
		SecureRandom random = new SecureRandom();
	    byte bytes[] = new byte[20];
	    random.nextBytes(bytes);
	    String randomStr = bytes.toString();
	    String keySource = randomStr+(new Date()).getTime();
	    byte [] tokenByte = Base64.encodeBase64(keySource.getBytes());
	    String token = new String(tokenByte);
	    synchronized (tokenStore) {
	    	tokenStore.put(new RateLimit(token, null, 10, 0));
		}
	    return token;
	}
	
	public static boolean isRateOverLimit(String token){
		synchronized (tokenStore) {
			RateLimit rateLimit = tokenStore.get(token);
			Logger.debug(rateLimit.toString());
			if(rateLimit!=null){
				Date now = new Date();
				if(rateLimit.getStartInterval()!=null){
					long interval = now.getTime() - rateLimit.getStartInterval().getTime();
					if(rateLimit.getStatus()==RateLimitStatus.ACTIVE){
						if(interval <= CHECK_INTERVAL){
							int currentRate = rateLimit.getCurrentRate()+1;
							if(currentRate > rateLimit.getRateLimit()){
								//ERROR!! You're under arrest
								rateLimit.setStartInterval(now);
								rateLimit.setStatus(RateLimitStatus.BANNED);
								tokenStore.put(rateLimit);
								return false;
							}else{
								//RATE_INCREMENT
								rateLimit.setCurrentRate(currentRate);
								tokenStore.put(rateLimit);
							}
						}else{
							//RENEW INTERVAL
							rateLimit.setStartInterval(now);
							tokenStore.put(rateLimit);
						}
					}else{
						//BANNED
						if(interval > BAN_INTERVAL){
							//Freedom!!!
							rateLimit.setCurrentRate(0);
							rateLimit.setStartInterval(null);
							rateLimit.setStatus(RateLimitStatus.ACTIVE);
							tokenStore.put(rateLimit);
						}else{
							//ERROR!! You're still in jail
							return false;
						}
					}
				}else{
					rateLimit.setStartInterval(now);
					rateLimit.setCurrentRate(1);
					tokenStore.put(rateLimit);
				}
			}
		}
		return true;
	}

}
