package com.citytuike.service;

import com.citytuike.mapper.TpDeviceQrMapper;
import com.citytuike.mapper.TpUsersMapper;
import com.citytuike.model.TpDeviceQr;
import com.citytuike.model.TpUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service@Transactional
public class TpDeviceQrServiceImpl implements TpDeviceQrService {
    private static double EARTH_RADIUS = 6371.393;
    @Autowired
    private TpDeviceQrMapper tpDeviceQrMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    @Override
    public String saveQR(Integer user_id,String latitude, String longitude) {
        TpDeviceQr tpDeviceQr = new TpDeviceQr();
        String scene_str = UUID.randomUUID().toString().substring(0, 32);
        tpDeviceQr.setQr_id(null);
        tpDeviceQr.setScene_str(scene_str);
        tpDeviceQr.setItem_type(2);
        tpDeviceQr.setItem_id(1);
        tpDeviceQr.setAdd_time((int)(new Date().getTime()/1000));
        tpDeviceQr.setStatus(0);
        TpUsers tpUsers = tpUsersMapper.getInviteCode(user_id);
        tpDeviceQr.setScan_user_id(0);
        tpDeviceQr.setScan_time(0);
        tpDeviceQr.setLat(new BigDecimal(latitude));
        tpDeviceQr.setLng(new BigDecimal(longitude));
        tpDeviceQrMapper.saveQr(tpDeviceQr.getQr_id(),tpDeviceQr.getScene_str(),tpDeviceQr.getItem_type(),tpDeviceQr.getItem_id(),tpDeviceQr.getAdd_time(),tpDeviceQr.getStatus(),tpUsers.getUser_id(),tpDeviceQr.getLat(),tpDeviceQr.getLng());
        String invite_code = tpUsers.getInvite_code();
        //生成二维码中要存储的信息
        String qrData = "https://m.citytuike.cn?scene_str_v2="+scene_str+"&invite_code="+invite_code;
        return qrData;
    }

    @Override
    public int selectStatus(String scene_str_v2) {
        int status = tpDeviceQrMapper.selectStatus(scene_str_v2);
        return status;
    }

    @Override
    public double updateQR(String scene_str_v2, String lat, String lng, int status,Integer user_id) {

        TpDeviceQr tpDeviceQr = new TpDeviceQr();
        tpDeviceQr.setScan_time((int)(new Date().getTime()/1000));
        tpDeviceQr.setStatus(1);
        TpDeviceQr tpDeviceQr1 = tpDeviceQrMapper.getLatAndLng(scene_str_v2);
        tpDeviceQr.setItem_type(2);
        tpDeviceQr.setItem_id(1);
        tpDeviceQrMapper.updateQR(tpDeviceQr.getItem_type(),tpDeviceQr.getItem_id(),scene_str_v2,user_id,user_id,tpDeviceQr.getScan_time(),tpDeviceQr.getStatus(),tpDeviceQr1.getLat(),tpDeviceQr1.getLng(),tpDeviceQr1.getAdd_time());
        BigDecimal latOld = tpDeviceQr1.getLat();
        BigDecimal lngOld = tpDeviceQr1.getLng();
        double a = rad(new BigDecimal(lat).doubleValue()) - rad(latOld.doubleValue());
        double b = rad(new BigDecimal(lng).doubleValue()) - rad(lngOld.doubleValue());
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(rad(new BigDecimal(lat).doubleValue()))*Math.cos(rad(latOld.doubleValue()))*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;

    }

    @Override
    public TpDeviceQr getLatAndLng(String scene_str_v2) {
        TpDeviceQr tpDeviceQr = tpDeviceQrMapper.getLatAndLng(scene_str_v2);
        return tpDeviceQr;
    }

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

}
