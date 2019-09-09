package kr.or.connect.edwith.dao.impl;

import static kr.or.connect.edwith.dao.sql.ProductDaoSqls.*;

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
	
	@Override
	public List<Product> selectAll(Integer categoryId,Integer start, Integer limit) {
		Map<String, Integer> params = new HashMap<>();
		
		logger.debug("ProductDaoImpl.. selectAll().. ");
		params.put("start",start);
		params.put("limit",limit);
		params.put("categoryId",categoryId);
		return jdbc.query(SELECT_PRODUCT_ALL, params, rowMapper);
		
	}


	@Override
	public Integer getCountById(Integer categoryId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId",categoryId);
		
		return jdbc.queryForObject(COUNT_BY_CATEGORY_ID, params,Integer.class);
	}

}
