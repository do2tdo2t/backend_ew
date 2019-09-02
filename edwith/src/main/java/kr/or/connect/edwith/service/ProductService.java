package kr.or.connect.edwith.service;

import java.util.List;

import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.Product;

public interface ProductService {
	public static final Integer ONE_PAGE_COUNT = 4;
	public int getCountById(Integer categoryId);
	public List<Product> getProducts(Integer categoryId, Integer limit);
	public DisplayInfo getDisplayInfoById(Integer displayInfoId);
}
