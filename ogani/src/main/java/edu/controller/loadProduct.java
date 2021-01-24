package edu.controller;

import edu.beans.Category;
import edu.beans.Product;
import edu.entity.ProductEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "loadProduct", urlPatterns = "/loadProduct")
public class loadProduct extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("pid");
        ProductEntity pe = new ProductEntity();
        Product p = pe.getById(id);
        List<Category> listC = pe.getAllCategory();

        request.setAttribute("detail",p);
        request.setAttribute("listC",listC);
        request.getRequestDispatcher("edit.jsp").forward(request,response);
    }
}
