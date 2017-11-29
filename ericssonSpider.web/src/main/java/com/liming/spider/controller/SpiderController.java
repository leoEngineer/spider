package com.liming.spider.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liming.spider.service.RobotsExclusionService;
import com.liming.spider.service.SpiderService;



@RestController
@EnableAutoConfiguration
@RequestMapping("/spider")
public class SpiderController {
	private static Logger logger = Logger.getLogger(SpiderController.class);

	@RequestMapping("/hi")
	public String sayHi() {
		return "hi, can i help you.";
	}

	@RequestMapping("")
	public String indexPage() {
		return "this is liming's spring boot Demo";
	}
	@RequestMapping("/crawl")
	public String crawl() {
		new SpiderService().startToCrawl();
		 return "the spider has been crawling, you can query the records via link: http://localhost:8080/spider/queryRecords";
	}
	
	@RequestMapping("/queryRecords")
	public String queryRecords() {
		return "the spider has been crawling";
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