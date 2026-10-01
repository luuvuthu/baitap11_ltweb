<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="admin-wrap">
    <a class="back" href="${pageContext.request.contextPath}/admin/books">‹ Về danh sách sách</a>
    <h1>Chi tiết sách</h1>

    <table class="view-table">
        <tr>
            <td class="cover-cell">
                <c:choose>
                    <c:when test="${not empty book.coverImage}">
                        <img src="${pageContext.request.contextPath}/images/${book.coverImage}" alt="${book.title}"
                             onerror="this.outerHTML='<span class=no-cover>📖</span>'">
                    </c:when>
                    <c:otherwise><span class="no-cover">📖</span></c:otherwise>
                </c:choose>
            </td>
            <td>
                <p><b>Mã sách:</b> ${book.bookid}</p>
                <p><b>Tiêu đề:</b> ${book.title}</p>
                <p><b>Mã isbn:</b> ${book.isbn}</p>
                <p><b>Tác giả:</b>
                    <c:forEach var="a" items="${book.authors}" varStatus="st">${st.first ? '' : ', '}${a.authorName}</c:forEach>
                </p>
                <p><b>Publisher:</b> ${book.publisher}</p>
                <p><b>Publisher_date:</b> <fmt:formatDate value="${book.publishDate}" pattern="dd/MM/yyyy"/></p>
                <p><b>Giá:</b> <fmt:formatNumber value="${book.price}" pattern="#,##0"/> ₫</p>
                <p><b>Quantity:</b> ${book.quantity}</p>
                <p><b>Mô tả:</b> ${book.description}</p>
            </td>
        </tr>
    </table>

    <div class="btn-row">
        <a class="btn" href="${pageContext.request.contextPath}/admin/book-form?id=${book.bookid}">✎ Sửa</a>
        <a class="btn danger" href="${pageContext.request.contextPath}/admin/book-delete?id=${book.bookid}"
           onclick="return confirm('Xóa sách này?')">🗑 Xóa</a>
    </div>
</div>

<style>
    .admin-wrap { max-width: 860px; margin: 0 auto; }
    .back { display: inline-block; color: #7f8c8d; text-decoration: none; font-size: 13px; margin-bottom: 10px; }
    .back:hover { color: #e67e22; }
    h1 { font-size: 22px; color: #2c3e50; }
    .view-table { width: 100%; border-collapse: collapse; background: #fff; box-shadow: 0 4px 16px rgba(0,0,0,.06); }
    .view-table td { border: 1px solid #e5e8e8; padding: 14px 16px; font-size: 14px; color: #34495e; vertical-align: top; }
    .view-table p { margin: 0 0 8px; }
    .cover-cell { width: 200px; text-align: center; vertical-align: middle; background: linear-gradient(135deg, #ecf0f1, #dfe6e9); }
    .cover-cell img { max-width: 100%; height: auto; display: block; }
    .no-cover { font-size: 64px; }
    .btn-row { margin-top: 14px; display: flex; gap: 10px; }
    .btn {
        background: #2c3e50; color: #f39c12; font-weight: bold; text-decoration: none;
        padding: 9px 18px; border-radius: 6px; font-size: 14px;
    }
    .btn.danger { background: #c0392b; color: #fff; }
    .btn:hover { opacity: .9; }
</style>
