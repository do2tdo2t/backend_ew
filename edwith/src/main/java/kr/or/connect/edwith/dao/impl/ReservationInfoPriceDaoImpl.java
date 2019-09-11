package kr.or.connect.edwith.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ReservationInfoPriceDao;
import kr.or.connect.edwith.dto.ReservationInfoPrice;

@Repository
public class ReservationInfoPriceDaoImpl implements ReservationInfoPriceDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ReservationInfoPriceDaoImpl(DataSource dataSource) {
			this.jdbc = new NamedParameterJdbcTemplate(dataSource);
			insertAction =  new SimpleJdbcInsert(dataSource)
	        .withTableName("reservation_info_price")
	        //id 자동 입력
	        .usingGeneratedKeyColumns("id");
	}
	@Override
	public int[] insertPrices(List<ReservationInfoPrice> prices) {
		List<SqlParameterSource> params = new ArrayList<SqlParameterSource>();
		
		for(ReservationInfoPrice price : prices) {
			params.add(new BeanPropertySqlParameterSource(price));
		}
		
		logger.debug("PHJ: Params: " + prices.toString());
		return insertAction.executeBatch(params.toArray(new SqlParameterSource[0]));
	}

}
