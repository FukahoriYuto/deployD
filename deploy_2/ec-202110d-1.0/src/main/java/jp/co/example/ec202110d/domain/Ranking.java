package jp.co.example.ec202110d.domain;



public class Ranking {
	
	private Integer itemId;
	private Item item;
	
	
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	@Override
	public String toString() {
		return "Ranking [itemId=" + itemId + ", item=" + item + "]";
	}
	
	
}
