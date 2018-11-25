package com.glodon.water.common.model.result;

import com.glodon.water.common.common.constant.ApiServiceContanst;

public class BaseResult {
	private int status;
	private String errMsg;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public boolean isSuccess() {
		return status == ApiServiceContanst.ResultCode.RESULT_OK;
	}
}
