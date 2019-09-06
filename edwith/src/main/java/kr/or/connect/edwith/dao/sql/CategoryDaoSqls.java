package kr.or.connect.edwith.dao.sql;

public class CategoryDaoSqls {
	public static final String SELECT_ALL_GROUP_BY_CATEGORY_ID =
			"select  " + 
			" count(category_id) as count " + 
			" , category_id " + 
			" , category_name " + 
			"from display_info " + 
			"left join( " + 
			" select  " + 
			"  product.id as product_id " + 
			"  , category_id " + 
			"  , category.name as category_name " + 
			" from product " + 
			" left join category on category.id = category_id " + 
			" ) as table1 " + 
			"on display_info.product_id = table1.product_id " + 
			"group by category_id; ";
}
