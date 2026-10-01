<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="admin-wrap">
    <a class="back" href="${pageContext.request.contextPath}/admin/books">‹ Về danh sách sách</a>
    <h1>${empty book.bookid ? 'Thêm sách mới' : 'Cập nhật sách'}</h1>

    <form class="admin-form" action="${pageContext.request.contextPath}/admin/book-save" method="post">
        <input type="hidden" name="bookid" value="${book.bookid}">
        <input type="hidden" name="page" value="1">

        <label>Tiêu đề
            <input type="text" name="title" value="${book.title}" required>
        </label>
        <label>Mã ISBN
            <input type="number" name="isbn" value="${book.isbn}">
        </label>
        <label>Tác giả (giữ Ctrl để chọn nhiều)
            <select name="authors" multiple size="5">
                <c:forEach var="a" items="${allAuthors}">
                    <option value="${a.authorId}" ${book.authors.contains(a) ? 'selected' : ''}>${a.authorName}</option>
                </c:forEach>
            </select>
        </label>
        <label>Publisher
            <input type="text" name="publisher" value="${book.publisher}">
        </label>
        <label>Publisher_date
            <input type="date" name="publishDate"
                   value="<fmt:formatDate value='${book.publishDate}' pattern='yyyy-MM-dd'/>">
        </label>
        <label>Giá (VNĐ)
            <input type="number" step="1000" name="price" value="${book.price}">
        </label>
        <label>Số lượng
            <input type="number" name="quantity" value="${book.quantity}" min="0">
        </label>
        <label>Ảnh bìa (tên file trong /images, vd: nha_gia_kim.jpg)
            <input type="text" name="coverImage" value="${book.coverImage}">
        </label>
        <label>Mô tả
            <textarea name="description" rows="4">${book.description}</textarea>
        </label>

        <div class="btn-row">
            <button type="submit">${empty book.bookid ? 'Thêm mới' : 'Cập nhật'}</button>
            <a class="btn-cancel" href="${pageContext.request.contextPath}/admin/books">Hủy</a>
        </div>
    </form>
</div>

<style>
    .admin-wrap { max-width: 640px; margin: 0 auto; }
    .back { display: inline-block; color: #7f8c8d; text-decoration: none; font-size: 13px; margin-bottom: 10px; }
    .back:hover { color: #e67e22; }
    h1 { font-size: 22px; color: #2c3e50; }
    .admin-form {
        background: #fff; padding: 20px; border-radius: 10px;
        box-shadow: 0 4px 16px rgba(0,0,0,.06);
        display: flex; flex-direction: column; gap: 12px;
    }
    .admin-form label { font-size: 14px; color: #2c3e50; font-weight: bold; display: flex; flex-direction: column; gap: 4px; }
    .admin-form input, .admin-form select, .admin-form textarea {
        padding: 8px 10px; border: 1px solid #dfe6e9; border-radius: 6px;
        font-size: 14px; font-family: inherit; font-weight: normal;
    }
    .btn-row { display: flex; gap: 10px; align-items: center; }
    .btn-row button {
        background: #2c3e50; color: #f39c12; font-weight: bold; font-size: 14px;
        border: none; border-radius: 6px; padding: 10px 26px; cursor: pointer;
    }
    .btn-row button:hover { background: #34495e; }
    .btn-cancel { color: #7f8c8d; text-decoration: none; font-size: 14px; }
    .btn-cancel:hover { color: #c0392b; }
</style>
