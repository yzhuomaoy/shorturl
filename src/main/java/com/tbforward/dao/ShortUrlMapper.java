package com.tbforward.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tbforward.beans.ShortUrl;

public class ShortUrlMapper implements RowMapper<ShortUrl> {
	public ShortUrl mapRow(ResultSet rs, int rowNum) throws SQLException {
		ShortUrl shortUrl = new ShortUrl();
		shortUrl.setId(rs.getInt("id"));
		shortUrl.setCode(rs.getString("code"));
		shortUrl.setUrl(rs.getString("url"));
		return shortUrl;
	}
}
