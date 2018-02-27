package com.cj.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cj.blog.model.Artical;

public interface ArticalMapper {

	int countArtical(@Param("tag") String tag, @Param("keyword") String keyword);

	List<Artical> getArticals(@Param("offset") int offset, @Param("limit") int limit, @Param("tag") String tag,
			@Param("keyword") String keyword);

	Artical getArticalDetail(@Param("artId") Integer artId);

}
