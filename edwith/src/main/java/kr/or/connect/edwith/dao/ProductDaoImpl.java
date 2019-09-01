package kr.or.connect.edwith.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kr.or.connect.edwith.dto.Product;
import static kr.or.connect.edwith.dao.ProductDaoSqls.*;

public class ProductDaoImpl implements ProductDao {
	 private NamedParameterJdbcTemplate jdbc;
	    private RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

	    
	public ProductDaoImpl(DataSource dataSource) {
		 this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public List<Product> selectAll(Integer categoryId, Integer limit) {
		Map<String, Integer> params = new HashMap<>();
		
		params.put("limit",limit);
		params.put("categoryId",categoryId);
		
		return jdbc.query(SELECT_ALL, params, rowMapper);
	}

	@Override
	public int getCount() {
		
		return 0;
	}

	@Override
	public int getCountById(Integer categoryId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId",categoryId);
		
		return  jdbc.query(COUNT_BY_CATEGORY_ID, params);
	}

}
