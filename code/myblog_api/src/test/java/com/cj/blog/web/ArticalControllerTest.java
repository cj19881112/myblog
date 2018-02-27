package com.cj.blog.web;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cj.blog.model.Artical;
import com.cj.blog.service.ArticalService;
import com.cj.util.ApiRet;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticalController.class)
public class ArticalControllerTest {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ArticalService service;

	@Test
	public void testGetArticals_returnAll() throws Exception {
		List<Artical> articals = new LinkedList<>();
		articals.add(new Artical(4, "world", "/abc", "hello", "hello world", 1, "nice,job",
				sdf.parse("2018-01-01 00:00:02"), "0"));
		articals.add(new Artical(4, "world", "/abc", "hello", "hello world", 1, "nice,job",
				sdf.parse("2018-01-01 00:00:02"), "0"));
		when(service.getArticals(any(), any(), any(), any())).thenReturn(articals);
		when(service.countArtical(any(), any())).thenReturn(articals.size());

		mvc.perform(get("/api/artical/get_articals").param("page", "0").param("cnt", "-1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.total", is(articals.size()))).andExpect(jsonPath("$.code", is("0000")))
				.andExpect(jsonPath("$.data.length()", is(articals.size())))
				.andExpect(jsonPath("$.data[0].artTitle", is("world")));
		// TODO more matcher
	}

	@Test
	public void testGetArticals_illegalArgument() throws Exception {
		mvc.perform(get("/api/artical/get_articals")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));

		mvc.perform(get("/api/artical/get_articals").param("page", "0")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));

		mvc.perform(get("/api/artical/get_articals").param("cnt", "10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));

		mvc.perform(get("/api/artical/get_articals").param("page", "-1").param("cnt", "10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));

		mvc.perform(get("/api/artical/get_articals").param("page", "a").param("cnt", "0")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));

		mvc.perform(get("/api/artical/get_articals").param("page", "0").param("cnt", "b")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));
	}

	@Test
	public void testGetArticals_returnEmpty() throws Exception {
		when(service.getArticals(any(), any(), any(), any())).thenReturn(null);
		when(service.countArtical(any(), any())).thenReturn(0);

		mvc.perform(get("/api/artical/get_articals").param("page", "0").param("cnt", "-1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.total", is(0))).andExpect(jsonPath("$.code", is("0000")))
				.andExpect(jsonPath("$.data.length()", is(0)));
	}

}
