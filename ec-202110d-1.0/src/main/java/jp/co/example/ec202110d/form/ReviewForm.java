package jp.co.example.ec202110d.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * レビューのリクエストパラメータが入るフォーム.
 * 
 * @author NobutakaYoshida
 *
 */
public class ReviewForm {

	/** ID. */
	private Integer id;

	/** レビュー者名. */
	@NotBlank(message = "名前を入力してください")
	@Size(min = 1, max = 20, message = "名前は20字以内で入力してください")
	private String name;

	/** レビューのタイトル. */
	@NotBlank(message = "タイトルを入力してください")
	@Size(min = 1, max = 15, message = "タイトルは15字以内で入力してください")
	private String title;

	/** レビュー内容. */
	@NotBlank(message = "レビューを入力してください")
	@Size(min = 1, max = 800, message = "レビューは800字以内で入力してください")
	private String content;

	/** 商品ID. */
	private Integer itemId;

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

	@Override
	public String toString() {
		return "ReviewForm [id=" + id + ", name=" + name + ", title=" + title + ", content=" + content + ", itemId="
				+ itemId + "]";
	}

}