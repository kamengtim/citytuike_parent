package com.citytuike.mapper;

import com.citytuike.model.TpApplyCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpApplyCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpApplyCard record);

    TpApplyCard selectByPrimaryKey(Integer id);

    List<TpApplyCard> selectAll();

    int updateByPrimaryKey(TpApplyCard record);

    void deleteApplyPeople(@Param("user_id") Integer user_id, @Param("id") int id);
}