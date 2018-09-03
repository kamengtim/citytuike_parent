package com.citytuike.mapper;

import com.citytuike.model.TpApplyReport;
import java.util.List;

public interface TpApplyReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpApplyReport record);

    TpApplyReport selectByPrimaryKey(Integer id);

    List<TpApplyReport> selectAll();

    int updateByPrimaryKey(TpApplyReport record);
}