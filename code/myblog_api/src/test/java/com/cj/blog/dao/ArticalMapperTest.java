package com.cj.blog.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.cj.blog.model.Artical;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticalMapperTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ArticalMapper mapper;

	@Test
	public void testGetArtical_returnAll() throws ParseException {
		List<Artical> articals = mapper.getArticals(0, -1, null, null);
		assertThat(articals.size()).isEqualTo(3);
		assertThat(articals.get(0)).isEqualToComparingFieldByField(new Artical(4, "world", "/abc", "hello",
				"hello world", 1, "nice,job", sdf.parse("2018-01-01 00:00:02"), "0"));
		assertThat(articals.get(1)).isEqualToComparingFieldByField(new Artical(3, "world", "/abc", "hello",
				"hello world", 1, "hi", sdf.parse("2018-01-01 00:00:01"), "0"));
		assertThat(articals.get(2)).isEqualToComparingFieldByField(new Artical(2, "hello", "/abc", "hello",
				"hello world", 1, "hi", sdf.parse("2018-01-01 00:00:00"), "0"));
	}

	@Test
	public void testGetArtical_returnPage() {
		List<Artical> articals = mapper.getArticals(0, 2, null, null);
		assertThat(articals.size()).isEqualTo(2);
	}

	@Test
	public void testGetArtical_likeTagPaged() {
		List<Artical> articals = mapper.getArticals(0, 2, "%hi%", null);
		assertThat(articals.size()).isEqualTo(2);
	}

	@Test
	public void testGetArtical_likeKeywordPaged() {
		List<Artical> articals = mapper.getArticals(0, 2, null, "%wor%");
		assertThat(articals.size()).isEqualTo(2);
	}

	@Test
	public void testGetArtical_likeTagAndKeywordPaged() {
		List<Artical> articals = mapper.getArticals(0, 2, "%h%", "%wor%");
		assertThat(articals.size()).isEqualTo(1);
	}

	@Test
	public void testCountArtical_returnAll() {
		int total = mapper.countArtical(null, null);
		assertThat(total).isEqualTo(3);
	}

	@Test
	public void testCountArtical_likeTag() {
		int total = mapper.countArtical("%h%", null);
		assertThat(total).isEqualTo(2);
	}

	@Test
	public void testCountArtical_likeKeyword() {
		int total = mapper.countArtical(null, "%wor%");
		assertThat(total).isEqualTo(2);
	}

	@Test
	public void testCountArtical_likeTagAndKeyword() {
		int total = mapper.countArtical("%h%", "%wor%");
		assertThat(total).isEqualTo(1);
	}

	@Test
	public void testGetArticalDetail_returnDetail() throws ParseException {
		Artical expectedArtical = new Artical(2, "hello", "/abc", "hello", "hello world", 1, "hi",
				sdf.parse("2018-01-01 00:00:00"), "0");
		Artical artical = mapper.getArticalDetail(2);
		assertThat(artical).isEqualToComparingFieldByField(expectedArtical);
	}

	@Test
	public void testGetArticalDetail_returnNull() {
		Artical artical = mapper.getArticalDetail(1000);
		assertThat(artical).isNull();
	}
}
