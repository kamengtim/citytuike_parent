package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpDevice;
import com.citytuike.model.TpDeviceLog;
import com.citytuike.model.TpUsers;
import com.citytuike.service.ITpDeviceService;
import com.citytuike.service.TpDeviceLogService;
import com.citytuike.service.TpUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/Paper")
public class DeviceController {
    @Autowired
    private ITpDeviceService tpDeviceService;
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpDeviceLogService deviceLogService;
    /**
     * @param model
     * @param id
     * @param p
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
     * @param model
     * @param id
     * @param p
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
        data.put("list",jsonArray);
        data.put("count",limtPageList.getPage().getTotalCount());
        jsonObj.put("return",data);
        jsonObj.put("status","1");
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
    /**
     * @param model
     * @param id
     * @param p
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
     * @param model
     * @param id
     * @param p
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
     * @param model
     * @param id
     * @param p
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
}
