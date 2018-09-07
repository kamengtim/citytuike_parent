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

    LimitPageList getTopUpLimitPageList(Integer page, int size);

    LimitPageList getApplyLimitPageList(Integer page, int size);

    TpAdTrade findTradeById(Integer trade_id);

    int insertAdApplyRegion(TpAdApplyRegion tpAdApplyRegion);

    int insertAdApplyMaterial(TpAdApplyMaterial tpAdApplyMaterial);

    List<TpAdApplyMaterial> findAdApplyMaterialByApplyId(Integer id);

    List<TpAdApplyRegion> findAdApplyRegionByApplyId(Integer id);

    int updateAdApply(TpAdApply tpAdApply1);
}
