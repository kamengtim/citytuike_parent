package com.citytuike.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.PhotoService;
import com.citytuike.service.TpUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/Photo")
@Api(value = "相册")
public class PhotoController {

    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private PhotoService photoService;

    /**
     * @param model
     * @param token
     * @return
     * 分类模板列表
     */
    @RequestMapping(value="/tmp_list",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "分类模板列表", notes = "分类模板列表")
    public @ResponseBody String tmpList(Model model, @RequestParam(required=true) String token,
                                        @RequestParam(required=true) Integer page,
                                        @RequestParam(required=true) String c_id) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = photoService.getLimitListByCid(c_id, page);
        for (TpPhotoAlbumTmp tpPhotoAlbumTmp : (List<TpPhotoAlbumTmp>)limitPageList.getList()) {
              JSONObject object = new JSONObject();
              object.put("id", tpPhotoAlbumTmp.getTmpId());
              object.put("image_url", tpPhotoAlbumTmp.getImageUrl());
              object.put("status", tpPhotoAlbumTmp.getStatus());
              object.put("sort", tpPhotoAlbumTmp.getSort());
              object.put("add_time", tpPhotoAlbumTmp.getAddTime());
              object.put("route_url", tpPhotoAlbumTmp.getRouteUrl());
              object.put("classify_id", tpPhotoAlbumTmp.getClassifyId());
            data.add(object);
        }
        jsonObj.put("result",data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

    /**
     * @param model
     * @param token
     * @param page
     * @return
     * 分类列表
     */
    @RequestMapping(value="/tmp_classify",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "分类列表", notes = "分类列表")
    public @ResponseBody String tmpClassify(Model model, @RequestParam(required=true) String token,
                                            @RequestParam(required=true) Integer page) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        JSONArray data = new JSONArray();
        JSONArray data1 = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        Integer fristClassify = null;
        List<TpPhotoAlbumTmpClassify> tpPhotoAlbumTmpClassifyList = photoService.findAllClassify();
        for (int i =0; i< tpPhotoAlbumTmpClassifyList.size(); i++){
            if (i==0){
                fristClassify = tpPhotoAlbumTmpClassifyList.get(i).getId();
            }
            JSONObject object = new JSONObject();
            object.put("id", tpPhotoAlbumTmpClassifyList.get(i).getId());
            object.put("name", tpPhotoAlbumTmpClassifyList.get(i).getName());
            object.put("sort", tpPhotoAlbumTmpClassifyList.get(i).getSort());
            object.put("status", tpPhotoAlbumTmpClassifyList.get(i).getStatus());
            object.put("icon", tpPhotoAlbumTmpClassifyList.get(i).getIcon());
            data.add(object);
        }
        jsonObj1.put("classify", data);
        if (null != fristClassify){
            JSONObject jsonObject = new JSONObject();
            LimitPageList limitPageList = photoService.getLimitListByCid(fristClassify+"", page);
            for (TpPhotoAlbumTmp tpPhotoAlbumTmp : (List<TpPhotoAlbumTmp>)limitPageList.getList()) {
                JSONObject object = new JSONObject();
                object.put("id", tpPhotoAlbumTmp.getTmpId());
                object.put("image_url", tpPhotoAlbumTmp.getImageUrl());
                object.put("status", tpPhotoAlbumTmp.getStatus());
                object.put("sort", tpPhotoAlbumTmp.getSort());
                object.put("add_time", tpPhotoAlbumTmp.getAddTime());
                object.put("route_url", tpPhotoAlbumTmp.getRouteUrl());
                object.put("classify_id", tpPhotoAlbumTmp.getClassifyId());
                data1.add(object);
            }
            jsonObject.put("total",limitPageList.getPage().getTotalCount());
            jsonObject.put("per_page",limitPageList.getPage().getPageNow());
            jsonObject.put("current_page",limitPageList.getPage().getPageNow());
            jsonObject.put("last_page",limitPageList.getPage().getTotalPageCount());
            jsonObject.put("data", data1);
            jsonObj1.put("first_list", jsonObject);
        }
        jsonObj.put("result",jsonObj1);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

    @RequestMapping(value="/create_ad",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "添加广告", notes = "添加广告")
    public @ResponseBody String createAd(@RequestParam(required=true) String token,
                                        @RequestParam(required=true) String desc,
                                        @RequestParam(required=true) String image,
                                        @RequestParam(required=true) String model,
                                        @RequestParam(required=true) String price,
                                        @RequestParam(required=false) String ori_price,
                                        @RequestParam(required=true) String link,
                                        @RequestParam(required=false) String ad_id) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpPhotoAlbumAd tpPhotoAlbumAd = new TpPhotoAlbumAd();
        tpPhotoAlbumAd.setUserId(tpUsers.getUser_id());
        tpPhotoAlbumAd.setDesc(desc);
        tpPhotoAlbumAd.setImage(image);
        tpPhotoAlbumAd.setModel(model);
        tpPhotoAlbumAd.setPrice(Double.parseDouble(price));
        tpPhotoAlbumAd.setOriPrice(Double.parseDouble(ori_price));
        tpPhotoAlbumAd.setLink(link);
        if (null != ad_id && !"".equals(ad_id)){
            TpPhotoAlbumAd tpPhotoAlbumAd1 = photoService.findOneAlbumAdById(ad_id);
            if(null != tpPhotoAlbumAd1){
                tpPhotoAlbumAd.setAdId(tpPhotoAlbumAd1.getAdId());
                int upResult = photoService.updataPhotoAlbumAd(tpPhotoAlbumAd);
                if (upResult < 0){
                    jsonObj.put("status", "0");
                    jsonObj.put("msg", "更新失败!");
                    return jsonObj.toString();
                }
            }
        }else{
            int adResult = photoService.insertPhotoAlbumAd(tpPhotoAlbumAd);
            if (adResult < 0){
                jsonObj.put("status", "0");
                jsonObj.put("msg", "添加失败!");
                return jsonObj.toString();
            }
        }

        jsonObj1.put("ad_id", tpPhotoAlbumAd.getAdId());
        jsonObj.put("result",jsonObj1);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

    /**
     * @param token
     * @param ad_id
     * @return
     * 获取广告
     */
    @RequestMapping(value="/get_ad",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取广告", notes = "获取广告")
    public @ResponseBody String getAd(@RequestParam(required=true) String token,
                                        @RequestParam(required=false) String ad_id) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpPhotoAlbumAd tpPhotoAlbumAd = photoService.findOneAlbumAdById(ad_id);
        if (null != tpPhotoAlbumAd){
            JSONObject object = new JSONObject();
            object.put("ad_id", tpPhotoAlbumAd.getAdId());
            object.put("desc", tpPhotoAlbumAd.getDesc());
            object.put("model", tpPhotoAlbumAd.getModel());
            object.put("price", tpPhotoAlbumAd.getPrice());
            object.put("link", tpPhotoAlbumAd.getLink());
            object.put("ori_price", tpPhotoAlbumAd.getOriPrice());
            object.put("text_2", tpPhotoAlbumAd.getText2());
            object.put("text_3", tpPhotoAlbumAd.getText3());
            jsonObj1.put("ad_info", object);
        }

        jsonObj.put("result",jsonObj1);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

    /**
     * @param token
     * @param ad_id
     * @param music_url
     * @param tmp_id
     * @param image_list
     * @param extend_str
     * @param p_id
     * @return
     * 保存相册
     */
    @RequestMapping(value="/save_photo",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "保存相册", notes = "保存相册")
    public @ResponseBody String savePhoto(@RequestParam(required=true) String token,
                                          @RequestParam(required=false) String ad_id,
                                          @RequestParam(required=false) String music_url,
                                          @RequestParam(required=false) String tmp_id,
                                          @RequestParam(required=false) String image_list,
                                          @RequestParam(required=false) String extend_str,
                                          @RequestParam(required=false) String p_id) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpPhotoAlbumUser tpPhotoAlbumUser = new TpPhotoAlbumUser();
        tpPhotoAlbumUser.setAdId(ad_id);
        tpPhotoAlbumUser.setMusicUrl(music_url);
        tpPhotoAlbumUser.setTmpId(Integer.parseInt(tmp_id));
        tpPhotoAlbumUser.setExtend(extend_str);
        tpPhotoAlbumUser.setAddTime(new Date());
        tpPhotoAlbumUser.setUserId(tpUsers.getUser_id());
        tpPhotoAlbumUser.setShare(0);
        tpPhotoAlbumUser.setSort(0);
        tpPhotoAlbumUser.setStatus(0);
        tpPhotoAlbumUser.setPv(0);
        TpPhotoAlbumAd tpPhotoAlbumAd = photoService.findOneAlbumAdById(ad_id);
        if (null != tpPhotoAlbumAd){
            tpPhotoAlbumUser.setDesc(tpPhotoAlbumAd.getDesc());
        }
        if (null != p_id && !"".equals(p_id)){
            TpPhotoAlbumUser tpPhotoAlbumUser1 = photoService.findOneAlbumUserById(p_id);
            if (null != tpPhotoAlbumUser1){
                tpPhotoAlbumUser.setpId(Integer.parseInt(p_id));
                int updataResult = photoService.updataPhotoAlbumUser(tpPhotoAlbumUser);
                if (updataResult <= 0){
                    jsonObj.put("status", "0");
                    jsonObj.put("msg", "更新失败!");
                    return jsonObj.toString();
                }
            }
            int deleteResult = photoService.deleteUserImageByPid(p_id);
            if (deleteResult <= 0){
                jsonObj.put("status", "0");
                jsonObj.put("msg", "更新失败!");
                return jsonObj.toString();
            }
        }else{
            int insertResult = photoService.insertPhotoAlbumUser(tpPhotoAlbumUser);
            if (insertResult <= 0){
                jsonObj.put("status", "0");
                jsonObj.put("msg", "添加失败!");
                return jsonObj.toString();
            }
        }
        TpPhotoAlbumUserImage tpPhotoAlbumUserImage = new TpPhotoAlbumUserImage();
        JSONArray jsonArray = JSONArray.parseArray(image_list);
        for (int i=0; i<jsonArray.size(); i++ ){
            tpPhotoAlbumUserImage = JSON.parseObject(jsonArray.get(i).toString(), TpPhotoAlbumUserImage.class);
            tpPhotoAlbumUserImage.setpId(tpPhotoAlbumUser.getpId());
            tpPhotoAlbumUserImage.setSort(1);
            int insertUserImage = photoService.insertPHotoAlbumUserImage(tpPhotoAlbumUserImage);
            if (insertUserImage <= 0){
                jsonObj.put("status", "0");
                jsonObj.put("msg", "添加失败!");
                return jsonObj.toString();
            }
        }
        jsonObj.put("result",jsonObj1);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

    /**
     * @param token
     * @param page
     * @return
     * 相册列表
     */
    @RequestMapping(value="/photo_list",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "相册列表", notes = "相册列表")
    public @ResponseBody String photoList(@RequestParam(required=true) String token,
                                      @RequestParam(required=false) String page) {
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj1 = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = photoService.getlimitListPhotoAlbumUser(Integer.parseInt(page), tpUsers.getUser_id());
        for (TpPhotoAlbumUser tpPhotoAlbumUser : (List<TpPhotoAlbumUser>)limitPageList.getList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("p_id", tpPhotoAlbumUser.getpId());
            jsonObject.put("desc", tpPhotoAlbumUser.getDesc());
            jsonObject.put("ad_id", tpPhotoAlbumUser.getAdId());
            jsonObject.put("music_url", tpPhotoAlbumUser.getMusicUrl());
            jsonObject.put("add_time", tpPhotoAlbumUser.getAddTime());
            jsonObject.put("status", tpPhotoAlbumUser.getStatus());
            jsonObject.put("pv", tpPhotoAlbumUser.getPv());
            jsonObject.put("sort", tpPhotoAlbumUser.getSort());
            jsonObject.put("user_id", tpPhotoAlbumUser.getUserId());
            jsonObject.put("tmp_id", tpPhotoAlbumUser.getTmpId());
            jsonObject.put("update_time", tpPhotoAlbumUser.getUpdateTime());
            jsonObject.put("share", tpPhotoAlbumUser.getShare());
            jsonObject.put("delete_time", tpPhotoAlbumUser.getDeleteTime());
            jsonObject.put("extend", tpPhotoAlbumUser.getExtend());
            List<TpPhotoAlbumUserImage> list = photoService.findAllPhotoAlbumUserImageByPid(tpPhotoAlbumUser.getpId());
            if (list.size() > 0){
                jsonObject.put("first_img", list.get(0).getImage());
            }
            jsonArray.add(jsonObject);
        }
        jsonObj1.put("data",jsonArray);
        jsonObj1.put("total",limitPageList.getPage().getTotalCount());
        jsonObj1.put("per_page",limitPageList.getPage().getPageNow());
        jsonObj1.put("current_page",limitPageList.getPage().getPageNow());
        jsonObj1.put("last_page",limitPageList.getPage().getTotalPageCount());
        jsonObj.put("result",jsonObj1);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

}
