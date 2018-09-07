package com.citytuike.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


import com.citytuike.constant.Constant;
import com.citytuike.exception.AccessTokenException;
import com.citytuike.exception.PermissionException;
import com.citytuike.exception.WeixinApiException;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUsersService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


public class WeixinAPI {
	
	@Autowired
	private TpUsersService tpUsersService;
	protected String appid = "";
	protected String appsecret = "";
	protected static Map<String, JSONObject> tokenStock = new HashMap<String, JSONObject>();
	protected static Map<String, JSONObject> webTokenStock = new HashMap<String, JSONObject>();
	protected static Map<String, JSONObject> jsTokenStock = new HashMap<String, JSONObject>();

	/**
	 * 以GET方式访问指定地址，获取JSON
	 * @return
	 * @throws WeixinApiException 
	 */
	protected JSONObject jsonGet(String urls) throws WeixinApiException {

		JSONObject jsonObject = null;

		// HTTP
		try {
	    	// Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        } };
	        // Install the all-trusting trust manager
	        final SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

	        URL url = new URL(urls);
	        URLConnection con = url.openConnection();

	        final Reader reader = new InputStreamReader(con.getInputStream(),"utf-8");
	        final BufferedReader br = new BufferedReader(reader);        
	        
	        StringBuffer sb = new StringBuffer();
	        String line = "";
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }        
	        br.close();
	        
	        jsonObject = new JSONObject( sb.toString() );
			if (jsonObject.has("errcode") && jsonObject.getInt("errcode") > 0) {
				if(jsonObject.getInt("errcode") == 40001){
					this.refreshAccessToken();
				}
				
				throw new PermissionException(jsonObject.getString("errmsg"));
			}
		} catch (JSONException e) {
			System.out.println("微信API数据格式错误！");
			throw new WeixinApiException("微信API数据格式错误！");
		} catch (Exception e) {
			System.out.println("微信API通信错误！");
			throw new WeixinApiException("微信API通信错误！");
		}

		return jsonObject;

	}

	public WeixinAPI(String appid, String appsecret) {
		super();
		this.appid = appid;
		this.appsecret = appsecret;
	}
	
	/**
	 * 提交JSON数据，返回JSON数据
	 * @param text
	 * @return
	 */
	protected JSONObject jsonPost(String urls, String text) throws WeixinApiException {

		JSONObject jsonObject = null;

		try {
			
	    	// Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        } };
	        // Install the all-trusting trust manager
	        final SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

	        URL url = new URL(urls);
	        URLConnection con = url.openConnection();
	        con.setDoOutput(true);
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"utf-8"));
	        writer.write(text);
	        writer.close();
	        final Reader reader = new InputStreamReader(con.getInputStream(), "utf-8");
	        final BufferedReader br = new BufferedReader(reader);        
	        
	        StringBuffer sb = new StringBuffer();
	        String line = "";
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }        
	        br.close();
	        
			jsonObject = new JSONObject( sb.toString() );
			if (jsonObject.has("errcode") && jsonObject.getInt("errcode") > 0) {
				if(jsonObject.getInt("errcode") == 40001){
					this.refreshAccessToken();
				}
				
				//throw new PermissionException(jsonObject.getString("errmsg"));
			}
		} catch (JSONException e) {
			throw new WeixinApiException(e.getMessage());
		} catch (Exception e) {
			throw new WeixinApiException(e.getMessage());
		}
		return jsonObject;
	}
	
	/**
	 * 提交JSON数据，返回JSON数据
	 * @param text
	 * @return
	 */
	protected XMLReturn xmlPost(String urls, String text) throws WeixinApiException {

		try {
			
	    	// Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        } };
	        // Install the all-trusting trust manager
	        final SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

	        URL url = new URL(urls);
	        URLConnection con = url.openConnection();
	        con.setDoOutput(true);
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"utf-8"));
	        writer.write(text);
	        writer.close();
	        final Reader reader = new InputStreamReader(con.getInputStream(),"utf-8");

	        Unmarshaller um = JAXBContext.newInstance(XMLReturn.class).createUnmarshaller();
	        return (XMLReturn)um.unmarshal( reader );
	        
		} catch (Exception e) {
			throw new WeixinApiException(e.getMessage());
		}
	}

	protected JSONObject newToken() throws WeixinApiException {
		StringBuffer url = new StringBuffer();
		url.append(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=")
				.append(appid).append("&secret=").append(appsecret);
		return this.jsonGet(url.toString());

	}

	public String getAccessToken() throws WeixinApiException {
		/*if(Config.USE_REMOTE_ACCESS_TOKEN){
			return getRemoteAccessToken();
		}*/
		try {
			JSONObject access_token = tokenStock.get(appid);
			if (access_token != null && access_token.has("expires")	&& access_token.getLong("expires") > System.currentTimeMillis()) {
				System.out.println("old token is " + access_token.getString("access_token"));
				return access_token.getString("access_token");
			} else {
				JSONObject newtoken = newToken();
				System.out.println("new token is " + newtoken.getString("access_token"));
				if (newtoken.has("expires_in") && newtoken.getInt("expires_in") > 0) {
					newtoken.put("expires", System.currentTimeMillis() + newtoken.getLong("expires_in")*1000);
					tokenStock.put(this.appid, newtoken);
					return newtoken.getString("access_token");
				} else {
					throw new AccessTokenException("Can't get access_token");
				}
			}
		} catch (JSONException e) {
			throw new AccessTokenException(e);
		}
	}
	
	protected JSONObject newTicket() throws WeixinApiException {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+getAccessToken()+"&type=jsapi");
		
		return this.jsonGet(url.toString());

	}
	/**
	 * 生成jsApiTicket
	 * @return
	 * @throws WeixinApiException 
	 */
	public String getJsApiTicket() throws WeixinApiException {
		//检测缓存是否有ticket
		try {
			JSONObject access_token = tokenStock.get("ticket");
			if (access_token != null && access_token.has("expires")	&& access_token.getLong("expires") > System.currentTimeMillis()) {
				System.out.println("old ticket is " + access_token.getString("ticket"));
				return access_token.getString("ticket");
			} else {
				JSONObject ticket = newTicket();
				System.out.println("new ticket is " + ticket.getString("ticket"));
				if (ticket.has("expires_in") && ticket.getInt("expires_in") > 0) {
					ticket.put("expires", System.currentTimeMillis() + ticket.getLong("expires_in")*1000);
					tokenStock.put("ticket", ticket);
					return ticket.getString("ticket");
				} else {
					throw new AccessTokenException("Can't get jsApiTicket");
				}
			}
		} catch (JSONException e) {
			throw new AccessTokenException(e);
		}
	}
	
	public String getRemoteAccessToken(){
		return getRemoteAccessToken( false );
	}
	public String getRemoteAccessToken(boolean refresh) {
		StringBuffer sb = new StringBuffer(Constant.REMOTE_ACCESS_TOKEN_URL);
		sb.append("appid=").append(appid).append("&key=").append(Constant.REMOTE_ACCESS_TOKEN_KEY);
		if(refresh){
			sb.append("&refresh=true");
		}

		try {
			URL url = new URL(sb.toString());
			URLConnection con = url.openConnection();

			final Reader reader = new InputStreamReader(con.getInputStream(), "utf-8");
			final BufferedReader br = new BufferedReader(reader);

			StringBuffer sb2 = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb2.append(line);
			}
			br.close();

			return sb2.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected void refreshAccessToken() throws WeixinApiException{
		/*if(Config.USE_REMOTE_ACCESS_TOKEN){
			getRemoteAccessToken( true );
		}*/
		try {
			JSONObject newtoken = newToken();
			if (newtoken.has("expires_in") && newtoken.getInt("expires_in") > 0) {
				newtoken.put("expires", System.currentTimeMillis() + newtoken.getLong("expires_in")*1000);
				tokenStock.put(this.appid, newtoken);
			} else {
				throw new AccessTokenException("Can't get access_token");
			}
		} catch (JSONException e) {
			throw new AccessTokenException(e);
		}
	}

	public JSONObject publishMenu(String menutext) throws WeixinApiException {
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=".concat( getAccessToken() );
		return this.jsonPost( url, menutext);
	}

	/**
	 * 
	 * @param userid
	 * @return 返回JSON格式
	 * 	 {
	*	    "subscribe": 1, 
	*	    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
	*	    "nickname": "Band", 
	*	    "sex": 1, 
	*	    "language": "zh_CN", 
	*	    "city": "广州", 
	*	    "province": "广东", 
	*	    "country": "中国", 
	*	    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0", 
	*	   "subscribe_time": 1382694957
	*	}
	 * @throws AccessTokenException
	 */
	public TpUsers getUser(String userid) throws WeixinApiException {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/user/info?access_token=")
				.append(getAccessToken()).append("&openid=").append(userid);
		TpUsers user = new TpUsers();
		System.out.println("get user info url is " + url.toString());
		JSONObject wxUser = this.jsonGet( url.toString() );
		try{
			String city = wxUser.getString("city");
			String country = wxUser.getString("country");
			String province = wxUser.getString("province");
			if(wxUser.has("city")) user.setCity(1);
			if(wxUser.has("headimgurl")) user.setHead_pic( wxUser.getString( "headimgurl" ));
			if(wxUser.has("unionid")) user.setUnionid( wxUser.getString( "unionid" ));
			if(wxUser.has("nickname")) user.setNickname( wxUser.getString("nickname"));
			if(wxUser.has("province")) user.setProvince(1);
			if(wxUser.has("sex")) user.setSex(wxUser.getInt("sex"));
		}catch(JSONException e){
			return null;
		}
		/*String ticket = getStrQRTicket(userid);
		if (null != ticket && !"".equals(ticket)) {
			user.setQrcode_url("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
		}
		System.out.println("用户二维码为：" + user.getQrcode_url());*/
		return user;
	}
	
	//生成字符串场景二维码
	public String getStrQRTicket(String strScene) throws WeixinApiException{
		try{
			JSONObject param = new JSONObject();
			param.put("action_name", "QR_LIMIT_STR_SCENE");
			JSONObject actioninfo = new JSONObject();
			JSONObject scene = new JSONObject();
			scene.put("scene_str", strScene);
			actioninfo.put("scene", scene);
			param.put("action_info", actioninfo );
			System.out.println("字符串二维码请求参数：" + param.toString());
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=".concat(this.getAccessToken());
			JSONObject rets = this.jsonPost(url, param.toString() );
			System.out.println("ticket is " + rets.getString("ticket"));
			if(rets.has("ticket")){
				return rets.getString("ticket");
			}else{
				return null;
			}
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *
	 * @param appid
	 * @param url
	 * @param scope
	 * @return
	 * use
	 */
	public static String getAuthorizeUrl(String appid, String url, String scope){
		StringBuffer sb = new StringBuffer();
		System.out.println("get url is " + url);
		try {
			sb.append("https://open.weixin.qq.com/connect/oauth2/authorize")
			.append("?appid=").append(appid)
			.append("&redirect_uri=").append(URLEncoder.encode(url, "UTF-8"))
			.append("&response_type=code")
			.append("&scope=").append(scope.toString())
			.append("&state=stat1#wechat_redirect");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * @param code
	 * @return
	 * 	{
	 *	   "access_token":"ACCESS_TOKEN",
	 *	   "expires_in":7200,
	 *	   "refresh_token":"REFRESH_TOKEN",
	 *	   "openid":"OPENID",
	 *	   "scope":"SCOPE"
	 *	}
	 * use
	 * @throws WeixinApiException 
	 */
	public String getWebVisitorOpenIdFromBase(String code, String invite_code) throws WeixinApiException{
			//fetch a new token
			StringBuffer url = new StringBuffer();
			url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
			url.append("appid=").append(this.appid).append("&secret=").append(this.appsecret);
			url.append("&code=").append(code).append("&grant_type=authorization_code");
			JSONObject newtoken = this.jsonGet(url.toString());
			if (newtoken.has("openid") && newtoken.has("refresh_token")) {
				try{
					newtoken.put("expires", System.currentTimeMillis() + newtoken.getLong("expires_in")*1000 - 5);
					webTokenStock.put(newtoken.getString("refresh_token"), newtoken);	
					String openid = newtoken.getString("openid");
					System.out.println("get opentid is " + openid);
					//判断用户是否存在 不存在新创建
					TpUsers tpUsers = tpUsersService.findOneByOpenId(openid);
					if (null ==tpUsers ){
						tpUsers = getUser(openid);
						tpUsers.setEmail("");
						tpUsers.setMobile("");
						tpUsers.setBirthday(0);
						tpUsers.setUser_money(0.00);
						tpUsers.setPay_points(0);
						tpUsers.setAddress_id(0);
						tpUsers.setReg_time((int)new Date().getTime());
						tpUsers.setLast_login((int)new Date().getTime());
						tpUsers.setLast_ip("");
						tpUsers.setMobile_validated(1);
						tpUsers.setEmail_validated(0);
						tpUsers.setMessage_mask(0);
						tpUsers.setPush_id("");
						tpUsers.setPassword("");
						tpUsers.setInvite_code(Util.getBigString(8));
						TpUsers tpUsers2 = tpUsersService.findOneByInvite(invite_code);
						if (null != tpUsers2){
							//绑定上级
							tpUsers.setParent_id(tpUsers2.getUser_id());
							if (null != tpUsers2.getRelation() && !"".equals(tpUsers2.getRelation())){
								tpUsers.setRelation(tpUsers2.getRelation() + "," + tpUsers2.getUser_id());
							}else{
								tpUsers.setRelation(tpUsers2.getUser_id() + "");
							}
						}
						tpUsersService.save(tpUsers);
					}
					return openid ;
				}catch(JSONException e){
					throw new AccessTokenException("Can't get access_token");
				}
			} else {
				throw new AccessTokenException("Can't get access_token");
			}
	}
	
	/**
	 * 微信JS接口授权的config方法。该方法自动缓存上次的JSTicket，过期后自动更新.
	 * @return
	 * @throws WeixinApiException
	 */
	public  Map<String, String> getWXConfig() throws WeixinApiException {
		String url = "http://paper.kamengjinfu.com/api/Wechat/share";
        Map<String, String> ret = new HashMap<String, String>();
		String jsapi_ticket = null;
        String nonce_str = null;
        String timestamp = null;
        StringBuffer string1 = new StringBuffer();
        String signature = "";

		try {
			JSONObject access_token = jsTokenStock.get(appid);
			if (access_token != null && access_token.has("expires")	&& access_token.getLong("expires") > System.currentTimeMillis()) {
				jsapi_ticket = access_token.getString("ticket");
				timestamp = access_token.getString("timestamp");
				nonce_str = access_token.getString("nonce_str");
			} else {
				StringBuffer jsUrl = new StringBuffer()
				.append("https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=")
				.append(this.getAccessToken() );

				JSONObject newtoken = jsonGet(jsUrl.toString());;
				if (newtoken.has("expires_in") && newtoken.getInt("expires_in") > 0) {
					newtoken.put("expires", System.currentTimeMillis() + newtoken.getLong("expires_in")*1000 - 5);
					timestamp = String.valueOf(System.currentTimeMillis()/1000);
					nonce_str = UUID.randomUUID().toString();
					newtoken.put("timestamp", timestamp );
					newtoken.put("nonce_str", nonce_str );
					jsTokenStock.put(this.appid, newtoken);
					jsapi_ticket = newtoken.getString("ticket");

				} else {
					throw new AccessTokenException("Can't get access_token");
				}
			}
		} catch (JSONException e) {
			throw new AccessTokenException(e);
		}

        //注意这里参数名必须全部小写，且必须有序
        string1.append("jsapi_ticket=").append(jsapi_ticket).append("&noncestr=").append(nonce_str)
        	.append("&timestamp=").append( timestamp).append("&url=").append( url);

        signature = SHA1.encrypt(string1.toString());
        ret.put("appid", this.appid);
        ret.put("url", url);
        ret.put("jsapi_ticket", string1.toString());
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
	}
}