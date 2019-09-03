package kr.or.connect.edwith.dao.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.Logger;
import kr.or.connect.edwith.dao.DisplayInfoDao;
import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.DisplayInfoImage;

import static kr.or.connect.edwith.dao.sql.DisplayDaoSqls.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

@Repository
public class DisplayDaoImpl implements DisplayInfoDao {
	 private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	 private static String NAME = "DisplayDaoImpl";
 
	 private NamedParameterJdbcTemplate jdbc;
	 private RowMapper<DisplayInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	
	 public DisplayDaoImpl(DataSource dataSource) {
	        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	 
	@Override
	public DisplayInfo selectDisplayInfoById(Integer displayInfoId) {
		Map<String,?> params = Collections.singletonMap("displayInfoId",displayInfoId);
		
		try {
			return jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_ID,params,rowMapper);
		}catch(EmptyResultDataAccessException e) {
			logger.error("조회된 데이터가 없습니다...");
			return null;
		}catch(NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

}
