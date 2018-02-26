package com.cj.blog.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.cj.blog.dao.TagMapper;
import com.cj.blog.model.Tag;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceImplTest {

	@InjectMocks
	private TagServiceImpl servcie;

	@Mock
	private TagMapper mapper;

	@Test
	public void testGetTags_returnAll() {
		List<Tag> mockTags = new LinkedList<>();
		mockTags.add(new Tag("hello", 0));
		mockTags.add(new Tag("world", 1));
		when(mapper.getTags()).thenReturn(mockTags);

		List<Tag> tags = servcie.getTags();

		assertThat(tags.size()).isEqualTo(mockTags.size());
		for (int i = 0; i < mockTags.size(); i++) {
			assertThat(tags.get(i).getTagName()).isEqualTo(mockTags.get(i).getTagName());
			assertThat(tags.get(i).getTagSort()).isEqualTo(mockTags.get(i).getTagSort());
		}
	}

	@Test
	public void testGetTags_returnEmpty() {
		when(mapper.getTags()).thenReturn(null);

		List<Tag> tags = servcie.getTags();
		assertThat(tags.size()).isEqualTo(0);
	}
}
