package edu.controller;

import edu.beans.Account;
import edu.entity.ProductEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;


@WebServlet(name = "SignUpControl", urlPatterns = "/signup")
public class SignUpControl extends HttpServlet {
    public static void generateKey(String user, String prvKeyDest){
        File prvFile = new File(prvKeyDest);
        if(!prvFile.exists()) {
            prvFile.mkdirs();
        }
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA","SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
            keyGen.initialize(1024,random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();

            //        save public key
            byte[] publicKey = pub.getEncoded();
            String publicKeyStr = Base64.getEncoder().encodeToString(publicKey);
            ProductEntity pe = new ProductEntity();
            pe.savePublicKey(user, publicKeyStr);

            //        save private key
            byte[] privateKey = priv.getEncoded();
            FileOutputStream privateKeyFile = new FileOutputStream(prvKeyDest + "/private.key");
            privateKeyFile.write(privateKey);
            privateKeyFile.close();
        } catch (Exception e){
            System.err.println(e.toString());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String re_pass = request.getParameter("repass");
        if(!pass.equals(re_pass)) {
            request.setAttribute("mess", "Mật khẩu nhập lại không khớp");
            request.getRequestDispatcher("signup.jsp").forward(request,response);
        } else {
            ProductEntity pe = new ProductEntity();
            Account a = pe.checkExist(user);
            if(a == null) {
                pe.signUp(user,pass);
                a = pe.login(user,pass);
                int userId = a.getId();
                String folderName = String.valueOf(userId);
                generateKey(user, getServletContext().getRealPath("key/" + folderName));
                HttpSession session = request.getSession();
                session.setAttribute("acc",a);
                response.sendRedirect("get-key.jsp");
            } else {
                request.setAttribute("mess", "Email đã được sử dụng");
                request.getRequestDispatcher("signup.jsp").forward(request,response);
            }
        }
    }
}
