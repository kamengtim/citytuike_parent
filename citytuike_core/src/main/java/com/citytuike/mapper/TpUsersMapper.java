package com.citytuike.mapper;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TpUsersMapper {

	TpUsers findOneByLogo(@Param("username") String username, @Param("password") String password);

	int save(TpUsers tpUsers);

	int updateBytokenIn(TpUsers tpUsers);

	TpUsers findOneByToken(@Param("token") String token);

	int updateBytokenOut(TpUsers tpUsers);

	List<TpUserAddress> findAddressByUserId(@Param("user_id") Integer user_id);

	int insertUserAddress(TpUserAddress tpUserAddress);

	TpUserAddress findUserAddresById(@Param("id") Integer id);

	int updateUserAddress(TpUserAddress tpUserAddress);

	List<TpRegion> findAllByLevel(@Param("id") Integer id);

	int updateAddressToDefault(@Param("id") Integer id, @Param("i") int i);

	List<TpUserAddress> findIsDefaultAll(Integer user_id);

    TpUsers findOneByInvite(@Param("invite") String invite);

	TpUsers findOneByMobile(@Param("username")String username);

	TpUsers findOneByOpenId(@Param("openid")String openid);

    int insertUserUpLog(TpUserUpLog tpUserUpLog);

    int updateUserLevel(@Param("user_id")Integer user_id, @Param("level")Integer level);

	TpUsers findOneByUserId(@Param("user_id")Integer user_id);

	List<TpUsers> findAllByUserParentId(@Param("parent_id")Integer parent_id);

	BigDecimal selectCountMoney(Integer user_id);

	int selectRegTime(Integer user_id);

	List<TpUsers> selectParentId(Integer user_id);

	List<TpUsers> selectByPage(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("user_id") Integer user_id);

    int updateUserFrozenMoney(@Param("user_id")Integer user_id, @Param("frozenMoney")double frozenMoney);

    TpUsers findOneByImId(@Param("im_id")String im_id);

	int updateUserParent(TpUsers users);

	int countByParentId(@Param("user_id")Integer user_id);

	int countByParentIdDay(@Param("user_id")Integer user_id, @Param("startTime") int startTime, @Param("endTime")int endTime);

	int insertApplicationforBusinessCooperation(TpApplicationForBusinessCooperation tpApplication);

	void deleteAddress(@Param("user_id") Integer user_id, @Param("id") String id);

	void updateUserInfo(@Param("user_id") Integer user_id,@Param("head_pic") String head_pic, @Param("nickname") String nickname, @Param("qq") String qq, @Param("sex") String sex, @Param("birthday") String birthday, @Param("province") String province, @Param("city") String city, @Param("district") String district, @Param("email") String email, @Param("scene") String scene, @Param("wechat_qrcode") String wechat_qrcode, @Param("wechat") String wechat);

	void updateUserAndMobile(@Param("user_id") Integer user_id,@Param("head_pic") String head_pic, @Param("nickname") String nickname, @Param("qq") String qq, @Param("sex") String sex, @Param("birthday") String birthday, @Param("province") String province, @Param("city") String city, @Param("district") String district, @Param("email") String email, @Param("scene") String scene, @Param("wechat_qrcode") String wechat_qrcode, @Param("wechat") String wechat, @Param("mobile") String mobile);

	BigDecimal selectFrozen(Integer user_id);

	void updateUserMoney(@Param("user_id") Integer user_id, @Param("newUserMoney") BigDecimal newUserMoney);

    TpUsers getInviteCodeUserInfo(String invite_code);

    String selectAddrass(Integer user_id);

    String selectCity(Integer user_id);

    String selectDistrict(Integer user_id);

    int selectMobile(Integer user_id);

	void updatePwd(@Param("user_id") Integer user_id, @Param("new_password") String new_password);

	BigDecimal getSumMoneyDevice(Integer user_id);

	TpUsers UserMoney(Integer user_id);

	TpUsers incomeCount(Integer user_id);

	TpUsers selectUserByMobile(String mobile);

    List<TpSysMessage> findAllSysMessage();

	TpPlateMsg findPlatMsgByFlagd(@Param("flag")int flag);

	TpSysMessageUser findOneBySysIdAndUserId(@Param("id")Integer id, @Param("user_id")Integer user_id);

    TpRegion findRegionById(@Param("regions_id")Integer regions_id);

	List<TpRegion> findRegionByParentId(@Param("id")int id);

    TpUserWallet findWalletByUserId(@Param("user_id")Integer user_id);

    int updateUserWalletBalance(TpUserWallet tpUserWallet1);

	int insertUserFinance(TpUserFinance tpUserFinance);

    TpUsers getUserNameByMobile(String mobile);

    TpUsers getInviteCode(Integer user_id);

    int getPaperNum(Integer user_id);

	void update(TpUsers tpUsers);

    TpRegion findRegionByName(@Param("launch_address") String launch_address);

	TpUsers getToken(String token);

	List<TpUsers> income();

	List<TpUsers> sale();
}
