package com.citytuike.mapper;

import com.citytuike.model.TpBusinessOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpBusinessOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_order
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_order
     *
     * @mbggenerated
     */
    int insert(TpBusinessOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_order
     *
     * @mbggenerated
     */
    int insertSelective(TpBusinessOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_order
     *
     * @mbggenerated
     */
    TpBusinessOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TpBusinessOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_business_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TpBusinessOrder record);

    List<TpBusinessOrder> findAllBusinessOrderByPay(@Param("user_id") Integer user_id, @Param("pay_status") int pay_status);
}