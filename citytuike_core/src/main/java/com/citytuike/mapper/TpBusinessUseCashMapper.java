package com.citytuike.mapper;

import com.citytuike.model.TpBusinessUseCash;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpBusinessUseCashMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_use_cash
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_use_cash
     *
     * @mbggenerated
     */
    int insert(TpBusinessUseCash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_use_cash
     *
     * @mbggenerated
     */
    int insertSelective(TpBusinessUseCash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_use_cash
     *
     * @mbggenerated
     */
    TpBusinessUseCash selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_use_cash
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TpBusinessUseCash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_use_cash
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TpBusinessUseCash record);

    TpBusinessUseCash findBusinessUseCashByNumber(String number);

    List<TpBusinessUseCash> findBusinessUseCashByStatus(@Param("user_id") Integer user_id, @Param("tpye") String tpye, @Param("status") Integer status);

    int getCount();

    List<TpBusinessUseCash> selectByPage(@Param("user_id") Integer user_id, @Param("business_id") Integer business_id, @Param("number") String number, @Param("startPos") int startPos, @Param("pageSize") int pageSize);

    List<TpBusinessUseCash> selectByPage1(@Param("user_id") Integer user_id, @Param("business_id") Integer business_id, @Param("flag") Integer flag, @Param("startPos") int startPos, @Param("pageSize") int pageSize);

    int getUserCashCountByCash(@Param("cash_id") Integer cash_id);

    int updataUserCashFlagByCashId(@Param("flag")int flag, @Param("cashId")Integer cashId);

    TpBusinessUseCash findUseByUserAndNumberAndUseStatus(@Param("number") String number, @Param("user_id") Integer user_id, @Param("use_status") int use_status);

    int updataUseCashForUseStatus(@Param("number") String number, @Param("cash_id") Integer cash_id, @Param("code") String code, @Param("use_status") int use_status);

    int updataUseCashForFlag(@Param("number")String number, @Param("user_id")Integer user_id, @Param("use_status")int use_status);

    TpBusinessUseCash findUseCashByCashIdAndUseIdAndFalg(@Param("cash_id") int cash_id, @Param("user_id")Integer user_id, @Param("flag")Integer flag);

    int updataUseCashByUser(@Param("id")Integer id, @Param("user_id") Integer user_id,@Param("flag") int flag);
}