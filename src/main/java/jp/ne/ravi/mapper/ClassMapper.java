package jp.ne.ravi.mapper;

import java.util.List;

import jp.ne.ravi.dto.ClassDto;

public interface ClassMapper {

	int count(ClassDto dto);

	List<ClassDto> selectAll();

	int insert(ClassDto dto);

	int delete(ClassDto dto);
}
