package kr.or.connect.edwith.dao.impl;

public class ProductDaoSqls {
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
	public static final String COUNT_ALL = "select count(*) from product";
	public static final String COUNT_BY_CATEGORY_ID = "select count(*) from product where category_id = :categoryId";

}
