package com.citytuike.mapper;

import com.citytuike.model.TpIndustry;

import java.util.List;

public interface TpIndustryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpIndustry record);

    TpIndustry selectByPrimaryKey(Integer id);

    List<TpIndustry> selectAll();

    int updateByPrimaryKey(TpIndustry record);

    List<TpIndustry> getIndustry();

    List<TpIndustry> getIndustrySon(Integer fid);
}