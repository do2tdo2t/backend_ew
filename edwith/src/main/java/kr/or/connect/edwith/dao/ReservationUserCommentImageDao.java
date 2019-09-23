package kr.or.connect.edwith.dao;

import java.util.List;

import kr.or.connect.edwith.dto.ReservationUserCommentImage;

public interface ReservationUserCommentImageDao {
	public List<ReservationUserCommentImage> selectAllByCommentId(Integer commentId);

	public Integer insertOne(ReservationUserCommentImage commentImage);
}
