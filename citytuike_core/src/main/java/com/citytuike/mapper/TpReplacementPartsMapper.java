package com.citytuike.mapper;

import com.alibaba.fastjson.JSONObject;
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


    int getCount(@Param("status") Integer status);

    List<JSONObject> selectByPage(@Param("i") int i, @Param("i1") int i1, @Param("status") Integer status);
}