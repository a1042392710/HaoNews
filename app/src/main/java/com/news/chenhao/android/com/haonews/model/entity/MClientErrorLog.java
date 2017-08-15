package com.news.chenhao.android.com.haonews.model.entity;

import java.io.Serializable;

public class MClientErrorLog implements Serializable {
	// Mcel_type , Mcel_No . Mcel_ver , Mcel_band , Mcel_model ,
	// Mcel_AndroidVer, Mcvl_Memo
	// 版本类型，版本号，版本名，手机品牌，手机型号，Android版本，错误信息
	private Integer mcelId;

	private String mcelType;

	private String mcelNo;

	private String mcelVer;

	private String mcelBand;

	private String mcelModel;

	private String mcelAndroidver;

	private String mcvlMemo;

	private static final Long serialVersionUID = 1L;

	public Object getPrimaryKeyValue() {
		return mcelId;
	}

	public Integer getMcelId() {
		return mcelId;
	}

	public void setMcelId(Integer mcelId) {
		this.mcelId = mcelId;
	}

	public String getMcelType() {
		return mcelType;
	}

	public void setMcelType(String mcelType) {
		this.mcelType = mcelType == null ? null : mcelType.trim();
	}

	public String getMcelNo() {
		return mcelNo;
	}

	public void setMcelNo(String mcelNo) {
		this.mcelNo = mcelNo == null ? null : mcelNo.trim();
	}

	public String getMcelVer() {
		return mcelVer;
	}

	public void setMcelVer(String mcelVer) {
		this.mcelVer = mcelVer == null ? null : mcelVer.trim();
	}

	public String getMcelBand() {
		return mcelBand;
	}

	public void setMcelBand(String mcelBand) {
		this.mcelBand = mcelBand == null ? null : mcelBand.trim();
	}

	public String getMcelModel() {
		return mcelModel;
	}

	public void setMcelModel(String mcelModel) {
		this.mcelModel = mcelModel == null ? null : mcelModel.trim();
	}

	public String getMcelAndroidver() {
		return mcelAndroidver;
	}

	public void setMcelAndroidver(String mcelAndroidver) {
		this.mcelAndroidver = mcelAndroidver == null ? null : mcelAndroidver
				.trim();
	}

	public String getMcvlMemo() {
		return mcvlMemo;
	}

	public void setMcvlMemo(String mcvlMemo) {
		this.mcvlMemo = mcvlMemo == null ? null : mcvlMemo.trim();
	}
}