package com.citytuike.constant;

public class Constant {
	public static double INVOICE_FREIGHT = 0.16;
	public static String WEIXIN_APPID = "wx59f6c47c4c6f1f77";
	public static String WEIXIN_APPSECRET = "ea5022fad52e675c2f684f4c11adb209";
	public static String WEIXIN_USER = "WEIXIN_USER";
	public static String YOP_PAY_NOTIFYURL = "http://citytuike.ngrok.xiaomiqiu.cn/api/Payment/yop_pay_back";
	//接口参数
	public static final String[] TRADEORDER = {"parentMerchantNo","merchantNo","orderId","orderAmount","timeoutExpress","requestDate","redirectUrl","notifyUrl","goodsParamExt","paymentParamExt","industryParamExt","memo","riskParamExt","csUrl","fundProcessType","divideDetail","divideNotifyUrl"};
	//支付方式
	public static final String[] CASHIER = {"merchantNo","token","timestamp","directPayType","cardType","userNo","userType","ext"};


}
