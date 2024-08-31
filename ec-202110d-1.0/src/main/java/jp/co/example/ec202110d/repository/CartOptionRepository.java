package jp.co.example.ec202110d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.CartOption;

/**
 * カートに追加した商品のオプションを操作するリポジトリ
 * 
 * @author yusukematsumoto
 *
 */
@Repository
public class CartOptionRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<CartOption> CART_OPTION_ROW_MAPPER = (rs, i) -> {
		CartOption cartOption = new CartOption();
		cartOption.setId(rs.getInt("id"));
		cartOption.setOptionId(rs.getInt("option_id"));
		cartOption.setCartId(rs.getInt("cart_id"));
		return cartOption;
	};

	/** インサート処理 */
	public void insertCartOption(CartOption cartOption) {
		String sql = "INSERT INTO cart_options(option_id, cart_id) VALUES(:optionId, :cartId);";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("optionId", cartOption.getOptionId())
				.addValue("cartId", cartOption.getCartId());
		template.update(sql, param);
	}

	/** カートIDをもとに値を取得 */
	public List<CartOption> findByCartId(Integer cartId) {
		String sql = "SELECT * FROM cart_options WHERE cart_id=:cartId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("cartId", cartId);
		return template.query(sql, param, CART_OPTION_ROW_MAPPER);
	}

	/** カートIDをもとに削除 */
	public void deleteByCartId(Integer cartId) {
		String sql = "DELETE FROM cart_options WHERE cart_id=:cartId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("cartId", cartId);
		template.update(sql, param);
	}

}
