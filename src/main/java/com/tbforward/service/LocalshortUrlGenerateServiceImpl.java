package com.tbforward.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbforward.beans.ShortUrl;
import com.tbforward.dao.ShortUrlDao;
import com.tbforward.exception.ServiceException;

@Service("localShortUrl")
public class LocalshortUrlGenerateServiceImpl implements
		ShortUrlGenerateService {
	
	@Autowired
	private ShortUrlDao shortUrlDao;
	
	@Override
	public ShortUrl getByCode(String code) throws ServiceException {
		ShortUrl loaded = shortUrlDao.getByCode(code);
		if (loaded == null) {
			throw new ServiceException("Not Found with code: " + code);
		}
		return loaded;
	}

	@Override
	public ShortUrl generate(String url) throws ServiceException {
		ShortUrl res = ShortUrl.convert(url);
		
		if (res.getType() == ShortUrl.URI.DIRECT) {
    		throw new ServiceException("Taobao/Tmall url is not valid.");
    	}
		
		ShortUrl loaded = shortUrlDao.getByCode(res.getCode());
		if (loaded == null) {
			shortUrlDao.save(res);
			loaded = this.getByCode(res.getCode());
		}
		
		return loaded;
	}
	
//	@Resource
//	private Ignite ignite;
//	
//	
//	@Override
//	public String generate(String url) {
//		String code = ShortUrlUtil.generate(url);
//		
//		IgniteCache<String, String> cache = ignite.getOrCreateCache(getCache());
//		
//		cache.get(code);
//		
//		cache.put(code, url);
//		
//		cache.get(code);
//		
//		return code;
//	}
//	
//	public static CacheConfiguration<String, String> getCache() {
//		CacheConfiguration<String, String> cacheCfg = new CacheConfiguration<>("shortUrlCache");
//
//        // Set atomicity as transaction, since we are showing transactions in example.
//        cacheCfg.setAtomicityMode(org.apache.ignite.cache.CacheAtomicityMode.ATOMIC);
//
//        // Configure Spring store.
//        cacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(ShortUrlCacheStore.class));
//
//        // Configure Spring session listener.
////        cacheCfg.setCacheStoreSessionListenerFactories(new Factory<CacheStoreSessionListener>() {
////            @Override public CacheStoreSessionListener create() {
////                CacheSpringStoreSessionListener lsnr = new CacheSpringStoreSessionListener();
////
////                lsnr.setDataSource(CacheSpringPersonStore.DATA_SRC);
////
////                return lsnr;
////            }
////        });
//
//        cacheCfg.setReadThrough(true);
//        cacheCfg.setWriteThrough(true);
//        
//        return cacheCfg;
//	}

}
