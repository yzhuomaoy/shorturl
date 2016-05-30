package com.tbforward.service;

import com.tbforward.beans.ShortUrl;
import com.tbforward.exception.ServiceException;

public interface ShortUrlGenerateService {
	
	ShortUrl generate(String url) throws ServiceException;
	
	ShortUrl getByCode(String code) throws ServiceException;
}
