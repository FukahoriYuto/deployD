package jp.co.example.ec202110d.form;

import java.util.List;

/**
 * ショッピングカートに追加するフォーム
 * 
 * @author yusukematsumoto
 *
 */
public class AddCartForm {
	/** ユーザーID */
	private Integer userId;
	/** アイテムID */
	private Integer itemId;
	/** オプション含んだ金額 */
	private Integer quantity;
	/** オプションID */
	private List<Integer> optionId;
	private Integer amount;
	
	
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<Integer> getOptionId() {
		return optionId;
	}

	public void setOptionId(List<Integer> optionId) {
		this.optionId = optionId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "AddCartForm [userId=" + userId + ", itemId=" + itemId + ", quantity=" + quantity + ", optionId="
				+ optionId + ", amount=" + amount + "]";
	}

}
