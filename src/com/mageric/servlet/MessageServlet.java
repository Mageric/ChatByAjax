package com.mageric.servlet;

import com.mageric.tools.Message;
import org.codehaus.jettison.json.JSONArray;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * Created by Mageric on 2016/7/28 0028.
 */

public class MessageServlet extends javax.servlet.http.HttpServlet {
    private final Queue<AsyncContext> asyncContexts = new ConcurrentLinkedQueue<AsyncContext>();
    private final Map<String,Integer> userMap       = new HashMap();
    private final Random              random        = new Random();
    private final Thread              generator     = new Thread("Event generator") {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int r=random.nextInt(1000);
                    Thread.sleep(r);
                    while (!asyncContexts.isEmpty()) {
                        AsyncContext       asyncContext = asyncContexts.peek ();
                        HttpServletRequest hsr          = (HttpServletRequest) asyncContext.getRequest();
                        if (Message.getId() > userMap.get(hsr.getParameter("user"))) {
                            asyncContext = asyncContexts.poll ();
                            HttpServletResponse peer = (HttpServletResponse) asyncContext.getResponse();
                            peer.getWriter().write(new JSONArray().put(Message.getTime() + " "+ Message.getUser() + " : "  + Message.getMessage()).toString());
                            peer.setStatus(HttpServletResponse.SC_OK);
                            peer.setHeader("Cache-Control", "no-cache, must-revalidate");
                            peer.setContentType("application/json");
                            asyncContext.complete();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    System.out.println("IOException");
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    };

    @Override
    public void init() throws ServletException {
        generator.start();
    }

    @Override
    public void destroy() {
        generator.interrupt();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        userMap.put(req.getParameter("user"),Message.getId());
        System.out.println(userMap.size());
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(0);
        asyncContexts.offer(asyncContext);
    }
}

