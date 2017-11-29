package com.liming.spider.service;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class SpiderService implements PageProcessor {
	private static Logger logger = Logger.getLogger(SpiderService.class);
	private Site spider = Site.me().setRetryTimes(3).setSleepTime(1000);

	private String siteAddress = "http://www.infoq.com/cn/java/articles";

	@Override
	public Site getSite() {
		return spider;
	}

	@Override
	public void process(Page page) {
		page.addTargetRequests(page.getHtml().links()
				.regex("(https://infoq\\.com/\\w+/\\w+)").all());
		page.putField("articles",
				page.getUrl().regex("https://infoq\\.com/(\\w+)/.*").toString());
		
		page.putField("text",page.getHtml().xpath("//a/text()"));
		
		page.putField("title",page.getHtml().xpath("//a/title")
						.toString());
		page.putField("href",
				page.getHtml().xpath("//a/href"));
		if (page.getResultItems().get("a") == null) {
			page.setSkip(true);
		}
		logger.getName();
		
	}

	public void startToCrawl() {
		logger.info("the spider system is starting to crawl website.....");
		Spider.create(new SpiderService())
				.addUrl(siteAddress).thread(2)
				.run();
		logger.info("the spider system is ending.");
	}
	
	  public static void main(String[] args) {
		  new SpiderService().startToCrawl();
	    }
}
