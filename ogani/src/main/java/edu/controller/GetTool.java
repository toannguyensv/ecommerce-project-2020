package edu.controller;

import edu.beans.Account;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet(name = "gettool", urlPatterns = { "/gettool" })
public class GetTool extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GetTool() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File filepath = new File(getServletContext().getRealPath("signtool/CreateSignature.jar"));
        byte[] data = FileUtils.readFileToByteArray(filepath);
        // Thiết lập thông tin trả về
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=CreateSignature.jar");
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
