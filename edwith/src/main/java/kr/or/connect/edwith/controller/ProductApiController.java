package kr.or.connect.edwith.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.classic.Logger;
import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.DisplayInfoImage;
import kr.or.connect.edwith.dto.Product;
import kr.or.connect.edwith.dto.ProductImage;
import kr.or.connect.edwith.dto.ProductPrice;
import kr.or.connect.edwith.dto.ReservationUserComment;
import kr.or.connect.edwith.service.DisplayService;
import kr.or.connect.edwith.service.ProductService;
import kr.or.connect.edwith.service.ReservationService;

@RestController
@RequestMapping(path="/api/products")
public class ProductApiController {
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProductService productService;
	
	@Autowired
	DisplayService displayService;
	
	@Autowired
	ReservationService reservationService;
	
	@GetMapping
	public Map<String,Object> list(@RequestParam(name="start",required=false, defaultValue="0")int start, 
				@RequestParam(name="categoryId", required=false, defaultValue="0") int categoryId){
		logger.debug("PHJ : start={},categoryId={}",start,categoryId);
		List<Product> list = productService.getProducts(categoryId,start);
		int totalCnt = productService.getCountById(categoryId);
		Map<String,Object> map= new HashMap<String,Object>();
		
		logger.debug("PHJ : products size={}",list.size());
		
		map.put("products", list);
		map.put("totalCnt", totalCnt);
		
		return map;
	}
	
	@GetMapping("/{displayInfoId}")
	public ModelAndView item(@PathVariable(name="displayInfoId",required=true) int displayInfoId){
		
		ModelAndView mav = new ModelAndView();
		Map<String,Object> map= new HashMap<String,Object>();
		
		DisplayInfo displayInfo = displayService.getDisplayInfoById(displayInfoId);
		
		DisplayInfoImage displayInfoImage = displayService.getDisplayInfoImageById(displayInfoId);
		
		List<ProductImage> productImages = productService.getProductImages(displayInfo.getProductId());
		
		List<ProductPrice> productPrices = productService.getProductPrices(displayInfo.getProductId());
		
		List<ReservationUserComment> comments = reservationService.getComments(displayInfo.getProductId());
		
		float averageScore = productService.getAverageScore(displayInfo.getProductId());
		
		map.put("displayInfo",displayInfo);
		map.put("dispalyInfoImage",displayInfoImage);
		map.put("productImages", productImages);
		map.put("productPrices",productPrices);
		map.put("comments",comments);
		map.put("averageScore", averageScore);
		
		mav.addAllObjects(map);
		mav.setViewName("detail");
		
		
		return mav;
	}
	
}
