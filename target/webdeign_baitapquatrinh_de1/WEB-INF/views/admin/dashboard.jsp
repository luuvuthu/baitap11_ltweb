<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="admin-wrap">
    <h1>Trang quản trị</h1>
    <p>Xin chào quản trị viên: <b>${sessionScope.user.fullname}</b></p>

    <div class="menu-cards">
        <a class="card" href="${pageContext.request.contextPath}/admin/books">
            <span class="icon">📚</span>
            <b>Quản lý sách</b>
            <span class="hint">Xem / Thêm / Sửa / Xóa (có phân trang)</span>
        </a>
        <a class="card" href="${pageContext.request.contextPath}/admin/authors">
            <span class="icon">✍️</span>
            <b>Quản lý tác giả</b>
            <span class="hint">Xem / Thêm / Sửa / Xóa (có phân trang)</span>
        </a>
    </div>
</div>

<style>
    .admin-wrap { max-width: 860px; margin: 0 auto; }
    h1 { font-size: 22px; color: #2c3e50; }
    p { color: #34495e; }
    .menu-cards { display: flex; gap: 16px; margin-top: 18px; }
    .card {
        flex: 1; background: #fff; border-radius: 10px; padding: 24px;
        box-shadow: 0 4px 16px rgba(0,0,0,.06);
        display: flex; flex-direction: column; gap: 8px;
        text-decoration: none; color: #2c3e50; font-size: 16px;
        border-bottom: 3px solid #f39c12;
    }
    .card:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(0,0,0,.1); }
    .card .icon { font-size: 32px; }
    .card .hint { font-size: 13px; color: #95a5a6; font-weight: normal; }
</style>
