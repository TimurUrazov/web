package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.TalksService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BasePage {
    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    protected final TalksService talksService = new TalksService();
    protected final ArticleService articleService = new ArticleService();
    private HttpServletRequest request;

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        putUser(request, view);
        view.put("userCount", userService.findCount());
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {

    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
    }

    private void putUser(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    protected void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }
}
