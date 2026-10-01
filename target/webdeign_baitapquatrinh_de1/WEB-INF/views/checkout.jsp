<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<h1>Thanh toán đơn hàng — COD (Thanh toán khi nhận hàng)</h1>

<c:if test="${not empty cartMsg}">
    <p class="msg err">${cartMsg}</p>
    <c:remove var="cartMsg" scope="session"/>
</c:if>

<div class="checkout-wrap">
    <%-- Tóm tắt giỏ hàng --%>
    <h2>Sản phẩm trong đơn</h2>
    <table>
        <tr>
            <th>Sách</th>
            <th>Giá (VNĐ)</th>
            <th>Số lượng</th>
            <th>Thành tiền (VNĐ)</th>
        </tr>
        <c:forEach var="item" items="${cartItems}">
            <tr>
                <td>${item.book.title}</td>
                <td><fmt:formatNumber value="${item.book.price}" pattern="#,##0"/></td>
                <td>${item.quantity}</td>
                <td><fmt:formatNumber value="${item.subtotal}" pattern="#,##0"/></td>
            </tr>
        </c:forEach>
        <tr class="total-row">
            <td colspan="3"><b>Tổng cộng</b></td>
            <td><b><fmt:formatNumber value="${cartTotal}" pattern="#,##0"/> VNĐ</b></td>
        </tr>
    </table>

    <%-- Form thông tin giao hàng --%>
    <h2>Thông tin nhận hàng</h2>
    <form class="checkout-form" action="${pageContext.request.contextPath}/checkout" method="post">
        <label>Người nhận *
            <input type="text" name="receiver_name" value="${defaultName}" required maxlength="50">
        </label>
        <label>Số điện thoại *
            <input type="text" name="receiver_phone" value="${defaultPhone}" required
                   pattern="\d{9,11}" title="9-11 chữ số" maxlength="11">
        </label>
        <label>Địa chỉ giao hàng *
            <input type="text" name="receiver_address" required maxlength="255"
                   placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố">
        </label>
        <label>Phương thức thanh toán
            <select disabled>
                <option selected>COD — Thanh toán khi nhận hàng</option>
            </select>
        </label>
        <input type="hidden" name="payment_method" value="COD">
        <div class="form-actions">
            <a class="btn-back" href="${pageContext.request.contextPath}/cart">‹ Quay lại giỏ hàng</a>
            <button type="submit" class="btn-primary">Đặt hàng (COD)</button>
        </div>
    </form>
</div>

<style>
    .msg { padding: 10px 14px; border-radius: 6px; margin-bottom: 12px; font-size: 14px;
           background: #fdecea; color: #c0392b; border: 1px solid #f5b7b1; }
    .checkout-wrap h2 { color: #2c3e50; margin: 18px 0 6px; font-size: 17px; }
    .total-row td { background: #fbfcfc; }
    .checkout-form { display: flex; flex-direction: column; gap: 12px; max-width: 480px; }
    .checkout-form label { display: flex; flex-direction: column; gap: 4px; font-size: 14px; color: #2c3e50; }
    .checkout-form input, .checkout-form select {
        padding: 8px 10px; border: 1px solid #dfe6e9; border-radius: 6px; font-size: 14px;
        font-family: inherit;
    }
    .checkout-form select { background: #ecf0f1; }
    .form-actions { display: flex; justify-content: space-between; align-items: center; margin-top: 6px; }
    .btn-back { color: #7f8c8d; text-decoration: none; font-size: 13px; }
    .btn-back:hover { color: #e67e22; }
    .btn-primary {
        background: #f39c12; color: #fff; font-weight: bold; border: none;
        border-radius: 6px; padding: 10px 26px; cursor: pointer; font-size: 14px;
    }
    .btn-primary:hover { background: #e67e22; }
</style>
