package kr.or.connect.edwith.argumentresolver;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.or.connect.edwith.dto.Reservation;
import kr.or.connect.edwith.dto.ReservationInfo;

public class ReservationInfoArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType() == Reservation.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		
		ReservationInfo reservationInfo = new ReservationInfo();
		if(request.getAttribute("displayInfoId")!=null) {
			int displayInfoId = Integer.parseInt((String) request.getAttribute("displayInfoId"));
			reservationInfo.setDisplayInfoId(displayInfoId);
		}
		
		if(request.getAttribute("productId") != null) {
			int productId = Integer.parseInt((String) request.getAttribute("productId"));
			reservationInfo.setProductId(productId);
		}
		
		String reservationEmail =(String) request.getAttribute("reservationEmail");
		reservationInfo.setReservationEmail(reservationEmail);
		String reservationTelephone =(String) request.getAttribute("reservationTelephone");
		reservationInfo.setReservationTelephone(reservationTelephone);
		String reservationName =(String) request.getAttribute("reservationName");
		reservationInfo.setReservationName(reservationName);
		String reservationDate = (String) request.getAttribute("reservationYearMonth");
		reservationInfo.setReservationDate(reservationDate);
		System.out.println("argumentResolver"+reservationInfo.toString());
		
		
		return reservationInfo;
	}

}
