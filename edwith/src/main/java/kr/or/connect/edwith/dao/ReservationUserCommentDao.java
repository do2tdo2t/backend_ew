package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.ReservationUserComment;

public interface ReservationUserCommentDao {
	public List<ReservationUserComment> selectByProductId(Integer productId, Integer limit);
	public int insertComment(ReservationUserComment comment);
	public List<ReservationUserComment> selectAllByProductId(Integer productId);
	public Integer getCountCommentsByProductId(Integer productId);
	public Integer putComment(ReservationUserComment comment);
}
