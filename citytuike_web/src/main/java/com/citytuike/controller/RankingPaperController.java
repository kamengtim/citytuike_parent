package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpUserLog;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUserLogService;
import com.citytuike.service.TpUsersService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("api/Ranking")
public class RankingPaperController extends BaseController{
    @Autowired
    private TpUserLogService tpUserLogService;
    @Autowired
    private TpUsersService tpUsersService;
    /**
     * @return 领取纸巾
     */
    @RequestMapping(value = "paper", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "领取纸巾", notes = "领取纸巾")
    public @ResponseBody String paper(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        List<TpUserLog>tpUserLogs = tpUserLogService.paper();
        for (TpUserLog tpUserLog : tpUserLogs) {
            jsonObject = tpUserLogService.getJson(tpUserLog);
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 收益
     */
    @RequestMapping(value = "income", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "收益", notes = "收益")
    public @ResponseBody String income(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        List<TpUsers> tpUser = tpUsersService.income();
        for (TpUsers tpUse : tpUser) {
            jsonObject = tpUsersService.getUserlJson(tpUse);
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 销售
     */
    @RequestMapping(value = "sale", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "销售", notes = "销售")
    public @ResponseBody String sale(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        List<TpUsers> tpUser = tpUsersService.sale();
        for (TpUsers tpUse : tpUser) {
            jsonObject = tpUsersService.getUserlJson(tpUse);
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
}
