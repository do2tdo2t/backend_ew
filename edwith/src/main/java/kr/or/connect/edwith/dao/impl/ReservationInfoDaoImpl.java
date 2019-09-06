package kr.or.connect.edwith.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ReservationInfoDao;
import kr.or.connect.edwith.dto.ReservationInfo;

import static kr.or.connect.edwith.dao.sql.ReservationDaoSqls.*;

@Repository
public class ReservationInfoDaoImpl implements ReservationInfoDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationInfo> rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
	
	public ReservationInfoDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public List<ReservationInfo> selectAllByEmail(String reservationInfoEmail) {
		Map<String,?> params = Collections.singletonMap("reservationInfoEmail", reservationInfoEmail);
		return jdbc.query(SELECT_RESERVATION_INFO_BY_EMAIL, params,rowMapper);
	}

	@Override
	public int getTotalPriceById(Integer reservationInfoId) {
		Map<String,?> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
		return jdbc.queryForObject(COUNT_TOTATL_PRICE_BY_ID, params,Integer.class);
	}

	@Override
	public int countByEmail(String reservationInfoEmail) {
		Map<String,?> params = Collections.singletonMap("reservationInfoEmail", reservationInfoEmail);
		return jdbc.queryForObject(COUNT_RESERVATION_INFO_BY_EMAIL,params,Integer.class);
	}

}
