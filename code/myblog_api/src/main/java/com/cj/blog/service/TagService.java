package com.cj.blog.service;

import java.util.List;

import com.cj.blog.model.Tag;

public interface TagService {
	/**
	 * 获取系统所有的tag
	 * 
	 * @return 如果存在返回列表，否则返回空的集合
	 */
	List<Tag> getTags();
}
