package jp.co.example.ec202110d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ec202110d.domain.Item;
import jp.co.example.ec202110d.domain.Option;
import jp.co.example.ec202110d.domain.Review;
import jp.co.example.ec202110d.repository.ItemDetailRepository;
import jp.co.example.ec202110d.repository.OptionRepository;
import jp.co.example.ec202110d.repository.ReviewRepository;


/**
 * 商品詳細のService　今のところスルーするだけ
 * 
 * @author ootomokenji
 *
 */
@Service
@Transactional
public class ItemDetailService {

	@Autowired
	ItemDetailRepository itemDetailRepository;	
	
	@Autowired
	OptionRepository optionRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	
	/**
	 * idからItemを取得
	 * 
	 * @param id
	 * @return
	 */
	public Item findItemById(Integer id) {
		return itemDetailRepository.findById(id);
	}
	
	/**
	 * Optionの全件を検索
	 * @return
	 */
	public List<Option> findAllOption() {
		return optionRepository.findAllOption();
	}
	
	/**
	 * idからOptionを取得
	 * 
	 * @param id
	 * @return
	 */
	public Option findOptionById(Integer id) {
		return optionRepository.findOptionById(id);
	}

	
	/**
	 * レビュー一覧を取得します.
	 * 
	 * @return レビュー一覧情報
	 */
	public List<Review> findByItemId(Integer itemId) {
		return reviewRepository.findByItemId(itemId);
	}

	/**
	 * レビューを投稿します.
	 * 
	 * @param review レビュー情報
	 * @return 投稿したレビュー情報
	 */
	public void insert(Review review) {
		reviewRepository.insert(review);
	}

	/**
	 * レビューを削除します.
	 * 
	 * @param id 削除したいレビューID
	 */
	public void delete(Integer id) {
		reviewRepository.delete(id);
	}
	
	
	/**
	 * 役にたったボタンのカウント
	 * 
	 * @param itemId
	 * @return 商品ごとの役に立ったボタンを押された数
	 */
	public Integer searchLikeCount(Integer itemId) {
		return reviewRepository.searchLikeCount(itemId);
	}
	
	/**
	 * 役にたったボタンの更新
	 * 
	 * @param likeCount
	 * @return 更新された商品ごとの役に立ったボタンを押された数
	 */
	public void updateLikeCount(Integer searchLikeCount, Integer id) {
		reviewRepository.updateLikeCount(searchLikeCount, id);
	}
	
	/**
	 * 違反を報告するボタンのカウント
	 * 
	 * @param itemId
	 * @return 商品ごとの違反を報告するボタンを押された数
	 */
	public Integer searchDeleteCount(Integer itemId) {
		return reviewRepository.searchDeleteCount(itemId);
	}
	
	/**
	 * 違反を報告するボタンの更新
	 * 
	 * @param deleteCount
	 * @return 更新された商品ごとの違反を報告するボタンを押された数
	 */
	public void updateDeleteCount(Integer searchDeleteCount, Integer id) {
		reviewRepository.updateDeleteCount(searchDeleteCount, id);
	}

	/**
	 * 報告が１０件溜まったらコメントを更新
	 * 
	 * @param searchDeleteCount
	 * @param deleteComment
	 * @param id
	 * @return
	 */
	public void updateDeleteComment(String deleteComment, Integer id) {
		reviewRepository.updateDeleteComment(deleteComment, id);
	}
}
