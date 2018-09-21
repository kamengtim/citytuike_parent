package com.citytuike.mapper;

import com.citytuike.model.TpFans;
import com.citytuike.service.TpFansService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpFansMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpFans record);

    TpFans selectByPrimaryKey(Integer id);

    List<TpFans> selectAll();

    int updateByPrimaryKey(TpFans record);

    int getCount(@Param("area_id") String area_id, @Param("industry") String industry);

    List<TpFans> selectByPage(@Param("startPos") int startPos, @Param("pageSize") int pageSize);

    TpFans fansDetails(String id);

    List fansTypeList();

    void saveFansList(TpFans tpFans);

    TpFans getFansOrderSn(Integer id);
}