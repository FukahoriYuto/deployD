//package jp.co.example.ec202110d.controller;
//
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import jp.co.example.ec202110d.domain.Cart;
//import jp.co.example.ec202110d.domain.Item;
//import jp.co.example.ec202110d.domain.OrderdItems;
//import jp.co.example.ec202110d.domain.Ranking;
//import jp.co.example.ec202110d.form.HeaderForm;
//import jp.co.example.ec202110d.repository.ItemDetailRepository;
//import jp.co.example.ec202110d.repository.RankingRepository;
//import jp.co.example.ec202110d.service.ItemDetailService;
//import jp.co.example.ec202110d.service.TopPageService;
//
///**
// * ランキングを表示するページ
// * @author iimura
// */
//@Controller
//@RequestMapping("/ranking")
//public class RankingController {
//	
//	@Autowired
//	private Item item;
//	
//	@Autowired
//	private ItemDetailService itemDetailService;
//	
//	@Autowired
//	private OrderdItems orderdItems;
//	
//	@Autowired
//	private TopPageService topPageService;
//	
//	@Autowired
//	private RankingRepository rankingRepository;
//	
//	
//	/** 
//	 * item_id数の降順に商品を画面に出力
//	 * */
//	@RequestMapping("")
//	public String rankIndex(Model model) {
//		List<Item> itemList =rankingRepository.getItemList();
//		for (Item item : itemList) {
//			int itemId = item.getId();
//			itemList.setItem(itemId);
//		}
//		
//		// item_id数とitem_idの情報をリストに格納
//		List<Ranking> rankingList = rankingRepository.getOrderdItems(item.getId());
//		model.addAttribute("RankingList", rankingList);
////		
////		// itemの情報をitemリストに格納
////		Item item = new Item();
////		// BeanUtils.copyProperties(itemId, item);
////		
////		model.addAttribute("item", item);
//		
//		for(int i=0; i<rankingList.size(); i++) {
//			rankingList.get(i);
//		} 
//		return "/user-view/ranking";
//	}
//	
//	/**商品詳細画面へ遷移するためのメソッド 
//	@RequestMapping("/item-detail")
//	public String itemDetail() {
//		return "/user-view/item-detail";
//	}
//	*/
//	
//}
