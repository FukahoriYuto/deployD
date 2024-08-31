package jp.co.example.ec202110d.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.Item;

@Repository
public class TopPageRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	/**
	 * Itemオブジェクトを生成するローマッパー.
	 */
	public final static RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPrice(rs.getInt("price"));
		item.setImagePath1(rs.getString("image_path1"));
		item.setImagePath2(rs.getString("image_path2"));
		item.setImagePath3(rs.getString("image_path3"));
		return item;
	};
	
	/**
	 * 全件検索（ソートも可）
	 * 
	 * @return 全件検索の結果を金額の降順または昇順で表示
	 */
	public List<Item> findAllItem(Integer sortNum) {
		if(sortNum == 1) {
			String sql = "SELECT * FROM items ORDER BY price ASC";
			List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
			return itemList;
		} else {
			String sql = "SELECT * FROM items ORDER BY price DESC";
			List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
			return itemList;
		}
	}
	
	public List<Item> getItemList(HashMap<String, String> search,Integer sortNum) throws SQLException, IOException {
		int limit = Integer.valueOf(search.get("limit"));
		int page = Integer.valueOf(search.get("page")) - 1;
		String sql;
		if (sortNum == 1) {
			sql = "SELECT * FROM items ORDER BY price ASC LIMIT :limit OFFSET :offset";
		}else {
			sql = "SELECT * FROM items ORDER BY price DESC LIMIT :limit OFFSET :offset";
		}
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("limit", limit).addValue("offset", limit * page);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	public List<Item> getItemListByName(HashMap<String, String> search,String name,Integer sortNum) throws SQLException, IOException {
		int limit = Integer.valueOf(search.get("limit"));
		int page = Integer.valueOf(search.get("page")) - 1;
		String sql;
		if (sortNum == 1) {
			sql = "SELECT * FROM items WHERE name LIKE :name ORDER BY price ASC LIMIT :limit OFFSET :offset";
		}else {
			sql = "SELECT * FROM items WHERE name LIKE :name ORDER BY price DESC LIMIT :limit OFFSET :offset";
		}
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", '%'+name+'%') .addValue("limit", limit).addValue("offset", limit * page);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public int getItemListCount() {
		String sql = "SELECT count(*) FROM items";
		SqlParameterSource param = new MapSqlParameterSource();
		Integer sqlCount = template.queryForObject(sql, param, Integer.class);
		return sqlCount;
	}

	public int getItemListCountByName(String name) {
		String sql = "SELECT count(*) FROM items WHERE name LIKE :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", '%'+name+'%');
		Integer sqlCount = template.queryForObject(sql, param, Integer.class);
		return sqlCount;
	}
	
	/**
	 * 曖昧検索（金額の降順）
	 * 
	 * @param name 検索欄に入力された商品名の一部または全部
	 * @return 曖昧検索の結果を金額の降順で表示
	 */
	public List<Item> findByLikeName(String name, Integer sortNum) {
		if(sortNum == 1) {
			String sql = "SELECT * FROM items WHERE name LIKE :name ORDER BY price ASC";
			SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
			List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
			return itemList;
		} else {
			String sql = "SELECT * FROM items WHERE name LIKE :name ORDER BY price DESC";
			SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
			List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
			return itemList;
		}
	}
	
	
	
}
