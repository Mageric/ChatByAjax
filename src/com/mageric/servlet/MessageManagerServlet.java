package com.mageric.servlet;

import com.mageric.tools.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class MessageManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Message.setId();
        Message.setUser(request.getParameter("user"));
        Message.setTime(new Date());
        Message.setMessage(request.getParameter("message"));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Cache-Control", "no-cache, must-revalidate");//避免IE和Edge直接读缓存
    }
}
