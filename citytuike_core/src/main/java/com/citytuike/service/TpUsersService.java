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

    TpRegion findRegionById(Integer regions_id);

	List<TpRegion> findRegionByParentId(int id);

    TpUserWallet findWalletByUserId(Integer user_id);

    int updateUserWalletBalance(TpUserWallet tpUserWallet1);

	int insertUserFinance(TpUserFinance tpUserFinance);

    TpRegion findRegionByName(String launch_address);

	TpUsers getToken(String token);

	List<TpUsers> income();

	List<TpUsers> sale();

    TpUsers getInviteCode(String invite_code);

	TpUsers getUserInfo(String user_id);

    int updatePayPwd(String password,Integer user_id);

	TpUsers getMobile(String mobile);

	int updatePassword(Integer user_id, String confirm_password);

	void updateSetPass(Integer user_id);
}
