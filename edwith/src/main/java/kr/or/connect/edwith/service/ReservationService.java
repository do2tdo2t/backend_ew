package kr.or.connect.edwith.service;

import java.util.List;

import kr.or.connect.edwith.dto.ReservationUserComment;
import kr.or.connect.edwith.dto.ReservationUserCommentImage;

public interface ReservationService {
	public static final Integer COMMENT_COUNT = 3;
	public List<ReservationUserComment> getComments(Integer productId);
	public List<ReservationUserCommentImage> getCommentImages(Integer commentId);
}
