package kr.or.connect.edwith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.edwith.dao.DisplayInfoDao;
import kr.or.connect.edwith.dao.ReservationInfoDao;
import kr.or.connect.edwith.dao.ReservationUserCommentDao;
import kr.or.connect.edwith.dao.ReservationUserCommentImageDao;
import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.ReservationInfo;
import kr.or.connect.edwith.dto.ReservationUserComment;
import kr.or.connect.edwith.dto.ReservationUserCommentImage;
import kr.or.connect.edwith.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	ReservationUserCommentDao commentDao;
	
	@Autowired
	ReservationUserCommentImageDao commentImageDao;
	
	@Autowired
	ReservationInfoDao reservationInfoDao;
	
	@Autowired
	DisplayInfoDao displayInfoDao;
	
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

	@Override
	public List<ReservationInfo> getReservationInfos(String reservationInfoEmail) {
		
		List<ReservationInfo> list = reservationInfoDao.selectAllByEmail(reservationInfoEmail);
		
		DisplayInfo displayInfo = null;
		
		int displayInfoId;
		
		for (ReservationInfo reservation : list) {
			displayInfoId = reservation.getDisplayInfoId();
			
			displayInfo = displayInfoDao.selectDisplayInfoById(displayInfoId);
			reservation.setDisplayInfo(displayInfo);
		}
		
		return list;
	}

	@Override
	public int getCountByEmail(String reservationInfoEmail) {
		
		return reservationInfoDao.countByEmail(reservationInfoEmail);
	}
	
}
