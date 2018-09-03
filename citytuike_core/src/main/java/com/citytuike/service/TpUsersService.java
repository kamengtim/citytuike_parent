package com.citytuike.service;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;

public interface TpUsersService {

	TpUsers findOneByLogo(String username, String password);

	int save(TpUsers tpUsers);

	int updateBytokenIn(TpUsers tpUsers);

	TpUsers findOneByToken(String token);

	int updateBytokenOut(TpUsers tpUsers);

	JSONObject getUserlJson(TpUsers tpUsers);

	List<TpUserAddress> findAddressByUserId(Integer user_id);

	int insertUserAddress(TpUserAddress tpUserAddress);

	TpUserAddress findUserAddresById(Integer id);

	int updateUserAddress(TpUserAddress tpUserAddress);

	List<TpRegion> findAllByLevel(Integer id);

	int updateAddressToDefault(Integer id, int i);

	List<TpUserAddress> findIsDefaultAll(Integer user_id);

    TpUsers findOneByInvite(String invite);

	TpUsers findOneByMobile(String username);

	TpUsers findOneByOpenId(String openid);

    int insertUserUpLog(TpUserUpLog tpUserUpLog);

    int updateUserLevel(Integer user_id, Integer level);

	TpUsers findOneByUserId(Integer user_id);

	List<TpUsers> findAllByUserParentId(Integer parent_id);
	BigDecimal selectCountMoney(Integer user_id);

	int selectRegTime(Integer user_id);

	LimitPageList getLimitPageList(Integer user_id, String page);

    int updateUserFrozenMoney(Integer user_id, double frozenMoney);

    TpUsers findOneByImId(String im_id);

	int updateUserParent(TpUsers users);

	int countByParentId(Integer user_id);

	int countByParentIdDay(Integer user_id, int startTime, int endTime);

	int insertApplicationforBusinessCooperation(TpApplicationForBusinessCooperation tpApplication);
	void updateUser(Integer user_id,String head_pic, String nickname, String qq, String sex, String birthday, String province, String city, String district, String email, String scene, String wechat_qrcode, String wechat);

	void deleteAddress(Integer user_id, String id);

	void updateUserAndMobile(Integer user_id,String head_pic, String nickname, String qq, String sex, String birthday, String province, String city, String district, String email, String scene, String wechat_qrcode, String wechat, String mobile);

	BigDecimal selectFrozen(Integer user_id);

	JSONObject getInviteCodeUserInfo(String invite_code);

    int selectMobile(Integer user_id);

	void updatePwd(Integer user_id,String new_password);

	BigDecimal getSumMoneyDevice(Integer user_id);

	JSONObject UserMoney(Integer user_id);

	JSONObject incomeCount(Integer user_id);

    TpPlateMsg findPlatMsgByFlagd(int flag);

	List<TpSysMessage> findAllSysMessage();

	TpSysMessageUser findOneBySysIdAndUserId(Integer id, Integer user_id);
}
