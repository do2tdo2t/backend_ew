package kr.or.connect.edwith.dao.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ProductImageDao;
import kr.or.connect.edwith.dto.ProductImage;
import static kr.or.connect.edwith.dao.sql.ProductDaoSqls.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ProductImageDaoImpl implements ProductImageDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductImage> rowMapper= BeanPropertyRowMapper.newInstance(ProductImage.class);
	
	
	public ProductImageDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		
	}
	@Override
	public List<ProductImage> selectByProductId(Integer productId) {
		
		Map<String,?> params = Collections.singletonMap("productId", productId);
		
		return jdbc.query(SELECT_PRODUCT_IMAGE_BY_ID, params, rowMapper);
	}

}
