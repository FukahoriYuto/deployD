package jp.co.example.ec202110d.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.UserInfo;

/**
 * ログイン・ログアウトのためのリポジトリ
 * 
 * @author NobutakaYoshida
 *
 */
@Repository
public class LoginRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<UserInfo> USERINFO_ROW_MAPPER = (rs, i) -> {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(rs.getInt("id"));
		userInfo.setName(rs.getString("name"));
		userInfo.setEmail(rs.getString("email"));
		userInfo.setPassword(rs.getString("password"));
		userInfo.setZipcode(rs.getString("zipcode"));
		userInfo.setAddress(rs.getString("address"));
		userInfo.setTelephone(rs.getString("telephone"));
		return userInfo;
	};

//	/**
//	 * メールアドレスとパスワードからユーザー情報を取得する （１件も存在しない場合はnullを返す）
//	 */
//	public UserInfo findByEmailAndPassword(String email, String password) {
//		String sql = "SELECT * FROM user_info WHERE email=:email AND password=:password;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
//		List<UserInfo> userInfoList = template.query(sql, param, USERINFO_ROW_MAPPER);
//		if (userInfoList.size() == 0) {
//			return null;
//		}
//		return userInfoList.get(0);
//	}

	/**
	 * メールアドレスからユーザーを検索する
	 */
	public UserInfo findByMail(String email) {
		String sql = "SELECT * FROM user_info WHERE email=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		try {
			UserInfo user = template.queryForObject(sql, param, USERINFO_ROW_MAPPER);
			return user;
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	public UserInfo findById(Integer userId) {
		String sql = "SELECT * FROM user_info WHERE id=:userId;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(userId);
		UserInfo userInfo = template.queryForObject(sql, param, USERINFO_ROW_MAPPER);
		return userInfo;
	}

}
