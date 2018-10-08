package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.interceptor.LoginUtil;
import com.citytuike.interceptor.RedisConstant;
import com.citytuike.model.*;
import com.citytuike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/Paper")
public class PaperController {
    @Autowired
    private ITpDeviceService tpDeviceService;
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpPaperLogService tpPaperLogService;
    @Autowired
    private TpGoodsService tpGoodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TpSmsLogService tpSmsLogService;
    /**
     * @return
     * 机器订单首页-总收益部分
     */
    @RequestMapping(value = "/user_statistics",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String userStatics(@RequestParam(required = true) String token){
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
        jsonObject1.put("day_avg_income",tpUsersService.getSumMoneyDevice(tpUsers.getUser_id()).divide(BigDecimal.valueOf((int)i)));
        jsonObject1.put("ad_number",0);
        jsonArray.add(jsonObject1);
        jsonObj.put("return",jsonArray);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾收益列表（分页）
     */
    @RequestMapping(value = "paper_income_list",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String PaperIncomeStatistics(@RequestParam(required = true) String token,@RequestParam(required = true)String page){
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
        LimitPageList limtPageList = tpDeviceService.getLimtPageList(tpUsers.getUser_id(),page);
        data.put("return",limtPageList);
        List<TpDevice> tpDevices = (List<TpDevice>)limtPageList.getList();
        for (TpDevice tpDevice : tpDevices) {
            JSONObject device = tpDeviceService.getDeviceJson(tpDevice);
            jsonArray.add(device);
        }
        data.put("current_page", limtPageList.getPage().getPageNow());
        data.put("total", limtPageList.getPage().getTotalCount());
        data.put("per_page", limtPageList.getPage().getPageSize());
        data.put("last_page",limtPageList.getPage().getTotalPageCount());
        data.put("data",jsonArray);
        jsonObj.put("result",data);
        jsonObj.put("status","1");
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾收益统计
     */
    @RequestMapping(value = "paper_income_statistics",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String PaperIncomeStatistics(@RequestParam(required = true) String token){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        BigDecimal SumMoney = tpDeviceService.getSumMoneyDevice(tpUsers.getUser_id());
        jsonObj.put("status","1");
        jsonObj.put("msg","请求成功");
        jsonObj.put("return",SumMoney);
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾余量统计+纸巾收益
     */
    @RequestMapping(value = "statistics",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String statistics(@RequestParam(required = true) String token){
        JSONObject jsonObj = new JSONObject();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = tpDeviceService.statistics(tpUsers.getUser_id());
        return jsonObject.toString();
    }
    /**
     * @return
     * 设备列表（分页）
     */
    @RequestMapping(value = "lists",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String lists(@RequestParam(required = true) String token,
                                      @RequestParam(required = true) String page){
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
       LimitPageList limitPageList =  tpDeviceService.selectDeviceList(tpUsers.getUser_id(),page);
        List<TpDevice> tpDevices = (List<TpDevice>)limitPageList.getList();
        for (TpDevice tpDevice : tpDevices) {
            JSONObject device = tpDeviceService.getNewDeviceJson(tpDevice);
            jsonArray.add(device);
        }
        data.put("current_page", limitPageList.getPage().getPageNow());
        data.put("total", limitPageList.getPage().getTotalCount());
        data.put("per_page", limitPageList.getPage().getPageSize());
        data.put("last_page",limitPageList.getPage().getTotalPageCount());
        data.put("data",jsonArray);
        jsonObj.put("result",data);
        jsonObj.put("status","1");
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
    /**
     * @return
     * 指定设备详情
     */
    @RequestMapping(value = "device_paper_info",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String devicePaperInfo(@RequestParam(required = true) String token,
                                                @RequestParam(required = true) String device_id,
                                                @RequestParam(required = true) String device_sn){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = tpDeviceService.getOnlyDevice(tpUsers.getUser_id(),device_id,device_sn);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        jsonObj.put("result",jsonObject);
        return jsonObj.toString();
    }
    /**
     * @return
     * 设备纸巾进/出库日志（分页）
     */
    @RequestMapping(value = "paper_log",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String paperLog(@RequestParam(required = true) String token,
                                         @RequestParam(required = true) String page,
                                         @RequestParam(required = true) String device_id,
                                         @RequestParam(required = false) String type){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = tpPaperLogService.paperLog(tpUsers.getUser_id(),page,device_id,type);
        List<TpPaperLog>tpPaperLogs = (List<TpPaperLog>)limitPageList.getList();
        for (TpPaperLog tpPaperLog : tpPaperLogs) {
            JSONObject jsonObject = tpPaperLogService.getJsonLog(tpPaperLog);
            jsonArray.add(jsonObject);
        }
        data.put("current_page", limitPageList.getPage().getPageNow());
        data.put("total", limitPageList.getPage().getTotalCount());
        data.put("per_page", limitPageList.getPage().getPageSize());
        data.put("last_page",limitPageList.getPage().getTotalPageCount());
        data.put("data",jsonArray);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        jsonObj.put("result",data);
        return jsonObj.toString();
    }
    /**
     * @return
     * 投放纸巾
     */
    @RequestMapping(value = "add_paper",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String add_paper(@RequestParam(required = true) String token,
                                                @RequestParam(required = true) String number,
                                                @RequestParam(required = true) String device_sn){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpDevice tpDevice = tpDeviceService.selectPaper(device_sn,tpUsers.getUser_id());
        if(tpDevice == null){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "错误的设备!");
            return jsonObj.toString();
        }
        tpPaperLogService.save(device_sn,number,tpUsers.getUser_id());
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾转赠
     */
    @RequestMapping(value = "paper_transfer", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String paperTransfer(HttpServletRequest request,
                                              @RequestParam(required = true) String money,
                                              @RequestParam(required = true) String number,
                                              @RequestParam(required = true) String invite_code,
                                              @RequestParam(required = true) String code){
        JSONObject jsonObj = new JSONObject();
        String header = request.getHeader("p-token");
        if(header == null || header.trim().length() == 0){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "登录异常!");
            return jsonObj.toString();
        }

        String token = (String) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ header);
        TpUsers tpUsers = tpUsersService.getToken(token);
        if(tpUsers == null){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "登录超时!");
            return jsonObj.toString();
        }
        TpGoods tpGoods = tpGoodsService.selectPrice();
        TpUsers fromUser = tpUsersService.selectPaperCount(tpUsers.getUser_id());
        String shop_price = tpGoods.getShop_price() + "" != null ? tpGoods.getShop_price() + "" : "0";
        if (Integer.parseInt(money) > Integer.parseInt(shop_price)){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "转赠单价不能大于"+shop_price+"/包!");
        }else if(number.equals("0")){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "转赠单价不能大于"+0+"包!");
        }else if(fromUser == null || Integer.parseInt(number) > fromUser.getPaper_number_allowance()){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "纸巾数量不足，现库存为"+tpUsers.getPaper_number_allowance()+"!");
        }

        //转赠的人
        TpUsers toUser = tpUsersService.selectToUser(invite_code);
        if(toUser == null){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "转赠用户不存在!");
        }
        int i = tpSmsLogService.selectvalidateCode(code, tpUsers.getMobile());
        if(i>0){

        }
        jsonObj.put("status", "1");
        jsonObj.put("msg", "发送成功!");
        return null;
    }
}
