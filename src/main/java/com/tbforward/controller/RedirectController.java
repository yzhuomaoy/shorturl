package com.tbforward.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncByteConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tbforward.beans.ShortUrl;
import com.tbforward.service.ShortUrlGenerateService;

@Controller
@RequestMapping("/redirect")
public class RedirectController {
	
	@Autowired
	@Qualifier("localShortUrl")
	private ShortUrlGenerateService localShortUrlService;
	
	@RequestMapping("{code}")
	public void syncRetrieve(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response) {
		ShortUrl shortUrl = localShortUrlService.getByCode(code);
		
		String ua = request.getHeader("User-Agent").toLowerCase();
		CloseableHttpResponse tbRes = null;
		try {
//		if (ua.matches("micromessenger")) {
			// webchat
			HttpGet get = new HttpGet(shortUrl.toUrl());
	        tbRes = HttpClientBuilder.create().build().execute(get);
	        FileCopyUtils.copy(tbRes.getEntity().getContent(), response.getOutputStream());
//		} else {
//			response.sendRedirect(shortUrl.getUrl());
//		}
		} catch (Exception e) {
			if (tbRes != null) {
				try {
					tbRes.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	@RequestMapping("/async/{code}")
	public void asyncRetrieve(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ShortUrl shortUrl = localShortUrlService.getByCode(code);
		
		String ua = request.getHeader("User-Agent").toLowerCase();
//		if (ua.matches("micromessenger")) {
			// webchat
			CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
			try {
				httpclient.start();
				Future<Boolean> future = httpclient.execute(
						HttpAsyncMethods.createGet(shortUrl.toUrl()),
						new MyResponseConsumer(response), null);
				Boolean result = future.get();
				if (result != null && result.booleanValue()) {
					// System.out.println("Request successfully executed");
				} else {
					// System.out.println("Request failed");
				}
				// System.out.println("Shutting down");
			} finally {
				httpclient.close();
			}
//		} else {
//			response.sendRedirect(shortUrl.getUrl());
//		}
	}

	// public static void fileDownload(String fileName, InputStream stream,
	// HttpServletResponse response, String contentType)
	// throws IOException {
	// response.setHeader("Cache-Control", "no-cache");
	// response.setHeader("Pragma", "no-cache");
	// response.setDateHeader("Expires", 0);
	// response.setContentType(contentType);
	// response.setHeader("Content-Disposition", "attachment;filename="
	// + fileName);
	//
	// FileCopyUtils.copy(stream, response.getOutputStream());
	// }

	static class MyResponseConsumer extends AsyncByteConsumer<Boolean> {

		private HttpServletResponse servletResponse;

		public MyResponseConsumer(HttpServletResponse servletResponse) {
			this.servletResponse = servletResponse;
		}

		@Override
		protected void onByteReceived(ByteBuffer buf, IOControl ioctrl)
				throws IOException {
			WritableByteChannel channel = Channels
					.newChannel(this.servletResponse.getOutputStream());

//			ByteBuffer tmp = ByteBuffer.wrap(new String("<head><div id='wx_pic' style='margin:0 auto;display:none;'><img src='test.jpg'/></div></head>").getBytes()).put(buf);
			channel.write(buf);
		}

		@Override
		protected void onResponseReceived(HttpResponse response)
				throws HttpException, IOException {
		}

		@Override
		protected Boolean buildResult(HttpContext context) throws Exception {
			return Boolean.TRUE;
		}

	}
}