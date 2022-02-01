package ru.itmo.wp.web.page;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends BasePage {
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            setMessage("Messages are available only for logged users");
            throw new RedirectException("/index");
        }
        view.put("users", userService.findAll());
        view.put("talks", talksService.findAllById(getUser().getId()));
    }

    public void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String recipient = request.getParameter("recipient");
        String text = request.getParameter("text");

        talksService.validateText(text);

        User target = userService.findByLoginOrEmail(recipient);

        talksService.saveMessage(getUser(),target, text);
        throw new RedirectException("/talks");
    }
}
