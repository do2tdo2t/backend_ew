package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.ReservationInfo;

public interface ReservationInfoDao {
	
	public List<ReservationInfo> selectAllByEmail(String reservationInfoEmail);
	public int getTotalPriceById(Integer reservationInfoId);
	public int countByEmail(String reservationInfoEmail);
	public int insertReservationInfo(ReservationInfo reservationInfo);
}
