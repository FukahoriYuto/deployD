package jp.co.example.ec202110d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.OrderHistory;

/**
 * 1回のオーダーを操作するリポジトリ
 * 
 * @author yusukematsumoto
 *
 */
@Repository
public class OrderHistoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<OrderHistory> ORDERHISTORY_ROW_MAPPER = new BeanPropertyRowMapper<>(
			OrderHistory.class);

	/** ユーザーIDをもとに注文履歴を全件取得 */
	public List<OrderHistory> getOrderHistory(Integer userId) {
		String sql = "SELECT * FROM order_info WHERE user_id=:userId ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<OrderHistory> orderHistory = template.query(sql, param, ORDERHISTORY_ROW_MAPPER);
		return orderHistory;
	}

	private Integer findLastId() {
		String sql = "SELECT * FROM order_info WHERE id = (SELECT  MAX(id) FROM order_info);";
		SqlParameterSource param = new MapSqlParameterSource();
		try {
			OrderHistory orderHistory = template.queryForObject(sql, param, ORDERHISTORY_ROW_MAPPER);
			return orderHistory.getId();
		} catch (Exception e) {
			return 1;
		}
	}

	/** オーダーの追加 */
	public Integer insertOrderInfo(OrderHistory orderInfo) {
		String sql = "INSERT INTO order_info(user_id, status, total_price, order_date, "
				+ "destination_name, destination_email, destination_zipcode, destination_address, "
				+ "destination_tel, payment_method) "
				+ "VALUES(:userId, :status, :total_price, :order_date, :destinationName, "
				+ ":destinationEmail, :destinationZipcode, :destinationAddress, :destinationTel, :paymentMethod);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderInfo);
		template.update(sql, param);
		return findLastId();
	}

}
