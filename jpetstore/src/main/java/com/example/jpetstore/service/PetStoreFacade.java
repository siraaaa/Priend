package com.example.jpetstore.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.jpetstore.domain.Account;
import com.example.jpetstore.domain.Advertisement;
import com.example.jpetstore.domain.Answer;
import com.example.jpetstore.domain.AuctionItem;
import com.example.jpetstore.domain.Bid;
import com.example.jpetstore.domain.BlackList;
import com.example.jpetstore.domain.Category;
import com.example.jpetstore.domain.GeneralItem;
import com.example.jpetstore.domain.Item;
import com.example.jpetstore.domain.Letter;
import com.example.jpetstore.domain.Mileage;
import com.example.jpetstore.domain.MySales;
import com.example.jpetstore.domain.Order;
import com.example.jpetstore.domain.Post;
import com.example.jpetstore.domain.Product;
import com.example.jpetstore.domain.QAndA;
import com.example.jpetstore.domain.Question;

/**
 * JPetStore's central business interface.
 *
 * @author Juergen Hoeller
 * @since 30.11.2003
 */
public interface PetStoreFacade {

	Account getAccount(String username);

	Account getAccount(String username, String password);

	void insertAccount(Account account);

	void updateAccount(Account account);

	List<String> getUsernameList();


	List<Category> getCategoryList();

	Category getCategory(String categoryId);
	

	List<Product> getProductListByCategory(String categoryId);

	Product getProduct(String productId);


	List<Item> getItemListByProduct(String productId);

	Item getItem(String itemId);

	boolean isItemInStock(String itemId);
	
	List<Item> searchItemList(String keywords, String array, int priceMax, int priceMin, String option);


	void insertOrder(Order order);

	Order getOrder(int orderId);

	List<Order> getOrdersByUsername(String username);
	
	//180521 ad���� �޼ҵ� ����
	void insertAd(Advertisement Ad);
	
	List<Advertisement> advertise(String today);
	
	List<Advertisement> favAdvertise(String favcategory, String today);
	
	//180623 ad delete �߰�
	void deleteAdByItemId(String itemId);
	
	//180523 �߰�
	List<MySales> getItemListByUserid(String userid);
	
	void updateAdStatus(String itmeId, String adStatus);
	
	List<Bid> getAuctionItemsByUsername(String username);
	
	int getHighestBidPrice(String auctionId);
	
	//180528 �߰�
	
	List<Post> getReviewByUsername(String username);
	
	List<QAndA> getQuestionByUsername(String username);
	
	List<QAndA> getAnswerByUsername(String username);
	
	List<Mileage> getMileageListByUsername(String username);
	
	//180530 �߰�
	
	void removeFromBlackList(String username);
	
	BlackList getDetail(String username);
	
	void removeDetail();
	
	List<String> getUserOnBlackList();
	
	List<BlackList> getReasonByUsername(String username);
	
	List<Advertisement> getApprovalList();
	

		//question list
	List<Question> getListQuestion(String itemId);
	Question getQuestion(String questionId);
	void insertQuestion(Question question);
		
		//black list
	void insertBlackList(BlackList black);
	BlackList getBlack(String blackId);

	List<String> productNameList(String categoryId);
	String getProductIdByName(String name);
		
//	int getItemID();
	
		
	int isAuctionByItem(String itemId);
		
	//180621 ��� �󼼺���/����/���� �߰�
	public AuctionItem getAuctionItem(String itemId);
	
	List<Bid> getBidByAuctionId(String auctionId);
	
	Integer getBidPriceByUsername(String bidder, String auctionId);
	
	void updateBid(String bidder, int price);
	
	void insertBid(Bid bid);
	
	void deleteBid(String bidder, String auctionId);
	
	void updateItemStatus(String itemId, String status);
	
	void endAuctionBySeller(String auctionId);
	
	Bid getHighestBidder(String auctionId);
	
	void auctionedOffScheduler(Date auctionedOffTime, AuctionItem auctionItem, int itemId);
	
	void bidderScheduler(Date deadline, String bidder, String itemId, String auctionId, int price);


	
	void advertisementScheduler(Advertisement ad, Account account);
	
	
	
	//-----------------180623 수정 ---------------//
	//상품 등록
	void insertGeneralItemAll(GeneralItem generalItem);

	//void insertQuantity(Inventory inventory); 트랜잭션 처리
	
	public void insertAuctionItemAll(AuctionItem auctionItem);
	//public void insertAuction(AuctionItem auctionItem); 트랜잭션 처리
	
	//상품 수정
	GeneralItem getGeneralItem(String sitemId);
	public void updateGeneralItemAll(GeneralItem generalItem);
	public void updateAuctionItemAll(AuctionItem autionItem);
	
	//상품 삭제
	public void deleteItemAll(String itemId);
	public void deleteAuctionInfoAll(String auctionId);
	
	//하이 차트
	public List<Bid> bidValuesForChart(String auctionId);
	public List<Bid> getBidByAuctionIdASC(String auctionId);
	
	//answer list
	public List<Answer> getAnswerList(String questionId);
	public void insertAnswer(Answer answer);
	
	//6.24 쪽지 관련 추가 
	   public List<Letter> selectByPage(String loginID, int pageNo, int rowsPerPage);
	   public int countAllLetter(String loginID);
	   public List<Letter> selectBySearchWord(Map<String, String> parameters);
	   public int insertLetter(Letter letter);
	   public List<Account> findListById(String loginID);
	   
	   
	   //6.24 포스팅
	   int insert(Post post);
	   List<Post> selectPage(int pageNo, int rowsPerPage);
	   int countAll();
	   Post selectByPostId(int postid);
	   void updateHitcount(int postid, String loginID, int view_count);
	   void delete(int postid);
	   void update(Post post);
	   void updateLikecount(int postid, String loginID, int like_count);
			
	
	
}