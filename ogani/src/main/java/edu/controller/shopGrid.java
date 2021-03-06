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
import java.util.Collection;
import java.util.List;

@WebServlet(name = "shopGrid", urlPatterns = "/shopGrid")
public class shopGrid extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductEntity productEntity = new ProductEntity();
        Collection<Product> list = productEntity.getAll();
        List<Category> listC = productEntity.getAllCategory();

        String indexS = request.getParameter("index");
        int index = Integer.parseInt(indexS);

        int count = list.size();
        int pageSize = 16;
        int endPage = 0;
        endPage = count / pageSize;
        if(count % pageSize != 0){
            endPage++;
        }

        List<Product> listPage = productEntity.get5Each(index,pageSize);

        request.setAttribute("endPage",endPage);
        request.setAttribute("listPage",listPage);
        request.setAttribute("index",index);
        request.setAttribute("listC",listC);
        request.setAttribute("list",list);
        request.getRequestDispatcher("shopgrid.jsp").forward(request,response);
    }
}
