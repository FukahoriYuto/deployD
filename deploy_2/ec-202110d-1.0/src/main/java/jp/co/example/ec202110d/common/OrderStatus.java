package jp.co.example.ec202110d.common;

public enum OrderStatus {

	ShippingPreparation(0, "出荷準備中"), Shipping(1, "出荷"), Delivery(2, "配達中"), DeliveryCompleted(3, "配達完了");

	private int id;
	private String status;

	private OrderStatus(int id, String status) {
		this.id = id;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public String getOrderStatus() {
		return status;
	}

	public static OrderStatus getById(int id) {
		for (OrderStatus status : OrderStatus.values()) {
			if (status.getId() == id) {
				return status;
			}
		}
		return null;
	}

}
