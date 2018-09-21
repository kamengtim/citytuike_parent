package com.citytuike.mapper;

import com.citytuike.model.TpDevice;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TpDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpDevice record);

    TpDevice selectByPrimaryKey(Integer id);

    List<TpDevice> selectAll();

    int updateByPrimaryKey(TpDevice record);

    int selectCountDevice(@Param("user_id") Integer user_id);

    List<TpDevice> selectByPage(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("user_id") Integer user_id);

    List<TpDevice> selectIncome(Integer user_id);

    List<TpDevice> selectParent(Integer user_id);

    List<TpDevice> findByCity(@Param("id") int id);

    List<TpDevice> statistics(Integer user_id);

    TpDevice getOnlyDevice(@Param("user_id") Integer user_id, @Param("device_id") String device_id, @Param("device_sn") String device_sn);

    void updateDeviceNumber(@Param("number") String number, @Param("device_sn") String device_sn);

    TpDevice selectDeviceBySn(String deviceSn);

    void insertDevice(@Param("user_id") Integer user_id, @Param("device_sn") String device_sn, @Param("province") String province, @Param("province1") String province1, @Param("city") String city, @Param("district") String district, @Param("landmark_picture") String landmark_picture);

    List<TpDevice> getHaveDeviceCity();

    TpDevice getUserDevice(@Param("productKey") String productKey, @Param("deviceName") String deviceName);

    TpDevice getDeviceId(@Param("lng") BigDecimal lng, @Param("lat") BigDecimal lat);

    TpDevice getDevice(Integer device_id);

    void update(TpDevice tpDevice);

    int toDayCountPaper(Integer id);

    int allToDayPaper();

    void updateType(TpDevice tpDevice);

    TpDevice getDeviceById(String device_id);

    void updateRunStatus(TpDevice tpDevice);

    void updateVersion(@Param("get_version") String get_version, @Param("imei") String imei);
}