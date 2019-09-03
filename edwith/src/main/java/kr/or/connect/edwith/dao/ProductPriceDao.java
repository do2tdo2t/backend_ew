package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.ProductPrice;

public interface ProductPriceDao {
	public  List<ProductPrice> selectAllByProductId(Integer productId);

	public float getAverageScore(Integer productId);
}
