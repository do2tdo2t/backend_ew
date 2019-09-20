package kr.or.connect.edwith.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.edwith.dao.DisplayInfoDao;
import kr.or.connect.edwith.dao.ReservationInfoDao;
import kr.or.connect.edwith.dao.ReservationInfoPriceDao;
import kr.or.connect.edwith.dao.ReservationUserCommentDao;
import kr.or.connect.edwith.dao.ReservationUserCommentImageDao;
import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.ReservationInfo;
import kr.or.connect.edwith.dto.ReservationInfoPrice;
import kr.or.connect.edwith.dto.ReservationUserComment;
import kr.or.connect.edwith.dto.ReservationUserCommentImage;
import kr.or.connect.edwith.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ReservationUserCommentDao commentDao;
	
	@Autowired
	ReservationUserCommentImageDao commentImageDao;
	
	@Autowired
	ReservationInfoDao reservationInfoDao;
	
	@Autowired
	ReservationInfoPriceDao reservationInfoPriceDao;
	
	@Autowired
	DisplayInfoDao displayInfoDao;
	
	@Override
	public List<ReservationUserComment> getComments(Integer productId) {
		List<ReservationUserComment> comments = commentDao.selectByProductId(productId, COMMENT_COUNT);
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

	@Override
	public int putReservationInfo(ReservationInfo reservationInfo) {
		
		int reservationInfoId = reservationInfoDao.insertReservationInfo(reservationInfo);
		logger.debug("PHJ: Success put ReservationInfo : reservationInfoId : "+reservationInfoId);
		List<ReservationInfoPrice> list = new ArrayList<ReservationInfoPrice>();
		
		for(ReservationInfoPrice price : reservationInfo.getPrices()) {
			price.setReservationInfoId(reservationInfoId);
			list.add(price);
		}
		logger.debug("PHJ: Set ReservationInfo : {} : "+ list.toString() );
		
		int[] result2 = reservationInfoPriceDao.insertPrices(list);
		
		logger.debug("PHJ: Success put {} ReservationInfoPrices",result2);
		return 200;
	}

	@Override
	public int putReservationComment(int reservationInfoId, ReservationUserComment comment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReservationUserComment> getCommentsAll(Integer productId) {
		
		List<ReservationUserComment> comments = commentDao.selectAllByProductId(productId);
		List<ReservationUserCommentImage> images = null;
		for (ReservationUserComment comment : comments) {
			images = getCommentImages(comment.getCommentId());
			comment.setCommentImages(images);
		}
		
		logger.debug("PHJ : getCommentsAll().. comments : "+comments.toString() );
		
		return comments;
	}

	@Override
	public boolean checkReservations(ReservationInfo reservationInfo) {
		
		return reservationInfoDao.selectReservationInfoByEmail(reservationInfo);
		
	}

	@Override
	public int deleteReservation(int reservationId) {
		int cnt = reservationInfoDao.deleteReservationById(reservationId);
		if(cnt == 1) return 200;
		else return -1;
	}
	
}
