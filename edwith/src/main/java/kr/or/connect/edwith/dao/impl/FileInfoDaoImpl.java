package kr.or.connect.edwith.dao.impl;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.FileInfoDao;
import kr.or.connect.edwith.dto.FileInfo;

@Repository
public class FileInfoDaoImpl implements FileInfoDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//private NamedParameterJdbcTemplate jdbc;
	//private RowMapper<FileInfo> rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class);
	private SimpleJdbcInsert insertAction;
	
	public FileInfoDaoImpl(DataSource dataSource) {
		//this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("file_info")
				.usingGeneratedKeyColumns("id");
				
	}
	
	 /*
	  * 파일 정보 (FileInfo) 넣기
	  * */
	@Override
	public Integer insertOne(FileInfo fileInfo) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("file_name", fileInfo.getFileName());
		paramSource.addValue("save_file_name", fileInfo.getSaveFileName());
		paramSource.addValue("content_type", fileInfo.getContentType());
		paramSource.addValue("create_date", fileInfo.getCreateDate());
		paramSource.addValue("modify_date", fileInfo.getModifyDate());
		paramSource.addValue("delete_flag",0 );
		
		logger.debug("FileInfoDaoImpl... {}",fileInfo.toString());
		Integer id = insertAction.executeAndReturnKey(paramSource).intValue();
		logger.debug("FileInfoDaoImpl... result : {}",id);
		return id;
	}
	
}
