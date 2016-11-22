package jp.ne.ravi.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jp.ne.ravi.constant.Const;
import jp.ne.ravi.dto.ContentsDto;
import jp.ne.ravi.mapper.ContentsMapper;

public class ContentsDao {

	private Logger logger = LogManager.getLogger(ContentsDao.class);

	private ContentsMapper mapper = null;

	public ContentsDao(SqlSession session) {
		this.mapper = session.getMapper(ContentsMapper.class);
	}

	public int countByStatus(String status) {
		logger.debug("Contents Table countByStatus Run");
		return mapper.countByStatus(status);
	}

	public int countByExceptStatus(String status) {
		logger.debug("Contents Table countByExceptStatus Run");
		return mapper.countByExceptStatus(status);
	}

	public List<ContentsDto> selectAll() {
		logger.debug("Contents Table selectAll Run");
		return mapper.selectAll();
	}

	public Date selectMaxUploadDate() {
		logger.debug("Contents Table selectMaxUploadDate Run");
		return mapper.selectMaxUploadDate();
	}

	public ContentsDto selectBySeqNo(int seqNo) {
		logger.debug("Contents Table selectBySeqNo Run");
		return mapper.selectBySeqNo(seqNo);
	}

	public List<ContentsDto> selectByStatus(String status) {
		logger.debug("Contents Table selectByStatus Run");
		return mapper.selectByStatus(status);
	}

	public List<ContentsDto> selectByExceptStatus(String status) {
		logger.debug("Contents Table selectByExceptStatus Run");
		return mapper.selectByExceptStatus(status);
	}

	// public List<ContentsDto> selectByExceptStatusWithPaging(String status,
	// int start, int end) {
	// logger.debug("Contents Table selectByExceptStatusWithPaging Run");
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("status", status);
	// param.put("start", start);
	// param.put("end", end);
	// return mapper.selectByExceptStatusWithPagingForOracle(param);
	// }

	public List<ContentsDto> selectByExceptStatusWithPaging(String status, int start, int end) {
		logger.debug("Contents Table selectByExceptStatusWithPaging Run");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", status);
		int offset = (start - 1) < 0 ? 0 : (start - 1);
		int limit = (end - start) < 0 ? 0 : (end - start) + 1;
		param.put("offset", offset);
		param.put("limit", limit);
		return mapper.selectByExceptStatusWithPagingForPostgres(param);
	}

	public List<ContentsDto> selectByClassLAndStatus(String largeClass, String status) {
		logger.debug("Contents Table selectByClassLAndStatus Run");
		ContentsDto dto = new ContentsDto();
		dto.setLargeClass(largeClass);
		dto.setStatus(status);
		return mapper.selectByClassLAndStatus(dto);
	}

	public List<ContentsDto> selectByClassLMAndStatus(String largeClass, String middleClass, String status) {
		logger.debug("Contents Table selectByClassLMAndStatus Run");
		ContentsDto dto = new ContentsDto();
		dto.setLargeClass(largeClass);
		dto.setMiddleClass(middleClass);
		dto.setStatus(status);
		return mapper.selectByClassLMAndStatus(dto);
	}

	public List<ContentsDto> selectByClassLMSAndStatus(String largeClass, String middleClass, String smallClass,
			String status) {
		logger.debug("Contents Table selectByClassLMSAndStatus Run");
		ContentsDto dto = new ContentsDto();
		dto.setLargeClass(largeClass);
		dto.setMiddleClass(middleClass);
		dto.setSmallClass(smallClass);
		dto.setStatus(status);
		return mapper.selectByClassLMSAndStatus(dto);
	}

	public List<ContentsDto> selectByClassLAndExceptStatus(String largeClass, String status) {
		logger.debug("Contents Table selectByClassLAndExceptStatus Run");
		ContentsDto dto = new ContentsDto();
		dto.setLargeClass(largeClass);
		dto.setStatus(status);
		return mapper.selectByClassLAndExceptStatus(dto);
	}

	public List<ContentsDto> selectByClassLMAndExceptStatus(String largeClass, String middleClass, String status) {
		logger.debug("Contents Table selectByClassLMAndExceptStatus Run");
		ContentsDto dto = new ContentsDto();
		dto.setLargeClass(largeClass);
		dto.setMiddleClass(middleClass);
		dto.setStatus(status);
		return mapper.selectByClassLMAndExceptStatus(dto);
	}

	public List<ContentsDto> selectByClassLMSAndExceptStatus(String largeClass, String middleClass, String smallClass,
			String status) {
		logger.debug("Contents Table selectByClassLMSAndExceptStatus Run");
		ContentsDto dto = new ContentsDto();
		dto.setLargeClass(largeClass);
		dto.setMiddleClass(middleClass);
		dto.setSmallClass(smallClass);
		dto.setStatus(status);
		return mapper.selectByClassLMSAndExceptStatus(dto);
	}

	public List<ContentsDto> selectByClassLAndStatusAndSearch(String largeClass, String status, String search) {
		logger.debug("Contents Table selectByClassLAndStatusAndSearch Run");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("largeClass", largeClass);
		param.put("status", status);
		param.put("search", search);
		return mapper.selectByClassLAndStatusAndSearch(param);
	}

	public List<ContentsDto> selectByClassLMAndStatusAndSearch(String largeClass, String middleClass, String status,
			String search) {
		logger.debug("Contents Table selectByClassLAndStatusAndSearch Run");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("largeClass", largeClass);
		param.put("middleClass", middleClass);
		param.put("status", status);
		param.put("search", search);
		return mapper.selectByClassLAndStatusAndSearch(param);
	}

	public int insert(ContentsDto dto) {
		logger.debug("Contents Table insert Run");
		mapper.insert(dto);
		return dto.getSeqNo();
	}

	public int update(ContentsDto dto) {
		logger.debug("Contents Table update Run");
		return mapper.update(dto);
	}

	public int updateTheStatus(int seqNo, String status, String userId) {
		logger.debug("Contents Table updateTheStatus Run");
		ContentsDto dto = new ContentsDto();
		dto.setSeqNo(seqNo);
		dto.setStatus(status);

		int result = 0;
		if (status.equals(Const.CONTENTS_DELETE)) {
			dto.setDeleteUserId(userId);
			dto.setDeleteTime(new Timestamp(System.currentTimeMillis()));
			result = mapper.updateTheStatusByDelete(dto);
		} else {
			dto.setUpdateUserId(userId);
			dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			result = mapper.updateTheStatusByUpdate(dto);
		}

		return result;
	}

}
