package com.citytuike.mapper;

import com.citytuike.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpAdMapper {

    List<TpStatisticsData> findStatisticsDataByDeviceId(@Param("id") Integer id, @Param("year") int year, @Param("month") int month, @Param("day") int day);

    int insertAdApply(TpAdApply tpAdApply);

    TpAdApply findAdApplyByOrderSn(@Param("apply_sn")String apply_sn);

    List<TpAdTrade> findAllAdTrade();

    List<TpAdTrade> findAllAdTradeByParentId(@Param("trade_id")Integer trade_id);

    int insertAdTopUp(TpAdTopUp tpAdTopUp);

    int getTopUpCount(@Param("user_id") Integer user_id);

    List<TpGoods> selectTopUpByPage(@Param("user_id") Integer user_id, @Param("startPos") int startPos, @Param("pageSize") int pageSize);

    int getApplyCount(@Param("user_id")Integer user_id, @Param("status") String status);

    List<TpGoods> selectApplyByPage(@Param("user_id")Integer user_id, @Param("status") String status, @Param("startPos") int startPos, @Param("pageSize") int pageSize);

    TpAdTrade findTradeById(@Param("trade_id")Integer trade_id);

    int insertAdApplyRegion(TpAdApplyRegion tpAdApplyRegion);

    int insertAdApplyMaterial(TpAdApplyMaterial tpAdApplyMaterial);

    List<TpAdApplyMaterial> findAdApplyMaterialByApplyId(@Param("id")Integer id);

    List<TpAdApplyRegion> findAdApplyRegionByApplyId(@Param("id")Integer id);

    int updateAdApply(TpAdApply tpAdApply1);

    TpAdApply findAdApplyByOrderSnAndStatus(@Param("apply_sn") String apply_sn, @Param("user_id") Integer user_id, @Param("state") String state);

    int updataApplyBystate(@Param("adApplyId")Integer adApplyId, @Param("state") String state);

    int updateAdApplyByActivity(TpAdApply tpAdApply1);

    TpAdApply findAdApplyById(@Param("id")Integer id);

    TpAdCategory findAdCategoryById(@Param("cate")Integer cate);
}
