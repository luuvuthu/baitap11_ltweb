<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<h1>Đơn hàng của tôi</h1>

<c:if test="${not empty param.success}">
    <p class="msg ok">🎉 Đặt hàng thành công! Mã đơn hàng: #${param.success}.
        Bạn sẽ thanh toán tiền mặt khi nhận hàng (COD).</p>
</c:if>

<%-- Bộ lọc lịch sử đặt hàng theo trạng thái --%>
<div class="status-filter">
    <span class="filter-label">Lọc theo trạng thái:</span>
    <a class="chip ${empty statusFilter ? 'active' : ''}"
       href="${pageContext.request.contextPath}/orders">Tất cả</a>
    <c:forEach var="s" items="${statusList}">
        <a class="chip ${statusFilter == s ? 'active' : ''}"
           href="${pageContext.request.contextPath}/orders?status=${s}">${s}</a>
    </c:forEach>
</div>

<c:choose>
    <c:when test="${empty orders}">
        <p class="muted">
            <c:choose>
                <c:when test="${not empty statusFilter}">
                    Không có đơn hàng nào ở trạng thái "<b>${statusFilter}</b>".
                    <a href="${pageContext.request.contextPath}/orders">Xem tất cả đơn hàng →</a>
                </c:when>
                <c:otherwise>
                    Bạn chưa có đơn hàng nào. <a href="${pageContext.request.contextPath}/products">Mua sách ngay →</a>
                </c:otherwise>
            </c:choose>
        </p>
    </c:when>
    <c:otherwise>
        <p class="muted result-count">Hiển thị ${orders.size()} đơn hàng
            <c:if test="${not empty statusFilter}"> — trạng thái "<b>${statusFilter}</b>"</c:if>
        </p>
        <c:forEach var="o" items="${orders}">
            <div class="order-card">
                <p class="order-head">
                    <b>Đơn hàng #${o.orderid}</b>
                    <span class="status">— ${o.status}</span>
                    <span class="date"><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/></span>
                </p>
                <p class="order-info">
                    <b>Người nhận:</b> ${o.receiverName} | <b>SĐT:</b> ${o.receiverPhone}
                    | <b>Địa chỉ:</b> ${o.receiverAddress} | <b>Thanh toán:</b> ${o.paymentMethod}
                </p>
                <table>
                    <tr>
                        <th>Sách</th>
                        <th>Giá (VNĐ)</th>
                        <th>Số lượng</th>
                        <th>Thành tiền (VNĐ)</th>
                    </tr>
                    <c:forEach var="d" items="${o.details}">
                        <tr>
                            <td>${d.book.title}</td>
                            <td><fmt:formatNumber value="${d.price}" pattern="#,##0"/></td>
                            <td>${d.quantity}</td>
                            <td><fmt:formatNumber value="${d.subtotal}" pattern="#,##0"/></td>
                        </tr>
                    </c:forEach>
                    <tr class="total-row">
                        <td colspan="3"><b>Tổng cộng</b></td>
                        <td><b><fmt:formatNumber value="${o.total}" pattern="#,##0"/> VNĐ</b></td>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>

<style>
    .msg { padding: 10px 14px; border-radius: 6px; margin-bottom: 12px; font-size: 14px;
           background: #eafaf1; color: #1e8449; border: 1px solid #a9dfbf; }
    .muted { color: #7f8c8d; }
    .muted a { color: #e67e22; }
    .result-count { margin: 10px 0; font-size: 13px; }

    .status-filter { margin: 14px 0; display: flex; flex-wrap: wrap; gap: 8px; align-items: center; }
    .filter-label { font-size: 14px; color: #2c3e50; font-weight: bold; }
    .chip {
        display: inline-block; padding: 6px 12px; border-radius: 16px; font-size: 13px;
        background: #ecf0f1; color: #2c3e50; text-decoration: none; border: 1px solid #dfe6e9;
    }
    .chip:hover { border-color: #f39c12; }
    .chip.active { background: #f39c12; color: #fff; border-color: #f39c12; font-weight: bold; }

    .order-card {
        border: 1px solid #dfe6e9; border-radius: 8px; padding: 14px 16px; margin-bottom: 16px;
        background: #fdfefe;
    }
    .order-head { margin: 0 0 6px; color: #2c3e50; }
    .status { color: #e67e22; }
    .date { float: right; color: #95a5a6; font-size: 13px; }
    .order-info { font-size: 13px; color: #34495e; margin: 0 0 8px; }
    .total-row td { background: #fbfcfc; }
</style>
