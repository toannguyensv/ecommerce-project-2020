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
    <title>Xác nhận Private Key</title>
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
            <h2 class="mt-0 mb-5">Xác nhận Private Key của bạn</h2>
            Vui lòng upload tập tin <b>Private Key</b> của bạn lên để thực hiện xác nhận,
            nếu bạn chưa tải xuống hãy truy cập trang nhận file Private Key <a href="get-key.jsp">tại đây.</a> <br> <br>
            <b><i>Lưu ý:</i></b> Sau khi xác nhận thành công bạn không thể lấy lại <b>Private Key</b> nếu đánh mất.
        </div>
    </div>
    <div class="custom-file w-50 mb-5 ml-5">
        <input onchange="uploadFile()" type="file" class="custom-file-input" id="ajaxfile">
        <label class="custom-file-label" for="ajaxfile" id="ajaxfilelabel">Choose File</label>
    </div>
    <div class="d-block text-center">
        <a href="verifykey" class="btn btn-outline-success btn-lg">
            Tiếp tục
        </a>
    </div>
    <div class="d-block text-center mt-4">
        <h4 id="mess" class="text-danger">${mess}</h4>
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
<script type="text/javascript">
    async function uploadFile() {
        document.getElementById('ajaxfilelabel').innerHTML = ajaxfile.files[0].name;
        document.getElementById('mess').innerHTML = "";
        let formData = new FormData();
        formData.append("file", ajaxfile.files[0]);
        await fetch('keyuploadservlet', {
            method: "POST",
            body: formData
        })
        .then((response) => {
            alert('Tải lên thành công');
        })
        .catch((error) => {
            alert('Đã có lỗi khi tải lên. Vui lòng kiểm tra lại tập tin')
        });
    }
</script>
</body>
</html>