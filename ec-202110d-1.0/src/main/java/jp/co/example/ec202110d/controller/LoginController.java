package jp.co.example.ec202110d.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ec202110d.domain.Cart;
import jp.co.example.ec202110d.domain.Item;
import jp.co.example.ec202110d.domain.UserInfo;
import jp.co.example.ec202110d.form.HeaderForm;
import jp.co.example.ec202110d.form.LoginForm;
import jp.co.example.ec202110d.service.CartAndOptionService;
import jp.co.example.ec202110d.service.LoginService;
import jp.co.example.ec202110d.service.TopPageService;

/**
 * ログイン・ログアウトのためのコントローラー
 * 
 * @author NobutakaYoshida
 *
 */
@Controller
@RequestMapping("/login-page")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private TopPageService topPageService;

	@Autowired
	private CartAndOptionService cartAndOptionService;

	@Autowired
	private HttpSession session;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
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

	/////////////////////////////////////////////////////
	// ユースケース：ログインをする
	/////////////////////////////////////////////////////
	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("")
	public String toLogin() {
		return "/entry/login";
	}

	/**
	 * ログインします.
	 * 
	 * @param form   ユーザー情報用フォーム
	 * @param result エラー情報格納用オブジェクト
	 * @return ログイン後のトップページ画面
	 */
	@RequestMapping("/login")
	public String login(@Validated LoginForm loginForm, BindingResult result, HeaderForm headerForm, Model model) {
		UserInfo userInfo = loginService.login(loginForm.getEmail(), loginForm.getPassword());
		if(result.hasErrors()) {
			return toLogin();
		}
		
		if (userInfo == null) {
			model.addAttribute("errorMessage", "メールアドレス、またはパスワードが間違っています");
			return toLogin();
		}
		/** ユーザー情報を表示するメソッド */
		session.setAttribute("userInfo", userInfo);
		List<Cart> cartList = cartAndOptionService.findByUserId(userInfo.getId());
		session.setAttribute("cartItems", cartList.size());
		/** アイテム一覧を表示するメソッド */
		List<Item> itemList = topPageService.findAllItem(headerForm.getSortNum());
		model.addAttribute("itemList", itemList);

		return "redirect:/top-page";
	}

	/**
	 * ユーザー登録画面へ遷移
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("/create-account")
	public String createAccount() {
		return "redirect:/createAccount/new-account";
	}

	/////////////////////////////////////////////////////
	// ユースケース：ログアウトをする
	/////////////////////////////////////////////////////
	/**
	 * ログアウトをします. (SpringSecurityに任せるためコメントアウトしました)
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/login-page";
	}

}
