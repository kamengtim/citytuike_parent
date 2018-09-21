package com.citytuike.mapper;

import com.citytuike.model.TpCartGift;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpCartGiftMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpCartGift record);

    TpCartGift selectByPrimaryKey(Integer id);

    List<TpCartGift> selectAll();

    int updateByPrimaryKey(TpCartGift record);

    int getCount(Integer user_id);

    List<TpCartGift> selectByPage(@Param("pageNow") int pageNow, @Param("pageSize") int pageSize, @Param("user_id") Integer user_id);
}