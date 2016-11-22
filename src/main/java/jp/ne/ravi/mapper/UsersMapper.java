package jp.ne.ravi.mapper;

import java.util.Map;

import jp.ne.ravi.dto.UsersDto;

public interface UsersMapper {

	int count(UsersDto dto);

	int updateTheLastLoginTime(Map<String, Object> param);
}
