package jp.co.example.ec202110d.form;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 注文確定処理の情報を受取るためのフォーム
 * 
 * @author yusukematsumoto
 *
 */
public class OrderInfoForm {

	private Integer id;
	private Integer userId;
	private Integer status;
	private Integer total_price;
	private Date order_date;

	@NotBlank(message = "受取人名を入力してください！")
	private String destinationName;
	@NotBlank(message = "メールアドレスを入力してください！")
	@Email(message = "メールアドレスの形式が不正です！")
	private String destinationEmail;
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "ハイフンを入れた７桁の郵便番号を入力してください！")
	private String destinationZipcode;
	@NotBlank(message = "配送先住所を入力してください！")
	private String destinationAddress;
	@Pattern(regexp = "^[0-9]{2,5}-[0-9]{1,4}-[0-9]{4}$", message = "ハイフンを入れた連絡先電話番号を入力してください！")
	private String destinationTel;
	@NotNull(message = "決済方法を選択してください")
	private Integer paymentMethod;
	@NotBlank(message = "配達希望日を選択してください")
	private String deliveryDate;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Integer total_price) {
		this.total_price = total_price;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Override
	public String toString() {
		return "OrderInfoForm [id=" + id + ", userId=" + userId + ", status=" + status + ", total_price=" + total_price
				+ ", order_date=" + order_date + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", paymentMethod=" + paymentMethod
				+ ", deliveryDate=" + deliveryDate + "]";
	}

}
