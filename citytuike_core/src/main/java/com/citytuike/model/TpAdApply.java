package com.citytuike.model;

import java.io.Serializable;
import java.util.Date;

public class TpAdApply implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 申请订单唯一编号
     */
    private String order_sn;
    /**
     * 用户Id
     */
    private Integer user_id;
    /**
     * 类型（1屏幕广告|2二维码|3纸巾|4APP广告）
     */
    private Integer cate;
    /**
     * 行业（默认0）
     */
    private Integer trade_id;
    /**
     * 周期天数
     */
    private Integer days;
    /**
     * 链接地址
     */
    private String url;
    /**
     * 描述
     */
    private String describe;
    /**
     * 纸巾广告面数
     */
    private Integer side;
    /**
     * 纸巾广告总数量
     */
    private Integer launch_num;
    /**
     * 订单总价格
     */
    private double order_amount;
    /**
     * 状态（apply申请|pass通过审核|reject驳回|cancel取消|fail失效）
     */
    private String state;
    /**
     * 支付状态（0提交|1已支付|2失效）
     */
    private Integer pay_status;
    /**
     * 支付类型
     */
    private String pay_code;
    /**
     * 支付类型名称
     */
    private String pay_name;
    /**
     * 广告投放开始日期
     */
    private Date launch_at;
    /**
     * 支付时间
     */
    private Date paid_at;
    /**
     * 审核时间
     */
    private Date audited_at;
    /**
     * 创建时间
     */
    private Date created_at;
    /**
     * 更新时间
     */
    private Date updated_at;
    /**
     * 是否一元活动
     */
    private Integer activity;
    /**
     * 订单原价
     */
    private double ori_order_amount;
    /**
     * 0
     */
    private Integer before_days;
    /**
     * 二维码 时必须
     */
    private Integer fans_need_id;
    /**
     * APP投放位置：1启动页广告|2领纸广告|3关注公众号广告|4领纸成功广告|5领红包广告|6抽奖广告
     */
    private String launch_position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCate() {
        return cate;
    }

    public void setCate(Integer cate) {
        this.cate = cate;
    }

    public Integer getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(Integer trade_id) {
        this.trade_id = trade_id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }

    public Integer getLaunch_num() {
        return launch_num;
    }

    public void setLaunch_num(Integer launch_num) {
        this.launch_num = launch_num;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public double getOri_order_amount() {
        return ori_order_amount;
    }

    public void setOri_order_amount(double ori_order_amount) {
        this.ori_order_amount = ori_order_amount;
    }

    public Integer getBefore_days() {
        return before_days;
    }

    public void setBefore_days(Integer before_days) {
        this.before_days = before_days;
    }

    public Integer getFans_need_id() {
        return fans_need_id;
    }

    public void setFans_need_id(Integer fans_need_id) {
        this.fans_need_id = fans_need_id;
    }

    public String getLaunch_position() {
        return launch_position;
    }

    public void setLaunch_position(String launch_position) {
        this.launch_position = launch_position;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }

    public Date getLaunch_at() {
        return launch_at;
    }

    public void setLaunch_at(Date launch_at) {
        this.launch_at = launch_at;
    }

    public Date getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(Date paid_at) {
        this.paid_at = paid_at;
    }

    public Date getAudited_at() {
        return audited_at;
    }

    public void setAudited_at(Date audited_at) {
        this.audited_at = audited_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
