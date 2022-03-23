package edu.controller;

import edu.beans.Account;
import edu.entity.ProductEntity;

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

@WebServlet(name = "verify", urlPatterns = "/verify")
public class Verify extends HttpServlet {
    public static boolean verifySignature(String pubKeyPath, String sigPath, String filePath) {
        boolean verifies = false;
        try {
            FileInputStream keyfis = new FileInputStream(pubKeyPath);
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);

            String path = sigPath;
            FileInputStream sigfis = new FileInputStream(path);
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify);
            sigfis.close();

            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(publicKey);
            FileInputStream datafis = new FileInputStream(filePath);
            BufferedInputStream bufin = new BufferedInputStream(datafis);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) != -1) {
                sig.update(buffer, 0, len);
            }
            bufin.close();

            verifies = sig.verify(sigToVerify);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifies;
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

        DownloadServlet downloadServlet = new DownloadServlet();
        String pubKeyPath = getServletContext().getRealPath("key/" + folderName + "/public/public.pub");
        String filePath = getServletContext().getRealPath("hoadon/" + folderName + "/"
                + downloadServlet.pickLatestFileFromDownloads((getServletContext().getRealPath("hoadon/" + folderName))));

        String signPath = getServletContext().getRealPath("signature/" + folderName + "/"
                + downloadServlet.pickLatestFileFromDownloads((getServletContext().getRealPath("signature/" + folderName))));

        boolean result = verifySignature(pubKeyPath, signPath, filePath);
        if(!result) {
            String alert = "Chữ ký không hợp lệ";
            request.setAttribute("alert", alert);
            request.getRequestDispatcher("order.jsp").forward(request,response);
        } else {
            request.setAttribute("pubKeyPath", pubKeyPath);
            request.setAttribute("filePath", filePath);
            request.setAttribute("signPath", signPath);
            request.getRequestDispatcher("success.jsp").forward(request,response);
        }
    }
}
