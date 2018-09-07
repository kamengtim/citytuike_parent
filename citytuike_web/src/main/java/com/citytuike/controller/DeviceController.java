package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpDevice;
import com.citytuike.model.TpUsers;
import com.citytuike.service.*;
import com.sun.xml.internal.ws.client.SenderException;
import com.swetake.util.Qrcode;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.List;
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
    /**
     * @return
     * 获取设备配置
     */
    @RequestMapping(value = "getConf",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String getConf(@RequestParam(required = true) String deviceSn){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = tpDeviceService.selectDeviceBySn(deviceSn);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功!");
        jsonObj.put("result",jsonObject);
        return jsonObj.toString();
    }
    /**
     * @return
     * 激活设备
     */
    @RequestMapping(value = "active",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String getConf(@RequestParam(required = true)String token,
                                        @RequestParam(required = true)String device_sn,
                                        @RequestParam(required = true)String province,
                                        @RequestParam(required = true) String city,
                                        @RequestParam(required = true)String district,
                                        @RequestParam(required = true)String landmark_picture){
        JSONObject jsonObj = new JSONObject();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        tpDeviceService.getConf(tpUsers.getUser_id(),device_sn,province,city,district,landmark_picture);
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请先登陆!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 团队机器
     */
    @RequestMapping(value = "team_device",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String TeamDevice(@RequestParam(required = true)String token,
                                           @RequestParam(required = true)String page){
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
        jsonObject1.put("device_num",tpDeviceService.selectCountDevice(tpUsers.getUser_id()));
        jsonObject1.put("income",tpUsersService.getSumMoneyDevice(tpUsers.getUser_id()));
        int beginData = tpUsersService.selectRegTime(tpUsers.getUser_id());
        int i = ((int)new Date().getTime() - beginData)/1000/60/60/24;
        jsonObject1.put("day_avg_income",tpUsersService.getSumMoneyDevice(tpUsers.getUser_id()).divide(BigDecimal.valueOf((int)i),10,BigDecimal.ROUND_HALF_DOWN));
        jsonObject1.put("ad_number",0);
        jsonArray.add(jsonObject1);
        LimitPageList limitPageList = tpUsersService.getLimitPageList(tpUsers.getUser_id(),page);
        List<TpUsers> list = (List<TpUsers>)limitPageList.getList();
        for (TpUsers tpUsers1 : list) {
            JSONObject device = tpUsersService.getUserlJson(tpUsers1);
            jsonArray.add(device);
        }
        data.put("current_page", limitPageList.getPage().getPageNow());
        data.put("total", limitPageList.getPage().getTotalCount());
        data.put("per_page", limitPageList.getPage().getPageSize());
        data.put("last_page",limitPageList.getPage().getTotalPageCount());
        data.put("data",jsonArray);
        jsonObj.put("status","1");
        jsonObj.put("msg","请求成功");
        jsonObj.put("result",data);
        return jsonObj.toString();
    }
    /**
     * @return
     * 新增机器栏目数据
     */
    @RequestMapping(value = "new_device_number",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String NewDeviceNumber(@RequestParam(required = true) String token){
        JSONObject jsonObj = new JSONObject();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = deviceLogService.getTodayDevice(tpUsers.getUser_id());
        jsonObj.put("result",jsonObject);
        jsonObj.put("status","1");
        jsonObj.put("msg","ok");
        return jsonObj.toString();
    }
    /**
     * @return
     * 获取有设备的城市
     */
    @RequestMapping(value = "deviceCityList",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String deviceCityList(){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
       List<TpDevice>tpDevices = tpDeviceService.getHaveDeviceCity();
        for (TpDevice tpDevice : tpDevices) {
            String cityName = tpRegionService.getCityName(tpDevice.getCity());
            jsonArray.add(cityName);
        }
        jsonObj.put("result",jsonArray);
        jsonObj.put("status","1");
        jsonObj.put("msg","ok");
        return jsonObj.toString();
    }
    //二维码生成,首先先获取机器二维码,就是根据参数在device_qr里面插入一条随机数
    //然后就是二维码的生成,参考网上的二维码生成
    /**
     * @return
     * 获取机器二维码-v2
     */
    @RequestMapping(value = "getQrCodeV2",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String getUserMpQr(@RequestParam(required = true)String ProductKey,
                                            @RequestParam(required = true)String DeviceName,
                                            @RequestParam(required = true)String latitude,
                                            @RequestParam(required = true) String longitude){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        TpDevice tpDevice = tpDeviceService.getUserDevice(ProductKey,DeviceName,latitude,longitude);
        if(tpDevice == null){
            jsonObj.put("status","0");
            jsonObj.put("msg","没有该设备");
            return jsonObj.toString();
        }else if(tpDevice.getActive_time() == 0){
            jsonObj.put("status","203");
            jsonObj.put("msg","该设备没有激活");
            return jsonObj.toString();
        }
        String conent = tpDeviceQrService.saveQR(tpDevice.getUser_id(),latitude,longitude);
        jsonObject.put("device_id",tpDevice.getId());
        jsonObject.put("ticket","");
        jsonObject.put("url",conent);
        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeErrorCorrect('M');//纠错等级（分为L、M、H三个等级）
        qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符
        qrcode.setQrcodeVersion(7);//版本
        //生成二维码中要存储的信息
        String qrData = conent;
        //设置一下二维码的像素
        int width = 300;
        int height = 300;
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
        if(d.length > 0 && d.length <120){
            boolean[][] s = qrcode.calQrcode(d);
            for(int i=0;i<s.length;i++){
                for(int j=0;j<s.length;j++){
                    if(s[j][i]){
                        gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
                    }
                }
            }
        }
        String str = UUID.randomUUID().toString().substring(0,32);
        gs.dispose();
        bufferedImage.flush();
        try {
            ImageIO.write(bufferedImage, "jpg", new File("D:/tuike/citytuike_parent/citytuike_web/src/resources/public/upload/device_code/20180819/"+str+".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status","1");
        jsonObj.put("msg","成功");
        return jsonObj.toString();
    }
    @RequestMapping(value = "getMpList",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String getMpList(@RequestParam(required = true)String scene_str_v2,
                                          @RequestParam(required = true)String lat,
                                          @RequestParam(required = true)String lng,
                                          @RequestParam(required = true)String token){
        JSONObject jsonObj = new JSONObject();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
       int status = tpDeviceQrService.selectStatus(scene_str_v2);
       if(status == 1){
           throw new SenderException("该二维码已经被使用");
       }
       if(status == 0){
           double v = tpDeviceQrService.updateQR(scene_str_v2, lat, lng, status, tpUsers.getUser_id());
           if(v > 10){
               return "请在机器旁边进行扫码";
           }
       }
        jsonObj.put("status","1");
        jsonObj.put("msg","成功");
       return jsonObj.toString();
    }

}
