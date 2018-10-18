package com.citytuike.mapper;

import com.citytuike.model.TpJoinUs;
import java.util.List;

public interface TpJoinUsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpJoinUs record);

    TpJoinUs selectByPrimaryKey(Integer id);

    List<TpJoinUs> selectAll();

    int updateByPrimaryKey(TpJoinUs record);
}