package jp.co.example.ec202110d.service;

import java.util.ArrayList;

/**
 * 注文履歴を作成するリポジトリ
 */
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jp.co.example.ec202110d.domain.Cart;
import jp.co.example.ec202110d.domain.Item;
import jp.co.example.ec202110d.domain.OrderHistory;
import jp.co.example.ec202110d.domain.OrderdItems;
import jp.co.example.ec202110d.repository.ItemDetailRepository;
import jp.co.example.ec202110d.repository.OrderHistoryRepository;
import jp.co.example.ec202110d.repository.OrderdItemsRepository;

@Service
public class OrderHistoryService {

	@Autowired
	private OrderHistoryRepository orderHistoryRepository;
	@Autowired
	private OrderdItemsRepository orderdItemsRepository;
	@Autowired
	private ItemDetailRepository itemDetailRepository;
	@Autowired
	private CartAndOptionService cartAndOptionService;
	@Autowired
	private JavaMailSender sender;

	/** 注文履歴を全件取得 */
	public List<OrderHistory> getOrderHistory(Integer userId) {
		List<OrderHistory> orderHistoryList = orderHistoryRepository.getOrderHistory(userId);
		for (OrderHistory orderHistory : orderHistoryList) {
			List<OrderdItems> orderdItems = orderdItemsRepository.findByOrderId(orderHistory.getId());
			orderHistory.setOrderdItemsList(orderdItems);
		}
		for (OrderHistory orderHistory : orderHistoryList) {
			List<Item> itemList = new ArrayList<>();
			for (OrderdItems orderdItem : orderHistory.getOrderdItemsList()) {
				Item item = itemDetailRepository.findById(orderdItem.getItemId());
				itemList.add(item);
			}
			orderHistory.setItemList(itemList);
		}
		return orderHistoryList;
	}

	/** 注文履歴を追加 */
	public void insertOrderHistory(OrderHistory orderHistory) {
		Integer orderId = orderHistoryRepository.insertOrderInfo(orderHistory);
		List<Cart> cartList = cartAndOptionService.findByUserId(orderHistory.getUserId());
		for (Cart cart : cartList) {
			OrderdItems orderdItems = new OrderdItems();
			orderdItems.setItemId(cart.getItemId());
			orderdItems.setOrderId(orderId);
			orderdItems.setQuantity(cart.getPrice());
			orderdItemsRepository.insertOrderdItems(orderdItems);
		}

		// 注文確定メールを送信
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("yusuke.matsumoto@rakus-partners.co.jp");
			helper.setTo(orderHistory.getDestinationEmail());
			helper.setSubject("Rakuzon.co.jpでのご注文");
			helper.setText(orderHistory.getDestinationName() + "\nRakuzon.co.jpをご利用いただき、ありがとうございます。\n"
					+ "詳細は、以下URLよりログイン後、ご確認ください。\n" + "http://localhost:8080/order-history?userId="
					+ orderHistory.getUserId());
			sender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/** 今すぐ購入ボタンを押した際の処理 */
	public void BuyNow(OrderHistory orderHistory, Integer itemId) {
		Integer orderId = orderHistoryRepository.insertOrderInfo(orderHistory);
		OrderdItems items = new OrderdItems();
		items.setItemId(itemId);
		items.setOrderId(orderId);
		items.setQuantity(orderHistory.getTotal_price());
		orderdItemsRepository.insertOrderdItems(items);

		// 注文確定メールを送信
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("yusuke.matsumoto@rakus-partners.co.jp");
			helper.setTo(orderHistory.getDestinationEmail());
			helper.setSubject("Rakuzon.co.jpでのご注文");
			helper.setText(orderHistory.getDestinationName() + "\nRakuzon.co.jpをご利用いただき、ありがとうございます。\n"
					+ "詳細は、以下URLよりログイン後、ご確認ください。\n" + "http://localhost:8080/order-history?userId="
					+ orderHistory.getUserId());
			sender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
