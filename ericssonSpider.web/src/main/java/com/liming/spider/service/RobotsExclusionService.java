package com.liming.spider.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.liming.spider.domain.RobotsExclusion;

public class RobotsExclusionService {
	private static Logger logger = LoggerFactory
			.getLogger(RobotsExclusionService.class);
	private static String filePath = "./Robots_exclusion_standard.properties";
	private static RobotsExclusion currentRobotsExclusion = null;
	private static List<RobotsExclusion> currentRobotsExclusions = new ArrayList<RobotsExclusion>();

	public List<RobotsExclusion> readFile() throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();
		URL url = classLoader.getResource(filePath);
		System.out.println(url.getFile());
		File file = new File(url.getFile());
		File f = new File(file.getAbsolutePath());

		InputStreamReader read = new InputStreamReader(new FileInputStream(f),
				"UTF-8");
		BufferedReader reader = new BufferedReader(read);
		String line = null;
		while ((line = reader.readLine()) != null) {

			if ("".equals(line)) {
				continue;
			} else {
				saveToVO(line.trim());
			}
		}
		logger.info(" has already read, the total agents : "
				+ currentRobotsExclusions.size());
		return currentRobotsExclusions;
	}

	public String readRobotsExclusions() {
		JSONArray array = new JSONArray();
		try {
			array.addAll(readFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return array.toJSONString();
	}

	private void saveToVO(String line) {
		// User-agent
		// Disallow
		// Allow

		String userAgent = "User-agent";
		String disallow = "Disallow";
		String allow = "Allow";
		String symbol = ":";
		String[] values = line.split(symbol);
		if (line.startsWith(userAgent)) {
			currentRobotsExclusion = new RobotsExclusion();
			currentRobotsExclusions.add(currentRobotsExclusion);
			if (values.length == 2) {
				currentRobotsExclusion.setUserAgent(values[1]);
			}
		} else if (line.startsWith(disallow)) {
			if (values.length == 2) {
				currentRobotsExclusion.getDisallows().add(values[1]);
			}
		} else if (line.startsWith(allow)) {
			if (values.length == 2) {
				currentRobotsExclusion.getAllows().add(values[1]);
			}
		}
	}

	public static List<RobotsExclusion> getCurrentRobotsExclusions() {
		return currentRobotsExclusions;
	}

	// disallow as false;
	private static boolean judgeIfExclusionUrl(String url) {
		List<RobotsExclusion> exclusions = RobotsExclusionService
				.getCurrentRobotsExclusions();
		for (RobotsExclusion robotsExclusion : exclusions) {

			if (url.startsWith(robotsExclusion.getUserAgent())) {
				if (!judgeIfDisallow(url, robotsExclusion.getDisallows())) {
					return false;
				}
			}
		}
		return true;
	}

	// disallow as false;
	private static boolean judgeIfDisallow(String url, List<String> disallows) {
		for (String disallow : disallows) {
			if (url.indexOf(disallow) != -1) {
				return false;
			}
		}
		return true;
	}
	
	public static void filterUrlByExclusionSetting(List<String> urls) {

		for (Iterator iterator = urls.iterator(); iterator.hasNext();) {
			Object url = iterator.next();
			if (!judgeIfExclusionUrl(url.toString())) {
				urls.remove(url);
			}
		}
	}
	
	public static void main(String[] args) {
		RobotsExclusionService service = new RobotsExclusionService();
		try {
			service.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
