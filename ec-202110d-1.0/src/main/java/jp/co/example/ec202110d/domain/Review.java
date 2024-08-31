package jp.co.example.ec202110d.domain;

import java.util.Date;

/**
 * レビューを表すエンティティ.
 * 
 * @author NobutakaYoshida
 */
public class Review {

	/** id */
	private Integer id;

	/** 名前 */
	private String name;

	/** タイトル */
	private String title;

	/** 内容 */
	private String content;

	/** 商品ID */
	private Integer itemId;

	/** 役に立ったボタンの数 */
	private Integer likeCount;

	/** 違反を報告するボタンの数 */
	private Integer deleteCount;
	
	/** 投稿日 */
	private Date reviewPostDate;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getDeleteCount() {
		return deleteCount;
	}

	public void setDeleteCount(Integer deleteCount) {
		this.deleteCount = deleteCount;
	}

	public Date getReviewPostDate() {
		return reviewPostDate;
	}

	public void setReviewPostDate(Date reviewPostDate) {
		this.reviewPostDate = reviewPostDate;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", name=" + name + ", title=" + title + ", content=" + content + ", itemId="
				+ itemId + ", likeCount=" + likeCount + ", deleteCount=" + deleteCount + ", reviewPostDate="
				+ reviewPostDate + "]";
	}

	

}