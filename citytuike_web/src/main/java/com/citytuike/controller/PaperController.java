package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.interceptor.JpushClientUtil;
import com.citytuike.model.*;
import com.citytuike.service.*;
import com.citytuike.util.Util;
import com.github.pagehelper.PageInfo;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/Paper")
public class PaperController extends BaseController{
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
    @Autowired
    private TpPaperTransferService tpPaperTransferService;
    @Autowired
    private TpMessageService tpMessageService;

    /**
     * @return
     * 机器订单首页-总收益部分
     */
    @RequestMapping(value = "/user_statistics",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "机器订单首页-总收益部分", notes = "机器订单首页-总收益部分")
    public @ResponseBody String userStatics(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
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
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾收益列表（分页）
     */
    @RequestMapping(value = "paper_income_list",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "纸巾收益列表", notes = "纸巾收益列表")
    public @ResponseBody String PaperIncomeList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String page = jsonRequest.getString("page");
        if(page == null || page.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误!");
            return jsonObj.toString();
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
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
        jsonObj.put("status", 1);
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾收益统计
     */
    @RequestMapping(value = "paper_income_statistics",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "纸巾收益统计", notes = "纸巾收益统计")
    public @ResponseBody String PaperIncomeStatistics(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        BigDecimal SumMoney = tpDeviceService.getSumMoneyDevice(tpUsers.getUser_id());
        jsonObj.put("status", 1);
        jsonObj.put("msg","请求成功");
        jsonObj.put("return",SumMoney);
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾余量统计+纸巾收益
     */
    @RequestMapping(value = "statistics",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "纸巾余量统计+纸巾收益", notes = "纸巾余量统计+纸巾收益")
    public @ResponseBody String statistics(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = tpDeviceService.statistics(tpUsers.getUser_id());
        return jsonObject.toString();
    }
    /**
     * @return
     * 设备列表（分页）
     */
    @RequestMapping(value = "lists",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "设备列表", notes = "设备列表")
    public @ResponseBody String lists(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String page = jsonRequest.getString("page");
        if(page == null || page.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误!");
            return jsonObj.toString();
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
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
        jsonObj.put("status", 1);
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
    /**
     * @return
     * 指定设备详情
     */
    @RequestMapping(value = "device_paper_info",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "指定设备详情", notes = "指定设备详情")
    public @ResponseBody String devicePaperInfo(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String device_id = jsonRequest.getString("device_id");
        String device_sn = jsonRequest.getString("device_sn");
        if(device_id == null || device_id.equals("") || device_sn == null || device_sn.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误!");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = tpDeviceService.getOnlyDevice(tpUsers.getUser_id(),device_id,device_sn);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
        jsonObj.put("result",jsonObject);
        return jsonObj.toString();
    }
    /**
     * @return
     * 设备纸巾进/出库日志（分页）
     */
    @RequestMapping(value = "paper_log",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "设备纸巾进/出库日志（分页）", notes = "设备纸巾进/出库日志（分页）")
    public @ResponseBody String paperLog(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String page = jsonRequest.getString("page");
        String device_id = jsonRequest.getString("device_id");
        String type = jsonRequest.getString("type");
        if(page == null || page.equals("") || device_id == null || device_id.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误!");
            return jsonObj.toString();
        }
        JSONObject data = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
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
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
        jsonObj.put("result",data);
        return jsonObj.toString();
    }
    /**
     * @return
     * 投放纸巾
     */
    @RequestMapping(value = "add_paper",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "投放纸巾", notes = "投放纸巾")
    public @ResponseBody String add_paper(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String number = jsonRequest.getString("number");
        String device_sn = jsonRequest.getString("device_sn");
        if(number == null || number.equals("") || device_sn ==null || device_sn.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误!");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpDevice tpDevice = tpDeviceService.selectPaper(device_sn,tpUsers.getUser_id());
        if(tpDevice == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "错误的设备!");
            return jsonObj.toString();
        }
        tpPaperLogService.save(device_sn,number,tpUsers.getUser_id());
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾转赠
     */
    @RequestMapping(value = "paper_transfer", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "纸巾转赠", notes = "纸巾转赠")
    public @ResponseBody String paperTransfer(HttpServletRequest request,
                                              @RequestParam(required = true) String money,
                                              @RequestParam(required = true) String number,
                                              @RequestParam(required = true) String invite_code,
                                              @RequestParam(required = true) String code){
        JSONObject jsonObj = new JSONObject();
        JSONObject msg = new JSONObject();
        JSONObject table = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpGoods tpGoods = tpGoodsService.selectPrice();
        TpUsers fromUser = tpUsersService.selectPaperCount(tpUsers.getUser_id());
        String shop_price = tpGoods.getShop_price() + "" != null ? tpGoods.getShop_price() + "" : "0";
        if (Double.parseDouble(money)> Double.parseDouble(shop_price)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "转赠单价不能大于"+shop_price+"/包!");
            return jsonObj.toString();
        }else if(number.equals("0")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "转赠单价不能大于"+0+"包!");
            return jsonObj.toString();
        }else if(fromUser == null || Integer.parseInt(number) > fromUser.getPaper_number_allowance()){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "纸巾数量不足，现库存为"+tpUsers.getPaper_number_allowance()+"!");
            return jsonObj.toString();
        }

        //接受者
        TpUsers toUser = tpUsersService.selectToUser(invite_code);
        if(toUser == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "转赠用户不存在!");
        }
        //验证码
        TpSmsLog tpSmsLog = tpSmsLogService.selectvalidateCode(code, tpUsers.getMobile());
        if(tpSmsLog.getStatus() != 1){
            return tpSmsLog.getMsg();
        }
        int i  = tpUsersService.updateNumber(tpUsers.getUser_id(),number);
        if(i<=0){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "转赠失败!");
        }else{
            //写日志
            int a = tpPaperTransferService.saveTransfer(tpUsers.getUser_id(),toUser.getUser_id(),number,money,fromUser.getPaper_number_allowance());
            if(a<=0){
                jsonObj.put("status", 0);
                jsonObj.put("msg", "转赠失败!");
            }else{
                Map<String, String> parm =new HashMap<String, String>();
                msg.put("title", "纸巾转赠");
                msg.put("discription", tpUsers.getNickname()+"向您转赠了"+number+"包纸巾，请确认查收~");
                msg.put("post_time",(int)(new Date().getTime()/1000));
                parm.put("msg",msg.toString());
                parm.put("RegId", String.valueOf(toUser.getUser_id()));
                table.put("category","0");
                table.put("type","0");
                jsonObj.put("table",table.toString());
                JpushClientUtil.testSendPush(parm);
                jsonObj.put("msg",msg);
                tpMessageService.save(jsonObj);
                jsonObj.put("status", 1);
                jsonObj.put("msg", "转赠成功，请等待对方确认!");
            }
        }
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾转赠列表
     */
    @RequestMapping(value = "paper_transfer_list", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "纸巾转赠列表", notes = "纸巾转赠列表")
    public @ResponseBody String paper_transfer_list(HttpServletRequest request,
                                                    @RequestParam(required = true)String page,
                                                    @RequestParam(required = true)String status){
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject object = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        if(Integer.parseInt(status) > 0 ){
            String bigStatus=status;
            PageInfo pageInfo = tpPaperTransferService.selectList(bigStatus,tpUsers.getUser_id(),page);
            List<TpPaperTransfer> tpPaperTransfers = pageInfo.getList();
            for (TpPaperTransfer tpPaperTransfer : tpPaperTransfers) {
                JSONObject item = new JSONObject();
                switch (tpPaperTransfer.getStatus()){
                    case 1:
                        item.put("status_str","待确认");
                    break;
                    case 2:
                        item.put("status_str","转赠成功");
                    break;
                    case 3:
                        item.put("status_str","已拒绝");
                    break;
                    case 4:
                        item.put("status_str","超时退回");
                    break;
                }
                if(tpPaperTransfer.getFrom_user_id().intValue() == tpUsers.getUser_id().intValue()){
                    item.put("add_time",Util.transferLongToDate("yyyy-MM-dd HH:mm:ss",tpPaperTransfer.getAdd_time().getTime()/1000));
                    item.put("number",tpPaperTransfer.getNumber());
                    item.put("money",tpPaperTransfer.getMoney());
                    item.put("from_user_id",tpPaperTransfer.getFrom_user_id());
                    item.put("to_user_id",tpPaperTransfer.getTo_user_id());
                    item.put("status",tpPaperTransfer.getStatus());
                    item.put("operate_time", Util.transferLongToDate("yyyy-MM-dd HH:mm:ss",tpPaperTransfer.getOperate_time().getTime()/1000));
                    item.put("remark",tpPaperTransfer.getRemark());
                    item.put("type","1");
                    item.put("type_str","转出");
                    item.put("log_desc","受让人");
                    TpUsers tpUser = tpUsersService.getToUser(tpPaperTransfer.getTo_user_id());
                    item.put("user_name",tpUser.getNickname());
                    jsonArray.add(item);
                }else if(tpPaperTransfer.getTo_user_id().intValue() == tpUsers.getUser_id().intValue()){
                    item.put("add_time",Util.transferLongToDate("yyyy-MM-dd HH:mm:ss",tpPaperTransfer.getAdd_time().getTime()/1000));
                    item.put("number",tpPaperTransfer.getNumber());
                    item.put("money",tpPaperTransfer.getMoney());
                    item.put("from_user_id",tpPaperTransfer.getFrom_user_id());
                    item.put("to_user_id",tpPaperTransfer.getTo_user_id());
                    item.put("status",tpPaperTransfer.getStatus());
                    item.put("operate_time", Util.transferLongToDate("yyyy-MM-dd HH:mm:ss",tpPaperTransfer.getOperate_time().getTime()/1000));
                    item.put("remark",tpPaperTransfer.getRemark());
                    item.put("type","2");
                    item.put("type_str","转入");
                    item.put("log_desc","转让人");
                    TpUsers tpUser = tpUsersService.getToUser(tpPaperTransfer.getFrom_user_id());
                    item.put("user_name",tpUser.getNickname());
                    jsonArray.add(item);
                }
            }
            object.put("current_page", pageInfo.getPageNum());
            object.put("total", pageInfo.getTotal());
            object.put("per_page", pageInfo.getPageSize());
            object.put("last_page",pageInfo.getLastPage());
        }
        jsonObj.put("result",object);
        object.put("data",jsonArray);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾转赠详情
     */
    @RequestMapping(value = "paper_transfer_view", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "纸巾转赠详情", notes = "纸巾转赠详情")
    public @ResponseBody String paper_transfer_view(HttpServletRequest request,
                                                    @RequestParam(required = false)String log_id){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONArray jsonArray = new JSONArray();
        if(log_id != null){
            String[] arr = log_id.split(",");
            for (int i = 0; i <arr.length ; i++) {
                int id = Integer.parseInt(arr[i]);
                jsonObject = tpPaperTransferService.selectArr(id);
                jsonArray.add(jsonObject);
                jsonObj.put("result",jsonArray);
                jsonObj.put("status", 1);
                jsonObj.put("msg", "ok!");
            }
        }else{
            List<TpPaperTransfer>tpPaperTransfers = tpPaperTransferService.selectAll(tpUsers.getUser_id());
            if(tpPaperTransfers == null){
                jsonObj.put("status", 0);
                jsonObj.put("msg","没有数据");
            }else{
                 for (TpPaperTransfer tpPaperTransfer : tpPaperTransfers) {
                     jsonObject = tpPaperTransferService.getJson(tpPaperTransfer);
                     jsonArray.add(jsonObject);
                     jsonObj.put("result",jsonArray);
                     jsonObj.put("status", 1);
                     jsonObj.put("msg", "ok!");
                }

            }
        }
        return jsonObj.toString();
    }
    /**
     * @return
     * 纸巾转赠确认
     */
    @RequestMapping(value = "paper_transfer_confirm", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "纸巾转赠确认", notes = "纸巾转赠确认")
    public @ResponseBody String paper_transfer_confirm(HttpServletRequest request,
                                                    @RequestParam(required = true)String status,
                                                    @RequestParam(required = true)String log_id,
                                                    @RequestParam(required = false)String reason){
        JSONObject jsonObj = new JSONObject();
        JSONObject msg = new JSONObject();
        JSONObject table = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpPaperTransfer tpPaperTransfer = tpPaperTransferService.selectToUser(tpUsers.getUser_id(), Integer.valueOf(log_id));
        if(tpPaperTransfer == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "记录不存在!");
            return jsonObj.toString();
        }else if(tpPaperTransfer.getStatus() != 1){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "该记录已操作过，请勿重复操作~!");
            return jsonObj.toString();
        }
        //修改状态
        if(status.equals("3")){
            //拒绝,把数量加回去
            TpUsers FromUser = tpUsersService.selectFromUser(tpPaperTransfer.getFrom_user_id());
            int i= tpPaperTransferService.updateStatus(tpPaperTransfer.getFrom_user_id(),log_id,tpUsers.getPaper_number_allowance(),tpPaperTransfer.getNumber());
            if(i>0){
                //加数量
                tpUsersService.addNumber(tpPaperTransfer.getFrom_user_id(),tpPaperTransfer.getNumber());
            }
            //推送消息 TODO
            Map<String, String> parm =new HashMap<String, String>();
            msg.put("title", "纸巾转赠");
            msg.put("discription", tpUsers.getNickname()+"用户拒绝了您的转赠请求，数量已返回您的账号");
            msg.put("post_time",(int)(new Date().getTime()/1000));
            parm.put("msg",msg.toString());
            parm.put("RegId", String.valueOf(FromUser.getUser_id()));
            table.put("category","0");
            table.put("type","0");
            jsonObj.put("table",table.toString());
            JpushClientUtil.testSendPush(parm);
            jsonObj.put("msg",msg);
            tpMessageService.save(jsonObj);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "操作成功!");
        }else if(status.equals("2")){
            //确认领取
            TpUsers toUsers = tpUsersService.selectToUsers(tpUsers.getUser_id());
            int a = tpPaperTransferService.addPaper(status,toUsers.getPaper_number_allowance(),tpPaperTransfer.getNumber(), Integer.valueOf(log_id),toUsers.getUser_id());
            if(a>0){
                //加数量
                tpUsersService.addNumberToUser(tpUsers.getUser_id(),tpPaperTransfer.getNumber());
                jsonObj.put("status", 1);
                jsonObj.put("msg", "确认成功!");
            }
        }
        return jsonObj.toString();
    }
}
