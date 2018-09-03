package com.citytuike.model;

import java.io.Serializable;

public class TpPlateMsg implements Serializable{

	/**
	 * 消息板块记录
	 */
	private static final long serialVersionUID = 15898777487924007L;
	/**
	 * 用户id
	 */
	private Integer id;
	/**
	 *
	 */
	private String content;
	/**
	 *
	 */
	private String title;
	/**
	 *
	 */
	private int create_time;
	/**
	 *
	 */
	private String right_title;
	/**
	 *
	 */
	private Integer flag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCreate_time() {
		return create_time;
	}

	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}

	public String getRight_title() {
		return right_title;
	}

	public void setRight_title(String right_title) {
		this.right_title = right_title;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
