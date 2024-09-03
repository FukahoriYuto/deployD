package jp.co.example.ec202110d.form;

import java.util.List;

public class ItemDetailForm {
	
	private Integer userId;
	private Integer itemId;
	private Integer totalPrice;
	private List<Integer> optionList;
	
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
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<Integer> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<Integer> optionList) {
		this.optionList = optionList;
	}
	
	@Override
	public String toString() {
		return "ItemDetailForm [userId=" + userId + ", itemId=" + itemId + ", totalPrice=" + totalPrice
				+ ", optionList=" + optionList + "]";
	}
	

}
