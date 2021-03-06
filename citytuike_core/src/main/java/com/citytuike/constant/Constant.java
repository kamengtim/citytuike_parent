package com.citytuike.constant;

public class Constant {
	public static double INVOICE_FREIGHT = 0.16;
	public static String WEIXIN_APPID = "wx59f6c47c4c6f1f77";
	public static String WEIXIN_APPSECRET = "ea5022fad52e675c2f684f4c11adb209";
	public static String WEIXIN_USER = "WEIXIN_USER";
	public static String REMOTE_ACCESS_TOKEN_URL = "";
	public static String REMOTE_ACCESS_TOKEN_KEY = "";
	public static String YOP_PAY_NOTIFYURL = "http://citytuike.ngrok.xiaomiqiu.cn/api/Payment/yop_payback";
	//接口参数
	public static final String[] TRADEORDER = {"parentMerchantNo","merchantNo","orderId","orderAmount","timeoutExpress","requestDate","redirectUrl","notifyUrl","goodsParamExt","paymentParamExt","industryParamExt","memo","riskParamExt","csUrl","fundProcessType","divideDetail","divideNotifyUrl"};
	//支付方式
	public static final String[] CASHIER = {"merchantNo","token","timestamp","directPayType","cardType","userNo","userType","ext"};
	//阿里云营业执照
	public static String OCR_HOST = "http://qyocrbl.market.alicloudapi.com";
	public static String OCR_PATH = "/clouds/ocr/businessLicense";
	public static String OCR_METHOD = "POST";
	public static String OCR_APPCODE = "你自己的AppCode";

	//阿里云天客气
	public static String WEATHER_HOST = "https://jisutqybmf.market.alicloudapi.com";
	public static String WEATHER_PATH = "/weather/query";
	public static String WEATHER_METHOD = "GET";
	public static String WEATHER_APPCODE = "你自己的AppCode";

	//七牛
	public static String QINIU_ACCESSKEY = "你自己的AppCode";
	public static String QINIU_SECRETKEY = "你自己的AppCode";

	//話費充值
	public static String MOBILE_APIKEY = "zra9I277yTKD6yimeV56";
	public static String MOBILE_USERNAME = "23235795";

	//登录令牌
	public static final String CURRENT_USER = "user:";

	//推送类型
	public static final Integer SYSTEMTYYE = 0;

	public static final Integer ACCOUNT_CHANGE_TYPE_STATUS_WAIT_ACT = 4;
	public static final String GROUP_KEY = "chat_group_";
	public static final String DEVICE_LIMIT_CONFIG_ = "device_limit_config_";
}
