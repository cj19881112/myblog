package com.cj.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cj.blog.dao.ArticalMapper;
import com.cj.blog.model.Artical;
import com.cj.blog.service.ArticalService;
import com.cj.util.SqlUtil;

@Service
public class ArticalServiceImpl implements ArticalService {
	@Autowired
	private ArticalMapper mapper;

	@Override
	public List<Artical> getArticals(Integer page, Integer cnt, String tag, String keyword) {
		List<Artical> articals = mapper.getArticals(page * cnt, cnt, SqlUtil.like(tag), SqlUtil.like(keyword));
		if (null == articals) {
			articals = new ArrayList<>();
		}
		return articals;
	}

	@Override
	public int countArtical(String tag, String keyword) {
		return mapper.countArtical(SqlUtil.like(tag), SqlUtil.like(keyword));
	}
}
