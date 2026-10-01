<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản trị - <sitemesh:write property="title" xmlns:sitemesh="http://www.sitemesh.org/xml/sitemesh3"/></title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, "Segoe UI", sans-serif; background: #e8eaed; min-height: 100vh; display: flex; flex-direction: column; }
        header { background: #1a1a2e; color: #fff; padding: 12px 24px; }
        header .brand { font-size: 20px; font-weight: bold; color: #e94560; margin-right: 40px; }
        header nav { display: inline-block; }
        header nav a { color: #ededed; text-decoration: none; margin-right: 24px; padding: 6px 10px; border-radius: 4px; }
        header nav a:hover { background: #16213e; color: #e94560; }
        main { flex: 1; width: 95%; max-width: 1200px; margin: 24px auto; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 1px 6px rgba(0,0,0,.15); }
        table { width: 100%; border-collapse: collapse; margin-top: 12px; }
        th, td { border: 1px solid #ddd; padding: 8px 10px; text-align: left; }
        th { background: #16213e; color: #fff; }
        footer { background: #1a1a2e; color: #9aa0a6; text-align: center; padding: 14px 0; font-size: 14px; }
        footer b { color: #e94560; }
    </style>
</head>
<body>
<header>
    <span class="brand">BookStore - Quản trị</span>
    <nav>
        <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
        <a href="${pageContext.request.contextPath}/products">Sản phẩm</a>
        <a href="${pageContext.request.contextPath}/admin">Trang quản trị</a>
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất (${sessionScope.user.fullname})</a>
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
