package com.citytuike.constant;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016091800537142";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "nOXJ/qJu/jhRGj72gHBg5A==";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA54nvVAIQdcdpwDfJc2R+uWW75hHt4FAEPgTg53EC8fn0wsoY38Cs5I5Kwrmzypu2SoMYNqRZWiYmlBjqFrC9/kDbulvotZIfEan9pgYNDxIpRTJ7vPs0h2u+LFuu3dt7R9BR3i2bkjiCKKjDxQ2XWlnn4BSf356etwTkTZTgqXNo7ogE5ML0NHZoZyeXvSsHWjTZ/LbSy/pMlVV4XU48uxYEPSzw1pJ7U/S2qy116iEk4FZTuNoYZbDfedfWr/a9yzG7AKqMvpRZJKnzZvMuqElAE+3h4NX0iYH7uXvhAvdkahRpUoevjXoRdWeROH/Fl/7WBy8Qb6o3Q7yr45mYLwIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
