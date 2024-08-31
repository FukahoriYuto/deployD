package jp.co.example.ec202110d.domain;

/**
 * ユーザーカートに入っている商品のオプションに関するドメイン
 * @author yusukematsumoto
 *
 */
public class CartOption {

	private Integer id;
	private Integer optionId;
	private Integer cartId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	@Override
	public String toString() {
		return "CartOption [id=" + id + ", optionId=" + optionId + ", cartId=" + cartId + "]";
	}

}
