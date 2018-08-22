package com.citytuike.model;

import java.util.List;

public class TpReturn<T>{

	private String status;
	private String msg;
	private T result;
	private List<T> resultL;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public List<T> getResultL() {
		return resultL;
	}
	public void setResultL(List<T> resultL) {
		this.resultL = resultL;
	}
}
