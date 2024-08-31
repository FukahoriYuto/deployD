package jp.co.example.ec202110d.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.Item;

/**
 * Itemsテーブルからデータを取得する
 * 
 * @author ootomokenji
 *
 */
@Repository
public class ItemDetailRepository {

	private static final RowMapper<Item>ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	/**
	 * 受け取った Item id からItemを探して返す
	 * @param id
	 * @return Item
	 */
	public Item findById(Integer id) {
		
		String sql = "SELECT id , name , description , price , image_path1, image_path2, image_path3 "
				+ " FROM items "
				+ " WHERE id = :id";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
}
