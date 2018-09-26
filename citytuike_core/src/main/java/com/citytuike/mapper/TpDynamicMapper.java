package com.citytuike.mapper;

import com.citytuike.model.TpDynamic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpDynamicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dynamic
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dynamic
     *
     * @mbggenerated
     */
    int insert(TpDynamic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dynamic
     *
     * @mbggenerated
     */
    int insertSelective(TpDynamic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dynamic
     *
     * @mbggenerated
     */
    TpDynamic selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dynamic
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TpDynamic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dynamic
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TpDynamic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dynamic
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TpDynamic record);

    int getCount(@Param("user_id") Integer user_id);

    List<TpDynamic> selectByPage(@Param("startPos")int startPos, @Param("pageSize")int pageSize, @Param("user_id")Integer user_id);
}