package jp.co.example.ec202110d.domain;

import java.util.List;

public class Item {

	private Integer id;
	private String name;
	private String description;
	private Integer price;
	private String imagePath1;
	private String imagePath2;
	private String imagePath3;
	private List<Review> reviewList;
	private List<Item> itemList;
	// private List<Ranking> rankingList;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImagePath1() {
		return imagePath1;
	}

	public void setImagePath1(String imagePath1) {
		this.imagePath1 = imagePath1;
	}

	public String getImagePath2() {
		return imagePath2;
	}

	public void setImagePath2(String imagePath2) {
		this.imagePath2 = imagePath2;
	}

	public String getImagePath3() {
		return imagePath3;
	}

	public void setImagePath3(String imagePath3) {
		this.imagePath3 = imagePath3;
	}

	public List<Review> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<Review> reviewList) {
		this.reviewList = reviewList;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}


	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", imagePath1=" + imagePath1 + ", imagePath2=" + imagePath2 + ", imagePath3=" + imagePath3
				+ ", reviewList=" + reviewList + "]";
	}
}
