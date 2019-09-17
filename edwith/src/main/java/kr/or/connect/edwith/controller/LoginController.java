package kr.or.connect.edwith.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.edwith.dto.ReservationInfo;
import kr.or.connect.edwith.service.ReservationService;

@RestController
@RequestMapping(path="/login")
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ReservationService reservationService;
	
	
	@GetMapping("/page")
	public ModelAndView page() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		
		return mav;
	}
	
	@PostMapping("/try")
	public ModelAndView check(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		
		String reservationEmail = request.getParameter("reservationEmail");
		logger.debug("PHJ.. try login...reservationEmail : {}",reservationEmail);
		ReservationInfo reservationInfo = new ReservationInfo();
		reservationInfo.setReservationEmail(reservationEmail);
		
		if(reservationService.checkReservations(reservationInfo)) {
			//예약이 확인 되었을 시
			logger.debug("예약이 확인 되었습니다.. ReservationInfo : {}", reservationInfo.toString());
			session.setAttribute("rChk", "y");
			session.setAttribute("remail", reservationInfo.getReservationEmail());
			session.setAttribute("rname", reservationInfo.getReservationName());
			session.setAttribute("rtel", reservationInfo.getReservationTelephone());
			mav.setViewName("redirect:/api/reservations/mypage");
			
		}else {

			logger.debug("확인된 예약이 없습니다.");
			session.setAttribute("rChk", "n");
			mav.setViewName("redirect:/login/page");
		}
		
		return mav;
	}
	
	
}
