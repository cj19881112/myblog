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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artBrief == null) ? 0 : artBrief.hashCode());
		result = prime * result + ((artContent == null) ? 0 : artContent.hashCode());
		result = prime * result + ((artCreatedAt == null) ? 0 : artCreatedAt.hashCode());
		result = prime * result + artId;
		result = prime * result + ((artImgUrl == null) ? 0 : artImgUrl.hashCode());
		result = prime * result + ((artIsDel == null) ? 0 : artIsDel.hashCode());
		result = prime * result + artReadCnt;
		result = prime * result + ((artTags == null) ? 0 : artTags.hashCode());
		result = prime * result + ((artTitle == null) ? 0 : artTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artical other = (Artical) obj;
		if (artBrief == null) {
			if (other.artBrief != null)
				return false;
		} else if (!artBrief.equals(other.artBrief))
			return false;
		if (artContent == null) {
			if (other.artContent != null)
				return false;
		} else if (!artContent.equals(other.artContent))
			return false;
		if (artCreatedAt == null) {
			if (other.artCreatedAt != null)
				return false;
		} else if (!artCreatedAt.equals(other.artCreatedAt))
			return false;
		if (artId != other.artId)
			return false;
		if (artImgUrl == null) {
			if (other.artImgUrl != null)
				return false;
		} else if (!artImgUrl.equals(other.artImgUrl))
			return false;
		if (artIsDel == null) {
			if (other.artIsDel != null)
				return false;
		} else if (!artIsDel.equals(other.artIsDel))
			return false;
		if (artReadCnt != other.artReadCnt)
			return false;
		if (artTags == null) {
			if (other.artTags != null)
				return false;
		} else if (!artTags.equals(other.artTags))
			return false;
		if (artTitle == null) {
			if (other.artTitle != null)
				return false;
		} else if (!artTitle.equals(other.artTitle))
			return false;
		return true;
	}
}
