package com.citytuike.mapper;

import com.citytuike.model.TpDeviceQr;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TpDeviceQrMapper {
    int deleteByPrimaryKey(Integer qr_id);

    int insert(TpDeviceQr record);

    TpDeviceQr selectByPrimaryKey(Integer qr_id);

    List<TpDeviceQr> selectAll();

    int updateByPrimaryKey(TpDeviceQr record);

    void saveQr(@Param("qr_id") Integer qr_id, @Param("scene_str") String scene_str, @Param("item_type") Integer item_type, @Param("item_id") Integer item_id, @Param("add_time") Integer add_time, @Param("status") Integer status, @Param("user_id") Integer user_id, @Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng);

    int selectStatus(String scene_str_v2);

    void updateQR(@Param("scene_str_v2") String scene_str_v2, @Param("user_id") Integer user_id, @Param("user_id1") Integer user_id1, @Param("scan_time") Integer scan_time, @Param("status") Integer status);

}