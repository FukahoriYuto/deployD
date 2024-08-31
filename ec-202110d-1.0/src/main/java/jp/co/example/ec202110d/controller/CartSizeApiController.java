package jp.co.example.ec202110d.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.example.ec202110d.service.CartAndOptionService;

/**
 * 画面更新をした場合、カートの中身の数を書き換えるAPI
 * 
 * @author ootomokenji
 *
 */
@RestController
@RequestMapping("/cart-size")
public class CartSizeApiController {

	@Autowired
	CartAndOptionService service;
	
	@ResponseBody
	@RequestMapping("/size")
	public Map<String, Integer> size(Integer id) {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("size", service.findByUserId(id).size());
		
		return map;
	}
	
}
