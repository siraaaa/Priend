package com.example.jpetstore.controller;

public class Page {
	
	 private static Page page = new Page();
	  private int startRow, endRow;
	  private StringBuffer sb;
	 
	  private Page() {}
	   
	  public static Page getInstance() {
	    if(page == null) {
	      page = new Page();
	    }
	    return page;
	  }
	   
	  // pageSize:리스트 row 개수, pageBlock: 하단 페이지 개수
	  public synchronized void paging(int pageNum, int totalCount, int pageSize, int pageBlock) {
	    // totalPage : 총 페이지 수
	    int totalPage = (int)Math.ceil((double)totalCount/pageSize);    // Math.ceil() : 올림
	    // startRow, endRow : 한 페이지의 start, end row 계산
	    startRow = (pageNum - 1) * pageSize + 1;
	    endRow = pageNum * pageSize;
	     
	    // 연산자 우선순위: 단항연산자(casting 등) > 산술연산자(* 등)
	    // 현재 페이지에 관한 하단부에 출력할 start, end 페이지 계산한다.
	    // 예를 들면, 3페이지는 1~10페이지 속에 포함된다.
	    int startPage = (int)((pageNum - 1)/pageBlock) * pageBlock + 1;
	    int endPage = startPage + pageBlock - 1;
	     
	    // 만약 계산된 마지막 페이지가 실제 마지막 페이지보다 많다면,
	    // 예를 들면, 총 77페이지까지 존재하지만 계산된 페이지는 80까지이므로 77로 변경해준다.
	    if(endPage > totalPage) {
	      endPage = totalPage;
	    }
	     
	    // html코드 삽입할 StringBuffer인스턴스 생성
	    sb = new StringBuffer();
	     
	    if(startPage < pageBlock) {    // 예를 들면, startPage=1, pageBlock=10이라면, '<'기능 필요없다.
//	      sb.append("<img src='images/hot.gif' width='30' height='9'>");
	    } else {    // 예를 들면, startPage=11, pageBlock=10이라면, '<'기능 필요.
//	      sb.append("<img src='images/hot.gif' width='30' height='9' ");
	      sb.append("<span width='30' height='9' ");
	      sb.append("onclick='location.href=\"list.bbs?pageNum=");
	      sb.append(startPage - pageBlock);   // 예를 들면, startPage-pageBlock=11-10=1 이라면, 1페이지로 돌아감
	      sb.append("\"' style='cursor:pointer'>&lt;</span>");
	      sb.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
	    }
	     
	    // pageBlock에 포함되는 페이지 숫자 출력하고, 링크를 달아준다.
	    for(int i = startPage; i <= endPage; i++) {
	      if(i == pageNum) {
	        sb.append("&nbsp;&nbsp;<b><font color='#91B7EF'>");
	        sb.append(i);
	        sb.append("</font></b>");
	      } else {
	        sb.append("&nbsp;&nbsp;<a href='list.bbs?pageNum=");
	        sb.append(i);
	        sb.append("'>");
	        sb.append(i);
	        sb.append("</a>");
	      }
	    }
	     
	    // '>'기능 구현으로, '<'기능과 반대로 구현한다.
	    if(endPage < totalPage) {
	      sb.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
//	      sb.append("<img src='images/hot.gif' width='30' height='9' ");
	      sb.append("<span width='30' height='9' ");
	      sb.append("onclick='location.href=\"list.bbs?pageNum=");
	      sb.append(startPage + pageBlock);
	      sb.append("\"' style='cursor:pointer'>&gt;</span>");
	    } else {
//	      sb.append("<img src='images/hot.gif' width='30' height='9'>");
	    }
	  }
	   
	  public StringBuffer getSb() {
	    return sb;
	  }
	 
	  public int getStartRow() {
	    return startRow;
	  }
	 
	  public int getEndRow() {
	    return endRow;
	  }

}
