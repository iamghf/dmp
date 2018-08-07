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
import com.ghf.dmp.bean.SysParaDo;

@Repository
@CacheConfig(cacheNames = "sysParas")
public class SysParaInfoDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(StaffInfoDo[] values) throws Exception {

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Cacheable("gSysParas")
	public List<SysParaDo> getSysPara() throws Exception {
		List<SysParaDo> userList = jdbcTemplate.query("select * from sys_para",
				new BeanPropertyRowMapper(SysParaDo.class));
		/*if(null != userList && !userList.isEmpty()){
			return userList.toArray(new StaffInfoDo[0]);
		}*/
		return userList;
	}
	
	public List<SysParaDo> getSysParaByCode(String code)throws Exception {
		List<SysParaDo> userList = jdbcTemplate.query("select * from sys_para t where t.code = ?", new String[] {code},
				new BeanPropertyRowMapper<SysParaDo>(SysParaDo.class));
		return userList;
	}
}
