package com.cj.blog.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cj.blog.model.Artical;
import com.cj.blog.service.ArticalService;
import com.cj.util.ApiRet;
import com.cj.util.excep.ArticalNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticalController.class)
public class ArticalControllerTest {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ArticalService service;

	Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

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
				.andExpect(jsonPath("$.total", is(articals.size())))
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.SUCC.getCode())))
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

	/**
	 * 创建文章-正常
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_returnId() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(com.cj.util.Constants.SESSION_KEY, com.cj.util.Constants.SESSION_KEY);

		Artical artical = new Artical(null, "hello", "/abc", null, "hello world", null, "hi", null, null);
		Integer id = 1;
		when(service.createArtical(any())).thenReturn(id);

		mvc.perform(post("/api/artical/create_artical").contentType(MediaType.APPLICATION_JSON)
				.content(g.toJson(artical)).session(session)).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.SUCC.getCode()))).andExpect(jsonPath("$.data", is(id)));

		ArgumentCaptor<Artical> articalCaptor = ArgumentCaptor.forClass(Artical.class);
		verify(service).createArtical(articalCaptor.capture());

		assertThat(articalCaptor.getValue()).isEqualToIgnoringNullFields(artical);
	}

	/**
	 * 创建文章-未登录
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_noLogin() throws Exception {
		Artical artical = new Artical(null, "hello", "/abc", null, "hello world", null, "hi", null, null);

		mvc.perform(
				post("/api/artical/create_artical").contentType(MediaType.APPLICATION_JSON).content(g.toJson(artical)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.code", is(ApiRet.ErrCode.NOT_LOGIN.getCode())));
	}

	/**
	 * 创建文章 - 错误的参数
	 * 
	 * @param artical
	 *            模拟的数据
	 * @throws ParseException
	 * @throws Exception
	 */
	private void testCreateArtical_invalidArgument(Artical artical) throws ParseException, Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(com.cj.util.Constants.SESSION_KEY, com.cj.util.Constants.SESSION_KEY);

		Integer id = 1;
		when(service.createArtical(any())).thenReturn(id);

		mvc.perform(post("/api/artical/create_artical").contentType(MediaType.APPLICATION_JSON)
				.content(g.toJson(artical)).session(session)).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));
	}

	/**
	 * 创建文章-缺少title
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_errorNoTitle() throws Exception {
		testCreateArtical_invalidArgument(new Artical(null, null, "/abc", "hello", "hello world", 1, "hi",
				sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	/**
	 * 创建文章-title为空
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_emptyTitle() throws Exception {
		testCreateArtical_invalidArgument(
				new Artical(null, "", "/abc", "hello", "hello world", 1, "hi", sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	/**
	 * 创建文章-缺少imgUrl
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_errorNoImgUrl() throws Exception {
		testCreateArtical_invalidArgument(new Artical(null, "title", null, "hello", "hello world", 1, "hi",
				sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	/**
	 * 创建文章-imgUrl为空
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_errorEmptyImgUrl() throws Exception {
		testCreateArtical_invalidArgument(
				new Artical(null, "title", "", "hello", "hello world", 1, "hi", sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	/**
	 * 创建文章-缺少tag
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_errorNoTag() throws Exception {
		testCreateArtical_invalidArgument(new Artical(null, "title", "/url", null, "hello world", 1, null,
				sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	/**
	 * 创建文章-tag为空
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_errorEmptyTag() throws Exception {
		testCreateArtical_invalidArgument(
				new Artical(null, "title", "/url", "", "hello world", 1, "", sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	/**
	 * 创建文章-缺少content
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_errorNoContent() throws Exception {
		testCreateArtical_invalidArgument(
				new Artical(null, "title", "/url", null, null, 1, "hi", sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	/**
	 * 创建文章-content为空
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateArtical_errorEmptyContent() throws Exception {
		testCreateArtical_invalidArgument(
				new Artical(null, "title", "/url", "", "", 1, "hi", sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	/**
	 * 编辑文章成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateArtical_success() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(com.cj.util.Constants.SESSION_KEY, com.cj.util.Constants.SESSION_KEY);

		Artical artical = new Artical(4, "world", "/abc", null, "hello world", null, "nice,job", null, null);
		mvc.perform(post("/api/artical/update_artical").contentType(MediaType.APPLICATION_JSON)
				.content(g.toJson(artical)).session(session)).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.SUCC.getCode())));

		ArgumentCaptor<Artical> articalCaptor = ArgumentCaptor.forClass(Artical.class);
		verify(service).updateArtical(articalCaptor.capture());

		assertThat(articalCaptor.getValue()).isEqualToIgnoringNullFields(artical);
	}

	/**
	 * 编辑文章成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateArtical_notFound() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(com.cj.util.Constants.SESSION_KEY, com.cj.util.Constants.SESSION_KEY);

		doThrow(ArticalNotFoundException.class).when(service).updateArtical(any());

		Artical artical = new Artical(4, "world", "/abc", null, "hello world", null, "nice,job", null, null);

		mvc.perform(post("/api/artical/update_artical").contentType(MediaType.APPLICATION_JSON)
				.content(g.toJson(artical)).session(session)).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.NOT_FOUND.getCode())));
	}

	private void testUpdateArtical_illegalArgument(Artical artical) throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(com.cj.util.Constants.SESSION_KEY, com.cj.util.Constants.SESSION_KEY);

		mvc.perform(post("/api/artical/update_artical").contentType(MediaType.APPLICATION_JSON)
				.content(g.toJson(artical)).session(session)).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is(ApiRet.ErrCode.ILLEGAL_ARGUMENT.getCode())));
	}

	/**
	 * 编辑文章-未填写ID
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateArtical_notId() throws Exception {
		testUpdateArtical_illegalArgument(
				new Artical(null, "world", "/abc", null, "hello world", null, "nice,job", null, null));
	}

	/**
	 * 编辑文章-所有字段都是null
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateArtical_allFieldNull() throws Exception {
		testUpdateArtical_illegalArgument(new Artical(4, null, null, null, null, null, null, null, null));
	}

	/**
	 * 编辑文章-所有字段都是空的字符
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateArtical_allFieldEmpty() throws Exception {
		testUpdateArtical_illegalArgument(new Artical(4, null, null, null, null, null, null, null, null));
	}

	/**
	 * 编辑文章-所有字段有些empty有些null
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateArtical_allSomeNullSomeEmpty() throws Exception {
		testUpdateArtical_illegalArgument(new Artical(4, "", null, null, null, null, null, null, null));
	}
}
