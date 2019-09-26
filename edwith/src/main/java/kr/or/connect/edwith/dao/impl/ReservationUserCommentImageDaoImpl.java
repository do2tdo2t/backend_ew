package kr.or.connect.edwith.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ReservationUserCommentImageDao;
import kr.or.connect.edwith.dto.ReservationUserCommentImage;

import static kr.or.connect.edwith.dao.sql.ReservationDaoSqls.*;

@Repository
public class ReservationUserCommentImageDaoImpl implements ReservationUserCommentImageDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationUserCommentImage> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserCommentImage.class);
	private SimpleJdbcInsert insertAction;
	 
	

	public ReservationUserCommentImageDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_user_comment_image")
				.usingGeneratedKeyColumns("id");
	}
	
	/*
	 * 리뷰 아이디로 리뷰에 등록된 이미지 가져오기
	 * */
	@Override
	public List<ReservationUserCommentImage> selectAllByCommentId(Integer commentId) {
		Map<String , ?> params = Collections.singletonMap("commentId", commentId);
		return jdbc.query(SELECT_COMMENT_IMAGES_BY_ID,params,rowMapper);
	}

	/*
	 * 리뷰에 등록된 사진 정보 넣기
	 * */
	@Override
	public Integer insertOne(ReservationUserCommentImage commentImage) {
	MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("file_id", commentImage.getFileId());
		paramSource.addValue("reservation_info_id", commentImage.getReservationInfoId());
		paramSource.addValue("reservation_user_comment_id", commentImage.getReservationUserCommentId());


		return insertAction.executeAndReturnKey(paramSource).intValue();
	}

}
