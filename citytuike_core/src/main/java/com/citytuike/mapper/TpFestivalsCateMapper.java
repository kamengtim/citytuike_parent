package com.citytuike.mapper;

import com.citytuike.model.TpFestivalsCate;
import java.util.List;

public interface TpFestivalsCateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpFestivalsCate record);

    TpFestivalsCate selectByPrimaryKey(Integer id);

    List<TpFestivalsCate> selectAll();

    int updateByPrimaryKey(TpFestivalsCate record);

    TpFestivalsCate selectCate(String article_id);
}