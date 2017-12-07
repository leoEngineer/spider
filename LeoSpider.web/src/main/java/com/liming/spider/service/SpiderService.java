package com.liming.spider.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class SpiderService implements PageProcessor {
	private static Logger logger = LoggerFactory.getLogger(SpiderService.class);
	private Site spider = Site.me().setRetryTimes(3).setSleepTime(1000);
	private String basePath = "C:\\develping\\logs\\data\\spider\\pages\\www.infoq.com\\";

	private String siteAddress = "http://www.infoq.com/cn/java/articles";

	@Override
	public Site getSite() {
		return spider;
	}

	@Override
	public void process(Page page) {

		logger.info("count links:"
				+ page.getHtml().links().regex("(http://www.infoq.com/cn/*)")
						.all());
		
		List<String> urls = page.getHtml().links().regex("(http://www.infoq.com/cn/*)").all();
		
		RobotsExclusionService.filterUrlByExclusionSetting(urls);
		page.addTargetRequests(urls);

		page.putField("title", page.getHtml().xpath("//title/text()"));
		page.putField("url", page.getRequest().getUrl());
		String path = basePath + System.currentTimeMillis() + ".html";
		page.putField("savePath", path);
		if (page.getResultItems().get("title") == null) {
			page.setSkip(true);
		} else {
			new QueryService().getPages().add(path);
			saveAsHtmlFile(page);
		}
	}


	private void saveAsHtmlFile(Page page) {
		page.getHtml();
		FileWriter fw;
		try {

			fw = new FileWriter(page.getResultItems().get("savePath")
					.toString());

			String content = page.getHtml().toString();

			fw.write(content, 0, content.length());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("occured error when write file for saving html.");
		}

	}

	public void startToCrawl() {
		logger.info("the spider system is starting to crawl website.....");
		Spider.create(new SpiderService())
				.addUrl(siteAddress)
				// .setScheduler(new RedisScheduler("localhost"))   // support to configure redis server if redis has already prepared;
				.addPipeline(
						new JsonFilePipeline(
								"C:\\develping\\logs\\data\\spider\\json"))
				.thread(5).run();
		logger.info("the spider system is ending.");
	}

	public static void main(String[] args) {
		new SpiderService().startToCrawl();
	}
}
