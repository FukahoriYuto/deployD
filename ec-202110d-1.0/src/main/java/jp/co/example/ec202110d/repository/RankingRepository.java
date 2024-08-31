package jp.co.example.ec202110d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.Item;
import jp.co.example.ec202110d.domain.OrderdItems;
import jp.co.example.ec202110d.domain.Ranking;

/**
 * itemsテーブルとreviewsテーブルからデータを取得する。
 * @author iimura
 *
 */
@Repository
public class RankingRepository {
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	@Autowired
	private ItemDetailRepository itemDetailRepository;
	
	/** Itemオブジェクトを生成するローマッパー　*/
	private static final RowMapper<Item> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);
	
	/** Rankingオブジェクトを生成するローマッパー　*/
	private static final RowMapper<Ranking> RANKING_ROW_MAPPER = new BeanPropertyRowMapper<>(Ranking.class);
	
	/**
	 * itemsテーブルを全件検索。リストでitemテーブル内の値を全て取り出す。
	 * ※やってることitemDetailリポジトリと一緒だからいらない？
	 * @param itemId
	 * @return Item
	 
	public List<Item> getItemList(){
		String sql = "SELECT * FROM items"
				 	+ "WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Item> itemList = template.query(sql,ITEM_ROW_MAPPER);
		return itemList;
	}
	 */
	
	
	/**
	 * itemId列内の、item_idと同じitem_idの合計数を取得。
	 * @return 
	 */
	public List<Ranking> getOrderdItems() {
		//String sql = "SELECT item_id,COUNT(item_id) FROM orderd_items group by item_id";
		String sql= "SELECT item_id FROM orderd_items group by item_id ORDER BY count(item_id) DESC";
		// SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		List<Ranking> rankingList = template.query(sql,RANKING_ROW_MAPPER);
		for (Ranking ranking : rankingList) {
			Integer itemId = ranking.getItemId();
			Item item = itemDetailRepository.findById(itemId);
			ranking.setItem(item);
		}
		return rankingList;
	}
	
	/**
	public Item getItemList(itemId){
		String sql= "SELECT * FROM items";
		// SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	*/
	
		
}
