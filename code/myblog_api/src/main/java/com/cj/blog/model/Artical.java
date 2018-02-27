package com.cj.blog.model;

import java.util.Date;

public class Artical {
	private int artId;
	private String artTitle;
	private String artImgUrl;
	private String artBrief;
	private String artContent;
	private int artReadCnt;
	private String artTags;
	private Date artCreatedAt;
	private String artIsDel;

	public Artical() {
	}

	public Artical(int artId, String artTitle, String artImgUrl, String artBrief, String artContent, int artReadCnt,
			String artTags, Date artCreatedAt, String artIsDel) {
		super();
		this.artId = artId;
		this.artTitle = artTitle;
		this.artImgUrl = artImgUrl;
		this.artBrief = artBrief;
		this.artContent = artContent;
		this.artReadCnt = artReadCnt;
		this.artTags = artTags;
		this.artCreatedAt = artCreatedAt;
		this.artIsDel = artIsDel;
	}

	public int getArtId() {
		return artId;
	}

	public void setArtId(int artId) {
		this.artId = artId;
	}

	public String getArtTitle() {
		return artTitle;
	}

	public void setArtTitle(String artTitle) {
		this.artTitle = artTitle;
	}

	public String getArtImgUrl() {
		return artImgUrl;
	}

	public void setArtImgUrl(String artImgUrl) {
		this.artImgUrl = artImgUrl;
	}

	public String getArtBrief() {
		return artBrief;
	}

	public void setArtBrief(String artBrief) {
		this.artBrief = artBrief;
	}

	public String getArtContent() {
		return artContent;
	}

	public void setArtContent(String artContent) {
		this.artContent = artContent;
	}

	public int getArtReadCnt() {
		return artReadCnt;
	}

	public void setArtReadCnt(int artReadCnt) {
		this.artReadCnt = artReadCnt;
	}

	public String getArtTags() {
		return artTags;
	}

	public void setArtTags(String artTags) {
		this.artTags = artTags;
	}

	public Date getArtCreatedAt() {
		return artCreatedAt;
	}

	public void setArtCreatedAt(Date artCreatedAt) {
		this.artCreatedAt = artCreatedAt;
	}

	public String getArtIsDel() {
		return artIsDel;
	}

	public void setArtIsDel(String artIsDel) {
		this.artIsDel = artIsDel;
	}
}
