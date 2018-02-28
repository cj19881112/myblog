package com.cj.blog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cj.blog.dao.ArticalMapper;
import com.cj.blog.model.Artical;
import com.cj.blog.model.EnumBoolean;
import com.cj.blog.service.ArticalService;
import com.cj.util.Constants;
import com.cj.util.SqlUtil;

@Service
@Transactional
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

	@Override
	public Artical getArticalDetail(int artId) {
		return mapper.getArticalDetail(artId);
	}

	@Override
	public Integer createArtical(Artical artical) {
		artical.setArtBrief(getBrief(artical.getArtContent(), Constants.BRIEF_LENGTH));
		artical.setArtCreatedAt(new Date());
		artical.setArtIsDel(EnumBoolean.FALSE.getCode());
		artical.setArtReadCnt(0);
		mapper.saveArtical(artical);
		return artical.getArtId();
	}

	@Override
	public String getBrief(String content, int briefLength) {
		if (StringUtils.isEmpty(content) || briefLength <= 0) {
			return "";
		}

		if (briefLength >= content.length()) {
			return content;
		}

		return content.substring(0, briefLength);
	}
}
