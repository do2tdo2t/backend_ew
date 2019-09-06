package kr.or.connect.edwith.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.edwith.dto.ReservationInfo;
import kr.or.connect.edwith.service.ReservationService;

@RestController
@RequestMapping(path="/api/reservations")
public class ReservationApiController {
	
	@Autowired
	ReservationService reservationService;
	
	@GetMapping
	public Map<String,Object> getReservations(
			@RequestParam(name="reservationEmail", required=true)
			String reservationInfoEmail ){
		
		System.out.println("reservationInfoEmail : " + reservationInfoEmail);
		Map<String,Object> map = new HashMap<String,Object>();
		List<ReservationInfo> reservations = reservationService.getReservationInfos(reservationInfoEmail);
		int size = reservationService.getCountByEmail(reservationInfoEmail);
		
		map.put("reservations", reservations);
		map.put("size", size);
		
		return map;
	}

}
