package com.cj.blog.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.cj.blog.model.Tag;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TagMapperTest {

	@Autowired
	private TagMapper mapper;

	@Test
	public void testGetTags_returnMockData() {
		List<Tag> tags = mapper.getTags();

		assertThat(tags.get(0).getTagName()).isEqualTo("hi");
		assertThat(tags.get(0).getTagSort()).isEqualTo(0);

		assertThat(tags.get(1).getTagName()).isEqualTo("hello");
		assertThat(tags.get(1).getTagSort()).isEqualTo(1);
	}

}
