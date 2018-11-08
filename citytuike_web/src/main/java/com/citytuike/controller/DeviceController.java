package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.iot.model.v20180120.QueryDeviceDetailResponse;
import com.citytuike.constant.Constant;
import com.citytuike.exception.WeixinApiException;
import com.citytuike.interceptor.JpushClientUtil;
import com.citytuike.model.*;
import com.citytuike.service.*;
import com.citytuike.util.AliyunIotApi;
import com.citytuike.util.RedisUtil;
import com.citytuike.util.WeixinAPI;
import com.swetake.util.Qrcode;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("api/Iot")
public class DeviceController extends BaseController{
    @Autowired
    private ITpDeviceService tpDeviceService;
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpDeviceLogService deviceLogService;
    @Autowired
    private TpRegionService tpRegionService;
    @Autowired
    private TpDeviceQrService tpDeviceQrService;
    @Autowired
    private TpWxUserService tpWxUserService;
    @Autowired
    private TpScanLogService tpScanLogService;
    @Autowired
    private TpReplacementPartsService  tpReplacementPartsService;
    @Autowired
    private TpAppVersionService tpAppVersionService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TpOrderService tpOrderService;
    @Autowired
    private TpMessageService tpMessageService;
    @Autowired
    private TpAdService tpAdService;

    /**
     * @return 获取设备配置
     */
    @RequestMapping(value = "getConf", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "获取设备配置", notes = "获取设备配置")
    public @ResponseBody String getConf(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String deviceSn = jsonRequest.getString("deviceSn");
        if(deviceSn == null || deviceSn.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpDevice tpDevice = tpDeviceService.findDeviceBySn(deviceSn);
        if (null != tpDevice){
            //验证机器是否有限制，将限制缓存到redis
            if (null != tpDevice.getLimitConfig()){
                JSONObject jsonObject = JSONObject.parseObject(tpDevice.getLimitConfig());
                //使用多久之后开始限制
                String use_time = jsonObject.getString("use_time");
                String[] use_time_arr = use_time.split("-");
                Random random = new Random();
                int s = random.nextInt(Integer.parseInt(use_time_arr[1]))%(Integer.parseInt(use_time_arr[1])-Integer.parseInt(use_time_arr[0])+1) + Integer.parseInt(use_time_arr[0]);
                long time_start = Calendar.getInstance().getTimeInMillis() + s*60  + random.nextInt(59)%60;
                // 限制的时长
               String limit_time = jsonObject.getString("limit_time");
                String[] limit_time_arr = use_time.split("-");
                int ss = random.nextInt(Integer.parseInt(use_time_arr[1]))%(Integer.parseInt(limit_time_arr[1])-Integer.parseInt(limit_time_arr[0])+1) + Integer.parseInt(limit_time_arr[0]);
                long time_end = time_start + ss;

                String limit_type = jsonObject.getString("type");
                long cache_times = time_end - Calendar.getInstance().getTimeInMillis();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("start", time_start);
                jsonObject1.put("end_time", time_end);
                jsonObject1.put("limit_type", time_start);
                RedisUtil.valueSet("\""+ Constant.DEVICE_LIMIT_CONFIG_ + deviceSn + "\"", jsonObject1);
                List<TpAdPosition> tpAdPositionList = tpAdService.findAdPositionByDeviceId(tpDevice.getId());
                JSONArray jsonArray = new JSONArray();
                for (TpAdPosition tpAdPosition : tpAdPositionList) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("position_id", tpAdPosition.getPositionId());
                    jsonObject2.put("position_name", tpAdPosition.getPositionName());
                    jsonObject2.put("ad_width", tpAdPosition.getAdWidth());
                    jsonObject2.put("ad_height", tpAdPosition.getAdHeight());
                    jsonObject2.put("position_desc", tpAdPosition.getPositionDesc());
                    jsonObject2.put("is_open", tpAdPosition.getIsOpen());
                    jsonObject2.put("region_id", tpAdPosition.getRegionId());
                    jsonObject2.put("address", tpAdPosition.getAddress());
                    jsonObject2.put("device_id", tpAdPosition.getDeviceId());
                    jsonObject2.put("position_style", tpAdPosition.getPositionStyle());
                    jsonArray.add(jsonObject2);
                }
                data.put("ProductKey", tpDevice.getProductKey());
                data.put("DeviceName", tpDevice.getDeviceName());
                data.put("deviceSecret", tpDevice.getDeviceSecret());
                data.put("no_send", tpDevice.getNoSend());
                data.put("positionList", jsonArray);
            }
            TpDevicePlay tpDevicePlay = tpDeviceService.findDevicePlayByDeviceId(tpDevice.getId());
            if (null != tpDevicePlay){
                int updataDeviceByRegionId = tpDeviceService.updataDeviceByRegionId(tpDevice.getId(), tpDevice.getDistrict());
                if (updataDeviceByRegionId > 0){
                    jsonObj.put("status", 1);
                    jsonObj.put("msg", "成功!");
                    jsonObj.put("result", data);
                }
            }
        }
//        JSONObject jsonObject = tpDeviceService.selectDeviceBySn(deviceSn);
        return jsonObj.toString();
    }

    /**
     * @return 激活设备
     */
    @RequestMapping(value = "active", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "激活设备", notes = "激活设备")
    public @ResponseBody String active  (HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String device_sn = jsonRequest.getString("device_sn");
        Integer province = jsonRequest.getInteger("province");
        String city = jsonRequest.getString("city");
        String district = jsonRequest.getString("district");
        String landmark_picture = jsonRequest.getString("landmark_picture");
        if(device_sn == null || device_sn.equals("") || province == null || city == null || city.equals("") || district == null ||
           district.equals("") || landmark_picture == null || landmark_picture.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpDevice tpDevice = tpDeviceService.selectDevice(tpUsers.getUser_id(), device_sn);
        if(tpDevice == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "错误的设备号!");
            return jsonObj.toString();
        }else if(tpDevice.getOrderId() == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "请求错误-未知订单，请联系客服处理!");
            return jsonObj.toString();
        }else if(tpDevice.getIsActive().equals(Byte.valueOf("1"))){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "设备号已激活，不能重复激活");
            return jsonObj.toString();
        }
        Boolean check_active_code = true;
        if(check_active_code){
            int  i = tpDeviceService.update(tpDevice.getId(), String.valueOf(province),city,district,landmark_picture);
            if(i>0){
                tpOrderService.updateOrder(tpDevice.getOrderId());
                redisTemplate.opsForValue().set("d_active_my_list",tpUsers.getUser_id());
                jsonObj.put("status", 1);
                jsonObj.put("msg", "设备激活成功!");
            }
        }else{
            jsonObj.put("status", 0);
            jsonObj.put("msg", "激活码错误!");
        }
        return jsonObj.toString();
    }

    /**
     * @return 团队机器
     */
    @RequestMapping(value = "team_device", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "团队机器", notes = "团队机器")
    public @ResponseBody String TeamDevice(HttpServletRequest request) {
        JSONObject jsonRequest = getRequestJson(request);
        String page = jsonRequest.getString("page");
        String type = jsonRequest.getString("type");
        JSONObject jsonObj = new JSONObject();
        if(page == null || page.equals("") || type == null || type.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("device_num", tpDeviceService.selectCountDevice(tpUsers.getUser_id()));
        jsonObject1.put("income", tpUsersService.getSumMoneyDevice(tpUsers.getUser_id()));
        Integer level = 1;
        if(type.equals("1")){
            tpUsers = tpUsersService.selectLevel(level,type);
        }else if(type.equals("2")){
            tpUsers = tpUsersService.selectLevel(level,type);
        }
        int beginData = tpUsersService.selectRegTime(tpUsers.getUser_id());
        int i = ((int) new Date().getTime() - beginData) / 1000 / 60 / 60 / 24;
        jsonObject1.put("day_avg_income", tpUsersService.getSumMoneyDevice(tpUsers.getUser_id()).divide(BigDecimal.valueOf((int) i), 10, BigDecimal.ROUND_HALF_DOWN));
        jsonObject1.put("ad_number", 0);
        jsonArray.add(jsonObject1);
        LimitPageList limitPageList = tpUsersService.getLimitPageList(tpUsers.getUser_id(), page);
        List<TpUsers> list = (List<TpUsers>) limitPageList.getList();
        for (TpUsers tpUsers1 : list) {
            JSONObject device = tpUsersService.getUserlJson(tpUsers1);
            jsonArray.add(device);
        }
        data.put("current_page", limitPageList.getPage().getPageNow());
        data.put("total", limitPageList.getPage().getTotalCount());
        data.put("per_page", limitPageList.getPage().getPageSize());
        data.put("last_page", limitPageList.getPage().getTotalPageCount());
        data.put("data", jsonArray);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功");
        jsonObj.put("result", data);
        return jsonObj.toString();
    }

    /**
     * @return 新增机器栏目数据
     */
    @RequestMapping(value = "new_device_number", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "新增机器栏目数据", notes = "新增机器栏目数据")
    public @ResponseBody String NewDeviceNumber(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = deviceLogService.getTodayDevice(tpUsers.getUser_id());
        jsonObj.put("result", jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok");
        return jsonObj.toString();
    }

    /**
     * @return 获取有设备的城市
     */
    @RequestMapping(value = "deviceCityList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取有设备的城市", notes = "获取有设备的城市")
    public @ResponseBody String deviceCityList(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONArray jsonArray = new JSONArray();
        List<TpDevice> tpDevices = tpDeviceService.getHaveDeviceCity();
        for (TpDevice tpDevice : tpDevices) {
            String cityName = tpRegionService.getCityName(tpDevice.getCity());
            jsonArray.add(cityName);
        }
        jsonObj.put("result", jsonArray);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok");
        return jsonObj.toString();
    }
    //二维码生成,首先先获取机器二维码,就是根据参数在device_qr里面插入一条随机数
    //然后就是二维码的生成,参考网上的二维码生成

    /**
     * @return 获取机器二维码-v2
     */
    @RequestMapping(value = "getQrCodeV2", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取机器二维码-v2", notes = "获取机器二维码-v2")
    public @ResponseBody
    String getUserMpQr(HttpServletRequest  request,
                       @RequestParam(required = true,defaultValue = "a1njSyrGdTz") String ProductKey,
                       @RequestParam(required = true,defaultValue = "device_lMWz") String DeviceName,
                       @RequestParam(required = true) String latitude,
                       @RequestParam(required = true) String longitude) {
        JSONObject jsonObj = new JSONObject();
        if(ProductKey == null || ProductKey.equals("") || DeviceName == null || DeviceName.equals("")
           || latitude ==null || latitude.equals("") || longitude == null || longitude.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = new JSONObject();
        TpDevice tpDevice = tpDeviceService.getUserDevice(ProductKey, DeviceName, latitude, longitude);
        if (tpDevice == null) {
            jsonObj.put("status", 0);
            jsonObj.put("msg", "没有该设备");
            return jsonObj.toString();
        } else if (tpDevice.getActiveTime() == 0) {
            jsonObj.put("status", "203");
            jsonObj.put("msg", "该设备没有激活");
            return jsonObj.toString();
        }
        String conent = tpDeviceQrService.saveQR(tpDevice.getUserId(), latitude, longitude);
        jsonObject.put("device_id", tpDevice.getId());
        jsonObject.put("ticket", "");
        jsonObject.put("url", conent);

        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeErrorCorrect('M');//纠错等级（分为L、M、H三个等级）
        qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符
        qrcode.setQrcodeVersion(7);//版本
        //生成二维码中要存储的信息
        String qrData = conent;
        //设置一下二维码的像素
        int width = 67 + 12 * (10 - 1);
        ;
        int height = 67 + 12 * (10 - 1);
        ;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //绘图
        Graphics2D gs = bufferedImage.createGraphics();
        gs.setBackground(Color.WHITE);
        gs.setColor(Color.BLACK);
        gs.clearRect(0, 0, width, height);//清除下画板内容

        //设置下偏移量,如果不加偏移量，有时会导致出错。
        int pixoff = 2;

        byte[] d = new byte[0];
        try {
            d = qrData.getBytes("gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (d.length > 0 && d.length < 120) {
            boolean[][] s = qrcode.calQrcode(d);
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        }
        String str = UUID.randomUUID().toString().substring(0, 32);
        gs.dispose();
        bufferedImage.flush();
        try {
            ImageIO.write(bufferedImage, "jpg", new File("D:/tuike/citytuike_parent/citytuike_web/src/resources/public/upload/device_code/20180819/" + str + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonObj.put("result", jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

    /**
     * @return 获取公众号列表
     */
    @RequestMapping(value = "getMpList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "获取公众号列表", notes = "获取公众号列表")
    public @ResponseBody
    String getMpList(HttpServletRequest request)  {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String scene_str_v2 = jsonRequest.getString("scene_str_v2");
        String lat = jsonRequest.getString("lat");
        String lng = jsonRequest.getString("lng");
        if(scene_str_v2 == null || scene_str_v2.equals("") || lat == null || lat.equals("0")
           || lng == null || lng.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject WXJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        TpDeviceQr tpDeviceQr = tpDeviceQrService.getLatAndLng(scene_str_v2);
        TpDevice tpDevice = tpDeviceService.getDeviceId(tpDeviceQr.getLng(), tpDeviceQr.getLat());
        int status = tpDeviceQrService.selectStatus(scene_str_v2);
        if (status == 1) {
            jsonObj.put("status", 0);
            jsonObj.put("msg", "该二维码已经被使用!");
            return jsonObj.toString();
        }
        if (status == 0) {
            double v = tpDeviceQrService.updateQR(scene_str_v2, lat, lng, status, tpUsers.getUser_id());
            if (v < 10000) {
                List<TpWxUser> tpWxUsers = tpWxUserService.getWxUser();
                for (TpWxUser tpWxUser : tpWxUsers) {
                    String str = UUID.randomUUID().toString().substring(0, 32);
                    String ticket = null;
                    try {
                        ticket = WeixinAPI.getStrQRTicket(str);
                    } catch (WeixinApiException e) {
                        e.printStackTrace();
                    }
                    String conent = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
                    String name = tpWxUser.getWxname();
                    String headerpic = tpWxUser.getHeaderpic();
                    jsonObject.put("name", name);
                    jsonObject.put("headerpic", headerpic);
                    jsonObject.put("qrcode", conent);
                    //这里用Redis缓存Json参数
                    WXJsonObject.put("usr_id", tpUsers.getUser_id());
                    WXJsonObject.put("wx_id", tpWxUser.getWxid());
                    WXJsonObject.put("device_id", tpDevice.getId());
                    WXJsonObject.put("scene", str);
                    //这里用redis存WXJsonObject,作为value,key值用str
                    Qrcode qrcode = new Qrcode();
                    qrcode.setQrcodeErrorCorrect('L');//纠错等级（分为L、M、H三个等级）
                    qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符
                    qrcode.setQrcodeVersion(0);//版本
                    //生成二维码中要存储的信息
                    String qrData = conent;
                    //设置一下二维码的像素
                    int width = 67 + 12 * (10 - 1);
                    int height = 67 + 12 * (10 - 1);
                    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    //绘图
                    Graphics2D gs = bufferedImage.createGraphics();
                    gs.setBackground(Color.WHITE);
                    gs.setColor(Color.BLACK);
                    gs.clearRect(0, 0, width, height);//清除下画板内容

                    //设置下偏移量,如果不加偏移量，有时会导致出错。
                    int pixoff = 2;

                    byte[] d = new byte[0];
                    try {
                        d = qrData.getBytes("gb2312");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (d.length > 0 && d.length < 300) {
                        boolean[][] s = qrcode.calQrcode(d);
                        for (int i = 0; i < s.length; i++) {
                            for (int j = 0; j < s.length; j++) {
                                if (s[j][i]) {
                                    gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                                }
                            }
                        }
                    }
                    gs.dispose();
                    bufferedImage.flush();
                    try {
                        ImageIO.write(bufferedImage, "jpg", new File("D:/tuike/citytuike_parent/citytuike_web/src/resources/public/upload/device_code/20180819/" + str + ".jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                jsonObj.put("status", 0);
                jsonObj.put("msg", "请在机器旁边进行扫码");
                return jsonObj.toString();
            }
        }
        jsonObj.put("result", jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

    /**
     * @return 关注公众号后回到前端页面--检查paper_token是否有效
     */
    @RequestMapping(value = "checkPaperToken", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "关注公众号后回到前端页面", notes = "关注公众号后回到前端页面")
    public @ResponseBody String checkPaperToken(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String paper_token = jsonRequest.getString("paper_token");
        if(paper_token == null || paper_token.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        String str = (String) redisTemplate.opsForValue().get(paper_token);
        if(str == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "错误的场景码");
            return jsonObj.toString();
        }
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

    /**
     * 使用paper_token领取纸巾
     */
    @RequestMapping(value = "getPaperWx", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "使用paper_token领取纸巾", notes = "使用paper_token领取纸巾")
    public @ResponseBody String getPaperWx(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String paper_token = jsonRequest.getString("paper_token");
        if(paper_token == null || paper_token.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        List<TpScanLog> tpScanLogs = tpScanLogService.findAlltpScanLogService();
        String data = (String) redisTemplate.opsForValue().get(paper_token);
        if(data == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "错误的场景码或者您已领取");
            return jsonObj.toString();
        }else if(data.length() == 0){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "错误的场景码2");
            return jsonObj.toString();
        }
        for(int i=0;i<tpScanLogs.size();i++){

            JSONObject json = new JSONObject();
            json.put("id", tpScanLogs.get(i).getId());
            json.put("user_id", tpScanLogs.get(i).getUser_id());
            boolean is_test = false;
            if(tpScanLogs.get(i).getId() > 0 ){
                TpScanLog tpScanLog = tpScanLogService.getStutas(tpScanLogs.get(i).getId());
                if(tpScanLog.getStatus() == true){
                    jsonObj.put("status", 0);
                    jsonObj.put("msg", "错误的场景码4");
                    return jsonObj.toString();
                }
                tpScanLogService.update(tpScanLogs.get(i).getId());
            }
        is_test=true;
        JSONObject jsonObject= tpScanLogService.sendPaperWx(tpScanLogs.get(i).getId(),tpScanLogs.get(i).getDevice_id(),paper_token,is_test);
        jsonObj.put("result",jsonObject);
        }
        return jsonObj.toString();
    }
    /**
     * @return 机器纸巾情况上报
     */
    @RequestMapping(value = "lack_paper", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "机器纸巾情况上报", notes = "机器纸巾情况上报")
    public @ResponseBody String lack_paper(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String type = jsonRequest.getString("type");
        String ProductKey = jsonRequest.getString("ProductKey");
        String DeviceName = jsonRequest.getString("DeviceName");
        if(ProductKey == null || ProductKey.equals("") || DeviceName == null || DeviceName.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject object = new JSONObject();
        TpDevice tpDevice = tpDeviceService.getDevice(ProductKey,DeviceName);
        if(tpDevice == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "机器不存在");
            return jsonObj.toString();
        }
        tpDevice.setLackPaper(Integer.parseInt(type));
        tpDeviceService.updateType(tpDevice);
        if(type.equals("1")){
            Map<String, String> parm =new HashMap<String, String>();
            jsonObject.put("title","缺纸消息");
            jsonObject.put("discription","当前设备缺纸，请尽快补充");
            jsonObject.put("device_sn",tpDevice.getDeviceSn());
            jsonObject.put("paper_inventory",tpDevice.getPaperInventory());
            jsonObject.put("address",tpDevice.getAddress());
            jsonObject.put("longitude",tpDevice.getLatitude());
            jsonObject.put("latitude",tpDevice.getLatitude());
            parm.put("RegId", String.valueOf(tpDevice.getUserId()));
            parm.put("msg",jsonObject.toString());
            object.put("category",12);
            object.put("type",0);
            jsonObj.put("table",object.toString());
            jsonObj.put("msg",jsonObject);
            JpushClientUtil.testSendPush(parm);
            tpMessageService.save(jsonObj);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "成功");
        }
        return jsonObj.toString();
    }
    /**
     * @return 申请更换配件
     */
    @RequestMapping(value = "replacement_parts", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "申请更换配件", notes = "申请更换配件")
    public @ResponseBody String replacement_parts(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String device_id = jsonRequest.getString("device_id");
        String name = jsonRequest.getString("name");
        String reason = jsonRequest.getString("reason");
        String files = jsonRequest.getString("files");
        String address = jsonRequest.getString("address");
        if(device_id == null || device_id.equals("") || name == null || name.equals("") || reason == null || reason.equals("") || files == null || files.equals("") || address == null || address.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        if(device_id != null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "请选择设备");
            return jsonObj.toString();
       }
       TpDevice tpDevice = tpDeviceService.getDeviceById(device_id);
       if(tpDevice.getUserId() == 0){
           jsonObj.put("status", 0);
           jsonObj.put("msg", "请选择正确的设备");
           return jsonObj.toString();
       }
        tpReplacementPartsService.insertReplacement(tpDevice,name,reason,files,address);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
        /**
         * @return 更换配件列表-v2
         */
    @RequestMapping(value = "replacement_parts_list_v2", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "更换配件列表-v2", notes = "更换配件列表-v2")
    public @ResponseBody String replacement_parts_list_v2(HttpServletRequest request,
                                                          @RequestParam(required = true)Integer type,
                                                          @RequestParam(required = true)String pageNo,
                                                          @RequestParam(required = true)String pageSize){
        JSONObject jsonObj = new JSONObject();
        if(type == null || pageNo ==null || pageNo.equals("") || pageSize ==null || pageSize.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        LimitPageList limitPageList = tpReplacementPartsService.getLimitPageList(type,pageNo,pageSize);
        List<TpReplacementParts> tpReplacementParts = (List<TpReplacementParts>) limitPageList.getList();
        for (TpReplacementParts tpReplacementPart : tpReplacementParts) {
            JSONObject jsonObject = tpReplacementPartsService.getJson(tpReplacementPart,type);
            jsonArray.add(jsonObject);
        }
        data.put("current_page", limitPageList.getPage().getPageNow());
        data.put("total", limitPageList.getPage().getTotalCount());
        data.put("per_page", limitPageList.getPage().getPageSize());
        data.put("last_page", limitPageList.getPage().getTotalPageCount());
        data.put("data",jsonArray);
        jsonObj.put("result",data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 设备进入测试环境
     */
    @RequestMapping(value = "change_run_status", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "设备进入测试环境", notes = "设备进入测试环境")
    public @ResponseBody String change_run_status(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String id = jsonRequest.getString("id");
        String run_status = jsonRequest.getString("run_status");
        if(id == null || id.equals("") || run_status==null || run_status.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpDevice tpDevice  = tpDeviceService.getDeviceById(id);
        if(tpDevice != null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "错误的设备");
            return jsonObj.toString();
        }
        tpDevice.setRunStatus(Integer.parseInt(run_status));
        tpDeviceService.updateRunStatus(tpDevice);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 设备详情
     */
    @RequestMapping(value = "device_info", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "设备详情", notes = "设备详情")
    public @ResponseBody String device_info(HttpServletRequest request,
                                            @RequestParam(required = true) String id){
        JSONObject jsonObj = new JSONObject();
        if(id == null || id.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = new JSONObject();
        TpDevice tpDevice = tpDeviceService.getDeviceById(id);
        String online_status = "";
        if(tpDevice == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "错误的设备");
            return jsonObj.toString();
        }
        AliyunIotApi aliyunIotApi = new AliyunIotApi();
        QueryDeviceDetailResponse pubResponse = aliyunIotApi.queryDeviceDetailRequest(tpDevice.getProductKey(), tpDevice.getDeviceName());
        String status = pubResponse.getData().getStatus();
        if(status.equals("ONLINE")){
            tpDevice.setOnline(true);
            tpDeviceService.updateRunStatus(tpDevice);
            online_status = (String) redisTemplate.opsForValue().get("device_online_status_"+id);
            if(online_status != null){
                Random random = new Random();
                for (int i = 70; i <85 ; i++) {
                    online_status = String.valueOf((random.nextInt(9)+1));
                    redisTemplate.opsForValue().set("device_online_status_"+id, online_status, 15*60);
                }
            }
            }else{
                tpDevice.setOnline(false);
                tpDeviceService.updateRunStatus(tpDevice);
                online_status = "离线";
        }
            String cityName = tpRegionService.getCityName(tpDevice.getCity());
            String province =tpRegionService.getProvince(tpDevice.getProvince());
            String district =tpRegionService.getDistrict(tpDevice.getDistrict());
            jsonObject.put("online_time",pubResponse.getData().getGmtOnline());
            jsonObject.put("status",online_status);
            jsonObject.put("imei",tpDevice.getDeviceSn());
            TpUsers tpUser = tpUsersService.findOneByUserId(tpDevice.getUserId());
            jsonObject.put("name",tpUser.getNickname());
            jsonObject.put("region",province+cityName+district);
            jsonObject.put("paper","正常");
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok");
        return jsonObj.toString();
    }
    /**
     * @return 版本检测
     */
    @RequestMapping(value = "version", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "版本检测", notes = "版本检测")
    public @ResponseBody String version(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpAppVersion tpAppVersion = tpAppVersionService.getVersion();
        JSONObject jsonObject =new JSONObject();
        TpAppVersion version = new TpAppVersion();
        if(tpAppVersion == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "没有版本");
            return jsonObj.toString();
        }
        //TODO
        String[]imeis = new String[]{};
        String imei = request.getParameter("imei");
        String get_version = request.getParameter("version");
        String imei_test = (String) redisTemplate.opsForValue().get("imei_test");
        if(imei_test != null){
            String imei_str = imei_test.replace("\r\n","<br />");
            imeis = imei_str.split("<br />");
        }else{
            imeis=null;
        }
        if(imei !=null && imeis != null){
            version = tpAppVersionService.getVersion();
            jsonObject = tpAppVersionService.getJson(version);
        }else{
            version = tpAppVersionService.getNewVersion();
            jsonObject = tpAppVersionService.getJson(version);
        }
        if(imei != null && get_version != null){
            tpDeviceService.updateVersion(get_version,imei);
        }
        jsonObj.put("",jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok");
        return jsonObj.toString();
    }
    /**
     * @return 领完纸后，等待2到3秒，查询出纸情况
     */
    @RequestMapping(value = "queryResult", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "领完纸后，等待2到3秒，查询出纸情况", notes = "领完纸后，等待2到3秒，查询出纸情况")
    public @ResponseBody String queryResult(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String paper_token = jsonRequest.getString("paper_token");
        if(paper_token == null || paper_token.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        String data = (String) redisTemplate.opsForValue().get(paper_token);
        org.json.JSONObject object = new org.json.JSONObject(data);
        if(data == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "错误场景码");
        }else{
            String status = (String) object.get("status");
            if (status.equals("1") ){
                jsonObj.put("status", 1);
                jsonObj.put("msg", "ok");
            }else if(status.equals("2")){
                jsonObj.put("status", 2);
                jsonObj.put("msg", "出纸失败");
            }else if(status.equals("3")){
                jsonObj.put("status", 3);
                jsonObj.put("msg", "等待回调");
            }
        }
        return jsonObj.toString();
    }

    //TODO 13. 获取可关注公众号列表
    //TODO 14. 关注公众号后回到前端页面--检查paper_token是否有效
    //TODO 17. 机器出纸回调
    //TODO 24. 修改机器别名
    //TODO 25. 区域机器数量--省份
    //TODO 26. 区域机器数量-市
    //TODO 27. 机器当前纸巾库存
}
