package kr.or.connect.edwith.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.edwith.dto.Promotion;
import kr.or.connect.edwith.service.PromotionService;

@RestController
@RequestMapping(path="/api/promotions")
public class PromotionApiController {
	
	@Autowired
	PromotionService promotionService;
	
	/*
	 * 프로모션 정보 목록 가져오기
	 * */
	@GetMapping
	public Map<String,Object> getPromotions(){
		Map<String,Object> map = new HashMap<>();
		
		List<Promotion> items = promotionService.getPromotions();
		map.put("items", items);
		
		return map;
	}
}
