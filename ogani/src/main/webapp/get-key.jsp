<%@ page import="edu.beans.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Nhận Key</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">

    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');
    </style>
    <style type="text/css">
        body{
            font-family: 'Roboto', sans-serif;
            background-image: url('img/organicproduce.jpg');
            background-size: cover;
        }
        .container {
            background: #e1ffe8;
        }
        .btn-lg {
            border-radius: 11rem !important;
        }
        .btn-outline-success {
            padding: .5rem 15rem;
        }
    </style>
</head>
<%
    Account acc = (Account) session.getAttribute("acc");
    if (acc == null) {
        response.sendRedirect("login.jsp");
    }
%>
<body>
<div id="preloder">
    <div class="loader"></div>
</div>
<div class="container col-9 mx-auto mt-5 pb-5 rounded shadow">
    <div class="media">
        <div class="media-body my-5 mx-5">
            <h2 class="mt-0 mb-5">Nhận Private Key của bạn</h2>
            <b>Private Key</b> dùng để xác minh người thực hiện mua hàng chính là bạn. <br><br>
            Tải tập tin <b>Private Key</b> này xuống và lưu giữ trên thiết bị của bạn hoặc sao lưu ở những môi trường lưu trữ khác.<br><br>
            <b><i>Lưu ý:</i></b> Bạn không thể đặt hàng nếu không có tập tin <b>Private Key</b>.
        </div>
        <div class="d-flex flex-column mx-5">
            <img class="mb-4 mt-5" src="img/key-img.png" alt="image" style="width:15rem; height: 15rem"/>
            <a class="btn btn-success btn-lg mb-5" href="getkey">Tải xuống</a>
        </div>
    </div>
    <div class="d-block text-center">
        <a href="key-confirm.jsp" class="btn btn-outline-success btn-lg">Tiếp tục</a>
    </div>
</div>

<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/mixitup.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>
</body>
</html>