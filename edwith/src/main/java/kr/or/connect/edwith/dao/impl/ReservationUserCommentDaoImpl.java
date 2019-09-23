package kr.or.connect.edwith.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartResolver;

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
	
	@Bean
	public MultipartResolver multipartResolver() {
		org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10485760); // 1024 * 1024 * 10
		return multipartResolver;
	}
	
	@Override
	public List<ReservationUserComment> selectByProductId(Integer productId, Integer limit) {
		
		Map<String,Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("limit",limit);
		
		return jdbc.query(SELECT_COMMENTS_BY_ID, params, rowMapper );
	}

	@Override
	public int insertComment(ReservationUserComment comment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	@Override
	public List<ReservationUserComment> selectAllByProductId(Integer productId) {
		Map<String,Object> params = new HashMap<>();
		params.put("productId", productId);
		return jdbc.query(SELECT_COMMENTS_ALL_BY_ID, params, rowMapper );
	}

	@Override
	public Integer getCountCommentsByProductId(Integer productId) {
		Map<String,Object> params = new HashMap<>();
		params.put("productId", productId);
		
		return jdbc.queryForObject(COUNT_COMMENTS_BY_PRODUCT_ID, params, Integer.class );
	}

	@Override
	public Integer putComment(ReservationUserComment comment) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("product_id", comment.getProductId());
		paramSource.addValue("reservation_info_id", comment.getReservationInfoId());
		paramSource.addValue("score", comment.getScore());
		paramSource.addValue("comment",comment.getComment() );
		
		
		return insertAction.executeAndReturnKey(paramSource).intValue();
	}

}
