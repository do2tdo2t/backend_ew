package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.ProductImage;

public interface ProductImageDao {
	
	public List<ProductImage> selectAllByProductId(Integer productId);

	public ProductImage selectOneByProductId(Integer productId);
	
}
