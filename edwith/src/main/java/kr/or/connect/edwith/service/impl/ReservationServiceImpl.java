package kr.or.connect.edwith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.edwith.dao.ReservationUserCommentDao;
import kr.or.connect.edwith.dao.ReservationUserCommentImageDao;
import kr.or.connect.edwith.dto.ReservationUserComment;
import kr.or.connect.edwith.dto.ReservationUserCommentImage;
import kr.or.connect.edwith.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	ReservationUserCommentDao commentDao;
	
	@Autowired
	ReservationUserCommentImageDao commentImageDao;
	
	
	@Override
	public List<ReservationUserComment> getComments(Integer productId) {
		List<ReservationUserComment> comments = commentDao.selectAllByProductId(productId, COMMENT_COUNT);
		List<ReservationUserCommentImage> images = null;
		for (ReservationUserComment comment : comments) {
			images = getCommentImages(comment.getCommentId());
			comment.setCommentImages(images);
		}
		return comments;
	}

	@Override
	public List<ReservationUserCommentImage> getCommentImages(Integer commentId) {
		List<ReservationUserCommentImage> commentImages = commentImageDao.selectAllByCommentId(commentId);
		return commentImages;
	}
	
}
