package kr.or.connect.edwith.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.edwith.dto.Category;
import kr.or.connect.edwith.service.CategoryService;

@RestController
@RequestMapping(path="/api/categories")
public class CategoryApiController {
	@Autowired
	CategoryService categoryService;
	
	
	@GetMapping
	public Map<String,Object> list() {
		 Map<String,Object> map = new HashMap<String,Object>();
		 
		 List<Category> items = categoryService.getCategories();
		 
		 map.put("items", items);
		 
		 return map;
	}

}
