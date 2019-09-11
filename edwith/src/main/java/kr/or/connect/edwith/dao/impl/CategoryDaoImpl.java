package kr.or.connect.edwith.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dao.CategoryDao;
import kr.or.connect.edwith.dto.Category;

import static kr.or.connect.edwith.dao.sql.CategoryDaoSqls.*;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	private NamedParameterJdbcTemplate jdbc;
	@SuppressWarnings("rawtypes")
	private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Category.class);
	
	public CategoryDaoImpl(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> selectAllByCategoryID() {
		return jdbc.query(SELECT_ALL_GROUP_BY_CATEGORY_ID,rowMapper);
	}

}
