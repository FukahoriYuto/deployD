package jp.co.example.ec202110d.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.ec202110d.domain.Cart;
import jp.co.example.ec202110d.domain.CartOption;
import jp.co.example.ec202110d.domain.Option;
import jp.co.example.ec202110d.repository.CartOptionRepository;
import jp.co.example.ec202110d.repository.CartRepository;
import jp.co.example.ec202110d.repository.ItemDetailRepository;
import jp.co.example.ec202110d.repository.OptionRepository;

/**
 * オプションを含めた情報をカートに追加または取得するサービス
 * 
 * @author yusukematsumoto
 *
 */
@Service
public class CartAndOptionService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartOptionRepository cartOptionRepository;

	@Autowired
	private ItemDetailRepository itemDetailRepository;

	@Autowired
	private OptionRepository optionRepository;

	/** カート, カートオプションに追加する処理 */
	public void insertCartAndOption(Cart cart) {
		Integer cartId = cartRepository.insertCart(cart);
		if (cart.getCartOptionList() != null) {
			for (CartOption option : cart.getCartOptionList()) {
				option.setCartId(cartId);
				cartOptionRepository.insertCartOption(option);
			}
		}
	}

	/** カート, カートオプションから値を取得する処理 */
	public List<Cart> findByUserId(Integer userId) {
		List<Cart> cartList = cartRepository.findByUserId(userId);
		for (Cart cart : cartList) {
			List<CartOption> cartOptionList = cartOptionRepository.findByCartId(cart.getId());
			cart.setCartOptionList(cartOptionList);
			cart.setItem(itemDetailRepository.findById(cart.getItemId()));
			List<Option> optionList = new ArrayList<>();
			for (CartOption cartOption : cartOptionList) {
				Option option = optionRepository.findOptionById(cartOption.getOptionId());
				optionList.add(option);
			}
			cart.setOptionList(optionList);
		}
		return cartList;
	}

	/** 商品を購入した際にカート、カートオプションテーブルの値を削除する処理 */
	public void deleteByUserId(Integer userId) {
		List<Integer> deleteCartList = cartRepository.deleteCart(userId);
		for (Integer cartId : deleteCartList) {
			cartOptionRepository.deleteByCartId(cartId);
		}
	}

	/** カートから商品を削除する処理 */
	public void deleteByCartAndUserId(Integer cartId, Integer userId) {
		cartRepository.deleteByCartIdAndUserId(cartId, userId);
		cartOptionRepository.deleteByCartId(cartId);
	}

}
