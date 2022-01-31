package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    private String generateCaptcha() {
        return Integer.toString(100 + new Random().nextInt(900));
    }
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if ("GET".equals(request.getMethod())) {
            if ("/captcha.png".equals(request.getRequestURI())) {
                response.setContentType("image/png");
                response.getOutputStream().write(ImageUtils.toPng((String) session.getAttribute("captcha")));
                return;
            }
            if (session.getAttribute("ensureCaptcha") == null) {
                session.setAttribute("ensureCaptcha", "unchecked");
                session.setAttribute("uri", request.getRequestURI());
                System.out.println(request.getRequestURI());
                session.setAttribute("captcha", generateCaptcha());
                response.sendRedirect("/captcha.html");
            } else if (!request.getRequestURI().equals("/captcha.html") && session.getAttribute("ensureCaptcha").equals("unchecked")) {
                response.sendRedirect("/captcha.html");
            }
            chain.doFilter(request, response);
        }
        if ("POST".equals(request.getMethod())) {
            String assumption = request.getParameter("captcha");
            String captcha = (String) session.getAttribute("captcha");
            if (captcha != null && captcha.equals(assumption) || session.getAttribute("ensureCaptcha") != null && session.getAttribute("ensureCaptcha").equals("checked")) {
                session.setAttribute("ensureCaptcha", "checked");
                response.sendRedirect((String) session.getAttribute("uri"));
            } else {
                session.setAttribute("captcha", generateCaptcha());
                response.sendRedirect("/captcha.html");
            }
        }
    }
}
