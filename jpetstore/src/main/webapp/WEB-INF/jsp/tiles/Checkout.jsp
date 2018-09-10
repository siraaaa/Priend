<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table>
  <tr>
    <td style="text-align: left; vertical-align: top; width: 20%">
      <table id="main-menu">
        <tr>
          <td><a href='<c:url value="/shop/viewCart.do"/>'><b>
            <font color="black" size="2">&lt;&lt; Shopping Cart</font></b></a></td>
        </tr>
      </table>
    </td>

    <td style="text-align: center; vertical-align: top">
      <h2>Checkout Summary</h2>
      <table class="n25">
        <tr bgcolor="#CCCCCC">
          <td><b>Item ID</b></td>
          <td><b>Product ID</b></td>
          <td><b>Description</b></td>
          <td><b>In Stock?</b></td>
          <td><b>Quantity</b></td>
          <td><b>List Price</b></td>
          <td><b>Total Cost</b></td>
        </tr>
        <c:forEach var="cartItem" items="${cart.cartItemList.pageList}">
          <tr bgcolor="#FFFF88">
            <td><b> 
              <a href='<c:url value="/shop/viewItem.do">
                <c:param name="itemId" value="${cartItem.item.itemId}"/></c:url>'>
                  <c:out value="${cartItem.item.itemId}" />
              </a></b>
            </td>
            <td><c:out value="${cartItem.item.productId}" /></td>
            <td>
              <c:out value="${cartItem.item.attribute1}" /> 
              <c:out value="${cartItem.item.attribute2}" /> 
              <c:out value="${cartItem.item.attribute3}" /> 
              <c:out value="${cartItem.item.attribute4}" /> 
              <c:out value="${cartItem.item.attribute5}" /> 
              <c:out value="${cartItem.item.product.name}" />
            </td>
            <td align="center"><c:out value="${cartItem.inStock}" /></td>
            <td align="center"><c:out value="${cartItem.quantity}" /></td>
            <td align="right"><fmt:formatNumber
                value="${cartItem.item.listPrice}" pattern="$#,##0.00" /></td>
            <td align="right"><fmt:formatNumber
                value="${cartItem.totalPrice}" pattern="$#,##0.00" /></td>
          </tr>
        </c:forEach>
        <tr bgcolor="#FFFF88">
          <td colspan="7" align="right">
          	<input type="text" name="uMileage" id="uMileage" size="10"/>&nbsp;<input type="button" value="사용" onclick="useMileage(1000, ${cart.subTotal});"/>
          	<b id="cMileage">보유 마일리지:1000</b>
			<hr />
          	<b id="subTotal">Sub Total: <fmt:formatNumber
                value="${cart.subTotal}" pattern="$#,##0.00" /></b><br /></td>		<!-- DB에 접근해서 현재 value로 보유 mileage와 subTotal 변경해야 함. -->
        </tr>
      </table>

      <c:if test="${!cart.cartItemList.firstPage}">
        <a href="checkout.do?page=previousCart"><font color="green">
          <B>&lt;&lt; Prev</B></font></a>
      </c:if>
      <c:if test="${!cart.cartItemList.lastPage}">
        <a href="checkout.do?page=nextCart"><font color="green">
          <B>Next &gt;&gt;</B></font></a>
      </c:if>
      <br> 
      <a href='<c:url value="/shop/newOrder.do"/>'>
        <img border="0" src="../images/button_continue.gif" alt="" /></a>
    </td>
    <td style="text-align: right; vertical-align: top; width: 20%">&nbsp;</td>
  </tr>
</table>

