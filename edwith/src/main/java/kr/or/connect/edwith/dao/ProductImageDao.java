package kr.or.connect.edwith.dao;

import kr.or.connect.edwith.dto.ProductImage;

public interface ProductImageDao {
	public ProductImage selectByProductId(Integer productId);
}
