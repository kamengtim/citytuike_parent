package com.citytuike.mapper;

import com.citytuike.model.TpPaperTransfer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpPaperTransferMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpPaperTransfer record);

    TpPaperTransfer selectByPrimaryKey(Integer id);

    List<TpPaperTransfer> selectAll(Integer user_id);

    int updateByPrimaryKey(TpPaperTransfer record);

    int save(TpPaperTransfer tpPaperTransfer);

    List<TpPaperTransfer> selectList(@Param("bigStatus") String bigStatus, @Param("user_id") Integer user_id);

    TpPaperTransfer selectArr(int id);

    TpPaperTransfer selectToUser(@Param("user_id") Integer user_id, @Param("log_id") Integer log_id);

    int updateStatus(@Param("from_user_id") Integer from_user_id, @Param("log_id") String log_id, @Param("paper_number_allowance") Integer paper_number_allowance, @Param("number") Integer number);

    int addPaper(@Param("status") String status, @Param("paper_number_allowance") Integer paper_number_allowance, @Param("number") Integer number, @Param("log_id") Integer log_id, @Param("toUser") Integer toUser);
}