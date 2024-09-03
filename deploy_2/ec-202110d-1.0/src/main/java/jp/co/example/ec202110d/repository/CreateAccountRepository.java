package jp.co.example.ec202110d.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.UserInfo;

/**
 * ユーザー登録をするためのリポジトリ。
 * 
 * @author iimura
 *
 */
@Repository
public class CreateAccountRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザー情報登録のメソッド
	 *
	 */
	public void insert(UserInfo userInfo) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(userInfo);
		String sql = "insert into user_info(name,email,password,zipcode,address,telephone)values(:name,:email,:password,:zipcode,:address,:telephone)";
		template.update(sql, param);
	}

}
