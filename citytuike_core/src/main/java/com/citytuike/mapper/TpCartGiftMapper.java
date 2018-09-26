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

    TpCartGift getCartGift(@Param("id") int id, @Param("user_id") Integer user_id);

    int update(@Param("id") int id, @Param("user_id") Integer user_id, @Param("orderid") String orderid, @Param("taskid") String taskid, @Param("time") int time, @Param("mobile") String mobile);

    TpCartGift getCartGiftByUserId(Integer user_id);

    int getGifts(@Param("user_id") Integer user_id, @Param("id") int id, @Param("gift_type") int gift_type, @Param("gift_name") String gift_name, @Param("date") int date);

    List<TpCartGift> queryList(Integer user_id);

    TpCartGift useGifts(Integer user_id, int id);
}