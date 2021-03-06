package com.citytuike.mapper;

import com.citytuike.model.TpFabulous;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpFabulousMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_fabulous
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_fabulous
     *
     * @mbggenerated
     */
    int insert(TpFabulous record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_fabulous
     *
     * @mbggenerated
     */
    int insertSelective(TpFabulous record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_fabulous
     *
     * @mbggenerated
     */
    TpFabulous selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_fabulous
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TpFabulous record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_fabulous
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TpFabulous record);

    List<TpFabulous> findAllFabulousByDynamicId(@Param("d_id") Integer d_id);
}