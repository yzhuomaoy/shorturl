package com.tbforward.beans;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.tbforward.utils.ShortUrlUtil;

public class ShortUrl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3191420075693320144L;
	
	private int id;
	private String code;
	
	private URI type;
	private String uri;
	
	public ShortUrl(){}
	
	public ShortUrl(String code, URI type, String uri) {
		this.code = code;
		this.type = type;
		this.uri = uri;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public URI getType() {
		return type;
	}

	public void setType(URI type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public String toString() {
		return "{ID=" + id + ",Code=" + code + ",Type=" + type + ",Uri=" + uri + "}";
	}
	
	public String toUrl() {
		if (type == URI.TMALL_ITEM || type == URI.TAOBAO_ITEM) {
			return type.getLink() + uri;
		} else if (type == URI.TMALL_SHOP || type == URI.TAOBAO_SHOP) {
			return type.getLink().replace("{shop}", uri);
		}
		return uri;
	}
	
	public static class JsonResult {
		String code;
		String url;
		
		public JsonResult() {}
		public JsonResult(String code, String url) {
			this.code = code;
			this.url = url;
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
	
	public JsonResult toJsonResult() {
		return new JsonResult(this.code, this.toUrl());
	}
	
	public enum URI {
		DIRECT(0, ""),
		TMALL_ITEM(1, "https://detail.m.tmall.com/item.htm?id="),
		TMALL_SHOP(2, "https://{shop}.m.tmall.com"),
		TAOBAO_ITEM(3, "https://h5.m.taobao.com/awp/core/detail.htm?id="),
		TAOBAO_SHOP(4, "https://{shop}.m.taobao.com");
		
		private int type;
		
		private String link;
		
		private URI(int type, String link) {
			this.type = type;
			this.setLink(link);
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}
		
		public static URI fromType(int type) {
			for (URI value : URI.values()) {
				if (value.type == type) {
					return value;
				}
			}
			return null;
		}
	}
	
	public static ShortUrl convert(String orgUrl) {
		
		URI type = URI.DIRECT;
		String shopOrId = null;
		
		if (Pattern.matches(TMALL_ITEM_PATTERN, orgUrl)) {
    		int idIndex = orgUrl.lastIndexOf("id=")+3;
    		int endIdx = orgUrl.indexOf("&", idIndex);
    		endIdx = endIdx == -1 ? orgUrl.length() : endIdx;
    		shopOrId = orgUrl.substring(idIndex, endIdx);
    		type = URI.TMALL_ITEM;
    	} else if (Pattern.matches(TMALL_SHOP_PATTERN, orgUrl)) {
    		int firstIdx = orgUrl.indexOf("//");
    		int endIdx = orgUrl.indexOf(".tmall.com", firstIdx+2);
    		endIdx = endIdx == -1 ? orgUrl.length() : endIdx;
    		shopOrId = orgUrl.substring(firstIdx+2, endIdx);
    		if (shopOrId.endsWith(".m")) {
    			shopOrId = shopOrId.substring(0, shopOrId.indexOf(".m")); 
    		}
    		type = URI.TMALL_SHOP;
    	} else if (Pattern.matches(TAOBAO_ITEM_PATTERN, orgUrl)) {
    		int idIndex = orgUrl.lastIndexOf("id=")+3;
    		int endIdx = orgUrl.indexOf("&", idIndex);
    		endIdx = endIdx == -1 ? orgUrl.length() : endIdx;
    		shopOrId = orgUrl.substring(idIndex, endIdx);
    		type = URI.TAOBAO_ITEM;
    	} else if (Pattern.matches(TAOBAO_SHOP_PATTERN, orgUrl)) {
    		int firstIdx = orgUrl.indexOf("//");
    		int endIdx = orgUrl.indexOf(".taobao.com", firstIdx+2);
    		endIdx = endIdx == -1 ? orgUrl.length() : endIdx;
    		
    		shopOrId = orgUrl.substring(firstIdx+2, endIdx);
    		if (shopOrId.endsWith(".m")) {
    			shopOrId = shopOrId.substring(0, shopOrId.indexOf(".m")); 
    		}
    		type = URI.TAOBAO_SHOP;
    	}
		
		if (type == URI.TMALL_ITEM || type == URI.TAOBAO_ITEM) {
			return new ShortUrl(ShortUrlUtil.generate(type.getLink() + shopOrId), type, shopOrId);
		} else if (type == URI.TMALL_SHOP || type == URI.TAOBAO_SHOP) {
			return new ShortUrl(ShortUrlUtil.generate(type.getLink().replace("{shop}", shopOrId)), type, shopOrId);
		}
		
		return new ShortUrl(ShortUrlUtil.generate(orgUrl), type, orgUrl);
	}
	
	public static final String TMALL_ITEM_PATTERN = "^(http:|https:)//(detail.tmall.com|detail.m.tmall.com)/\\S*(id=\\d+)\\S*";
    
    public static final String TMALL_SHOP_PATTERN = "^(http:|https:)//(\\w+.tmall.com|\\w+.m.tmall.com)/?\\S*";
    
    public static final String TAOBAO_ITEM_PATTERN = "^(http:|https:)//(item.taobao.com|h5.m.taobao.com)/\\S*(id=\\d+)\\S*";
    
    public static final String TAOBAO_SHOP_PATTERN = "^(http:|https:)//(\\w+.taobao.com|\\w+.m.taobao.com)/?\\S*";
    
    // https://reenew.tmall.com/?spm=a220o.1000855.1997427721.d4918089.ZKgyo2
	// https://reenew.m.tmall.com/
	
    // https://detail.tmall.com/item.htm?spm=a1z10.4-b.w5003-11124227772.1.hFyXbp&id=42952421177&scene=taobao_shop
	// https://detail.m.tmall.com/item.htm?id=42952421177
	
    // https://yuhemrmf.taobao.com/?spm=2013.1.1000126.d21.zvSSWZ
	// https://yuhemrmf.m.taobao.com
	
    // https://item.taobao.com/item.htm?spm=5148.7690843.1998498240.1.iRQlpM&id=527166537250
	// https://h5.m.taobao.com/awp/core/detail.htm?id=41575096943

	public static String converterString(String url) {
		String toGen = null;
    	if (Pattern.matches(TMALL_ITEM_PATTERN, url)) {
    		int idIndex = url.lastIndexOf("id=")+3;
    		int endIdx = url.indexOf("&", idIndex);
    		endIdx = endIdx == -1 ? url.length() : endIdx;
    		toGen = "https://detail.m.tmall.com/item.htm?id=" + url.substring(idIndex, endIdx);
    	} else if (Pattern.matches(TMALL_SHOP_PATTERN, url)) {
    		int firstIdx = url.indexOf("//");
    		int endIdx = url.indexOf("/", firstIdx+2);
    		endIdx = endIdx == -1 ? url.length() : endIdx;
    		String orgUrl = url.substring(firstIdx+2, endIdx);
    		if (orgUrl.endsWith("m.tmall.com")) {
    			toGen = "https://" + orgUrl; 
    		} else {
    			toGen = "https://" + orgUrl.substring(0, orgUrl.indexOf("tmall.com")) + "m.tmall.com";
    		}
    	} else if (Pattern.matches(TAOBAO_ITEM_PATTERN, url)) {
    		int idIndex = url.lastIndexOf("id=")+3;
    		int endIdx = url.indexOf("&", idIndex);
    		endIdx = endIdx == -1 ? url.length() : endIdx;
    		toGen = "https://h5.m.taobao.com/awp/core/detail.htm?id=" + url.substring(idIndex, endIdx);
    	} else if (Pattern.matches(TAOBAO_SHOP_PATTERN, url)) {
    		int firstIdx = url.indexOf("//");
    		int endIdx = url.indexOf("/", firstIdx+2);
    		endIdx = endIdx == -1 ? url.length() : endIdx;
    		String orgUrl = url.substring(firstIdx+2, endIdx);
    		if (orgUrl.endsWith("m.taobao.com")) {
    			toGen = "https://" + orgUrl; 
    		} else {
    			toGen = "https://" + orgUrl.substring(0, orgUrl.indexOf("taobao.com")) + "m.taobao.com";
    		}
    	}
    	
    	return toGen;
	}
	
	public static void main(String[] args) {
		System.out.println(converterString("https://reenew.tmall.com/?spm=a220o.1000855.1997427721.d4918089.ZKgyo2"));
		System.out.println(convert("https://reenew.tmall.com/?spm=a220o.1000855.1997427721.d4918089.ZKgyo2"));
		System.out.println(converterString("https://reenew.m.tmall.com/"));
		System.out.println(convert("https://reenew.m.tmall.com/"));
		System.out.println(converterString("https://detail.tmall.com/item.htm?spm=a1z10.4-b.w5003-11124227772.1.hFyXbp&id=42952421177&scene=taobao_shop"));
		System.out.println(convert("https://detail.tmall.com/item.htm?spm=a1z10.4-b.w5003-11124227772.1.hFyXbp&id=42952421177&scene=taobao_shop"));
		System.out.println(converterString("https://detail.m.tmall.com/item.htm?id=42952421177"));
		System.out.println(convert("https://detail.m.tmall.com/item.htm?id=42952421177"));
		System.out.println(converterString("https://yuhemrmf.taobao.com/?spm=2013.1.1000126.d21.zvSSWZ"));
		System.out.println(convert("https://yuhemrmf.taobao.com/?spm=2013.1.1000126.d21.zvSSWZ"));
		System.out.println(converterString("https://yuhemrmf.m.taobao.com"));
		System.out.println(convert("https://yuhemrmf.m.taobao.com"));
		System.out.println(converterString("https://item.taobao.com/item.htm?spm=5148.7690843.1998498240.1.iRQlpM&id=527166537250"));
		System.out.println(convert("https://item.taobao.com/item.htm?spm=5148.7690843.1998498240.1.iRQlpM&id=527166537250"));
		System.out.println(converterString("https://h5.m.taobao.com/awp/core/detail.htm?id=41575096943"));
		System.out.println(convert("https://h5.m.taobao.com/awp/core/detail.htm?id=41575096943"));
		
//		SinaShortUrlGenerateServiceImpl service = new SinaShortUrlGenerateServiceImpl();
//		System.out.println(service.generate("http://www.oschina.net"));
	}
}