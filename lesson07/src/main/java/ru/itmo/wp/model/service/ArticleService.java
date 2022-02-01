package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.*;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    public void validateArticle(String title, String text) throws ValidationException {
        if (Strings.isNullOrEmpty(title)) {
            throw new ValidationException("Title should contain at least one symbol");
        }
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Text should contain at least one symbol");
        }
        if (title.length() > 255) {
            throw new ValidationException("Title can't be longer than 255 characters");
        }
        if (text.length() > 255) {
            throw new ValidationException("Text can't be longer than 255 characters");
        }
    }

    public void updateHidden(long articleId, boolean hidden) {
        articleRepository.updateHidden(articleId, hidden);
    }

    public void validateArticle(long articleId, long userId) throws ValidationException {
        if (articleRepository.find(articleId) == null) {
            throw new ValidationException("No such article");
        }
        if (articleRepository.find(articleId).getUserId() != userId) {
            throw new ValidationException("This is another user's article");
        }
    }

    public void saveArticle(long userId, String title, String text) {
        Article article = new Article();
        article.setUserId(userId);
        article.setTitle(title);
        article.setText(text);
        articleRepository.save(article);
    }

    public List<ArticleViewAll> findAll() {
        List<ArticleViewAll> articles = new ArrayList<>();
        for (Article article : articleRepository.findAll()) {
            articles.add(new ArticleViewAll(
                    userRepository.find(article.getUserId()).getLogin(),
                    article.getTitle(),
                    article.getText(),
                    article.getCreationTime(),
                    getTimeAgo(article.getCreationTime())));
        }
        return articles;
    }

    public List<ArticleViewId> findAllById(long id) {
        List<ArticleViewId> articles = new ArrayList<>();
        for (Article article : articleRepository.findById(id)) {
            articles.add(new ArticleViewId(article.getId(), article.getTitle(), article.isHidden()));
        }
        return articles;
    }

    private String getTimeAgo(Date creationTime) {
        Date date = articleRepository.getCurrentTime();

        Calendar begin = new GregorianCalendar();
        begin.setTimeInMillis(0);

        Calendar differenceOfDates = new GregorianCalendar();
        differenceOfDates.setTimeInMillis(date.getTime() - creationTime.getTime());

        if (begin.get(Calendar.YEAR) != differenceOfDates.get(Calendar.YEAR)) {
            return differenceOfDates.get(Calendar.YEAR) - begin.get(Calendar.YEAR) + " years ago";
        }

        if (begin.get(Calendar.MONTH) != differenceOfDates.get(Calendar.MONTH)) {
            return differenceOfDates.get(Calendar.MONTH) - begin.get(Calendar.MONTH) + " months ago";
        }

        if (begin.get(Calendar.DAY_OF_MONTH) != differenceOfDates.get(Calendar.DAY_OF_MONTH)) {
            return differenceOfDates.get(Calendar.DAY_OF_MONTH) - begin.get(Calendar.DAY_OF_MONTH) + " days ago";
        }

        if (begin.get(Calendar.HOUR_OF_DAY) != differenceOfDates.get(Calendar.HOUR_OF_DAY)) {
            return differenceOfDates.get(Calendar.HOUR_OF_DAY) - begin.get(Calendar.HOUR_OF_DAY) + " hours ago";
        }

        if (begin.get(Calendar.MINUTE) != differenceOfDates.get(Calendar.MINUTE)) {
            return differenceOfDates.get(Calendar.MINUTE) - begin.get(Calendar.MINUTE) + " minutes ago";
        }

        return "few seconds ago";
    }

    public static class ArticleViewId {
        private long id;
        private String title;
        private boolean hidden;

        public ArticleViewId(long id, String title, boolean hidden) {
            this.id = id;
            this.title = title;
            this.hidden = hidden;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isHidden() {
            return hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }
    }

    public static class ArticleViewAll {
        private String user;
        private String title;
        private String text;
        private Date creationTime;
        private String timeAgo;

        public ArticleViewAll(String user, String title, String text, Date creationTime, String timeAgo) {
            this.user = user;
            this.title = title;
            this.text = text;
            this.creationTime = creationTime;
            this.timeAgo = timeAgo;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getTimeAgo() {
            return timeAgo;
        }

        public void setTimeAgo(String timeAgo) {
            this.timeAgo = timeAgo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Date getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(Date creationTime) {
            this.creationTime = creationTime;
        }
    }
}
