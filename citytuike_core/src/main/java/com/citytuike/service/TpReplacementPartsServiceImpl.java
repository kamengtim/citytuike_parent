package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.*;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TpReplacementPartsServiceImpl implements TpReplacementPartsService {
    @Autowired
    private TpReplacementPartsMapper tpReplacementPartsMapper;
    @Autowired
    private ITpDeviceService tpDeviceService;
    @Autowired
    private TpOrderGoodsMapper tpOrderGoodsMapper;
    @Autowired
    private TpOrderMapper tpOrderMapper;
    @Autowired
    private TpGoodsMapper tpGoodsMapper;
    @Autowired
    private TpGoodsImagesMapper tpGoodsImagesMapper;

    @Override
    public void insertReplacement(TpDevice tpDevice, String name, String reason, String files, String address) {
        TpReplacementParts tpReplacementParts = new TpReplacementParts();
        tpReplacementParts.setUser_id(tpDevice.getUserId());
        tpReplacementParts.setName(name);
        tpReplacementParts.setReason(reason);
        tpReplacementParts.setAdd_time((int)(new Date().getTime()/1000));
        tpReplacementParts.setDevice_id(tpDevice.getId());
        tpReplacementParts.setFiles(files);
        tpReplacementParts.setAddress(address);
        tpReplacementParts.setStatus(1);
        tpReplacementParts.setInspect_status(1);
        tpReplacementPartsMapper.insertReplacement(tpReplacementParts);
    }

    @Override
    public LimitPageList getLimitPageList(Integer type, String pageNo, String pageSize) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int count = tpReplacementPartsMapper.getCount(type);
        List<JSONObject> stuList=new ArrayList<JSONObject>();
        Page page=null;
        if(pageNo != null){
            page=new Page(count, Integer.parseInt(pageNo));
            page.setPageSize(Integer.parseInt(pageSize));
            stuList=tpReplacementPartsMapper.selectByPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize),type);//从startPos开始，获取pageSize条数据
        }else{
            page=new Page(count, 1);
            page.setPageSize(Integer.parseInt(pageSize));
            stuList=tpReplacementPartsMapper.selectByPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize),type);
        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public JSONObject getJson(TpReplacementParts tpReplacementPart,Integer type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpReplacementPart.getId());
        jsonObject.put("user_id",tpReplacementPart.getUser_id());
        jsonObject.put("name",tpReplacementPart.getName());
        jsonObject.put("reason",tpReplacementPart.getReason());
        String dataFormat = transferLongToDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(tpReplacementPart.getAdd_time()));
        jsonObject.put("add_time",dataFormat);
        jsonObject.put("device_id",tpReplacementPart.getDevice_id());
        jsonObject.put("files",tpReplacementPart.getFiles());
        jsonObject.put("shipping_name",tpReplacementPart.getShipping_name());
        jsonObject.put("invoice_no",tpReplacementPart.getInvoice_no());
        jsonObject.put("shipping_time",tpReplacementPart.getShipping_time());
        jsonObject.put("address",tpReplacementPart.getAddress());
        /*jsonObject.put("status",tpReplacementPart.getStatus());*/
        jsonObject.put("linkman",tpReplacementPart.getLinkman());
        jsonObject.put("mobile",tpReplacementPart.getMobile());
        jsonObject.put("shipping_files",tpReplacementPart.getShipping_files());
       /* jsonObject.put("inspect_status",tpReplacementPart.getInspect_status());*/
        jsonObject.put("inspect_time",tpReplacementPart.getInspect_time());
        jsonObject.put("refute_reason",tpReplacementPart.getRefute_reason());
        jsonObject.put("refute_time",tpReplacementPart.getRefute_time());
        jsonObject.put("refute_imgs",tpReplacementPart.getRefute_imgs());
        jsonObject.put("confirm_time",tpReplacementPart.getConfirm_time());
        TpOrder tpOrder = tpOrderMapper.selectOrder(String.valueOf(tpReplacementPart.getTpDevice().getOrderId()));
        TpOrderGoods tpOrderGoods = tpOrderGoodsMapper.selectGoodsAndOrder(String.valueOf(tpReplacementPart.getTpDevice().getOrderId()));
        TpGoodsImages tpGoodsImages = tpGoodsImagesMapper.selectURL(tpOrderGoods.getGoods_id());
        //TpDevice tpDevice = tpDeviceService.getDeviceById((String)tpReplacementPart.get("order_id"));
        //String url = tpOrderGoodsMapper.getGoodsUrl(tpDevice.getOrder_id(),tpDevice.getGoods_id());
        jsonObject.put("shop_img",tpGoodsImages.getImage_url()==null?"":tpGoodsImages.getImage_url());
        jsonObject.put("order_id",tpOrder.getOrder_id());
        jsonObject.put("device_name",tpReplacementPart.getTpDevice().getDeviceName());
        if(type == 1){
            jsonObject.put("status_str",tpReplacementPart.getInspect_status());
        }else if(type == 0){
            jsonObject.put("status_str","申请中");
        }else{
            jsonObject.put("status_str",tpReplacementPart.getInspect_status());
        }
        return jsonObject;
    }
    private String transferLongToDate(String dateFormat,Long millSec){
        String result = null;
        Date date = new Date(millSec*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        result =  sdf.format(date);
        return result;

    }
}
