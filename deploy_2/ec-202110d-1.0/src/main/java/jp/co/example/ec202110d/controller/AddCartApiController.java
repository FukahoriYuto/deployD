package jp.co.example.ec202110d.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.ec202110d.domain.Cart;
import jp.co.example.ec202110d.domain.CartOption;
import jp.co.example.ec202110d.form.AddCartForm;
import jp.co.example.ec202110d.service.CartAndOptionService;

/**
 * カートに商品を追加するAPI
 * 
 * @author ootomokenji
 *
 */
@RestController
@RequestMapping("add-cart-api")
public class AddCartApiController {

	@Autowired
	private CartAndOptionService cartAndOptionService;

	@ResponseBody
	@RequestMapping("/addCart")
	public Map<String, Integer> addCart(AddCartForm form) {
		
		Cart cart = new Cart();
		cart.setUserId(form.getUserId());
		cart.setItemId(form.getItemId());
		cart.setPrice(form.getQuantity());
		if (form.getOptionId() != null) {
			List<CartOption> cartOptionList = new ArrayList<>();
			for (Integer optionId : form.getOptionId()) {
				CartOption cartOption = new CartOption();
				cartOption.setOptionId(optionId);
				cartOptionList.add(cartOption);
			}
			cart.setCartOptionList(cartOptionList);
		}
		if (form.getAmount()==null) {
			form.setAmount(1);
		}
		for (int i = 0; i < form.getAmount(); i++) {
			cartAndOptionService.insertCartAndOption(cart);
		}

		Map<String, Integer>map = new HashMap<>();
		map.put("size", cartAndOptionService.findByUserId(form.getUserId()).size());
		return map;
	}
}
