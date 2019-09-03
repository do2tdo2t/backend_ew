package kr.or.connect.edwith.dao.sql;

public class ProductDaoSqls {

	public static final String SELECT_PRODUCT_ALL =
			"select " + 
			"	display_info_id, " + 
			"	table1.product_id, " + 
			"	product_description, " + 
			"	place_name, " + 
			"	product_content, " + 
			"	product_image_url " + 
			"from " + 
			"	(select " + 
			"		product_id, " + 
			"		save_file_name as product_image_url " + 
			"	from product_image " + 
			"	left join  file_info " + 
			"	on file_id = file_info.id " + 
			"	where product_image.type = \"th\") as table1 " + 
			"left join " + 
			"	( " + 
			"	select " + 
			"		product_id, " + 
			"		description as product_description, " + 
			"		content as product_content, " + 
			"		display_info.id as display_info_id, " + 
			"		place_name " + 
			"	from display_info " + 
			"	left join product " + 
			"	on product.id = product_id ) as table2 " + 
			"on table1.product_id = table2.product_id " + 
			"group by product_id " + 
			"order by product_id DESC limit 0, :limit; ";
	
	public static final String SELECT_PRODUCT_BY_CATEGORY_ID =
			"select " + 
			"	display_info_id, " + 
			"	table1.product_id, " + 
			"	product_description, " + 
			"	place_name, " + 
			"	product_content, " + 
			"	product_image_url " + 
			"from " + 
			"	(select " + 
			"		product_id, " + 
			"		save_file_name as product_image_url " + 
			"	from product_image " + 
			"	left join  file_info " + 
			"	on file_id = file_info.id " + 
			"	where product_image.type = \"th\") as table1 " + 
			"left join " + 
			"	( " + 
			"	select " + 
			"		product_id, " + 
			"		description as product_description, " + 
			"		content as product_content, " + 
			"		display_info.id as display_info_id, " + 
			"		place_name " + 
			"	from display_info " + 
			"	left join product " + 
			"	on product.id = product_id " + 
			"	where category_id = :categoryId ) as table2 " + 
			"on table1.product_id = table2.product_id " + 
			"group by product_id " + 
			"order by product_id DESC limit 0, :limit; ";
		
	public static final String COUNT_ALL = "select count(*) from product";
	public static final String COUNT_BY_CATEGORY_ID = "select count(*) from product where category_id = :categoryId";
	public static final String SELECT_PRODUCT_IMAGE_BY_ID =
			"\r\n" + 
			"select\r\n" + 
			" content_type\r\n" + 
			" ,create_date\r\n" + 
			" ,delete_flag\r\n" + 
			" ,file_info.id as file_info_id\r\n" + 
			" ,modify_date\r\n" + 
			" ,product_id\r\n" + 
			" ,product_image.id as product_image_id\r\n" + 
			" ,save_file_name\r\n" + 
			" ,type\r\n" + 
			"from product_image\r\n" + 
			"left join file_info\r\n" + 
			"on file_info.id = product_image.file_id\r\n" + 
			"where type=\"ma\"\r\n" + 
			"and product_id = :productId";
	
	public static final String SELECT_PRODUCT_PRICE_ALL_BY_ID =
			"select\r\n" + 
			" create_date\r\n" + 
			" ,discount_rate\r\n" + 
			" ,modify_date\r\n" + 
			" ,price\r\n" + 
			" ,price_type_name\r\n" + 
			" ,product_id\r\n" + 
			" ,id as product_price_id\r\n" + 
			"from product_price\r\n" + 
			"where product_id = :productId;";
	
	public static final String GET_AVERAGE_SCORE =
			"-- averageScore \r\n" + 
			"select round(AVG(score),1) as averageScore\r\n" + 
			"from reservation_user_comment\r\n" + 
			"group by product_id having product_id = :productId;";
}
