package kr.or.connect.edwith.dao.impl;

public class ProductDaoSqls {
	/*
	public static final String SELECT_ALL = 
			"select "
			+ "product.id as product_id, "
			+ "display_info.id as display_info_id , "
			+ "description as product_description, "
			+ "content as product_content, "
			+ "place_name, "
			+ "save_file_name as product_image_url "
			+ "from product, display_info, file_info, product_image "
			+ "where product.id = display_info.product_id "
			+ "and product_image.product_id = product.id "
			+ "and product_image.file_id = file_info.id "
			+ "ORDER BY product.id DESC limit 0, :limit";
	*/
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

		
	public static final String COUNT_ALL = "select count(*) from product";
	public static final String COUNT_BY_CATEGORY_ID = "select count(*) from product where category_id = :categoryId";

}
