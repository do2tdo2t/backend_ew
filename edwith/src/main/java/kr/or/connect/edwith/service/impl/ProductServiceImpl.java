package kr.or.connect.edwith.service.impl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import kr.or.connect.edwith.dao.ProductDao;
import kr.or.connect.edwith.dao.ProductImageDao;
import kr.or.connect.edwith.dao.ProductPriceDao;
import kr.or.connect.edwith.dao.ReservationUserCommentDao;
import kr.or.connect.edwith.dto.Product;
import kr.or.connect.edwith.dto.ProductImage;
import kr.or.connect.edwith.dto.ProductPrice;
import kr.or.connect.edwith.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	private static final String CLASS_NAME = "ProductServiceImpl";
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductImageDao productImageDao;
	
	@Autowired
	ProductPriceDao productPriceDao;
	
	@Autowired
	ReservationUserCommentDao reservationUserCommentDao;
	
	@Override
	public List<Product> getProducts(Integer categoryId, Integer start) 
	{
		logger.debug("{}... getProducts()..",CLASS_NAME);
		int limit = (start == 0 ? ONE_PAGE_COUNT : start * ONE_PAGE_COUNT);
		List<Product> list = productDao.selectAll(categoryId,start,limit);
		
		return list;
	}

	@Override
	public int getCountById(Integer categoryId) {
		
		return productDao.getCountById(categoryId);
	}

	@Override
	public List<ProductImage> getProductImages(Integer productId) {
		
		return productImageDao.selectAllByProductId(productId);
	}

	@Override
	public List<ProductPrice> getProductPrices(Integer productId) {
		
		return productPriceDao.selectAllByProductId(productId);
	}

	@Override
	public Float getAverageScore(Integer productId) {
		int commentCnt = reservationUserCommentDao.getCountCommentsByProductId(productId);
		if(commentCnt == 0) return new Float(0.0);
		return productDao.getAverageScore(productId);
	}

	@Override
	public ProductImage getProductImage(Integer productId) {
		// TODO Auto-generated method stub
		return productImageDao.selectByProductId(productId);
	}

}
