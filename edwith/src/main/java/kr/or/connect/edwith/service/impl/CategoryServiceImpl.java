package kr.or.connect.edwith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.edwith.dao.CategoryDao;
import kr.or.connect.edwith.dto.Category;
import kr.or.connect.edwith.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao categoryDao;
	
	
	@Override
	public List<Category> getCategories() {
		
		return categoryDao.selectAllByCategoryID();
	}

}
