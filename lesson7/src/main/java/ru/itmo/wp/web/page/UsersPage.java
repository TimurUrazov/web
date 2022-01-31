package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class UsersPage extends BasePage {
    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            request.getSession().setAttribute("user", userService.find(user.getId()));
        }
        super.before(request, view);
    }

    @Json
    public void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        if (getUser() != null) {
            view.put("isAdmin", getUser().isAdmin());
        }
    }

    @Json
    public void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user", userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    public void updateAdmin(HttpServletRequest request) throws ValidationException {
        long id = Long.parseLong(request.getParameter("userId"));
        boolean admin = Boolean.parseBoolean(request.getParameter("admin"));
        userService.validateAndUpdateAdmin(getUser().getId(), id, admin);
    }
}
