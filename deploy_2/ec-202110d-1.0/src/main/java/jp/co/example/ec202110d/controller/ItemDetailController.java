package jp.co.example.ec202110d.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ec202110d.domain.Cart;
import jp.co.example.ec202110d.domain.CartOption;
import jp.co.example.ec202110d.domain.Item;
import jp.co.example.ec202110d.domain.Option;
import jp.co.example.ec202110d.domain.Ranking;
import jp.co.example.ec202110d.domain.Review;
import jp.co.example.ec202110d.form.HeaderForm;
import jp.co.example.ec202110d.form.ItemDetailForm;
import jp.co.example.ec202110d.form.ReviewForm;
import jp.co.example.ec202110d.repository.RankingRepository;
import jp.co.example.ec202110d.service.ItemDetailService;

/**
 * 商品詳細画面についてのコントローラー
 * 
 * @author ootomokenji
 *
 */
@Controller
@RequestMapping("/itemDetail")
public class ItemDetailController {
	
	@Autowired
	private ItemDetailService service;
	
	@Autowired
	private HttpSession session;
	
//	@Autowired
//	private ServletContext application; // ←いいね件数を保存するため
	
	@ModelAttribute
	public ItemDetailForm setUpItemDetailForm() {
		return new ItemDetailForm();
	}
	
	@ModelAttribute
	public ReviewForm setUpReviewForm() {
		return new ReviewForm();
	}
	
	@Autowired
	public RankingRepository rankingRepository;
	
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

	/**
	 * 商品詳細画面を表示
	 * 
	 * @param id　Itemのid
	 * @param model　リクエストスコープへ格納
	 * @return
	 */
	@RequestMapping("")
	public String toItemDetail(Integer id, Model model) {
		
		Item item = service.findItemById(id);
		model.addAttribute("item", item);
		
		List<Option>optionList = service.findAllOption();
		model.addAttribute("optionList", optionList);
		
		
		// レビュー表示
		
		// レビューの件数をスコープに格納する
		List<Review> reviewList = service.findByItemId(id);
		model.addAttribute("listSize", reviewList.size());
		
		// 件数が多いと表示は時間がかかるので最初の10個のみスコープへ格納する
//		if(itemList.size() >= 10) {
//			itemList = itemList.subList(0, 10);
//		}
		
		model.addAttribute("reviewList", reviewList);
		
		
		List<Ranking> rankingList =rankingRepository.getOrderdItems();
		model.addAttribute("rankingList",rankingList);
		
		return "user-view/item-detail";
	}
	
	/**
	 * 表示されている商品を購入するための注文確認画面へ遷移
	 * 
	 * @param model
	 * @param form　userのid、Itemのid、Optionのidなどを受け取る
	 * @return
	 */
	@RequestMapping("/toCart")
	public String toCart(Model model,ItemDetailForm form) {
		Cart cart = new Cart();
		cart.setUserId(form.getUserId());
		cart.setItemId(form.getItemId());
		cart.setPrice(form.getTotalPrice());
		
		if (form.getOptionList() != null) {
			List<Integer>formOptions = form.getOptionList();
			
			List<CartOption>cartOptionList = new ArrayList<>();
			
			for (Integer optionCode : formOptions) {
				
				CartOption cartOption = new CartOption();
				cartOption.setOptionId(optionCode);
				
				cartOptionList.add(cartOption);
			}
			
			cart.setCartOptionList(cartOptionList);
			
		}
		session.setAttribute("cart", cart);
		return "user-view/confirm-order";
	}

	
	/**
	 * レビュー投稿.
	 * 
	 * @param form
	 *            フォーム
	 * @param result
	 *            リザルト
	 * @param model
	 *            モデル
	 * @return レビュー画面
	 */
	@RequestMapping(value = "/postReview")
	public String postReview(@Validated ReviewForm reviewForm, BindingResult result, Model model) {
		
		String name = reviewForm.getName().strip();
		Integer errorCount = 0;
		if ("".equals(name)) {
			++errorCount;
			model.addAttribute("NameNotSpaceMessage","スペースのみでは登録できません");
			System.out.println(name + "こんにちは");
		}
		
		String title = reviewForm.getTitle().strip();
		if ("".equals(title)) {
			++errorCount;
			model.addAttribute("TitleNotSpaceMessage","スペースのみでは登録できません");
		}
		
		String content = reviewForm.getContent().strip();
		if ("".equals(content)) {
			++errorCount;
			model.addAttribute("ContentNotSpaceMessage","スペースのみでは登録できません");
		}
		
		if (errorCount != 0) {
			return toItemDetail(reviewForm.getItemId(), model);
		}
		
		if (result.hasErrors()) {
			return toItemDetail(reviewForm.getItemId(), model);
		}
		
		Review review = new Review();
		BeanUtils.copyProperties(reviewForm, review);
		service.insert(review);
		
		return "redirect:/itemDetail?id=" + reviewForm.getItemId();
	}

	/**
	 * 役に立ったボタン.
	 * 
	 * @param form
	 *            レビューフォーム
	 * @param model
	 *            モデル
	 * @return レビュー画面
	 */
	@RequestMapping(value = "/likeCount")
	public String likeCount(ReviewForm reviewForm, Model model) {
		
		// レビューごとの役に立ったの数を算出
		Integer searchLikeCount = service.searchLikeCount(reviewForm.getId());
		// ボタンを押されたら１増やす
		++searchLikeCount;
		
		// データを更新
		service.updateLikeCount(searchLikeCount, reviewForm.getId());
		
		// 更新されたデータを格納
		model.addAttribute("likeCount", searchLikeCount);
		
		return "redirect:/itemDetail?id="+ reviewForm.getItemId();
	}
	
	/**
	 * 違反を報告するボタン.
	 * 
	 * @param form
	 *            レビューフォーム
	 * @param model
	 *            モデル
	 * @return レビュー画面
	 */
	@RequestMapping(value = "/deleteCount")
	public String deleteCount(ReviewForm reviewForm, Model model) {
		
		// レビューごとの役に立ったの数を算出
		Integer searchDeleteCount = service.searchDeleteCount(reviewForm.getId());
		// ボタンを押されたら１増やす
		++searchDeleteCount;
		
		// データを更新
		service.updateDeleteCount(searchDeleteCount, reviewForm.getId());
		
		// 10回溜まったらコメントを更新
		if(searchDeleteCount >= 10) {
			String deleteComment = "一定数の違反が報告されたため、投稿されたカスタマーレビューの掲載を控えさせていただきました。"; 
			service.updateDeleteComment(deleteComment, reviewForm.getId());
		}
		
		return "redirect:/itemDetail?id="+ reviewForm.getItemId();
	}
	
	
	/** あなたへのおすすめを表示するメソッド
	 * @author iimura
	 * 
	 *
	@RequestMapping("/")
	public String rankIndex(ReviewForm reviewForm,Model model) {
		List<Item> itemList =rankingRepository.getItemList();
		for (Item item : itemList) {
			int itemId = item.getId();
			item.setRankingList(rankingRepository.getOrderdItems(itemId));
			
		}
		model.addAttribute("itemList",itemList);
		return  "redirect:/itemDetail?id="+ reviewForm.getItemId();
	}
	*/
	
	/**商品詳細画面へ遷移するためのメソッド */
	@RequestMapping("/item-detail")
	public String itemDetail() {
		return "redirect:/user-view/item-detail";
	}
		

}
