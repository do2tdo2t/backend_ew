package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.Product;

public interface ProductDao {

	List<Product> selectAll(Integer categoryId,Integer start, Integer limit);

	Float getAverageScore(Integer productId);
	Integer getCountByCategoryId(Integer categoryId);
	String getProductDescription(Integer productId);

	String getProductDescriptionById(Integer productId);
	
}
