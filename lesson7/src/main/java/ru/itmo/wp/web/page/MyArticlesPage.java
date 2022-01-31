package ru.itmo.wp.web.page;

import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class MyArticlesPage extends BasePage {
    @Json
    public void updateHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long articleId = Long.parseLong(request.getParameter("articleId"));
        boolean hidden = Boolean.parseBoolean(request.getParameter("hide"));

        articleService.validateArticle(articleId, getUser().getId());

        articleService.updateHidden(articleId, hidden);
    }

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAllById(getUser().getId()));
    }
}
