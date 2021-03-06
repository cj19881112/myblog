package com.cj.blog.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cj.blog.model.Tag;
import com.cj.blog.service.TagService;
import com.cj.util.ApiRet;

@Controller
@RequestMapping("/api/tag")
public class TagController {

	@Autowired
	private TagService tagService;

	/**
	 * 获取系统的所有tag
	 * @return 如果存在返回tag列表，不存在返回空的列表
	 */
	@RequestMapping("/get_tags")
	public @ResponseBody ApiRet<List<Tag>> getTags() {
		List<Tag> tags = tagService.getTags();
		if (tags == null) {
			tags = new ArrayList<>();
		}
		return ApiRet.ok(tags);
	}
}
