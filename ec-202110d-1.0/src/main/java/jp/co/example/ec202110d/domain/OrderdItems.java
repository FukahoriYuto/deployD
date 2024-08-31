package jp.co.example.ec202110d.domain;

public class OrderdItems {

	private Integer id;
	private Integer itemId;
	private Integer orderId;
	private Integer quantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderdItems [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity + "]";
	}

}
