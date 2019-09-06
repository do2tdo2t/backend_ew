package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.Category;

public interface CategoryDao {
	public List<Category> selectAllByCategoryID();
	
}
