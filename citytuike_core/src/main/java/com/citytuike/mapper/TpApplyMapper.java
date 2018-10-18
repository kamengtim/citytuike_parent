package com.citytuike.mapper;

import com.citytuike.model.TpApply;
import java.util.List;

public interface TpApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpApply record);

    TpApply selectByPrimaryKey(Integer id);

    List<TpApply> selectAll();

    int updateByPrimaryKey(TpApply record);
}