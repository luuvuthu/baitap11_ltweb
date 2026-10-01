<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>BookStore - <sitemesh:write property="title" xmlns:sitemesh="http://www.sitemesh.org/xml/sitemesh3"/></title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, "Segoe UI", sans-serif; background: #f4f4f4; min-height: 100vh; display: flex; flex-direction: column; }
        header { background: #2c3e50; color: #fff; padding: 12px 24px; }
        header .brand { font-size: 20px; font-weight: bold; color: #f39c12; display: inline-block; margin-right: 40px; }
        header nav { display: inline-block; }
        header nav a { color: #ecf0f1; text-decoration: none; margin-right: 24px; padding: 6px 10px; border-radius: 4px; }
        header nav a:hover, header nav a.active { background: #34495e; color: #f39c12; }
        header .user-info { float: right; }
        header .user-info span { margin-right: 12px; }
        header .user-info a { color: #f39c12; text-decoration: none; }
        main { flex: 1; width: 90%; max-width: 1100px; margin: 24px auto; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,.12); }
        table { width: 100%; border-collapse: collapse; margin-top: 12px; }
        th, td { border: 1px solid #ddd; padding: 8px 10px; text-align: left; }
        th { background: #ecf0f1; }
        footer { background: #2c3e50; color: #bdc3c7; text-align: center; padding: 14px 0; font-size: 14px; }
        footer b { color: #f39c12; }
    </style>
</head>
<body>
<header>
    <span class="brand">BookStore</span>
    <nav>
        <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
        <a href="${pageContext.request.contextPath}/products">Sản phẩm</a>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/cart">🛒 Giỏ hàng</a>
                <a href="${pageContext.request.contextPath}/orders">Đơn hàng</a>
                <a href="${pageContext.request.contextPath}/logout">Đăng xuất (${sessionScope.user.fullname})</a>
                <c:if test="${sessionScope.isAdmin}">
                    <a href="${pageContext.request.contextPath}/admin">Trang quản trị</a>
                </c:if>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<main>
    <sitemesh:write property="body" xmlns:sitemesh="http://www.sitemesh.org/xml/sitemesh3"/>
</main>

<footer>
    <p><b>Họ và tên:</b> Lưu Vũ Thu &nbsp;|&nbsp; <b>MSSV:</b> 24162122 &nbsp;|&nbsp; <b>Mã đề:</b> 01</p>
</footer>
</body>
</html>
