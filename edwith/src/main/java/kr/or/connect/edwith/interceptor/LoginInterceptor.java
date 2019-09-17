package kr.or.connect.edwith.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("PHJ...LoginIntercepter");
		
		if(wasLogin(request)) {
			return true;
		}else {
			response.sendRedirect("/edwith/login");
			return false;
		}
	}
	
	public boolean wasLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String remail = (String) session.getAttribute("remail");
		if(remail == null || "".equals(remail))  return false;
		return true;
	}
	
}
