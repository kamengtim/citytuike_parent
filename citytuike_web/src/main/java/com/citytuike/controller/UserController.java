package com.citytuike.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.citytuike.exception.SendMessageException;
import com.citytuike.model.*;
import com.citytuike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.util.MD5Utils;
import com.citytuike.util.Util;

@Controller
@RequestMapping("api/User")
public class UserController {
	@Autowired
	private TpUsersService tpUsersService;
	@Autowired
	private TpSmsLogService tpSmsLogService;
	@Autowired
	private ITpAccountLogService tpAccountLogService;
	@Autowired
	private TpBankService tpBankService;
	@Autowired
	private TpUserBankService tpUserBankService;
	@Autowired
	private TpWithdrawalsService tpWithdrawalsService;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private TpOrderService tpOrderService;
	/**
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 * 登陆
	 */
	@RequestMapping(value="/do_login",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String doLogin(Model model,@RequestParam(required=true) String username,
			@RequestParam(required=true) String password){
		String pwd = MD5Utils.md5("TPSHOP" + password);
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "登陆失败!");
		TpUsers tpUsers = tpUsersService.findOneByLogo(username, pwd);
		if (null != tpUsers) {
			String token = MD5Utils.md5(System.currentTimeMillis()+Util.generateString(16));
			tpUsers.setToken(token);
			int result = tpUsersService.updateBytokenIn(tpUsers);
			if (result > 0) {
				jsonObj.put("status", "1");
				jsonObj.put("msg", "登陆成功!");
				data = tpUsersService.getUserlJson(tpUsers);
				jsonObj.put("return", data);
			}else {
				System.out.println("系统错误!");
			}
			
		}else {
			jsonObj.put("status", "-1");
			jsonObj.put("msg", "账号不存在!");
		}
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param nickname
	 * @param username
	 * @param password
	 * @param password2
	 * @param mobile_code
	 * @param scene
	 * @param invite
	 * @return
	 * 注册 
	 */
	@RequestMapping(value="/reg",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String reg(Model model,@RequestParam(required=false) String nickname,
			@RequestParam(required=true) String username,
			@RequestParam(required=true) String password,
			@RequestParam(required=true) String password2,
			@RequestParam(required=true) String mobile_code,
			@RequestParam(required=false) String scene,
			@RequestParam(required=false) String invite){
		
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "注册失败!");
		TpUsers tpUsers1 = tpUsersService.findOneByMobile(username);
		if (null != tpUsers1){
			jsonObj.put("status", "5");
			jsonObj.put("msg", "手机已注册!");
			return jsonObj.toString();
		}
		TpUsers tpUsers = new TpUsers();
		tpUsers.setNickname(nickname);
		tpUsers.setEmail("");
		tpUsers.setMobile(username);
		tpUsers.setSex(0);
		tpUsers.setBirthday(0);
		tpUsers.setUser_money(0.00);
		tpUsers.setPay_points(0);
		tpUsers.setAddress_id(0);
		tpUsers.setReg_time((int)new Date().getTime());
		tpUsers.setLast_login((int)new Date().getTime());
		tpUsers.setLast_ip("");
		tpUsers.setMobile_validated(1);
		tpUsers.setEmail_validated(0);
		tpUsers.setMessage_mask(0);
		tpUsers.setPush_id("");
		if (!password.equals(password2)) {
			jsonObj.put("status", "2");
			jsonObj.put("msg", "两次密码不一致!");
			return jsonObj.toString();
		}
		String pwd = MD5Utils.md5("TPSHOP" + password);
		tpUsers.setPassword(pwd);
		TpSmsLog tpSmsLog = tpSmsLogService.findOneByMobileAndCode(username, mobile_code);
		/*if (null == tpSmsLog) {
			jsonObj.put("status", "3");
			jsonObj.put("msg", "验证码错误!");
			return jsonObj.toString();
		}else {
			if (tpSmsLog.getStatus() == 1) {
				jsonObj.put("status", "4");
				jsonObj.put("msg", "验证码已使用!");
				return jsonObj.toString();
			}
		}*/
		tpUsers.setInvite_code(Util.getBigString(8));
		if (invite != null && !invite.equals("")) {
			//邀约码不为空
			TpUsers tpUsers2 = tpUsersService.findOneByInvite(invite);
			if (null != tpUsers2){
				tpUsers.setParent_id(tpUsers2.getUser_id());
				if (null != tpUsers2.getRelation() && !"".equals(tpUsers2.getRelation())){
					tpUsers.setRelation(tpUsers2.getRelation() + "," + tpUsers2.getUser_id());
				}else{
					tpUsers.setRelation(tpUsers2.getUser_id() + "");
				}
			}
		}
		//注册用户
		int result = tpUsersService.save(tpUsers);
		//更新验证码
//		result = tpSmsLogService.updateByStatus(tpSmsLog);
		if (result > 0) {
			jsonObj.put("status", "1");
			jsonObj.put("msg", "注册成功!");
			data = tpUsersService.getUserlJson(tpUsers);
			jsonObj.put("return", data);
		}else {
			System.out.println("系统错误!");
		}
		
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @return
	 * 退出登陆 
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String logout(Model model,@RequestParam(required=true) String token){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "退出登陆失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null != tpUsers) {
			tpUsers.setToken("");
			int result = tpUsersService.updateBytokenOut(tpUsers);
			if (result > 0) {
				jsonObj.put("status", "1");
				jsonObj.put("msg", "退出登陆成功!");
			}else {
				System.out.println("系统错误!");
			}
		}
		
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @return
	 * 用户地址列表
	 */
	@RequestMapping(value="/address_list",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String addressList(Model model,@RequestParam(required=true) String token){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		List<TpUserAddress> userAddresses = tpUsersService.findAddressByUserId(tpUsers.getUser_id());
		for (TpUserAddress tpUserAddress : userAddresses) {
			JSONObject jsonObj1 = new JSONObject();
			jsonObj1.put("address_id", tpUserAddress.getAddress_id());
			jsonObj1.put("user_id", tpUserAddress.getUser_id());
			jsonObj1.put("consignee", tpUserAddress.getConsignee());
			jsonObj1.put("email", tpUserAddress.getEmail());
			jsonObj1.put("country", tpUserAddress.getCountry());
			jsonObj1.put("province", tpUserAddress.getProvince());
			jsonObj1.put("city", tpUserAddress.getCity());
			jsonObj1.put("district", tpUserAddress.getDistrict());
			jsonObj1.put("twon", tpUserAddress.getTwon());
			jsonObj1.put("address", tpUserAddress.getAddress());
			jsonObj1.put("zipcode", tpUserAddress.getZipcode());
			jsonObj1.put("mobile", tpUserAddress.getMobile());
			jsonObj1.put("is_default", tpUserAddress.getIs_default());
			jsonObj1.put("is_pickup", tpUserAddress.getIs_pickup());
			jsonArray.add(jsonObj1);
		}
		jsonObj.put("status", "1");
		jsonObj.put("msg", "OK!");
		jsonObj.put("return", jsonArray);
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @param province
	 * @param address
	 * @param consignee
	 * @param city
	 * @param district
	 * @param mobile
	 * @param is_default
	 * @return
	 * 添加地址
	 */
	@RequestMapping(value="/add_address",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String addAddress(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true)Integer province,
			@RequestParam(required=true)String address,
			@RequestParam(required=true)String consignee,
			@RequestParam(required=true)Integer city,
			@RequestParam(required=true)Integer district,
			@RequestParam(required=true)String mobile,
			@RequestParam(required=true)Integer is_default){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		TpUserAddress tpUserAddress = new TpUserAddress();
		tpUserAddress.setProvince(province);
		tpUserAddress.setAddress(address);
		tpUserAddress.setConsignee(consignee);
		tpUserAddress.setCity(city);
		tpUserAddress.setDistrict(district);
		tpUserAddress.setMobile(mobile);
		tpUserAddress.setIs_default(is_default);
		int returns = tpUsersService.insertUserAddress(tpUserAddress);
		if (returns > 0) {
			jsonObj.put("status", "1");
			jsonObj.put("msg", "成功!");
		}
		
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @param id
	 * @param province
	 * @param address
	 * @param consignee
	 * @param city
	 * @param district
	 * @param mobile
	 * @param is_default
	 * @return
	 * 修改地址
	 */
	@RequestMapping(value="/edit_address",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String editAddress(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true)Integer id,
			@RequestParam(required=true)Integer province,
			@RequestParam(required=true)String address,
			@RequestParam(required=true)String consignee,
			@RequestParam(required=true)Integer city,
			@RequestParam(required=true)Integer district,
			@RequestParam(required=true)String mobile,
			@RequestParam(required=true)Integer is_default){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		TpUserAddress address2 = tpUsersService.findUserAddresById(id);
		if (null != address2) {
			TpUserAddress tpUserAddress = new TpUserAddress();
			tpUserAddress.setAddress_id(id);
			tpUserAddress.setProvince(province);
			tpUserAddress.setAddress(address);
			tpUserAddress.setConsignee(consignee);
			tpUserAddress.setCity(city);
			tpUserAddress.setDistrict(district);
			tpUserAddress.setMobile(mobile);
			tpUserAddress.setIs_default(is_default);
			int returns = tpUsersService.updateUserAddress(tpUserAddress);
			if (returns > 0) {
				jsonObj.put("status", "1");
				jsonObj.put("msg", "成功!");
			}
		}
		
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @param id
	 * @return
	 * 获取城县区
	 */
	@RequestMapping(value="/get_region",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRegion(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true)Integer id){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArry = new JSONArray();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		if (id ==0) {
			id = 1;
		}
		List<TpRegion> tpRegions = tpUsersService.findAllByLevel(id);
		for (TpRegion tpRegion : tpRegions) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", tpRegion.getId());
			jsonObject.put("name", tpRegion.getName());
			jsonObject.put("level", tpRegion.getLevel());
			jsonObject.put("parent_id", tpRegion.getParent_id());
			jsonObject.put("initials", tpRegion.getInitials());
			jsonObject.put("num", tpRegion.getInitials());
			jsonArry.add(jsonObject);
		}
		data.put("region_list", jsonArry);
		jsonObj.put("return", data);
		jsonObj.put("status", "1");
		jsonObj.put("msg", "成功!");
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @param id
	 * @return
	 * 设置默认值
	 */
	@RequestMapping(value="/set_default",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String setDefault(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true)Integer id){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		if (id ==0) {
			id = 1;
		}
		TpUserAddress address = tpUsersService.findUserAddresById(id);
		if (null != address) {
			List<TpUserAddress> addresses = tpUsersService.findIsDefaultAll(tpUsers.getUser_id());
			for (TpUserAddress tpUserAddress : addresses) {
				int num = tpUsersService.updateAddressToDefault(tpUserAddress.getAddress_id(), 0);
				if (num <= 0) {
					jsonObj.put("status", "0");
					jsonObj.put("msg", "失败!");
					return jsonObj.toString();
				}
			}
			int num = tpUsersService.updateAddressToDefault(id, 1);
			if (num > 0) {
				jsonObj.put("status", "1");
				jsonObj.put("msg", "成功!");
			}
		}
		
		return jsonObj.toString();
	}
	/**
	 * @param token
	 * @return
	 * 账户管理
	 */
	@RequestMapping(value="/account",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public  @ResponseBody String account(@RequestParam(required = true)String token){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		JSONObject jsonObject = tpAccountLogService.UserMoney(tpUsers.getUser_id());
		return jsonObject.toString();
	}
	/**
	 * @param token
	 * @return
	 * 收益明细列表
	 */
	@RequestMapping(value="/account_list",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String AccountList(@RequestParam(required = true)String token,
											@RequestParam(required = true)String type,
											@RequestParam(required = false)String page){
		JSONObject data = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		JSONArray  jsonArray = new JSONArray();
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		LimitPageList  limitPageList  = tpAccountLogService.getDetail(tpUsers.getUser_id(),type,page);
		List<TpAccountLog> list = (List<TpAccountLog>)limitPageList.getList();
		for (TpAccountLog tpAccountLog : list) {
			JSONObject jsonObject = tpAccountLogService.getJsonToAccount(tpAccountLog);
			jsonArray.add(jsonObject);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		double count_money = tpAccountLogService.SumMoney(tpUsers.getUser_id());
		data.put("count_money",sdf.format(new Date())+":"+count_money);
		data.put("current_page", limitPageList.getPage().getPageNow());
		data.put("total", limitPageList.getPage().getTotalCount());
		data.put("per_page", limitPageList.getPage().getPageSize());
		data.put("last_page",limitPageList.getPage().getTotalPageCount());
		data.put("data",jsonArray);
		jsonObj.put("status", "1");
		jsonObj.put("msg", "ok!");
		jsonObj.put("result",data);

		return jsonObj.toString();
	}
	/**
	 * @param token
	 * @return
	 * 银行卡列表
	 */
	@RequestMapping(value = "bank_list",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String BankList(@RequestParam(required = true)String token){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		List<TpUserBank>tpUserBanks = tpUserBankService.getBankByUserId(tpUsers.getUser_id());
		for (TpUserBank tpUserBank : tpUserBanks) {
			JSONObject jsonObject1 = tpUserBankService.getJsonBankAndUser(tpUserBank);
			TpBank tpBank = tpBankService.getListBank(tpUserBank.getBank_id());
			JSONObject jsonObject = tpBankService.getJsonBank(tpBank);
			jsonObj.put("result",jsonObject1);
			jsonObject1.put("bank",jsonObject);
		}
		jsonObj.put("status", "1");
		jsonObj.put("msg", "ok!");
		return jsonObj.toString();
	}
	/**
	 * @param token
	 * @return
	 * 提现申请列表
	 */
	@RequestMapping(value = "withdrawals_list",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String WithdrawalsList(@RequestParam(required = true)String token,
												@RequestParam(required = false)String page){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		LimitPageList tpWithdrawals = tpWithdrawalsService.getWithdrawalsList(tpUsers.getUser_id(),page);
		List<TpWithdrawals> withdrawals = (List<TpWithdrawals>)tpWithdrawals.getList();
		for (TpWithdrawals withdrawal : withdrawals) {
			JSONObject jsonObject = tpWithdrawalsService.JsonWithdrawals(withdrawal);
			jsonArray.add(jsonObject);
		}
		data.put("data",jsonArray);
		data.put("current_page", tpWithdrawals.getPage().getPageNow());
		data.put("total", tpWithdrawals.getPage().getTotalCount());
		data.put("per_page", tpWithdrawals.getPage().getPageSize());
		data.put("last_page",tpWithdrawals.getPage().getTotalPageCount());
		jsonObj.put("result",data);
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 短信发送
	 */
	@RequestMapping(value = "send_validate_code",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String SendValidateCode(@RequestParam(required = false)String type,
												 @RequestParam(required = false,defaultValue = "6")String scene,
												 @RequestParam(required = true)String mobile,
												 @RequestParam(required = false) String send,
												 @RequestParam(required = false)String verify_code,
												 @RequestParam(required = false)String unique_id){
		try{
		sendMessageService.sendVerifyCode(type,scene,mobile,send,verify_code,unique_id);
		return "发送成功";
		}catch (Exception e){
		throw new SendMessageException("发送超时");
		}
	}

    /**
     * @param user_id
     * @return
     * 根据用户ID 获取信息
     */
	@RequestMapping(value = "user_info_by_id",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String userInfoById(@RequestParam(required = true)String user_id, @RequestParam(required = true)String token){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers1 = tpUsersService.findOneByToken(token);
        if (null == tpUsers1) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
		TpUsers tpUsers = tpUsersService.findOneByUserId(Integer.parseInt(user_id));
		if (null != tpUsers) {
			data.put("nickname", tpUsers.getNickname());
			data.put("invite_code", tpUsers.getInvite_code());
			data.put("head_pic", tpUsers.getHead_pic());
			data.put("level", tpUsers.getLevel());

		}
		jsonObj.put("result",data);
		return jsonObj.toString();
	}


    /**
     * @param invite_code
     * @param token
     * @return
     * 根据invite_code 获取用户信息
     */
	@RequestMapping(value = "get_invite_code_user_info",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String getInviteCodeUerInfo(@RequestParam(required = true)String invite_code, @RequestParam(required = true)String token){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers1 = tpUsersService.findOneByToken(token);
        if (null == tpUsers1) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
		TpUsers tpUsers = tpUsersService.findOneByInvite(invite_code);
		if (null != tpUsers) {
            data = tpUsersService.getUserlJson(tpUsers);
		}
		jsonObj.put("result",data);
		return jsonObj.toString();
	}
    /**
     * @return
     * 根据im_id 获取用户信息
     */
	@RequestMapping(value = "user_info_by_im_id",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String userInfoByImId(@RequestParam(required = true)String im_id, @RequestParam(required = true)String token){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers1 = tpUsersService.findOneByToken(token);
        if (null == tpUsers1) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
		TpUsers tpUsers = tpUsersService.findOneByImId(im_id);
		if (null != tpUsers) {
			data.put("nickname", tpUsers.getNickname());
			data.put("invite_code", tpUsers.getInvite_code());
			data.put("head_pic", tpUsers.getHead_pic());
			data.put("level", tpUsers.getLevel());
			data.put("im_id", tpUsers.getIm_id());
			data.put("im_pwd", tpUsers.getIm_pwd());
			data.put("user_id", tpUsers.getUser_id());

		}
		jsonObj.put("result",data);
		return jsonObj.toString();
	}
    /**
     * @return
     * 加入团队
     */
	@RequestMapping(value = "join",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String join(@RequestParam(required = true)String invite_code, @RequestParam(required = true)String token){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers1 = tpUsersService.findOneByToken(token);
        if (null == tpUsers1) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
		TpUsers tpUsers = tpUsersService.findOneByInvite(invite_code);
		if (null != tpUsers) {
		    TpUsers tpUsers2 = new TpUsers();
		    tpUsers2.setUser_id(tpUsers1.getUser_id());
            tpUsers2.setParent_id(tpUsers.getUser_id());
            tpUsers2.setRelation(tpUsers.getRelation()+","+tpUsers.getUser_id());
		    int result = tpUsersService.updateUserParent(tpUsers2);
		    if (result > 0){
                jsonObj.put("status", "1");
                jsonObj.put("msg", "ok!");
            }
		}
		jsonObj.put("result",data);
		return jsonObj.toString();
	}

    /**
     * @param token
     * @return
     * 意向代理人数统计
     */
	@RequestMapping(value = "team_device_count",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String teamDeviceCount(@RequestParam(required = true)String token){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers1 = tpUsersService.findOneByToken(token);
        String startTime = String.valueOf(Util.getStartTime());
        String endTime = String.valueOf(Util.getnowEndTime());
        if (null == tpUsers1) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        int allNum = tpUsersService.countByParentId(tpUsers1.getUser_id());
        int dayNum = tpUsersService.countByParentIdDay(tpUsers1.getUser_id(), Integer.parseInt(startTime), Integer.parseInt(endTime));

        data.put("count", allNum);
        data.put("today", dayNum);

        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
		jsonObj.put("result",data);
		return jsonObj.toString();
	}

    /**
     * @param token
     * @return
     * 机器顶部统计
     */
	@RequestMapping(value = "device_number",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String deviceNumber(@RequestParam(required = true)String token){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers1 = tpUsersService.findOneByToken(token);
        if (null == tpUsers1) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }

        List<TpOrder> tpOrderList = tpOrderService.findAllOrderAndGoods(tpUsers1.getUser_id());
        int self = 0;
        int team = 0;
        for (TpOrder tporder : tpOrderList) {
            for (TpOrderGoods tpOrderGoods : tporder.getTpOrderGoodsList()) {
                self += tpOrderGoods.getGoods_num();
            }
        }
        List<TpUsers> tpUsersList = tpUsersService.findAllByUserParentId(tpUsers1.getUser_id());
        for (TpUsers tpUsers : tpUsersList) {
            List<TpOrder> tpOrderList1 = tpOrderService.findAllOrderAndGoods(tpUsers.getUser_id());
            for (TpOrder tporder : tpOrderList1) {
                for (TpOrderGoods tpOrderGoods : tporder.getTpOrderGoodsList()) {
                    team += tpOrderGoods.getGoods_num();
                }
            }
        }
        data.put("count", self);
        data.put("today", team);

        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
		jsonObj.put("result",data);
		return jsonObj.toString();
	}

    /**
     * @param token
     * @return
     * 申请商务合作
     */
	@RequestMapping(value = "application_for_business_cooperation",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	public @ResponseBody String applicationForBusinessCooperation(@RequestParam(required = true)String code,
                                                                  @RequestParam(required = true)String token,
                                                                  @RequestParam(required = true)String name,
                                                                  @RequestParam(required = true)String email,
                                                                  @RequestParam(required = true)String province,
                                                                  @RequestParam(required = true)String city,
                                                                  @RequestParam(required = true)String district,
                                                                  @RequestParam(required = true)String type,
                                                                  @RequestParam(required = true)String bank_name,
                                                                  @RequestParam(required = true)String ways_of_cooperation,
                                                                  @RequestParam(required = true)String company,
                                                                  @RequestParam(required = true)String day_number,
                                                                  @RequestParam(required = true)String details,
                                                                  @RequestParam(required = true)String files,
                                                                  @RequestParam(required = true)String reason){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers1 = tpUsersService.findOneByToken(token);
        if (null == tpUsers1) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
     /*   TpSmsLog tpSmsLog = tpSmsLogService.findOneByMobileAndCode(tpUsers1.getMobile(), code);
		if (null == tpSmsLog) {
			jsonObj.put("status", "3");
			jsonObj.put("msg", "验证码错误!");
			return jsonObj.toString();
		}else {
			if (tpSmsLog.getStatus() == 1) {
				jsonObj.put("status", "4");
				jsonObj.put("msg", "验证码已使用!");
				return jsonObj.toString();
			}
		}*/
        TpApplicationForBusinessCooperation tpApplication = new TpApplicationForBusinessCooperation();
        tpApplication.setUser_id(tpUsers1.getUser_id());
        tpApplication.setName(name);
        tpApplication.setEmail(email);
        tpApplication.setProvince(Integer.parseInt(province));
        tpApplication.setCity(city);
        tpApplication.setDistrict(Integer.parseInt(district));
        tpApplication.setType(Integer.parseInt(type));
        tpApplication.setBank_name(bank_name);
        tpApplication.setWays_of_cooperation(ways_of_cooperation);
        tpApplication.setCompany(company);
        tpApplication.setDay_number(Integer.parseInt(day_number));
        tpApplication.setDetails(details);
        tpApplication.setFiles(files);
        tpApplication.setMobile(tpUsers1.getMobile());
        tpApplication.setReason(reason);
        tpApplication.setRead(Integer.parseInt(""));
        tpApplication.setReply_mess("");
        int result = tpUsersService.insertApplicationforBusinessCooperation(tpApplication);
        if (result > 0){
            jsonObj.put("status", "1");
            jsonObj.put("msg", "ok!");
        }
		jsonObj.put("result",data);
		return jsonObj.toString();
	}
}
