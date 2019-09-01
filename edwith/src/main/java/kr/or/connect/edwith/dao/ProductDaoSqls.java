package kr.or.connect.edwith.dao;

public class ProductDaoSqls {
	public static final String SELECT_ALL = "select id,category_id,description, content,event, create_date, modify_date from product where category_id : categoryId ORDER BY id DESC limit 0, :limit";
	public static final String COUNT_ALL = "select count(*) from product";
	public static final String COUNT_BY_CATEGORY_ID = "select count(*) from product where category_id = : categoryId";

}
