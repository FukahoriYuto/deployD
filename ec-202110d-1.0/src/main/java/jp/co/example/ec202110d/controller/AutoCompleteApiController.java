package jp.co.example.ec202110d.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.ec202110d.domain.Item;
import jp.co.example.ec202110d.service.TopPageService;

/**
 * オートコンプリートするためのAPIコントローラー
 * 
 * @author ootomokenji
 *
 */
@RestController
@RequestMapping("/auto-complete")
public class AutoCompleteApiController {
	
	@Autowired
	TopPageService service;
	
	/**
	 * @param name 入力された文字
	 * @param sortNum ソートで使う判定の数字（今は１で固定）
	 * @return　nameで曖昧検索されたItemの名前リスト
	 */
	@ResponseBody
	@RequestMapping("/list")
	public List<String> list(String name,Integer sortNum) {
		
		List<String>list = new ArrayList<>();
		List<Item>itemList = new ArrayList<>();
		
		sortNum = 1;
//		入力された名前で曖昧検索し、リストに入れる
		itemList = service.findByLikeName(name,sortNum);
//		リストから名前だけを取り出し、returnのためのリストに入れる
		for (Item item : itemList) {
			list.add(item.getName());
		}
		
		return list;
	}

}
