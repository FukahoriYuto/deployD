package jp.co.example.ec202110d.domain;

import java.util.List;

public class Cart {

	private Integer id;
	private Integer userId;
	private Integer itemId;
	private Integer price;
	private List<CartOption> cartOptionList;
	private Item item;
	private List<Option> optionList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<CartOption> getCartOptionList() {
		return cartOptionList;
	}

	public void setCartOptionList(List<CartOption> cartOptionList) {
		this.cartOptionList = cartOptionList;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Option> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", userId=" + userId + ", itemId=" + itemId + ", price=" + price + ", cartOptionList="
				+ cartOptionList + ", item=" + item + ", optionList=" + optionList + "]";
	}

}
