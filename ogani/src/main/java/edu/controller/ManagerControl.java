package edu.controller;

import edu.beans.Account;
import edu.beans.Category;
import edu.beans.Product;
import edu.entity.ProductEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerControl", urlPatterns = "/manage")
public class ManagerControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Account a = (Account) session.getAttribute("acc");
        ProductEntity pe = new ProductEntity();
        List<Product> list = pe.getAll();
        List<Category> listC = pe.getAllCategory();
        String indexS = request.getParameter("index");
        int index = Integer.parseInt(indexS);

        int count = list.size();
        int pageSize = 5;
        int endPage = 0;
        endPage = count / pageSize;
        if(count % pageSize != 0){
            endPage++;
        }

        List<Product> listPage = pe.get5Each(index,pageSize);


        request.setAttribute("endPage",endPage);
        request.setAttribute("listPage",listPage);
        request.setAttribute("listP",list);
        request.setAttribute("listC",listC);
        request.setAttribute("index",index);
        request.getRequestDispatcher("manageproduct.jsp").forward(request,response);
    }
}
