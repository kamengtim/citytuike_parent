package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpArticle;
import com.citytuike.model.TpArticleCat;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpArticleService;
import com.citytuike.service.TpUsersService;
import com.google.gson.JsonObject;
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
@RequestMapping("api/Article")
public class ArticleController extends BaseController{
    @Autowired
    private TpArticleService tpArticleService;
    @Autowired
    private TpUsersService tpUsersService;
    /**
     * @return 统一文章列表
     */
    @RequestMapping(value = "getArticleList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "统一文章列表", notes = "统一文章列表")
    public @ResponseBody String getArticleList(HttpServletRequest request,
                                               @RequestParam(required = true) String cat_id){
        JSONObject jsonObj = new JSONObject();
        if(cat_id == null || cat_id.equals("")){
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
        List<TpArticle>tpArticles = tpArticleService.getArticleList(cat_id);
        for (TpArticle tpArticle : tpArticles) {
            String description = tpArticle.getDescription().replace("&quot;",String.valueOf(" ' "));
            tpArticle.setDescription(description);
            jsonObject = tpArticleService.getJson(tpArticle);
        }
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 办卡攻略文章内容
     */
    @RequestMapping(value = "getArticleCon", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "办卡攻略文章内容", notes = "办卡攻略文章内容")
    public @ResponseBody String getArticleCon(HttpServletRequest request,
                                              @RequestParam(required = true) String article_id){
        JSONObject jsonObj = new JSONObject();
        if(article_id == null || article_id.equals("")){
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
        TpArticle tpArticle = tpArticleService.getArticleCon(article_id);
        int click = tpArticle.getClick()+1;
        tpArticle.setClick(click);
        int a = tpArticleService.update(tpArticle);
        if(a >= 0){
            jsonObject =tpArticleService.getDetailJson(tpArticle);
            jsonObj.put("result",jsonObject);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "成功");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败");
        return jsonObj.toString();
    }
    @RequestMapping(value = "getArticleCate", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取文章分类", notes = "获取文章分类")
    public @ResponseBody String getArticleCate(HttpServletRequest request,
                                              @RequestParam(required = true) Integer pid){
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
        List<TpArticleCat> tpArticleCatList = tpArticleService.findArticleCatByPid(pid);
        for (TpArticleCat tpArticleCat : tpArticleCatList) {
            JSONObject jsonObject = new JSONObject();
            int click_count = tpArticleService.getClick_Count(tpArticleCat.getCatId());
            jsonObject.put("cat_id", tpArticleCat.getCatId());
            jsonObject.put("cat_name", tpArticleCat.getCatName());
            jsonObject.put("parent_id", tpArticleCat.getParentId());
            jsonObject.put("sort_order", tpArticleCat.getSortOrder());
            jsonObject.put("cat_icon", tpArticleCat.getCatIcon());
            jsonObject.put("click_count", click_count);
            jsonArray.add(jsonObject);
        }

        jsonObj.put("result", jsonArray);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

}
