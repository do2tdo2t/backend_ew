package kr.or.connect.edwith.dao.sql;

public class ProductDaoSqls {

	public static final String SELECT_PRODUCT_ALL_BY_CATEGORY =
			"select" + 
			"	display_info_id," + 
			"	table1.product_id," + 
			"	product_description," + 
			"	place_name," + 
			"	product_content," + 
			"	product_image_url," + 
			"	category_id " + 
			"from" + 
			"	(select" + 
			"		product_id," + 
			"		save_file_name as product_image_url" + 
			"	from product_image" + 
			"	left join  file_info" + 
			"	on file_id = file_info.id" + 
			"	where product_image.type = \"th\") as table1 " + 
			"right join " + 
			"	(" + 
			"	select " + 
			"		product_id," + 
			"		description as product_description," + 
			"		content as product_content," + 
			"		display_info.id as display_info_id," + 
			"		place_name," + 
			"        category_id" + 
			"	from display_info" + 
			"	right join " + 
			"		( " + 
			"        select * " + 
			"        from product " + 
			"        where category_id = :categoryId" + 
			" ) as table3 " + 
			"	on table3.id = product_id ) as table2 " + 
			"on table1.product_id = table2.product_id " +
			"group by product_id " +
			"order by product_id DESC limit 0, :limit;" ;
	
	public static final String SELECT_PRODUCT_ALL =
			"select" + 
			"	display_info_id," + 
			"	table1.product_id," + 
			"	product_description," + 
			"	place_name," + 
			"	product_content," + 
			"	product_image_url," + 
			"	category_id " + 
			"from" + 
			"	(select" + 
			"		product_id," + 
			"		save_file_name as product_image_url" + 
			"	from product_image" + 
			"	left join  file_info" + 
			"	on file_id = file_info.id" + 
			"	where product_image.type = \"th\") as table1 " + 
			"right join " + 
			"	(" + 
			"	select " + 
			"		product_id," + 
			"		description as product_description," + 
			"		content as product_content," + 
			"		display_info.id as display_info_id," + 
			"		place_name," + 
			"        category_id" + 
			"	from display_info" + 
			"	right join " + 
			"		( " + 
			"        select * " + 
			"        from product ) as table3 " + 
			"	on table3.id = product_id ) as table2 " + 
			"on table1.product_id = table2.product_id " +
			"group by product_id " +
			"order by product_id DESC limit 0, :limit;" ;
		
	public static final String COUNT_ALL = "select count(*) from product";
	public static final String COUNT_BY_CATEGORY_ID = 
			"select count(*) from product where category_id = :categoryId";
	
	
	public static final String SELECT_PRODUCT_IMAGE_BY_ID =
			"select" + 
			" content_type" + 
			" ,create_date" + 
			" ,delete_flag" + 
			" ,file_info.id as file_info_id" + 
			" ,modify_date" + 
			" ,product_id" + 
			" ,product_image.id as product_image_id" + 
			" ,save_file_name" + 
			" ,type " + 
			"from product_image " + 
			"left join file_info " + 
			"on file_info.id = product_image.file_id " + 
			"where type='ma' " + 
			"and product_id = :productId";
	
	public static final String SELECT_PRODUCT_PRICE_ALL_BY_ID =
			"select" + 
			" create_date" + 
			" ,discount_rate" + 
			" ,modify_date" + 
			" ,price" + 
			" ,price_type_name" + 
			" ,product_id" + 
			" ,id as product_price_id" + 
			" from product_price" + 
			" where product_id = :productId;";
	
	public static final String GET_AVERAGE_SCORE =
			"select round(AVG(score),1) as averageScore " + 
			"from reservation_user_comment " + 
			"group by product_id having product_id = :productId";
}
