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
	
	 /*
	  * 공연,전시 이미지 전체 가져오기
	  * (이미지 타입이 'ma' 이거나 'et')
	  * */
	@Override
	public List<ProductImage> selectAllByProductId(Integer productId) {
		
		Map<String,?> params = Collections.singletonMap("productId", productId);
		
		return jdbc.query(SELECT_ALL_PRODUCT_IMAGE_BY_ID, params, rowMapper);
	}
	
	 /*
	  * 공연,전시 이미지  가져오기
	  * (이미지 타입이 'th')
	  * */
	@Override
	public ProductImage selectOneByProductId(Integer productId) {

		Map<String,?> params = Collections.singletonMap("productId", productId);
		
		return jdbc.queryForObject(SELECT_PRODUCT_IMAGE_BY_ID, params, rowMapper);
	}

}
