package com.tbforward.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncByteConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
//@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "tmall", method = RequestMethod.GET)
	public void getWorkflow(@RequestParam("shopUrl") String shopUrl, 
			HttpServletResponse response) throws Exception {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			httpclient.start();
			Future<Boolean> future = httpclient.execute(
					HttpAsyncMethods.createGet(shopUrl),
					new MyResponseConsumer(response), null);
			Boolean result = future.get();
			if (result != null && result.booleanValue()) {
//				System.out.println("Request successfully executed");
			} else {
//				System.out.println("Request failed");
			}
//			System.out.println("Shutting down");
		} finally {
			httpclient.close();
		}
//		System.out.println("Done");

		// response.setContentType(wrapper.getContentType());
		// FileCopyUtils.copy(wrapper.getBytes(), response.getOutputStream());
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