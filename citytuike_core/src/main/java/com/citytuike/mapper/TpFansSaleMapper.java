package com.citytuike.mapper;

import com.citytuike.model.TpFansSale;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpFansSaleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpFansSale record);

    TpFansSale selectByPrimaryKey(Integer id);

    List<TpFansSale> selectAll();

    int updateByPrimaryKey(TpFansSale record);

    int save(TpFansSale tpFansSale);
}