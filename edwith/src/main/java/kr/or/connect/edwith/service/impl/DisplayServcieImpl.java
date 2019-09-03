package kr.or.connect.edwith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.edwith.dao.DisplayInfoDao;
import kr.or.connect.edwith.dao.DisplayInfoImageDao;
import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.DisplayInfoImage;
import kr.or.connect.edwith.service.DisplayService;

@Service
public class DisplayServcieImpl implements DisplayService {
	
	@Autowired
	DisplayInfoDao displayInfoDao;
	
	@Autowired
	DisplayInfoImageDao displayInfoImageDao;
	
	@Override
	public DisplayInfo getDisplayInfoById(Integer displayInfoId) {
		
		return displayInfoDao.selectDisplayInfoById(displayInfoId);
		
	}

	@Override
	public DisplayInfoImage getDisplayInfoImageById(Integer displayInfoId) {
		// TODO Auto-generated method stub
		return displayInfoImageDao.selectByDisplayInfoId(displayInfoId);
	}

}
