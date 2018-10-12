package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.configs.UtilConfig;
import com.citytuike.constant.Constant;
import com.citytuike.exception.WeixinApiException;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.Auth;
import com.citytuike.util.Base64Img;
import com.citytuike.util.HttpUtils;
import com.citytuike.util.WeixinAPI;
import com.yeepay.shade.org.apache.http.HttpResponse;
import com.yeepay.shade.org.apache.http.util.EntityUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api")
public class PubliclController extends BaseController{

    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @return
     * @throws WeixinApiException
     */
    @RequestMapping(value="/Wechat/share",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "微信分享SDK", notes = "微信分享SDK")
    public @ResponseBody  String share(HttpServletRequest request) throws WeixinApiException {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        WeixinAPI api = new WeixinAPI(Constant.WEIXIN_APPID, Constant.WEIXIN_APPSECRET);
        Map<String, String> map = api.getWXConfig();
        data.put("appId", map.get("appId"));
        data.put("nonceStr", map.get("nonceStr"));
        data.put("timestamp", map.get("timestamp"));
        data.put("url", map.get("url"));
        data.put("signature", map.get("signature"));
        data.put("rawString", map.get("jsapi_ticket"));

        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }

    /**
     * @param request
     * @return
     * 内容生成二维码
     */
    @RequestMapping(value="/Index/make_qrcode",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "内容生成二维码", notes = "内容生成二维码")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody  String make_qrcode(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String content = jsonO.getString("content");
        if (null == content || "".equals(content)){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        String base64url = UtilConfig.generalQRCode(content);
        jsonObj.put("result", "data:image/png;base64,"+base64url);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }

    /**
     * @param request
     * @param url
     * @return
     * url 转图片
     */
    @RequestMapping(value="/Index/url_to_images",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "url 转图片", notes = "url 转图片")
    public @ResponseBody  String url_to_images(HttpServletRequest request,
                                             @RequestParam(required=true) String url) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        String base64url = Base64Img.GetImageStrFromUrl(url);
        jsonObj.put("result", "data:image/png;base64,"+base64url);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }

    /**
     * @param pic
     * @return
     * 识别营业执照
     */
    @RequestMapping(value="/index/business_license_recognition",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "识别营业执照", notes = "识别营业执照")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody  String business_license_recognition(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String pic = jsonO.getString("pic");
        if (null == pic || "".equals(pic)){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + Constant.OCR_APPCODE);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        String bodys = "{\"imageBase64:" + pic + "\"}";

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = (HttpResponse) HttpUtils.doPost(Constant.OCR_HOST, Constant.OCR_PATH, Constant.OCR_METHOD, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
            String resBody = EntityUtils.toString(response.getEntity());
            data = new JSONObject(Boolean.parseBoolean(resBody));
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }

    /**
     * @param longitude
     * @param latitude
     * @param ip
     * @return
     * 天气预报-阿里
     */
    @RequestMapping(value="/index/getWeatherAli",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "天气预报-阿里", notes = "天气预报-阿里")
    public @ResponseBody  String getWeatherAli(HttpServletRequest request,
                                               @RequestParam(required=false) String longitude,
                                               @RequestParam(required=false) String latitude,
                                               @RequestParam(required=false) String ip) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + Constant.WEATHER_APPCODE);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", ip);
        String location = latitude + "," + longitude;
        querys.put("location", latitude);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = (HttpResponse) HttpUtils.doGet(Constant.WEATHER_HOST, Constant.WEATHER_PATH, Constant.WEATHER_METHOD, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
            String resBody = EntityUtils.toString(response.getEntity());
            data = new JSONObject(Boolean.parseBoolean(resBody));
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }
    @RequestMapping(value="/index/im_status",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "检查用户是否环信在线", notes = "检查用户是否环信在线")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody  String imStatus(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String ids = jsonO.getString("ids");
        if (null == ids || "".equals(ids)){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }

    /**
     * @param domain
     * 七牛上传token
     * @return
     */
    @RequestMapping(value="/Upload/qiniu_token",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "七牛上传token", notes = "七牛上传token")
    public @ResponseBody  String qiniuToken(HttpServletRequest request,
                                               @RequestParam(required=true) String domain) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        Auth auth = Auth.create(Constant.QINIU_ACCESSKEY,Constant.QINIU_SECRETKEY);
        String tokenqiniu = auth.uploadToken(domain);
        data.put("domain", domain);
        data.put("expires", 36000);
        data.put("token", tokenqiniu);
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }
}
