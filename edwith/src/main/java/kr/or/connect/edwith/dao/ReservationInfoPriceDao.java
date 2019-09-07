package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.ReservationInfoPrice;


public interface ReservationInfoPriceDao {
	
	public int[] insertPrices(List<ReservationInfoPrice> prices);
}
