package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.iot.model.v20180120.QueryDeviceDetailResponse;
import com.citytuike.exception.WeixinApiException;
import com.citytuike.model.*;
import com.citytuike.service.*;
import com.citytuike.util.AliyunIotApi;
import com.citytuike.util.WeixinAPI;
import com.sun.xml.internal.ws.client.SenderException;
import com.swetake.util.Qrcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("api/Iot")
public class DeviceController {
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

    /**
     * @return 获取设备配置
     */
    @RequestMapping(value = "getConf", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getConf(@RequestParam(required = true) String deviceSn) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = tpDeviceService.selectDeviceBySn(deviceSn);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功!");
        jsonObj.put("result", jsonObject);
        return jsonObj.toString();
    }

    /**
     * @return 激活设备
     */
    @RequestMapping(value = "active", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getConf(@RequestParam(required = true) String token,
                   @RequestParam(required = true) String device_sn,
                   @RequestParam(required = true) String province,
                   @RequestParam(required = true) String city,
                   @RequestParam(required = true) String district,
                   @RequestParam(required = true) String landmark_picture) {
        JSONObject jsonObj = new JSONObject();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        tpDeviceService.getConf(tpUsers.getUser_id(), device_sn, province, city, district, landmark_picture);
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请先登陆!");
        return jsonObj.toString();
    }

    /**
     * @return 团队机器
     */
    @RequestMapping(value = "team_device", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String TeamDevice(@RequestParam(required = true) String token,
                      @RequestParam(required = true) String page) {
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("device_num", tpDeviceService.selectCountDevice(tpUsers.getUser_id()));
        jsonObject1.put("income", tpUsersService.getSumMoneyDevice(tpUsers.getUser_id()));
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功");
        jsonObj.put("result", data);
        return jsonObj.toString();
    }

    /**
     * @return 新增机器栏目数据
     */
    @RequestMapping(value = "new_device_number", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String NewDeviceNumber(@RequestParam(required = true) String token) {
        JSONObject jsonObj = new JSONObject();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = deviceLogService.getTodayDevice(tpUsers.getUser_id());
        jsonObj.put("result", jsonObject);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok");
        return jsonObj.toString();
    }

    /**
     * @return 获取有设备的城市
     */
    @RequestMapping(value = "deviceCityList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deviceCityList() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        List<TpDevice> tpDevices = tpDeviceService.getHaveDeviceCity();
        for (TpDevice tpDevice : tpDevices) {
            String cityName = tpRegionService.getCityName(tpDevice.getCity());
            jsonArray.add(cityName);
        }
        jsonObj.put("result", jsonArray);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok");
        return jsonObj.toString();
    }
    //二维码生成,首先先获取机器二维码,就是根据参数在device_qr里面插入一条随机数
    //然后就是二维码的生成,参考网上的二维码生成

    /**
     * @return 获取机器二维码-v2
     */
    @RequestMapping(value = "getQrCodeV2", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getUserMpQr(@RequestParam(required = true) String ProductKey,
                       @RequestParam(required = true) String DeviceName,
                       @RequestParam(required = true) String latitude,
                       @RequestParam(required = true) String longitude) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        TpDevice tpDevice = tpDeviceService.getUserDevice(ProductKey, DeviceName, latitude, longitude);
        if (tpDevice == null) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "没有该设备");
            return jsonObj.toString();
        } else if (tpDevice.getActive_time() == 0) {
            jsonObj.put("status", "203");
            jsonObj.put("msg", "该设备没有激活");
            return jsonObj.toString();
        }
        String conent = tpDeviceQrService.saveQR(tpDevice.getUser_id(), latitude, longitude);
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

    /**
     * @return 获取公众号列表
     */
    @RequestMapping(value = "getMpList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getMpList(@RequestParam(required = true) String scene_str_v2,
                     @RequestParam(required = true) String lat,
                     @RequestParam(required = true) String lng,
                     @RequestParam(required = true) String token) throws WeixinApiException {
        JSONObject jsonObj = new JSONObject();
        JSONObject WXJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        TpDeviceQr tpDeviceQr = tpDeviceQrService.getLatAndLng(scene_str_v2);
        TpDevice tpDevice = tpDeviceService.getDeviceId(tpDeviceQr.getLng(), tpDeviceQr.getLat());
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        int status = tpDeviceQrService.selectStatus(scene_str_v2);
        if (status == 1) {
            throw new SenderException("该二维码已经被使用");
        }
        if (status == 0) {
            double v = tpDeviceQrService.updateQR(scene_str_v2, lat, lng, status, tpUsers.getUser_id());
            if (v < 10000) {
                List<TpWxUser> tpWxUsers = tpWxUserService.getWxUser();
                for (TpWxUser tpWxUser : tpWxUsers) {
                    String str = UUID.randomUUID().toString().substring(0, 32);
                    String ticket = WeixinAPI.getStrQRTicket(str);
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
                return "请在机器旁边进行扫码";
            }
        }
        jsonObj.put("result", jsonObject);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

    /**
     * @return 关注公众号后回到前端页面--检查paper_token是否有效
     */
    @RequestMapping(value = "checkPaperToken", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkPaperToken(@RequestParam(required = true) String paper_token) {
        //待完善,此处应该根据redisTemple取值判断值
        JSONObject jsonObj = new JSONObject();
        String str = (String) redisTemplate.opsForValue().get(paper_token);
        if(str == null){
            return "错误的场景码";
        }
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

    /**
     * 使用paper_token领取纸巾
     */
    @RequestMapping(value = "getPaperWx", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getPaperWx(@RequestParam(required = true) String paper_token) {
        //待完善,此处应该根据redisTemple取值判断值
        JSONObject jsonObj = new JSONObject();
        //这里下面是对的
        List<TpScanLog> tpScanLogs = tpScanLogService.findAlltpScanLogService();
        String data = (String) redisTemplate.opsForValue().get(paper_token);
        if(data == null){
            return "错误的场景码或者您已领取";
        }else if(data.length() == 0){
            return "错误的场景码2";
        }
        for(int i=0;i<tpScanLogs.size();i++){

            JSONObject json = new JSONObject();
            json.put("id", tpScanLogs.get(i).getId());
            json.put("user_id", tpScanLogs.get(i).getUser_id());
            boolean is_test = false;
            if(tpScanLogs.get(i).getId() > 0 ){
                TpScanLog tpScanLog = tpScanLogService.getStutas(tpScanLogs.get(i).getId());
                if(tpScanLog.getStatus() == true){
                    return "错误的场景码4";
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
    @RequestMapping(value = "lack_paper", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String lack_paper(@RequestParam(required = true,defaultValue = "0") String type,
                                           @RequestParam(required = true) String ProductKey,
                                           @RequestParam(required = true) String DeviceName) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject object = new JSONObject();
        TpDevice tpDevice = tpDeviceService.getDevice(ProductKey,DeviceName);
        if(tpDevice == null){
            return "机器不存在";
        }
        tpDevice.setLack_paper(Integer.parseInt(type));
        if(type.equals("1")){
            jsonObject.put("title","缺纸消息");
            jsonObject.put("discription","当前设备缺纸，请尽快补充");
            jsonObject.put("device_sn",tpDevice.getDevice_sn());
            jsonObject.put("paper_inventory",tpDevice.getPaper_inventory());
            jsonObject.put("address",tpDevice.getAddress());
            jsonObject.put("longitude",tpDevice.getLatitude());
            jsonObject.put("latitude",tpDevice.getLatitude());
            object.put("category",12);
            object.put("type",0);
            //TOdo 这里应该有推送业务
        }
        tpDeviceService.updateType(tpDevice);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 申请更换配件
     */
    @RequestMapping(value = "replacement_parts", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String replacement_parts(@RequestParam(required = true) String device_id,
                                                  @RequestParam(required = true) String name,
                                                  @RequestParam(required = true) String reason,
                                                  @RequestParam(required = true) String files,
                                                  @RequestParam(required = true) String address){
        JSONObject jsonObj = new JSONObject();
        if(device_id != null){
           return "请选择设备";
       }
       TpDevice tpDevice = tpDeviceService.getDeviceById(device_id);
       if(tpDevice.getUser_id() == 0){
            return "请选择正确的设备";
       }
        tpReplacementPartsService.insertReplacement(tpDevice,name,reason,files,address);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
        /**
         * @return 更换配件列表-v2
         */
    @RequestMapping(value = "replacement_parts_list_v2", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String replacement_parts_list_v2(@RequestParam(required = true) Integer type,
                                                          @RequestParam(required = true) String pageNo,
                                                          @RequestParam(required = true) String pageSize){
        JSONObject jsonObj = new JSONObject();
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 设备进入测试环境
     */
    @RequestMapping(value = "change_run_status", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String change_run_status(@RequestParam(required = true) String id,
                                                  @RequestParam(required = true) String run_status){
        JSONObject jsonObj = new JSONObject();
        TpDevice tpDevice  = tpDeviceService.getDeviceById(id);
        if(tpDevice != null){
            return "错误的设备";
        }
        tpDevice.setRun_status(Integer.parseInt(run_status));
        tpDeviceService.updateRunStatus(tpDevice);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 设备详情
     */
    @RequestMapping(value = "device_info", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String device_info(@RequestParam(required = true) String id){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        TpDevice tpDevice = tpDeviceService.getDeviceById(id);
        String online_status = "";
        if(tpDevice == null){
            return "错误的设备";
        }
        AliyunIotApi aliyunIotApi = new AliyunIotApi();
        QueryDeviceDetailResponse pubResponse = aliyunIotApi.queryDeviceDetailRequest(tpDevice.getProduct_key(), tpDevice.getDevice_name());
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
            jsonObject.put("imei",tpDevice.getDevice_sn());
            TpUsers tpUsers = tpUsersService.findOneByUserId(tpDevice.getUser_id());
            jsonObject.put("name",tpUsers.getNickname());
            jsonObject.put("region",province+cityName+district);
            jsonObject.put("paper","正常");
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok");
        return jsonObj.toString();
    }
    /**
     * @return 版本检测
     */
    @RequestMapping(value = "version", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String version(){
        TpAppVersion tpAppVersion = tpAppVersionService.getVersion();
        if(tpAppVersion == null){
            return "没有版本";
        }
        //Todo
        return null;
    }
}
