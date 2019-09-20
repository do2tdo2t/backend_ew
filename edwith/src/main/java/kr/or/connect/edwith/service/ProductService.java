package kr.or.connect.edwith.service;

import java.util.List;

import kr.or.connect.edwith.dto.Product;
import kr.or.connect.edwith.dto.ProductImage;
import kr.or.connect.edwith.dto.ProductPrice;

public interface ProductService {
	public static final Integer ONE_PAGE_COUNT = 4;
	public int getCountById(Integer categoryId);
	public List<Product> getProducts(Integer categoryId, Integer limit);
	public List<ProductImage> getProductImages(Integer productId);
	public ProductImage getProductImage(Integer productId);
	public  List<ProductPrice> getProductPrices(Integer productId);
	public Float getAverageScore(Integer productId);
	public String getProductDescription(Integer productId);
}
