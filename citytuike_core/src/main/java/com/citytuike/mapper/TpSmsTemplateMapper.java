package com.citytuike.mapper;

import com.citytuike.model.TpSmsTemplate;

import java.util.List;

public interface TpSmsTemplateMapper {
    int deleteByPrimaryKey(Integer tpl_id);

    int insert(TpSmsTemplate record);

    TpSmsTemplate selectByPrimaryKey(Integer tpl_id);

    List<TpSmsTemplate> selectAll();

    int updateByPrimaryKey(TpSmsTemplate record);

    String selectScene(String scene);
}