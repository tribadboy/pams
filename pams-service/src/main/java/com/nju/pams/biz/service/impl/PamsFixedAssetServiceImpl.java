package com.nju.pams.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.model.vo.FixedAssetOverallVO;
import com.nju.pams.biz.service.PamsFixedAssetService;
import com.nju.pams.mapper.dao.PamsFixedAssetDAO;
import com.nju.pams.model.asset.FixedAsset;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.EmptyUtil;

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
		List<FixedAsset> resultList = pamsFixedAssetDAO.getFixedAssetListByUserId(userId);
		if(null == resultList) {
			return new ArrayList<FixedAsset>();
		} else {
			return resultList;
		}
	}
	
	@Override
	public List<FixedAsset> getFixedAssetListByUserIdInPeriodTime(Integer userId,
			String startDate, String endDate) {
		List<FixedAsset> resultList = pamsFixedAssetDAO.getFixedAssetListByUserIdInPeriodTime(userId, startDate, endDate);
		if(null == resultList) {
			return new ArrayList<FixedAsset>();
		} else {
			return resultList;
		}
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

	@Override
	public BigDecimal computeAllConsumptionValue(Integer userId) {
		BigDecimal result = BigDecimal.ZERO;
		List<FixedAsset> resultList = pamsFixedAssetDAO.getFixedAssetListByUserId(userId);
		if(CollectionUtils.isNotEmpty(resultList)) {
			for(FixedAsset asset : resultList) {
				result = result.add(asset.getRecordValue());
			}
		}
		return BigDecimalUtil.generateFormatNumber(result);
	}

	@Override
	public FixedAssetOverallVO getFixedAssetOverall(Integer userId) {
		BigDecimal result = BigDecimal.ZERO;
		int count = 0;
		List<FixedAsset> resultList = pamsFixedAssetDAO.getFixedAssetListByUserId(userId);
		if(CollectionUtils.isNotEmpty(resultList)) {
			for(FixedAsset asset : resultList) {
				result = result.add(asset.getRecordValue());
			}
			count = resultList.size();
		}
		String minDate = EmptyUtil.notEmtpyProcess(pamsFixedAssetDAO.getMinDateByUserId(userId));
		String maxDate = EmptyUtil.notEmtpyProcess(pamsFixedAssetDAO.getMaxDateByUserId(userId));
		return new FixedAssetOverallVO(BigDecimalUtil.generateFormatNumber(result), count, minDate, maxDate);
	}
	
}
