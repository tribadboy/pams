package com.nju.pams.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsNoticeAndInformService;
import com.nju.pams.mapper.dao.PamsInformDAO;
import com.nju.pams.mapper.dao.PamsInformUserRefDAO;
import com.nju.pams.mapper.dao.PamsNoticeDAO;
import com.nju.pams.model.system.InformUserRef;
import com.nju.pams.model.system.PamsInform;
import com.nju.pams.model.system.PamsNotice;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsNoticeAndInformServiceImpl implements PamsNoticeAndInformService {
	
	@Autowired
	PamsNoticeDAO pamsNoticeDAO;
	
	@Autowired
	PamsInformDAO pamsInformDAO;
	
	@Autowired
	PamsInformUserRefDAO pamsInformUserRefDAO;

	/**
	 * 根据noticeId获取公告 
	 */
	@Override
	public PamsNotice getPamsNoticeByNoticeId(Integer noticeId) {
		return pamsNoticeDAO.getPamsNoticeByNoticeId(noticeId);
	}

	/**
	 * 获取最新的有效的一条公告
	 */
	@Override
	public PamsNotice getNewestValidPamsNotice() {
		return pamsNoticeDAO.getNewestValidPamsNotice();
	}

	/**
	 * 获取全部的公告
	 */
	@Override
	public List<PamsNotice> getAllPamsNotice() {
		List<PamsNotice> resultList = pamsNoticeDAO.getAllPamsNotice();
		if(null == resultList) {
			return new ArrayList<PamsNotice>();
		} else {
			return resultList;
		}
	}

	/**
	 * 插入一条的公告
	 */
	@Override
	public void insertPamsNotice(PamsNotice pamsNotice) {
		pamsNoticeDAO.insertPamsNotice(pamsNotice);
	}

	/**
	 * 更新一条公告
	 */
	@Override
	public void updatePamsNotice(PamsNotice pamsNotice) {
		pamsNoticeDAO.updatePamsNotice(pamsNotice);
	}

	/**
	 * 删除一条公告
	 */
	@Override
	public void deletePamsNoticeByNoticeId(Integer noticeId) {
		pamsNoticeDAO.deletePamsNoticeByNoticeId(noticeId);
	}
	
	/**
	 * 获取当前正在进行中的公告
	 */
	@Override
	public List<PamsNotice> getAllValidPamsNotice() {
		List<PamsNotice> resultList = pamsNoticeDAO.getAllValidPamsNotice();
		if(null == resultList) {
			return new ArrayList<PamsNotice>();
		} else {
			return resultList;
		}
		
	}
	
	
	//-------------------------------------------------------------------------------------

	/**
	 * 设置某个通知为无效状态
	 * @param informId
	 */
	@Override
	public void setInformInvalid(Integer informId) {
		PamsInform pamsInform = pamsInformDAO.getPamsInformByInformId(informId);
		if(null != pamsInform) {
			pamsInform.setStatus(PamsInform.Status.Invalid.toIntValue());
			pamsInformDAO.setPamsInformStatus(pamsInform);
		}
	}
	
	/**
	 * 设置某个通知为有效状态
	 * @param informId
	 */
	@Override
	public void setInformValid(Integer informId) {
		PamsInform pamsInform = pamsInformDAO.getPamsInformByInformId(informId);
		if(null != pamsInform) {
			pamsInform.setStatus(PamsInform.Status.Valid.toIntValue());
			pamsInformDAO.setPamsInformStatus(pamsInform);
		}
	}

	/**
	 * 设置某个通知为针对全体用户
	 * @param informId
	 */
	@Override
	public void setInformTypeAll(Integer informId) {
		pamsInformUserRefDAO.deleteInformUserRefByInformId(informId);
		PamsInform pamsInform = pamsInformDAO.getPamsInformByInformId(informId);
		if(null != pamsInform) {
			pamsInform.setInformTypeId(PamsInform.InformType.Total.toIntValue());
			pamsInformDAO.setPamsInformType(pamsInform);
		}
	}

	/**
	 * 设置某个通知为针对特定用户
	 * @param informId
	 */
	@Override
	public void setInformTypeSpecial(Integer informId) {
		PamsInform pamsInform = pamsInformDAO.getPamsInformByInformId(informId);
		if(null != pamsInform) {
			pamsInform.setInformTypeId(PamsInform.InformType.Special.toIntValue());
			pamsInformDAO.setPamsInformType(pamsInform);
		}
	}

	/**
	 * 获取所有通知
	 * @return
	 */
	@Override
	public List<PamsInform> getAllPamsInforms() {
		List<PamsInform> resultList = pamsInformDAO.getAllPamsInforms();
		if(null == resultList) {
			return new ArrayList<PamsInform>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取所有有效的通知
	 * @return
	 */
	@Override
	public List<PamsInform> getValidPamsInforms() {
		List<PamsInform> resultList = pamsInformDAO.getValidPamsInforms();
		if(null == resultList) {
			return new ArrayList<PamsInform>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取所有有效的针对特定用户的通知
	 * @return
	 */
	@Override
	public List<PamsInform> getValidTypeSpecialPamsInforms() {
		List<PamsInform> resultList = pamsInformDAO.getValidTypeSpecialPamsInforms();
		if(null == resultList) {
			return new ArrayList<PamsInform>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取所有有效的针对所有用户的通知
	 * @return
	 */
	@Override
	public List<PamsInform> getValidTypeTotalPamsInforms() {
		List<PamsInform> resultList = pamsInformDAO.getValidTypeTotalPamsInforms();
		if(null == resultList) {
			return new ArrayList<PamsInform>();
		} else {
			return resultList;
		}
	}	

	/**
	 * 获取所有针对全体用户的通知
	 */
	@Override
	public List<PamsInform> getAllTypeTotalPamsInforms() {
		List<PamsInform> resultList = pamsInformDAO.getAllTypeTotalPamsInforms();
		if(null == resultList) {
			return new ArrayList<PamsInform>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取所有针对特定用户的通知
	 */
	@Override
	public List<PamsInform> getAllTypeSpecialPamsInforms() {
		List<PamsInform> resultList = pamsInformDAO.getAllTypeSpecialPamsInforms();
		if(null == resultList) {
			return new ArrayList<PamsInform>();
		} else {
			return resultList;
		}
	}


	/**
	 * 插入某一条通知
	 * @param pamsInform
	 */
	@Override
	public void insertInform(PamsInform pamsInform) {
		pamsInformDAO.insertPamsInform(pamsInform);
	}

	/**
	 * 删除某一条通知
	 * @param informId
	 */
	@Override
	public void deletePamsInformByInformId(Integer informId) {
		pamsInformDAO.deletePamsInformByInformId(informId);
		pamsInformUserRefDAO.deleteInformUserRefByInformId(informId);
	}

	/**
	 * 插入某一条通知针对用户的关联,已经存在则不再插入
	 * @param informUserRef
	 */
	@Override
	public void insertInformUserRef(InformUserRef informUserRef) {
		int informId = informUserRef.getInformId();
		int userId = informUserRef.getUserId();
		PamsInform inform = pamsInformDAO.getPamsInformByInformId(informId);
		if(null != inform && inform.getStatus() == PamsInform.Status.Valid.toIntValue() 
				&& inform.getInformTypeId() == PamsInform.InformType.Special.toIntValue()) {
			InformUserRef ref = pamsInformUserRefDAO.getInformUserRefByInformIdAndUserId(informId, userId);
			if(null == ref) {
				pamsInformUserRefDAO.insertInformUserRef(informUserRef);
			}
		}
	}

	/**
	 * 删除某个通知与用户的关联
	 * @param refId
	 */
	@Override
	public void deleteInformUserRefByRefId(Integer refId) {
		pamsInformUserRefDAO.deleteInformUserRefByRefId(refId);
	}

	/**
	 * 获取对某个用户有效的所有通知
	 * @param userId
	 * @return
	 */
	@Override
	public List<PamsInform> getAllValidInformForCertainUser(Integer userId) {
		List<PamsInform> resultList = new ArrayList<PamsInform>();
		resultList.addAll(getValidTypeTotalPamsInforms());
		List<Integer> allSpecialInformIdForUser = pamsInformUserRefDAO.getAllSpecialInformIdByUserId(userId);
		if(CollectionUtils.isNotEmpty(allSpecialInformIdForUser)) {
			for(Integer informId : allSpecialInformIdForUser) {
				PamsInform inform = pamsInformDAO.getPamsInformByInformId(informId);
				if(null != inform && inform.getStatus() == PamsInform.Status.Valid.toIntValue()) {
					resultList.add(inform);
				}
			}
		}
		return resultList;
	}

	/**
	 * 查询用户是否与某个通知有关联
	 */
	@Override
	public InformUserRef getInformUserRefByInformIdAndUserId(Integer informId, Integer userId) {
		return pamsInformUserRefDAO.getInformUserRefByInformIdAndUserId(informId, userId);
	}


}
