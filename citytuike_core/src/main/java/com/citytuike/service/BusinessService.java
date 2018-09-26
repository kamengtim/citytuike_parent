package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;

import java.util.List;

public interface BusinessService {
    int insertBusinessShare(TpBusinessShare tpBusinessShare);

    int insertBusinessImages(TpBusinessImages tpBusinessImages);

    List<TpBusinessType> findAllBusinessType();

    int insertBusinessComment(TpBusinessComment tpBusinessComment);

    List<TpBusinessShare> findNearByBusiness(int business_type, String geohash, int tuijian);

    JSONObject getBusinessShareJson(TpBusinessShare tpbusinessShare, int xpoint, int ypoint);

    TpBusinessShare findBusinessShareById(int id);

    List<TpBusinessComment> findAllBusinessCommentByBusinessId(int id);

    TpBusinessComment findOneCommentById(int id);

    TpBusinessTag findOneTagById(int id);

    TpBusinessCashFace findBusinessCashFaceById(int id);

    TpBusinessShare findBusinessShareByUserId(Integer user_id);

    int insertBusinessOrder(TpBusinessOrder tpBusinessOrder);

    int insertBusinessCash(TpBusinessCash tpBusinessCash);

    List<TpBusinessCashFace> findAllBusinessCashFace();

    List<TpBusinessOrder> findAllBusinessOrderByPay(Integer user_id, int i);

    TpBusinessCash findBusinessCashById(int cash_id);

    int updataCashByThaw(TpBusinessCash tpBusinessCash1);

    JSONObject getBUsinessCashJson(TpBusinessCash tpBusinessCash);

    TpBusinessUseCash findBusinessUseCashByNumber(String number);

    List<TpBusinessUseCash> findBusinessUseCashByStatus(Integer user_id, String status);

    LimitPageList getLimitPageUseCashList(Integer flag, String number, Integer p, Integer business_id, Integer user_id);

}
