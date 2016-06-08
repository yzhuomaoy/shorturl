<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<link rel='icon' href='' type=‘image/x-ico’ />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="renderer" content="webkit"> 
		<title>
			tbforward
		</title>
		<link rel="stylesheet" href="style/in_20131127.css" type="text/css" />
		<link onerror="wx_loaderror(this)" rel="stylesheet"
		href="style/page_login262dd6.css?23232">
		<link href="style/video.css" rel="stylesheet" type="text/css">
		<link href="style/in_20151119.css" rel="stylesheet" type="text/css">
		<style>.layer_error {
	font-size: 15px;
}</style>
		<link href="style/select2.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.9.1.min.js">
		</script>
		<script src="js/layer-2.x/layer.js">
		</script>
		<script src="js/layer-2.x/extend/layer.ext.js">
		</script>
		<script type="text/javascript" src="js/jquery.qrcodes.min.js">
		</script>
		<script type="text/javascript">var points = [(window.performance && window.performance.timing && window.performance.timing.navigationStart) || +new Date()];
points[1] = +new Date() - points[0]; //css加载完成
$(function() {
	$.ajax({
		url: 'common/User_Head.php',
		data: {},
		type: 'post',
		cache: false,
		async: false,
		dataType: 'html',
		success: function(data) {
			$("#head").html(data);
		}
	});
	$.ajax({
		url: 'common/Bottom.Inc.php',
		data: {},
		type: 'post',
		cache: false,
		dataType: 'html',
		success: function(data) {
			$("#_bottom").html(data);
		}
	});
	$.ajax({
		url: 'ajax/short_url/to_log_count_db.php',
		data: {},
		type: 'post',
		cache: false,
		dataType: 'text',
		success: function(data) {
			var log_count = (parseInt(data) + parseInt(173452410)) + "UV";
			$("#log_count_span").html(log_count);
		}
	});
	$(".a_index").hide(); //首页a标签隐藏
	$("#loginBt").click(function() {
		$("#form_url").submit();
		//loginBt_click();
	})
})</script>
		<script>var _hmt = _hmt || [];
(function() {
	var hm = document.createElement("script");
	hm.src = "//hm.baidu.com/hm.js?a27fc630bba0cc00592cbc8d4ccfadf8";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(hm, s);
})();</script>
	</head>
	<body>
		<div id="head">
		</div>
		<div id="max" style="">
			<div style="margin: 0 auto; width: 510px; margin-top: 60px;">
				<img style="" id="qiange_video_img"
				src="images/qiange_title_12.31.png" alt="" />
				<br />
				<form id="form_url" action="#" method="get"
				onsubmit="javascript:return check()">
					<table id="tb1" cellspacing="4px" width="700"
					style="margin-top: 30px;">
						<tr>
							<td>
								店铺网址:
							</td>
							<td>
								<textarea style="height: 80px; width: 605px;" id="url"
								name="shopUrl"></textarea>
</td>						</tr>
						<tr>
							<td>
							</td>
							<td style="color: #9A9999">
								输入店铺网址,转换以后就可在微信打开(支持首页、商品页、优惠券、h5自定义)。
								<a
								target="_Blank" href="Help_Wechat_url.php">
									帮助
								</a>
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td>
								<div class="login_btn_panel">
									<a class="btn_login" title="转换网址" href="javascript:"
									id="loginBt"
									style="width: 155px; height: 42px;">
										转换网址
									</a>
								</div>
								<div id="point" style="color: red; font-size: 15px;">
								</div>
							</td>
						</tr>
					</table>
					<br />
					<br />
					<img style="display: none;" id="loading"
					src="images/loading_circle2.gif" alt="" />
					<p class="url_p"
					style="display: none; font-size: 17px; margin-left: 45px; font-weight: bold; float: left;">
						微信扫一下
					</p>
					<p style="clear: left;">
					</P>
					<table>
						<tr>
							<td width="40">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td width="40">
							</td>
							<td style="">
								<div id="code" style="">
								</div>
							</td>
						</tr>
						<tr>
							<td width="40">
							</td>
							<td>
								<div id="shop_url" style="font-size: 17px;">
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<script>function check() {
	var mobile = "";//获取登陆用户的手机号
var userid = "";//获取登陆用户的id号
var service_name = "";
if (userid == "") {
	userid = "error";
}
if (service_name == "") {
	service_name = "error";
}
$("#code").html("");
var name = $("#shopName").val();
var names = $.trim(name); //标题名
var url = document.getElementById('url').value;
var urls = $.trim(url); //链接名
var short_url = 1; //短连接变量
if (userid == "error") {
	$(".url_p").hide();
	$("#shop_url").hide();
	var html = $.ajax({
		url: "login.php",
		async: false
	}).responseText;
	//页面层-自定义
	layer.open({
		type: 1,
		title: ['', 'font-size:26px;'],
		skin: 'layui-layer-rim', //加上边框
		area: ['700px', '480px'], //宽高
		content: html
	});
} else {
	if (urls == '') {
		//非空验证
		$("#point").html("网址不能为空");
		$(".url_p").hide();
		$("#shop_url").hide();
	} else {
		var results = to_Judge_url(urls, names); //存储返回过来的url
		$("#point").html("");
		if (results["Numerical"] != -1) {
			//是天猫和淘宝的链接
			var convert_result = "trues";
			if (results["Numerical"] == -2) {
				//链接类型不支持转换
				convert_result = "falses";
				$("#point").html("链接类型不支持转换");
				$(".url_p").hide();
				$("#shop_url").hide();
				name = results["title"];
				to_fail_wechat_url(name, url, convert_result, userid);
			} else {
				to_y_url(results, userid, url, convert_result, service_name);
			}
		} else {
			$(".url_p").hide();
			$("#shop_url").hide();
			$("#point").html("请输入淘宝跟天猫的链接");
		}
	}
}
return false;
}
//满足条件-不为空，已转换链接
function to_y_url(result, userid, url, convert_result, service_name) {
	urls = result["url"];
	name = result["title"];
	shop_type = result["type"];
	small_shop_type = result["Small_shop_type"];
	to_user_type(userid, urls);
	if (service_name == "error") {
		var service_ip = to_service_ip(shop_type); //确定服务器
	} else {
		var service_ip = service_name; //确定服务器
		;
	}
	clean_url = urls;
	urls = encodeURIComponent(urls);
	var shop = "http://djaa.cn/shopUrl.php?shopName=" + name + "&shopUrl=" + urls + "&userid=" + userid; //拼接成的链接
	short_url = to_short_url(shop, 6, 5); //短链接生成返回的值
	var short_result = to_short_url_repeart(short_url, name, clean_url, userid); //验证短链接是否重复
	var short_repeart = short_result["repeart"];
	var short_flag = short_result["flag"];
	if (short_repeart == 'yes' && short_flag == false) {
		//不正常状态，重新转换短链接
		short_url = to_short_url(shop, 8, 3); //短链接生成返回的值
		short_result = to_short_url_repeart(short_url, name, clean_url, userid); //验证短链接是否重复
		short_repeart = short_result["repeart"];
		short_flag = short_result["flag"];
	}
	if (short_repeart == 'no') {
		//短链接未重复，添加链接
		var result = to_wechat_url(name, url, shop, short_url, shop_type, small_shop_type, clean_url, convert_result, service_ip, userid); //信息存入数据库 -1.先判断短链接是否存在，若存在，不添加数据
	}
	var url_short = "http://" + service_ip + "/" + short_url; //转换成功后的短网址
	var random = MathRand();
	var url_short_random = url_short + "?id=" + random;
	url_short_random = $.trim(url_short_random);//暂时取消随机值
	var flag = false;
	for (var i = 0; i < 99; i++) {
		var result_url =url_short;
		var result_url = to_weibo_url(url_short); //新浪微博短链接生成
		result_url = $.trim(result_url);
		if (result_url != "error" && result_url != "") {
			flag = true;
			to_wechat_weibo_url(short_url, result_url, userid); //新浪微博短链接添加数据库
			$("#shop_url").html(result_url);
			url_crode(result_url);
			
			break;
		} else {
			$(".url_p").hide();
			$("#shop_url").hide();
		}
	}
	if (flag == false) {
		layer.alert('网络连接异常，请稍后再试-01', {
			icon: 2
		});
		var dates = getNowFormatDate(); //获取当前时间
		to_weibo_log(url_short_random, result_url, dates);
	} else {
		//判断是否是1.天猫店铺主页和阿里旅行2.淘宝店铺主页和极有家 3.淘宝自定义页面 4.所有商品 5.单品页面6.美店自定义
		if (((shop_type == "tmall"||shop_type=="alitrip") && small_shop_type == "shop_index") || ((shop_type == "taobao"||shop_type=="jyj") && small_shop_type == "shop_index") || (shop_type == "taobao" && small_shop_type == "pageId") || (small_shop_type == "discount") || ( small_shop_type == "all_cm")||(small_shop_type=="cm_details")||small_shop_type=='custom_page') {
			//if (((1==2)&&(shop_type == "tmall"||shop_type=="alitrip") && small_shop_type == "shop_index") || ((1==2)&&(shop_type == "taobao"||shop_type=="jyj") && small_shop_type == "shop_index") || ((1==2)&&shop_type == "taobao" && small_shop_type == "pageId") || (small_shop_type == "discount") || ( small_shop_type == "all_cm")||(small_shop_type=="cm_details")||small_shop_type=='custom_page') {
			$("#code").hide();
			to_linux_load_one(urls, shop_type, small_shop_type);
		} else {
			$(".url_p").show();
			$("#shop_url").show();
		}
	}
	//do something
}
//个人账户类别确认-符合添加天猫
function to_user_type(userid, urls) {
	$.ajax({
		url: 'ajax/users/user_type.php',
		type: 'post',
		data: {
			userid: userid,
			urls: urls
		},
		success: function(data) {
			results = data;
		},
		error: function() {
			alert("error_type");
		}
	})
}
//判断链接是否符合条件
function to_Judge_url(urls, names) {
	$.ajax({
		url: 'ajax/to_Judge_url.php',
		type: 'post',
		dataType: 'json',
		async: false,
		data: {
			urls: urls,
			names: names
		},
		success: function(data) {
			results = data;
		},
		error: function() {
			alert("判断链接条件出现错误");
		}
	})
	return results;
}
//短链接生成
function to_short_url(shop, a, b) {
	$.ajax({
		//短链接生成
		url: 'ajax/short_url/to_short_url.php',
		type: 'post',
		dataType: 'text',
		async: false,
		data: {
			url: shop,
			a: a,
			b: b
		},
		success: function(data) {
			short_url = data;
		},
		error: function() {
			alert("短链接生成出错");
		}
	})
	return short_url;
}
//短链接验证重复
function to_short_url_repeart(short_url, name, clean_url, userid) {
	$.ajax({
		//短链接生成
		url: 'ajax/short_url/to_short_url_repeart.php',
		type: 'post',
		dataType: 'json',
		async: false,
		data: {
			short_url: short_url,
			name: name,
			clean_url: clean_url,
			userid: userid
		},
		success: function(data) {
			result = data;
		},
		error: function() {
			alert("短链接验证重复出错");
		}
	})
	return result;
}
//信息添加入数据库
function to_wechat_url(name, urls, shop, short_url, shop_type, small_shop_type, clean_url, convert_result, service_ip, userid) {
	$.ajax({
		url: 'ajax/short_url/to_wechat_url.php',
		type: 'post',
		dataType: 'json',
		async: false,
		data: {
			name: name,
			url_before: urls,
			url_after: shop,
			short_url: short_url,
			shop_type: shop_type,
			small_shop_type: small_shop_type,
			clean_url: clean_url,
			convert_result: convert_result,
			service_ip: service_ip,
			userid: userid
		},
		success: function(data) {
			result = data;
		},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-02', {
				icon: 2
			});
		}
	})
	return result;
}
//转换失败记录存入数据库
function to_fail_wechat_url(name, url_before, convert_result, userid) {
	$.ajax({
		url: 'ajax/short_url/to_fail_wechat_url.php',
		type: 'post',
		dataType: 'text',
		async: false,
		data: {
			name: name,
			url_before: url_before,
			convert_result: convert_result,
			userid: userid
		},
		success: function(data) {
			result = data;
		},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-03', {
				icon: 2
			});
		}
	})
}
//解决二维码中文乱码
function toUtf8(str) {
	var out, i, len, c;
	out = "";
	len = str.length;
	for (i = 0; i < len; i++) {
		c = str.charCodeAt(i);
		if ((c >= 0x0001) && (c <= 0x007F)) {
			out += str.charAt(i);
		} else if (c > 0x07FF) {
			out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
			out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
			out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		} else {
			out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
			out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		}
	}
	return out;
}
//新浪微博短链接生成
function to_weibo_url(short_url) {
	$.ajax({
		url: 'ajax/short_url/to_weibo_url.php',
		type: 'post',
		dataType: 'text',
		async: false,
		data: {
			short_url: short_url
		},
		success: function(data) {
			result = data;
		},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-04', {
				icon: 2
			});
		}
	})
	return result;
}
//新浪短链接添加入数据库
function to_wechat_weibo_url(short_url, weibo_short_url, userid) {
	short_url = $.trim(short_url);
	weibo_short_url = $.trim(weibo_short_url);
	$.ajax({
		url: 'ajax/short_url/to_weibo_url_db.php',
		type: 'post',
		dataType: 'text',
		async: false,
		data: {
			short_url: short_url,
			weibo_short_url: weibo_short_url,
			userid: userid
		},
		success: function(data) {
			result = data;
		},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-05', {
				icon: 2
			});
		}
	})
}
//百度短链接生成
function to_baidu_url(short_url) {
	$.ajax({
		url: 'ajax/short_url/to_baidu_url.php',
		type: 'post',
		dataType: 'text',
		async: false,
		data: {
			short_url: short_url
		},
		success: function(data) {
			result = data;
		},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-06', {
				icon: 2
			});
		}
	})
	return result;
}
//百度短链接添加入数据库
function to_wechat_baidu_url(short_url, baidu_short_url) {
	short_url = $.trim(short_url);
	baidu_short_url = $.trim(baidu_short_url);
	$.ajax({
		url: 'ajax/short_url/to_baidu_url_db.php',
		type: 'post',
		dataType: 'text',
		async: false,
		data: {
			short_url: short_url,
			baidu_short_url: baidu_short_url
		},
		success: function(data) {
			result = data;
		},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-07', {
				icon: 2
			});
		}
	})
}
//生成6位随机数
function MathRand() {
	var Num = "";
	for (var i = 0; i < 6; i++) {
		Num += Math.floor(Math.random() * 10);
	}
	return Num;
}
//获取当前时间
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
	return currentdate;
}
//点击转换-统计代码
function loginBt_click() {
	$.ajax({
		url: 'Statistics/loginBt_click.php',
		type: 'post',
		async: false,
		success: function(data) {},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-08', {
				icon: 2
			});
		}
	})
}
//模拟访问一次-1.天猫店铺主页2.淘宝店铺主页 3.淘宝自定义页面4.优惠券页面
function to_linux_load_one(urls, type, small_shop_type) {
	
	$.ajax({
		url: 'ajax/linux_load/to_linux_load_one.php',
		type: 'post',
		dataType: 'json',
		data: {
			urls: urls,
			type: type,
			small_shop_type: small_shop_type
		},
		success: function(data) {
			
			if(data["rs"]=="no"){
			    			 layer.alert('网络连接异常,转换失败,请稍后再试', {icon: 2}); 
			    		}
		},
		beforeSend: function() {
			$(".url_p").hide();
			$("#shop_url").hide();
			$("#loading").show();
		},
		complete: function() {
			// Handle the complete event
			$("#code").show();
			$("#loading").hide();
			$(".url_p").show();
			$("#shop_url").show();
		},
		error: function() {
			 layer.alert('网络连接异常,转换失败,请稍后再试', {icon: 2});
		}
	})
}
//获取服务器ip
function to_service_ip(shop_type) {
	var rs = "";
	$.ajax({
		url: 'ajax/short_url/to_service_ip.php',
		type: 'post',
		dataType: 'text',
		async: false,
		data: {
			shop_type: shop_type
		},
		success: function(data) {
			rs = data;
		},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-10', {
				icon: 2
			});
		}
	})
	return rs;
}
//新浪短链接出现错误,日志记录
function to_weibo_log(url_short, result_url, dates) {
	$.ajax({
		url: 'ajax/to_weibo_log.php',
		type: 'post',
		dataType: 'text',
		data: {
			url_short: url_short,
			result_url: result_url,
			dates: dates
		},
		success: function(data) {},
		error: function() {
			layer.alert('网络连接异常，请稍后再试-11', {
				icon: 2
			});
		}
	})
	
}
//二维码生成
	 	function url_crode(result_url){
	 		if(navigator.appName.indexOf("Microsoft Internet Explorer")!=-1){
				//IE浏览器
				$("#code").qrcode({ 
					           		render   : "table",//设置渲染方式  
					           		width       : 200,     //设置宽度  
					           		height      : 200,     //设置高度  
					           	    text: result_url //任意内容 
					           	});
			}else{
				$("#code").qrcode({ 
					           		render   : "canvas",//设置渲染方式  
					           		width       : 200,     //设置宽度  
					           		height      : 200,     //设置高度  
					           	    text: result_url //任意内容 
					           	});       
			}
	 		
	 							
	 	}</script>
			<script type="text/javascript">//上报测速 --dom加载完成点
window._points && (window._points[2] = +new Date());</script>
			<script type="text/javascript">points[2] = +new Date() - points[0]; //dom加载完成</script>
			<script onerror="wx_loaderror(this)" type="text/javascript"
			src="js/html5Report218877.js">
			</script>
			<script type="text/javascript">points[3] = +new Date() - points[0]; //js加载完成
jQuery(function() {
	points[4] = +new Date() - points[0]; //onready
});
window.onload = function() {
	points[5] = +new Date() - points[0]; //onload
	var url = ["flag1=7839&flag2=4&flag3=1", "1=" + points[1], "2=" + points[2], "3=" + points[3], "4=" + points[4], "5=" + points[5]].join("&");
	if (window.location.href.indexOf("https") > -1) {
		var img = new Image();
	} else {
		//console.log(url);
	}
	if (html5Report && typeof html5Report === "function") {
		html5Report([7839, 4, 4], [
			points[1] + points[0],
			points[2] + points[0],
			points[3] + points[0],
			points[4] + points[0],
			points[5] + points[0]
		]);
	}
}</script>
			<br />
			<br />
			<br />
		</div>
	</body>
</html>