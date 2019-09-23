package kr.or.connect.edwith.dao.impl;

import static kr.or.connect.edwith.dao.sql.ReservationDaoSqls.INSERT_RESERVATION_INFO;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.FileInfoDao;
import kr.or.connect.edwith.dto.FileInfo;
import kr.or.connect.edwith.dto.ReservationInfo;

@Repository
public class FileInfoDaoImpl implements FileInfoDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<FileInfo> rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class);
	private SimpleJdbcInsert insertAction;
	
	public FileInfoDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("file_info")
				.usingGeneratedKeyColumns("id");
				
	}
	
	@Override
	public Integer insertOne(FileInfo fileInfo) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("file_name", fileInfo.getFileName());
		paramSource.addValue("save_file_name", fileInfo.getSaveFileName());
		paramSource.addValue("content_type", fileInfo.getContentType());
		paramSource.addValue("delete_flag",fileInfo.getDeleteFlag() );
		 // Number newId = insertActor.executeAndReturnKey(parameters);
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbc.update(INSERT_RESERVATION_INFO,paramSource,holder);
		
		
		logger.debug("insertReservationInfo holderKey: {}",holder.getKey());
		Integer id = insertAction.executeAndReturnKey(paramSource).intValue();
		return id;
	}
	
}
