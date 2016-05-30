package com.tbforward.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.http.HttpStatus;

import com.tbforward.beans.JsonData;

@ControllerAdvice
public class SpringExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(SpringExceptionResolver.class);

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleServiceException(HttpServletRequest req,
			ServiceException e) {
		this.loggingMessage(req, e, false);

//		ModelAndView mav = new ModelAndView();
//		mav.addObject(JsonData.error(e.getMessage()));
		ModelAndView mav = new ModelAndView("exception", JsonData.error(e.getMessage()).toMap());
		return mav;
	}
    
    private void loggingMessage(HttpServletRequest req, Exception e, boolean isError) {
        if (isError) {
//            logger.error(String.format("URI: %s", CommonUtils.getSecureResource(req.getMethod(), req.getRequestURI())));
        	logger.error("Exception: ", e);
        } else {
//            logger.debug(String.format("URI: %s", CommonUtils.getSecureResource(req.getMethod(), req.getRequestURI())));
        	logger.debug("Exception: ", e);
        }
    }

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		this.loggingMessage(request, ex, true);
//		ModelAndView mav = new ModelAndView();
//		mav.addObject(JsonData.error(ex.getMessage()));
		ModelAndView mav = new ModelAndView("exception", JsonData.error(ex.getMessage()).toMap());
		return mav;
	}

//    private ModelAndView constructMAV(ErrorCode errorCode, String message) {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("errorCode", errorCode.getCode());
//        mav.addObject("errorType", errorCode.getType());
//        mav.addObject("message", message);
//        return mav;
//    }
//
//    private ModelAndView constructMAV(ClusterErrorCode errorCode, String message) {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("errorCode", errorCode.errorCode());
//        mav.addObject("errorType", errorCode.name());
//        mav.addObject("message", message);
//        return mav;
//    }

}
