package com.citytuike.mapper;

import com.citytuike.model.TpUserFeedback;

import java.util.List;

public interface TpUserFeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpUserFeedback record);

    TpUserFeedback selectByPrimaryKey(Integer id);

    List<TpUserFeedback> selectAll();

    int updateByPrimaryKey(TpUserFeedback record);

    List fansTypeList(Integer user_id);
}