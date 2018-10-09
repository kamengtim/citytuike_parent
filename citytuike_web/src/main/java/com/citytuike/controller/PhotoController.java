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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/Photo")
@Api(value = "相册")
public class PhotoController extends BaseController{

    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private PhotoService photoService;

    /**
     * @return
     * 分类模板列表
     */
    @RequestMapping(value="/tmp_list",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "分类模板列表", notes = "分类模板列表")
    public @ResponseBody String tmpList(@RequestParam(required=true) Integer page,
                                        @RequestParam(required=true) String c_id,
                                        HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
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
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param page
     * @return
     * 分类列表
     */
    @RequestMapping(value="/tmp_classify",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "分类列表", notes = "分类列表")
    public @ResponseBody String tmpClassify(HttpServletRequest request,
                                            @RequestParam(required=true) Integer page) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        JSONArray data = new JSONArray();
        JSONArray data1 = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
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
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    @RequestMapping(value="/create_ad",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "添加广告", notes = "添加广告")
    public @ResponseBody String createAd(HttpServletRequest request,
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
        TpUsers tpUsers = initUser(request);
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
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param ad_id
     * @return
     * 获取广告
     */
    @RequestMapping(value="/get_ad",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取广告", notes = "获取广告")
    public @ResponseBody String getAd(HttpServletRequest request,
                                        @RequestParam(required=true) String ad_id) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
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
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
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
    public @ResponseBody String savePhoto(HttpServletRequest request,
                                          @RequestParam(required=false) String desc,
                                          @RequestParam(required=false) String ad_id,
                                          @RequestParam(required=true) String music_url,
                                          @RequestParam(required=true) String tmp_id,
                                          @RequestParam(required=false) String image_list,
                                          @RequestParam(required=true) String extend_str,
                                          @RequestParam(required=false) String p_id) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
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
        tpPhotoAlbumUser.setDesc(desc);
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
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param page
     * @return
     * 相册列表
     */
    @RequestMapping(value="/photo_list",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "相册列表", notes = "相册列表")
    public @ResponseBody String photoList(HttpServletRequest request,
                                      @RequestParam(required=true) String page) {
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj1 = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
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
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param p_id
     * @param request
     * @return
     * 相册详情
     */
    @RequestMapping(value="/photo_detail",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "相册详情", notes = "相册详情")
    public @ResponseBody String photoDetail(@RequestParam(required=true) String p_id, HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpPhotoAlbumUser tpPhotoAlbumUser = photoService.findOneAlbumUserById(p_id);
        if (null != tpPhotoAlbumUser){
            JSONObject jsonObj2 = new JSONObject();
            jsonObj2.put("p_id", tpPhotoAlbumUser.getpId());
            jsonObj2.put("desc", tpPhotoAlbumUser.getDesc());
            jsonObj2.put("ad_id", tpPhotoAlbumUser.getAdId());
            jsonObj2.put("music_url", tpPhotoAlbumUser.getMusicUrl());
            jsonObj2.put("add_time", tpPhotoAlbumUser.getAddTime());
            jsonObj2.put("status", tpPhotoAlbumUser.getStatus());
            jsonObj2.put("pv", tpPhotoAlbumUser.getPv());
            jsonObj2.put("sort", tpPhotoAlbumUser.getSort());
            jsonObj2.put("user_id", tpPhotoAlbumUser.getUserId());
            jsonObj2.put("tmp_id", tpPhotoAlbumUser.getTmpId());
            jsonObj2.put("update_time", tpPhotoAlbumUser.getUpdateTime());
            jsonObj2.put("share", tpPhotoAlbumUser.getShare());
            jsonObj2.put("delete_time", tpPhotoAlbumUser.getDeleteTime());
            jsonObj2.put("extend", tpPhotoAlbumUser.getExtend());
            jsonObj1.put("photo", jsonObj2);
            if (null != tpPhotoAlbumUser.getAdId()){
                TpPhotoAlbumAd tpPhotoAlbumAd = photoService.findOneAlbumAdById(tpPhotoAlbumUser.getAdId());
                if (null != tpPhotoAlbumAd){
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("ad_id", tpPhotoAlbumAd.getAdId());
                    jsonObject3.put("desc", tpPhotoAlbumAd.getDesc());
                    jsonObject3.put("model", tpPhotoAlbumAd.getModel());
                    jsonObject3.put("price", tpPhotoAlbumAd.getPrice());
                    jsonObject3.put("link", tpPhotoAlbumAd.getLink());
                    jsonObject3.put("ori_price", tpPhotoAlbumAd.getOriPrice());
                    jsonObject3.put("text_2", tpPhotoAlbumAd.getText2());
                    jsonObject3.put("text_3", tpPhotoAlbumAd.getText3());
                    jsonObj1.put("ad_info", jsonObject3);
                }
            }
            if (null != tpPhotoAlbumUser.getTmpId()){
                TpPhotoAlbumTmp tpPhotoAlbumTmp = photoService.findOneAlbumTmpById(tpPhotoAlbumUser.getTmpId());
                if (null != tpPhotoAlbumTmp){
                    JSONObject jsonObject4 = new JSONObject();
                    jsonObject4.put("id", tpPhotoAlbumTmp.getTmpId());
                    jsonObject4.put("image_url", tpPhotoAlbumTmp.getImageUrl());
                    jsonObject4.put("status", tpPhotoAlbumTmp.getStatus());
                    jsonObject4.put("sort", tpPhotoAlbumTmp.getSort());
                    jsonObject4.put("add_time", tpPhotoAlbumTmp.getAddTime());
                    jsonObject4.put("route_url", tpPhotoAlbumTmp.getRouteUrl());
                    jsonObject4.put("classify_id", tpPhotoAlbumTmp.getClassifyId());
                    jsonObj1.put("tmp_info", jsonObject4);
                }
            }
            List<TpPhotoAlbumUserImage> tpPhotoAlbumUserImages = photoService.findAllPhotoAlbumUserImageByPid(tpPhotoAlbumUser.getpId());
            JSONArray jsonArray = new JSONArray();
            for (TpPhotoAlbumUserImage tpPhotoAlbumUserImage : tpPhotoAlbumUserImages) {
                JSONObject jsonObject5 = new JSONObject();
                jsonObject5.put("image", tpPhotoAlbumUserImage.getImage());
                jsonObject5.put("desc", tpPhotoAlbumUserImage.getDesc());
                jsonArray.add(jsonObject5);
            }
            jsonObj1.put("image_list", jsonArray);
        }
        jsonObj.put("result",jsonObj1);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param p_id
     * @param request
     * @return
     * 删除相册
     */
    @RequestMapping(value="/photo_delete",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "删除相册", notes = "删除相册")
    public @ResponseBody String photoDelete(@RequestParam(required=true) String p_id, HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpPhotoAlbumUser tpPhotoAlbumUser = photoService.findOneAlbumUserById(p_id);
        if (null != tpPhotoAlbumUser){
            int deleteResult =  photoService.deletePhotoAlbumUser(p_id);
            if (deleteResult > 0){
                int deleteResultImage = photoService.deleteUserImageByPid(p_id);
                if (deleteResultImage <= 0){
                    jsonObj.put("status", "0");
                    jsonObj.put("msg", "删除失败，请稍后再试");
                    return jsonObj.toString();
                }
            }
        }
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param p_id
     * @param action
     * @param request
     * @return
     * 添加浏览\分享数
     */
    @RequestMapping(value="/add_pv_share",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "添加浏览\\分享数", notes = "添加浏览\\分享数")
    public @ResponseBody String addPvOrShare(@RequestParam(required=true) String p_id,
            @RequestParam(required=true) String action,
            HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpPhotoAlbumUser tpPhotoAlbumUser = photoService.findOneAlbumUserById(p_id);
        if (null != tpPhotoAlbumUser){
            if (action.equals("pv")){
                int updataPVResult = photoService.updataPhotoAlbumUserPv(p_id);
                if (updataPVResult <= 0){
                    jsonObj.put("status", "0");
                    jsonObj.put("msg", "请求失败，请稍后再试");
                    return jsonObj.toString();
                }
            }else if (action.equals("share")){
                int updataShareResult = photoService.updataPhotoAlbumUserShare(p_id);
                if (updataShareResult <= 0){
                    jsonObj.put("status", "0");
                    jsonObj.put("msg", "请求失败，请稍后再试");
                    return jsonObj.toString();
                }
            }
        }
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param p_id
     * @param content
     * @param request
     * @return
     * 相册评论
     */
    @RequestMapping(value="/photo_comment",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "相册评论", notes = "相册评论")
    public @ResponseBody String photoComment(@RequestParam(required=true) String p_id,
            @RequestParam(required=true) String content,
            HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpPhotoAlbumUser tpPhotoAlbumUser = photoService.findOneAlbumUserById(p_id);
        if (null != tpPhotoAlbumUser){
            TpPhotoAlbumComment tpPhotoAlbumComment = new TpPhotoAlbumComment();
            tpPhotoAlbumComment.setAddTime(new Date());
            tpPhotoAlbumComment.setContent(content);
            tpPhotoAlbumComment.setpId(Integer.parseInt(p_id));
            tpPhotoAlbumComment.setStatus(1);
            tpPhotoAlbumComment.setUserId(tpUsers.getUser_id());
            int insertComment = photoService.insertPhotoAlbumComment(tpPhotoAlbumComment);
            if(insertComment <= 0){
                jsonObj.put("status", "0");
                jsonObj.put("msg", "请求失败，请稍后再试");
                return jsonObj.toString();
            }
        }
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param p_id
     * @param request
     * @return
     * 评论列表
     */
    @RequestMapping(value="/photo_comment_list",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "评论列表", notes = "评论列表")
    public @ResponseBody String photoCommentList(@RequestParam(required=true) String p_id,
            HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpPhotoAlbumUser tpPhotoAlbumUser = photoService.findOneAlbumUserById(p_id);
        if (null != tpPhotoAlbumUser){
            List<TpPhotoAlbumComment> tpPhotoAlbumCommentList = photoService.findAllPhotoAlbumComment(p_id);
            for (TpPhotoAlbumComment tpPhotoAlbumComment : tpPhotoAlbumCommentList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", tpPhotoAlbumComment.getId());
                jsonObject.put("p_id", tpPhotoAlbumComment.getpId());
                jsonObject.put("user_id", tpPhotoAlbumComment.getUserId());
                jsonObject.put("add_time", tpPhotoAlbumComment.getAddTime());
                jsonObject.put("content", tpPhotoAlbumComment.getContent());
                jsonObject.put("status", tpPhotoAlbumComment.getStatus());
                if (null != tpPhotoAlbumComment.getUserId() && !"".equals(tpPhotoAlbumComment.getUserId())){
                    TpUsers tpUsers1 = tpUsersService.findOneByUserId(tpPhotoAlbumComment.getUserId());
                    if (null != tpUsers1){
                        jsonObject.put("nickname", tpUsers1.getNickname());
                        jsonObject.put("head_pic", tpUsers1.getHead_pic());
                    }
                }
                jsonArray.add(jsonObject);
            }
        }
        jsonObj.put("result", jsonArray);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

}
