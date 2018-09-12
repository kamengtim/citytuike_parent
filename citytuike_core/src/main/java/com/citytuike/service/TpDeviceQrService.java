package com.citytuike.service;

import com.citytuike.model.TpDeviceQr;

public interface TpDeviceQrService {
    String saveQR(Integer user_id, String latitude, String longitude);

    int selectStatus(String scene_str_v2);

    double updateQR(String scene_str_v2, String lat, String lng, int status, Integer user_id);

    TpDeviceQr getLatAndLng(String scene_str_v2);
}
