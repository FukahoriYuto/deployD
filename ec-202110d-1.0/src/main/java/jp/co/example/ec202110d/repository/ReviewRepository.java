package jp.co.example.ec202110d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ec202110d.domain.Review;

/**
 * reviewsテーブル操作用のリポジトリクラス.
 * 
 * @author NobutakaYoshida
 *
 */
@Repository
public class ReviewRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	public static final RowMapper<Review> REVIEW_ROW_MAPPER = (rs, i) -> {
		Review review = new Review();
		review.setId(rs.getInt("id"));
		review.setName(rs.getString("name"));
		review.setTitle(rs.getString("title"));
		review.setContent(rs.getString("content"));
		review.setItemId(rs.getInt("item_id"));
		review.setLikeCount(rs.getInt("like_count"));
		review.setDeleteCount(rs.getInt("delete_count"));
		review.setReviewPostDate(rs.getDate("review_post_date"));
		return review;
	};
	

	/**
	 * 商品IDを基にレビュー一覧を取得します.
	 * 
	 * @return 商品IDに基づいたレビュー一覧情報
	 */
	public List<Review> findByItemId(Integer itemId) {
		String sql = "SELECT * FROM reviews WHERE item_id = :itemId ORDER By id DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		List<Review> reviewList = template.query(sql, param, REVIEW_ROW_MAPPER);
		return reviewList;
	}

	/**
	 * レビューをインサートします.
	 * 
	 * @param review レビュー
	 * @return レビュー
	 */
	public void insert(Review review) {
		String insertSql = "INSERT INTO reviews(name, title, content, item_id) VALUES(:name, :title, :content, :itemId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(review);
		template.update(insertSql, param);
	}

	/**
	 * レビューをDBから削除する. 
	 * 
	 * @param id 削除したいレビューID
	 */
	public void delete(Integer id) {
		String deleteSql = "DELETE FROM reviews WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(deleteSql, param);
	}
	
	/**
	 * 役にたったボタンのカウント
	 * 
	 * @param itemId
	 * @return 商品ごとの役に立ったボタンを押された数
	 */
	public Integer searchLikeCount(Integer id) {
		String sql = "SELECT like_count FROM reviews WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Integer searchLikeCount = template.queryForObject(sql, param, Integer.class);
		return searchLikeCount;
	}
	
	/**
	 * 役にたったボタンの更新
	 * 
	 * @param likeCount
	 */
	public Integer updateLikeCount(Integer searchLikeCount, Integer id) {
		String updateLikeSql = "UPDATE reviews SET like_count = :likeCount WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("likeCount", searchLikeCount).addValue("id", id);
		return template.update(updateLikeSql, param);
	}
	
	/**
	 * 違反を報告するボタンのカウント
	 * 
	 * @param itemId
	 * @return 商品ごとの違反を報告するボタンを押された数
	 */
	public Integer searchDeleteCount(Integer id) {
		String sql = "SELECT delete_count FROM reviews WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Integer searchDeleteCount = template.queryForObject(sql, param, Integer.class);
		return searchDeleteCount;
	}
	
	/**
	 * 違反を報告するボタンの更新
	 * 
	 * @param deleteCount
	 */
	public void updateDeleteCount(Integer searchDeleteCount, Integer id) {
		String updateDeleteSql = "UPDATE reviews SET delete_count = :deleteCount WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("deleteCount", searchDeleteCount).addValue("id", id);
		template.update(updateDeleteSql, param);
	}
	
	/**
	 * 違反コメントの更新
	 * 
	 * @param deleteCount
	 */
	public void updateDeleteComment(String deleteComment, Integer id) {
		String updateDeleteSql = "UPDATE reviews SET content = :deleteComment WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("deleteComment", deleteComment).addValue("id", id);
		template.update(updateDeleteSql, param);
	}
}