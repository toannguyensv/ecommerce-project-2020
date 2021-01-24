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


@WebServlet(name = "CategoryControl", urlPatterns = "/category")
public class CategoryControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String cateId = request.getParameter("cid");
        ProductEntity pe = new ProductEntity();
        List<Product> list = pe.getProductByCid(cateId);

        request.setAttribute("listP",list);
        request.getRequestDispatcher("meat.jsp").forward(request,response);
    }
}
