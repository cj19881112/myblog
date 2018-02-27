package com.cj.blog.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cj.blog.model.Artical;
import com.cj.blog.service.ArticalService;
import com.cj.util.ApiRet;
import com.cj.util.ArticalNotFoundException;
import com.cj.util.ApiRet.ErrCode;

@Controller
@RequestMapping("/api/artical")
public class ArticalController {

	@Autowired
	private ArticalService articalService;

	/**
	 * 获取文章列表
	 * 
	 * @param page
	 *            页码
	 * @param cnt
	 *            每页的条目数
	 * @param tag
	 *            需要所搜的tag
	 * @param keyword
	 *            需要搜索的关键字
	 * @return 如果找到文章返回文章列表，没找到返回空的集合
	 */
	@RequestMapping("/get_articals")
	public @ResponseBody ApiRet<List<Artical>> getArticals(Integer page, Integer cnt, String tag, String keyword) {
		if (page == null || cnt == null || page < 0 || (cnt < 0 && cnt != -1)) {
			throw new IllegalArgumentException();
		}

		int totalRows = articalService.countArtical(tag, keyword);
		List<Artical> articals = articalService.getArticals(page, cnt, tag, keyword);
		if (null == articals) {
			articals = new ArrayList<>();
		}

		return ApiRet.ok(totalRows, articals);
	}

	/**
	 * 获取文章详情
	 * 
	 * @param artId
	 *            文章的ID
	 * @return 文章详情
	 * @throws ArticalNotFoundException
	 *             未找到文章
	 */
	@RequestMapping("/get_artical_detail")
	public @ResponseBody ApiRet<Artical> getArticalDetail(Integer artId) throws ArticalNotFoundException {
		if (null == artId) {
			throw new IllegalArgumentException();
		}

		Artical artical = articalService.getArticalDetail(artId);
		if (null == artical) {
			throw new ArticalNotFoundException();
		}

		return ApiRet.ok(artical);
	}

	/**
	 * 创建文章
	 * 
	 * @param artical
	 *            文章内容
	 * @return 生成的文章的ID
	 */
	@RequestMapping("/create_artical")
	public @ResponseBody ApiRet<Integer> createArtical(@RequestBody Artical artical) {
		if (StringUtils.isEmpty(artical.getArtTitle()) || StringUtils.isEmpty(artical.getArtImgUrl())
				|| StringUtils.isEmpty(artical.getArtTags()) || StringUtils.isEmpty(artical.getArtContent())) {
			throw new IllegalArgumentException();
		}
		return ApiRet.ok(articalService.createArtical(artical));
	}

	/**
	 * 处理未找到文章异常
	 * 
	 * @return 对应的错误码
	 */
	@ExceptionHandler(ArticalNotFoundException.class)
	public @ResponseBody ApiRet<Void> articalNotFoundException() {
		return ApiRet.err(ErrCode.NOT_FOUND);
	}
}
