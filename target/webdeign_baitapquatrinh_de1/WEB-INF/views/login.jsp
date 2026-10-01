<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="auth-card">
    <div class="auth-head">
        <div class="auth-logo">📚</div>
        <h1>Đăng nhập</h1>
        <p>Chào mừng trở lại với BookStore!</p>
    </div>
    <c:if test="${not empty error}"><div class="msg err">${error}</div></c:if>
    <c:if test="${not empty success}"><div class="msg ok">${success}</div></c:if>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Tên đăng nhập (email)</label>
        <input type="text" name="username" placeholder="VD: LVT" required autofocus>
        <label>Mật khẩu</label>
        <input type="password" name="password" placeholder="••••••••" required>
        <button type="submit">Đăng nhập</button>
    </form>
    <p class="auth-link">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a></p>
</div>

<style>
    .auth-card {
        max-width: 380px;
        margin: 30px auto;
        background: #fff;
        border: 1px solid #e5e8e8;
        border-radius: 10px;
        padding: 28px 30px;
        box-shadow: 0 4px 16px rgba(0,0,0,.08);
    }
    .auth-head { text-align: center; margin-bottom: 18px; }
    .auth-logo { font-size: 40px; }
    .auth-head h1 { font-size: 22px; color: #2c3e50; margin: 6px 0 2px; }
    .auth-head p { color: #95a5a6; font-size: 13px; }
    .auth-card label { display: block; font-size: 13px; color: #34495e; margin: 12px 0 4px; }
    .auth-card input {
        width: 100%;
        padding: 9px 12px;
        border: 1px solid #dfe6e9;
        border-radius: 6px;
        font-size: 14px;
        box-sizing: border-box;
        outline: none;
    }
    .auth-card input:focus { border-color: #f39c12; }
    .auth-card button {
        width: 100%;
        margin-top: 18px;
        padding: 10px;
        background: #2c3e50;
        color: #f39c12;
        font-weight: bold;
        font-size: 15px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
    }
    .auth-card button:hover { background: #34495e; }
    .msg { padding: 10px 12px; border-radius: 6px; font-size: 13px; margin-bottom: 6px; }
    .msg.err { background: #fdecea; color: #c0392b; border: 1px solid #f5b7b1; }
    .msg.ok { background: #eafaf1; color: #1e8449; border: 1px solid #a9dfbf; }
    .auth-link { text-align: center; font-size: 13px; color: #7f8c8d; margin-top: 16px; }
    .auth-link a { color: #e67e22; text-decoration: none; font-weight: bold; }
</style>
