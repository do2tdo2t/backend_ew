package kr.or.connect.edwith.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import ch.qos.logback.core.net.SyslogOutputStream;
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
	
	@GetMapping("/mypage")
	public ModelAndView mypage() {
		logger.debug("PHJ... request page()");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("myreservation");
		return mav;
	}
	
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
	
	@PostMapping
	public Integer putReservation(HttpServletRequest request, 
			@RequestBody ReservationInfo reservationInfo, HttpSession session) {
		logger.info("POST /api/reservations..");
		logger.info("POST /api/reservations.. Params: {}", reservationInfo.toString());
		
		int result = reservationService.putReservationInfo(reservationInfo);
		
		return result;
	}
	
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
	
	/* comment 등록 */
	@PostMapping("/{reservationInfoId}/comments")
	public Integer putReservationComment(
			HttpServletRequest request,
			@PathVariable(name = "reservationInfoId", required = true) int reservationInfoId,
			@RequestParam("files") List<MultipartFile> files, 
			ReservationUserComment comment) {
		
		logger.debug("putReservationComment files size : {}, comment : {}", files.size(),
				comment);
		
		String uploadFolderUrl  = request.getServletContext().getRealPath("/")+"tmp";
		System.out.println(uploadFolderUrl);
		//1. file upload
	
		FileInfo fileInfo = null;
		String saveFileName = "";
		
		
		//2. file_info db update
		Integer fileInfoId;
		List<Integer> fileInfoIds = new ArrayList<Integer>();
		for(MultipartFile file : files) {
			fileInfo = new FileInfo();
			saveFileName = fileUpload(uploadFolderUrl,file);
			logger.debug("new File name : {}",saveFileName);
			fileInfo.setFileName(saveFileName);
			fileInfo.setSaveFileName(uploadFolderUrl+"/"+saveFileName);
			fileInfo.setContentType(file.getContentType());
			
			fileInfoId = fileInfoService.putFileInfo(fileInfo);
			logger.info("fileInfoId : {}",fileInfoId);
			fileInfoIds.add(fileInfoId);
		}
		
		//3. comment db update
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
		
		return 200;
	}


	public String fileUpload(String uploadFolderUrl,MultipartFile file) {
		logger.debug("ReservationApiController fileUpload()...old file name : {}", file.getOriginalFilename());
		
		File uploadFolder = new File(uploadFolderUrl);
		if(!uploadFolder.isDirectory()) {
			System.out.println("존재하지 X.. ");
			uploadFolder.mkdir();
		}
		
		String newFileName = getFileNewName(uploadFolderUrl,file.getOriginalFilename());
		
		//file 새로운 이름으로 생성
		try {
			file.transferTo(new File(uploadFolderUrl, newFileName));
		}catch(IOException e) {
			//throw new RuntimeException("file Save" + " Error...1");
			e.printStackTrace();
		}
		
		logger.debug("ReservationApiController fileUpload()... file upload...{}",file.getOriginalFilename());
		
		/*
		try (FileOutputStream fos = new FileOutputStream(folder+"/" + file.getOriginalFilename());
				InputStream is = file.getInputStream();) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			//throw new RuntimeException("file Save" + " Error");
			ex.printStackTrace();
		}
		*/
		return newFileName;
	}
	
	public String getFileNewName(String folder, String orgFileName) {
		SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMddHHmmss");
		String date;
		int idx = orgFileName.lastIndexOf("."); // 마지막 .의 위치를 구함
		
		String fileExtension = orgFileName.substring(idx+1);
		
		String newFileName = format.format(new Date())+"."+fileExtension;
		File fCheck = new File(folder,newFileName);
	
		while(fCheck.exists()) {
			date = format.format(new Date());
			newFileName = date +"."+ fileExtension;
			fCheck = new File(folder,newFileName);
		}
		
		logger.debug("ReservationApiController newFileName()....{} {}",folder,newFileName);
		return newFileName;
	}
	
	
	@GetMapping("/{productId}")
	public Map getCommentsAll(
			@PathVariable(name="productId", required=true) int productId ) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReservationUserComment> comments = reservationService.getCommentsAll(productId);
		map.put("comments", comments);
		
		return map;
	}
	
	
	@PutMapping("/{reservationId}")
	public Integer deleteReservation(
			@PathVariable(name="reservationId", required=true) int reservationId ) {
	
		int status = reservationService.deleteReservation(reservationId);
		logger.debug("PHJ : ResrevationApiController.. deleteReservation().. : Result : {}", status);
		
		return status;
	}
	
	
	
	

}
