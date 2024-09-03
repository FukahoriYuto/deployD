package jp.co.example.ec202110d.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.example.ec202110d.domain.UserInfo;
import jp.co.example.ec202110d.form.CreateAccountForm;
import jp.co.example.ec202110d.service.CreateAccountService;

/**
 * ユーザー登録を行う為のコントローラ
 * 
 * @author iimura
 */
@Controller
@RequestMapping("/createAccount")
public class CreateAccountController {

	@Autowired
	private CreateAccountService createAccountService;

	@Autowired
	private JavaMailSender sender;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納す。
	 * 
	 */
	@ModelAttribute
	public CreateAccountForm setUpForm() {
		return new CreateAccountForm();
	}

	/**
	 * ユーザー登録画面をフォワード
	 */
	@RequestMapping("/new-account")
	public String createAccount() {
		return "/entry/create-account";
	}

	/**
	 * ユーザー登録画面を登録する
	 * 
	 * @param ユーザー情報用フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated CreateAccountForm form, BindingResult result, Model model) {

		String name = form.getName().strip();
		if (name.equals("")) {
			model.addAttribute("spaceOnlyname", "全角スペースのみでは登録できません！");
			return createAccount();
		}

		String address = form.getAddress().strip();
		if (address.equals("")) {
			model.addAttribute("spaceOnlyaddress", "全角スペースのみでは登録できません！");
			return createAccount();
		}

		if (result.hasErrors()) {
			return createAccount();
		}

		if (!form.getPassword().equals(form.getConfirmpassword())) {
			model.addAttribute("passerror", "パスワードと確認用パスワードが不一致です");
			return createAccount();
		}

		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(form, userInfo);
		userInfo.setName(name);
		userInfo.setAddress(address);

		// 登録処理
		try {
			createAccountService.insert(userInfo);
		} catch (DuplicateKeyException e) {
			model.addAttribute("dupricateMail", "Eメールアドレスはすでに使用されています");
			return createAccount();
		}

		// 登録完了メールを送信
		MimeMessage message = sender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("yusuke.matsumoto@rakus-partners.co.jp");
			helper.setTo(userInfo.getEmail());
			helper.setSubject("[Rakuzon]会員登録について");
			helper.setText("Rakuzonへようこそ！ " + userInfo.getName() + " 様。会員登録が完了しました。\n"
					+ "以下URLよりログインしてショッピングをはじめましょう！\n" + "http://localhost:8080/login-page");
			sender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		// ログイン画面へリダイレクト
		return "redirect:/createAccount/welcomeAccount";

	}

	@GetMapping("/welcomeAccount")
	public static String welcomeAccount(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("createAccountMessage", "Rakuzonへようこそ！新規アカウントの登録が完了しました！" + "登録完了メールを送信しました。");
		return "redirect:/login-page";
	}

}
