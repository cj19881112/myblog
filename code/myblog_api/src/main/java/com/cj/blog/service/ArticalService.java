package com.cj.blog.service;

import java.util.List;

import com.cj.blog.model.Artical;

public interface ArticalService {

	public List<Artical> getArticals(Integer page, Integer cnt, String tag, String keyword);

	public int countArtical(String tag, String keyword);

}
