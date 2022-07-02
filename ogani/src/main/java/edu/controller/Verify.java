package edu.controller;

import edu.beans.Account;
import edu.entity.ProductEntity;
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
import java.util.Base64;

@WebServlet(name = "verify", urlPatterns = "/verify")
public class Verify extends HttpServlet {
    public static boolean verifySignature(String pubKeyBase64, String sigPath, String filePath) {
        boolean verifies = false;
        try {
            byte[] encKey = Base64.getDecoder().decode(pubKeyBase64);
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
        ProductEntity pe = new ProductEntity();
        String pubKeyBase64 = (String) session.getAttribute("publicKey");

        String filePath = getServletContext().getRealPath("hoadon/" + folderName + "/"
                + downloadServlet.pickLatestFileFromDownloads((getServletContext().getRealPath("hoadon/" + folderName))));

        String signPath = getServletContext().getRealPath("signature/" + folderName + "/"
                + downloadServlet.pickLatestFileFromDownloads((getServletContext().getRealPath("signature/" + folderName))));

        boolean result = verifySignature(pubKeyBase64, signPath, filePath);

        System.out.println(pubKeyBase64);
        System.out.println(result);
        if(!result) {
            String alert = "Chữ ký không hợp lệ";
            request.setAttribute("alert", alert);
            request.getRequestDispatcher("order.jsp").forward(request,response);
        } else {
            byte[] signBytes = FileUtils.readFileToByteArray(new File(signPath));
            String signBase64 = Base64.getEncoder().encodeToString(signBytes);
            pe.saveSignature((String) session.getAttribute("orderName"), signBase64);

            File dir = new File(getServletContext().getRealPath("signature/" + folderName));
            for(File file: dir.listFiles()) {
                if (file == null) {
                    return;
                } else {
                    file.delete();
                }
            }
            session.removeAttribute("cart");
            response.sendRedirect("success.jsp");
        }
    }
}
