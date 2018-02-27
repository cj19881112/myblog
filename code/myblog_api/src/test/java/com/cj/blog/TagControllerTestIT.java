package com.cj.blog;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.cj.blog.model.Tag;
import com.cj.util.ApiRet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TagControllerTestIT {

	@Autowired
	private TestRestTemplate rest;

	@Test
	public void testGetTags_returnTags() {
		ResponseEntity<ApiRet<List<Tag>>> tags = new RestHelper(rest).getForEntity("/api/tag/get_tags",
				new ParameterizedTypeReference<ApiRet<List<Tag>>>() {
				});
		assertThat(tags.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());
		assertThat(tags.getBody().getData().size()).isGreaterThan(0);
		assertThat(tags.getBody().getData().get(0).getTagName()).isEqualTo("hi");
		assertThat(tags.getBody().getData().get(0).getTagSort()).isEqualTo(0);
		assertThat(tags.getBody().getData().get(1).getTagName()).isEqualTo("hello");
		assertThat(tags.getBody().getData().get(1).getTagSort()).isEqualTo(1);
	}

}
