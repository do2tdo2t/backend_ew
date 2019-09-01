package kr.or.connect.edwith.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kr.or.connect.edwith.dao.DisplayInfoDao;
import kr.or.connect.edwith.dao.ProductDao;
import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.Product;

public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;
	
	@Autowired
	DisplayInfoDao displayInfoDao;
	
	
	@Override
	public List<Product> getProducts(Integer categoryId, Integer start) 
	{
		int limit = (start == 0 ? start : start * ONE_PAGE_COUNT);
		List<Product> list = productDao.selectAll(categoryId,limit);
		
		return list;
	}

	@Override
	public int getCount() {
		int totalCnt = productDao.getCount();
		return totalCnt;
	}

	@Override
	public DisplayInfo getDisplayInfoById(Integer displayInfoId) {
		return displayInfoDao.selectDisplayInfoById(displayInfoId);
	}

	@Override
	public int getCountById(Integer categoryId) {
		
		return productDao.getCountById(categoryId);
	}


	@Override
	public DisplayInfo getDisplayInfoById(int displayInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

}
