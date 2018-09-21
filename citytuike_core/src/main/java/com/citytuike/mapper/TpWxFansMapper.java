package com.citytuike.mapper;

import com.citytuike.model.TpWxFans;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpWxFansMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpWxFans record);

    TpWxFans selectByPrimaryKey(Integer id);

    List<TpWxFans> selectAll();

    int updateByPrimaryKey(TpWxFans record);

    List<TpWxFans> getWxFans(@Param("user_id") Integer user_id, @Param("id") Integer id);

    List<TpWxFans> getWxFansByWx(@Param("user_id") Integer user_id, @Param("id") Integer id, @Param("ref_date") String ref_date);


    List<TpWxFans> addWx(@Param("user_id") Integer user_id, @Param("id") Integer id, @Param("ref_date") String ref_date);


    int updateWx( @Param("id1") Integer id, @Param("ref_date") String ref_date, @Param("add_time") Integer add_time, @Param("count_ref_date") String count_ref_date, @Param("cumulate_user") Integer cumulate_user, @Param("user_id") Integer user_id);

    int updateWxNew(@Param("id") Integer uid, @Param("ref_date") String ref_date, @Param("canceluser") Integer canceluser, @Param("add_time") Integer add_time, @Param("count_ref_date") String count_ref_date, @Param("cumulate_user") Integer cumulate_user, @Param("user_id") Integer user_id, @Param("newuser") Integer newuser, @Param("need_fans_id") Integer id);
}