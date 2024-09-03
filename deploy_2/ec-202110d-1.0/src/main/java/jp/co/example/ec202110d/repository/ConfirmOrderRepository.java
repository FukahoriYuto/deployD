//package jp.co.example.ec202110d.repository;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.stereotype.Repository;
//
//import jp.co.example.ec202110d.domain.Cart;
//import jp.co.example.ec202110d.domain.CartAndOptions;
//import jp.co.example.ec202110d.domain.CartOption;
//import jp.co.example.ec202110d.domain.Item;
//import jp.co.example.ec202110d.domain.Option;
//import jp.co.example.ec202110d.domain.UserInfo;
//import jp.co.example.ec202110d.service.CartAndOptionService;
//
///**
// * @author iimura 注文確認画面のリポジトリ― ユーザー情報とカートの中身の取得⇒メソッドはひとつにまとめたほうがよい
// *         cartのuserIdとUserInfoのidを紐づけ結合し情報を取得
// */
//@Repository
//public class ConfirmOrderRepository {
//
//	@Autowired
//	private NamedParameterJdbcTemplate template;
//
//	@Autowired
//	private CartRepository cartRepository;
//
//	@Autowired
//	private CartOptionRepository cartOptionRepository;
//
//	@Autowired
//	private CartAndOptionService cartAndOptionService;
//
//	/** UserInfoオブジェクトを生成するローマッパー */
//	private static final RowMapper<UserInfo> USERINFO_ROW_MAPPER = (rs, i) -> {
//		UserInfo userInfo = new UserInfo();
//		userInfo.setId(rs.getInt("id"));
//		userInfo.setId(rs.getInt("name"));
//		userInfo.setId(rs.getInt("email"));
//		userInfo.setId(rs.getInt("password"));
//		userInfo.setId(rs.getInt("zipcode"));
//		userInfo.setId(rs.getInt("address"));
//		userInfo.setId(rs.getInt("telephone"));
//		return userInfo;
//	};
//
//	/** cartオブジェクトを生成するローマッパー */
//	private static final RowMapper<Cart> CART_ROW_MAPPER = (rs, i) -> {
//		Cart cart = new Cart();
//		cart.setId(rs.getInt("id"));
//		cart.setUserId(rs.getInt("userId"));
//		cart.setItemId(rs.getInt("itemId"));
//		return cart;
//	};
//
//	/** CartOptionオブジェクトを生成するローマッパー */
//	private static final RowMapper<CartOption> CARTOPTION_ROW_MAPPER = (rs, i) -> {
//		CartOption cartOption = new CartOption();
//		cartOption.setId(rs.getInt("id"));
//		cartOption.setOptionId(rs.getInt("optionId"));
//		cartOption.setCartId(rs.getInt("cartd"));
//		return cartOption;
//	};
//
//	/** itemオブジェクトを生成するローマッパー */
//	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
//		Item item = new Item();
//		item.setId(rs.getInt("id"));
//		item.setName(rs.getString("name"));
//		item.setDescription(rs.getString("description"));
//		item.setPrice(rs.getInt("price"));
//		item.setImagePath(rs.getString("imagePath"));
//		return item;
//	};
//
//	/** Optionオブジェクトを生成するローマッパー */
//	private static final RowMapper<Option> OPTION_ROW_MAPPER = (rs, i) -> {
//		Option option = new Option();
//		option.setId(rs.getInt("id"));
//		option.setName(rs.getString("name"));
//		option.setPrice(rs.getInt("price"));
//		return option;
//	};
//
//	/**
//	 * 主キーから以下ユーザー情報を取得する。 ・お届け先情報（名前・住所・電話番号）
//	 */
//	public UserInfo findUserInfo(Integer id) {
//		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
//		String sql = "SELECT * FROM user_info WHERE id= :id";
//		UserInfo userInfo = template.queryForObject(sql, param, USERINFO_ROW_MAPPER);
//		return userInfo;
//	}
//
//	/**
//	 * ユーザーidからカートの中身 ※削除予定 public List<CartAndOptions> findItemsByUserId(Integer
//	 * userId){ String sql = "SELECT * FROM cart WHERE user_id= :id"; // + "INNER
//	 * JOIN cart_options " //+ "ON cart.id = cart_options.cart_id";
//	 * SqlParameterSource param = new MapSqlParameterSource().addValue("id",
//	 * userId); //List<CartAndOptions> cartAndoptionsList =
//	 * template.query(sql,param, CART_ROW_MAPPER);
//	 * 
//	 * //return cartAndoptionsList; }
//	 */
//
//	/** カートidをもとにオプションの値を取得 */
//	public List<CartOption> findByCartId(Integer cartId) {
//		String sql = "SELECT * FROM cart_options WHERE cart_id=:cartId;";
//		SqlParameterSource param = new BeanPropertySqlParameterSource(cartId);
//		return template.query(sql, param, CARTOPTION_ROW_MAPPER);
//	}
//
//	public Item findItems(Integer itemId) {
//		String sql = "SELECT * FROM items WHERE id=:itemId";
//		SqlParameterSource param = new BeanPropertySqlParameterSource(itemId);
//		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
//		return item;
//	}
//
//	public Option findOption(Integer optionId) {
//		String sql = "SELECT * FROM options WHERE id=:optionId";
//		SqlParameterSource param = new BeanPropertySqlParameterSource(optionId);
//		Option option = template.queryForObject(sql, param, OPTION_ROW_MAPPER);
//		return option;
//	}
//
//}
