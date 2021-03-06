package com.cj.blog;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cj.RestHelper;
import com.cj.blog.dao.ArticalMapper;
import com.cj.blog.model.Artical;
import com.cj.sys.SysConfigControllerIT;
import com.cj.util.ApiRet;
import com.cj.util.excep.IllegalCaptchaModeException;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticalControllerTestIT {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private TestRestTemplate rest;

	@Autowired
	private ArticalMapper mapper;

	@Test
	public void testGetArtical_returnAll() throws ParseException {

		ResponseEntity<ApiRet<List<Artical>>> artical = new RestHelper(rest).getForEntity(
				"/api/artical/get_articals?page=0&cnt=-1", new ParameterizedTypeReference<ApiRet<List<Artical>>>() {
				});

		assertThat(artical.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());
		assertThat(artical.getBody().getData().size()).isEqualTo(3);
		assertThat(artical.getBody().getData().get(1)).isEqualToComparingFieldByField(new Artical(3, "world", "/abc",
				"hello", "hello world", 1, "hi", sdf.parse("2018-01-01 00:00:01"), "0"));
		assertThat(artical.getBody().getData().get(2)).isEqualToComparingFieldByField(new Artical(2, "hello", "/abc",
				"hello", "hello world", 1, "hi", sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	@Test
	public void testGetArticalDetail_returnDetail() throws ParseException {

		ResponseEntity<ApiRet<Artical>> artical = new RestHelper(rest).getForEntity(
				"/api/artical/get_artical_detail?artId=2", new ParameterizedTypeReference<ApiRet<Artical>>() {
				});
		Artical expectedArtical = new Artical(2, "hello", "/abc", "hello", "hello world", 1, "hi",
				sdf.parse("2018-01-01 00:00:00"), "0");
		assertThat(artical.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());
		assertThat(artical.getBody().getData()).isEqualToComparingFieldByField(expectedArtical);
		assertThat(artical.getBody().getData()).isNotNull();
	}

	/**
	 * 创建文章-集成测试
	 * 
	 * @throws ParseException
	 * @throws IllegalCaptchaModeException
	 */
	@Test
	public void testCreateArtical_returnId() throws ParseException, IllegalCaptchaModeException {
		RestHelper restHelper = new RestHelper(rest);

		// 先登录
		SysConfigControllerIT.testLogin_returnSuccess(restHelper);

		// 测试
		Artical artical = new Artical(null, "hello", "/abc", "hello", "hello world", null, "hi", null, null);
		ResponseEntity<ApiRet<Integer>> ret = restHelper.postForEntity("/api/artical/create_artical",
				new Gson().toJson(artical), new ParameterizedTypeReference<ApiRet<Integer>>() {
				});
		assertThat(ret.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());
		assertThat(ret.getBody().getData()).isGreaterThan(0);

		mapper.removeArtical(ret.getBody().getData());
	}

	/**
	 * 编辑文章-集成测试
	 * 
	 * @throws IllegalCaptchaModeException
	 */
	@Test
	public void testUpdateArtical_returnOk() throws IllegalCaptchaModeException {
		RestHelper restHelper = new RestHelper(rest);

		ResponseEntity<ApiRet<Artical>> originArtical = new RestHelper(rest).getForEntity(
				"/api/artical/get_artical_detail?artId=4", new ParameterizedTypeReference<ApiRet<Artical>>() {
				});

		// 先登录
		SysConfigControllerIT.testLogin_returnSuccess(restHelper);

		// 测试
		Artical artical = new Artical(4, "world", "/abc", null, "hello world", null, "nice,job", null, null);
		ResponseEntity<ApiRet<Void>> ret = restHelper.postForEntity("/api/artical/update_artical",
				new Gson().toJson(artical), new ParameterizedTypeReference<ApiRet<Void>>() {
				});
		assertThat(ret.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());

		// 还原数据
		restHelper.postForEntity("/api/artical/update_artical", new Gson().toJson(originArtical),
				new ParameterizedTypeReference<ApiRet<Void>>() {
				});
	}

	/**
	 * 删除文章-集成测试
	 * 
	 * @throws IllegalCaptchaModeException
	 */
	@Test
	public void testRemoveArtical_returnOk() throws IllegalCaptchaModeException {
		RestHelper restHelper = new RestHelper(rest);

		// 先登录
		SysConfigControllerIT.testLogin_returnSuccess(restHelper);

		// 创建数据
		Artical artical = new Artical(null, "hello", "/abc", "hello", "hello world", null, "hi", null, null);
		ResponseEntity<ApiRet<Integer>> ret = restHelper.postForEntity("/api/artical/create_artical",
				new Gson().toJson(artical), new ParameterizedTypeReference<ApiRet<Integer>>() {
				});

		// 测试删除
		ResponseEntity<ApiRet<Void>> delRet = restHelper.postForEntity(
				"/api/artical/remove_artical?artId=" + ret.getBody().getData(),
				new ParameterizedTypeReference<ApiRet<Void>>() {
				});
		assertThat(delRet.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());
	}
}
