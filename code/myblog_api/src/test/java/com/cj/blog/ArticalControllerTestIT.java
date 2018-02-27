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
import com.cj.blog.model.Artical;
import com.cj.util.ApiRet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticalControllerTestIT {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private TestRestTemplate rest;

	@Test
	public void testGetArtical_returnAll() throws ParseException {

		ResponseEntity<ApiRet<List<Artical>>> artical = new RestHelper(rest).getForEntity(
				"/api/artical/get_articals?page=0&cnt=-1", new ParameterizedTypeReference<ApiRet<List<Artical>>>() {
				});

		assertThat(artical.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());
		assertThat(artical.getBody().getData().size()).isEqualTo(3);
		assertThat(artical.getBody().getData().get(0)).isEqualToComparingFieldByField(new Artical(4, "world", "/abc",
				"hello", "hello world", 1, "nice,job", sdf.parse("2018-01-01 00:00:02"), "0"));
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

}
