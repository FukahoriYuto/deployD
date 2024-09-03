package jp.co.example.ec202110d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ec202110d.domain.UserInfo;
import jp.co.example.ec202110d.repository.CreateAccountRepository;

/**
 * ユーザー登録メソッドを操作するサービスクラス
 * @author iimura
 */
@Service
@Transactional
public class CreateAccountService {
	
	@Autowired
	private CreateAccountRepository createAccountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * ユーザー情報を登録する。
	 * @param userInfo ユーザー情報
	 */
	public void insert(UserInfo userInfo) {
		String hashedPassword = passwordEncoder.encode(userInfo.getPassword());
		userInfo.setPassword(hashedPassword);
		createAccountRepository.insert(userInfo);
	}
}
