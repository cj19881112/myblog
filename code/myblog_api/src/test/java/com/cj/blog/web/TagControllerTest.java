package com.cj.blog.web;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cj.blog.model.Tag;
import com.cj.blog.service.TagService;
import com.cj.util.ApiRet;

@RunWith(SpringRunner.class)
@WebMvcTest(TagController.class)
public class TagControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TagService tagService;

	@Test
	public void testGetTags_returnAll() throws Exception {

		// mock data [hello, world]
		List<Tag> tags = new LinkedList<>();
		tags.add(new Tag("hello", 0));
		tags.add(new Tag("world", 1));

		when(tagService.getTags()).thenReturn(tags);

		mvc.perform(get("/api/tag/get_tags")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.SUCC.getCode())))
				.andExpect(jsonPath("$.data[0].tagName", is("hello"))).andExpect(jsonPath("$.data[0].tagSort", is(0)))
				.andExpect(jsonPath("$.data[1].tagName", is("world"))).andExpect(jsonPath("$.data[1].tagSort", is(1)));
	}

	@Test
	public void testGetTags_returnEmpty() throws Exception {
		when(tagService.getTags()).thenReturn(null);

		mvc.perform(get("/api/tag/get_tags")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.SUCC.getCode())))
				.andExpect(jsonPath("$.data.length()", is(0)));
	}
}
