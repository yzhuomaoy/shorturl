package com.tbforward.utils;

import java.util.regex.Pattern;

public class TaobaoUtil {
	
	public static final String TMALL_ITEM_PATTERN = "^(http:|https:)//(detail.tmall.com|detail.m.tmall.com)/\\S*(id=\\d{11})\\S*";
    
    public static final String TMALL_SHOP_PATTERN = "^(http:|https:)//(\\w+.tmall.com|\\w+.m.tmall.com)/?\\S*";
    
    public static final String TAOBAO_ITEM_PATTERN = "^(http:|https:)//(item.taobao.com|h5.m.taobao.com)/\\S*(id=\\d{11})\\S*";
    
    public static final String TAOBAO_SHOP_PATTERN = "^(http:|https:)//(\\w+.taobao.com|\\w+.m.taobao.com)/?\\S*";
    
    // https://reenew.tmall.com/?spm=a220o.1000855.1997427721.d4918089.ZKgyo2
	// https://reenew.m.tmall.com/
	
    // https://detail.tmall.com/item.htm?spm=a1z10.4-b.w5003-11124227772.1.hFyXbp&id=42952421177&scene=taobao_shop
	// https://detail.m.tmall.com/item.htm?id=42952421177
	
    // https://yuhemrmf.taobao.com/?spm=2013.1.1000126.d21.zvSSWZ
	// https://yuhemrmf.m.taobao.com
	
    // https://item.taobao.com/item.htm?spm=5148.7690843.1998498240.1.iRQlpM&id=527166537250
	// https://h5.m.taobao.com/awp/core/detail.htm?id=41575096943

	public static String converter(String url) {
		String toGen = null;
    	if (Pattern.matches(TMALL_ITEM_PATTERN, url)) {
    		int idIndex = url.lastIndexOf("id=")+3;
    		toGen = "https://detail.m.tmall.com/item.htm?id=" + url.substring(idIndex, idIndex+11);
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
    		toGen = "https://h5.m.taobao.com/awp/core/detail.htm?id=" + url.substring(idIndex, idIndex+11);
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
		System.out.println(TaobaoUtil.converter("https://reenew.tmall.com/?spm=a220o.1000855.1997427721.d4918089.ZKgyo2"));
		System.out.println(TaobaoUtil.converter("https://reenew.m.tmall.com/"));
		System.out.println(TaobaoUtil.converter("https://detail.tmall.com/item.htm?spm=a1z10.4-b.w5003-11124227772.1.hFyXbp&id=42952421177&scene=taobao_shop"));
		System.out.println(TaobaoUtil.converter("https://detail.m.tmall.com/item.htm?id=42952421177"));
		System.out.println(TaobaoUtil.converter("https://yuhemrmf.taobao.com/?spm=2013.1.1000126.d21.zvSSWZ"));
		System.out.println(TaobaoUtil.converter("https://yuhemrmf.m.taobao.com"));
		System.out.println(TaobaoUtil.converter("https://item.taobao.com/item.htm?spm=5148.7690843.1998498240.1.iRQlpM&id=527166537250"));
		System.out.println(TaobaoUtil.converter("https://h5.m.taobao.com/awp/core/detail.htm?id=41575096943"));
		
//		SinaShortUrlGenerateServiceImpl service = new SinaShortUrlGenerateServiceImpl();
//		System.out.println(service.generate("http://www.oschina.net"));
	}
}
