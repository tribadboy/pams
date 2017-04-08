package com.nju.pams.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsFixedAssetService;
import com.nju.pams.mapper.dao.PamsFixedAssetDAO;
import com.nju.pams.model.asset.FixedAsset;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsFixedAssetServiceImpl implements PamsFixedAssetService {
	
	@Autowired
	PamsFixedAssetDAO pamsFixedAssetDAO;

	@Override
	public FixedAsset getFixedAssetByAssetId(Integer assetId) {
		return pamsFixedAssetDAO.getFixedAssetByAssetId(assetId);
	}

	@Override
	public List<FixedAsset> getFixedAssetListByUserId(Integer userId) {
		return pamsFixedAssetDAO.getFixedAssetListByUserId(userId);
	}
	
	@Override
	public List<FixedAsset> getFixedAssetListByUserIdInPeriodTime(Integer userId,
			String startDate, String endDate) {
		return pamsFixedAssetDAO.getFixedAssetListByUserIdInPeriodTime(userId, startDate, endDate);
	}
	
	@Override
	public void insertFixedAsset(FixedAsset fixedAsset) {
		pamsFixedAssetDAO.insertFixedAsset(fixedAsset);
	}

	@Override
	public void updateFixedAsset(FixedAsset fixedAsset) {
		pamsFixedAssetDAO.updateFixedAsset(fixedAsset);
	}

	@Override
	public void deleteFixedAssetByAssetId(Integer assetId) {
		pamsFixedAssetDAO.deleteFixedAssetByAssetId(assetId);
	}
	
}
