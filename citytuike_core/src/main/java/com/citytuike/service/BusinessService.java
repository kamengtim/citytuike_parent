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

    List<TpBusinessShare> findBusinessShareByUserId(Integer user_id);

    int insertBusinessOrder(TpBusinessOrder tpBusinessOrder);

    int insertBusinessCash(TpBusinessCash tpBusinessCash);

    List<TpBusinessCashFace> findAllBusinessCashFace();

    List<TpBusinessOrder> findAllBusinessOrderByPay(Integer user_id, int pay_status);

    TpBusinessCash findBusinessCashById(int cash_id);

    int updataCashByThaw(TpBusinessCash tpBusinessCash1);

    JSONObject getBUsinessCashJson(TpBusinessCash tpBusinessCash);

    TpBusinessUseCash findBusinessUseCashByNumber(String number);

    List<TpBusinessUseCash> findBusinessUseCashByStatus(Integer user_id, String type, int status);

    LimitPageList getLimitPageUseCashList(Integer flag, String number, Integer p, Integer business_id, Integer user_id);

    List<TpBusinessType> findAllBusinessCommentByUserId(Integer user_id, Integer business_id);

    TpBusinessShare findBusinessShareByTag(Integer business_id, String tag);

    int updataShareByTag(Integer business_id, String tags);

    List<TpBusinessSave> findAllSaveByOrder(String orderSn);

    List<TpBusinessImages> findAllImagesByShare(Integer businessId);

    List<TpBusinessCash> findAllCashByShare(Integer businessId);

    int getUserCashCountByCash(Integer cash_id);

    TpBusinessSave findSavaByCashId(Integer cash_id);

    JSONObject getBusinessUserCashJson(TpBusinessUseCash tpbusinessUseCash);

    int updataUserCashFlagByCashId(int flag, Integer cashId);

    TpBusinessUseCash findUseCashById(int id);
}
