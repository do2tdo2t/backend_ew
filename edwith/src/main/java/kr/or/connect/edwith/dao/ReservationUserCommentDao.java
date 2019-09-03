package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.ReservationUserComment;

public interface ReservationUserCommentDao {
	public List<ReservationUserComment> selectAllByProductId(Integer productId, Integer limit);
}
