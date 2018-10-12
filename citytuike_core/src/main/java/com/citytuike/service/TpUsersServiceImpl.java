package com.citytuike.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.citytuike.mapper.*;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpUsersMapper;
import org.springframework.transaction.annotation.Transactional;

@Service@Transactional
public class TpUsersServiceImpl implements TpUsersService{

	@Autowired
	private TpUsersMapper tpUsersMapper;
	@Autowired
	private TpDeviceMapper tpDeviceMapper;
	@Autowired
	private TpUserLevelMapper tpUserLevelMapper;
	@Autowired
	private TpDynamicMapper tpDynamicMapper;
	@Autowired
	private TpReplayMapper tpReplayMapper;
	@Autowired
	private TpFabulousMapper tpFabulousMapper;


	public TpUsers findOneByLogo(String username, String password) {
		return tpUsersMapper.findOneByLogo(username, password);
	}

	public int save(TpUsers tpUsers) {
		return tpUsersMapper.save(tpUsers);
	}

	public int updateBytokenIn(TpUsers tpUsers) {
		return tpUsersMapper.updateBytokenIn(tpUsers);
	}

	public TpUsers findOneByToken(String token) {
		return tpUsersMapper.findOneByToken(token);
	}

	public int updateBytokenOut(TpUsers tpUsers) {
		return tpUsersMapper.updateBytokenOut(tpUsers);
	}

	public JSONObject getUserlJson(TpUsers tpUsers) {
		JSONObject data = new JSONObject();
		data.put("user_id", tpUsers.getUser_id());
		data.put("email", tpUsers.getEmail());
		data.put("password", tpUsers.getPassword());
		data.put("paypwd", tpUsers.getPaypwd());
		data.put("sex", tpUsers.getSex());
		data.put("birthday", tpUsers.getBirthday());
		data.put("user_money", tpUsers.getUser_money());
		data.put("frozen_money", tpUsers.getFrozen_money());
		data.put("distribut_money", tpUsers.getDistribut_money());
		data.put("underling_number", tpUsers.getUnderling_number());
		data.put("pay_points", tpUsers.getPay_points());
		data.put("address_id", tpUsers.getAddress_id());
		data.put("reg_time", tpUsers.getReg_time());
		data.put("last_login", tpUsers.getLast_login());
		data.put("last_ip", tpUsers.getLast_ip());
		data.put("qq", tpUsers.getQq());
		data.put("mobile", tpUsers.getMobile());
		data.put("mobile_validated", tpUsers.getMobile_validated());
		data.put("oauth", tpUsers.getOauth());
		data.put("openid", tpUsers.getOpenid());
		data.put("unionid", tpUsers.getUnionid());
		data.put("head_pic", tpUsers.getHead_pic());
		data.put("province", tpUsers.getProvince());
		data.put("city", tpUsers.getCity());
		data.put("district", tpUsers.getDistrict());
		data.put("email_validated", tpUsers.getEmail_validated());
		data.put("nickname", tpUsers.getNickname());
		data.put("level", tpUsers.getLevel());
		data.put("discount", tpUsers.getDiscount());
		data.put("total_amount", tpUsers.getTotal_amount());
		data.put("is_lock", tpUsers.getIs_lock());
		data.put("is_distribut", tpUsers.getIs_distribut());
		data.put("first_leader", tpUsers.getFirst_leader());
		data.put("second_leader", tpUsers.getSecond_leader());
		data.put("third_leader", tpUsers.getThird_leader());
		data.put("token", tpUsers.getToken());
		data.put("message_mask", tpUsers.getMessage_mask());
		data.put("push_id", tpUsers.getPush_id());
		data.put("distribut_level", tpUsers.getDistribut_level());
		data.put("is_vip", tpUsers.getIs_vip());
		data.put("invite_code", tpUsers.getInvite_code());
		data.put("relation", tpUsers.getRelation());
		data.put("sale_number", tpUsers.getSale_number());
		data.put("parent_id", tpUsers.getParent_id());
		data.put("wechat", tpUsers.getWechat());
		data.put("wechat_qrcode", tpUsers.getWechat_qrcode());
		data.put("set_pass", tpUsers.getSet_pass());
		data.put("im_id", tpUsers.getIm_id());
		data.put("im_pwd", tpUsers.getIm_pwd());
		return data;
	}

	public List<TpUserAddress> findAddressByUserId(Integer user_id) {
		return tpUsersMapper.findAddressByUserId(user_id);
	}

	public int insertUserAddress(TpUserAddress tpUserAddress) {
		return tpUsersMapper.insertUserAddress(tpUserAddress);
	}

	public TpUserAddress findUserAddresById(Integer id) {
		return tpUsersMapper.findUserAddresById(id);
	}

	public int updateUserAddress(TpUserAddress tpUserAddress) {
		return tpUsersMapper.updateUserAddress(tpUserAddress);
	}

	public List<TpRegion> findAllByLevel(Integer id) {
		return tpUsersMapper.findAllByLevel(id);
	}

	public int updateAddressToDefault(Integer id, int i) {
		return tpUsersMapper.updateAddressToDefault(id, i);
	}

	public List<TpUserAddress> findIsDefaultAll(Integer user_id) {
		return tpUsersMapper.findIsDefaultAll(user_id);
	}

	public TpUsers findOneByInvite(String invite) {
		return tpUsersMapper.findOneByInvite(invite);
	}

	public TpUsers findOneByMobile(String username) {
		return tpUsersMapper.findOneByMobile(username);
	}

	public TpUsers findOneByOpenId(String openid) {
		return tpUsersMapper.findOneByOpenId(openid);
	}

	public int insertUserUpLog(TpUserUpLog tpUserUpLog) {
		return tpUsersMapper.insertUserUpLog(tpUserUpLog);
	}

	public int updateUserLevel(Integer user_id, Integer level) {
		return tpUsersMapper.updateUserLevel(user_id, level);
	}

	public TpUsers findOneByUserId(Integer user_id) {
		return tpUsersMapper.findOneByUserId(user_id);
	}

	public List<TpUsers> findAllByUserParentId(Integer parent_id) {
		return tpUsersMapper.findAllByUserParentId(parent_id);
	}

	public BigDecimal selectCountMoney(Integer user_id) {
		return tpUsersMapper.selectCountMoney(user_id);
	}

	public int selectRegTime(Integer user_id) {
		return tpUsersMapper.selectRegTime(user_id);
	}

	public List<TpUsers> getParentId(Integer user_id) {

		return tpUsersMapper.selectParentId(user_id);
	}

	public LimitPageList getLimitPageList(Integer user_id, String page) {
		LimitPageList limitPageList = new LimitPageList();
		int totalCount = tpDeviceMapper.selectCountDevice(user_id);
		List<TpUsers> stuList = new ArrayList<TpUsers>();
		Page PageSize = null;
		if (page != null) {
			PageSize = new Page(totalCount, Integer.valueOf(page));
			PageSize.setPageSize(10);
			stuList = tpUsersMapper.selectByPage(PageSize.getStartPos(), PageSize.getPageSize(), user_id);
		} else {
			PageSize = new Page(totalCount, 1);
			PageSize.setPageSize(10);
			stuList = tpUsersMapper.selectByPage(PageSize.getStartPos(), PageSize.getPageSize(), user_id);
		}
		limitPageList.setPage(PageSize);
		limitPageList.setList(stuList);
		return limitPageList;
	}

	public int updateUserFrozenMoney(Integer user_id, double frozenMoney) {
		return tpUsersMapper.updateUserFrozenMoney(user_id, frozenMoney);
	}

	@Override
	public TpUsers findOneByImId(String im_id) {
		return tpUsersMapper.findOneByImId(im_id);
	}

	@Override
	public int updateUserParent(TpUsers users) {
		return tpUsersMapper.updateUserParent(users);
	}

	@Override
	public int countByParentId(Integer user_id) {
		return tpUsersMapper.countByParentId(user_id);
	}

	@Override
	public int countByParentIdDay(Integer user_id, int startTime, int endTime) {
		return tpUsersMapper.countByParentIdDay(user_id, startTime, endTime);
	}

	@Override
	public int insertApplicationforBusinessCooperation(TpApplicationForBusinessCooperation tpApplication) {
		return 0;
	}

	@Override
	public void updateUser(Integer user_id, String head_pic, String nickname, String qq, String sex, String birthday, String province, String city, String district, String email, String scene, String wechat_qrcode, String wechat) {
		tpUsersMapper.updateUserInfo(user_id, head_pic, nickname, qq, sex, birthday, province, city, district, email, scene, wechat_qrcode, wechat);
	}


	@Override
	public void deleteAddress(Integer user_id, String id) {
		tpUsersMapper.deleteAddress(user_id, id);
	}

	@Override
	public void updateUserAndMobile(Integer user_id, String head_pic, String nickname, String qq, String sex, String birthday, String province, String city, String district, String email, String scene, String wechat_qrcode, String wechat, String mobile) {
		tpUsersMapper.updateUserAndMobile(user_id, head_pic, nickname, qq, sex, birthday, province, city, district, email, scene, wechat_qrcode, wechat, mobile);
	}

	@Override
	public BigDecimal selectFrozen(Integer user_id) {
		return tpUsersMapper.selectFrozen(user_id);
	}

	@Override
	public JSONObject getInviteCodeUserInfo(String invite_code) {
		JSONObject data = new JSONObject();
		TpUsers tpUsers = tpUsersMapper.getInviteCodeUserInfo(invite_code);
		String level_name = tpUserLevelMapper.selectLevelName(tpUsers.getLevel());
		String province_name = tpUsersMapper.selectAddrass(tpUsers.getUser_id());
		String city_name = tpUsersMapper.selectCity(tpUsers.getUser_id());
		String district_name = tpUsersMapper.selectDistrict(tpUsers.getUser_id());
		data.put("user_id", tpUsers.getUser_id());
		data.put("email", tpUsers.getEmail());
		data.put("password", tpUsers.getPassword());
		data.put("paypwd", tpUsers.getPaypwd());
		data.put("sex", tpUsers.getSex());
		data.put("birthday", tpUsers.getBirthday());
		data.put("user_money", tpUsers.getUser_money());
		data.put("frozen_money", tpUsers.getFrozen_money());
		data.put("distribut_money", tpUsers.getDistribut_money());
		data.put("underling_number", tpUsers.getUnderling_number());
		data.put("pay_points", tpUsers.getPay_points());
		data.put("address_id", tpUsers.getAddress_id());
		data.put("reg_time", tpUsers.getReg_time());
		data.put("last_login", tpUsers.getLast_login());
		data.put("last_ip", tpUsers.getLast_ip());
		data.put("qq", tpUsers.getQq());
		data.put("mobile", tpUsers.getMobile());
		data.put("mobile_validated", tpUsers.getMobile_validated());
		data.put("oauth", tpUsers.getOauth());
		data.put("openid", tpUsers.getOpenid());
		data.put("unionid", tpUsers.getUnionid());
		data.put("head_pic", tpUsers.getHead_pic());
		data.put("province", tpUsers.getProvince());
		data.put("city", tpUsers.getCity());
		data.put("district", tpUsers.getDistrict());
		data.put("email_validated", tpUsers.getEmail_validated());
		data.put("nickname", tpUsers.getNickname());
		data.put("level", tpUsers.getLevel());
		data.put("discount", tpUsers.getDiscount());
		data.put("total_amount", tpUsers.getTotal_amount());
		data.put("is_lock", tpUsers.getIs_lock());
		data.put("is_distribut", tpUsers.getIs_distribut());
		data.put("first_leader", tpUsers.getFirst_leader());
		data.put("second_leader", tpUsers.getSecond_leader());
		data.put("third_leader", tpUsers.getThird_leader());
		data.put("token", tpUsers.getToken());
		data.put("message_mask", tpUsers.getMessage_mask());
		data.put("push_id", tpUsers.getPush_id());
		data.put("distribut_level", tpUsers.getDistribut_level());
		data.put("is_vip", tpUsers.getIs_vip());
		data.put("invite_code", tpUsers.getInvite_code());
		data.put("relation", tpUsers.getRelation());
		data.put("sale_number", tpUsers.getSale_number());
		data.put("parent_id", tpUsers.getParent_id());
		data.put("wechat", tpUsers.getWechat());
		data.put("wechat_qrcode", tpUsers.getWechat_qrcode());
		data.put("level_name", level_name);
		data.put("province_name", province_name);
		data.put("city_name", city_name);
		data.put("district_name", district_name);
		return data;
	}

	@Override
	public TpPlateMsg findPlatMsgByFlagd(int flag) {
		return tpUsersMapper.findPlatMsgByFlagd(flag);
	}

	@Override
	public List<TpSysMessage> findAllSysMessage() {
		return tpUsersMapper.findAllSysMessage();
	}

	@Override
	public TpSysMessageUser findOneBySysIdAndUserId(Integer id, Integer user_id) {
		return tpUsersMapper.findOneBySysIdAndUserId(id, user_id);
	}

	@Override
	public TpRegion findRegionById(Integer regions_id) {
		return tpUsersMapper.findRegionById(regions_id);
	}

	@Override
	public List<TpRegion> findRegionByParentId(int id) {
		return tpUsersMapper.findRegionByParentId(id);
	}

	@Override
	public TpUserWallet findWalletByUserId(Integer user_id) {
		return tpUsersMapper.findWalletByUserId(user_id);
	}

	@Override
	public int updateUserWalletBalance(TpUserWallet tpUserWallet1) {
		return tpUsersMapper.updateUserWalletBalance(tpUserWallet1);
	}

	@Override
	public int insertUserFinance(TpUserFinance tpUserFinance) {
		return tpUsersMapper.insertUserFinance(tpUserFinance);
	}

	@Override
	public TpRegion findRegionByName(String launch_address) {
		return tpUsersMapper.findRegionByName(launch_address);
	}

	@Override
	public TpUsers getToken(String token) {
		return tpUsersMapper.getToken(token);
	}


	@Override
	public int selectMobile(Integer user_id) {
		return tpUsersMapper.selectMobile(user_id);
	}

	@Override
	public void updatePwd(Integer user_id, String new_password) {
		tpUsersMapper.updatePwd(user_id, new_password);
	}

	@Override
	public BigDecimal getSumMoneyDevice(Integer user_id) {
		return tpUsersMapper.getSumMoneyDevice(user_id);
	}

	@Override
	public JSONObject UserMoney(Integer user_id) {
		TpUsers tpUsers = tpUsersMapper.UserMoney(user_id);
		JSONObject jsonObj = new JSONObject();
		double UserMoney = tpUsers.getUser_money();
		double FrozenMoney = tpUsers.getFrozen_money();
		double balance = tpUsers.getDistribut_money();
		jsonObj.put("balance", balance);
		return jsonObj;
	}

	@Override
	public JSONObject incomeCount(Integer user_id) {
		JSONObject jsonObject = new JSONObject();
		TpUsers tpUsers = tpUsersMapper.incomeCount(user_id);
		int saleNumber = tpUsers.getSale_number();
		double distribut_money = tpUsers.getDistribut_money();
		int paper = 0;
		int ad = 0;
		jsonObject.put("sale", saleNumber * 1450);
		jsonObject.put("distribut_money", distribut_money);
		jsonObject.put("paper", paper);
		jsonObject.put("ad", ad);
		return jsonObject;
	}

	@Override
	public List<TpUsers> income() {
		List<TpUsers> tpUsers = tpUsersMapper.income();
		return tpUsers;
	}

	@Override
	public List<TpUsers> sale() {
		List<TpUsers> tpUsers = tpUsersMapper.sale();
		return tpUsers;
	}

	@Override
	public LimitPageList getLimitPageListByDynamic(Integer user_id, Integer page) {
		LimitPageList limitPageList = new LimitPageList();
		int totalCount = tpDynamicMapper.getCount(user_id);
		List<TpDynamic> stuList = new ArrayList<TpDynamic>();
		Page PageSize = null;
		if (page != null) {
			PageSize = new Page(totalCount, Integer.valueOf(page));
			PageSize.setPageSize(10);
			stuList = tpDynamicMapper.selectByPage(PageSize.getStartPos(), PageSize.getPageSize(), user_id);
		} else {
			PageSize = new Page(totalCount, 1);
			PageSize.setPageSize(10);
			stuList = tpDynamicMapper.selectByPage(PageSize.getStartPos(), PageSize.getPageSize(), user_id);
		}
		limitPageList.setPage(PageSize);
		limitPageList.setList(stuList);
		return limitPageList;
	}

	@Override
	public JSONObject getTpDynamicJson(TpDynamic tpDynamic) {
		JSONObject object = new JSONObject();
		object.put("id", tpDynamic.getId());
		object.put("user_id", tpDynamic.getUserId());
		object.put("image", tpDynamic.getImage());
		object.put("add_time", tpDynamic.getAddTime());
		object.put("fabulous", tpDynamic.getFabulous());
		object.put("nums", tpDynamic.getNums());
		object.put("view_status", tpDynamic.getViewStatus());
		object.put("is_delete", tpDynamic.getIsDelete());
		object.put("content", tpDynamic.getContent());
		object.put("times", "处理时间");
		TpUsers tpUsers1 = tpUsersMapper.findOneByUserId(tpDynamic.getUserId());
		if (null != tpUsers1) {
			object.put("nickname", tpUsers1.getNickname());
			object.put("head_pic", tpUsers1.getHead_pic());
		}
		List<TpReplay> tpReplayList = tpReplayMapper.findAllReplayByDynamicId(tpDynamic.getId());
		JSONArray jsonArray = new JSONArray();
		for (TpReplay tpReplay : tpReplayList) {
			JSONObject object12 = new JSONObject();
			object12.put("id", tpReplay.getId());
			object12.put("d_id", tpReplay.getdId());
			object12.put("add_time", tpReplay.getAddTime());
			object12.put("replay_content", tpReplay.getReplayContent());
			object12.put("user_id", tpReplay.getUserId());
			TpUsers tpUsers12 = tpUsersMapper.findOneByUserId(tpReplay.getUserId());
			if (null != tpUsers12) {
				object12.put("nickname", tpUsers12.getNickname());
				object12.put("head_pic", tpUsers12.getHead_pic());
			}
			jsonArray.add(object12);
		}
		object.put("replay", jsonArray);
		List<TpFabulous> tpFabulousList = tpFabulousMapper.findAllFabulousByDynamicId(tpDynamic.getId());
		JSONArray jsonArray1 = new JSONArray();
		for (TpFabulous tpFabulous : tpFabulousList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", tpFabulous.getId());
			jsonObject.put("user_id", tpFabulous.getUserId());
			jsonObject.put("d_id", tpFabulous.getdId());
			jsonObject.put("add_time", tpFabulous.getAddTime());
			TpUsers tpUsers121 = tpUsersMapper.findOneByUserId(tpFabulous.getUserId());
			if (null != tpUsers121) {
				jsonObject.put("nickname", tpUsers121.getNickname());
				jsonObject.put("head_pic", tpUsers121.getHead_pic());
				jsonArray1.add(jsonObject);
			}
			object.put("fabulous", jsonArray1);
		}
		return object;
	}

	@Override
	public TpUsers getInviteCode(String invite_code) {
		TpUsers share_user_info = tpUsersMapper.getUserByInviteCode(invite_code);
		return share_user_info;
	}

	@Override
	public TpUsers getUserInfo(String user_id) {
		TpUsers tpUsers = tpUsersMapper.getUserInfo(user_id);
		return tpUsers;
	}

	@Override
	public int updatePayPwd(String password,Integer user_id) {
		int i = tpUsersMapper.updatePayPwd(password,user_id);
		return i;
	}

	@Override
	public TpUsers getMobile(String mobile) {
		return tpUsersMapper.getMobile(mobile);
	}

	@Override
	public int updatePassword(Integer user_id, String confirm_password) {

		return tpUsersMapper.updatePassword(user_id,confirm_password);
	}

	@Override
	public void updateSetPass(Integer user_id) {
		tpUsersMapper.updateSetPass(user_id);
	}

	@Override
	public int insertTpDynamic(TpDynamic tpDynamic) {
		return tpDynamicMapper.insert(tpDynamic);
	}

	@Override
	public int insertTpReplay(TpReplay tpReplay) {
		return tpReplayMapper.insert(tpReplay);
	}

	@Override
	public TpDynamic findOneTpDynamicById(Integer id) {
		return tpDynamicMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updataDynamicByNums(TpDynamic tpDynamic1) {
		return tpDynamicMapper.updateByPrimaryKeySelective(tpDynamic1);
	}

	@Override
	public int insertTpFabulous(TpFabulous tpFabulous) {
		return tpFabulousMapper.insert(tpFabulous);
	}

	@Override
	public int updataUserByBackimg(TpUsers tpUsers1) {
		return tpUsersMapper.updataUSerByBackimg(tpUsers1);
	}

	@Override
	public TpUsers selectLevel(int level,String type) {
		TpUsers tpUsers = tpUsersMapper.selectLevel(level,type);
		return tpUsers;
	}

	@Override
	public TpUsers selectPaperCount(Integer user_id) {
		TpUsers tpUsers = tpUsersMapper.selectPaperCount(user_id);
		return tpUsers;
	}

	@Override
	public TpUsers selectToUser(String invite_code) {
		TpUsers tpUsers = tpUsersMapper.selectToUser(invite_code);
		return tpUsers;
	}

	@Override
	public int updateNumber(Integer user_id, String number) {
		int i= tpUsersMapper.updateNumber(user_id,number);
		return i;
	}

	@Override
	public TpUsers getToUser(Integer to_user_id) {
		TpUsers tpUsers = tpUsersMapper.getToUser(to_user_id);
		return tpUsers;
	}

	@Override
	public TpUsers selectFromUser(Integer from_user_id) {
		TpUsers fromUser = tpUsersMapper.selectFromUser(from_user_id);
		return fromUser;
	}

	@Override
	public void addNumber(Integer from_user_id,Integer number) {
		tpUsersMapper.addNumber(from_user_id,number);
	}

	@Override
	public TpUsers selectToUsers(Integer user_id) {
		TpUsers toUsers =tpUsersMapper.selectToUsers(user_id);
		return toUsers;
	}

	@Override
	public void addNumberToUser(Integer user_id, Integer number) {
		tpUsersMapper.addNumberToUser(user_id,number);
	}
}
