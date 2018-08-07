package com.ghf.dmp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ghf.dmp.bean.StaffInfoDo;

@Repository
@CacheConfig(cacheNames = "staffs")
public class StaffInfoDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(StaffInfoDo[] values) throws Exception {

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Cacheable("gStaffs")
	public List<StaffInfoDo> getStaffByCode(String code) throws Exception {
		List<StaffInfoDo> userList = jdbcTemplate.query("select * from staff_info t where t.state = ?", new String[]{"1"},
				new BeanPropertyRowMapper(StaffInfoDo.class));
		return userList;
	}
}
