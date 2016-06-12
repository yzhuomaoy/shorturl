package com.tbforward;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;


public class GenTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// https://detail.m.tmall.com/item.htm?id=42952421177
		ExecuteThread[] threads = new ExecuteThread[50];
		for (int i=0; i<50; i++) {
			threads[i] = new ExecuteThread(500*(i+100), 400);
		}
		
		for (int i=0; i<50; i++) {
			Thread thread = new Thread(threads[i]);
			thread.start();
		}

	}
	
	static class ExecuteThread implements Runnable {
		
		private Integer start;
		private Integer range;
		
		public ExecuteThread(Integer start, Integer range) {
			this.start = start;
			this.range = range;
		}

		@Override
		public void run() {
			for (int i=start; i<start+range; i++) {
				CloseableHttpResponse postRes = null;
				CloseableHttpResponse getRes = null;
				try {
					HttpPost post = new HttpPost("http://localhost:8080/gen");
					post.setEntity(new StringEntity("https://detail.m.tmall.com/item.htm?id=42952421" +i));
			        postRes = HttpClientBuilder.create().build().execute(post);
					String result = EntityUtils.toString(postRes.getEntity());
					ObjectMapper mapper = new ObjectMapper();
					JsonResponse json = mapper.readValue(result, JsonResponse.class);
					
					System.out.println(Thread.currentThread().getId());
					System.out.println(json.getData().getCode());
					System.out.println(json.getData().getUrl());
					
					
//					postRes = HttpClientBuilder.create().build().execute(post);
					
					HttpGet get = new HttpGet("http://localhost:8080/redirect/" + json.getData().getCode());
					getRes = HttpClientBuilder.create().build().execute(get);
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					if (postRes != null) {
						try {
							postRes.close();
						} catch (IOException e) {
						}
					}
					if (getRes != null) {
						try {
							getRes.close();
						} catch (IOException e) {
						}
					}
				}
			}
		}
		
	}
	
	static class JsonResponse {
		
//		@JsonProperty("ret")
		Boolean ret;
		
//		@JsonProperty("url_long")
		String msg;
		
//		@JsonProperty("type")
		
		JsonData data;
		
		static class JsonData {
			Integer id;
			String code;
			String url;
			
			public Integer getId() {
				return id;
			}
			public void setId(Integer id) {
				this.id = id;
			}
			public String getCode() {
				return code;
			}
			public void setCode(String code) {
				this.code = code;
			}
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
		}

		public Boolean getRet() {
			return ret;
		}

		public void setRet(Boolean ret) {
			this.ret = ret;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public JsonData getData() {
			return data;
		}

		public void setData(JsonData data) {
			this.data = data;
		}
	}

}
