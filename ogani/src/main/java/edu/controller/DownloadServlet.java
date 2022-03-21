package edu.controller;

import edu.beans.Account;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "download", urlPatterns = { "/download" })
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DownloadServlet() {
        super();
    }

    public String pickLatestFileFromDownloads(String folder) {
        File dir = new File(folder);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        String k = lastModifiedFile.toString();

        System.out.println(lastModifiedFile);
        Path p = Paths.get(k);
        String file = p.getFileName().toString();
        return file;

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("acc");
        int userId = account.getId();
        String folderName = String.valueOf(userId);

        String fileName = pickLatestFileFromDownloads((getServletContext().getRealPath("hoadon/" + folderName)));

        File filepath = new File(getServletContext().getRealPath("hoadon/" + folderName + "/" + fileName));
        byte[] data = FileUtils.readFileToByteArray(filepath);
        // Thiết lập thông tin trả về
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=hoadon "+fileName+".txt");
        response.setContentLength(data.length);
        InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
        // Ghi file ra response outputstream.
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outStream.close();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
