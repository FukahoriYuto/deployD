package jp.co.example.ec202110d.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.Cart;

/**
 * 
 * @author yusukematsumoto
 *
 */
@Repository
public class CartRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Cart> CART_ROW_MAPPER = (rs, i) -> {
		Cart cart = new Cart();
		cart.setId(rs.getInt("id"));
		cart.setUserId(rs.getInt("user_id"));
		cart.setItemId(rs.getInt("item_id"));
		cart.setPrice(rs.getInt("price"));
		return cart;
	};

	/** カート追加時にカートIDに紐付いたオプションを追加するため最後のIDを取得 */
	private Integer findLastId() {
		String sql = "SELECT * FROM cart WHERE id = (SELECT  MAX(id) FROM cart);";
		SqlParameterSource param = new MapSqlParameterSource();
		try {
			Cart cart = template.queryForObject(sql, param, CART_ROW_MAPPER);
			return cart.getId();
		} catch (Exception e) {
			return 1;
		}
	}

	/** ユーザーIDからカートに入っている商品をすべて取得 */
	public List<Cart> findByUserId(Integer userId) {
		String sql = "SELECT * FROM cart WHERE user_id=:userId ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Cart> cartList = template.query(sql, param, CART_ROW_MAPPER);
		return cartList;
	}

	/** カートにユーザーID, 商品IDを追加する */
	/** 追加した際に付与されるIDを返却 */
	public Integer insertCart(Cart cart) {
		String sql = "INSERT INTO cart(user_id, item_id, price) VALUES(:userId, :itemId, :price)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", cart.getUserId())
				.addValue("itemId", cart.getItemId()).addValue("price", cart.getPrice());
		template.update(sql, param);
		return findLastId();
	}

	/** ユーザーIDからカートの商品を全件削除 */
	public List<Integer> deleteCart(Integer userId) {
		// 削除するカートのIDをリストにして取得
		List<Integer> deleteCartId = new ArrayList<>();
		List<Cart> cartList = findByUserId(userId);
		for (Cart cart : cartList) {
			deleteCartId.add(cart.getId());
		}
		String sql = "DELETE FROM cart WHERE user_id=:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		template.update(sql, param);
		return deleteCartId;
	}

	/** カートIDからカートの商品を削除 */
	public void deleteByCartIdAndUserId(Integer cartId, Integer userId) {
		String sql = "DELETE FROM cart WHERE id=:cartId AND user_id=:userId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("cartId", cartId).addValue("userId", userId);
		template.update(sql, param);
	}

}
