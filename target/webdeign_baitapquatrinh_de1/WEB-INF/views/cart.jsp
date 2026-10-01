<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<h1>Giỏ hàng của bạn</h1>

<c:if test="${not empty cartMsg}">
    <p class="msg err">${cartMsg}</p>
    <c:remove var="cartMsg" scope="session"/>
</c:if>
<c:if test="${not empty cartOk}">
    <p class="msg ok">${cartOk}</p>
    <c:remove var="cartOk" scope="session"/>
</c:if>

<c:choose>
    <c:when test="${empty cartItems}">
        <p class="muted">Giỏ hàng đang trống. <a href="${pageContext.request.contextPath}/products">Mua sách ngay →</a></p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Sách</th>
                <th>Giá (VNĐ)</th>
                <th>Số lượng</th>
                <th>Thành tiền (VNĐ)</th>
                <th>Xóa</th>
            </tr>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td>
                        <a class="book-link" href="${pageContext.request.contextPath}/book?id=${item.book.bookid}">
                            ${item.book.title}
                        </a>
                    </td>
                    <td><fmt:formatNumber value="${item.book.price}" pattern="#,##0"/></td>
                    <td>
                        <%-- Thay đổi số lượng, giới hạn 1..tồn kho (cập nhật về 0 = xóa) --%>
                        <form class="qty-form" action="${pageContext.request.contextPath}/cart/update" method="post">
                            <input type="hidden" name="bookid" value="${item.book.bookid}">
                            <input type="number" name="quantity" min="1" max="${item.book.quantity}"
                                   value="${item.quantity}" onchange="this.form.submit()">
                            <span class="stock">/ còn ${item.book.quantity}</span>
                            <button type="submit">Cập nhật</button>
                        </form>
                    </td>
                    <td><fmt:formatNumber value="${item.subtotal}" pattern="#,##0"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart/remove" method="post">
                            <input type="hidden" name="bookid" value="${item.book.bookid}">
                            <button type="submit" class="btn-danger">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <tr class="total-row">
                <td colspan="3"><b>Tổng cộng</b></td>
                <td colspan="2"><b><fmt:formatNumber value="${cartTotal}" pattern="#,##0"/> VNĐ</b></td>
            </tr>
        </table>

        <div class="cart-actions">
            <form action="${pageContext.request.contextPath}/cart/clear" method="post">
                <button type="submit" class="btn-danger">Xóa toàn bộ giỏ hàng</button>
            </form>
            <a class="btn-primary" href="${pageContext.request.contextPath}/checkout">Thanh toán (COD) →</a>
        </div>
    </c:otherwise>
</c:choose>

<style>
    .msg { padding: 10px 14px; border-radius: 6px; margin-bottom: 12px; font-size: 14px; }
    .msg.err { background: #fdecea; color: #c0392b; border: 1px solid #f5b7b1; }
    .msg.ok { background: #eafaf1; color: #1e8449; border: 1px solid #a9dfbf; }
    .muted { color: #7f8c8d; }
    .muted a { color: #e67e22; }
    .book-link { color: #2c3e50; text-decoration: none; font-weight: bold; }
    .book-link:hover { color: #e67e22; }
    .qty-form { display: flex; align-items: center; gap: 6px; }
    .qty-form input[type="number"] { width: 70px; padding: 5px; border: 1px solid #dfe6e9; border-radius: 4px; }
    .stock { color: #95a5a6; font-size: 12px; }
    .qty-form button, .btn-danger {
        background: #ecf0f1; border: 1px solid #bdc3c7; border-radius: 4px;
        padding: 5px 10px; cursor: pointer; font-size: 13px; color: #2c3e50;
    }
    .btn-danger:hover { background: #f5b7b1; border-color: #c0392b; color: #c0392b; }
    .total-row td { background: #fbfcfc; font-size: 15px; }
    .cart-actions { margin-top: 18px; display: flex; justify-content: space-between; align-items: center; }
    .cart-actions form { margin: 0; }
    .btn-primary {
        display: inline-block; background: #f39c12; color: #fff; font-weight: bold;
        text-decoration: none; padding: 10px 22px; border-radius: 6px; font-size: 14px;
    }
    .btn-primary:hover { background: #e67e22; }
</style>
