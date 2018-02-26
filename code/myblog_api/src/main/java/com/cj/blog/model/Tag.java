package com.cj.blog.model;

public class Tag {
	private int tagId;
	private String tagName;
	private int tagSort;
	
	public Tag() {
	}

	public Tag(String tagName, int sort) {
		this.tagName = tagName;
		this.tagSort = sort;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getTagSort() {
		return tagSort;
	}

	public void setTagSort(int tagSort) {
		this.tagSort = tagSort;
	}

}
