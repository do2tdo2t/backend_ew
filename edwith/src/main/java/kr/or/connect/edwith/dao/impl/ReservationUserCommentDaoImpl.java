package kr.or.connect.edwith.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.ReservationUserCommentDao;
import kr.or.connect.edwith.dto.ReservationUserComment;
import static kr.or.connect.edwith.dao.sql.ReservationDaoSqls.*;

@Repository
public class ReservationUserCommentDaoImpl implements ReservationUserCommentDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationUserComment> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserComment.class);
	
	public ReservationUserCommentDaoImpl(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public List<ReservationUserComment> selectAllByProductId(Integer productId, Integer limit) {
		
		Map<String,Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("limit",limit);
		
		return jdbc.query(SELECT_COMMENTS_BY_ID, params, rowMapper );
	}

}
