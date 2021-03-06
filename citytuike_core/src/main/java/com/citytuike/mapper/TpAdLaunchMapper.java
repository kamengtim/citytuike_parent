package com.citytuike.mapper;

import com.citytuike.model.TpAdLaunch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpAdLaunchMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_ad_launch
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_ad_launch
     *
     * @mbggenerated
     */
    int insert(TpAdLaunch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_ad_launch
     *
     * @mbggenerated
     */
    int insertSelective(TpAdLaunch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_ad_launch
     *
     * @mbggenerated
     */
    TpAdLaunch selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_ad_launch
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TpAdLaunch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_ad_launch
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TpAdLaunch record);

    List<TpAdLaunch> findAllAdLaunchByDeviceId(@Param("deviceId") Integer deviceId, @Param("end_date") String end_date);
}