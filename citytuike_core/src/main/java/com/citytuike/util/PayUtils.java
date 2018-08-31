package com.citytuike.util;

import com.citytuike.model.*;
import com.citytuike.service.ITpAccountLogService;
import com.citytuike.service.TpOrderService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.AlipayConfig;
import com.citytuike.util.Util;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayUtils {

    @Autowired
    private TpOrderService tpOrderService;
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private ITpAccountLogService iTpAccountLogService;

    // 指向自己实例的私有静态引用，主动创建
    private static PayUtils payUtils = new PayUtils();

    // 私有的构造方法
    private PayUtils(){}

    // 以自己实例为返回值的静态的公有方法，静态工厂方法
    public static PayUtils getPayUtils(){
        return payUtils;
    }
    public int updateOrder(String out_trade_no, String total_fee, String trade_no, int type){
        //请在这里加上商户的业务逻辑程序代码
        TpOrder tpOrder = tpOrderService.findOrderByOrderSn(out_trade_no);
        if (null != tpOrder) {
            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            if (!(tpOrder.getTotal_amount() + "").equals(total_fee)) {
                System.out.println("订单金额不一致!!!");
                return 0;
            }
            TpUsers tpUsers = tpUsersService.findOneByUserId(tpOrder.getUser_id());
            if (null != tpUsers) {
                //更新订单状态
                TpOrder tpOrder1 = new TpOrder();
                tpOrder1.setOrder_id(tpOrder.getOrder_id());
                if (type == 1){
                    tpOrder1.setPay_code("alipayMobile");
                    tpOrder1.setPay_name("手机网站支付宝");
                }else if (type == 2){
                    tpOrder1.setPay_code("yop");
                    tpOrder1.setPay_name("银联在线支付");
                }
                tpOrder1.setPay_time((int) new Date().getTime());
                tpOrder1.setTransaction_id(trade_no);
                tpOrder1.setPay_status(1);
                int orderResult = tpOrderService.updateOrderByAlipay(tpOrder);
                if (orderResult > 0) {
                    //订单变化记录
                    TpOrderAction tpOrderAction = tpOrderService.getOrderAction(tpOrder, 1);
                    int actionResult1 = tpOrderService.insertOrderAction(tpOrderAction);
                    if (actionResult1 > 0) {
                        int goodsnum = 0;
                        //升级和返佣金
                        List<TpOrderGoods> tpOrderGoodsList = tpOrderService.findAllGoodsByOrderId(tpOrder.getOrder_id());
                        List<Integer> goodsIds = new ArrayList<Integer>();
                        for (TpOrderGoods tpOrderGoods : tpOrderGoodsList) {
                            if (tpOrderGoods.getGoods_id() == 144) {
                                //统计机器数量,用作判断上面三级是否升级
                                goodsnum += tpOrderGoods.getGoods_num();
                            }
                            if (!goodsIds.contains(tpOrderGoods.getGoods_id())) {
                                goodsIds.add(tpOrderGoods.getGoods_id());
                            }
                        }
                        Integer level = 0;
                        //存在机器时
                        if (goodsnum > 0) {
                            //当前用户升级
                            if (tpUsers.getLevel() == 0) {
                                level = 20;
                                int userResult = tpUsersService.updateUserLevel(tpOrder.getUser_id(), level);
                                if (userResult > 0) {
                                    //升级记录
                                    int uplogResult = insertUplogo(tpOrder, level);
                                    if (uplogResult <= 0) {
                                        return 0;
                                    }
                                }
                            }
                            //上级是否升级
                            if (null != tpUsers.getParent_id() && "".equals(tpUsers.getParent_id())) {
                                TpUsers tpUsers1 = tpUsersService.findOneByUserId(tpUsers.getParent_id());
                                if (null != tpUsers1) {
                                    List<TpUsers> listUsers = tpUsersService.findAllByUserParentId(tpUsers.getParent_id());
                                    int goodsNum = 0;
                                    for (TpUsers tpuser : listUsers) {
                                        List<TpOrder> orderList = tpOrderService.findAllOrderByUserId(tpuser.getUser_id());
                                        for (TpOrder tporder : orderList) {
                                            List<TpOrderGoods> ordergoodsList = tpOrderService.findAllGoodsByOrderId(tporder.getOrder_id());
                                            for (TpOrderGoods tpOrderGoods : ordergoodsList) {
                                                if (144 == tpOrderGoods.getOrder_id()) {
                                                    goodsNum += tpOrderGoods.getGoods_num();
                                                }
                                            }
                                        }
                                    }
                                    if ((goodsnum >= 45 && goodsnum < 150) && (listUsers.size() >= 15 && listUsers.size() < 50)) {
                                        //总监
                                        level = 30;
                                        int userResult1 = tpUsersService.updateUserLevel(tpUsers1.getUser_id(), level);
                                        if (userResult1 > 0) {
                                            //升级记录
                                            int uplogResult = insertUplogo(tpOrder, level);
                                            if (uplogResult <= 0) {
                                                return 0;
                                            }
                                        }
                                    } else if (goodsnum >= 150 && listUsers.size() >= 50) {
                                        //合伙人
                                        level = 40;
                                        int userResult1 = tpUsersService.updateUserLevel(tpUsers1.getUser_id(), level);
                                        if (userResult1 > 0) {
                                            int uplogResult = insertUplogo(tpOrder, level);
                                            if (uplogResult <= 0) {
                                                return 0;
                                            }
                                        }
                                    }
                                    //返佣金
                                    TpUsers tpUsers2 = tpUsersService.findOneByUserId(tpUsers1.getParent_id());
                                    if (null != tpUsers2) {
                                        //感恩金
                                        double frozenMoney = goodsnum * 20 + tpUsers2.getFrozen_money();
                                        int moneyResult = tpUsersService.updateUserFrozenMoney(tpUsers2.getUser_id(), frozenMoney);
                                        if (moneyResult > 0) {
                                            //金额变化记录
                                            TpAccountLog tpAccountLog = new TpAccountLog();
                                            tpAccountLog.setUser_id(tpUsers2.getUser_id());
                                            tpAccountLog.setChange_time(Integer.parseInt(Util.CreateDate()));
                                            tpAccountLog.setFrozen_money(frozenMoney);
                                            String desc = "您的好友 " + tpUsers.getMobile() + " 购买智媒体广告机（订单：" + tpOrder.getOrder_sn() + " ），您获得 20 元奖励";
                                            tpAccountLog.setDesc(desc);
                                            tpAccountLog.setOrder_id(tpOrder.getOrder_id());
                                            tpAccountLog.setOrder_sn(tpOrder.getOrder_sn());
                                            tpAccountLog.setUser_money(tpUsers2.getUser_money());
                                            tpAccountLog.setPay_points(0);
                                            tpAccountLog.setSecond_type(0);
                                            tpAccountLog.setThird_type(200);
                                            tpAccountLog.setChange_type(1);
                                            tpAccountLog.setStatus(0);
                                            tpAccountLog.setIs_delete(0);
                                            int accountlogResult = iTpAccountLogService.insertAccountLog(tpAccountLog);
                                            if (accountlogResult <= 0) {
                                                return 0;
                                            }
                                        }
                                        //同根
                                        if (null != tpUsers2.getRelation() && !"".equals(tpUsers2.getRelation())) {
                                            String[] users = tpUsers2.getRelation().split(",");
                                            double frozenMoney1 = goodsnum * 60 / users.length;
                                            for (String userId : users) {
                                                TpUsers tpUsers3 = tpUsersService.findOneByUserId(Integer.parseInt(userId));
                                                if (null != tpUsers3) {
                                                    int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers3.getUser_id(), frozenMoney1 + tpUsers3.getFrozen_money());
                                                    if (moneyResult3 > 0) {
                                                        int accountlogResult = insertAccountLog(tpUsers, tpUsers3, frozenMoney1, tpOrder);
                                                        if (accountlogResult <= 0) {
                                                            return 0;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //级差
                            if (null != tpUsers.getRelation() && !"".equals(tpUsers.getRelation())) {
                                String[] userIds = tpUsers.getRelation().split(",");
                                int levelNum = 0;
                                for (String userid : userIds) {
                                    TpUsers tpUsers4 = tpUsersService.findOneByUserId(Integer.parseInt(userid));
                                    switch (tpUsers4.getLevel()) {
                                        case 20:
                                            if (levelNum == 0) {
                                                //先遇到经理分钱 278
                                                int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 278 + tpUsers4.getFrozen_money());
                                                if (moneyResult3 > 0) {
                                                    int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 278, tpOrder);
                                                    if (accountlogResult <= 0) {
                                                        return 0;
                                                    }
                                                }
                                                levelNum = 20;
                                            } else if (levelNum == 20) {
                                                //经理已经分过钱
                                            }
                                            break;
                                        case 30:
                                            if (levelNum == 0) {
                                                //先遇到总监分钱 278+80
                                                int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 278 + 80 + tpUsers4.getFrozen_money());
                                                if (moneyResult3 > 0) {
                                                    int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 278 + 80, tpOrder);
                                                    if (accountlogResult <= 0) {
                                                        return 0;
                                                    }
                                                }
                                                levelNum = 30;
                                            } else if (levelNum == 20) {
                                                //已有经理分钱 80
                                                int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 80 + tpUsers4.getFrozen_money());
                                                if (moneyResult3 > 0) {
                                                    int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 80, tpOrder);
                                                    if (accountlogResult <= 0) {
                                                        return 0;
                                                    }
                                                }
                                                levelNum = 30;
                                            } else if (levelNum == 30) {
                                                //总监已分钱
                                            }
                                            break;
                                        case 40:
                                            if (levelNum == 0) {
                                                //先遇到合伙人分钱 278+80+110
                                                int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 278 + 80 + 110 + tpUsers4.getFrozen_money());
                                                if (moneyResult3 > 0) {
                                                    int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 278 + 80 + 110, tpOrder);
                                                    if (accountlogResult <= 0) {
                                                        return 0;
                                                    }
                                                }
                                                levelNum = 40;
                                            } else if (levelNum == 20) {
                                                //已有经理分钱, 没有总监分钱 80+110
                                                int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 80 + 110 + tpUsers4.getFrozen_money());
                                                if (moneyResult3 > 0) {
                                                    int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 80 + 110, tpOrder);
                                                    if (accountlogResult <= 0) {
                                                        return 0;
                                                    }
                                                }
                                                levelNum = 40;
                                            } else if (levelNum == 30) {
                                                //总监已分钱 110
                                                int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 110 + tpUsers4.getFrozen_money());
                                                if (moneyResult3 > 0) {
                                                    int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 110, tpOrder);
                                                    if (accountlogResult <= 0) {
                                                        return 0;
                                                    }
                                                }
                                                levelNum = 40;
                                            } else if (levelNum == 40) {
                                                //合伙人已分钱
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return 1;
    }
    public int insertUplogo(TpOrder tpOrder, int level){
        int result = 0;
        TpUserUpLog  tpUserUpLog = new TpUserUpLog();
        tpUserUpLog.setUser_id(tpOrder.getUser_id());
        tpUserUpLog.setLevel(level);
        tpUserUpLog.setAdd_time((int)new Date().getTime());
        int uplogResult = tpUsersService.insertUserUpLog(tpUserUpLog);
        return result;

    }
    public  int insertAccountLog(TpUsers tpUsers, TpUsers tpUsers3, double frozenMoney1, TpOrder tpOrder){
        //金额变化记录
        TpAccountLog tpAccountLog = new TpAccountLog();
        tpAccountLog.setUser_id(tpUsers3.getUser_id());
        tpAccountLog.setChange_time((int)new Date().getTime());
        tpAccountLog.setFrozen_money(frozenMoney1+tpUsers3.getFrozen_money());
        String desc = "您的好友 " + tpUsers.getMobile() + " 购买智媒体广告机（订单：" + tpOrder.getOrder_sn() + " ），您获得 "+frozenMoney1+" 元奖励";
        tpAccountLog.setDesc(desc);
        tpAccountLog.setOrder_id(tpOrder.getOrder_id());
        tpAccountLog.setOrder_sn(tpOrder.getOrder_sn());
        tpAccountLog.setUser_money(tpUsers3.getUser_money());
        tpAccountLog.setPay_points(0);
        tpAccountLog.setSecond_type(0);
        tpAccountLog.setThird_type(300);
        tpAccountLog.setChange_type(1);
        tpAccountLog.setStatus(0);
        tpAccountLog.setIs_delete(0);
        int accountlogResult = iTpAccountLogService.insertAccountLog(tpAccountLog);
        return accountlogResult;
    }
}
