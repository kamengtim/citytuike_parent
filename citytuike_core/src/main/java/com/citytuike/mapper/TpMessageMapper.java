package com.citytuike.mapper;

import com.citytuike.model.TpMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpMessageMapper {
    int deleteByPrimaryKey(Integer message_id);

    int insert(TpMessage record);

    TpMessage selectByPrimaryKey(Integer message_id);

    List<TpMessage> selectAll();

    int updateByPrimaryKey(TpMessage record);

    List<TpMessage> selectMessageByMessageId(@Param("message_id") Integer message_id);

    int selectCount(Integer user_id);


    List<TpMessage> NewSelectMessageByMessageId(@Param("message_id") Integer message_id, @Param("category") Integer category);

    List<TpMessage> selectByPage(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("message_id") Integer message_id, @Param("cate") String cate, @Param("user_id") Integer user_id);

    List<TpMessage> selectMessage(@Param("category") int category, @Param("user_id") Integer user_id);

    void save(TpMessage tpMessage);
}