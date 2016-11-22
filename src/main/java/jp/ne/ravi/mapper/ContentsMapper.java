package jp.ne.ravi.mapper;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import jp.ne.ravi.dto.ContentsDto;

public interface ContentsMapper {

	int countByStatus(String status);

	int countByExceptStatus(String status);

	List<ContentsDto> selectAll();

	Date selectMaxUploadDate();

	ContentsDto selectBySeqNo(int seqNo);

	List<ContentsDto> selectByStatus(String status);

	List<ContentsDto> selectByExceptStatus(String status);

	List<ContentsDto> selectByExceptStatusWithPagingForOracle(Map<String, Object> param);

	List<ContentsDto> selectByExceptStatusWithPagingForPostgres(Map<String, Object> param);

	List<ContentsDto> selectByClassLAndStatus(ContentsDto dto);

	List<ContentsDto> selectByClassLMAndStatus(ContentsDto dto);

	List<ContentsDto> selectByClassLMSAndStatus(ContentsDto dto);

	List<ContentsDto> selectByClassLAndExceptStatus(ContentsDto dto);

	List<ContentsDto> selectByClassLMAndExceptStatus(ContentsDto dto);

	List<ContentsDto> selectByClassLMSAndExceptStatus(ContentsDto dto);

	List<ContentsDto> selectByClassLAndStatusAndSearch(Map<String, Object> param);

	List<ContentsDto> selectByClassLMAndStatusAndSearch(Map<String, Object> param);

	int insert(ContentsDto dto);

	int update(ContentsDto dto);

	int updateTheStatusByUpdate(ContentsDto dto);

	int updateTheStatusByDelete(ContentsDto dto);
}
