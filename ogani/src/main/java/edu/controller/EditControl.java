package edu.controller;

import edu.entity.ProductEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditControl", urlPatterns = "/edit")
public class EditControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String img = request.getParameter("image");
        String price = request.getParameter("price");
        String priceLong = request.getParameter("title");
        String cateId = request.getParameter("category");

        ProductEntity pe = new ProductEntity();
        pe.editProduct(name,img,price,priceLong,cateId,id);
        response.sendRedirect("manage?index=1");
    }
}
