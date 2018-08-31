package com.citytuike.mapper;


import com.citytuike.model.TpUserLevel;

import java.util.List;

public interface TpUserLevelMapper {
    int deleteByPrimaryKey(Short level_id);

    int insert(TpUserLevel record);

    TpUserLevel selectByPrimaryKey(Short level_id);

    List<TpUserLevel> selectAll();

    int updateByPrimaryKey(TpUserLevel record);

    String selectLevelName(Integer level);
}