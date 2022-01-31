package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {
    @Override
    protected String getObject() {
        return "User";
    }

    @Override
    public User find(long id) {
        return find(new String[] {"id"}, new Object[] {id});
    }

    @Override
    public User findByLogin(String login) {
        return find(new String[] {"login"}, new Object[] {login});
    }

    @Override
    public User findByEmail(String email) {
        return find(new String[] {"email"}, new Object[] {email});
    }

    private User findByLoginAndPasswordSha(String login, String passwordSha) {
        return find(new String[] {"login", "passwordSha"}, new Object[] {login, passwordSha});
    }

    private User findByEmailAndPasswordSha(String email, String passwordSha) {
        return find(new String[] {"email", "passwordSha"}, new Object[] {email, passwordSha});
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        User user = findByLoginAndPasswordSha(loginOrEmail, passwordSha);
        if (user == null) {
            user = findByEmailAndPasswordSha(loginOrEmail, passwordSha);
        }
        return user;
    }

    @Override
    public User findByLoginOrEmail(String loginOrEmail) {
        User user = findByLogin(loginOrEmail);
        if (user == null) {
            user = findByEmail(loginOrEmail);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return findAll("ORDER BY id DESC");
    }

    @Override
    public void save(User user, String passwordSha) {
        save(user, new String[] {"login", "email", "passwordSha", "admin"},
                new Object[]{user.getLogin(), user.getEmail(), passwordSha, false});
    }

    @Override
    protected void saveImpl(User user, ResultSet generatedKeys) throws SQLException {
        user.setId(generatedKeys.getLong(1));
        user.setCreationTime(find(user.getId()).getCreationTime());
    }

    @Override
    public void updateAdmin(long userId, boolean admin) {
        updateField("admin", admin, userId);
    }

    public long findCount() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) FROM User")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't count users.", e);
        }
    }

    @Override
    protected User toObjectImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "admin":
                    user.setAdmin(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations.
            }
        }
        return user;
    }
}
