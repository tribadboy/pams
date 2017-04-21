package com.nju.pams.biz.service;

import java.math.BigDecimal;
import java.util.List;

import com.nju.pams.model.asset.FixedAsset;

public interface PamsFixedAssetService {
	FixedAsset getFixedAssetByAssetId(Integer assetId);
	
	List<FixedAsset> getFixedAssetListByUserId(Integer userId);
	
	List<FixedAsset> getFixedAssetListByUserIdInPeriodTime(Integer userId,String startDate,String endDate);
	
	void insertFixedAsset(FixedAsset fixedAsset);
	
	void updateFixedAsset(FixedAsset fixedAsset);
	
	void deleteFixedAssetByAssetId(Integer assetId);
	
	BigDecimal computeAllConsumptionValue(Integer userId);
}
