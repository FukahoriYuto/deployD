package jp.co.example.ec202110d.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.example.ec202110d.domain.Cart;
import jp.co.example.ec202110d.domain.CartOption;
import jp.co.example.ec202110d.form.AddCartForm;
import jp.co.example.ec202110d.form.HeaderForm;
import jp.co.example.ec202110d.service.CartAndOptionService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartAndOptionService cartAndOptionService;

	@Autowired
	private HttpSession session;
	
	/**
	 * ホーム画面のソートのため、初期値をセットするためのフォームをセットアップ
	 * 
	 * @return　初期値１としてセットアップされたフォーム
	 */
	@ModelAttribute
	public HeaderForm setUpForm() {
		HeaderForm form = new HeaderForm();
		form.setSortNum(1);
		return form;
	}

	@GetMapping("")
	public String cart(Integer userId, Model model) {
		if(session.getAttribute("cart") != null) {
			session.removeAttribute("cart");
		}
		List<Cart> cartList = cartAndOptionService.findByUserId(userId);
		Integer totalPrice = 0;
		for (Cart cart : cartList) {
			totalPrice += cart.getPrice();
		}
		model.addAttribute("cartList", cartList);
		model.addAttribute("totalPrice", totalPrice);
		return "user-view/cart";
	}

	@GetMapping("/addCartWithNoAccount")

	public static String addCartWithNoAccount(RedirectAttributes redirectAttribute) {
		redirectAttribute.addFlashAttribute("loginMessage", "ログインまたは新規登録をしてショッピングを楽しみましょう！");
		return "redirect:/login-page";
	}

	@PostMapping("/addCart")
	public void addCart(AddCartForm form, RedirectAttributes redirectAttribute,Model model) {
		
		if (form.getUserId() == null) {
			addCartWithNoAccount(redirectAttribute);
		}

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
		cartAndOptionService.insertCartAndOption(cart);
		redirectAttribute.addFlashAttribute("setCartMessage", "カートに追加しました！");
		List<Cart> cartList = cartAndOptionService.findByUserId(cart.getUserId());
		session.setAttribute("cartItems", cartList.size());
	}

}
