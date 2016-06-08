package com.tbforward.ignite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteCallable;

public class IgniteDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		try (Ignite ignite = Ignition.start("/root/git/tbforward/src/main/resources/example-cache.xml")) {
//		    IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");
//		 
//		    // Store keys in cache (values will end up on different cache nodes).
//		    for (int i = 0; i < 10; i++)
//		        cache.put(i, Integer.toString(i));
//		 
//		    for (int i = 0; i < 10; i++)
//		        System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
//		    
//		    Thread.sleep(10000);
//		    
//		    for (int i = 0; i < 10; i++)
//		        System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
//		}
		
		IgniteConfiguration cfg = new IgniteConfiguration();
		CacheConfiguration cacheCfg = new CacheConfiguration();
		cfg.setCacheConfiguration(cacheCfg);
		cacheCfg.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 5)));
		
		Ignite ignite = Ignition.start(cfg);
		
		IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");
//		cache = cache.withExpiryPolicy(
//			    new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 5)));
		
		
		 // Store keys in cache (values will end up on different cache nodes).
	    for (int i = 0; i < 10; i++)
	        cache.put(i, Integer.toString(i));
	 
	    for (int i = 0; i < 10; i++)
	        System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
	    
	    Thread.sleep(10000);
	    
	    System.out.println(">>> after 10s sleep");
	    
	    for (int i = 0; i < 10; i++)
	        System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');

	}

}
