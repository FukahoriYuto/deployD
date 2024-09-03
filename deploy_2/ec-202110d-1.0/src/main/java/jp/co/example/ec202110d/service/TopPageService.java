package jp.co.example.ec202110d.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ec202110d.domain.Item;
import jp.co.example.ec202110d.repository.TopPageRepository;

/**
 * ホーム画面用のサービスクラス
 * 
 * @author NobutakaYoshida
 *
 */
@Service
@Transactional
public class TopPageService {
	
	@Autowired
	private TopPageRepository topPageRepository;
	
	
	/**
	 * TopPageRepositoryに接続し、降順で全件検索
	 * 
	 * @return　全件検索された結果
	 */
	public List<Item> findAllItem(Integer sortNum) {
		return topPageRepository.findAllItem(sortNum);
	}
	
	
	/**
	 * TopPageRepositoryに接続し、検索欄に入力された文字から曖昧検索
	 * 
	 * @return　検索された曖昧検索結果
	 */
	public List<Item> findByLikeName(String name, Integer sortNum) {
		return topPageRepository.findByLikeName(name, sortNum);
	}
	
	
	public int getItemListCount() {
		return topPageRepository.getItemListCount();
	}
	
	public int getItemListCountByName(String name) {
		return topPageRepository.getItemListCountByName(name);
	}
	
	public List<Item> getItemList(HashMap<String, String> search,Integer sortNum) throws SQLException, IOException {
		return topPageRepository.getItemList(search,sortNum);
	}
	
	public List<Item> getItemListByName(HashMap<String, String> search,String name,Integer sortNum) throws SQLException, IOException {
		return topPageRepository.getItemListByName(search, name,sortNum);
	}
}
