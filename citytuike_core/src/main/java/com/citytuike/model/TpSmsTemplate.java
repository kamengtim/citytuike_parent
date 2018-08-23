package com.citytuike.model;

import java.io.Serializable;

public class TpSmsTemplate implements Serializable{

	/**
	 * 验证码记录表
	 */
	private static final long serialVersionUID = -5451266569853979384L;
	/**
	 * id
	 */
	private int tpl_id;
	/**
	 * 短信签名
	 */
	private String sms_sign;
	/**
	 * 短信模板ID
	 */
	private String sms_tpl_code;
	/**
	 * 发送短信内容
	 */
	private String tpl_content;
	/**
	 * 短信发送场景
	 */
	private String send_scene;
	/**
	 * 添加时间
	 */
	private int add_time;

    public int getTpl_id() {
        return tpl_id;
    }

    public void setTpl_id(int tpl_id) {
        this.tpl_id = tpl_id;
    }

    public String getSms_sign() {
        return sms_sign;
    }

    public void setSms_sign(String sms_sign) {
        this.sms_sign = sms_sign;
    }

    public String getSms_tpl_code() {
        return sms_tpl_code;
    }

    public void setSms_tpl_code(String sms_tpl_code) {
        this.sms_tpl_code = sms_tpl_code;
    }

    public String getTpl_content() {
        return tpl_content;
    }

    public void setTpl_content(String tpl_content) {
        this.tpl_content = tpl_content;
    }

    public String getSend_scene() {
        return send_scene;
    }

    public void setSend_scene(String send_scene) {
        this.send_scene = send_scene;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }
}
