package edu.controller;

import edu.model.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "OrderControl", urlPatterns = "/order")
public class OrderControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("ten");
        String address = request.getParameter("add1")
                +" - "+ request.getParameter("add2")
                +" - "+ request.getParameter("add3")
                +" - "+ request.getParameter("add4");
        String phone = request.getParameter("phone");

        request.setAttribute("ten", name);
        request.setAttribute("diachi", address);
        request.setAttribute("dienthoai", phone);

        request.getRequestDispatcher("order.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
