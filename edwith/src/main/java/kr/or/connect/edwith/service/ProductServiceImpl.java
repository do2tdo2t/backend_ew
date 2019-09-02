package kr.or.connect.edwith.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import kr.or.connect.edwith.dao.DisplayInfoDao;
import kr.or.connect.edwith.dao.ProductDao;
import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.Product;

@Service
public class ProductServiceImpl implements ProductService {
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	private static final String CLASS_NAME = "ProductServiceImpl";
	@Autowired
	ProductDao productDao;
	
	/*
	@Autowired
	DisplayInfoDao displayInfoDao;
	*/
	
	public ProductServiceImpl() {
		
	}
	
	@Override
	public List<Product> getProducts(Integer categoryId, Integer start) 
	{
		logger.debug("{}... getProducts()..",CLASS_NAME);
		int limit = (start == 0 ? ONE_PAGE_COUNT : start * ONE_PAGE_COUNT);
		List<Product> list = productDao.selectAll(categoryId,limit);
		
		return list;
	}

	@Override
	public DisplayInfo getDisplayInfoById(Integer displayInfoId) {
		return null;
		//return displayInfoDao.selectDisplayInfoById(displayInfoId);
	}

	@Override
	public int getCountById(Integer categoryId) {
		
		return productDao.getCountById(categoryId);
	}


}
