package com.tbforward.ignite;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;

public class IgniteDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try (Ignite ignite = Ignition.start("/root/git/tbforward/src/main/resources/example-cache.xml")) {
		    IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");
		 
		    // Store keys in cache (values will end up on different cache nodes).
		    for (int i = 0; i < 10; i++)
		        cache.put(i, Integer.toString(i));
		 
		    for (int i = 0; i < 10; i++)
		        System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
		}

	}

}
