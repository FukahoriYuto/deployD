package jp.co.example.ec202110d.controller;

//コメント１

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.example.ec202110d.domain.Item;
import jp.co.example.ec202110d.form.HeaderForm;
import jp.co.example.ec202110d.form.LoginForm;
import jp.co.example.ec202110d.service.TopPageService;

/**
 * ホーム画面用のコントローラー
 * 
 * @author NobutakaYoshida
 *
 */
@Controller
@RequestMapping("/top-page")
public class TopPageController {

	@Autowired
	private TopPageService topPageService;

	@Autowired
	private HttpSession session;

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

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/** １ページの表示数 */
	private final String limit = "8";

	/** ページネーションで表示するページ数 */
	private int showPageSize = 3;

	/**
	 * ホーム画面を表示
	 * 
	 * @param model
	 * @return ホーム画面
	 */
	@RequestMapping("")
	public String home(HeaderForm headerForm, Model model, @RequestParam HashMap<String, String> params)
			throws Exception {
		if (session.getAttribute("cart") != null) {
			session.removeAttribute("cart");
		}

		// パラメータを設定し、現在のページを取得する
		String currentPage = params.get("page");

		// 初期表示ではパラメータを取得できないので、1ページに設定
		if (currentPage == null) {
			currentPage = "1";
		}

		// データ取得時の取得件数、取得情報の指定
		HashMap<String, String> search = new HashMap<String, String>();
		search.put("limit", limit);
		search.put("page", currentPage);

		int total = 0;
		List<Item> itemList = null;
		try {
			// データ総数を取得
			total = topPageService.getItemListCount();
			// データ一覧を取得
			itemList = topPageService.getItemList(search, headerForm.getSortNum());
		} catch (Exception e) {
			return "/user-view/home";
		}

		// pagination処理
		// "総数/1ページの表示数"から総ページ数を割り出す
		int totalPage = (total + Integer.valueOf(limit) - 1) / Integer.valueOf(limit);
		int page = Integer.valueOf(currentPage);
		// 表示する最初のページ番号を算出
		// (例)1,2,3ページのstartPageは1。4,5,6ページのstartPageは4
		int startPage = page - (page - 1) % showPageSize;
		// 表示する最後のページ番号を算出
		int endPage = (startPage + showPageSize - 1 > totalPage) ? totalPage : startPage + showPageSize - 1;
		model.addAttribute("itemList", itemList);
		model.addAttribute("total", total);
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "/user-view/home";

	}

	/**
	 * 商品の写真をクリックし、商品詳細画面へ遷移
	 * 
	 * @return 商品詳細画面
	 */
	@RequestMapping("/item-detail")
	public String itemDetail() {
		return "/user-view/item-detail";
	}

	/**
	 * 「カートへ追加」ボタンをクリックし、カートの中身画面へ遷移
	 * 
	 * @return カートの中身画面
	 */
	@RequestMapping("/cart")
	public String Cart() {
		return "user-view/cart";
	}

	/**
	 * 商品名の曖昧検索 （空白の場合は全件表示。商品が見つからない場合はエラーメッセージを出力。）
	 * 
	 * @param headerForm 商品名の一部または全部
	 * @param model
	 * @return 曖昧検索された結果
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public String search(HeaderForm headerForm, Model model, @RequestParam HashMap<String, String> params)
			throws Exception {
		if (session.getAttribute("cart") != null) {
			session.removeAttribute("cart");
		}

		// パラメータを設定し、現在のページを取得する
		String currentPage = params.get("page");

		// 初期表示ではパラメータを取得できないので、1ページに設定
		if (currentPage == null) {
			currentPage = "1";
		}

		if (params.get("name").equals("null")) {
			headerForm.setName("");
		}

		if (headerForm.getSortNum() == null) {
			headerForm.setSortNum(1);
		}

		// データ取得時の取得件数、取得情報の指定
		HashMap<String, String> search = new HashMap<String, String>();
		search.put("limit", limit);
		search.put("page", currentPage);

		int total = 0;
		List<Item> itemList = null;
		try {
			if (headerForm.getName().equals("")) {
				itemList = topPageService.getItemList(search, headerForm.getSortNum());
				total = topPageService.getItemListCount();
			} else {
				// データ総数を取得
				total = topPageService.getItemListCountByName(headerForm.getName());
				// データ一覧を取得
				itemList = topPageService.getItemListByName(search, headerForm.getName(), headerForm.getSortNum());
			}
		} catch (Exception e) {
			return "index";
		}

		if (itemList.size() == 0) {
			itemList = topPageService.getItemList(search, headerForm.getSortNum());
			model.addAttribute("searchErrorMessage", "該当する商品がありません。");
			total = topPageService.getItemListCount();
		}

		// pagination処理
		// "総数/1ページの表示数"から総ページ数を割り出す
		int totalPage = (total + Integer.valueOf(limit) - 1) / Integer.valueOf(limit);
		int page = Integer.valueOf(currentPage);
		// 表示する最初のページ番号を算出
		// (例)1,2,3ページのstartPageは1。4,5,6ページのstartPageは4
		int startPage = page - (page - 1) % showPageSize;
		// 表示する最後のページ番号を算出
		int endPage = (startPage + showPageSize - 1 > totalPage) ? totalPage : startPage + showPageSize - 1;
		model.addAttribute("itemList", itemList);
		model.addAttribute("total", total);
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "/user-view/home";
	}

}
