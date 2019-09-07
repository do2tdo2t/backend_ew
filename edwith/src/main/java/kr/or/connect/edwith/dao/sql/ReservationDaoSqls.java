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
	
	public static final String SELECT_RESERVATION_INFO_BY_EMAIL=
			"select " + 
			" id as reservation_info_id " + 
			" , product_id " + 
			" ,display_info_id " + 
			" ,reservation_name " + 
			" ,reservation_tel as reservation_telephone " + 
			" ,reservation_email " + 
			" ,cancel_flag as cancelYn " + 
			" ,reservation_date " + 
			" ,create_date " + 
			" ,modify_date " + 
			" ,total_price " + 
			" from reservation_info " + 
			" left join( " + 
			" select  " + 
			"  sum(price) as total_price " + 
			"  ,reservation_info_id " + 
			" from reservation_info_price " + 
			" left join product_price " + 
			" on reservation_info_price.product_price_id = product_price.id " + 
			" group by reservation_info_id " + 
			" ) as table1 " + 
			" on table1.reservation_info_id = reservation_info.id " + 
			" where reservation_email = :reservationInfoEmail";
	
	public static final String COUNT_TOTATL_PRICE_BY_ID=
			"select  " + 
			" sum(price) as totalPrice " + 
			"from reservation_info_price " + 
			"left join product_price " + 
			"on reservation_info_price.product_price_id = product_price.id " + 
			"where reservation_info_id = :reservationInfoId " + 
			"group by reservation_info_id";
	
	public static final String COUNT_RESERVATION_INFO_BY_EMAIL = 
			"select count(*) "
			+ "from reservation_info "
			+ "where reservation_email = :reservationInfoEmail";
	
	public static final String INSERT_RESERVATION_INFO = 
			"insert into reservation_info( " + 
			" display_info_id " + 
			" ,product_id " + 
			" ,reservation_email " + 
			" ,reservation_name " + 
			" ,reservation_tel " + 
			" ,reservation_date " + 
			" ,cancel_flag " + 
			" ,create_date " + 
			" , modify_date " + 
			") values( " + 
			":display_info_id"
			+ ",:product_id"
			+ ",:reservation_email"
			+ ",:reservation_name"
			+ ",:reservation_tel"
			+ ",:reservation_date"
			+ ",0"
			+ ",now()"
			+ ",now()" + 
			")";		
}
