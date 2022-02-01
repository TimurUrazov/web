package ru.itmo.wp.web.page;

import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class ArticlePage extends BasePage {
    @Json
    void createArticle(HttpServletRequest request) throws ValidationException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        articleService.validateArticle(title, text);

        articleService.saveArticle(getUser().getId(), title, text);

        throw new RedirectException("/index");
    }
}
