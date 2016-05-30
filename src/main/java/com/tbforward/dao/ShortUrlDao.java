package com.tbforward.dao;

import com.tbforward.beans.ShortUrl;

public interface ShortUrlDao {
	// Create
	public void save(ShortUrl shortUrl);

	// Read
	public ShortUrl getByCode(String code);
	
	// Update
	public void update(ShortUrl shorUrl);

	// Delete
	public void deleteById(Integer id);
}
