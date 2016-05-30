package com.tbforward.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbforward.beans.ShortUrl;
import com.tbforward.exception.ServiceException;

@Service("sinaShortUrl")
public class SinaShortUrlGenerateServiceImpl implements ShortUrlGenerateService {
	
	@Override
	public ShortUrl getByCode(String code) throws ServiceException {
		return null;
	}
	
	@Override
	public ShortUrl generate(String url) throws ServiceException {
		String sinaUrl = "http://api.t.sina.com.cn/short_url/shorten.json?source="
				+ 1681459862 + "&url_long=";

		SinaResponse[] array = null;
		try {
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://" + url;
			}
	        HttpGet get = new HttpGet(sinaUrl + url);
	        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(get);
	        String result = EntityUtils.toString(response.getEntity());
	        ObjectMapper mapper = new ObjectMapper();
			array = mapper.readValue(result, SinaResponse[].class);
		} catch (Exception e) {
		}
		
		return array != null && array.length == 1 ? 
				new ShortUrl(array[0].getShortUrl(), array[0].getOrgUrl()) : null;
	}
	
	static class SinaResponse {
		
		@JsonProperty("url_short")
		String shortUrl;
		
		@JsonProperty("url_long")
		String orgUrl;
		
		@JsonProperty("type")
		Integer type;

		public String getShortUrl() {
			return shortUrl;
		}

		public void setShortUrl(String shortUrl) {
			this.shortUrl = shortUrl;
		}

		public String getOrgUrl() {
			return orgUrl;
		}

		public void setOrgUrl(String orgUrl) {
			this.orgUrl = orgUrl;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}
	}
	
	

}


