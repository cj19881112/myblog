package com.cj.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cj.blog.model.Artical;

public interface ArticalMapper {

	/**
	 * 统计文章总数，根据tag，关键字模糊查询，统计结果不包含已经删除的文章
	 * 
	 * @param tag
	 *            标签
	 * @param keyword
	 *            关键字
	 * @return 条数
	 */
	int countArtical(@Param("tag") String tag, @Param("keyword") String keyword);

	/**
	 * 分页获取文章表的内容，根据tag与关键字匹配，查询的结果不包含已经删除的文章
	 * 
	 * @param offset
	 *            mysql-offset
	 * @param limit
	 *            mysql-limit
	 * @param tag
	 *            标签
	 * @param keyword
	 *            关键字
	 * @return 文章列表，不存在返回null
	 */
	List<Artical> getArticals(@Param("offset") int offset, @Param("limit") int limit, @Param("tag") String tag,
			@Param("keyword") String keyword);

	/**
	 * 获取文章详情
	 * 
	 * @param artId
	 *            文章ID
	 * @return 文章详情，未找到返回null
	 */
	Artical getArticalDetail(@Param("artId") Integer artId);

}
