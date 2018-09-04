package com.citytuike.mapper;

import com.citytuike.model.TpPaperLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpPaperLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpPaperLog record);

    TpPaperLog selectByPrimaryKey(Integer id);

    List<TpPaperLog> selectAll();

    int updateByPrimaryKey(TpPaperLog record);

    int selectCount(Integer device_id);

    List<TpPaperLog> selectByPage(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("device_id") String device_id);

    int selectCountToDay(Integer device_id);

    List<TpPaperLog> selectByPageToDay(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("device_id") String device_id);

    int selectCountToWeek(Integer device_id);

    List<TpPaperLog> selectByPageToWeek(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("device_id") String device_id);

    int selectCountToMonth(Integer device_id);

    List<TpPaperLog> selectByPageToMonth(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("device_id") String device_id);

    Integer savePaperLog(String device_sn);

    void insertPaperLog(TpPaperLog tpPaperLog);
}