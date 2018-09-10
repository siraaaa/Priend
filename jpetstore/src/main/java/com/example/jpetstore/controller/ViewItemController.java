package com.example.jpetstore.controller;
import java.util.Calendar;
//180621 isAuction���� ���/�Ϲ� ����
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.jpetstore.dao.mybatis.MybatisAuctionItemDao;
import com.example.jpetstore.dao.mybatis.MybatisGeneralItemDao;
import com.example.jpetstore.domain.AuctionItem;
import com.example.jpetstore.domain.Bid;
import com.example.jpetstore.domain.GeneralItem;
import com.example.jpetstore.domain.Item;
import com.example.jpetstore.domain.Question;
import com.example.jpetstore.service.PetStoreFacade;

@Controller
@SessionAttributes("userSession")
public class ViewItemController { 

	private PetStoreFacade petStore;

	@Autowired
	public void setPetStore(PetStoreFacade petStore) {
		this.petStore = petStore;
	}
	
	@Autowired
	private MybatisGeneralItemDao gItemDao;
	
	//��� ������ Dao �߰�
	@Autowired
	private MybatisAuctionItemDao auctionItemDao;

	
	@RequestMapping("/shop/viewItem.do")
	public String handleRequest(
			HttpServletRequest request,
			@RequestParam("itemId") String itemId,
			@RequestParam("isAuction") String isAuction,
			ModelMap model) throws Exception {
		
		//Question List 
		List<Question> questionList = this.petStore.getListQuestion(itemId);
		PagedListHolder<Question> qList = new PagedListHolder<Question>(this.petStore.getListQuestion(itemId));
		qList.setPageSize(2);
	
		
		if (isAuction.equals("off")) {
			GeneralItem item = this.gItemDao.getGeneralItem(itemId);
			model.put("item", item);
			model.put("product", item.getProductId());
			model.put("questionList", questionList);
			
			return "tiles/GeneralItem";
		}
		else if(isAuction.equals("on")) {
			UserSession userSession = 
					(UserSession) WebUtils.getSessionAttribute(request, "userSession");
			model.put("userId", "");
			if(userSession != null)
				model.put("userId", userSession.getAccount().getUser_id());
			AuctionItem auctionItem = this.auctionItemDao.getAuctionItem(itemId);
			model.put("item", auctionItem);
			model.put("isAuction", isAuction);//0624
			model.put("product", auctionItem.getProductId());
			model.put("questionList", questionList);
			
			List<Bid> bidList = petStore.bidValuesForChart(auctionItem.getAuctionId());
			model.put("bidListChart", bidList);
			System.out.println(bidList);
		
			return "tiles/AuctionItem";
		}
		
		return "tiles/GeneralItem";
	}
	
	@RequestMapping("/shop/auctionedOff.do")
	public String handleRequest2(
			@RequestParam("itemId") String itemId,
			@RequestParam("isAuction") String isAuction,
			@RequestParam("auctionId") String auctionId,
			@RequestParam("auctionedOffOk") String auctionedOffOk) {
		
		if(auctionedOffOk.equals("No"))
			return "redirect:/shop/viewItem.do?itemId="+itemId+"&&"+"isAuction="+isAuction;
		
		petStore.updateItemStatus(itemId, "off");
		petStore.endAuctionBySeller(auctionId);
		Calendar c = Calendar.getInstance();
//		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+2);
		c.set(Calendar.DATE, c.get(Calendar.DATE)+3);
		Bid bidder = petStore.getHighestBidder(auctionId);
		if(bidder != null) 
			petStore.bidderScheduler(c.getTime(), bidder.getBidder(), itemId, auctionId, bidder.getBid_price());
		
		return "redirect:/shop/viewItem.do?itemId="+itemId+"&&"+"isAuction="+isAuction;
	}

}
