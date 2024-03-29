package kr.or.connect.edwith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.edwith.dao.PromotionDao;
import kr.or.connect.edwith.dto.Promotion;
import kr.or.connect.edwith.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	PromotionDao promotionDao;
	
	/*
	 * 프로모션 목록 전체 가져오기
	 * */
	@Override
	public List<Promotion> getPromotions() {
		
		return promotionDao.selectAll();
	}

}
