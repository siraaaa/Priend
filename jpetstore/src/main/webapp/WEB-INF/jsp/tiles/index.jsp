<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<br/><br/>
<%@ include file="IncludeQuickHeader.jsp" %><br/>

<style>
table#index tr td#cat:hover {
    padding: 20px; 
    background : #FFD8D8;
}
</style>

<table style="border:none;border-collapse:collapse;width:1500px">
  <tr>
    <td style="text-align:left;vertical-align:top">
      <table style="border:none;border-collapse:collapse;width:100%">
        <tr>
          <td valign="top" width="300px">                    
            <!-- SIDEBAR -->
           	<table id="index">
              <tr>
                <td>
                <c:if test="${!empty userSession.account}">
			       <b><i><font size="4" color="RED">Welcome ${userSession.account.user_id}!</font></i></b>
                </c:if>&nbsp;
                </td>
              </tr>
              
     
              <tr>
                <td id="cat">
                <a href="<c:url value="/shop/viewCategory.do?categoryId=FISH"/>"> 
                   <!-- <div><B>FISH</B></div> -->
                   <img border="0" src="../images/fish_icon.gif" />
                </a>
                </td>
              </tr>
     
              <tr>
                <td id="cat">
                  <a href="<c:url value="/shop/viewCategory.do?categoryId=DOGS"/>">
                    <img border="0" src="../images/dogs_icon.gif" />
                    <!-- <b>DOG</b> --></a>
                </td>
              </tr>
              <tr>
                <td id="cat">
                  <a href="<c:url value="/shop/viewCategory.do?categoryId=CATS"/>">
                    <img border="0" src="../images/cats_icon.gif" />
                    <!-- <B>CAT</B> --></a>
                </td>
              </tr>
              <tr>
                <td id="cat">
                  <a href="<c:url value="/shop/viewCategory.do?categoryId=REPTILES"/>">
                    <img border="0" src="../images/reptiles_icon.gif" />
                    <!-- <B>REPTILES</B> --></a>
                </td>
              </tr>
              <tr>
                <td id="cat">
                  <a href="<c:url value="/shop/viewCategory.do?categoryId=BIRDS"/>">
                    <img border="0" src="../images/birds_icon.gif" />
                    <!-- <B>BIRDS</B> --></a>
                </td>
              </tr>
            </table>
          </td>
          <td style="text-align:center;background-color:#FFD8D8;height:300;width:100%">
            <!-- MAIN IMAGE -->
            <map name="estoremap">
              <area alt="Birds" coords="72,2,280,250" href="viewCategory.do?categoryId=BIRDS" shape="RECT" />
              <area alt="Fish" coords="2,180,72,250" href="viewCategory.do?categoryId=FISH" shape="RECT" />
              <area alt="Dogs" coords="60,250,130,320" href="viewCategory.do?categoryId=DOGS" shape="RECT" />
              <area alt="Reptiles" coords="140,270,210,340" href="viewCategory.do?categoryId=REPTILES" shape="RECT" />
              <area alt="Cats" coords="225,240,295,310" href="viewCategory.do?categoryId=CATS" shape="RECT" />
              <area alt="Birds" coords="280,180,350,250" href="viewCategory.do?categoryId=BIRDS" shape="RECT" />
            </map>
          <!--   <img src="../images/splash.gif" usemap="#estoremap" style="float:center;width:350;height:355;border:0;" /> -->
          </td>
          <td width="300px">&nbsp;</td>
          </tr>
    </table>
   </td>
  </tr>
</table>
<table>
	<tr>
		<td width="600px"></td>
		<td width="600px"></td>
 
		<td style="width:500px">
		<br/><br/>
			<script>
				$(document).ready(function(){
					$('.slider').bxSlider({
						auto: true,
						captions: true,
				    	slideWidth: 370,
				    	pager: false,
				    	controls: false,
				    	mode: 'fade',
				    	adaptiveHeight: true
					});
					$('.slider2').bxSlider({
						auto: true,
						captions: true,
				    	slideWidth: 370,
				    	pager: false,
				    	controls: false,
				    	mode: 'fade',
				    	adaptiveHeight: true
					});
				});
			</script>
			<div class="slider">
				<c:forEach var="ad" items="${firstAdList}">
					<div><a href="<c:url value="/shop/viewItem.do?itemId=${ ad.itemid }"/>"><img src="../images/ad_images/${ ad.image }" title="${ ad.title }"/></a></div>
				</c:forEach>
			</div>
			<div class="slider2">
				<c:forEach var="ad2" items="${secondAdList}">
					<div><a href="<c:url value="/shop/viewItem.do?itemId=${ ad2.itemid }"/>"><img src="../images/ad_images/${ ad2.image }" title="${ ad2.title }"/></a></div>
				</c:forEach>
			</div>
		</td>
		    
	</tr>
</table>