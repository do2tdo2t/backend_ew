package kr.or.connect.edwith.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.DisplayInfoImage;
import kr.or.connect.edwith.dto.FileInfo;
import kr.or.connect.edwith.dto.ProductPrice;
import kr.or.connect.edwith.dto.ReservationInfo;
import kr.or.connect.edwith.dto.ReservationUserComment;
import kr.or.connect.edwith.dto.ReservationUserCommentImage;
import kr.or.connect.edwith.service.DisplayService;
import kr.or.connect.edwith.service.FileInfoService;
import kr.or.connect.edwith.service.ProductService;
import kr.or.connect.edwith.service.ReservationService;
import kr.or.connect.edwith.util.DateUtil;
import kr.or.connect.edwith.util.FileUploadUtil;

@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationApiController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	DisplayService displayService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	FileInfoService fileInfoService;
	
	
	/*
	 * 예매하기 페이지와 공연,전시 상세 정보
	 * */
	@GetMapping("/{displayInfoId}/buy")
	public ModelAndView reservationPage(
			@PathVariable(name="displayInfoId", required=true) Integer displayInfoId ) {
		ModelAndView mav = new ModelAndView();
		
		DisplayInfo displayInfo = displayService.getDisplayInfoById(displayInfoId);
		DisplayInfoImage displayInfoImage = displayService.getDisplayInfoImageById(displayInfoId);
		List<ProductPrice> productPrices = productService.getProductPrices(displayInfo.getProductId());
		HashMap<String,ProductPrice> productPricesMap = new HashMap<String,ProductPrice>();
		
		//priceTypeName을 키로하는 Map 생성
		for(ProductPrice price : productPrices) {
			logger.info("PHJ: {}",price);
			productPricesMap.put(price.getPriceTypeName(), price);
		}
		
		/*reservateDate 규칙에 따라 생성
		 * 예매내용내의 예매일은 웹프론트엔드에서 사용자가 선택하거나 입력해서 얻어지는 정보가 아니고, 서버에서 다음의 규칙으로 생성해서 내려준다.  
		 * (예매일생성 규칙 : 예매일 기준  오늘포함해서 1-5일 랜덤값으로 서버에서 생성해서 내려줌) yyyymmdd
		 * */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA );
		Calendar cal = new GregorianCalendar(Locale.KOREA); 
		Date today = new Date();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, (int)((Math.random()*5 ) +1 ));
		String reservateDate = sdf.format(cal.getTime());
		
		mav.addObject("displayInfo", displayInfo);
		mav.addObject("displayInfoImage", displayInfoImage);
		mav.addObject("productPricesMap",productPricesMap);
		mav.addObject("reservateDate", reservateDate);
		mav.setViewName("buy");
		
		return mav;
	}
	
	/*
	 * 나의 예약 페이지
	 * */
	@GetMapping("/mypage")
	public ModelAndView mypage() {
		logger.debug("PHJ... request page()");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("myreservation");
		return mav;
	}
	
	/*
	 * 나의 예약 목록 가져오기 - Ajax
	 * */
	@GetMapping
	public Map<String, Object> getReservations(
			@RequestParam(name = "reservationEmail", required = true) String reservationInfoEmail) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<ReservationInfo> reservations = reservationService.getReservationInfos(reservationInfoEmail);
		int size = reservationService.getCountByEmail(reservationInfoEmail);

		map.put("reservations", reservations);
		map.put("size", size);

		return map;
	}
	
	/*
	 * 예약하기 
	 * */
	@PostMapping
	public Integer putReservation(HttpServletRequest request, 
			@RequestBody ReservationInfo reservationInfo, HttpSession session) {
		logger.info("POST /api/reservations..");
		logger.info("POST /api/reservations.. Params: {}", reservationInfo.toString());
		
		int result = reservationService.putReservationInfo(reservationInfo);
		
		return result;
	}
	
	
	/*
	 * 리뷰 작성 페이지 
	 * */
	@GetMapping("/review/{productId}/{reservationInfoId}")
	public ModelAndView reviewPage(
			@PathVariable(name="productId",required = true) Integer productId,
			@PathVariable(name="reservationInfoId",required = true) Integer reservationInfoId) {
		logger.debug("PHJ... ReservationApiController... reviewPage()");
		
		String productName = productService.getProductDescription(productId);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("productId", productId);
		mav.addObject("reservationInfoId", reservationInfoId);
		mav.addObject("productName", productName);
		mav.setViewName("reviewWrite");
		return mav;
	}
	
	/*
	 * 리뷰 등록 페이지
	 * 1. 이미지 파일 업로드
	 * 2. file_info DB에 파일 정보 업로드
	 * 3. reservation_user_comment DB에 리뷰 업로드
	 * 4. reservation_user_comment_image DB에 이미지 정보 업로드
	 * 
	 * local variable
	 *  - folder : 이미지 파일 업로드 폴더의 절대 경로
	 *  - url : 이미지 파일 업로드 폴더의 웹프로젝트 내 상대 경로
	 *  - saveFileName : 업로드를 위해 새롭게 얻은 파일 이름
	 * */
	@PostMapping("/{reservationInfoId}/comments")
	public ModelAndView putReservationComment(
			HttpServletRequest request,
			@PathVariable(name = "reservationInfoId", required = true) int reservationInfoId,
			@RequestParam("files") List<MultipartFile> files, 
			ReservationUserComment comment) {
		
		logger.debug("putReservationComment files size : {}, comment : {}", files.size(),
				comment);
		
		
		String folder  = request.getSession().getServletContext().getRealPath("/img_comment");
		String url ="img_comment/";
		
		FileInfo fileInfo = null;
		String saveFileName = "";
		
		//1. file upload
		//2. file_info db update
		Integer fileInfoId;
		List<Integer> fileInfoIds = new ArrayList<Integer>();
		for(MultipartFile file : files) {
			fileInfo = new FileInfo();
			saveFileName = FileUploadUtil.getNewFileName(folder,file.getOriginalFilename());

			fileInfo.setFileName(saveFileName);
			fileInfo.setSaveFileName(url+"/"+saveFileName);
			fileInfo.setContentType(file.getContentType());
			fileInfo.setCreateDate(DateUtil.getNowDate());
			fileInfo.setModifyDate(DateUtil.getNowDate());
			
			//fileInfo insert db
			fileInfoId = fileInfoService.putFileInfo(fileInfo);
			logger.info("putReservationComment fileInfoId : {}",fileInfoId);
			fileInfoIds.add(fileInfoId);
			
			//fileupload
			FileUploadUtil.fileUpload(folder, saveFileName, file);
			
		}
		
		//3. comment db update
		comment.setCreateDate(DateUtil.getNowDate());
		comment.setModifyDate(DateUtil.getNowDate());
		int reservationUserCommentId = reservationService.putReservationComment(reservationInfoId, comment);
		
		//4. comment_image db update
		ReservationUserCommentImage commentImage;
		List<ReservationUserCommentImage> commentImages = new ArrayList<ReservationUserCommentImage>();
		for(Integer fid : fileInfoIds) {
			commentImage = new ReservationUserCommentImage();
			commentImage.setFileId(fid);
			commentImage.setReservationInfoId(reservationInfoId);
			commentImage.setReservationUserCommentId(reservationUserCommentId);
			commentImages.add(commentImage);
			int result = reservationService.putCommentImage(commentImage);
			logger.info("comment image id : {}", result);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/api/products/"+comment.getProductId());
		return mav;
	}

	
	/*
	 * 리뷰 가져오기 - Ajax
	 * */
	@GetMapping("/{productId}")
	public Map getCommentsAll(
			@PathVariable(name="productId", required=true) int productId ) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReservationUserComment> comments = reservationService.getCommentsAll(productId);
		map.put("comments", comments);
		
		return map;
	}
	
	/*
	 * 예약 삭제하기 
	 * 예약 삭제는 실제 리뷰를 삭제하지 않고, cancleYn 컬럼의 값을 true로 변경
	 * */
	@PutMapping("/{reservationId}")
	public Integer deleteReservation(
			@PathVariable(name="reservationId", required=true) int reservationId ) {
	
		int status = reservationService.deleteReservation(reservationId);
		logger.debug("PHJ : ResrevationApiController.. deleteReservation().. : Result : {}", status);
		
		return status;
	}
	
	
	
	

}
