package com.cj.blog.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
				.andExpect(jsonPath("$.total", is(articals.size()))).andExpect(jsonPath("$.code", is(ApiRet.ErrCode.SUCC.getCode())))
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
				.andExpect(jsonPath("$.total", is(0))).andExpect(jsonPath("$.code", is(ApiRet.ErrCode.SUCC.getCode())))
				.andExpect(jsonPath("$.data.length()", is(0)));
	}

	@Test
	public void testGetArticalDetail_returnDetail() throws Exception {
		Artical artical = new Artical(1, "artTitle", "artImgUrl", "artBrief", "artContent", 11, "artTags", null, "0");
		when(service.getArticalDetail(anyInt())).thenReturn(artical);

		mvc.perform(get("/api/artical/get_artical_detail").param("artId", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.SUCC.getCode())))
				.andExpect(jsonPath("$.data.artId", is(1))).andExpect(jsonPath("$.data.artTitle", is("artTitle")))
				.andExpect(jsonPath("$.data.artImgUrl", is("artImgUrl")))
				.andExpect(jsonPath("$.data.artBrief", is("artBrief")))
				.andExpect(jsonPath("$.data.artContent", is("artContent")))
				.andExpect(jsonPath("$.data.artReadCnt", is(11))).andExpect(jsonPath("$.data.artTags", is("artTags")))
				// .andExpect(jsonPath("$.data.artCreatedAt").doesNotExist())
				.andExpect(jsonPath("$.data.artIsDel", is("0")));

		ArgumentCaptor<Integer> artIdCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(service).getArticalDetail(artIdCaptor.capture());
		assertThat(artIdCaptor.getValue()).isEqualTo(1);
	}

	@Test
	public void testGetArticalDetail_throwIllegaArgument() throws Exception {
		mvc.perform(get("/api/artical/get_artical_detail")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));
	}

	@Test
	public void testGetArticalDetail_throwNotFound() throws Exception {
		when(service.getArticalDetail(anyInt())).thenReturn(null);

		mvc.perform(get("/api/artical/get_artical_detail").param("artId", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.NOT_FOUND.getCode())));
	}
}
