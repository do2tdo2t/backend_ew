package kr.or.connect.edwith.dao.sql;

public class ReservationDaoSqls {
	public static final String SELECT_COMMENTS_BY_ID =
			"select " + 
			" comment " + 
			" ,reservation_user_comment.id as comment_id " + 
			" ,reservation_user_comment.create_date " + 
			" ,reservation_user_comment.modify_date " + 
			" ,reservation_info.product_id " + 
			" ,reservation_date " + 
			" ,reservation_name " +
			" ,reservation_tel as reservation_telephone " +
			" ,reservation_email " + 
			" ,reservation_info.id as reservation_info_id " + 
			"from reservation_user_comment " + 
			"left join reservation_info " + 
			"on reservation_info_id = reservation_info.id " + 
			"where reservation_info.product_id=:productId " +
			"order by reservation_user_comment.create_date " + 
			"limit 0,:limit";
	
	public static final String SELECT_COMMENT_IMAGES_BY_ID =
			"select " + 
			" content_type " + 
			" ,create_date " + 
			" ,delete_flag " + 
			" ,file_id " + 
			" ,file_name " + 
			" ,reservation_user_comment_image.id as image_id " + 
			" ,modify_date " + 
			" ,reservation_info_id " + 
			" ,reservation_user_comment_id " + 
			" ,save_file_name " + 
			"from reservation_user_comment_image " + 
			"left join file_info " + 
			"on file_info.id = file_id " + 
			"where reservation_user_comment_id = :commentId;";
}
