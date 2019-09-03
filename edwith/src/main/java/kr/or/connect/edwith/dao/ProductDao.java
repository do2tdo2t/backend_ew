package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.Product;

public interface ProductDao {

	List<Product> selectAll(Integer categoryId, Integer limit);

	Integer getCountById(Integer categoryId);
}