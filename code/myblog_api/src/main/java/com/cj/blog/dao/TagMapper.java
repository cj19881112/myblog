package com.cj.blog.dao;

import java.util.List;

import com.cj.blog.model.Tag;

public interface TagMapper {
	/**
	 * 返回tag表中的内容
	 * 
	 * @return
	 */
	List<Tag> getTags();
}
