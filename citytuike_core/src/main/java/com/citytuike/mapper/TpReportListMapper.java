package com.citytuike.mapper;


import com.citytuike.model.TpReportList;

import java.util.List;

public interface TpReportListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpReportList record);

    TpReportList selectByPrimaryKey(Integer id);

    List<TpReportList> selectAll();

    int updateByPrimaryKey(TpReportList record);

    List<TpReportList> getSendReport(Integer user_id);
}