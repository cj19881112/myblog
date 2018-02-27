package com.cj.blog.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

	@ExceptionHandler(IllegalArgumentException.class)
	public @ResponseBody ApiRet<Void> illegalArgumentExceptionHandler() {
		return ApiRet.err(ErrCode.ILLEGAL_ARGUMENT);
	}

	@ExceptionHandler(ArticalNotFoundException.class)
	public @ResponseBody ApiRet<Void> articalNotFoundException() {
		return ApiRet.err(ErrCode.NOT_FOUND);
	}
}
