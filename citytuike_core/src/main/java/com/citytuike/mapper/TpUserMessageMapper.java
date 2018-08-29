package com.citytuike.mapper;

import com.citytuike.model.TpUserMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpUserMessageMapper {
    int deleteByPrimaryKey(Integer rec_id);

    int insert(TpUserMessage record);

    TpUserMessage selectByPrimaryKey(Integer rec_id);

    List<TpUserMessage> selectAll();

    int updateByPrimaryKey(TpUserMessage record);

    List<TpUserMessage> selectMessageByUser(@Param("user_id") Integer user_id);

    List<TpUserMessage> NewSelectMessageByUser(@Param("user_id") Integer user_id, @Param("cate") String cate);

    List<TpUserMessage> selectMessageByUserDetail(@Param("user_id") Integer user_id, @Param("rec_id") String rec_id);
}