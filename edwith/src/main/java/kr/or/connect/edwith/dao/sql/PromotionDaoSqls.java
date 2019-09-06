package kr.or.connect.edwith.dao.sql;

public class PromotionDaoSqls {
	public static final String SELECT_PROMOTIONS =
			"select  " + 
			" promotion.id " + 
			" , table1.product_id " + 
			" , product_image_url " + 
			"from promotion " + 
			"left join  " + 
			" ( " + 
			" select  " + 
			"  product_id " + 
			"  , save_file_name as product_image_url " + 
			" from product_image  " + 
			" left join file_info " + 
			" on file_info.id = product_image.file_id " + 
			" where type=\"th\" " + 
			" ) as table1 " + 
			"on promotion.product_id = table1.product_id; ";
}
