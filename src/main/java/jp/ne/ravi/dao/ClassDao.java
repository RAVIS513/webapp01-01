package jp.ne.ravi.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jp.ne.ravi.dto.ClassDto;
import jp.ne.ravi.mapper.ClassMapper;

public class ClassDao {

	private Logger logger = LogManager.getLogger(ClassDao.class);

	private ClassMapper mapper = null;

	public ClassDao(SqlSession session) {
		this.mapper = session.getMapper(ClassMapper.class);
	}

	public int count(ClassDto dto) {
		logger.debug("Class Table count run");
		return mapper.count(dto);
	}

	public List<ClassDto> selectAll() {
		logger.debug("Class Table selectAll run");
		return mapper.selectAll();
	}

	public int insert(ClassDto dto) {
		logger.debug("Class Table insert run");
		return mapper.insert(dto);
	}

	public int delete(ClassDto dto) {
		logger.debug("Class Table delete run");
		return mapper.delete(dto);
	}
}
