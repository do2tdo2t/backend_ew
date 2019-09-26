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
	
	/*
	 * 리뷰 전체 가져오기
	 * */
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
	
	/*
	 * 리뷰 이미지 가져오기
	 * */
	@Override
	public List<ReservationUserCommentImage> getCommentImages(Integer commentId) {
		List<ReservationUserCommentImage> commentImages = commentImageDao.selectAllByCommentId(commentId);
		return commentImages;
	}

	/*
	 * 나의 예매 목록 가져오기
	 * */
	@Override
	public List<ReservationInfo> getReservationInfos(String reservationInfoEmail) {
		
		List<ReservationInfo> list = reservationInfoDao.selectAllByEmail(reservationInfoEmail);
		
		DisplayInfo displayInfo = null;
		
		int displayInfoId;
		
		for (ReservationInfo reservation : list) {
			displayInfoId = reservation.getDisplayInfoId();
			
			displayInfo = displayInfoDao.selectOneById(displayInfoId);
			reservation.setDisplayInfo(displayInfo);
		}
		
		return list;
	}

	
	/*
	 * 나의 예매 목록 개수 가져오기
	 * */
	@Override
	public Integer getCountByEmail(String reservationInfoEmail) {
		
		return reservationInfoDao.countByEmail(reservationInfoEmail);
	}

	
	/*
	 * 예매하기
	 * 1. 예매 정보 넣기
	 * 2. 예매 가격 넣기
	 * */
	@Override
	public Integer putReservationInfo(ReservationInfo reservationInfo) {
		
		//1. 예매 정보 넣기
		int reservationInfoId = reservationInfoDao.insertReservationInfo(reservationInfo);
		
		logger.debug("PHJ: Success put ReservationInfo : reservationInfoId : "+reservationInfoId);
		List<ReservationInfoPrice> list = new ArrayList<ReservationInfoPrice>();
		
		//1. 예매 가격 정보
		for(ReservationInfoPrice price : reservationInfo.getPrices()) {
			price.setReservationInfoId(reservationInfoId);
			list.add(price);
		}
		logger.debug("PHJ: Set ReservationInfo : {} : "+ list.toString() );
		
		int[] result2 = reservationInfoPriceDao.insertPrices(list);
		
		logger.debug("PHJ: Success put {} ReservationInfoPrices",result2);
		return 200;
	}

	/*
	 * 리뷰 넣기
	 * */
	@Override
	public Integer putReservationComment(int reservationInfoId, ReservationUserComment comment) {
		comment.setReservationInfoId(reservationInfoId);
		return commentDao.insertComment(comment);
	}

	/*
	 * 리뷰 전체 가져오기
	 * */
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

	/*
	 * 예약 여부 확인하기
	 * */
	@Override
	public boolean checkReservations(ReservationInfo reservationInfo) {
		
		return reservationInfoDao.selectReservationInfoByEmail(reservationInfo);
		
	}

	/*
	 * 예약 취소하기
	 * */
	@Override
	public Integer deleteReservation(int reservationId) {
		int cnt = reservationInfoDao.deleteReservationById(reservationId);
		if(cnt == 1) return 200;
		else return -1;
	}

	
	/*
	 * 리뷰에 등록된 이미지 정보 넣기
	 * */
	@Override
	public Integer putCommentImage(ReservationUserCommentImage commentImage) {
		
		return commentImageDao.insertOne(commentImage);
	}
	
}
