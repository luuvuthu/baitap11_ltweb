<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="admin-wrap">
    <a class="back" href="${pageContext.request.contextPath}/admin/authors">‹ Về danh sách tác giả</a>
    <h1>${empty author.authorId ? 'Thêm tác giả mới' : 'Cập nhật tác giả'}</h1>

    <form class="admin-form" action="${pageContext.request.contextPath}/admin/author-save" method="post">
        <input type="hidden" name="authorId" value="${author.authorId}">
        <input type="hidden" name="page" value="1">

        <label>Tên tác giả
            <input type="text" name="authorName" value="${author.authorName}" required>
        </label>
        <label>Ngày sinh
            <input type="date" name="dateOfBirth"
                   value="<fmt:formatDate value='${author.dateOfBirth}' pattern='yyyy-MM-dd'/>">
        </label>

        <div class="btn-row">
            <button type="submit">${empty author.authorId ? 'Thêm mới' : 'Cập nhật'}</button>
            <a class="btn-cancel" href="${pageContext.request.contextPath}/admin/authors">Hủy</a>
        </div>
    </form>
</div>

<style>
    .admin-wrap { max-width: 480px; margin: 0 auto; }
    .back { display: inline-block; color: #7f8c8d; text-decoration: none; font-size: 13px; margin-bottom: 10px; }
    .back:hover { color: #e67e22; }
    h1 { font-size: 22px; color: #2c3e50; }
    .admin-form {
        background: #fff; padding: 20px; border-radius: 10px;
        box-shadow: 0 4px 16px rgba(0,0,0,.06);
        display: flex; flex-direction: column; gap: 12px;
    }
    .admin-form label { font-size: 14px; color: #2c3e50; font-weight: bold; display: flex; flex-direction: column; gap: 4px; }
    .admin-form input {
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
