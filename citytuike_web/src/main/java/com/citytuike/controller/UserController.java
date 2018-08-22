package com.citytuike.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpRegion;
import com.citytuike.model.TpSmsLog;
import com.citytuike.model.TpUserAddress;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpSmsLogService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.MD5Utils;
import com.citytuike.util.Util;

@Controller
@RequestMapping("api/User")
public class UserController {
	@Autowired
	private TpUsersService tpUsersService;
	@Autowired
	private TpSmsLogService tpSmsLogService;
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
		TpUsers tpUsers = new TpUsers();
		tpUsers.setNickname(nickname);
		tpUsers.setMobile(username);
		if (!password.equals(password2)) {
			jsonObj.put("status", "2");
			jsonObj.put("msg", "两次密码不一致!");
			return jsonObj.toString();
		}
		String pwd = MD5Utils.md5("TPSHOP" + password);
		tpUsers.setPassword(pwd);
		TpSmsLog tpSmsLog = tpSmsLogService.findOneByMobileAndCode(username, mobile_code);
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
		}
		
		if (invite != null && !invite.equals("")) {
			//邀约码不为空
			
		}
		//注册用户
		int result = tpUsersService.save(tpUsers);
		//更新验证码
		result = tpSmsLogService.updateByStatus(tpSmsLog);
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
}
