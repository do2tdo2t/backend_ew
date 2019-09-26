package kr.or.connect.edwith.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ReservationUserCommentDao;
import kr.or.connect.edwith.dto.ReservationUserComment;
import static kr.or.connect.edwith.dao.sql.ReservationDaoSqls.*;

@Repository
public class ReservationUserCommentDaoImpl implements ReservationUserCommentDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationUserComment> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserComment.class);
	private SimpleJdbcInsert insertAction;
	public ReservationUserCommentDaoImpl(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment")
                //id 자동 입력
                .usingGeneratedKeyColumns("id");
	}
	
	

	/*
	 * 공연,전시 아이디 기준 예약 리뷰 전체 가져오기
	 * */
	@Override
	public List<ReservationUserComment> selectAllByProductId(Integer productId) {
		Map<String,Object> params = new HashMap<>();
		params.put("productId", productId);
		return jdbc.query(SELECT_COMMENTS_ALL_BY_ID, params, rowMapper );
	}
	
	/*
	 * 공연,전시 아이디 기준 예약 리뷰 목록 가져오기
	 * - 목록 개수는 limit
	 * */
	@Override
	public List<ReservationUserComment> selectAllByProductId(Integer productId, Integer limit) {
		
		Map<String,Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("limit",limit);
		
		return jdbc.query(SELECT_COMMENTS_BY_ID, params, rowMapper );
	}

	/*
	 * 리뷰 넣기
	*/
	@Override
	public int insertComment(ReservationUserComment comment) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("product_id", comment.getProductId());
		paramSource.addValue("reservation_info_id", comment.getReservationInfoId());
		paramSource.addValue("score", comment.getScore());
		paramSource.addValue("comment",comment.getComment() );
		paramSource.addValue("create_date", comment.getCreateDate());
		paramSource.addValue("modify_date", comment.getModifyDate());
		
		return insertAction.executeAndReturnKey(paramSource).intValue();
	}
	
	
	/*
	 * 공연, 전시 아이디 기준 전체 리뷰 개수 가져오기
	 * */
	@Override
	public Integer getCountCommentsByProductId(Integer productId) {
		Map<String,Object> params = new HashMap<>();
		params.put("productId", productId);
		
		return jdbc.queryForObject(COUNT_COMMENTS_BY_PRODUCT_ID, params, Integer.class );
	}



}
