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
    public List<TpBusinessUseCash> findBusinessUseCashByStatus(Integer user_id, String status) {
        return tpBusinessUseCashMapper.findBusinessUseCashByStatus(user_id, status);
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
}
