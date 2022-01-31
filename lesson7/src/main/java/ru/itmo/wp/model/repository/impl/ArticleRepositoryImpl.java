package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.*;
import java.util.List;

public class ArticleRepositoryImpl extends BaseRepositoryImpl<Article> implements ArticleRepository {

    @Override
    protected String getObject() {
        return "Article";
    }

    public Article find(long id) {
        return find(new String[] {"id"}, new Object[] {id});
    }

    @Override
    public void save(Article article) {
        save(article, new String[] {"userId", "title", "text", "hidden"},
                new Object[]{article.getUserId(), article.getTitle(), article.getText(), article.isHidden()});
    }

    @Override
    public List<Article> findAll() {
        return findAll("WHERE hidden=false ORDER BY id DESC");
    }

    @Override
    public List<Article> findById(long id) {
        return findAll("WHERE userId=? ORDER BY id DESC", id);
    }

    @Override
    protected void saveImpl(Article article, ResultSet generatedKeys) throws SQLException {
        article.setId(generatedKeys.getLong(1));
        article.setCreationTime(find(article.getId()).getCreationTime());
    }

    @Override
    public void updateHidden(long articleId, boolean hidden) {
        updateField("hidden", hidden, articleId);
    }

    @Override
    protected Article toObjectImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return article;
    }

    @Override
    public Timestamp getCurrentTime() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT CURRENT_TIMESTAMP")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException("Can't find current server time.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find current server time.", e);
        }
    }
}
