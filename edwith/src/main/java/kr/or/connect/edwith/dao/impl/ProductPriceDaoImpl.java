package kr.or.connect.edwith.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ProductPriceDao;
import kr.or.connect.edwith.dto.ProductPrice;
import static kr.or.connect.edwith.dao.sql.ProductDaoSqls.*;

@Repository
public class ProductPriceDaoImpl implements ProductPriceDao{

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
	
	public ProductPriceDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	 /*
	  * 공연, 전시의 가격 가져오기
	  * 
	  * prictType에 따라  A는 어른, B는 유아, Y는 청소년
	  * */
	@Override
	public  List<ProductPrice> selectAllByProductId(Integer productId) {
		
		Map<String,?> params = Collections.singletonMap("productId", productId);
		return jdbc.query(SELECT_PRODUCT_PRICE_ALL_BY_ID, params,rowMapper);
		
	}


}
