<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="admin-wrap">
    <a class="back" href="${pageContext.request.contextPath}/admin">‹ Về trang quản trị</a>
    <div class="admin-head">
        <h1>Quản lý sách</h1>
        <a class="btn-add" href="${pageContext.request.contextPath}/admin/book-form">+ Thêm sách</a>
    </div>

    <table class="admin-table">
        <tr>
            <th>Mã</th>
            <th>Tiêu đề</th>
            <th>Tác giả</th>
            <th>ISBN</th>
            <th>Publisher</th>
            <th>Giá (VNĐ)</th>
            <th>SL</th>
            <th>Hành động</th>
        </tr>
        <c:forEach var="b" items="${books}">
            <tr>
                <td>${b.bookid}</td>
                <td>${b.title}</td>
                <td>
                    <c:forEach var="a" items="${b.authors}" varStatus="st">${st.first ? '' : ', '}${a.authorName}</c:forEach>
                </td>
                <td>${b.isbn}</td>
                <td>${b.publisher}</td>
                <td><fmt:formatNumber value="${b.price}" pattern="#,##0"/></td>
                <td>${b.quantity}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/admin/book-view?id=${b.bookid}">Xem</a>
                    <a href="${pageContext.request.contextPath}/admin/book-form?id=${b.bookid}">Sửa</a>
                    <a class="danger" href="${pageContext.request.contextPath}/admin/book-delete?id=${b.bookid}&page=${currentPage}"
                       onclick="return confirm('Xóa sách [${b.title}]?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty books}">
            <tr><td colspan="8" class="muted">Chưa có sách nào.</td></tr>
        </c:if>
    </table>

    <%-- Phân trang --%>
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}">‹ Trước</a>
        </c:if>
        <c:forEach var="p" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${p == currentPage}"><span class="active">${p}</span></c:when>
                <c:otherwise><a href="?page=${p}">${p}</a></c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}">Sau ›</a>
        </c:if>
    </div>
</div>

<style>
    .admin-wrap { max-width: 1000px; margin: 0 auto; }
    .back { display: inline-block; color: #7f8c8d; text-decoration: none; font-size: 13px; margin-bottom: 10px; }
    .back:hover { color: #e67e22; }
    .admin-head { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; }
    .admin-head h1 { font-size: 22px; color: #2c3e50; margin: 0; }
    .btn-add {
        background: #2c3e50; color: #f39c12; font-weight: bold; text-decoration: none;
        padding: 9px 18px; border-radius: 6px; font-size: 14px;
    }
    .btn-add:hover { background: #34495e; }

    .admin-table { width: 100%; border-collapse: collapse; background: #fff; box-shadow: 0 4px 16px rgba(0,0,0,.06); }
    .admin-table th, .admin-table td { border: 1px solid #e5e8e8; padding: 8px 10px; font-size: 14px; color: #34495e; text-align: left; }
    .admin-table th { background: #2c3e50; color: #ecf0f1; }
    .admin-table tr:nth-child(even) td { background: #fbfcfc; }
    .actions a { color: #2980b9; text-decoration: none; margin-right: 8px; }
    .actions a.danger { color: #c0392b; }
    .actions a:hover { text-decoration: underline; }
    .muted { color: #95a5a6; text-align: center; }

    .pagination { display: flex; gap: 6px; margin-top: 14px; justify-content: center; }
    .pagination a, .pagination span {
        padding: 6px 12px; border: 1px solid #e5e8e8; border-radius: 6px;
        color: #2c3e50; text-decoration: none; font-size: 14px; background: #fff;
    }
    .pagination span.active { background: #f39c12; color: #fff; border-color: #f39c12; font-weight: bold; }
    .pagination a:hover { border-color: #f39c12; }
</style>
