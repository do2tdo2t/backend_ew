package kr.or.connect.edwith.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.Product;
import kr.or.connect.edwith.service.ProductService;

@RestController
@RequestMapping(path="/api/products")
public class ProductApiController {
	@Autowired
	ProductService productService;
	
	@GetMapping
	public Map<String,Object> list(@RequestParam(name="start",required=false, defaultValue="0")int start, 
				@RequestParam(name="categoryId", required=false, defaultValue="1") int categoryId){
		
		List<Product> list = productService.getProducts(categoryId,start);
		int totalCnt = productService.getCountById(categoryId);
		Map<String,Object> map= new HashMap<String,Object>();
	
		map.put("list", list);
		map.put("totalCnt", totalCnt);
		
		return map;
	}
	
	
	@GetMapping("/{displayInfoId}")
	public Map<String,Object> item(@RequestParam(name="displayInfoId",required=true)int displayInfoId){
		Map<String,Object> map= new HashMap<String,Object>();
		
		DisplayInfo displayInfo = productService.getDisplayInfoById(displayInfoId);
		
		map.put("displayInfo",displayInfo);
		
		return map;
		
	}
}
