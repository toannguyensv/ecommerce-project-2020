package edu.controller;

import edu.beans.Account;
import edu.beans.Product;
import edu.model.Cart;
import org.apache.commons.io.FileUtils;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@WebServlet(name = "OrderControl", urlPatterns = "/order")
public class OrderControl extends HttpServlet {
    public static void generateKey(String pubKeyDest, String prvKeyDest){
        File pubFile = new File(pubKeyDest);
        if(!pubFile.exists()) {
            pubFile.mkdirs();
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
            FileOutputStream publicKeyFile = new FileOutputStream(pubKeyDest + "/public.pub");
            publicKeyFile.write(publicKey);
            publicKeyFile.close();
            //        save private key
            byte[] privateKey = priv.getEncoded();
            FileOutputStream privateKeyFile = new FileOutputStream(prvKeyDest + "/private.key");
            privateKeyFile.write(privateKey);
            privateKeyFile.close();
        } catch (Exception e){
            System.err.println(e.toString());
        }
    }

    public boolean isKeyFileAvailable(String path) {
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return false;
        }
        return true;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("ten");
        String address = request.getParameter("add1");
        String phone = request.getParameter("phone");
        String sanpham = "";

        HttpSession session = request.getSession();
        Cart c = Cart.getCart(session);
        Collection<Product> data = c.getData();
        long total = c.total();
        String tong = (total/1000 + 40.0) + "00đ";

        Account account = (Account) session.getAttribute("acc");
        int userId = account.getId();
        String folderName = String.valueOf(userId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String createTime = dtf.format(now);

        String fileName = folderName + "_" + createTime;

        for(Product item: data){
            sanpham += item.toString() + "\n";
        }

        request.setAttribute("ten", name);
        request.setAttribute("diachi", address);
        request.setAttribute("dienthoai", phone);
        request.setAttribute("sanpham", sanpham);

        String billDetail = "Khách hàng: " + name + "\n"
                + "Địa chỉ giao hàng: " + address + "\n"
                + "Điện thoại: " + phone + "\n" + "\n"
                + "Sản phẩm: " + sanpham + "\n"
                + "Phí giao hàng: 40.000đ" + "\n"
                + "Tổng cộng: " + tong;

        // create bill
        File file = new File(getServletContext().getRealPath("hoadon/"+ folderName + "/" + fileName + ".txt"));
        FileUtils.writeStringToFile(file, billDetail, "UTF-8");

        // create key
        boolean keyAvailable = isKeyFileAvailable(getServletContext().getRealPath("key/" + folderName + "/private"));
        if(!keyAvailable) {
            generateKey(getServletContext().getRealPath("key/" + folderName + "/public"),
                    getServletContext().getRealPath("key/" + folderName + "/private"));
        }

        request.getRequestDispatcher("order.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
