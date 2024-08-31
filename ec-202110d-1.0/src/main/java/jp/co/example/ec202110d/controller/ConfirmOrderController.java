package jp.co.example.ec202110d.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.example.ec202110d.domain.Cart;
import jp.co.example.ec202110d.domain.Option;
import jp.co.example.ec202110d.domain.OrderHistory;
import jp.co.example.ec202110d.domain.UserInfo;
import jp.co.example.ec202110d.form.AddCartForm;
import jp.co.example.ec202110d.form.HeaderForm;
import jp.co.example.ec202110d.form.OrderInfoForm;
import jp.co.example.ec202110d.repository.OptionRepository;
import jp.co.example.ec202110d.service.CartAndOptionService;
import jp.co.example.ec202110d.service.ItemDetailService;
import jp.co.example.ec202110d.service.OrderHistoryService;

/**
 * @author iimura 注文確認画面を表示する。
 */
@Controller
@Transactional
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

	@ModelAttribute
	public OrderInfoForm setOrderInfoForm() {
		return new OrderInfoForm();
	}

	/**
	 * ホーム画面のソートのため、初期値をセットするためのフォームをセットアップ
	 * 
	 * @return 初期値１としてセットアップされたフォーム
	 */
	@ModelAttribute
	public HeaderForm setUpForm() {
		HeaderForm form = new HeaderForm();
		form.setSortNum(1);
		return form;
	}

	@Autowired
	private CartAndOptionService cartAndOptionService;

	@Autowired
	private OrderHistoryService orderHistoryService;

	@Autowired
	private ItemDetailService itemDetailService;

	@Autowired
	private OptionRepository optionRepository;

	@Autowired
	private HttpSession session;

	/** ユーザーIDをもとにカートに追加されている商品を注文確認画面に出力 */
	@PostMapping("")
	public String confirmOrder(Integer userId, Model model) {
		List<Cart> cartList = cartAndOptionService.findByUserId(userId);
		model.addAttribute("cartList", cartList);
		Integer totalPrice = 0;
		for (Cart cart : cartList) {
			totalPrice += cart.getPrice();
		}
		model.addAttribute("totalPrice", totalPrice);
		return "/user-view/confirm-order";
	}

	@GetMapping("")
	public String getConfirmOrder(Integer userId) {
		return "redirect:/top-page";
	}

	/** 今すぐ購入ボタンでの購入確認画面遷移 */
	@RequestMapping("/buy-now")
	public String buyNow(AddCartForm form, Model model, RedirectAttributes redirectAttribute) {
		if (form.getUserId() == null) {
			return CartController.addCartWithNoAccount(redirectAttribute);
		}
		List<Cart> cartList = new ArrayList<>();
		Cart cart = new Cart();
		cart.setItemId(form.getItemId());
		cart.setItem(itemDetailService.findItemById(form.getItemId()));
		cart.setPrice(form.getQuantity());
		List<Option> optionList = new ArrayList<>();
		if (form.getOptionId() != null) {
			for (Integer optionId : form.getOptionId()) {
				Option option = optionRepository.findOptionById(optionId);
				optionList.add(option);
			}
		}
		// 今すぐ購入ボタンを押した際のカートの件数を送る
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		Integer cartItems = cartAndOptionService.findByUserId(userInfo.getId()).size();
		session.setAttribute("cartItems", cartItems);

		cart.setOptionList(optionList);
		cartList.add(cart);
		session.setAttribute("cart", cart);
		model.addAttribute("cartList", cartList);
		model.addAttribute("totalPrice", form.getQuantity());
		return "user-view/confirm-order";
	}

	@GetMapping("/delete")
	public String update(Integer cartId, Integer userId) {
		cartAndOptionService.deleteByCartAndUserId(cartId, userId);
		return "redirect:/cart?userId=" + userId;
	}

	/** カートから商品を削除し注文履歴に追加後、注文完了画面に遷移 */
	@PostMapping("/complete-order")
	public String completeOrder(@Validated OrderInfoForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes, AddCartForm addCartForm) {
		if (result.hasErrors()) {
			return confirmOrder(form.getUserId(), model);
		}

		DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime inputDateTime = LocalDateTime.parse(form.getDeliveryDate(), inputDateFormat);
		LocalDateTime nowDateTime = LocalDateTime.now().plusHours(3);
		if (ChronoUnit.DAYS.between(nowDateTime, inputDateTime) == 0) {
			if (ChronoUnit.HOURS.between(nowDateTime, inputDateTime) < 0) {
				model.addAttribute("timeError", "3時間以内のご指定はできません");
				return confirmOrder(form.getUserId(), model);
			}
		}

		/** セッションにカートリストがあった場合の処理 */
		if (session.getAttribute("cart") != null) {
			LocalDate date = LocalDate.now();
			form.setOrder_date(java.sql.Date.valueOf(date));
			OrderHistory confirmOrder = new OrderHistory();
			BeanUtils.copyProperties(form, confirmOrder);
			Integer totalPrice = (int) (confirmOrder.getTotal_price() * 1.1);
			confirmOrder.setTotal_price(totalPrice);

			// 注文履歴に追加
			Cart cart = (Cart) session.getAttribute("cart");
			orderHistoryService.BuyNow(confirmOrder, cart.getItemId());

			// sessionに保管したカートを削除
			session.removeAttribute("cart");
			date.plusDays(1);
			redirectAttributes.addFlashAttribute("deliveryDate", date);
			redirectAttributes.addFlashAttribute("destinationAddress", confirmOrder.getDestinationAddress());
			return "redirect:/confirm-order/complete";
		}
		LocalDate date = LocalDate.now();
		form.setOrder_date(java.sql.Date.valueOf(date));
		OrderHistory confirmOrder = new OrderHistory();
		BeanUtils.copyProperties(form, confirmOrder);
		Integer totalPrice = (int) (confirmOrder.getTotal_price() * 1.1);
		confirmOrder.setTotal_price(totalPrice);
		// 注文履歴に追加
		orderHistoryService.insertOrderHistory(confirmOrder);
		// カートから商品削除
		cartAndOptionService.deleteByUserId(form.getUserId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日  HH:mm");
		redirectAttributes.addFlashAttribute("deliveryDate", inputDateTime.format(formatter));
		redirectAttributes.addFlashAttribute("destinationAddress", confirmOrder.getDestinationAddress());
		return "redirect:/confirm-order/complete";
	}

	/** リダイレクト用 */
	@GetMapping("/complete")
	public String complete() {
		return "/user-view/order-completed";
	}

}
