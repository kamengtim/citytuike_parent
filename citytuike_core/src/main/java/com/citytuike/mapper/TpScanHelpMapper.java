package com.citytuike.mapper;

import com.citytuike.model.TpScanHelp;
import java.util.List;

public interface TpScanHelpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpScanHelp record);

    TpScanHelp selectByPrimaryKey(Integer id);

    List<TpScanHelp> selectAll();

    int updateByPrimaryKey(TpScanHelp record);

    int fansHelp(Integer user_id);

    int toDayHelp(Integer invite_id);
}