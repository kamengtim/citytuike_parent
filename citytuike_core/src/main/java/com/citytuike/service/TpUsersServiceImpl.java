package com.citytuike.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpUsersMapper;

@Service
public class TpUsersServiceImpl implements TpUsersService{

	@Autowired
	private TpUsersMapper tpUsersMapper;

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
		int totalCount = tpUsersMapper.selectCountDevice(user_id);
		List<TpUsers> stuList = new ArrayList<TpUsers>();
		Page PageSize = null;
		if(page != null){
			PageSize=new Page(totalCount,Integer.valueOf(page));
			PageSize.setPageSize(10);
			stuList = tpUsersMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),user_id);
		}else{
			PageSize = new Page(totalCount,1);
			PageSize.setPageSize(10);
			stuList = tpUsersMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),user_id);
		}
		limitPageList.setPage(PageSize);
		limitPageList.setList(stuList);
		return limitPageList;
	}

    public int updateUserFrozenMoney(Integer user_id, double frozenMoney) {
        return tpUsersMapper.updateUserFrozenMoney(user_id, frozenMoney);
    }

}
