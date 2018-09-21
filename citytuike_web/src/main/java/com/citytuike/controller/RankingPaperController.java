package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpUserLog;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUserLogService;
import com.citytuike.service.TpUsersService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("api/Ranking")
public class RankingPaperController {
    @Autowired
    private TpUserLogService tpUserLogService;
    @Autowired
    private TpUsersService tpUsersService;
    /**
     * @return 领取纸巾
     */
    @RequestMapping(value = "paper", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String paper(){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        List<TpUserLog>tpUserLogs = tpUserLogService.paper();
        for (TpUserLog tpUserLog : tpUserLogs) {
            jsonObject = tpUserLogService.getJson(tpUserLog);
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 收益
     */
    @RequestMapping(value = "income", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String income(){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        List<TpUsers> tpUsers = tpUsersService.income();
        for (TpUsers tpUser : tpUsers) {
            jsonObject = tpUsersService.getUserlJson(tpUser);
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 销售
     */
    @RequestMapping(value = "sale", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String sale(){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        List<TpUsers> tpUsers = tpUsersService.sale();
        for (TpUsers tpUser : tpUsers) {
            jsonObject = tpUsersService.getUserlJson(tpUser);
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
}
