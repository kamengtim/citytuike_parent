package com.citytuike.mapper;

import com.citytuike.model.TpFestivalsContent;
import java.util.List;

public interface TpFestivalsContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpFestivalsContent record);

    TpFestivalsContent selectByPrimaryKey(Integer id);

    List<TpFestivalsContent> selectAll();

    int updateByPrimaryKey(TpFestivalsContent record);

    List<TpFestivalsContent> midAutumn(String ha_id);
}