<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="auth-card">
    <div class="auth-head">
        <div class="auth-logo">✉️</div>
        <h1>Kích hoạt tài khoản</h1>
        <p>Nhập mã OTP đã gửi tới <b>${sessionScope.registerEmail}</b></p>
        <p class="hint">Mã có hiệu lực trong 5 phút</p>
    </div>
    <c:if test="${not empty message}"><div class="msg ok">${message}</div></c:if>
    <c:if test="${not empty error}"><div class="msg err">${error}</div></c:if>
    <form method="post" action="${pageContext.request.contextPath}/verify">
        <input type="text" name="otp" class="otp-input" maxlength="6" pattern="[0-9]{6}" placeholder="- - - - - -" required autofocus>
        <button type="submit">Kích hoạt</button>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/resend">
        <button type="submit" class="btn-light">Gửi lại mã OTP</button>
    </form>
    <p class="auth-link"><a href="${pageContext.request.contextPath}/login">Về trang đăng nhập</a></p>
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
        text-align: center;
    }
    .auth-logo { font-size: 40px; }
    .auth-head h1 { font-size: 22px; color: #2c3e50; margin: 6px 0 2px; }
    .auth-head p { color: #7f8c8d; font-size: 13px; }
    .hint { font-size: 12px; color: #95a5a6; }
    .otp-input {
        width: 100%;
        padding: 10px;
        font-size: 22px;
        letter-spacing: 12px;
        text-align: center;
        border: 1px solid #dfe6e9;
        border-radius: 6px;
        box-sizing: border-box;
        outline: none;
        margin-bottom: 4px;
    }
    .otp-input:focus { border-color: #f39c12; }
    .auth-card button {
        width: 100%;
        margin-top: 12px;
        padding: 10px;
        background: #2c3e50;
        color: #f39c12;
        font-weight: bold;
        font-size: 15px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
    }
    .auth-card button.btn-light { background: #ecf0f1; color: #2c3e50; }
    .msg { padding: 10px 12px; border-radius: 6px; font-size: 13px; margin-bottom: 6px; text-align: left; }
    .msg.err { background: #fdecea; color: #c0392b; border: 1px solid #f5b7b1; }
    .msg.ok { background: #eafaf1; color: #1e8449; border: 1px solid #a9dfbf; }
    .auth-link { text-align: center; font-size: 13px; color: #7f8c8d; margin-top: 16px; }
    .auth-link a { color: #e67e22; text-decoration: none; }
</style>
