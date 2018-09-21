package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpArticle;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpArticleService;
import com.citytuike.service.TpUsersService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("api/Article")
public class ArticleController {
    @Autowired
    private TpArticleService tpArticleService;
    @Autowired
    private TpUsersService tpUsersService;
    /**
     * @return 统一文章列表
     */
    @RequestMapping(value = "getArticleList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getArticleList(@RequestParam(required = true) String cat_id){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        List<TpArticle>tpArticles = tpArticleService.getArticleList(cat_id);
        for (TpArticle tpArticle : tpArticles) {
            String description = tpArticle.getDescription().replace("&quot;",String.valueOf(" ' "));
            tpArticle.setDescription(description);
            jsonObject = tpArticleService.getJson(tpArticle);
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 办卡攻略文章内容
     */
    @RequestMapping(value = "getArticleCon", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getArticleCon(@RequestParam(required = true) String token,
                                               @RequestParam(required = true) String article_id){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        JSONObject  jsonObj = new JSONObject();
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpArticle tpArticle = tpArticleService.getArticleCon(article_id);
        int click = tpArticle.getClick()+1;
        tpArticle.setClick(click);
        int a = tpArticleService.update(tpArticle);
        if(a >= 0){
            jsonObject =tpArticleService.getDetailJson(tpArticle);
            jsonObj.put("result",jsonObject);
            jsonObj.put("status", "1");
            jsonObj.put("msg", "成功");
            return jsonObj.toString();
        }
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败");
        return jsonObj.toString();
    }

}
