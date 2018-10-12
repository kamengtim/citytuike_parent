package com.citytuike.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.constant.Constant;
import com.citytuike.model.*;
import com.citytuike.service.*;
import com.citytuike.util.MD5Utils;
import com.citytuike.util.Util;
import com.citytuike.util.mobileCheck;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("api/User")
public class UserController extends BaseController{
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
	@Autowired
    private TpReportListService  tpReportListService;
	@Autowired
	private ITpDeviceService tpDeviceService;
	@Autowired
    private TpCardListService tpCardListSevice;
	@Autowired
    private TpApplyCardService tpApplyCardService;
	@Autowired
	private TpApplyReportService tpApplyReportService;
	@Autowired
    private TpUserAliAccountService tpUserAliAccountService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private TpUserLevelService tpUserLevelService;
	@Autowired
	private TpCartGiftService tpCartGiftService;
	@Autowired
	private TpFestivalsContentService tpFestivalsContentService;
	@Autowired
	private TpFestivalsService tpFestivalsService;
	@Autowired
	private TpFestivalsCateService tpFestivalsCateService;
	/**
	 * @return
	 * 登陆
	 */
	@RequestMapping(value="/do_login",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "登陆", notes = "登陆")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String doLogin(HttpServletRequest request){

		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String username = jsonO.getString("username");
        String password = jsonO.getString("password");
        if (null == username || "".equals(username) || null == password || "".equals(password)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String pwd = MD5Utils.md5("TPSHOP" + password);
		TpUsers tpUsers = tpUsersService.findOneByLogo(username, pwd);
		if (null != tpUsers) {
			String token = MD5Utils.md5(System.currentTimeMillis()+Util.generateString(16));
			tpUsers.setToken(token);
			int result = tpUsersService.updateBytokenIn(tpUsers);
			TpUsers tpUsers1 = tpUsersService.getToken(tpUsers.getToken());
			redisTemplate.opsForValue().set(Constant.CURRENT_USER+tpUsers1.getToken(),tpUsers1.getToken());
			redisTemplate.expire(Constant.CURRENT_USER+tpUsers1.getToken(),30, TimeUnit.DAYS);
			if (result > 0) {
				jsonObj.put("status", 1);
				jsonObj.put("msg", "登陆成功!");
				data = tpUsersService.getUserlJson(tpUsers);
				jsonObj.put("return", data);
			}else {
				System.out.println("系统错误!");
			}

		}else {
			jsonObj.put("status", -1);
			jsonObj.put("msg", "账号不存在!");
		}
		return jsonObj.toString();
	}

	/**
	 * @return
	 * 注册 
	 */
	@RequestMapping(value="/reg",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "注册", notes = "注册")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String reg(HttpServletRequest request){
		
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String nickname = jsonO.getString("nickname");
        String username = jsonO.getString("username");
        String password = jsonO.getString("password");
        String password2 = jsonO.getString("password2");
        String mobile_code = jsonO.getString("mobile_code");
        String scene = jsonO.getString("scene");
        String invite = jsonO.getString("invite");
        if (null == password2 || "".equals(password2) || null == username || "".equals(username)
                || null == password || "".equals(password) || null == mobile_code || "".equals(mobile_code)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
		TpUsers tpUsers1 = tpUsersService.findOneByMobile(username);
		if (null != tpUsers1){
			jsonObj.put("status", 5);
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
			jsonObj.put("status", 2);
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
			jsonObj.put("status", 1);
			jsonObj.put("msg", "注册成功!");
			data = tpUsersService.getUserlJson(tpUsers);
			jsonObj.put("return", data);
		}else {
			System.out.println("系统错误!");
		}

		return jsonObj.toString();
	}
	/**
	 * @return
	 * 退出登陆 
	 */
	@RequestMapping(value="/logout",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "退出登陆", notes = "退出登陆")
	public @ResponseBody String logout(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null != tpUsers) {
			tpUsers.setToken("");
			int result = tpUsersService.updateBytokenOut(tpUsers);
			if (result > 0) {
				jsonObj.put("status", 1);
				jsonObj.put("msg", "退出登陆成功!");
			}else {
				System.out.println("系统错误!");
			}
		}
		
		return jsonObj.toString();
	}
	//TODO . 快速登录
	/**
	 * @return
	 * 用户地址列表
	 */
	@RequestMapping(value="/address_list",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "用户地址列表", notes = "用户地址列表")
	public @ResponseBody String addressList(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
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
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		jsonObj.put("return", jsonArray);
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 添加地址
	 */
	@RequestMapping(value="/add_address",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "添加地址", notes = "添加地址")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String addAddress(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        Integer province = jsonO.getInteger("province");
        String address = jsonO.getString("address");
        String consignee = jsonO.getString("consignee");
        Integer city = jsonO.getInteger("city");
        Integer district = jsonO.getInteger("district");
        String mobile = jsonO.getString("mobile");
        Integer is_default = jsonO.getInteger("is_default");
        if (null == province || "".equals(province) || null == address || "".equals(address)
                || null == consignee || "".equals(consignee) || null == city || "".equals(city)
                || null == district || "".equals(district) || null == mobile || "".equals(mobile)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
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
			jsonObj.put("status", 1);
			jsonObj.put("msg", "请求成功!");
		}
		
		return jsonObj.toString();
	}
	/**
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
	//TODO 地址编辑
	@RequestMapping(value="/edit_address",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "修改地址", notes = "修改地址")
	public @ResponseBody String editAddress(HttpServletRequest request,
			@RequestParam(required=true)Integer id,
			@RequestParam(required=true)Integer province,
			@RequestParam(required=true)String address,
			@RequestParam(required=true)String consignee,
			@RequestParam(required=true)Integer city,
			@RequestParam(required=true)Integer district,
			@RequestParam(required=true)String mobile,
			@RequestParam(required=true)Integer is_default){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
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
				jsonObj.put("status", 1);
				jsonObj.put("msg", "请求成功!");
			}
		}
		
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 获取城县区
	 */
	@RequestMapping(value="/get_region",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "获取城县区", notes = "获取城县区")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String getRegion(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArry = new JSONArray();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        Integer id = jsonO.getInteger("id");
        if (null == id || "".equals(id)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
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
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 设置默认值
	 */
	@RequestMapping(value="/set_default",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "设置默认值", notes = "设置默认值")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String setDefault(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        Integer id = jsonO.getInteger("id");
        if (null == id || "".equals(id)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
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
					jsonObj.put("status", 0);
					jsonObj.put("msg", "请求失败，请稍后再试");
					return jsonObj.toString();
				}
			}
			int num = tpUsersService.updateAddressToDefault(id, 1);
			if (num > 0) {
				jsonObj.put("status", 1);
				jsonObj.put("msg", "请求成功!");
			}
		}
		
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 账户管理
	 */
	@RequestMapping(value="/account",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "账户管理", notes = "账户管理")
	public  @ResponseBody String account(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		JSONObject jsonObject = tpUsersService.UserMoney(tpUsers.getUser_id());
		return jsonObject.toString();
	}
	/**
	 * @return
	 * 收益明细列表
	 */
	@RequestMapping(value="/account_list",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "收益明细列表", notes = "收益明细列表")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String AccountList(HttpServletRequest request){
		JSONObject data = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		JSONArray  jsonArray = new JSONArray();
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String type = jsonO.getString("type");
        String page = jsonO.getString("page");
        if (null == type || "".equals(type) || null == page || "".equals(page)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
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
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		jsonObj.put("result",data);

		return jsonObj.toString();
	}
	//TODO 会员信息
    @RequestMapping(value="/account_info",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "会员信息", notes = "会员信息")
    public  @ResponseBody String accountInfo(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        return jsonObj.toString();
    }
	/**
	 * @return
	 * 银行卡列表
	 */
	@RequestMapping(value = "bank_list",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "银行卡列表", notes = "银行卡列表")
	public @ResponseBody String BankList(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
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
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
}
	/**
	 * @return
	 * 提现申请列表
	 */
	@RequestMapping(value = "withdrawals_list",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "提现申请列表", notes = "提现申请列表")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String WithdrawalsList(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String page = jsonO.getString("page");
        if (null == page || "".equals(page)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
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
	@RequestMapping(value = "send_validate_code",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "短信发送", notes = "短信发送")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String SendValidateCode(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObject.put("status", 0);
            jsonObject.put("msg", "参数有误");
            return jsonObject.toString();
        }
        String type = jsonO.getString("type");
        String scene = jsonO.getString("scene");
        String mobile = jsonO.getString("mobile");
        String send = jsonO.getString("send");
        String verify_code = jsonO.getString("verify_code");
        String unique_id = jsonO.getString("unique_id");
        if (null == mobile || "".equals(mobile)){
            jsonObject.put("status", 0);
            jsonObject.put("msg", "参数有误");
            return jsonObject.toString();
        }
		String mobile_code = (String) redisTemplate.opsForValue().get(mobile);
		if(mobile_code != null){
			jsonObject.put("status",0);
			jsonObject.put("msg","发送太频繁");
			return jsonObject.toString();
		}
		try{
		sendMessageService.sendVerifyCode(type,scene,mobile,send,verify_code,unique_id);
			jsonObject.put("status",1);
			jsonObject.put("msg","发送成功");
		}catch (Exception e){
			jsonObject.put("status",0);
			jsonObject.put("msg","发送太频繁");
		}
		return jsonObject.toString();
	}

    /**
     * @param user_id
     * @return
     * 根据用户ID 获取信息
     */
	@RequestMapping(value = "user_info_by_id",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "根据用户ID 获取信息", notes = "根据用户ID 获取信息")
	public @ResponseBody String userInfoById(@RequestParam(required = true)String user_id, HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers1 = initUser(request);
        if (null == tpUsers1) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
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
     * @return
     * 根据invite_code 获取用户信息
     */
	@RequestMapping(value = "get_invite_code_user_info",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "根据invite_code 获取用户信息", notes = "根据invite_code 获取用户信息")
	public @ResponseBody String getInviteCodeUerInfo(@RequestParam(required = true)String invite_code, HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers1 = initUser(request);
        if (null == tpUsers1) {
            TpUsers tpUsers = tpUsersService.findOneByInvite(invite_code);
            if (null != tpUsers) {
                data = tpUsersService.getUserlJson(tpUsers);
            }
            jsonObj.put("result",data);

        }
        return jsonObj.toString();
    }
	/**
	 * @return
	 * 更新用户信息
	 */
	@RequestMapping(value = "userinfo",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String UserInfo(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers = initUser(request);
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String head_pic = jsonO.getString("head_pic");
        String nickname = jsonO.getString("nickname");
        String qq = jsonO.getString("qq");
        String sex = jsonO.getString("sex");
        String birthday = jsonO.getString("birthday");
        String province = jsonO.getString("province");
        String city = jsonO.getString("city");
        String district = jsonO.getString("district");
        String email = jsonO.getString("email");
        String mobile = jsonO.getString("mobile");
        String scene = jsonO.getString("scene");
        String wechat_qrcode = jsonO.getString("wechat_qrcode");
        String wechat = jsonO.getString("wechat");
        String mobile_code = jsonO.getString("mobile_code");
        String voice_notice = jsonO.getString("voice_notice");
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		if(mobile_code == null || mobile == null){
			tpUsersService.updateUser(tpUsers.getUser_id(),head_pic,nickname,qq,sex,birthday,province,city,district,email,scene,wechat_qrcode,wechat);
		}else{
			if(mobile_code != null || mobile != null){
			tpUsersService.updateUserAndMobile(tpUsers.getUser_id(),head_pic,nickname,qq,sex,birthday,province,city,district,email,scene,wechat_qrcode,wechat,mobile);
			}else{
				jsonObj.put("status", 0);
				jsonObj.put("msg", "验证码不能为空!");
				return jsonObj.toString();
			}
		}
		jsonObj.put("status", 1);
		jsonObj.put("msg", "修改成功!");
		jsonObj.put("result",jsonArray);
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 发卡行列表
	 */
	@RequestMapping(value = "bank",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "发卡行列表", notes = "发卡行列表")
	public @ResponseBody String Bank(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONArray bankList = new JSONArray();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		List<TpBank>tpBanks = tpBankService.getBankList(tpUsers.getUser_id());
		for (TpBank tpBank : tpBanks) {
			JSONObject bank = tpBankService.getBank(tpBank);
			bankList.add(bank);
		}
		jsonObj.put("status", 1);
		jsonObj.put("msg", "ok!");
		jsonObj.put("result",bankList);
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 投诉
	 */
	@RequestMapping(value = "send_report",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "投诉", notes = "投诉")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String sendReport(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String address = jsonO.getString("address");
		String report_mess = jsonO.getString("report_mess");
		String area = jsonO.getString("area");
		String image = jsonO.getString("image");
		if (null == address || "".equals(address) || null == report_mess || "".equals(report_mess)
		 || null == area || "".equals(area) || null == image || "".equals(image)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpReportList tpReportList = new TpReportList();
		tpReportList.setUser_id(tpUsers.getUser_id());
		tpReportList.setAddress(address);
		tpReportList.setReport_mess(report_mess);
		tpReportList.setImage(image);
		tpReportList.setArea(area);
		tpReportList.setSend_time((int)(new Date().getTime()/1000));
		tpReportList.setStatus(false);
		tpReportList.setReport_mess("");
		tpReportList.setHan_time(null);
		try{
		tpReportListService.save(tpReportList);
			jsonObj.put("status", 0);
			jsonObj.put("msg", "投诉处理中!");
		        }catch (Exception e){
			jsonObj.put("status", 2);
			jsonObj.put("msg", "投诉失败!");
		}
		return jsonObj.toString();
	}
	/**
     * @return
     * 投诉记录
     */
    @RequestMapping(value = "report_list",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "投诉记录", notes = "投诉记录")
    public @ResponseBody String ReportList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        List<TpReportList> tpReportLists = tpReportListService.getSendReport(tpUsers.getUser_id());
        for (TpReportList tpReportList : tpReportLists) {
          JSONObject jsonObject =  tpReportListService.getJsonReport(tpReportList);
          jsonArray.add(jsonObject);
        }
        jsonObj.put("result",jsonArray);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 根据im_id 获取用户信息
     */
	@RequestMapping(value = "user_info_by_im_id",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "根据im_id 获取用户信息", notes = "根据im_id 获取用户信息")
	public @ResponseBody String userInfoByImId(@RequestParam(required = true)String im_id, HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers1 = initUser(request);
        if (null == tpUsers1) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
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
	@RequestMapping(value = "join",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "加入团队", notes = "加入团队")
	public @ResponseBody String join(@RequestParam(required = true)String invite_code, HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers1 = initUser(request);
        if (null == tpUsers1) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
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
                jsonObj.put("status", 1);
                jsonObj.put("msg", "请求成功!");
            }
		}
		jsonObj.put("result",data);
		return jsonObj.toString();
	}

    /**
     * @return
     * 意向代理人数统计
     */
	@RequestMapping(value = "team_device_count",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "意向代理人数统计", notes = "意向代理人数统计")
	public @ResponseBody String teamDeviceCount(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		TpUsers tpUsers1 = initUser(request);
        String startTime = String.valueOf(Util.getStartTime());
        String endTime = String.valueOf(Util.getnowEndTime());
        if (null == tpUsers1) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        int allNum = tpUsersService.countByParentId(tpUsers1.getUser_id());
        int dayNum = tpUsersService.countByParentIdDay(tpUsers1.getUser_id(), Integer.parseInt(startTime), Integer.parseInt(endTime));

        data.put("count", allNum);
        data.put("today", dayNum);

        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
		jsonObj.put("result",data);
		return jsonObj.toString();
	}

    /**
     * @return
     * 机器顶部统计
     */
	@RequestMapping(value = "device_number",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "机器顶部统计", notes = "机器顶部统计")
	public @ResponseBody String deviceNumber(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		TpUsers tpUsers1 = initUser(request);
        if (null == tpUsers1) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
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
        jsonObj.put("result",data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 热门城市
     */
	@RequestMapping(value = "get_hot_city",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "热门城市", notes = "热门城市")
	public @ResponseBody String getHotCity(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id",1);
		jsonObject.put("area_name","北京");
		jsonObject.put("level","1");
		jsonObject.put("parent_id","0");
		jsonArray.add(jsonObject);
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("id","10543");
		jsonObject1.put("area_name","上海");
		jsonObject1.put("level","1");
		jsonObject1.put("parent_id","0");
		jsonArray.add(jsonObject1);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("id","28558");
		jsonObject2.put("area_name","深圳");
		jsonObject2.put("level","2");
		jsonObject2.put("parent_id","28240");
		jsonArray.add(jsonObject2);
		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("id","28241");
		jsonObject3.put("area_name","广州");
		jsonObject3.put("level","2");
		jsonObject3.put("parent_id","28240");
		jsonArray.add(jsonObject3);
		JSONObject jsonObject4 = new JSONObject();
		jsonObject4.put("id","338");
		jsonObject4.put("area_name","天津");
		jsonObject4.put("level","1");
		jsonObject4.put("parent_id","0");
		jsonArray.add(jsonObject4);
		JSONObject jsonObject5 = new JSONObject();
		jsonObject5.put("id","31929");
		jsonObject5.put("area_name","重庆");
		jsonObject5.put("level","1");
		jsonObject5.put("parent_id","0");
		jsonArray.add(jsonObject5);
		jsonObj.put("region_list",jsonArray);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "ok!");
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 删除银行卡
	 */
	@RequestMapping(value = "del_bank",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "删除银行卡", notes = "删除银行卡")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String delBank(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String id = jsonO.getString("id");
        if (null == id || "".equals(id)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		try{
			tpUserBankService.deleteBank(tpUsers.getUser_id(),id);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "删除成功!");

		}catch (Exception e){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "删除失败!");
		}
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 添加银行卡(未测试)
	 */
	@RequestMapping(value = "add_bank",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "添加银行卡", notes = "添加银行卡")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String addBank(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String real_name = jsonO.getString("real_name");
        String mobile = jsonO.getString("mobile");
        String bank_card = jsonO.getString("bank_card");
        String bank_id = jsonO.getString("bank_id");
        String bank_name = jsonO.getString("bank_name");
        String branch = jsonO.getString("branch");
        String mobile_code = jsonO.getString("mobile_code");
        if (null == real_name || "".equals(real_name) || null == mobile || "".equals(mobile)
                || null == bank_card || "".equals(bank_card) || null == bank_id || "".equals(bank_id)
                || null == bank_name || "".equals(bank_name) || null == branch || "".equals(branch)
                || null == mobile_code || "".equals(mobile_code)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }

		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		String code = tpSmsLogService.selectLog(mobile);
		if(mobile_code.equals(code)){
			TpUserBank tpUserBank = new TpUserBank();
			tpUserBank.setAdd_time((int)(new Date().getTime()/1000));
			tpUserBank.setBank_card(bank_card);
			tpUserBank.setBank_id(Integer.valueOf(bank_id));
			tpUserBank.setBank_name(bank_name);
			tpUserBank.setBranch(branch);
			tpUserBank.setBranch_area(null);
			tpUserBank.setId_card(null);
			tpUserBank.setIs_delete(Byte.valueOf("0"));
			tpUserBank.setMobile(mobile);
			tpUserBank.setReal_name(real_name);
			tpUserBank.setUser_id(tpUsers.getUser_id());
			tpUserBankService.save(tpUserBank);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "请求成功!");
			jsonObj.put("result","");
			return jsonObj.toString();
		}else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "验证码不正确");
			jsonObj.put("result","");
			return jsonObj.toString();
		}
	}
	/**
	 * @return
	 * 删除地址
	 */
	@RequestMapping(value = "del_address",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "删除地址", notes = "删除地址")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String delAddress(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String id = jsonO.getString("id");
        if (null == id || "".equals(id)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		try{
		tpUsersService.deleteAddress(tpUsers.getUser_id(),id);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "删除成功!");
		        }catch (Exception e){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "删除失败!");
		}
		return jsonObj.toString();
	}
    /**
     * @return
     * 个人中心 总收益、广告收益、已提现
     */
	@RequestMapping(value = "user_account_statistics",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "个人中心 总收益、广告收益、已提现", notes = "个人中心 总收益、广告收益、已提现")
	public @ResponseBody String userAccountStatistics(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
        BigDecimal income = tpDeviceService.getSumMoneyDevice(tpUsers.getUser_id());
        BigDecimal ad_income = tpUsersService.selectFrozen(tpUsers.getUser_id());
        BigDecimal draw = tpWithdrawalsService.selectWithdrawalsMoney(tpUsers.getUser_id());
        jsonObject.put("income",income);
        jsonObject.put("ad_income",ad_income);
        jsonObject.put("draw",draw);
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
	}
    /**
     * @return
     * 用户提现
     */
    @RequestMapping(value = "get_money",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "用户提现", notes = "用户提现")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String getMoney(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonO = getRequestJson(request);
        if(null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        Integer id = jsonO.getInteger("id");
        float money = jsonO.getFloat("money");
        if (null == id || "".equals(id) || money > 0.00 || "".equals(money)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
		TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        try{
        tpWithdrawalsService.ApplyForWithdrawals(tpUsers.getUser_id(),id,money);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
                }catch (Exception e){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "提现失败!");
        }
        return jsonObj.toString();
    }
    /**
     * @return
     * 信用卡列表
     */
    @RequestMapping(value = "creditCard",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "信用卡列表", notes = "信用卡列表")
    public @ResponseBody String creditCard(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject object = new JSONObject();
		TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        List<TpCardList>tpCardLists = tpCardListSevice.selectCardList();
        int count = tpCardListSevice.countCard();
        for (TpCardList tpCardList : tpCardLists) {
            JSONObject jsonObject = tpCardListSevice.getJsonString(tpCardList);
            object.put("0",jsonObject);
        }
        object.put("count",count);
        object.put("showpage","");
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        jsonObj.put("result",object);
        return jsonObj.toString();
    }
    /**
     * @return
     * 新添申请人信息
     */
    @RequestMapping(value = "addApplyPeople",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "新添申请人信息", notes = "新添申请人信息")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String addApplyPeople(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String cardid = jsonO.getString("cardid");
		String name = jsonO.getString("name");
		String idcard = jsonO.getString("idcard");
		String mobile = jsonO.getString("mobile");
		String mobile_code = jsonO.getString("mobile_code");
		String area = jsonO.getString("area");
		if (null == cardid || "".equals(cardid) || null == name || "".equals(name)
		 || null == idcard || "".equals(idcard) || null == mobile || "".equals(mobile)
		 || null == mobile_code || "".equals(mobile_code)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        try{
        tpApplyCardService.save(tpUsers.getUser_id(),cardid,name,idcard,mobile,mobile_code,area);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
                }catch (Exception e){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "新增失败!");
        }
        return jsonObj.toString();
    }
    /**
     * @param id
     * @return
     * 删除信用卡申请人
     */
    @RequestMapping(value = "delApplyPeople",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "删除信用卡申请人", notes = "删除信用卡申请人")
    public @ResponseBody String delApplyPeople(HttpServletRequest request,
                                               @RequestParam(required = true)int id){
        JSONObject jsonObj = new JSONObject();
        JSONObject object = new JSONObject();
		TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        try{
        tpApplyCardService.deleteApplyPeople(tpUsers.getUser_id(),id);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "ok!");
                }catch (Exception e){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "删除失败!");
        }
        return jsonObj.toString();
    }
    /**
     * @return
     * 申请商务合作
     */
	@RequestMapping(value = "application_for_business_cooperation",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "申请商务合作", notes = "申请商务合作")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String applicationForBusinessCooperation(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String code = jsonO.getString("code");
		String name = jsonO.getString("name");
		String email = jsonO.getString("email");
		String province = jsonO.getString("province");
		String city = jsonO.getString("city");
		String district = jsonO.getString("district");
		String type = jsonO.getString("type");
		String bank_name = jsonO.getString("bank_name");
		String ways_of_cooperation = jsonO.getString("ways_of_cooperation");
		String company = jsonO.getString("company");
		String day_number = jsonO.getString("day_number");
		String details = jsonO.getString("details");
		String files = jsonO.getString("files");
		String reason = jsonO.getString("reason");
		if (null == code || "".equals(code) || null == name || "".equals(name)
				|| null == email || "".equals(email) || null == province || "".equals(province)
				|| null == city || "".equals(city) || null == district || "".equals(district)
				|| null == type || "".equals(type) || null == company || "".equals(company)
				|| null == files || "".equals(files)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
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
        tpApplication.setUser_id(tpUsers.getUser_id());
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
        tpApplication.setMobile(tpUsers.getMobile());
        tpApplication.setReason(reason);
        tpApplication.setRead(Integer.parseInt(""));
        tpApplication.setReply_mess("");
        int result = tpUsersService.insertApplicationforBusinessCooperation(tpApplication);
        if (result > 0){
            jsonObj.put("status", 1);
            jsonObj.put("msg", "ok!");
        }
        jsonObj.put("result",data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
	/**
	 * @return
	 * 提交信用卡申请
	 */
	@RequestMapping(value = "applyCard",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "提交信用卡申请", notes = "提交信用卡申请")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String applyCard(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String mobile_code = jsonO.getString("mobile_code");
		String cardid = jsonO.getString("cardid");
		String id = jsonO.getString("id");
		if (null == mobile_code || "".equals(mobile_code) || null == cardid || "".equals(cardid)
		 || null == id || "".equals(id)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		int count = tpSmsLogService.selectCode(mobile_code);
		if(count > 0 ){
		tpCardListSevice.save(mobile_code,cardid,id);
		}else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "验证码错误!");
			return jsonObj.toString();
		}
		jsonObj.put("status", 1);
		jsonObj.put("msg", "ok!");
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 修改密码
	 */
	@RequestMapping(value = "password",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "修改密码", notes = "修改密码")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String Password(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String new_password = jsonO.getString("new_password");
		String confirm_password = jsonO.getString("confirm_password");
		String code = jsonO.getString("code");
		if (null == new_password || "".equals(new_password) || null == confirm_password || "".equals(confirm_password)
		 || null == code || "".equals(code)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		int count = tpUsersService.selectMobile(tpUsers.getUser_id());
		if(count > 0){
			String newPwd = MD5Utils.md5("TPSHOP" + new_password);
			String pwd = MD5Utils.md5("TPSHOP" + confirm_password);
			if(newPwd.equals(pwd)){
				int num = tpSmsLogService.selectCode(code);
				if(num > 0 ){
				tpUsersService.updatePwd(tpUsers.getUser_id(),newPwd);

				}else{
					jsonObj.put("status", 0);
					jsonObj.put("msg", "请输入正确的验证码!");
					return jsonObj.toString();
				}
			}else{
				jsonObj.put("status", 0);
				jsonObj.put("msg", "两次密码输入不一致!");
				return jsonObj.toString();
			}
		}else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "请先绑定手机号!");
			return jsonObj.toString();
		}
		jsonObj.put("status", 1);
		jsonObj.put("msg", "ok!");
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 收益明细中的收益统计
	 */
	@RequestMapping(value = "income_count",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "收益明细中的收益统计", notes = "收益明细中的收益统计")
	public @ResponseBody String incomeCount(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		JSONObject jsonObject = tpUsersService.incomeCount(tpUsers.getUser_id());
		jsonObj.put("status", 1);
		jsonObj.put("msg", "ok!");
		jsonObj.put("result",jsonObject);
		return jsonObj.toString();
	}
	/**
	 * @param id
	 * @return
	 * 交易申请中心图
	 */
	@RequestMapping(value = "cardDetail",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "交易申请中心图", notes = "交易申请中心图")
	public @ResponseBody String cardDetail(HttpServletRequest request,
										   @RequestParam(required = true)int id){
		JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		JSONObject jsonObject = tpCardListSevice.cardDetail(id);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		jsonObj.put("result",jsonObject);
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 信用卡申请列表
	 */
	@RequestMapping(value = "getCardList",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "信用卡申请列表", notes = "信用卡申请列表")
	public @ResponseBody String getCardList(HttpServletRequest request){
	    JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
	    JSONObject object = new JSONObject();
		List<TpApplyReport>tpApplyReports = tpApplyReportService.getCardList();
		for (TpApplyReport tpApplyReport : tpApplyReports) {
			JSONObject jsonObject = tpApplyReportService.getJsonApplyReport(tpApplyReport);
			object.put("0",jsonObject);
		}
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
		jsonObj.put("result",object);
		return jsonObj.toJSONString();
	}
    /**
     * @return
     * 添加支付宝账号
     */
    @RequestMapping(value = "add_ali_account",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "添加支付宝账号", notes = "添加支付宝账号")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String addAliAccount(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String real_name = jsonO.getString("real_name");
		String mobile = jsonO.getString("mobile");
		String account = jsonO.getString("account");
		String mobile_code = jsonO.getString("mobile_code");
		if (null == real_name || "".equals(real_name) || null == mobile || "".equals(mobile)
		 || null == account || "".equals(account) || null == mobile_code || "".equals(mobile_code)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
        int count = tpSmsLogService.selectCode(mobile_code);
        if(count > 0 ){
            tpUserAliAccountService.addAliAccount(real_name,mobile,account);
        }else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "验证码错误!");
			return jsonObj.toString();
        }
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!!");
        return jsonObj.toString();
    }
    /**
     * @return
     * 支付账号列表
     */
    @RequestMapping(value = "ali_account",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "支付账号列表", notes = "支付账号列表")
    public @ResponseBody String AliAccount(HttpServletRequest request,
										   @RequestParam(required = false,defaultValue = "1")String page){
        JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
        JSONObject data = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        LimitPageList  limitPageList = tpUserAliAccountService.AliAccount(page);
        List<TpUserAliAccount> list = (List<TpUserAliAccount>)limitPageList.getList();
        for (TpUserAliAccount tpUserAliAccount : list) {
           JSONObject jsonObject =  tpUserAliAccountService.getJsonUserAliAccount(tpUserAliAccount);
           jsonArray.add(jsonObject);
        }
        data.put("current_page", limitPageList.getPage().getPageNow());
        data.put("total", limitPageList.getPage().getTotalCount());
        data.put("per_page", limitPageList.getPage().getPageSize());
        data.put("last_page",limitPageList.getPage().getTotalPageCount());
        data.put("data",jsonArray);
        jsonObj.put("result",data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
    /**
     * @param id
     * @return
     * 支付宝提现
     */
    @RequestMapping(value = "get_money_ali",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "支付宝提现", notes = "支付宝提现")
    public @ResponseBody String getMoneyAli(HttpServletRequest request,
											@RequestParam(required = true)String id,
                                            @RequestParam(required = true)String money){
        JSONObject jsonObj= new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
        try{
            tpUserAliAccountService.getMoneyAli(id,money);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }catch (Exception e){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "提现失败!");
        }
        return jsonObj.toString();
    }

	/**
	 * @param flag
	 *  1为首页2为个人中心3为订单4为消息
	 * @return
	 */
	@RequestMapping(value = "plateMsg",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "1为首页2为个人中心3为订单4为消息", notes = "1为首页2为个人中心3为订单4为消息")
	public @ResponseBody String plateMsg(HttpServletRequest request,
											   @RequestParam(required = true)int flag){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpPlateMsg tpPlateMsg = tpUsersService.findPlatMsgByFlagd(flag);
		if (null != tpPlateMsg){
			data.put("id", tpPlateMsg.getId());
			data.put("content", tpPlateMsg.getContent());
			data.put("title", tpPlateMsg.getTitle());
			data.put("create_time", tpPlateMsg.getCreate_time());
			data.put("right_title", tpPlateMsg.getRight_title());
			data.put("flag", tpPlateMsg.getFlag());
			jsonObj.put("result", data);

			jsonObj.put("status", 1);
			jsonObj.put("msg", "请求成功!");
		}
		return jsonObj.toString();
	}

	/**
	 * @return
	 * 系统通知
	 */
	@RequestMapping(value = "sysMessage",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "系统通知", notes = "系统通知")
	public @ResponseBody String sysMessage(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		List<TpSysMessage> tpSysMessageList = tpUsersService.findAllSysMessage();
		for (TpSysMessage tpMessage: tpSysMessageList) {
			JSONObject jsonObj1 = new JSONObject();
			jsonObj1.put("send_msg", tpMessage.getSend_msg());
			jsonObj1.put("send_time", tpMessage.getSend_time());
			jsonObj1.put("sys_msg_id", tpMessage.getId());
			TpSysMessageUser tpSysMessageUser = tpUsersService.findOneBySysIdAndUserId(tpMessage.getId(), tpUsers.getUser_id());
			if (null != tpSysMessageUser){
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("id", tpSysMessageUser.getId());
				jsonObject2.put("user_id", tpSysMessageUser.getUser_id());
				jsonObject2.put("sys_msg_id", tpSysMessageUser.getSys_msg_id());
				jsonObject2.put("status", tpSysMessageUser.getStatus());
				jsonObject2.put("create_time", tpSysMessageUser.getCreate_time());
				jsonObj1.put("user", jsonObject2);
			}else{
				jsonObj1.put("user", "");
			}
			jsonArray.add(String.valueOf(jsonObj1));
		}
		jsonObj.put("result", jsonArray);

		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}

	/**
	 * @return
	 * 系统通知判断
	 */
	@RequestMapping(value = "getSysList",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "系统通知判断", notes = "系统通知判断")
	public @ResponseBody String getSysList(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		List<TpSysMessage> tpSysMessageList = tpUsersService.findAllSysMessage();
		for (TpSysMessage tpMessage: tpSysMessageList) {
			JSONObject jsonObj1 = new JSONObject();
			jsonObj1.put("send_msg", tpMessage.getSend_msg());
			jsonObj1.put("send_time", tpMessage.getSend_time());
			jsonObj1.put("sys_msg_id", tpMessage.getId());
			TpSysMessageUser tpSysMessageUser = tpUsersService.findOneBySysIdAndUserId(tpMessage.getId(), tpUsers.getUser_id());
			if (null != tpSysMessageUser){
				jsonObj1.put("user_id", tpSysMessageUser.getUser_id());
				jsonObj1.put("read", "1");
			}else{
				jsonObj1.put("read", "0");
			}
			jsonArray.add(jsonObj1);
		}
		jsonObj.put("result", jsonArray);

		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 用户话费券列表
	 */
	@RequestMapping(value = "cartGift",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "用户话费券列表", notes = "用户话费券列表")
	public @ResponseBody String cartGift(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONObject object = new JSONObject();
		JSONObject data = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpUserLevel tpUserLevel = tpUserLevelService.getLevelName(tpUsers.getLevel());
		int count = tpCartGiftService.getCount(tpUsers.getUser_id());
		PageInfo pageInfo = tpCartGiftService.query(count,tpUsers.getUser_id());
		List<TpCartGift> tpCartGifts = pageInfo.getList();
		for (TpCartGift tpCartGift : tpCartGifts) {
			jsonObject = tpCartGiftService.getJson(tpUsers,tpCartGift,tpUsers.getLevel(),tpUsers.getNickname());
			jsonArray.add(jsonObject);
		}
		data.put("data",jsonArray);
		object.put("page", pageInfo.getPageNum());
		object.put("count", pageInfo.getTotal());
		object.put("per_page", pageInfo.getPrePage());
		object.put("totalPages",pageInfo.getPages());
		jsonObj.put("result",data);
		jsonObj.put("page",object);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 话费自助充值
	 */
	@RequestMapping(value = "refill",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "话费自助充值", notes = "话费自助充值")
	public @ResponseBody String refillH(HttpServletRequest request,
										 @RequestParam(required = true)int id,
										 @RequestParam(required = true)String mobile,
										 @RequestParam(required = true)String code){
		JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		int count = tpSmsLogService.selectCode(code);
		if(count <= 0 ){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "验证码错误!");
			return jsonObj.toString();
		}
        mobileCheck mobileCheck = new mobileCheck();
		if(!mobileCheck.isMobile(mobile)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "手机格式错误!");
			return jsonObj.toString();
        }
        TpCartGift tpCartGift = tpCartGiftService.getCartGift(id,tpUsers.getUser_id());
		if(tpCartGift != null){
            Map<String,Object> res = tpCartGiftService.refill(mobile, tpCartGift.getMoney());
            if(res != null){
                if(res.get("code").equals("1")){
            int i = tpCartGiftService.update(id,tpUsers.getUser_id(),mobile, (String) res.get("taskid"));
            }else{
					jsonObj.put("status", 0);
					jsonObj.put("msg", "自助充值系统维护中!");
					return jsonObj.toString();
             }
            }
        }else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "已经充值过或不存在该券!");
			return jsonObj.toString();
        }
		jsonObj.put("status", 1);
		jsonObj.put("msg", "自助充值兑换成功!");
		return jsonObj.toString();
    }
    /**
     * @param
     * @return
     * 检测该用户有没有未领取的礼品
     */
    @RequestMapping(value = "checkGift",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "检测该用户有没有未领取的礼品", notes = "检测该用户有没有未领取的礼品")
    public @ResponseBody String checkGift(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if(tpUsers == null){
			jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
		}
        JSONObject jsonObject =new JSONObject();
        TpCartGift tpCartGift = tpCartGiftService.getCartGiftByUserId(tpUsers.getUser_id());
        if(tpCartGift != null){
            jsonObject.put("user_id",tpCartGift.getUser_id());
            jsonObject.put("money",tpCartGift.getMoney());
        	jsonObj.put("result",jsonObject);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "请求成功!");
        }else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "没有该礼品!");
		}
        return jsonObj.toString();
    }
    /**
     * @param
     * @return
     * 领取礼品
     */
    @RequestMapping(value = "getGifts",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "领取礼品", notes = "领取礼品")
    public @ResponseBody String getGifts(HttpServletRequest request,
                                         @RequestParam(required = true)int id,
                                         @RequestParam(required = true)int gift_type){
        JSONObject jsonObj = new JSONObject();
        String gift_name = "";
		TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        if(gift_type == 1){
            gift_name = "话费";
        }else{
            gift_name = "油卡";
        }
        int date = (int)(new Date().getTime()/1000);
        int a = tpCartGiftService.getGifts(tpUsers.getUser_id(),id,gift_type,gift_name,date);
        if(a >0){
            jsonObj.put("status", 1);
            jsonObj.put("msg", "领取成功!");
        }
            return jsonObj.toString();
    }
    /**
     * @param
     * @return
     * 已领取列表页
     */
    @RequestMapping(value = "getGiftList",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "已领取列表页", notes = "已领取列表页")
    public @ResponseBody String getGiftList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject page = new JSONObject();
        String gift_name = "";
		TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        PageInfo pageInfo = tpCartGiftService.queryList(tpUsers.getUser_id());
        List<TpCartGift> tpCartGifts =pageInfo.getList();
		for (TpCartGift tpCartGift : tpCartGifts) {
			jsonObject = tpCartGiftService.getJsonCartGift(tpCartGift);
			jsonArray.add(jsonObject);
		}

		jsonObj.put("result",jsonArray);
		page.put("page", pageInfo.getPageNum());
		page.put("count", pageInfo.getTotal());
		page.put("per_page", pageInfo.getPrePage());
		page.put("totalPages",pageInfo.getPages());
		jsonObj.put("page",page);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
    }
	/**
	 * @param
	 * @return
	 * 立即使用
	 */
	@RequestMapping(value = "useGifts",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "立即使用", notes = "立即使用")
	public @ResponseBody String useGifts(HttpServletRequest request,
											@RequestParam(required = true)int id){
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpCartGift tpCartGift = tpCartGiftService.useGifts(tpUsers.getUser_id(),id);
		jsonObject.put("money",tpCartGift.getMoney());
		if (tpCartGift != null){
			jsonObj.put("result",jsonObject);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "ok!");
		}else {
			jsonObj.put("status", 0);
			jsonObj.put("msg", "该礼品不存在!");
		}
		return jsonObj.toString();
	}


	/**
	 * @param
	 * @return
	 * 提现支付密码设置
	 */
	@RequestMapping(value = "getMoneyPassword",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "提现支付密码设置", notes = "提现支付密码设置")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String getMoneyPassword(HttpServletRequest request){
		JSONObject jsonObj =new JSONObject();
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String mobile = jsonO.getString("mobile");
		String code = jsonO.getString("code");
		String paypwd = jsonO.getString("paypwd");
		String repaypwd = jsonO.getString("repaypwd");
		if (null == mobile || "".equals(mobile) || null == code || "".equals(code)
		 || null == paypwd || "".equals(paypwd) || null == repaypwd || "".equals(repaypwd)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpSmsLog tpSmsLog = tpSmsLogService.selectvalidateCode(code, mobile);
		if(tpSmsLog != null && tpSmsLog.getStatus() == 1 ){
			if(paypwd == null || repaypwd ==null){
				jsonObj.put("status", 0);
				jsonObj.put("msg", "提现码不能为空!");
				return jsonObj.toString();
			}else if(!paypwd.equals(repaypwd)){
				jsonObj.put("status", 0);
				jsonObj.put("msg", "提现码不一致!");
				return jsonObj.toString();
			}else{
				String password = MD5Utils.md5("TPSHOP" + paypwd);
				int i = tpUsersService.updatePayPwd(password,tpUsers.getUser_id());
				if(i>0){
					jsonObj.put("status", 1);
					jsonObj.put("msg", "请求成功!");
				}
				return jsonObj.toString();
			}
		}else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "验证码错误!");
			return jsonObj.toString();
		}
	}
	/**
	 * @param
	 * @return
	 * 中秋/国庆个人查看海报评论
	 */
	@RequestMapping(value = "midAutumn",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "中秋/国庆个人查看海报评论", notes = "中秋/国庆个人查看海报评论")
	public @ResponseBody String midAutumn(HttpServletRequest request,
										  @RequestParam(required = true)String ha_id){
		JSONObject jsonObj =new JSONObject();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		List<TpFestivalsContent> tpFestivalsContents = tpFestivalsContentService.midAutumn(ha_id);
		for (TpFestivalsContent tpFestivalsContent : tpFestivalsContents) {
			jsonObject = tpFestivalsContentService.getJson(tpFestivalsContent);
			jsonArray.add(jsonObject);
		}
		jsonObj.put("result",jsonArray);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}
	/**
	 * @param
	 * @return
	 * 中秋/国庆个人制作海报
	 */
	@RequestMapping(value = "setMidAutumn",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "中秋/国庆个人制作海报", notes = "中秋/国庆个人制作海报")
	public @ResponseBody String setMidAutumn(HttpServletRequest request,
											 @RequestParam(required = true)String article_id){
		JSONObject jsonObj =new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		int date = (int)(new Date().getTime()/1000);
		int i= tpFestivalsService.setMidAutumn(tpUsers.getUser_id(),date,article_id);
		if(i>0){
			jsonObj.put("status", 1);
			jsonObj.put("msg", "请求成功!");
		}
		return jsonObj.toString();
	}
	/**
	 * @param
	 * @return
	 * 评论海报
	 */
	@RequestMapping(value = "messMidAutumn",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "评论海报", notes = "评论海报")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String messMidAutumn(HttpServletRequest request){
		JSONObject jsonObj =new JSONObject();
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String content = jsonO.getString("content");
		String ha_id = jsonO.getString("ha_id");
		String user_id = jsonO.getString("user_id");
		if (null == content || "".equals(content) || null == ha_id || "".equals(ha_id)
		 || null == user_id || "".equals(user_id)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpFestivals tpFestivals = tpFestivalsService.selectFestivals(user_id,ha_id);
		if(tpFestivals == null){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "不存在海报!");
			return jsonObj.toString();
		}
		int i = tpFestivalsContentService.insertFestivals(ha_id,user_id,content);
		if(i>0){
			jsonObj.put("status", 1);
			jsonObj.put("msg", "评论成功!");
		}else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "评论失败!");
		}
		return jsonObj.toString();
	}
	/**
	 * @param
	 * @return
	 * 获取后台海报模块
	 */
	@RequestMapping(value = "getMidAutumnCate",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "获取后台海报模块", notes = "获取后台海报模块")
	public @ResponseBody String getMidAutumnCate(HttpServletRequest request,
												 @RequestParam(required = true)String article_id){
		JSONObject jsonObj =new JSONObject();
		JSONObject jsonObject = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		jsonObject = tpFestivalsCateService.selectCate(article_id);
		jsonObj.put("result",jsonObject);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "获取成功!");
		return jsonObj.toString();
	}
	/**
	 * @param
	 * @return
	 * 手机号修改密码
	 */
	@RequestMapping(value = "password2",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "手机号修改密码", notes = "手机号修改密码")
	public @ResponseBody String password2(HttpServletRequest request,
										  @RequestParam(required = true)String mobile,
											@RequestParam(required = true)String code,
											@RequestParam(required = true)String new_password,
											@RequestParam(required = true)String confirm_password){
		JSONObject jsonObj = new JSONObject();
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpUsers tpUser = tpUsersService.getMobile(mobile);
		if(tpUser.getMobile()== null){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "两次密码不一致!");
			return jsonObj.toString();
		}
		TpSmsLog tpSmsLog = tpSmsLogService.selectvalidateCode(code, mobile);
		if(tpSmsLog != null && tpSmsLog.getStatus() ==1 ){
			if(!new_password.equals(confirm_password)){
				jsonObj.put("status", 0);
				jsonObj.put("msg", "两次密码不一致!");
			}else {
				int i = tpUsersService.updatePassword(tpUser.getUser_id(), MD5Utils.md5("TPSHOP" + confirm_password));
				if(i>0){
					tpUsersService.updateSetPass(tpUser.getUser_id());
					jsonObj.put("status", 1);
					jsonObj.put("msg", "修改成功!");
				}
			}
		}else{
			jsonObj.put("status", 0);
			jsonObj.put("msg", "验证码为空!");
		}
		return jsonObj.toString();
	}


	/**
	 * @return
	 * 获取动态
	 */
	@RequestMapping(value = "getDynamic",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "获取动态", notes = "获取动态")
	public @ResponseBody String getDynamic(HttpServletRequest request,
												 @RequestParam(required = true) Integer p){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray data1 = new JSONArray();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		LimitPageList limitPageList = tpUsersService.getLimitPageListByDynamic(tpUsers.getUser_id(), p);
		for (TpDynamic tpDynamic : (List<TpDynamic>)limitPageList.getList()) {
			JSONObject jsonObject = tpUsersService.getTpDynamicJson(tpDynamic);
			data1.add(jsonObject);
		}
		JSONObject object = new JSONObject();
		object.put("count", limitPageList.getPage().getTotalCount());
		object.put("page", limitPageList.getPage().getPageNow());
		object.put("totalPages", limitPageList.getPage().getTotalPageCount());

		data.put("page", object);
		data.put("data", data1);
		jsonObj.put("result", data);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}

	/**
	 * @return
	 * 发布动态
	 */
	@RequestMapping(value = "setDynamic",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "发布动态", notes = "发布动态")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String setDynamic(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String image = jsonO.getString("image");
		String content = jsonO.getString("content");
		if (null == image || "".equals(image) || null == content || "".equals(content)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpDynamic tpDynamic = new TpDynamic();
		tpDynamic.setContent(content);
		tpDynamic.setUserId(tpUsers.getUser_id());
		tpDynamic.setImage(image);
		tpDynamic.setAddTime((int)Calendar.getInstance().getTimeInMillis());
		tpDynamic.setFabulous(0);
		tpDynamic.setNums(0);
		tpDynamic.setViewStatus(1);
		tpDynamic.setIsDelete(0);
		int result = tpUsersService.insertTpDynamic(tpDynamic);
		if (result > 0){
			jsonObj.put("result", data);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "发表成功");
		}
		return jsonObj.toString();
	}

	/**
	 * @return
	 * 回复动态
	 */
	@RequestMapping(value = "setReplayMess",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "回复动态", notes = "回复动态")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String setReplayMess(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		Integer did = jsonO.getInteger("did");
		String replay_content = jsonO.getString("replay_content");
		if (null == did || "".equals(did) || null == replay_content || "".equals(replay_content)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpDynamic tpDynamic = tpUsersService.findOneTpDynamicById(did);
		if (null != tpDynamic){
			TpReplay tpReplay = new TpReplay();
			tpReplay.setdId(did);
			tpReplay.setAddTime((int)Calendar.getInstance().getTimeInMillis());
			tpReplay.setUserId(tpUsers.getUser_id());
			tpReplay.setReplayContent(replay_content);
			int result = tpUsersService.insertTpReplay(tpReplay);
			if (result > 0){
				TpDynamic tpDynamic1 = new TpDynamic();
				tpDynamic1.setId(tpDynamic.getId());
				tpDynamic1.setNums(tpDynamic.getNums() + 1);
				int updataresult = tpUsersService.updataDynamicByNums(tpDynamic1);
				if (updataresult > 0){
					jsonObj.put("result", data);
					jsonObj.put("status", 1);
					jsonObj.put("msg", "回复成功");
				}
			}
		}
		return jsonObj.toString();
	}

	/**
	 * @param did
	 * @return
	 * 点赞
	 */
	@RequestMapping(value = "clickFabulous",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "点赞", notes = "点赞")
	public @ResponseBody String clickFabulous(HttpServletRequest request,
										   @RequestParam(required = true) Integer did){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpDynamic tpDynamic = tpUsersService.findOneTpDynamicById(did);
		if (null != tpDynamic){
			TpFabulous tpFabulous = new TpFabulous();
			tpFabulous.setdId(did);
			tpFabulous.setUserId(tpUsers.getUser_id());
			tpFabulous.setAddTime((int)Calendar.getInstance().getTimeInMillis());
			int result = tpUsersService.insertTpFabulous(tpFabulous);
			if (result > 0){
				TpDynamic tpDynamic1 = new TpDynamic();
				tpDynamic1.setId(tpDynamic.getId());
				tpDynamic1.setFabulous(tpDynamic.getFabulous() + 1);
				int updataresult = tpUsersService.updataDynamicByNums(tpDynamic1);
				if (updataresult > 0){
					jsonObj.put("result", data);
					jsonObj.put("status", 1);
					jsonObj.put("msg", "点赞成功");
				}
			}
		}
		return jsonObj.toString();
	}

	/**
	 * @param image
	 * @return
	 * 更改个人动态背景图
	 */
	@RequestMapping(value = "changeBackImg",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "更改个人动态背景图", notes = "更改个人动态背景图")
	public @ResponseBody String changeBackImg(HttpServletRequest request,
										   @RequestParam(required = true) String image){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpUsers tpUsers1 = new TpUsers();
		tpUsers1.setUser_id(tpUsers.getUser_id());
		tpUsers1.setBackimg(image);

		int result =  tpUsersService.updataUserByBackimg(tpUsers1);
		if(result > 0){
			jsonObj.put("result", data);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "请求成功!");
		}

		return jsonObj.toString();
	}

	/**
	 * @return
	 * 展示个人动态头像背景图
	 */
	@RequestMapping(value = "headPicBack",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "展示个人动态头像背景图", notes = "展示个人动态头像背景图")
	public @ResponseBody String headPicBack(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		data.put("backimg", tpUsers.getBackimg());
		data.put("head_pic", tpUsers.getHead_pic());
		data.put("nickname", tpUsers.getNickname());
		jsonObj.put("result", data);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}

	@RequestMapping(value = "getUserDynamic",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "获取用户动态", notes = "获取用户动态")
	public @ResponseBody String getUserDynamic(HttpServletRequest request,
										   @RequestParam(required = true) Integer user_id,
										   @RequestParam(required = true) Integer p){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray data1 = new JSONArray();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效!");
			return jsonObj.toString();
		}
		TpUsers tpUsers1 = tpUsersService.findOneByUserId(user_id);
		if (null == tpUsers1) {
			return jsonObj.toString();
		}
		LimitPageList limitPageList = tpUsersService.getLimitPageListByDynamic(tpUsers1.getUser_id(), p);
		for (TpDynamic tpDynamic : (List<TpDynamic>)limitPageList.getList()) {
			JSONObject jsonObject = tpUsersService.getTpDynamicJson(tpDynamic);
			data1.add(jsonObject);
		}
		JSONObject object = new JSONObject();
		object.put("count", limitPageList.getPage().getTotalCount());
		object.put("page", limitPageList.getPage().getPageNow());
		object.put("totalPages", limitPageList.getPage().getTotalPageCount());

		data.put("page", object);
		data.put("data", data1);
		jsonObj.put("result", data);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功");
		return jsonObj.toString();
	}
    /**
     * @return
     * 抽奖接口
     */
    @RequestMapping(value = "applyPrize",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "抽奖接口", notes = "抽奖接口")
    public @ResponseBody String applyPrize(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        String key = "apply_prize_count_"+Util.transferLongToDate("yyyyMMdd",new Date().getTime()/1000)+"_"+tpUsers.getUser_id();
        String o = (String) redisTemplate.opsForValue().get(key);
        org.json.JSONObject object = new org.json.JSONObject(o);
        if(Integer.parseInt((String)object.get("count"))>=6){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "已经超过今日领奖次数!");
            return jsonObj.toString();
        }
        if(Integer.parseInt((String)object.get("bonus_count"))>=1){

        }
        return null;
    }
//TODO 我要加盟
//TODO 首字母排序获取地址列表接口
//TODO H5--信息收集
//TODO 用户基础信息
//TODO 添加意向用户
//TODO 办卡人资料列表
//TODO 帮助朋友
//TODO 领取奖励

}
