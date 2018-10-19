package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.*;
import com.citytuike.model.*;
import com.citytuike.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private TpBusinessShareMapper tpBusinessShareMapper;
    @Autowired
    private TpBusinessImagesMapper tpBusinessImagesMapper;
    @Autowired
    private TpBusinessTypeMapper tpBusinessTypeMapper;
    @Autowired
    private TpBusinessCommentMapper tpBusinessCommentMapper;
    @Autowired
    private TpBusinessTagMapper tpBusinessTagMapper;
    @Autowired
    private TpBusinessCashFaceMapper tpBusinessCashFaceMapper;
    @Autowired
    private TpBusinessOrderMapper tpBusinessOrderMapper;
    @Autowired
    private TpBusinessCashMapper tpBusinessCashMapper;
    @Autowired
    private TpBusinessUseCashMapper tpBusinessUseCashMapper;
    @Autowired
    private TpBusinessSaveMapper tpBusinessSaveMapper;
    @Autowired
    private TpBusinessDiscountMapper tpBusinessDiscountMapper;
    @Override
    public int insertBusinessShare(TpBusinessShare tpBusinessShare) {
        return tpBusinessShareMapper.insert(tpBusinessShare);
    }

    @Override
    public int insertBusinessImages(TpBusinessImages tpBusinessImages) {
        return tpBusinessImagesMapper.insert(tpBusinessImages);
    }

    @Override
    public List<TpBusinessType> findAllBusinessType() {
        return tpBusinessTypeMapper.findAllBusinessType();
    }

    @Override
    public int insertBusinessComment(TpBusinessComment tpBusinessComment) {
        return tpBusinessCommentMapper.insert(tpBusinessComment);
    }

    @Override
    public List<TpBusinessShare> findNearByBusiness(int business_type, String geohash, int tuijian) {
        return tpBusinessShareMapper.findNearByBusiness(business_type, geohash, tuijian);
    }

    @Override
    public JSONObject getBusinessShareJson(TpBusinessShare tpbusinessShare, int xpoint, int ypoint) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", tpbusinessShare.getId());
        jsonObject.put("business_type", tpbusinessShare.getBusinessType());
        jsonObject.put("business_name", tpbusinessShare.getBusinessName());
        jsonObject.put("name", tpbusinessShare.getName());
        jsonObject.put("cus_nums", tpbusinessShare.getCusNums());
        jsonObject.put("business_start_time", tpbusinessShare.getBusinessStartTime());
        jsonObject.put("business_end_time", tpbusinessShare.getBusinessEndTime());
        jsonObject.put("mobile", tpbusinessShare.getMobile());
        jsonObject.put("xpoint", tpbusinessShare.getXpoint());
        jsonObject.put("ypoint", tpbusinessShare.getYpoint());
        jsonObject.put("location_area", tpbusinessShare.getLocationArea());
        jsonObject.put("address", tpbusinessShare.getAddress());
        jsonObject.put("tag", tpbusinessShare.getTag());
        jsonObject.put("create_time", tpbusinessShare.getCreateTime());
        jsonObject.put("geohash", tpbusinessShare.getGeohash());
        jsonObject.put("tuijian", tpbusinessShare.getTuijian());
        jsonObject.put("dongjie", tpbusinessShare.getDongjie());
        jsonObject.put("user_id", tpbusinessShare.getUserId());
        jsonObject.put("flag", tpbusinessShare.getFlag());
        jsonObject.put("distance", Util.getDistance((double)xpoint, (double)ypoint, Double.parseDouble(tpbusinessShare.getXpoint()),
                Double.parseDouble(tpbusinessShare.getYpoint())));
        return jsonObject;
    }

    @Override
    public TpBusinessShare findBusinessShareById(int id) {
        return tpBusinessShareMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TpBusinessComment> findAllBusinessCommentByBusinessId(int id) {
        return tpBusinessCommentMapper.findAllBusinessCommentByBusinessId(id);
    }

    @Override
    public TpBusinessComment findOneCommentById(int id) {
        return tpBusinessCommentMapper.selectByPrimaryKey(id);
    }

    @Override
    public TpBusinessTag findOneTagById(int id) {
        return tpBusinessTagMapper.selectByPrimaryKey(id);
    }

    @Override
    public TpBusinessCashFace findBusinessCashFaceById(int id) {
        return tpBusinessCashFaceMapper.selectByPrimaryKey(id);
    }

    @Override
    public TpBusinessShare findBusinessShareByUserId(Integer user_id) {
        return tpBusinessShareMapper.findBusinessShareByUserId(user_id);
    }

    @Override
    public int insertBusinessOrder(TpBusinessOrder tpBusinessOrder) {
        return tpBusinessOrderMapper.insert(tpBusinessOrder);
    }

    @Override
    public int insertBusinessCash(TpBusinessCash tpBusinessCash) {
        return tpBusinessCashMapper.insert(tpBusinessCash);
    }

    @Override
    public List<TpBusinessCashFace> findAllBusinessCashFace() {
        return tpBusinessCashFaceMapper.findAllBusinessCashFace();
    }

    @Override
    public List<TpBusinessOrder> findAllBusinessOrderByPay(Integer user_id, int pay_status) {
        return tpBusinessOrderMapper.findAllBusinessOrderByPay(user_id, pay_status);
    }

    @Override
    public TpBusinessCash findBusinessCashById(int id) {
        return tpBusinessCashMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updataCashByThaw(TpBusinessCash tpBusinessCash1) {
        return tpBusinessCashMapper.updateByPrimaryKeySelective(tpBusinessCash1);
    }

    @Override
    public JSONObject getBUsinessCashJson(TpBusinessCash tpBusinessCash) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", tpBusinessCash.getId());
        jsonObject.put("skin", tpBusinessCash.getSkin());
        jsonObject.put("business_id", tpBusinessCash.getBusinessId());
        jsonObject.put("business_name", tpBusinessCash.getBusinessName());
        jsonObject.put("price", tpBusinessCash.getPrice());
        jsonObject.put("fullsub_price", tpBusinessCash.getFullsubPrice());
        jsonObject.put("price_date", tpBusinessCash.getPriceDate());
        jsonObject.put("use_flag", tpBusinessCash.getUseFlag());
        jsonObject.put("launch_address", tpBusinessCash.getLaunchAddress());
        jsonObject.put("launch_id", tpBusinessCash.getLaunchId());
        jsonObject.put("launch_date_start", tpBusinessCash.getLaunchDateStart());
        jsonObject.put("launch_date_end", tpBusinessCash.getLaunchDateEnd());
        jsonObject.put("baozhengjin", tpBusinessCash.getBaozhengjin());
        jsonObject.put("thaw_mess", tpBusinessCash.getThawMess());
        jsonObject.put("jie_time", tpBusinessCash.getJieTime());
        return jsonObject;
    }

    @Override
    public TpBusinessUseCash findBusinessUseCashByNumber(String number) {
        return tpBusinessUseCashMapper.findBusinessUseCashByNumber(number);
    }

    @Override
    public List<TpBusinessUseCash> findBusinessUseCashByStatus(Integer user_id, String type, int status) {
        return tpBusinessUseCashMapper.findBusinessUseCashByStatus(user_id, type, status);
    }

    @Override
    public LimitPageList getLimitPageUseCashList(Integer flag, String number, Integer p, Integer business_id, Integer user_id) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpBusinessUseCashMapper.getCount();//获取总的记录数
        List<TpBusinessUseCash> stuList=new ArrayList<TpBusinessUseCash>();
        Page page=null;
        if (flag == 1){
            flag = null;
        }
        if(p!=null){
            page=new Page(totalCount, p);
            page.setPageSize(10);
            if (!"".equals(number)){
                stuList=tpBusinessUseCashMapper.selectByPage(user_id, business_id, number, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
            }else{
                stuList=tpBusinessUseCashMapper.selectByPage1(user_id, business_id, flag, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
            }
        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(10);
            if (!"".equals(number)){
                stuList=tpBusinessUseCashMapper.selectByPage(user_id, business_id, number, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
            }else{
                stuList=tpBusinessUseCashMapper.selectByPage1(user_id, business_id, flag, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
            }
        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public List<TpBusinessType> findAllBusinessCommentByUserId(Integer user_id, Integer business_id) {
        return tpBusinessCommentMapper.findAllBusinessCommentByUserId(user_id, business_id);
    }

    @Override
    public TpBusinessShare findBusinessShareByTag(Integer business_id, String tag) {
        return tpBusinessShareMapper.findBusinessShareByTag(business_id, tag);
    }

    @Override
    public int updataShareByTag(Integer business_id, String tags) {
        return tpBusinessShareMapper.updataShareByTag(business_id, tags);
    }

    @Override
    public List<TpBusinessSave> findAllSaveByOrder(String orderSn) {
        return tpBusinessSaveMapper.findAllSaveByOrder(orderSn);
    }

    @Override
    public List<TpBusinessImages> findAllImagesByShare(Integer businessId) {
        return tpBusinessImagesMapper.findAllImagesByShare(businessId);
    }

    @Override
    public List<TpBusinessCash> findAllCashByShare(Integer businessId) {
        return tpBusinessCashMapper.findAllCashByShare(businessId);
    }

    @Override
    public int getUserCashCountByCash(Integer cash_id) {
        return tpBusinessUseCashMapper.getUserCashCountByCash(cash_id);
    }

    @Override
    public TpBusinessSave findSavaByCashId(Integer cash_id) {
        return tpBusinessSaveMapper.findSavaByCashId(cash_id);
    }

    @Override
    public JSONObject getBusinessUserCashJson(TpBusinessUseCash tpbusinessUseCash) {
        JSONObject useCashObj = new JSONObject();
        useCashObj.put("id", tpbusinessUseCash.getId());
        useCashObj.put("user_id", tpbusinessUseCash.getUserId());
        useCashObj.put("cash_id", tpbusinessUseCash.getCashId());
        useCashObj.put("number", tpbusinessUseCash.getNumber());
        useCashObj.put("business_id", tpbusinessUseCash.getBusinessId());
        useCashObj.put("flag", tpbusinessUseCash.getFlag());
        useCashObj.put("add_time", tpbusinessUseCash.getAddTime());
        useCashObj.put("codes", tpbusinessUseCash.getCodes());
        useCashObj.put("userUseId", tpbusinessUseCash.getUserUseId());
        useCashObj.put("use_status", tpbusinessUseCash.getUseStatus());
        return useCashObj;
    }

    @Override
    public int updataUserCashFlagByCashId(int flag, Integer cashId) {
        return tpBusinessUseCashMapper.updataUserCashFlagByCashId(flag, cashId);
    }

    @Override
    public TpBusinessUseCash findUseCashById(int id) {
        return tpBusinessUseCashMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TpBusinessCash> findAllCashByShareAndThawflag(Integer business_id, int thaw_flag) {
        return tpBusinessCashMapper.findAllCashByShareAndThawflag(business_id, thaw_flag);
    }

    @Override
    public TpBusinessSave findSaveByBUsinessIdAndCashId(Integer business_id, Integer cash_id) {
        return tpBusinessSaveMapper.findSaveByBUsinessIdAndCashId(business_id, cash_id);
    }

    @Override
    public int insertBusinessDiscount(TpBusinessDiscount tpBusinessDiscount) {
        return tpBusinessDiscountMapper.insert(tpBusinessDiscount);
    }

    @Override
    public int insertBUsinessUseCash(TpBusinessUseCash tpBusinessUseCash) {
        return tpBusinessUseCashMapper.insert(tpBusinessUseCash);
    }

    @Override
    public LimitPageList getLimitPageDiscountListByBusinessId(Integer business_id, Integer p) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpBusinessDiscountMapper.getCountByBusinessId(business_id);//获取总的记录数
        List<TpBusinessUseCash> stuList=new ArrayList<TpBusinessUseCash>();
        Page page=null;
        if(p!=null){
            page=new Page(totalCount, p);
            page.setPageSize(10);
            stuList=tpBusinessDiscountMapper.selectByPageByBusinessId(business_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(10);
            stuList=tpBusinessDiscountMapper.selectByPageByBusinessId(business_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public JSONObject getBusinessDiscountJson(TpBusinessDiscount tpDisCount) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", tpDisCount.getId());
        jsonObject.put("business_id", tpDisCount.getBusinessId());
        jsonObject.put("business_name", tpDisCount.getBusinessName());
        jsonObject.put("business_address", tpDisCount.getBusinessAddress());
        jsonObject.put("price", tpDisCount.getPrice());
        jsonObject.put("discount_price", tpDisCount.getDiscountPrice());
        jsonObject.put("valid_date", tpDisCount.getValidDate());
        jsonObject.put("launch_address", tpDisCount.getLaunchAddress());
        jsonObject.put("launch_id", tpDisCount.getLaunchId());
        jsonObject.put("launch_date_start", Util.stampToDate1(tpDisCount.getLaunchDateStart() + ""));
        jsonObject.put("launch_date_end", Util.stampToDate1(tpDisCount.getLaunchDateEnd() + ""));
        jsonObject.put("nums", tpDisCount.getNums());
        jsonObject.put("add_time", tpDisCount.getAddTime());
        jsonObject.put("image", tpDisCount.getImage());
        jsonObject.put("status", tpDisCount.getStatus());
        int useCount = tpBusinessUseCashMapper.getUserCashCountByCash(tpDisCount.getId());
        jsonObject.put("use_count", useCount);
        return jsonObject;
    }

    @Override
    public TpBusinessUseCash findUseByUserAndNumberAndUseStatus(String number, Integer user_id, int use_status) {
        return tpBusinessUseCashMapper.findUseByUserAndNumberAndUseStatus(number, user_id, use_status);
    }

    @Override
    public int updataUseCashForUseStatus(String number, Integer cash_id, String code, int use_status) {
        return tpBusinessUseCashMapper.updataUseCashForUseStatus(number, cash_id, code, use_status);
    }

    @Override
    public int updataUseCashForFlag(String number, Integer user_id, int use_status) {
        return tpBusinessUseCashMapper.updataUseCashForFlag(number, user_id, use_status);
    }


}
