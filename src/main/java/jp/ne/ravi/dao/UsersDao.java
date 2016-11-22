package jp.ne.ravi.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jp.ne.ravi.dto.UsersDto;
import jp.ne.ravi.mapper.UsersMapper;

public class UsersDao {

	private Logger logger = LogManager.getLogger(UsersDao.class);

	private UsersMapper mapper = null;

	public UsersDao(SqlSession session) {
		this.mapper = session.getMapper(UsersMapper.class);
	}

	public int count(UsersDto dto) {
		logger.debug("Users Table count run");
		return mapper.count(dto);
	}

	public int updateTheLastLoginTime(String userId) {
		logger.debug("Users Table updateTheLastLoginTime run");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("loginTime", new Timestamp(System.currentTimeMillis()));
		return mapper.updateTheLastLoginTime(param);
	}

}
