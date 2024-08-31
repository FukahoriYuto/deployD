package jp.co.example.ec202110d.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ec202110d.domain.OrderHistory;
import jp.co.example.ec202110d.form.HeaderForm;
import jp.co.example.ec202110d.service.OrderHistoryService;

@Controller
@RequestMapping("/order-history")
public class OrderHistoryController {
	
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

	@Autowired
	private OrderHistoryService orderHistoryService;

	@RequestMapping("")
	public String orderHistory(Integer userId, Model model) {
		if(session.getAttribute("userInfo") == null) {
			return "redirect:/login-page";
		}
		if(session.getAttribute("cart") != null) {
			session.removeAttribute("cart");
		}
		List<OrderHistory> orderHistoryList = orderHistoryService.getOrderHistory(userId);
		model.addAttribute("orderHistoryList", orderHistoryList);
		model.addAttribute("NumberOfOrders", orderHistoryList.size());
		return "user-view/order-history";
	}
	
}
