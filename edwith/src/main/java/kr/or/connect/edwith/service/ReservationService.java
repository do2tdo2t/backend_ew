package kr.or.connect.edwith.service;

import java.util.List;

import kr.or.connect.edwith.dto.ReservationInfo;
import kr.or.connect.edwith.dto.ReservationUserComment;
import kr.or.connect.edwith.dto.ReservationUserCommentImage;

public interface ReservationService {
	public static final Integer COMMENT_COUNT = 3;
	public List<ReservationUserComment> getComments(Integer productId);
	public List<ReservationUserComment> getCommentsAll(Integer productId);
	public List<ReservationUserCommentImage> getCommentImages(Integer commentId);
	public List<ReservationInfo> getReservationInfos(String reservationInfoEmail);
	public Integer getCountByEmail(String reservationInfoEmail);
	public Integer putReservationInfo(ReservationInfo reservationInfo);
	public Integer putReservationComment(int reservationInfoId, ReservationUserComment comment);
	public boolean checkReservations(ReservationInfo reservationInfo);
	public Integer deleteReservation(int reservationId);
	public Integer putCommentImage(ReservationUserCommentImage commentImage);
}
