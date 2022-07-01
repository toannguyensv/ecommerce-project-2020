package edu.controller;

import edu.beans.Account;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "VerifyKey", urlPatterns = "/verifykey")
public class VerifyKey extends HttpServlet {
    private static boolean isEqual(File firstFile, File secondFile)
    {
        try {
            return FileUtils.contentEquals(firstFile, secondFile);
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("acc");
        int userId = account.getId();
        String folderName = String.valueOf(userId);

        File directory = new File(getServletContext().getRealPath("/keyupload/" + folderName));
        File dir = new File(String.valueOf(directory));
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File[] fileList = directory.listFiles();
        if(fileList.length == 0) {
            request.setAttribute("mess", "Chưa có tập tin nào được tải lên!");
            request.getRequestDispatcher("key-confirm.jsp").forward(request,response);
        } else {
            File uploadFolder = new File(getServletContext().getRealPath("/keyupload/" + folderName));
            File uploadKey = uploadFolder.listFiles()[0];

            File originalKeyFolder = new File(getServletContext().getRealPath("/key/" + folderName));
            File originalKey = originalKeyFolder.listFiles()[0];

            boolean equal = isEqual(uploadKey, originalKey);
            if (equal) {
                response.sendRedirect("verify-success.html");
                uploadKey.delete();
                originalKey.delete();
            }
            else {
                request.setAttribute("mess", "Tập tin tải lên không phải Private Key của bạn. Hãy thử lại");
                uploadKey.delete();
                request.getRequestDispatcher("key-confirm.jsp").forward(request,response);
            }
        }
    }
}
