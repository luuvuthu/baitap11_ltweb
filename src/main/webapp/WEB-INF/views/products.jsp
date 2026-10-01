<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h1>Danh sách sản phẩm</h1>
<table>
    <tr>
        <th>Mã sách</th>
        <th>ISBN</th>
        <th>Tiêu đề</th>
        <th>Nhà xuất bản</th>
        <th>Giá (VNĐ)</th>
        <th>Số lượng</th>
        <th>Chi tiết / Mua</th>
    </tr>
    <c:forEach var="b" items="${books}">
        <tr>
            <td>${b.bookid}</td>
            <td>${b.isbn}</td>
            <td>${b.title}</td>
            <td>${b.publisher}</td>
            <td>${b.price}</td>
            <td>${b.quantity}</td>
            <td>
                <a class="btn-detail" href="${pageContext.request.contextPath}/book?id=${b.bookid}">Xem</a>
                <c:choose>
                    <c:when test="${b.quantity > 0}">
                        <form class="inline-cart" action="${pageContext.request.contextPath}/cart/add" method="post">
                            <input type="hidden" name="bookid" value="${b.bookid}">
                            <input type="hidden" name="back" value="/products">
                            <input type="number" name="quantity" value="1" min="1" max="${b.quantity}">
                            <button type="submit">🛒 Thêm vào giỏ</button>
                        </form>
                    </c:when>
                    <c:otherwise><span class="out">Hết hàng</span></c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>

<style>
    .btn-detail { color: #2c3e50; text-decoration: none; margin-right: 8px; }
    .btn-detail:hover { color: #e67e22; }
    .inline-cart { display: inline-flex; align-items: center; gap: 4px; }
    .inline-cart input[type="number"] { width: 60px; padding: 4px; border: 1px solid #dfe6e9; border-radius: 4px; }
    .inline-cart button {
        background: #f39c12; color: #fff; border: none; border-radius: 4px;
        padding: 5px 10px; cursor: pointer; font-size: 13px;
    }
    .inline-cart button:hover { background: #e67e22; }
    .out { color: #c0392b; font-size: 13px; }
</style>
