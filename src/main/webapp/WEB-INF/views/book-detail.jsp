<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="detail-wrap">
    <a class="back" href="${pageContext.request.contextPath}/home">‹ Quay lại danh mục sách</a>

    <c:if test="${not empty cartMsg}">
        <p class="cart-msg err">${cartMsg}</p>
        <c:remove var="cartMsg" scope="session"/>
    </c:if>
    <c:if test="${not empty cartOk}">
        <p class="cart-msg ok">${cartOk}</p>
        <c:remove var="cartOk" scope="session"/>
    </c:if>

    <%-- Khối thông tin sách: cover | Tiêu đề, isbn, tác giả, publisher, publisher_date --%>
    <table class="detail-table">
        <tr>
            <td class="cover-cell" rowspan="2">
                <c:choose>
                    <c:when test="${not empty book.coverImage}">
                        <img src="${pageContext.request.contextPath}/images/${book.coverImage}" alt="${book.title}"
                             onerror="this.outerHTML='<span class=no-cover>📖</span>'">
                    </c:when>
                    <c:otherwise><span class="no-cover">📖</span></c:otherwise>
                </c:choose>
            </td>
            <td>
                <p class="info-line"><b>Tiêu đề:</b> ${book.title}</p>
                <p class="info-line"><b>Mã isbn:</b> ${book.isbn}</p>
                <p class="info-line"><b>Tác giả:</b>
                    <c:forEach var="a" items="${book.authors}" varStatus="st">${st.first ? '' : ', '}${a.authorName}</c:forEach>
                </p>
                <p class="info-line"><b>Publisher:</b> ${book.publisher}</p>
                <p class="info-line"><b>Publisher_date:</b> <fmt:formatDate value="${book.publishDate}" pattern="dd/MM/yyyy"/></p>
            </td>
        </tr>
        <%-- Quantity + số lượng reviews + thêm vào giỏ hàng --%>
        <tr>
            <td class="qty-cell">
                <p class="info-line"><b>Quantity:</b> ${book.quantity} quyển</p>
                <p class="info-line"><b>Reviews (${reviewCount})</b></p>
                <c:choose>
                    <c:when test="${book.quantity > 0}">
                        <form class="add-cart" action="${pageContext.request.contextPath}/cart/add" method="post">
                            <input type="hidden" name="bookid" value="${book.bookid}">
                            <input type="hidden" name="back" value="/book?id=${book.bookid}">
                            <label>Số lượng:
                                <input type="number" name="quantity" value="1" min="1" max="${book.quantity}">
                            </label>
                            <button type="submit">🛒 Thêm vào giỏ hàng</button>
                        </form>
                    </c:when>
                    <c:otherwise><p class="out-of-stock">Hết hàng</p></c:otherwise>
                </c:choose>
            </td>
        </tr>
        <%-- Header bảng reviews --%>
        <tr class="review-head">
            <td>Reviews</td>
            <td></td>
        </tr>
        <%-- Danh sách review: [users]: [review_text] --%>
        <c:choose>
            <c:when test="${empty reviews}">
                <tr><td colspan="2" class="muted">Chưa có đánh giá nào cho cuốn sách này.</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="r" items="${reviews}">
                    <tr class="review-row">
                        <td colspan="2">
                            <b>${r.user.fullname}:</b> ${r.reviewText}
                            <span class="stars small">
                                <c:forEach var="i" begin="1" end="5">
                                    <c:choose>
                                        <c:when test="${i <= r.rating}">★</c:when>
                                        <c:otherwise><span class="star-off">★</span></c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </span>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <%-- Form thêm reviews --%>
        <tr class="form-row">
            <td colspan="2">
                <p class="form-title">Form thêm reviews</p>
                <form action="${pageContext.request.contextPath}/book" method="post">
                    <input type="hidden" name="bookid" value="${book.bookid}">
                    <label>Số sao:
                        <select name="rating" required>
                            <option value="5" ${myReview.rating == 5 ? 'selected' : ''}>5 ★</option>
                            <option value="4" ${myReview.rating == 4 ? 'selected' : ''}>4 ★</option>
                            <option value="3" ${myReview.rating == 3 ? 'selected' : ''}>3 ★</option>
                            <option value="2" ${myReview.rating == 2 ? 'selected' : ''}>2 ★</option>
                            <option value="1" ${myReview.rating == 1 ? 'selected' : ''}>1 ★</option>
                        </select>
                    </label>
                    <textarea name="review_text" rows="3" placeholder="Nhập nội dung đánh giá..."
                              required>${myReview.reviewText}</textarea>
                    <button type="submit">Submit</button>
                </form>
            </td>
        </tr>
    </table>
</div>

<style>
    .detail-wrap { max-width: 860px; margin: 0 auto; }
    .cart-msg { padding: 10px 14px; border-radius: 6px; margin-bottom: 12px; font-size: 14px; }
    .cart-msg.err { background: #fdecea; color: #c0392b; border: 1px solid #f5b7b1; }
    .cart-msg.ok { background: #eafaf1; color: #1e8449; border: 1px solid #a9dfbf; }
    .back { display: inline-block; color: #7f8c8d; text-decoration: none; font-size: 13px; margin-bottom: 10px; }
    .back:hover { color: #e67e22; }

    .detail-table {
        width: 100%;
        border-collapse: collapse;
        background: #fff;
        box-shadow: 0 4px 16px rgba(0,0,0,.06);
    }
    .detail-table td {
        border: 1px solid #2c3e50;
        padding: 14px 16px;
        vertical-align: top;
        font-size: 14px;
        color: #34495e;
    }
    .cover-cell {
        width: 200px;
        background: linear-gradient(135deg, #ecf0f1, #dfe6e9);
        text-align: center;
        vertical-align: middle;
    }
    .cover-cell img { max-width: 100%; height: auto; display: block; }
    .no-cover { font-size: 64px; }

    .info-line { margin: 0 0 6px; }
    .info-line b { color: #2c3e50; }
    .qty-cell { border-top: none; }

    .review-head td { font-weight: bold; color: #2c3e50; background: #fbfcfc; }

    .review-row td { border-top: none; }
    .stars { color: #f39c12; }
    .stars .star-off { color: #dfe6e9; }
    .stars.small { font-size: 12px; }

    .form-row td { border-top: none; }
    .form-title { font-weight: bold; color: #2c3e50; margin: 0 0 10px; }
    .form-row form { display: flex; flex-direction: column; gap: 10px; align-items: flex-start; }
    .form-row select {
        padding: 6px 10px; border: 1px solid #dfe6e9; border-radius: 6px; font-size: 14px;
    }
    .form-row textarea {
        width: 100%; padding: 10px; border: 1px solid #dfe6e9; border-radius: 6px;
        font-size: 14px; resize: vertical; font-family: inherit;
    }
    .form-row button {
        background: #2c3e50; color: #f39c12; font-weight: bold; font-size: 14px;
        border: none; border-radius: 6px; padding: 10px 26px; cursor: pointer;
    }
    .form-row button:hover { background: #34495e; }
    .muted { color: #95a5a6; }

    .add-cart { margin-top: 10px; display: flex; align-items: center; gap: 10px; }
    .add-cart input[type="number"] {
        width: 70px; padding: 6px; border: 1px solid #dfe6e9; border-radius: 4px; font-size: 14px;
    }
    .add-cart button {
        background: #f39c12; color: #fff; font-weight: bold; border: none; border-radius: 6px;
        padding: 8px 18px; cursor: pointer; font-size: 14px;
    }
    .add-cart button:hover { background: #e67e22; }
    .out-of-stock { color: #c0392b; font-weight: bold; margin-top: 10px; }
</style>
