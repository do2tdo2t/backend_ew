package kr.or.connect.edwith.dao.impl;

import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.Logger;
import kr.or.connect.edwith.dao.DisplayInfoDao;
import kr.or.connect.edwith.dto.DisplayInfo;

import static kr.or.connect.edwith.dao.sql.DisplayDaoSqls.*;

import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

@Repository
public class DisplayDaoImpl implements DisplayInfoDao {
	 private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	 private NamedParameterJdbcTemplate jdbc;
	 private RowMapper<DisplayInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	
	 public DisplayDaoImpl(DataSource dataSource) {
	        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	 
	 /*
	  * 공연, 전시 상세 (DisplayInfo) 정보 아이디로 조회
	  * */
	@Override
	public DisplayInfo selectOneById(Integer displayInfoId) {
		Map<String,?> params = Collections.singletonMap("displayInfoId",displayInfoId);
		
		try {
			
			DisplayInfo displayInfo=jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_ID,params,rowMapper);
			
			//displayInfo.setOpeningHours(displayInfo.getOpeningHours().replaceAll("-", "<br> -"));
			
			return displayInfo;
		}catch(EmptyResultDataAccessException e) {
			logger.error("조회된 데이터가 없습니다...");
			return null;
		}catch(NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

}
