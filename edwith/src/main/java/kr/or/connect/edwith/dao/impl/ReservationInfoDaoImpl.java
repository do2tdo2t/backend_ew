package kr.or.connect.edwith.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ReservationInfoDao;
import kr.or.connect.edwith.dto.ReservationInfo;

import static kr.or.connect.edwith.dao.sql.ReservationDaoSqls.*;

@Repository
public class ReservationInfoDaoImpl implements ReservationInfoDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationInfo> rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);

	public ReservationInfoDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        
	}
	
	 /*
	  * 이메일 기준 모든 예약 내역 가져오기
	  * */
	@Override
	public List<ReservationInfo> selectAllByEmail(String reservationInfoEmail) {
		Map<String,?> params = Collections.singletonMap("reservationInfoEmail", reservationInfoEmail);
		return jdbc.query(SELECT_RESERVATION_INFO_BY_EMAIL, params,rowMapper);
	}

	 /*
	  * 예약 금액 가져오기
	  * */
	@Override
	public int getTotalPriceById(Integer reservationInfoId) {
		Map<String,?> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
		return jdbc.queryForObject(COUNT_TOTATL_PRICE_BY_ID, params,Integer.class);
	}

	 /*
	  * 이메일 기준 전체 예약 개수 가져오기
	  * */
	@Override
	public int countByEmail(String reservationInfoEmail) {
		Map<String,?> params = Collections.singletonMap("reservationInfoEmail", reservationInfoEmail);
		return jdbc.queryForObject(COUNT_RESERVATION_INFO_BY_EMAIL,params,Integer.class);
	}


	 /*
	  * 예약하기
	  * - 반환값 : id (reservation_info 테이블의 id )
	  * */
	@Override
	public int insertReservationInfo(ReservationInfo reservationInfo) {
		//SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfo);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("display_info_id", reservationInfo.getDisplayInfoId());
		paramSource.addValue("product_id", reservationInfo.getProductId());
		paramSource.addValue("reservation_email", reservationInfo.getReservationEmail());
		paramSource.addValue("reservation_name", reservationInfo.getReservationName());
		paramSource.addValue("reservation_tel", reservationInfo.getReservationTelephone());
		paramSource.addValue("reservation_date", reservationInfo.getReservationYearMonthDay());
		
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbc.update(INSERT_RESERVATION_INFO,paramSource,holder);
		
		
		logger.debug("insertReservationInfo holderKey: {}",holder.getKey());
		
		return holder.getKey().intValue();
	}
	
	
	 /*
	  * 이메일 기준 예약 여부 확인하기
	  * - 반환값 : 해당 이메일로 조회된 예약건이 있으면 true, 없으면 false
	  * */
	@SuppressWarnings("unused")
	@Override
	public Boolean selectReservationInfoByEmail(ReservationInfo reservationInfo) {
		Map<String,?> params = Collections.singletonMap("reservationInfoEmail", reservationInfo.getReservationEmail());
		
		int i = jdbc.queryForObject(COUNT_RESERVATION_INFO_BY_EMAIL, params, Integer.class);
		if(i < 1) return false;
		
		ReservationInfo mReservationInfo = jdbc.queryForObject(SELECT_RESERVATION_INFO_BY_EMAIL_FOR_CHECKING,params, rowMapper);
	
		reservationInfo.setReservationName(mReservationInfo.getReservationName());
		reservationInfo.setReservationTelephone(mReservationInfo.getReservationTelephone());
		
		logger.debug("PHJ... login.. reservationInfo : {}",reservationInfo.toString());
		
		if(reservationInfo == null) 
			return false;
		else return true;
	
	}

	 /*
	  * 예약 취소하기 
	  * - 예약 취소는 cancelYn 행의 값을 true로 변경한다.
	  * */
	@Override
	public int deleteReservationById(int reservationId) {
		Map<String,?> params = Collections.singletonMap("reservationInfoId", reservationId);
		
		return jdbc.update(UPDATE_CANCLE_YN_BY_ID, params);
		
	}

}
