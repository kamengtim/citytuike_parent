package com.citytuike.service;

import com.citytuike.model.*;

import java.util.List;

public interface TpAdService {

    int getPageAvg(TpRegion tpRegion2);

    int insertAdApply(TpAdApply tpAdApply);

    TpAdApply findAdApplyByOrderSn(String apply_sn);

    List<TpAdTrade> findAllAdTrade();

    List<TpAdTrade> findAllAdTradeByParentId(Integer trade_id);

    int insertAdTopUp(TpAdTopUp tpAdTopUp);

    LimitPageList getTopUpLimitPageList(Integer user_id, Integer page, int size);

    LimitPageList getApplyLimitPageList(Integer user_id, Integer page, int size);

    TpAdTrade findTradeById(Integer trade_id);

    int insertAdApplyRegion(TpAdApplyRegion tpAdApplyRegion);

    int insertAdApplyMaterial(TpAdApplyMaterial tpAdApplyMaterial);

    List<TpAdApplyMaterial> findAdApplyMaterialByApplyId(Integer id);

    List<TpAdApplyRegion> findAdApplyRegionByApplyId(Integer id);

    int updateAdApply(TpAdApply tpAdApply1);

    TpAdApply findAdApplyByOrderSnAndStatus(String apply_sn, Integer user_id, String status);

    int updataApplyBystate(Integer adApplyId, String state);
}
