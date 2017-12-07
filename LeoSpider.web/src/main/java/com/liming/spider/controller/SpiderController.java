package com.liming.spider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liming.spider.service.QueryService;
import com.liming.spider.service.RobotsExclusionService;
import com.liming.spider.service.SpiderService;



@RestController
@EnableAutoConfiguration
@RequestMapping("/spider")
public class SpiderController {
	private static Logger logger = LoggerFactory.getLogger(SpiderController.class);
	  
	@RequestMapping("")
	public String indexPage() {
		return "this is liming's spring boot Demo, you can request /readRobotsExclusions , and then request to crawl website html through /crawl";
	}
	@RequestMapping("/crawl")
	public String crawl() {
		new SpiderService().startToCrawl();
		 return "the spider has been crawling, you can query the records via link: http://localhost:8080/spider/queryRecords";
	}
	
	@RequestMapping("/queryRecords")
	public String queryRecords() {
		return new QueryService().showAllOfPages();
	}
	
	@RequestMapping("/readRobotsExclusions")
	public String readRobotsExclusions() {
		return new RobotsExclusionService().readRobotsExclusions();
	}
	
	public static void main(String[] args) {
		logger.info("The spider system is starting.......");
		SpringApplication.run(SpiderController.class, args);
	}
}