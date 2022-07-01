<%@ page import="edu.beans.Product" %>
<%@ page import="edu.model.Cart" %>
<%@ page import="java.util.Collection" %>
<%@ page import="edu.beans.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>OganiNLU - Thực phẩm hữu cơ</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');

        /* Housekeeping */
        body{
            font-size:12px;
        }
        .spreadSheetGroup{
            /*font:0.75em/1.5 sans-serif;
            font-size:14px;
          */
            color:#333;
            background-color:#fff;
            padding:1em;
        }

        /* Tables */
        .spreadSheetGroup table{
            width:100%;
            margin-bottom:1em;
            border-collapse: collapse;
        }
        .spreadSheetGroup .proposedWork th{
            background-color:#7fad39;
        }
        .tableBorder th{
            background-color:#eee;
        }
        .spreadSheetGroup th,
        .spreadSheetGroup tbody td{
            padding:0.5em;

        }
        .spreadSheetGroup tfoot td{
            padding:0.5em;

        }
        .spreadSheetGroup td:focus {
            border:1px solid #fff;
            -webkit-box-shadow:inset 0px 0px 0px 2px #5292F7;
            -moz-box-shadow:inset 0px 0px 0px 2px #5292F7;
            box-shadow:inset 0px 0px 0px 2px #5292F7;
            outline: none;
        }
        .spreadSheetGroup .spreadSheetTitle{
            font-weight: bold;
        }
        .spreadSheetGroup tr td{
            text-align:center;
        }
        /*
        .spreadSheetGroup tr td:nth-child(2){
          text-align:left;
          width:100%;
        }
        */

        /*
        .documentArea.active tr td.calculation{
          background-color:#fafafa;
          text-align:right;
          cursor: not-allowed;
        }
        */
        .spreadSheetGroup .calculation::before, .spreadSheetGroup .groupTotal::before{
            /*content: "$";*/
        }
        .spreadSheetGroup .trAdd{
            background-color: #007bff !important;
            color:#fff;
            font-weight:800;
            cursor: pointer;
        }
        .spreadSheetGroup .tdDelete{
            background-color: #eee;
            color:#888;
            font-weight:800;
            cursor: pointer;
        }
        .spreadSheetGroup .tdDelete:hover{
            background-color: #df5640;
            color:#fff;
            border-color: #ce3118;
        }
        .documentControls{
            text-align:right;
        }
        .spreadSheetTitle span{
            padding-right:10px;
        }

        .spreadSheetTitle a{
            font-weight: normal;
            padding: 0 12px;
        }
        .spreadSheetTitle a:hover, .spreadSheetTitle a:focus, .spreadSheetTitle a:active{
            text-decoration:none;
        }
        .spreadSheetGroup .groupTotal{
            text-align:right;
        }



        table.style1 tr td:first-child{
            font-weight:bold;
            white-space:nowrap;
            text-align:right;
        }
        table.style1 tr td:last-child{
            border-bottom:1px solid #000;
        }



        table.proposedWork td,
        table.proposedWork th,
        table.exclusions td,
        table.exclusions th{
            border:1px solid #000;
        }
        table.proposedWork thead th, table.exclusions thead th{
            font-weight:bold;
        }
        table.proposedWork td,
        table.proposedWork th:first-child,
        table.exclusions th, table.exclusions td{

        }
        table.proposedWork td.description{
            width:65%;
        }

        table.proposedWork td.amountColumn, table.proposedWork th.amountColumn,
        table.proposedWork td:last-child, table.proposedWork th:last-child{
            text-align:center;
            white-space:nowrap;
        }

        .amount:before, .total:before{

        }
        table.proposedWork tfoot td:first-child{
            border:none;
            text-align:right;
        }
        table.proposedWork tfoot tr:last-child td{
            font-size:16px;
            font-weight:bold;
        }

        table.style1 tr td:last-child{
            width:100%;
        }
        table.style1 td:last-child{
            text-align:left;
        }
        td.tdDelete{
            width:1%;
        }

        table.coResponse td{text-align:left}
        table.shipToFrom td, table.shipToFrom th{text-align:left}
        [contenteditable]:focus {
            border: 0px solid transparent;
        }

        .docEdit{border:0 !important}

        .tableBorder td, .tableBorder th{
            border:1px solid #000;
        }
        .tableBorder th, .tableBorder td{text-align:center}

        table.proposedWork td, table.proposedWork th{text-align:center}
        table.proposedWork td.description{text-align:left}

        #myDIV {

        }

        #content {
            height: 300px;
            width: 100%;
            background-color: #e9e9e9;
        }

        .load-btn {
            text-align: center;
            margin-bottom: 3rem;
        }

        .guide {
            text-decoration: underline;
        }
        .guide:hover, .guide:focus {
            text-decoration: underline;
            color: #007bff;
        }
    </style>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap" rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<%
    Account acc = (Account) session.getAttribute("acc");
    if (acc == null) {
        response.sendRedirect("login.jsp");
    }
%>
<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<jsp:include page="menu.jsp"></jsp:include>

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg" data-setbg="img/breadcrumb.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="breadcrumb__text">
                    <h2>Xác nhận đặt hàng</h2>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Checkout Section Begin -->
<section class="checkout spad">
    <div class="container">

        <div class="checkout__form html-content">
            <h4 style="font-size: 1.6rem; margin-left: 1em;">Thông tin đơn hàng</h4>
            <div class="document active">
                <div class="spreadSheetGroup">

                    <table class="shipToFrom">
                        <thead style="font-weight:bold">
                        <tr>
                            <th style="font-size: 1.2rem">Địa chỉ nhận hàng:</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td style="width:50%">
                                <b>${ten}</b><br/>
                                    ${diachi} <br/>
                                    Điện thoại: ${dienthoai}<br/>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <hr style="visibility:hidden"/>

                    <table class="proposedWork" width="100%" style="margin-top:20px">
                        <% Cart c = Cart.getCart(session);
                            Collection<Product> data = c.getData();
                            request.setAttribute("data",data);
                            long total = c.total();
                            request.setAttribute("total",total);
                        %>
                        <thead style="color: white">
                        <th>Số lượng</th>
                        <th>Mô tả</th>
                        <th>Đơn giá</th>
                        <th class="amountColumn">Tổng</th>
                        </thead>
                        <tbody>
                        <c:forEach items="${data}" var="d">
<%--                            <li>${d.name} - SL:${d.quantity}<span>${d.price}</span></li>--%>
                            <tr>
                                <td>${d.quantity}</td>
                                <td class="description">${d.name}</td>
                                <td class="amount">${d.price}</td>
                                <td class="amount amountColumn rowTotal">${d.quantity * d.priceLong /1000}00đ</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td style="border:none"></td>
                            <td style="border:none"></td>
                            <td style="border:none;text-align:right">Tổng cộng:</td>
                            <td class="amount subtotal">${total/1000}00đ</td>
                            <td class="docEdit"></td>
                        </tr>
                        <tr>
                            <td style="border:none"></td>
                            <td style="border:none"></td>
                            <td style="border:none;text-align:right">Giảm giá:</td>
                            <td class="amount">0đ</td>
                            <td class="docEdit"></td>
                        </tr>
                        <tr>
                            <td style="border:none"></td>
                            <td style="border:none"></td>
                            <td style="border:none;text-align:right;white-space:nowrap">Phí vận chuyển:</td>
                            <td class="amount">40.000đ</td>
                            <td class="docEdit"></td>
                        </tr>
                        <tr>
                            <td style="border:none"></td>
                            <td style="border:none"></td>
                            <td style="border:none;text-align:right">TỔNG:</td>
                            <td class="total amount">${total/1000 + 40.0}00đ</td>
                            <td class="docEdit"></td>
                        </tr>
                        </tfoot>
                    </table>



                    <table width="100%">
                        <tbody>
                        <tr>
                            <td width="50%" style="vertical-align:top">
                                <table style="width:100%">
                                    <tbody>
                                    <tr>
                                        <td style="text-align:left">
                                            <p>1. Vui lòng thực hiện theo hướng dẫn tạo và upload file signature để thực hiện đặt hàng. <a class="guide" href="#">Hướng dẫn</a></p>
                                            <p>2. Bằng việc tiến hành Đặt Hàng, khách hàng đồng ý với các Điều Kiện Giao Dịch Chung được ban hành bới OganiNLU.</p>
                                            <p>3. Liên hệ với chúng tôi nếu bạn gặp vấn đề trong việc nhận hàng.</p>
                                            <p>4. Mọi phản hồi xin gửi về:</p>
                                            <p style="padding-left:20px">OganiNLU - Thực phẩm hữu cơ
                                                <br/>
                                                8989 Xa lộ Hà Nội
                                                <br/>
                                                Khu Phố 5, P. Linh Trung, Thủ Đức, TP. Hồ Chí Minh
                                                <br/>
                                                Hotline: (84)32-993-0377
                                            </p>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td style="padding-left:40px;padding-right: 21px; width:50%; vertical-align:top">
                                <table style="width:100%">
                                    <tbody>
                                    <tr>
                                        <td style="text-align:left; padding: 0 0 .8rem">
                                            <strong>Lời nhắn:</strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td contenteditable="true" style="text-align:left;border: 1px solid #000"> </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <c:if test="${getKeyVisible}">
                                            <div class="load-btn" style="margin-bottom: 0">
                                                <a href="get-key.jsp" class="site-btn" style="margin: 20px; width: 235px">Nhận private key</a>
                                            </div>
                                            </c:if>
                                            <c:if test="${!getKeyVisible}">
                                                <div></div>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="load-btn">
                                                <a href="gettool" class="site-btn" style="width: 235px">Tải công cụ tạo chữ ký</a>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>

        <div class="load-btn" style="margin-bottom: 1rem">
            <a href="download" class="site-btn" style="margin: 20px">Tải xuống hóa đơn</a>
            <br/>
            <div class="custom-file w-50 mt-5">
                <input onchange="uploadFile()" id="ajaxfile" class="custom-file-input" type="file"/>
                <label class="custom-file-label" for="ajaxfile" id="ajaxfilelabel">Tải lên tập tin signature đã tạo</label>
            </div>
            <br/>
            <a href="verify" class="btn btn-success" style="margin-top: 2rem" role="button">Xác nhận</a>
        </div>
        <div name="wrongAlert" style="margin-bottom: 1rem; text-align: center; color: red" id="mess">
            ${alert}
        </div>


        <p style="text-align: center; font-size: 1.3rem; font-weight: bold; margin-top: 3rem">ĐIỀU KHOẢN DỊCH VỤ</p>
        <div id="myDIV" onscroll="myFunction()">
            <iframe id="content" src='https://view.officeapps.live.com/op/embed.aspx?src=https://res.cloudinary.com/ddhcdehae/raw/upload/v1639302736/samples/term_w8ysrn.docx' frameborder='0'><a target='_blank' href='http://office.com'>Microsoft Office</a> document, powered by <a target='_blank' href='http://office.com/webapps'>Office Online</a>.</iframe>
        </div>

        <p id="demo"></p>
    </div>
</section>


<!-- Checkout Section End -->

<footer class="footer spad">
    <div class="container">
        <div class="row" style="display: flex; justify-content: center;">

            <div class="col-lg-8 col-md-12" style="text-align: center;">
                <div class="footer__widget">
                    <h2>Đăng ký nhận ưu đãi ngay!</h2>
                    <p style="font-size: 1rem">Nhận email về sản phẩm đặc biệt và các ưu đãi hấp dẫn.</p>
                    <form action="#">
                        <input type="text" placeholder="Nhập địa chỉ email">
                        <button type="submit" class="site-btn">Đăng ký</button>
                    </form>
                    <div class="footer__widget__social">
                        <a href="#"><i class="fa fa-facebook"></i></a>
                        <a href="#"><i class="fa fa-instagram"></i></a>
                        <a href="#"><i class="fa fa-twitter"></i></a>
                        <a href="#"><i class="fa fa-pinterest"></i></a>
                    </div>
                </div>
            </div>
        </div>

    </div>
</footer>


<script>
    async function uploadFile() {
        document.getElementById('ajaxfilelabel').innerHTML = ajaxfile.files[0].name;
        document.getElementById('mess').innerHTML = "";

        let formData = new FormData();
        formData.append("file", ajaxfile.files[0]);
        await fetch('fileuploadservlet', {
            method: "POST",
            body: formData
        })
        .then((response) => {
            alert('Tải lên thành công!');
        })
        .catch((error) => {
            alert('Đã có lỗi khi tải lên. Vui lòng kiểm tra lại tập tin')
        });
    }

    $(document).ready(function(){

        function calculateTotal(currentGroup) {
            var groupTotal = 0;
            currentGroup.parents('table').find('.rowTotal').each(function( i ){
                groupTotal = Number(groupTotal) + Number( $(this).text() );
            });
            currentGroup.parents('table').find('.total').text(groupTotal.toFixed(2));
            currentGroup.parents('table').find('.subtotal').text(groupTotal.toFixed(2));
        }

        $(".document.active").delegate( ".tdDelete", "click", function() {
            if ($(this).parents('tbody').children().length > 1){
                $(this).prev().text('0');
                calculateTotal($(this));

                $(this).parents('tr').remove();
            }
        });

        $(".document.active").delegate( ".trAdd", "click", function() {
            $(this).parents('table').find('tbody').append( $(this).parents('table').find('tbody tr:last-child').clone() );
            calculateTotal($(this));
        });

        $(".document.active").delegate( ".amount", "keyup", function() {
            //console.log('test');
            calculateTotal($(this));
        });




        var tdValues = [];
        $(".document.active .proposedWork").delegate( "td:not(.description .unit)", "keyup", function() {
            tdValues.length = 0;

            //Paint
            $(this).parents('tr').find('td').each(function( i ){
                if(i > 4){return false}
                if(i == 4){$(this).text( tdValues[0]*tdValues[3] )}
                tdValues[i] = Number( $(this).text() );
            });

            calculateTotal($(this));
        });

    });
</script>
<!-- Js Plugins -->
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