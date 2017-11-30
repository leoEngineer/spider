package com.liming.spider.service;

import java.util.ArrayList;
import java.util.List;

public class QueryService {

	private static List<String> pages = new ArrayList<String>();

	public String showAllOfPages() {
		String content = "";
		for (String page : pages) {
			content += page + "                                  ";
		}
		return content;
	}

	public List<String> getPages() {
		return pages;
	}

	public void setPages(List<String> pages) {
		this.pages = pages;
	}
	
	
}
