package edu.controller;

import edu.beans.Account;
import edu.entity.ProductEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginControl", urlPatterns = "/login")
public class LoginControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        ProductEntity pe = new ProductEntity();
        Account a = pe.login(username,password);
        if(a == null) {
            request.setAttribute("mess", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("acc",a);
            request.getRequestDispatcher("home").forward(request,response);
        }
    }
}
