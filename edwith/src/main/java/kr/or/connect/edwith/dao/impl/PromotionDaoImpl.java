package kr.or.connect.edwith.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.PromotionDao;
import kr.or.connect.edwith.dto.Promotion;
import static kr.or.connect.edwith.dao.sql.PromotionDaoSqls.*;

@Repository
public class PromotionDaoImpl implements PromotionDao {
	private NamedParameterJdbcTemplate jdbc;
	@SuppressWarnings("rawtypes")
	private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);
	
	public PromotionDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	
	/*
	 * 프로모션(Promotion) 전체 가져오기
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Promotion> selectAll() {
		
		return jdbc.query(SELECT_PROMOTIONS, rowMapper);
	}

}
