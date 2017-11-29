package com.liming.spider.domain;

import java.util.ArrayList;
import java.util.List;

public class RobotsExclusion{

	// User-agent
	private String userAgent;
	// Disallow
	private List<String> disallows = new ArrayList<String>();
	// Allow
	private List<String> allows = new ArrayList<String>();

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public List<String> getDisallows() {
		return disallows;
	}

	public void setDisallows(List<String> disallows) {
		this.disallows = disallows;
	}

	public List<String> getAllows() {
		return allows;
	}

	public void setAllows(List<String> allows) {
		this.allows = allows;
	}

}
