package com.cj.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cj.blog.dao.TagMapper;
import com.cj.blog.model.Tag;
import com.cj.blog.service.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagMapper tagMapper;

	/**
	 * 获取所有tag，如果不存在返回空的集合
	 */
	@Override
	public List<Tag> getTags() {
		List<Tag> tags = tagMapper.getTags();
		if (null == tags) {
			tags = new ArrayList<>();
		}
		return tags;
	}

}
