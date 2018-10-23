package com.citytuike.service;

import com.citytuike.mapper.TpAdMapper;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class TpAdServiceImpl implements TpAdService {
    @Autowired
    private TpAdMapper tpAdMapper;
    @Autowired
    private ITpDeviceService iTpDeviceService;

    @Override
    public int getPageAvg(TpRegion tpRegion2) {
        int pageAvg = 0;
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);    //得到前一天
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        List<TpDevice> tpDeviceList = iTpDeviceService.findByCity(tpRegion2.getId());
        for (TpDevice tpDevice : tpDeviceList) {
            List<TpStatisticsData> tpStatisticsData = tpAdMapper.findStatisticsDataByDeviceId(tpDevice.getId(), year, month, day);
            for (TpStatisticsData tpStatistticsData : tpStatisticsData) {
                num += tpStatistticsData.getInt_value();
            }
        }
        if (tpDeviceList.size() > 0){
            double avg = (double)num / tpDeviceList.size();
            pageAvg =  (int)Math.round(avg);
            System.out.println("地区为:" + tpRegion2.getId() +"  计数为:" + num + "  机器有:" + tpDeviceList.size() +  "  平均为:" + pageAvg);
        }
        return pageAvg;
    }

    @Override
    public int insertAdApply(TpAdApply tpAdApply) {
        return tpAdMapper.insertAdApply(tpAdApply);
    }

    @Override
    public TpAdApply findAdApplyByOrderSn(String apply_sn) {
        return tpAdMapper.findAdApplyByOrderSn(apply_sn);
    }

    @Override
    public List<TpAdTrade> findAllAdTrade() {
        return tpAdMapper.findAllAdTrade();
    }

    @Override
    public List<TpAdTrade> findAllAdTradeByParentId(Integer trade_id) {
        return tpAdMapper.findAllAdTradeByParentId(trade_id);
    }

    @Override
    public int insertAdTopUp(TpAdTopUp tpAdTopUp) {
        return tpAdMapper.insertAdTopUp(tpAdTopUp);
    }

    @Override
    public LimitPageList getTopUpLimitPageList(Integer user_id, Integer page1, int size) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpAdMapper.getTopUpCount(user_id);//获取总的记录数
        List<TpGoods> stuList=new ArrayList<TpGoods>();
        Page page=null;
        if(page1!=null){
            page=new Page(totalCount, page1);
            page.setPageSize(size);
            stuList=tpAdMapper.selectTopUpByPage(user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(size);
            stuList=tpAdMapper.selectTopUpByPage(user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public LimitPageList getApplyLimitPageList(Integer user_id, Integer page1, int size) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpAdMapper.getApplyCount(user_id);//获取总的记录数
        List<TpGoods> stuList=new ArrayList<TpGoods>();
        Page page=null;
        if(page1!=null){
            page=new Page(totalCount, page1);
            page.setPageSize(size);
            stuList=tpAdMapper.selectApplyByPage(user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(size);
            stuList=tpAdMapper.selectApplyByPage(user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public TpAdTrade findTradeById(Integer trade_id) {
        return tpAdMapper.findTradeById(trade_id);
    }

    @Override
    public int insertAdApplyRegion(TpAdApplyRegion tpAdApplyRegion) {
        return tpAdMapper.insertAdApplyRegion(tpAdApplyRegion);
    }

    @Override
    public int insertAdApplyMaterial(TpAdApplyMaterial tpAdApplyMaterial) {
        return tpAdMapper.insertAdApplyMaterial(tpAdApplyMaterial);
    }

    @Override
    public List<TpAdApplyMaterial> findAdApplyMaterialByApplyId(Integer id) {
        return tpAdMapper.findAdApplyMaterialByApplyId(id);
    }

    @Override
    public List<TpAdApplyRegion> findAdApplyRegionByApplyId(Integer id) {
        return tpAdMapper.findAdApplyRegionByApplyId(id);
    }

    @Override
    public int updateAdApply(TpAdApply tpAdApply1) {
        return tpAdMapper.updateAdApply(tpAdApply1);
    }

    @Override
    public TpAdApply findAdApplyByOrderSnAndStatus(String apply_sn, Integer user_id, String status) {
        return tpAdMapper.findAdApplyByOrderSnAndStatus(apply_sn, user_id, status);
    }

    @Override
    public int updataApplyBystate(Integer adApplyId, String state) {
        return tpAdMapper.updataApplyBystate(adApplyId, state);
    }
}
