package kr.or.connect.edwith.dao.impl;

import static kr.or.connect.edwith.dao.sql.ProductDaoSqls.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.Logger;
import kr.or.connect.edwith.dao.ProductDao;
import kr.or.connect.edwith.dto.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
	 private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	 private NamedParameterJdbcTemplate jdbc;
	    private RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

	    
	public ProductDaoImpl(DataSource dataSource) {
		 this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	 /*
	  * 공연, 전시 목록 가져오기
	  * 목록 개수 : start * limit (start가 0일경우에는 limit만큼 가져옴)
	  * */
	@Override
	public List<Product> selectAll(Integer categoryId,Integer start, Integer limit) {
		Map<String, Integer> params = new HashMap<>();
		
		logger.debug("ProductDaoImpl.. selectAll().. ");
		params.put("start",start);
		params.put("limit",limit);
		params.put("categoryId",categoryId);
		
		if (categoryId == 0) {
			return  jdbc.query(SELECT_PRODUCT_ALL, params, rowMapper);
		}
		return jdbc.query(SELECT_PRODUCT_ALL_BY_CATEGORY, params, rowMapper);
		
	}

	 /*
	  * 카테고리별 공연, 전시 개수 가져오기
	  * */
	@Override
	public Integer getCountByCategoryId(Integer categoryId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId",categoryId);
		
		if(categoryId == 0) return jdbc.queryForObject(COUNT_ALL, params,Integer.class);
		else return jdbc.queryForObject(COUNT_BY_CATEGORY_ID, params,Integer.class);
	}
	
	 /*
	  * 공연,전시의 평균 평점 구하기
	  * */
	@Override
	public Float getAverageScore(Integer productId) {
		Map<String,?> params = Collections.singletonMap("productId", productId);
		
		//comment가 없으면 실행 하면 안되므로
		
		return jdbc.queryForObject(GET_AVERAGE_SCORE, params,Float.class);
	}

	 /*
	  * 공연, 전시의 상세 설명 구하기 (예약이 되어 있는 건에 한해서)
	  * */
	@Override
	public String getProductDescription(Integer productId) {
		Map<String,?> params = Collections.singletonMap("productId", productId);
		
		return jdbc.queryForObject(GET_PRODUCT_DESCRIPTION, params,String.class);
	}

	 /*
	  * 공연, 전시의 상세 설명 구하기
	  * */
	@Override
	public String getProductDescriptionById(Integer productId) {
		Map<String,?> params = Collections.singletonMap("productId", productId);
		// TODO Auto-generated method stub
		return jdbc.queryForObject(GET_PRODUCT_DESCRIPTION_BY_ID, params,String.class);
	}

}
