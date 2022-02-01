package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MessageServlet extends HttpServlet {
    private static class UserAndText {
        final String user;
        final String text;
        private UserAndText(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    private final ArrayList<UserAndText> messages = new ArrayList<>();

    private void processAuthentication(HttpServletRequest request, HttpSession session, PrintWriter writer) {
        String user = request.getParameter("user");
        String json = new Gson().toJson("");
        if (user != null && !"".equals(user.trim())) {
            json = new Gson().toJson(user);
            session.setAttribute("user", user);
        }
        writer.print(json);
        writer.flush();
    }

    private void processTexts(PrintWriter writer, HttpSession session) {
        if (session.getAttribute("user") != null) {
            writer.print(new Gson().toJson(messages));
            writer.flush();
        }
    }

    private void processAddition(HttpServletRequest request, HttpSession session) {
        String user = (String) session.getAttribute("user");
        String string = request.getParameter("text");
        if (user != null && !"".equals(string.trim())) {
            messages.add(new UserAndText(user, string));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        switch (uri) {
            case "/message/auth" :
                processAuthentication(request, session, writer);
                break;
            case "/message/findAll" :
                processTexts(writer, session);
                break;
            case "/message/add" :
                processAddition(request, session);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
