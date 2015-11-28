package models;

import java.util.Date;

import utils.RateLimitStatus;

public class RateLimit {
	private String token;
	private Date startInterval;
	private int rateLimit;
	private int currentRate;
	private RateLimitStatus status;
	public RateLimit next;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getStartInterval() {
		return startInterval;
	}
	public void setStartInterval(Date startInterval) {
		this.startInterval = startInterval;
	}
	public int getRateLimit() {
		return rateLimit;
	}
	public void setRateLimit(int rateLimit) {
		this.rateLimit = rateLimit;
	}
	public int getCurrentRate() {
		return currentRate;
	}
	public void setCurrentRate(int currentRate) {
		this.currentRate = currentRate;
	}
	public RateLimit(String token, Date startInterval, int rateLimit,
			int currentRate) {
		super();
		this.token = token;
		this.startInterval = startInterval;
		this.rateLimit = rateLimit;
		this.currentRate = currentRate;
		this.status = RateLimitStatus.ACTIVE;
	}
	public RateLimitStatus getStatus() {
		return status;
	}
	public void setStatus(RateLimitStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RateLimit [token=" + token + ", startInterval=" + startInterval
				+ ", rateLimit=" + rateLimit + ", currentRate=" + currentRate
				+ ", status=" + status + "]";
	}
	
}
