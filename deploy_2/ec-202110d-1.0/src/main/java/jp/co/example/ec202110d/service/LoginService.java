package jp.co.example.ec202110d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ec202110d.domain.UserInfo;
import jp.co.example.ec202110d.repository.LoginRepository;

/**
 * ログイン・ログアウトのためのサービス
 * 
 * @author NobutakaYoshida
 *
 */
@Service
@Transactional
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ログインをします.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return ユーザー情報 存在しない場合はnullが返ります
	 */
	public UserInfo login(String email, String password) {
		UserInfo user = loginRepository.findByMail(email);
		if (user == null) {
			return null;
		}
			// 入力されたパスワードをハッシュしてDB上のパスワードと一致していたらオブジェクトを返す
		if (passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		return null;
	}

	public UserInfo findById(Integer userId) {
		return loginRepository.findById(userId);
	}
}
