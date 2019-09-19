package kr.or.connect.edwith.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import kr.or.connect.edwith.dto.ProductPrice;
import kr.or.connect.edwith.dto.ReservationInfo;
import kr.or.connect.edwith.dto.ReservationUserComment;
import kr.or.connect.edwith.service.DisplayService;
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
	public ModelAndView page() {
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
	public Integer putReservation(HttpServletRequest request, @RequestBody ReservationInfo reservationInfo) {
		logger.info("POST /api/reservations..");
		logger.info("POST /api/reservations.. Params: {}", reservationInfo.toString());
		
		int result = reservationService.putReservationInfo(reservationInfo);
		
		return result;
	}

	@PostMapping("/{reservationInfoId}/comments")
	public Integer putReservationComment(
			@PathVariable(name = "reservationInfoId", required = true) int reservationInfoId,
			@RequestParam("file") MultipartFile file, @RequestBody ReservationUserComment comment) {

		
		int result = reservationService.putReservationComment(reservationInfoId, comment);
		return result;
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
		return 0;
	}
	
	
	public void fileUpload(MultipartFile file) {
		try (FileOutputStream fos = new FileOutputStream("c:/tmp/" + file.getOriginalFilename());
				InputStream is = file.getInputStream();) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Save" + " Error");
		}
	}
	
	

}
