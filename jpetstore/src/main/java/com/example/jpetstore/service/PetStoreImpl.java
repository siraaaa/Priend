package com.example.jpetstore.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpetstore.dao.AccountDao;
import com.example.jpetstore.dao.AdDao;
import com.example.jpetstore.dao.AnswerDao;
import com.example.jpetstore.dao.AuctionDao;
import com.example.jpetstore.dao.AuctionItemDao;
import com.example.jpetstore.dao.BlackListDao;
import com.example.jpetstore.dao.CategoryDao;
import com.example.jpetstore.dao.GeneralItemDao;
import com.example.jpetstore.dao.ItemDao;
import com.example.jpetstore.dao.LetterDao;
import com.example.jpetstore.dao.LineItemDao;
import com.example.jpetstore.dao.MileageDao;
import com.example.jpetstore.dao.OrderDao;
import com.example.jpetstore.dao.PostDao;
import com.example.jpetstore.dao.ProductDao;
import com.example.jpetstore.dao.QAndADao;
import com.example.jpetstore.dao.QuestionDao;
import com.example.jpetstore.dao.SequenceDao;
import com.example.jpetstore.domain.Account;
import com.example.jpetstore.domain.Advertisement;
import com.example.jpetstore.domain.Answer;
import com.example.jpetstore.domain.AuctionItem;
import com.example.jpetstore.domain.Bid;
import com.example.jpetstore.domain.BlackList;
import com.example.jpetstore.domain.Category;
import com.example.jpetstore.domain.GeneralItem;
import com.example.jpetstore.domain.Inventory;
import com.example.jpetstore.domain.Item;
import com.example.jpetstore.domain.Letter;
import com.example.jpetstore.domain.Mileage;
import com.example.jpetstore.domain.MySales;
import com.example.jpetstore.domain.Order;
import com.example.jpetstore.domain.Post;
import com.example.jpetstore.domain.Product;
import com.example.jpetstore.domain.QAndA;
import com.example.jpetstore.domain.Question;


@Service
@Transactional
public class PetStoreImpl implements PetStoreFacade { 
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private AdDao adDao;
	@Autowired
	private AuctionDao auctionDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private QAndADao qAndADao;
	@Autowired
	private MileageDao mileageDao;
	@Autowired
	private BlackListDao blackListDao;
	@Autowired
	private GeneralItemDao generalItemDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private AnswerDao answerDao;
	@Autowired
	private AuctionItemDao auctionItemDao;
	@Autowired		// applicationContext.xml�� ���ǵ� scheduler ��ü�� ���� ����
	private ThreadPoolTaskScheduler scheduler;
	@Autowired
	private LineItemDao lineItemDao;
	@Autowired
	private SequenceDao sequenceDao;
	//  쪽지 추가
	@Autowired
	private LetterDao letterDao;

	//-------------------------------------------------------------------------
	// Operation methods, implementing the PetStoreFacade interface
	//-------------------------------------------------------------------------

	@Autowired(required=false)
	private JavaMailSender mailSender;
	
	public Account getAccount(String username) {
		return accountDao.getAccount(username);
	}

	public Account getAccount(String username, String password) {
		return accountDao.getAccount(username, password);
	}

	public void insertAccount(Account account) {
		accountDao.insertAccount(account);
	}

	public void updateAccount(Account account) {
		accountDao.updateAccount(account);
	}

	public List<String> getUsernameList() {
		return accountDao.getUsernameList();
	}

	public List<Category> getCategoryList() {
		return categoryDao.getCategoryList();
	}

	public Category getCategory(String categoryId) {
		return categoryDao.getCategory(categoryId);
	}

	public List<Product> getProductListByCategory(String categoryId) {
		return productDao.getProductListByCategory(categoryId);
	}

	public Product getProduct(String productId) {
		return productDao.getProduct(productId);
	}

	public List<Item> getItemListByProduct(String productId) {
		return itemDao.getItemListByProduct(productId);
	}

	public Item getItem(String itemId) {
		return itemDao.getItem(itemId);
	}

	public boolean isItemInStock(String itemId) {
		return itemDao.isItemInStock(itemId);
	}

	public void insertOrder(Order order) {
		itemDao.updateQuantity(order);	    
		orderDao.insertOrder(order);
	}
	
	public Order getOrder(int orderId) {
		return orderDao.getOrder(orderId);
	}

	public List<Order> getOrdersByUsername(String username) {
		return orderDao.getOrdersByUsername(username);
	}

	//180523 �߰�
	public void insertAd(Advertisement Ad) {
		adDao.insertAd(Ad);
	}
	
	public List<MySales> getItemListByUserid(String userid) {
		return itemDao.getItemListByUserid(userid);
	}
	
	public void updateAdStatus(String itmeId, String adStatus) {
		itemDao.updateAdStatus(itmeId, adStatus);
	}

	public List<Advertisement> advertise(String today) {
		return adDao.advertise(today);
	}
	
	public List<Advertisement> favAdvertise(String favcategory, String today) {
		return adDao.favAdvertise(favcategory, today);
	}
	
	public void deleteAdByItemId(String itemId) {
		adDao.deleteAdByItemId(itemId);
		
	}

	public List<Item> searchItemList(String keywords, String array, int priceMax, int priceMin, String option) {
		return itemDao.searchItemList(keywords, array, priceMax, priceMin, option);
	}
	
	public List<Bid> getAuctionItemsByUsername(String username) {
		return auctionDao.getAuctionItemsByUsername(username);
	}
	public int getHighestBidPrice(String auctionId) {
		return auctionDao.getHighestBidPrice(auctionId);
	}

	public List<Post> getReviewByUsername(String username) {
		return postDao.getReviewByUsername(username);
	}

	public List<QAndA> getQuestionByUsername(String username) {
		return qAndADao.getQuestionByUsername(username);
	}
	
	public List<QAndA> getAnswerByUsername(String username) {
		return qAndADao.getAnswerByUsername(username);
	}

	public List<Mileage> getMileageListByUsername(String username) {
		return mileageDao.getMileageListByUsername(username);
	}

	//180530 �߰�
	public void removeFromBlackList(String username) {
		accountDao.removeFromBlackList(username);
	}
	
	public BlackList getDetail(String username) {
		return blackListDao.getDetail(username);
	}
	
	public void removeDetail() {
		blackListDao.removeDetail();
	}

	@Override
	public List<String> getUserOnBlackList() {
		return accountDao.getUserOnBlackList();
	}

	@Override
	public List<BlackList> getReasonByUsername(String username) {
		return blackListDao.getReasonByUsername(username);
	}

	@Override
	public List<Advertisement> getApprovalList() {
		
		return adDao.getApprovalList();
	}
	
	
	
	
	//180621 ��� �󼼺���/����/���� �߰�
	@Override
	public AuctionItem getAuctionItem(String itemId) {
		// TODO Auto-generated method stub
		return auctionItemDao.getAuctionItem(itemId);
	}

	@Override
	public List<Bid> getBidByAuctionId(String auctionId) {
		// TODO Auto-generated method stub
		return auctionItemDao.getBidByAuctionId(auctionId);
	}

	@Override
	public Integer getBidPriceByUsername(String bidder, String auctionId) {
		// TODO Auto-generated method stub
		return auctionDao.getBidPriceByUsername(bidder, auctionId);
	}

	@Override
	public void updateBid(String bidder, int price) {
		// TODO Auto-generated method stub
		auctionItemDao.updateBid(bidder, price);
	}

	@Override
	public void insertBid(Bid bid) {
		// TODO Auto-generated method stub
		auctionItemDao.insertBid(bid);
	}

	@Override
	public void deleteBid(String bidder, String auctionId) {
		auctionItemDao.deleteBid(bidder, auctionId);
		
	}

	@Override
	public void updateItemStatus(String itemId, String status) {
		// TODO Auto-generated method stub
		itemDao.updateItemStatus(itemId, status);
	}

	@Override
	public void endAuctionBySeller(String auctionId) {
		// TODO Auto-generated method stub
		auctionItemDao.endAuctionBySeller(auctionId);
	}

	@Override
	public Bid getHighestBidder(String auctionId) {
		// TODO Auto-generated method stub
		return auctionItemDao.getHighestBidder(auctionId);
	}
	

	
	public void auctionedOffScheduler(Date auctionedOffTime, AuctionItem auctionItem, int itemId) {
		//�ϴ� itemid �ְ� item id �ҷ��� �� Auction ���̺�� Inventory ���̺� itemid ����
		auctionItem.setItemId(String.valueOf(itemId));
		auctionItemDao.insertAuctionItem(auctionItem);
		auctionItem.setAuctionId(String.valueOf(sequenceDao.getNextId("seq_auction")));
		auctionItemDao.insertAuction(auctionItem);
		Inventory inventory = new Inventory();
		inventory.setItemId(auctionItem.getItemId());
		inventory.setQuantity(1);
	    //auctionItemDao.insertQuantity(inventory);
	    auctionItemDao.insertQuantity(auctionItem);
	    
		Runnable updateTableRunner = new Runnable() {	
			// anonymous class ����
			@Override
			public void run() {   // �����췯�� ���� �̷��� Ư�� ������ ����� �۾��� ����				
				itemDao.updateItemStatus(auctionItem.getItemId(), "off");
				
				Bid bidder = auctionItemDao.getHighestBidder(auctionItem.getAuctionId());

				if(bidder != null) {
					//����..
					sendAuctionMail(bidder.getBidder(), auctionedOffTime,auctionItem.getItemId(), bidder.getBid_price());
					
					//
					Calendar c = Calendar.getInstance();
					c.setTime(auctionedOffTime);
//					c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+2);
					c.set(Calendar.DATE, c.get(Calendar.DATE)+3);
					bidderScheduler(c.getTime(), bidder.getBidder(), auctionItem.getItemId(), auctionItem.getAuctionId(), bidder.getBid_price());
//					System.out.print("3�� �����ٷ� ����");
				}
			}
		};
		scheduler.schedule(updateTableRunner, auctionedOffTime);  
		
	}

	@Override
	public void bidderScheduler(Date deadline, String bidder, String itemId, String auctionId, int price) {
		// TODO Auto-generated method stub
		Runnable updateTableRunner2 = new Runnable() {	
			// anonymous class ����
			@Override
			public void run() {   // �����췯�� ���� �̷��� Ư�� ������ ����� �۾��� ����				
				int num = lineItemDao.getLineItemByItemId(itemId);
				if(num != 0) {
					return;
				}
				auctionItemDao.deleteBid(bidder, auctionId);
				Bid newBidder = auctionItemDao.getHighestBidder(auctionId);
				if(newBidder != null) {

					Calendar c = Calendar.getInstance();
					c.setTime(deadline);
					c.set(Calendar.DATE, c.get(Calendar.DATE)+3);
//					c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+2);
					bidderScheduler(c.getTime(), newBidder.getBidder(), itemId, auctionId, newBidder.getBid_price());

				}
			}
		};
		sendAuctionMail(bidder, deadline, auctionId, price);
		scheduler.schedule(updateTableRunner2, deadline);
	}

	
	public void advertisementScheduler(Advertisement ad, Account account) {
		Runnable updateTableRunner3 = new Runnable() {	
			// anonymous class ����
			@Override
			public void run() {   // �����췯�� ���� �̷��� Ư�� ������ ����� �۾��� ����				
				itemDao.updateAdStatus(ad.getItemid(), "0");
				adDao.deleteAdByItemId(ad.getItemid());
				sendAdMail(ad, account);
			}
		};
		adDao.insertAd(ad);
		itemDao.updateAdStatus(ad.getItemid(), "1");
		Calendar c = Calendar.getInstance();
//		c.set(Calendar.DATE, c.get(Calendar.DATE)+3);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+2);
		scheduler.schedule(updateTableRunner3, c.getTime());
	}
	
	public void sendAuctionMail(String bidder, Date deadline, String itemId, int price) {
		String setfrom = "springTest45@gmail.com";
		String title="Congratulations! Come and meet your new friend!";
		String tomail = accountDao.getAccount(bidder).getEmail();
		String to_msg = bidder+"'s Bid";
		String startDate = deadline.toString();
		Calendar c = Calendar.getInstance();
		c.setTime(deadline);
		c.set(Calendar.DATE, c.get(Calendar.DATE)+3);
		String endDate = c.getTime().toString();
		Item item = itemDao.getItem(itemId);
		

//		System.out.println("content : "+title+address+numberOfPerson+building+time+timeSelect+roomType+beam+beamSelect+date+notebook+notebookSelect);


//		String htmlContent = "<strong>Thank you for your order!</strong>";
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><!--[if IE]><html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"ie\"><![endif]--><!--[if !IE]><!--><html style=\"margin: 0;padding: 0;\" xmlns=\"http://www.w3.org/1999/xhtml\"><!--<![endif]--><head>\n" + 
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" + 
				"  </head>\n" + 
				"   <body class=\"full-padding\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;\">\n" + 
				"<!--<![endif]-->\n" + 
				"    <table class=\"wrapper\" style=\"border-collapse: collapse;table-layout: fixed;min-width: 320px;width: 100%;background-color: #f6f6f6;\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tbody><tr><td>\n" + 
				"      <div role=\"banner\">\n" + 
				"        <div class=\"preheader\" style=\"Margin: 0 auto;max-width: 560px;min-width: 280px; width: 280px;width: calc(28000% - 167440px);\">\n" + 
				"          <div style=\"border-collapse: collapse;display: table;width: 100%;\">\n" + 
				"          <!--[if (mso)|(IE)]><table align=\"center\" class=\"preheader\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 280px\" valign=\"top\"><![endif]-->\n" + 
				"            <div class=\"snippet\" style=\"display: table-cell;Float: left;font-size: 12px;line-height: 19px;max-width: 280px;min-width: 140px; width: 140px;width: calc(14000% - 78120px);padding: 10px 0 5px 0;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;\">\n" + 
				"              \n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td><td style=\"width: 280px\" valign=\"top\"><![endif]-->\n" + 
				"            <div class=\"webversion\" style=\"display: table-cell;Float: left;font-size: 12px;line-height: 19px;max-width: 280px;min-width: 139px; width: 139px;width: calc(14100% - 78680px);padding: 10px 0 5px 0;text-align: right;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;\">\n" + 
				"              \n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"          </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"header\" style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);\" id=\"emb-email-header-container\">\n" + 
				"        <!--[if (mso)|(IE)]><table align=\"center\" class=\"header\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 600px\"><![endif]-->\n" + 
				"          <div class=\"logo emb-logo-margin-box\" style=\"font-size: 26px;line-height: 32px;Margin-top: 6px;Margin-bottom: 20px;color: #c3ced9;font-family: Roboto,Tahoma,sans-serif;Margin-left: 20px;Margin-right: 20px;\" align=\"center\">\n" + 
				"            <div class=\"logo-center\" align=\"center\" id=\"emb-email-header\"><img style=\"display: block;height: auto;width: 100%;border: 0;max-width: 86px;\" src=\"https://i.imgur.com/Ik0ZVdr.png\" alt=\"\" width=\"86\" /></div>\n" + 
				"          </div>\n" + 
				"        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"        </div>\n" + 
				"      </div>\n" + 
				"      <div role=\"section\">\n" + 
				"      <div class=\"layout one-col fixed-width\" style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;\">\n" + 
				"        <div class=\"layout__inner\" style=\"border-collapse: collapse;display: table;width: 100%;background-color: #ffffff;\" emb-background-style>\n" + 
				"        <!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-fixed-width\" emb-background-style><td style=\"width: 600px\" class=\"w560\"><![endif]-->\n" + 
				"          <div class=\"column\" style=\"text-align: left;color: #8e8e8e;font-size: 16px;line-height: 24px;font-family: PT Sans,Trebuchet MS,sans-serif;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);\">\n" + 
				"        \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 24px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;mso-text-raise: 4px;\">\n" + 
				"        <p style=\"Margin-top: 0;Margin-bottom: 20px;text-align: center;\">Congratulations! Come and meet your new friend!</p>\n" + 
				"      </div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"        <div style=\"font-size: 12px;font-style: normal;font-weight: normal;line-height: 19px;\" align=\"center\">\n" + 
				"          <img style=\"border: 0;display: block;height: auto;width: 100%;max-width: 81px;\" alt=\"\" width=\"81\" src=\"https://i.imgur.com/IjH65Q8.png\" />\n" + 
				"        </div>\n" + 
				"      \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 20px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;line-height: 20px;font-size: 1px;\">&nbsp;</div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;line-height: 25px;font-size: 1px;\">&nbsp;</div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;mso-text-raise: 4px;\">\n" + 
				"        <p class=\"size-16\" style=\"Margin-top: 0;Margin-bottom: 20px;font-size: 16px;line-height: 24px;text-align: center;\" lang=\"x-size-16\">Check Out Your Bid.</p>\n" + 
				"      </div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;line-height: 20px;font-size: 1px;\">&nbsp;</div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"        <div style=\"font-size: 12px;font-style: normal;font-weight: normal;line-height: 19px;\" align=\"center\">\n" + 
				"          <img style=\"border: 0;display: block;height: auto;width: 100%;max-width: 174px;\" alt=\"\" width=\"174\" src=\"https://i.imgur.com/ss56Krv.png\" />\n");
				sb.append(to_msg+
						"      \n"+"<p style=\"Margin-top: 20px;Margin-bottom: 0;\">Start Date</p>");
				sb.append(startDate);
				sb.append("<p style=\"Margin-top: 20px;Margin-bottom: 0;\">End Date</p>");
				sb.append(endDate);
				sb.append("<p></p><table style=\'text-align:center\'><tr><td>Product</td><td>Item Title</td><td>Seller</td><td>Price</td><tr>");

				sb.append("<tr><td>"+item.getProduct()+"</td><td>"+item.getItemTitle()+"</td><td>"+item.getSeller()+"</td><td>"+item.getUnitCost()+"</td><tr>");

				sb.append(
				"        </table><p></p></div>\n" + 
				"      \n" + 
				"          </div>\n" + 
				"        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"        </div>\n" + 
				"      </div>\n");

				sb.append(
				"      <div style=\"mso-line-height-rule: exactly;\" role=\"contentinfo\">\n" + 
				"        <div class=\"layout email-footer\" style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;\">\n" + 
				"          <div class=\"layout__inner\" style=\"border-collapse: collapse;display: table;width: 100%;\">\n" + 
				"          <!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 400px;\" valign=\"top\" class=\"w360\"><![endif]-->\n" + 
				"            <div class=\"column wide\" style=\"text-align: left;font-size: 12px;line-height: 19px;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;Float: left;max-width: 400px;min-width: 320px; width: 320px;width: calc(8000% - 47600px);\">\n" + 
				"              <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 10px;Margin-bottom: 10px;\">\n" + 
				"                \n" + 
				"                <div style=\"font-size: 12px;line-height: 19px;\">\n" + 
				"                  <div>GSITM</div>\n" + 
				"                </div>\n" + 
				"                <div style=\"font-size: 12px;line-height: 19px;Margin-top: 18px;\">\n" + 
				"                  <div>&#51060; &#47700;&#51068;&#51008; &#48156;&#49888;&#51204;&#50857;&#47700;&#51068;&#51077;&#45768;&#45796;.</div>\n" + 
				"                </div>\n" + 
				"                <!--[if mso]>&nbsp;<![endif]-->\n" + 
				"              </div>\n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td><td style=\"width: 200px;\" valign=\"top\" class=\"w160\"><![endif]-->\n" + 
				"            <div class=\"column narrow\" style=\"text-align: left;font-size: 12px;line-height: 19px;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;Float: left;max-width: 320px;min-width: 200px; width: 320px;width: calc(72200px - 12000%);\">\n" + 
				"              <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 10px;Margin-bottom: 10px;\">\n" + 
				"                \n" + 
				"              </div>\n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"          </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"layout one-col email-footer\" style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;\">\n" + 
				"          <div class=\"layout__inner\" style=\"border-collapse: collapse;display: table;width: 100%;\">\n" + 
				"          <!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 600px;\" class=\"w560\"><![endif]-->\n" + 
				"            <div class=\"column\" style=\"text-align: left;font-size: 12px;line-height: 19px;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);\">\n" + 
				"              <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 10px;Margin-bottom: 10px;\">\n" + 
				"                <div style=\"font-size: 12px;line-height: 19px;\">\n" + 
				"                  <unsubscribe style=\"text-decoration: underline;\">Unsubscribe</unsubscribe>\n" + 
				"                </div>\n" + 
				"              </div>\n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"          </div>\n" + 
				"        </div>\n" + 
				"      </div>\n" + 
				"      <div style=\"mso-line-height-rule: exactly;line-height: 40px;font-size: 40px;\">&nbsp;</div>\n" + 
				"    </div></td></tr></tbody></table>\n" + 
				"  \n" + 
				"</body></html>");
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); 
			messageHelper.setTo(tomail); 
			messageHelper.setSubject(title);
			messageHelper.setText(sb.toString(),true); 

			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendAdMail(Advertisement ad, Account acc) {
		String setfrom = "springTest45@gmail.com";
		String title="���� ��û�� ��ҵǾ����ϴ�.";
		String tomail = acc.getEmail();
		String to_msg = acc.getUser_id()+"'s Request";
		String startDate = ad.getStartDate();
		String endDate = ad.getEndDate();
		
		

		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><!--[if IE]><html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"ie\"><![endif]--><!--[if !IE]><!--><html style=\"margin: 0;padding: 0;\" xmlns=\"http://www.w3.org/1999/xhtml\"><!--<![endif]--><head>\n" + 
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" + 
				"  </head>\n" + 
				"   <body class=\"full-padding\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;\">\n" + 
				"<!--<![endif]-->\n" + 
				"    <table class=\"wrapper\" style=\"border-collapse: collapse;table-layout: fixed;min-width: 320px;width: 100%;background-color: #f6f6f6;\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tbody><tr><td>\n" + 
				"      <div role=\"banner\">\n" + 
				"        <div class=\"preheader\" style=\"Margin: 0 auto;max-width: 560px;min-width: 280px; width: 280px;width: calc(28000% - 167440px);\">\n" + 
				"          <div style=\"border-collapse: collapse;display: table;width: 100%;\">\n" + 
				"          <!--[if (mso)|(IE)]><table align=\"center\" class=\"preheader\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 280px\" valign=\"top\"><![endif]-->\n" + 
				"            <div class=\"snippet\" style=\"display: table-cell;Float: left;font-size: 12px;line-height: 19px;max-width: 280px;min-width: 140px; width: 140px;width: calc(14000% - 78120px);padding: 10px 0 5px 0;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;\">\n" + 
				"              \n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td><td style=\"width: 280px\" valign=\"top\"><![endif]-->\n" + 
				"            <div class=\"webversion\" style=\"display: table-cell;Float: left;font-size: 12px;line-height: 19px;max-width: 280px;min-width: 139px; width: 139px;width: calc(14100% - 78680px);padding: 10px 0 5px 0;text-align: right;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;\">\n" + 
				"              \n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"          </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"header\" style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);\" id=\"emb-email-header-container\">\n" + 
				"        <!--[if (mso)|(IE)]><table align=\"center\" class=\"header\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 600px\"><![endif]-->\n" + 
				"          <div class=\"logo emb-logo-margin-box\" style=\"font-size: 26px;line-height: 32px;Margin-top: 6px;Margin-bottom: 20px;color: #c3ced9;font-family: Roboto,Tahoma,sans-serif;Margin-left: 20px;Margin-right: 20px;\" align=\"center\">\n" + 
				"            <div class=\"logo-center\" align=\"center\" id=\"emb-email-header\"><img style=\"display: block;height: auto;width: 100%;border: 0;max-width: 86px;\" src=\"https://i.imgur.com/Ik0ZVdr.png\" alt=\"\" width=\"86\" /></div>\n" + 
				"          </div>\n" + 
				"        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"        </div>\n" + 
				"      </div>\n" + 
				"      <div role=\"section\">\n" + 
				"      <div class=\"layout one-col fixed-width\" style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;\">\n" + 
				"        <div class=\"layout__inner\" style=\"border-collapse: collapse;display: table;width: 100%;background-color: #ffffff;\" emb-background-style>\n" + 
				"        <!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-fixed-width\" emb-background-style><td style=\"width: 600px\" class=\"w560\"><![endif]-->\n" + 
				"          <div class=\"column\" style=\"text-align: left;color: #8e8e8e;font-size: 16px;line-height: 24px;font-family: PT Sans,Trebuchet MS,sans-serif;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);\">\n" + 
				"        \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 24px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;mso-text-raise: 4px;\">\n" + 
				"        <p style=\"Margin-top: 0;Margin-bottom: 20px;text-align: center;\">����Ḧ ���� �ȿ� ���� ���ϼ̽��ϴ�.</p>\n" + 
				"      </div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"        <div style=\"font-size: 12px;font-style: normal;font-weight: normal;line-height: 19px;\" align=\"center\">\n" + 
				"          <img style=\"border: 0;display: block;height: auto;width: 100%;max-width: 81px;\" alt=\"\" width=\"81\" src=\"https://i.imgur.com/IjH65Q8.png\" />\n" + 
				"        </div>\n" + 
				"      \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 20px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;line-height: 20px;font-size: 1px;\">&nbsp;</div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;line-height: 25px;font-size: 1px;\">&nbsp;</div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;mso-text-raise: 4px;\">\n" + 
				"        <p class=\"size-16\" style=\"Margin-top: 0;Margin-bottom: 20px;font-size: 16px;line-height: 24px;text-align: center;\" lang=\"x-size-16\">Check Out Your Request.</p>\n" + 
				"      </div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"            <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" + 
				"      <div style=\"mso-line-height-rule: exactly;line-height: 20px;font-size: 1px;\">&nbsp;</div>\n" + 
				"    </div>\n" + 
				"        \n" + 
				"        <div style=\"font-size: 12px;font-style: normal;font-weight: normal;line-height: 19px;\" align=\"center\">\n" + 
				"          <img style=\"border: 0;display: block;height: auto;width: 100%;max-width: 174px;\" alt=\"\" width=\"174\" src=\"https://i.imgur.com/ss56Krv.png\" />\n");
				sb.append(to_msg+
						"      \n"+"<p style=\"Margin-top: 20px;Margin-bottom: 0;\">Start Date</p>");
				sb.append(startDate);
				sb.append("<p style=\"Margin-top: 20px;Margin-bottom: 0;\">End Date</p>");
				sb.append(endDate);
				sb.append("<p></p><table style=\'text-align:center\'><tr><td>Product</td><td>Ad Title</td><td>Description</td><td>Price</td><tr>");

				sb.append("<tr><td>"+ad.getFavCategory()+"</td><td>"+ad.getTitle()+"</td><td>"+ad.getDescription()+"</td><td>"+ad.getBannerPrice()+"</td><tr>");

				sb.append(
				"        </table><p></p></div>\n" + 
				"      \n" + 
				"          </div>\n" + 
				"        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"        </div>\n" + 
				"      </div>\n");

				sb.append(
				"      <div style=\"mso-line-height-rule: exactly;\" role=\"contentinfo\">\n" + 
				"        <div class=\"layout email-footer\" style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;\">\n" + 
				"          <div class=\"layout__inner\" style=\"border-collapse: collapse;display: table;width: 100%;\">\n" + 
				"          <!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 400px;\" valign=\"top\" class=\"w360\"><![endif]-->\n" + 
				"            <div class=\"column wide\" style=\"text-align: left;font-size: 12px;line-height: 19px;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;Float: left;max-width: 400px;min-width: 320px; width: 320px;width: calc(8000% - 47600px);\">\n" + 
				"              <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 10px;Margin-bottom: 10px;\">\n" + 
				"                \n" + 
				"                <div style=\"font-size: 12px;line-height: 19px;\">\n" + 
				"                  <div>GSITM</div>\n" + 
				"                </div>\n" + 
				"                <div style=\"font-size: 12px;line-height: 19px;Margin-top: 18px;\">\n" + 
				"                  <div>&#51060; &#47700;&#51068;&#51008; &#48156;&#49888;&#51204;&#50857;&#47700;&#51068;&#51077;&#45768;&#45796;.</div>\n" + 
				"                </div>\n" + 
				"                <!--[if mso]>&nbsp;<![endif]-->\n" + 
				"              </div>\n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td><td style=\"width: 200px;\" valign=\"top\" class=\"w160\"><![endif]-->\n" + 
				"            <div class=\"column narrow\" style=\"text-align: left;font-size: 12px;line-height: 19px;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;Float: left;max-width: 320px;min-width: 200px; width: 320px;width: calc(72200px - 12000%);\">\n" + 
				"              <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 10px;Margin-bottom: 10px;\">\n" + 
				"                \n" + 
				"              </div>\n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"          </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"layout one-col email-footer\" style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;\">\n" + 
				"          <div class=\"layout__inner\" style=\"border-collapse: collapse;display: table;width: 100%;\">\n" + 
				"          <!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 600px;\" class=\"w560\"><![endif]-->\n" + 
				"            <div class=\"column\" style=\"text-align: left;font-size: 12px;line-height: 19px;color: #8e8e8e;font-family: PT Sans,Trebuchet MS,sans-serif;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);\">\n" + 
				"              <div style=\"Margin-left: 20px;Margin-right: 20px;Margin-top: 10px;Margin-bottom: 10px;\">\n" + 
				"                <div style=\"font-size: 12px;line-height: 19px;\">\n" + 
				"                  <unsubscribe style=\"text-decoration: underline;\">Unsubscribe</unsubscribe>\n" + 
				"                </div>\n" + 
				"              </div>\n" + 
				"            </div>\n" + 
				"          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
				"          </div>\n" + 
				"        </div>\n" + 
				"      </div>\n" + 
				"      <div style=\"mso-line-height-rule: exactly;line-height: 40px;font-size: 40px;\">&nbsp;</div>\n" + 
				"    </div></td></tr></tbody></table>\n" + 
				"  \n" + 
				"</body></html>");
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); 
			messageHelper.setTo(tomail); 
			messageHelper.setSubject(title);
			messageHelper.setText(sb.toString(),true); 

			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//블랙리스트
	@Override
	public void insertBlackList(BlackList black) {
		// TODO Auto-generated method stub
		blackListDao.insertBlackList(black);
	}

	@Override
	public BlackList getBlack(String blackId) {
		// TODO Auto-generated method stub
		return blackListDao.getBlack(blackId);
	}
	
	
	
	/* ----------------------------20180623 수정---------------------------- */
	
	
	//일반 아이템 등록
	@Override
	@Transactional
	public void insertGeneralItemAll(GeneralItem generalItem) {
		generalItemDao.insertGeneralItem(generalItem);
		generalItemDao.insertQuantity(generalItem);
		
	}
	/*@Override
	public void insertQuantity(Inventory inventory) {
		// TODO Auto-generated method stub
		generalItemDao.insertQuantity(inventory);
	}*/
	
	//경매 아이템 등록
	@Transactional
	public void insertAuctionItemAll(AuctionItem auctionItem) {
		 auctionItemDao.insertAuctionItem(auctionItem);
		 auctionItemDao.insertAuction(auctionItem);
		 auctionItemDao.insertQuantity(auctionItem);
	}
	
/*	public void insertAuction(AuctionItem auctionItem) {
		auctionItemDao.insertAuction(auctionItem);
	}
	*/
		
	//아이템 수정
	public GeneralItem getGeneralItem(String sitemId) {
		return generalItemDao.getGeneralItem(sitemId);
	}
	
	@Transactional
	public void updateGeneralItemAll(GeneralItem generalItem) {
		generalItemDao.updateGeneralItem(generalItem);
		generalItemDao.updateQuantity(generalItem);
	}
	
	@Transactional
	public void updateAuctionItemAll(AuctionItem autionItem) {
		auctionItemDao.updateAuctionItem(autionItem);
		auctionItemDao.updateAuctionInfo(autionItem);
		auctionItemDao.updateQuantity(autionItem);
	}
	
	
	//아이템 삭제
	@Transactional
	public void deleteItemAll(String itemId) {
		itemDao.deleteInventoryFromTable(itemId);
		itemDao.deleteQuestionFromTable(itemId);
		itemDao.deleteItemFromTable(itemId);//가장 마지막에 실행
	}
	
	@Transactional
	public void deleteAuctionInfoAll(String auctionId) {
		itemDao.deleteBidFromTable(auctionId);
		itemDao.deleteAuctionFromTable(auctionId);
	}
	
	//하이차트를 위한 값
	public List<Bid> bidValuesForChart(String auctionId){
		return auctionDao.bidValuesForChart(auctionId);
	}
	
	public List<Bid> getBidByAuctionIdASC (String auctionId){
		return auctionDao.getBidByAuctionIdASC(auctionId);
	}
	
	
	//큐앤에이
	@Override
	public List<Answer> getAnswerList(String questionId){
		return answerDao.getAnswer(questionId);
	}
	
	public void insertAnswer(Answer answer) {
		answerDao.insertAnswer(answer);
	}
	
    
	//6.24 쪽지 관련
    

    @Override
    public List<Letter> selectByPage(String loginID, int pageNo, int rowsPerPage) {
       List<Letter> letterList= letterDao.selectLetterByPage(loginID, pageNo, rowsPerPage);
       return letterList;
    }

    @Override
    public int countAllLetter(String loginID) {
       int result= letterDao.countAllLetter(loginID);
       return result ;
    }

    @Override
    public List<Letter> selectBySearchWord(Map<String, String> parameters) {
       List<Letter> searchList= letterDao.selectBySearchWord(parameters);
       return searchList;
    }

    @Override
    public int insertLetter(Letter letter) {
       int result= letterDao.insertLetter(letter);
       return result;
    }
    @Override
    public List<Account> findListById(String loginID){
       List<Account> accountList = letterDao.findListById(loginID);
       return accountList;
    }

	
	
	
		public List<Question> getListQuestion(String itemId) {
			// TODO Auto-generated method stub
			return questionDao.getListQuestion(itemId);
		}
			
		public Question getQuestion(String itemId) {
			return questionDao.getQuestion(itemId);
		}
			
		public void insertQuestion(Question question) {
			questionDao.insertQuestion(question);
		}

			
		
		@Override
		public List<String> productNameList(String categoryId) {
			// TODO Auto-generated method stub
			return productDao.productNameList(categoryId);
		}

			@Override
			public String getProductIdByName(String name){
				// TODO Auto-generated method stub
				return productDao.getProductIdByName(name);
			}

//			@Override
//			public int getItemID() {
//				// TODO Auto-generated method stub
//				return generalItemDao.getItemID();
//			}
			
	
		
		public int isAuctionByItem(String itemId) {
			return itemDao.isAuctionByItem(itemId);
		}
		
		
		//0625 후기 게시판
		   @Override
		      public int insert(Post post) {
		         return postDao.insert(post);
		      }

		      @Override
		      public List<Post> selectPage(int pageNo, int rowsPerPage) {
		         return postDao.selectPage(pageNo, rowsPerPage);
		      }

		      @Override
		      public int countAll() {
		         return postDao.countAll();
		      }

		      @Override
		      public Post selectByPostId(int postid) {
		         return postDao.selectByPostId(postid);
		      }

		      @Override
		      public void updateHitcount(int postid, String loginID, int view_count) {
		         postDao.updateHitcount(postid, loginID, view_count);
		      }

		      @Override
		      public void delete(int postid) {
		         postDao.delete(postid);
		      }

		      @Override
		      public void update(Post post) {
		         postDao.update(post);
		      }

		      @Override
		      public void updateLikecount(int postid, String loginID, int like_count) {
		         postDao.updateLikecount(postid, loginID, like_count);
		      }


	
	
	
	

}