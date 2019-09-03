package kr.or.connect.edwith.dao.impl;

public class DisplayDaoImplSqls {

	public static final String SELECT_DISPLAY_INFO_BY_ID = "select  " + "product_id " + ", category_id "
			+ ", display_info_id " + ", name as category_name " + ", product_description " + ", product_content "
			+ ", product_event " + ", opening_hours " + ", product_event " + ", place_name " + ", place_lot "
			+ ", place_street " + ", telephone " + ", homepage " + ", email " + ", create_date " + ", modify_date "
			+ "from " + "( " + "select  " + "display_info.id as display_info_id " + ",product_id " + ",place_name "
			+ ",place_lot " + ",place_street " + ",tel as telephone " + ",homepage " + ",email " + ",opening_hours "
			+ ",display_info.create_date " + ",display_info.modify_date " + ",description as product_description "
			+ ",category_id " + ",content as product_content " + ", event as product_event " + "from display_info "
			+ "left join product " + "on product.id = display_info.product_id "
			+ "where display_info.id = :displayInfoId) as table1 " + "left join category "
			+ "on category.id = category_id; ";

	public static final String SELECT_DISPLAY_INFO_IMAGE_BY_ID = "select" + " content_type" + " ,create_date"
			+ " ,delete_flag" + " ,display_info_id" + " ,display_info_image.id as dispaly_info_image_id" + " ,file_id"
			+ " ,file_name" + " ,modify_date" + " ,save_file_name" + "from display_info_image" + "left join file_info"
			+ "on display_info_image.file_id = file_info.id" + "where display_info_id = 1;";
}
