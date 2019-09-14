package kr.or.connect.edwith.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.DisplayInfoImage;
import kr.or.connect.edwith.dto.ProductImage;
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
	
	
	@GetMapping("/reserve/{displayInfoId}")
	public ModelAndView reservationPage(
			@PathVariable(name="displayInfoId", required=true) Integer displayInfoId ) {
		ModelAndView mav = new ModelAndView();
		
		
		DisplayInfo displayInfo = displayService.getDisplayInfoById(displayInfoId);
		DisplayInfoImage displayInfoImage = displayService.getDisplayInfoImageById(displayInfoId);
		List<ProductPrice> productPrices = productService.getProductPrices(displayInfo.getProductId());
		HashMap<String,ProductPrice> productPricesMap = new HashMap<String,ProductPrice>();
		
		for(ProductPrice price : productPrices) {
			price.setRealPrice();
			productPricesMap.put(price.getPriceTypeName(), price);
			
		}
				
		mav.addObject("displayInfo", displayInfo);
		mav.addObject("displayInfoImage", displayInfoImage);
		mav.addObject("productPricesMap",productPricesMap);
		logger.debug("PHJ productPrices : {}",productPrices);
		mav.setViewName("reserve");
		
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
	public Integer putReservation(@RequestBody ReservationInfo reservationInfo) {
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
