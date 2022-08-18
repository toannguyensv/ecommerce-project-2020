package edu.controller;

import edu.beans.Account;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@WebServlet(name = "FileUploadServlet", urlPatterns = { "/fileuploadservlet" })
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)

public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FileUploadServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("acc");
        int userId = account.getId();
        String folderName = String.valueOf(userId);

        String savePath = getServletContext().getRealPath("/signature/" + folderName + "/");
        File dir = new File(savePath);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        File directory = new File(getServletContext().getRealPath("/signature/" + folderName));
        for(File file: directory.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        for (Part part : request.getParts()) {
            part.write(savePath + fileName);
        }
        DownloadServlet downloadServlet = new DownloadServlet();
        String signPath = getServletContext().getRealPath("signature/" + folderName + "/"
                + downloadServlet.pickLatestFileFromDownloads((getServletContext().getRealPath("signature/" + folderName))));

        byte[] signBytes = FileUtils.readFileToByteArray(new File(signPath));
        String signBase64 = Base64.getEncoder().encodeToString(signBytes);
        session.setAttribute("signBase64", signBase64);
        System.out.println(signBase64);
        response.getWriter().print("Tải lên thành công.");
    }

}
