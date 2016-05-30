package com.tbforward.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tbforward.beans.ShortUrl;

@Repository
@CacheConfig(cacheNames="shortUrl")
public class ShortUrlDaoImpl implements ShortUrlDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(ShortUrl shortUrl) {
		String SQL = "insert into shorturl (code, url) values (?, ?)";

		jdbcTemplate.update(SQL, shortUrl.getCode(), shortUrl.getUrl());
		
		System.out.println("Insert Record with Code = " + shortUrl.getCode());
	}

	@Override
	@Cacheable(key="#code", unless = "#result == null")
	public ShortUrl getByCode(String code) {
		String SQL = "select * from shorturl where code = ?";
		List<?> list = jdbcTemplate.query(SQL,
				new Object[] { code }, new BeanPropertyRowMapper<ShortUrl>(ShortUrl.class));
		
		System.out.println("Query Record with Code = " + code);
		return list.isEmpty() ? null : (ShortUrl) list.iterator().next();
	}

	@Override
	public void update(ShortUrl shortUrl) {
		String SQL = "update shorturl set code = ?, url = ? where id = ?";
		jdbcTemplate.update(SQL, shortUrl.getCode(), shortUrl.getUrl(),
				shortUrl.getId());
		System.out.println("Updated Record with ID = " + shortUrl.getId());
		return;
	}

	@Override
	public void deleteById(Integer id) {
		String SQL = "delete from shorturl where id = ?";
		jdbcTemplate.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;
	}

}
