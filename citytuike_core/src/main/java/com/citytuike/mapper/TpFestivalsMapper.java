package com.citytuike.mapper;

import com.citytuike.model.TpFestivals;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpFestivalsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpFestivals record);

    TpFestivals selectByPrimaryKey(Integer id);

    List<TpFestivals> selectAll();

    int updateByPrimaryKey(TpFestivals record);

    TpFestivals selectFestivals(@Param("user_id") String user_id, @Param("ha_id") String ha_id);
}