package kr.or.connect.edwith.dao.impl;
import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.Logger;
import kr.or.connect.edwith.dao.DisplayInfoImageDao;
import kr.or.connect.edwith.dto.DisplayInfoImage;
import static kr.or.connect.edwith.dao.sql.DisplayDaoSqls.*;

@Repository
public class DisplayInfoImageDaoImpl implements DisplayInfoImageDao {
	 private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	 private NamedParameterJdbcTemplate jdbc;
	 private RowMapper<DisplayInfoImage> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoImage.class);
	
	 public DisplayInfoImageDaoImpl(DataSource dataSource) {
	        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	 /*
	  * 공연, 전시 상세 (DisplayInfo) 정보 아이디 이미지 조회
	  * */
	@Override
	public DisplayInfoImage selectOneByDisplayInfoId(Integer displayInfoId) {
		Map<String,?> params = Collections.singletonMap("displayInfoId",displayInfoId);
		
		try {
			return jdbc.queryForObject(SELECT_DISPLAY_INFO_IMAGE_BY_ID,params,rowMapper);
		}catch(EmptyResultDataAccessException e) {
			logger.error("조회된 데이터가 없습니다...");
			return null;
		}catch(NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

}
