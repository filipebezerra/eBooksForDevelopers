package io.github.filipebezerra.ebooksfordevelopers.rest.model;

import com.google.gson.annotations.*;

public class BaseResponse {

	@SerializedName("Error")
	protected String mError;

	@SerializedName("Time")
	protected Double mTime;
	
	public BaseResponse() {
	}

	public BaseResponse(String error, Double time) {
		mError = error;
		mTime = time;
	}

	public void setError(String error) {
		mError = error;
	}

	public String getError() {
		return mError;
	}

	public void setTime(Double time) {
		mTime = time;
	}

	public Double getTime() {
		return mTime;
	}
	
}
