package com.citytuike.mapper;

import com.citytuike.model.TpReplacementParts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpReplacementPartsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpReplacementParts record);

    TpReplacementParts selectByPrimaryKey(Integer id);

    List<TpReplacementParts> selectAll();

    int updateByPrimaryKey(TpReplacementParts record);

    void insertReplacement(TpReplacementParts tpReplacementParts);

    int getCount();

    List<TpReplacementParts> selectByPage(@Param("startPos") int startPos, @Param("pageSize") int pageSize);
}