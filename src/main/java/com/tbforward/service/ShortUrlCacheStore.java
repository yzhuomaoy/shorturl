package com.tbforward.service;

import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;

import org.apache.ignite.cache.store.CacheStoreAdapter;

public class ShortUrlCacheStore extends CacheStoreAdapter<String, String> {

	@Override
	public String load(String key) throws CacheLoaderException {
		System.out.println(">>>>> load code: " + key);
		return "www.tmall.com";
	}

	@Override
	public void write(Entry<? extends String, ? extends String> entry)
			throws CacheWriterException {
		System.out.println(">>>>> write code: " + entry.getKey() + ", value: " + entry.getValue());
	}

	@Override
	public void delete(Object key) throws CacheWriterException {
		System.out.println(">>>>> remove code: " + key);
	}

}
