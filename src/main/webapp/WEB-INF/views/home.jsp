<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="home-head">
    <h1>Danh mục sách</h1>
    <p>Xin chào <b>${sessionScope.user.fullname}</b>! Cửa hàng hiện có <b>${totalBooks}</b> đầu sách.</p>
</div>

<div class="book-grid">
    <c:forEach var="b" items="${books}">
        <div class="book-card">
            <div class="book-cover">
                <c:choose>
                    <c:when test="${not empty b.coverImage}">
                        <img src="${pageContext.request.contextPath}/images/${b.coverImage}" alt="${b.title}"
                             onerror="this.parentNode.innerHTML='<span class='no-cover'>📖</span>'">
                    </c:when>
                    <c:otherwise>
                        <span class="no-cover">📖</span>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="book-info">
                <h3 class="book-title"><a href="${pageContext.request.contextPath}/book?id=${b.bookid}">${b.title}</a></h3>
                <ul class="book-meta">
                    <li><span>ISBN</span>${b.isbn}</li>
                    <li><span>Tác giả</span>
                        <c:forEach var="a" items="${b.authors}" varStatus="st">${st.first ? '' : ', '}${a.authorName}</c:forEach>
                    </li>
                    <li><span>NXB</span>${b.publisher}</li>
                    <li><span>Xuất bản</span><fmt:formatDate value="${b.publishDate}" pattern="dd/MM/yyyy"/></li>
                    <li><span>Còn lại</span>${b.quantity} quyển</li>
                </ul>
                <div class="book-foot">
                    <span class="price"><fmt:formatNumber value="${b.price}" pattern="#,##0"/> ₫</span>
                    <span class="review">💬 Review (${reviewCounts[b.bookid]})</span>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<c:if test="${totalPages > 1}">
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}" title="Trang trước">‹</a>
        </c:if>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${i == currentPage}"><span class="current">${i}</span></c:when>
                <c:otherwise><a href="?page=${i}">${i}</a></c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}" title="Trang sau">›</a>
        </c:if>
    </div>
</c:if>

<style>
    .home-head { border-bottom: 2px solid #f39c12; padding-bottom: 12px; margin-bottom: 18px; }
    .home-head h1 { font-size: 24px; color: #2c3e50; margin-bottom: 4px; }
    .home-head p { color: #7f8c8d; font-size: 14px; }

    .book-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 18px;
    }
    .book-card {
        display: flex;
        gap: 12px;
        border: 1px solid #e5e8e8;
        border-radius: 8px;
        background: #fff;
        padding: 12px;
        transition: box-shadow .2s, transform .2s;
    }
    .book-card:hover { box-shadow: 0 4px 14px rgba(0,0,0,.14); transform: translateY(-2px); }
    .book-cover {
        flex: 0 0 96px;
        height: 132px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: linear-gradient(135deg, #ecf0f1, #dfe6e9);
        border-radius: 6px;
        overflow: hidden;
    }
    .book-cover img { width: 100%; height: 100%; object-fit: cover; }
    .no-cover { font-size: 40px; }
    .book-info { flex: 1; min-width: 0; display: flex; flex-direction: column; }
    .book-title a { color: #2c3e50; text-decoration: none; }
    .book-title a:hover { color: #e67e22; }
    .book-title {
        font-size: 15px;
        color: #2c3e50;
        margin: 0 0 8px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .book-meta { list-style: none; margin: 0; padding: 0; font-size: 13px; color: #34495e; flex: 1; }
    .book-meta li { display: flex; gap: 6px; padding: 1px 0; }
    .book-meta li span { flex: 0 0 62px; color: #95a5a6; }
    .book-meta li { white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
    .book-foot {
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-top: 1px dashed #e5e8e8;
        padding-top: 8px;
        margin-top: 6px;
    }
    .price { color: #e67e22; font-weight: bold; font-size: 14px; }
    .review { color: #7f8c8d; font-size: 13px; }

    .pagination { text-align: center; margin-top: 22px; }
    .pagination a, .pagination .current {
        display: inline-block;
        min-width: 34px;
        padding: 6px 10px;
        margin: 0 3px;
        border: 1px solid #dfe6e9;
        border-radius: 20px;
        text-decoration: none;
        color: #2c3e50;
        font-size: 14px;
        line-height: 1.4;
    }
    .pagination .current { background: #2c3e50; color: #f39c12; border-color: #2c3e50; font-weight: bold; }
    .pagination a:hover { background: #ecf0f1; }
</style>
