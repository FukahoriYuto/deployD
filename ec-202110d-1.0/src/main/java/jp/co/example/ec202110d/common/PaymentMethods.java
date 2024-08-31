package jp.co.example.ec202110d.common;

public enum PaymentMethods {

	CashOnDelivery(0, "代金引換"), CreditCardpayment(1, "クレジットカード");

	private int id;
	private String paymentMethod;

	private PaymentMethods(int id, String paymentMethod) {
		this.id = id;
		this.paymentMethod = paymentMethod;
	}

	public int getId() {
		return id;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public static PaymentMethods getById(int id) {
		for (PaymentMethods payment : PaymentMethods.values()) {
			if (payment.getId() == id) {
				return payment;
			}
		}
		return null;
	}
}
