package jp.co.example.ec202110d.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ec202110d.form.AddCartForm;

@Controller
@RequestMapping("/addCart")
public class AddCartController {

	@ModelAttribute
	public AddCartForm setAddCartForm() {
		return new AddCartForm();
	}

	@RequestMapping("")
	public String addCart(AddCartForm form) {
//		System.out.println(form);
		return "index";
	}

}
