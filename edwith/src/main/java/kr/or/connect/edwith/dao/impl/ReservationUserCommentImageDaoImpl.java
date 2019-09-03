package kr.or.connect.edwith.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ReservationUserCommentImageDao;
import kr.or.connect.edwith.dto.ReservationUserCommentImage;

import static kr.or.connect.edwith.dao.sql.ReservationDaoSqls.*;

@Repository
public class ReservationUserCommentImageDaoImpl implements ReservationUserCommentImageDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationUserCommentImage> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserCommentImage.class);
	
	public ReservationUserCommentImageDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);

	}
	
	@Override
	public List<ReservationUserCommentImage> selectAllByCommentId(Integer commentId) {
		Map<String , ?> params = Collections.singletonMap("commentId", commentId);
		return jdbc.query(SELECT_COMMENT_IMAGES_BY_ID,params,rowMapper);
	}

}
