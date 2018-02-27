package com.cj.blog.service;

import java.util.List;

import com.cj.blog.model.Artical;

public interface ArticalService {

	/**
	 * 获取文章列表，根据tag，关键字进行模糊查询
	 * 
	 * @param page
	 *            页码
	 * @param cnt
	 *            每页条目
	 * @param tag
	 *            标签
	 * @param keyword
	 *            关键字
	 * @return 如果存在返回文章列表，否则返回空的集合
	 */
	public List<Artical> getArticals(Integer page, Integer cnt, String tag, String keyword);

	/**
	 * 统计文章条数，根据tag，关键字模糊查询
	 * 
	 * @param tag
	 *            标签
	 * @param keyword
	 *            关键字
	 * @return 匹配到的条目的数量
	 */
	public int countArtical(String tag, String keyword);

	/**
	 * 获取文章详情
	 * 
	 * @param artId
	 *            文章ID
	 * @return 文章详情，如果不存在返回null
	 */
	public Artical getArticalDetail(int artId);

}
