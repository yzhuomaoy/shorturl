package com.tbforward.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class SessionInterceptor extends HandlerInterceptorAdapter {

	private final static String[] excludeUrls = new String[]{
        "POST://session",
        "GET://redirect/{code}"
	};
	
//	@Autowired
//	private UserService userService;
	
	public static String getSecureResource(String method, String uri){
        uri = StringUtils.trim(uri);
        if (uri != null && uri.endsWith(".*")) {
            uri = uri.substring(0, uri.lastIndexOf(".*"));
        } else if (uri != null && uri.endsWith("/")) {
            uri = uri.substring(0, uri.lastIndexOf("/"));
        }
        return new StringBuffer().append(StringUtils.upperCase(method))
                                 .append(":/")
                                 .append(uri)
                                 .toString();
    }


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// assume that this is the first intercepter
		request.setAttribute("startedAt", System.currentTimeMillis());

		String method = request.getMethod();
		String uri = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		String secureResource = getSecureResource(method, uri);
		
		for(String url : excludeUrls){
			if (url.equals(secureResource)) {
				// skip this intercepter 
				return true;
			}
		}
		
		HttpSession session = request.getSession(false);
		
//		if (session == null) {
//		    throw new UnauthorizedException(ErrorCode.Unauthorized_InvalidSession, "There is no active session. Please try login first.");
//		}
//		
//		SessionUser user = (SessionUser)session.getAttribute(SessionConstants.SESSION_USER_OBJECT);
//		
//		if (user == null) {
//			// session has timed out
//			//logger.debug("Session timeout! sessionId => " + session.getId());
//			//logger.debug("check user has been deleted...true.");
//			session.invalidate();
//			throw new UnauthorizedException(ErrorCode.Unauthorized_InvalidSession, "Current session has timed out.");
//		} else if (user.getUserUUID() == null || userService.get(user.getUserUUID()) == null) {
//			session.invalidate();
//			throw new UnauthorizedException(ErrorCode.Unauthorized_InvalidSession, "Current session user has been deleted.");
//		} 

		return true;
	}

}

