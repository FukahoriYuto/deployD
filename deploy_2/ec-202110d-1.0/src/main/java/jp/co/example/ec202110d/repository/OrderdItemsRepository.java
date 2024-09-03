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

import jp.co.example.ec202110d.domain.OrderdItems;

/**
 * 注文した商品を操作するリポジトリ
 * @author yusukematsumoto
 *
 */
@Repository
public class OrderdItemsRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<OrderdItems> ORDERDITEMS_ROW_MAPPER
	= new BeanPropertyRowMapper<>(OrderdItems.class);
	
	/** 注文した商品を検索 */
	public List<OrderdItems> findByOrderId(Integer orderId){
		String sql = "SELECT * FROM orderd_items WHERE order_id=:orderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		List<OrderdItems> orderdItemsList = template.query(sql, param, ORDERDITEMS_ROW_MAPPER);
		return orderdItemsList;
	}
	
	public void insertOrderdItems(OrderdItems orderdItems) {
		String sql = "INSERT INTO orderd_items(item_id, order_id, quantity) "
				+ "VALUES(:itemId, :orderId, :quantity)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderdItems);
		template.update(sql, param);
	}
	

}
