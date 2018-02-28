package com.cj.blog.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.cj.blog.dao.ArticalMapper;
import com.cj.blog.model.Artical;
import com.cj.util.Constants;
import com.cj.util.DateUtil;
import com.cj.util.excep.ArticalNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ArticalServiceImplTest {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@InjectMocks
	private ArticalServiceImpl service;

	@Mock
	private ArticalMapper mockMapper;

	class TestGetArticalInput {
		List<Artical> articals;
		int page;
		int cnt;
		String tag;
		String keyword;

		public TestGetArticalInput(List<Artical> articals, int page, int cnt, String tag, String keyword) {
			this.articals = articals;
			this.page = page;
			this.cnt = cnt;
			this.tag = tag;
			this.keyword = keyword;
		}
	}

	class TestGetArticalOutput {
		List<Artical> articals;
		int offset;
		int limit;
		String tag;
		String keyword;

		public TestGetArticalOutput(List<Artical> articals, int offset, int limit, String tag, String keyword) {
			super();
			this.articals = articals;
			this.offset = offset;
			this.limit = limit;
			this.tag = tag;
			this.keyword = keyword;
		}
	}

	private void testGetArtical(TestGetArticalInput input, TestGetArticalOutput expectedOut) throws ParseException {
		when(mockMapper.getArticals(anyInt(), anyInt(), any(), any())).thenReturn(input.articals);

		List<Artical> articals = service.getArticals(input.page, input.cnt, input.tag, input.keyword);

		ArgumentCaptor<Integer> offsetCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> tagCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> keywordCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockMapper).getArticals(offsetCaptor.capture(), limitCaptor.capture(), tagCaptor.capture(),
				keywordCaptor.capture());

		assertThat(offsetCaptor.getValue()).isEqualTo(expectedOut.offset);
		assertThat(limitCaptor.getValue()).isEqualTo(expectedOut.limit);
		assertThat(tagCaptor.getValue()).isEqualTo(expectedOut.tag);
		assertThat(keywordCaptor.getValue()).isEqualTo(expectedOut.keyword);

		assertThat(articals.size()).isEqualTo(expectedOut.articals.size());
		for (int i = 0; i < expectedOut.articals.size(); i++) {
			assertThat(articals.get(i)).isEqualTo(expectedOut.articals.get(i));
		}
	}

	@Test
	public void testGetArticalPaged() throws ParseException {
		List<Artical> mockArticals = new LinkedList<>();
		mockArticals.add(new Artical(4, "world", "/abc", "hello", "hello world", 1, "nice,job",
				sdf.parse("2018-01-01 00:00:02"), "0"));
		mockArticals.add(new Artical(4, "world", "/abc", "hello", "hello world", 1, "nice,job",
				sdf.parse("2018-01-01 00:00:02"), "0"));
		TestGetArticalInput input = new TestGetArticalInput(mockArticals, 1, 2, null, null);
		TestGetArticalOutput output = new TestGetArticalOutput(mockArticals, 2, 2, null, null);
		testGetArtical(input, output);
	}

	@Test
	public void testGetArticalLikeTagAndKeyword() throws ParseException {
		List<Artical> mockArticals = new LinkedList<>();
		TestGetArticalInput input = new TestGetArticalInput(mockArticals, 1, 2, "ic", "or");
		TestGetArticalOutput output = new TestGetArticalOutput(mockArticals, 2, 2, "%ic%", "%or%");
		testGetArtical(input, output);
	}

	@Test
	public void testGetArtical_returnEmpty() throws ParseException {
		TestGetArticalInput input = new TestGetArticalInput(null, 0, -1, null, null);
		TestGetArticalOutput output = new TestGetArticalOutput(new ArrayList<>(), 0, -1, null, null);
		testGetArtical(input, output);
	}

	class TestCountArticalInputOut {
		String tag;
		String keyword;
		int total;

		public TestCountArticalInputOut(String tag, String keyword, int total) {
			super();
			this.tag = tag;
			this.keyword = keyword;
			this.total = total;
		}
	}

	private void testCountArtical(TestCountArticalInputOut in, TestCountArticalInputOut expectedOut) {
		when(mockMapper.countArtical(any(), any())).thenReturn(in.total);

		int total = service.countArtical(in.tag, in.keyword);

		ArgumentCaptor<String> tagCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> keyworkCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockMapper).countArtical(tagCaptor.capture(), keyworkCaptor.capture());

		assertThat(tagCaptor.getValue()).isEqualTo(expectedOut.tag);
		assertThat(keyworkCaptor.getValue()).isEqualTo(expectedOut.keyword);
		assertThat(total).isEqualTo(expectedOut.total);
	}

	@Test
	public void testCountArtical_noCond() {
		testCountArtical(new TestCountArticalInputOut(null, null, 10), new TestCountArticalInputOut(null, null, 10));
	}

	@Test
	public void testCountArtical_withCond() {
		testCountArtical(new TestCountArticalInputOut("ic", "ab", 1), new TestCountArticalInputOut("%ic%", "%ab%", 1));
	}

	@Test
	public void testGetArticalDetail_returnDetail() {
		int artId = 0;
		Artical mockArtical = new Artical(1, "artTitle", "artImgUrl", "artBrief", "artContent", 11, "artTags", null,
				"0");
		when(mockMapper.getArticalDetail(anyInt())).thenReturn(mockArtical);

		Artical artical = service.getArticalDetail(artId);

		ArgumentCaptor<Integer> artIdCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockMapper).getArticalDetail(artIdCaptor.capture());
		assertThat(artIdCaptor.getValue()).isEqualTo(artId);

		assertThat(artical).isEqualToComparingFieldByField(mockArtical);
	}

	@Test
	public void testGetArticalDetail_returnEmpty() {
		int artId = 0;
		when(mockMapper.getArticalDetail(anyInt())).thenReturn(null);

		Artical artical = service.getArticalDetail(artId);

		ArgumentCaptor<Integer> artIdCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockMapper).getArticalDetail(artIdCaptor.capture());
		assertThat(artIdCaptor.getValue()).isEqualTo(artId);

		assertThat(artical).isNull();
	}

	/**
	 * 测试创建文章，生成对应的字段
	 */
	@Test
	public void testCreateArtical_returnId() {
		Integer mockId = 11;
		String content = "very very long content string very very long content string very very long content string ";
		Artical artical = new Artical(null, "hello", "/abc", null, content, null, "hi", null, null);
		when(mockMapper.saveArtical(any())).thenAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				invocation.getArgumentAt(0, Artical.class).setArtId(mockId);
				return 1;
			}
		});

		Integer id = service.createArtical(artical);

		ArgumentCaptor<Artical> articalCaptor = ArgumentCaptor.forClass(Artical.class);
		verify(mockMapper).saveArtical(articalCaptor.capture());
		assertThat(id).isEqualTo(mockId);
		Artical articalExpected = new Artical(null, "hello", "/abc", service.getBrief(content, Constants.BRIEF_LENGTH),
				content, 0, "hi", null, "0");
		assertThat(articalCaptor.getValue()).isEqualToIgnoringNullFields(articalExpected);
	}

	/**
	 * 测试编辑，修改对应字段
	 * 
	 * @throws ArticalNotFoundException
	 */
	@Test
	public void testUpdateArtical_success() throws ArticalNotFoundException {
		String content = "very very nice content string very very long content string very very long content string ";
		Artical artical = new Artical(4, "hello", "/abc", "asdf", content, 2, "hi", DateUtil.now(), "0");
		when(mockMapper.updateArtical(any())).thenReturn(1);

		service.updateArtical(artical);

		ArgumentCaptor<Artical> articalCaptor = ArgumentCaptor.forClass(Artical.class);
		verify(mockMapper).updateArtical(articalCaptor.capture());

		Artical articalExpected = new Artical(4, "hello", "/abc", service.getBrief(content, Constants.BRIEF_LENGTH),
				content, null, "hi", null, null);
		assertThat(articalCaptor.getValue()).isEqualToComparingFieldByField(articalExpected);
	}

	/**
	 * 测试编辑，修改对应字段
	 * 
	 * @throws ArticalNotFoundException
	 */
	@Test(expected = ArticalNotFoundException.class)
	public void testUpdateArtical_notFound() throws ArticalNotFoundException {
		Artical artical = new Artical(11, "hello", "/abc", null, "", null, "hi", null, null);
		when(mockMapper.saveArtical(any())).thenReturn(0);

		service.updateArtical(artical);
	}

	/**
	 * 测试根据文章内容获取简短描述
	 */
	@Test
	public void testGetBrief_returnBreif() {
		assertThat(service.getBrief("hello", 5)).isEqualTo("hello");
		assertThat(service.getBrief("hello", 2)).isEqualTo("he");
		assertThat(service.getBrief("hello", 32)).isEqualTo("hello");
		assertThat(service.getBrief("", 2)).isEqualTo("");
		assertThat(service.getBrief(null, 2)).isEqualTo("");
		assertThat(service.getBrief("", -2)).isEqualTo("");
		assertThat(service.getBrief("", 0)).isEqualTo("");
	}
}
