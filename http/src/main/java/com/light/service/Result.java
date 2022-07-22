package com.light.service;

public class Result<T> {
	protected boolean ret = false;
	
	protected String errMsg;

	protected T info;
	
	public Result()
	{
		this.ret = false;
	}
	
	public Result(boolean ret, T info)
	{
		this.ret = ret;
		this.info = info;
	}
	
	public Result(boolean ret, String errMsg)
	{
		this.ret = ret;
		this.errMsg = errMsg;
	}
	
	public Result(boolean ret, String errMsg, T info)
	{
		this.ret = ret;
		this.errMsg = errMsg;
		this.info = info;
	}
	
	public boolean isRet() {
		return ret;
	}
	public void setRet(boolean ret) {
		this.ret = ret;
	}
	public T getInfo() {
		return info;
	}
	public void setInfo(T info) {
		this.info = info;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
